package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Kostur za vežbu F – dopunjava se u folderima 02–07.
 */
public class MainActivity extends AppCompatActivity {

    private TextView textViewLokacija;
    private TextView textViewProksimitet;
    private CheckBox checkBoxSnimanje;
    private CheckBox checkBoxDrzave;
    private Button buttonSnimi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLokacija = findViewById(R.id.textViewLokacija);
        textViewProksimitet = findViewById(R.id.textViewProksimitet);
        checkBoxSnimanje = findViewById(R.id.checkBoxSnimanje);
        checkBoxDrzave = findViewById(R.id.checkBoxDrzave);
        buttonSnimi = findViewById(R.id.buttonSnimi);

        // 02-geolokacija: geoHelper = ...
        // 03-audio-snimanje: audioHelper = ...
        // 04-room-drzave: countryDao = ...
        // 05+06: countryRepository = ...
        // 07-proksimitet: proksimitetHelper = ...
    }

    @Override
    protected void onResume() {
        super.onResume();
        // proksimitetHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // audioHelper.onPause();
        // proksimitetHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // audioHelper.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // geoHelper.onPermissionGranted(requestCode, grantResults);
        // audioHelper.onPermissionGranted(requestCode, grantResults);
    }
}
