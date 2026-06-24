package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Početni MainActivity – samo view-ovi + kostur lifecycle metoda.
 * Logiku dodaješ iz segment foldera (02–86).
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageButton imageButton;
    private ImageView imageView;
    private Switch switchPosts;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        switchPosts = findViewById(R.id.switchPosts);
        button = findViewById(R.id.button);

        // Inicijalizacija helpera i listenera – dodaješ u segment folderima
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Senzori, mapa, lokacija uživo – vidi HELPER-KLASE.md
        // ziroskopHelper.onResume();
        // akcelerometarHelper.onResume();
        // mapsOsmHelper.onResume();
        // lokacijaRealtimeHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Uvek upari sa onResume()
        // ziroskopHelper.onPause();
        // akcelerometarHelper.onPause();
        // mapsOsmHelper.onPause();
        // lokacijaRealtimeHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Oslobađanje resursa – audio, mreža, niti
        // audioRecorder.onDestroy();
        // mediaPlayerHelper.onDestroy();
        // okHttpHelper.shutdown();
        // threadExecutorHelper.shutdown();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Runtime dozvole – delegiraj helperima iz segment foldera
        // geoHelper.onPermissionGranted(requestCode, grantResults);       // 02-geo-lokacija
        // kameraHelper.onPermissionGranted(requestCode, grantResults);    // 03-kamera
        // kontaktiHelper.onPermissionGranted(requestCode, grantResults);  // 14-kontakti
        // galerijaHelper.onPermissionGranted(requestCode, grantResults);    // 76-galerija
        // koraciHelper.onPermissionGranted(requestCode, grantResults);      // 46-senzor-koraci
        // audioRecorder.onPermissionGranted(requestCode, grantResults);   // 83-audio-recorder
    }
}
