package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

/** Folder: 34-lokacija-realtime/ */
public class LokacijaRealtimeHelper {

    private final AppCompatActivity activity;
    private final TextView textView;
    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationCallback locationCallback;
    private boolean aktivan = false;

    public LokacijaRealtimeHelper(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Location loc = locationResult.getLastLocation();
                if (loc != null) {
                    textView.setText("Širina: " + loc.getLatitude()
                            + ", Dužina: " + loc.getLongitude());
                }
            }
        };
    }

    public void onResume() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationRequest request = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 3000)
                .setMinUpdateIntervalMillis(2000)
                .build();
        fusedLocationClient.requestLocationUpdates(request, locationCallback, activity.getMainLooper());
        aktivan = true;
    }

    public void onPause() {
        if (aktivan) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
            aktivan = false;
        }
    }
}
