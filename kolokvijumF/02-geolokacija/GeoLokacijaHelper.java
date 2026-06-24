package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/** vežba F – folder 02-geolokacija/ */
public class GeoLokacijaHelper {

    public static final int REQ_LOCATION = 100;

    private final AppCompatActivity activity;
    private final TextView textView;
    private final FusedLocationProviderClient fusedLocationClient;

    public GeoLokacijaHelper(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public void pokreni() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
            return;
        }
        ucitaj();
    }

    public void ucitaj() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, location -> {
                    if (location != null) {
                        textView.setText("Širina: " + location.getLatitude()
                                + ", Dužina: " + location.getLongitude());
                    } else {
                        textView.setText("Lokacija nije dostupna");
                    }
                });
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_LOCATION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ucitaj();
        }
    }
}
