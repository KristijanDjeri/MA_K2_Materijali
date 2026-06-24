package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.CountryDao;
import com.example.kolokvijum2.helper.AudioSnimanjeHelper;
import com.example.kolokvijum2.helper.CountryRepository;
import com.example.kolokvijum2.helper.GeoLokacijaHelper;
import com.example.kolokvijum2.helper.ProksimitetHelper;

/**
 * vežba F – kompletna referenca (zadaci 1–8).
 */
public class MainActivity extends AppCompatActivity {

    private TextView textViewLokacija;
    private TextView textViewProksimitet;
    private CheckBox checkBoxSnimanje;
    private CheckBox checkBoxDrzave;
    private Button buttonSnimi;

    private GeoLokacijaHelper geoHelper;
    private AudioSnimanjeHelper audioHelper;
    private ProksimitetHelper proksimitetHelper;

    private CountryDao countryDao;
    private CountryRepository countryRepository;
    private boolean drzaveUcitane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLokacija = findViewById(R.id.textViewLokacija);
        textViewProksimitet = findViewById(R.id.textViewProksimitet);
        checkBoxSnimanje = findViewById(R.id.checkBoxSnimanje);
        checkBoxDrzave = findViewById(R.id.checkBoxDrzave);
        buttonSnimi = findViewById(R.id.buttonSnimi);

        // Zadatak 3 – geolokacija
        geoHelper = new GeoLokacijaHelper(this, textViewLokacija);
        geoHelper.pokreni();

        // Zadatak 4 – audio snimanje
        audioHelper = new AudioSnimanjeHelper(this, buttonSnimi, checkBoxSnimanje);

        // Zadaci 5–7 – Room + Retrofit + CheckBox države
        countryDao = AppDatabase.getInstance(this).countryDao();
        countryRepository = new CountryRepository(this, countryDao);

        checkBoxDrzave.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            if (!drzaveUcitane) {
                countryRepository.ucitajDrzaveSaApi(new CountryRepository.OnDoneListener() {
                    @Override
                    public void onSuccess(int count) {
                        drzaveUcitane = true;
                        checkBoxDrzave.setChecked(false);
                    }

                    @Override
                    public void onFailure(String message) {
                        countryRepository.ucitajDrzaveLokalno(new CountryRepository.OnDoneListener() {
                            @Override
                            public void onSuccess(int count) {
                                drzaveUcitane = true;
                                checkBoxDrzave.setChecked(false);
                            }

                            @Override
                            public void onFailure(String msg) {
                                checkBoxDrzave.setChecked(false);
                            }
                        });
                    }
                });
            } else {
                countryRepository.obrisiPoslednjuDrzavu();
                checkBoxDrzave.setChecked(false);
            }
        });

        // Zadatak 8 – proksimitet
        proksimitetHelper = new ProksimitetHelper(this, textViewProksimitet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        proksimitetHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioHelper.onPause();
        proksimitetHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioHelper.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        geoHelper.onPermissionGranted(requestCode, grantResults);
        audioHelper.onPermissionGranted(requestCode, grantResults);
    }
}
