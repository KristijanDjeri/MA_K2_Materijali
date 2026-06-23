package com.example.kolokvijum2.helper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 58-maps-google-osm/ – Google Maps */
public class MapsGoogleHelper implements OnMapReadyCallback {

    private final AppCompatActivity activity;
    private GoogleMap googleMap;
    private double lat = 44.7866;
    private double lon = 20.4489;
    private String naslov = "Lokacija";

    public MapsGoogleHelper(AppCompatActivity activity, int mapFragmentId) {
        this.activity = activity;
        SupportMapFragment mapFragment = (SupportMapFragment)
                activity.getSupportFragmentManager().findFragmentById(mapFragmentId);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    public void setKoordinate(double lat, double lon, String naslov) {
        this.lat = lat;
        this.lon = lon;
        this.naslov = naslov;
        if (googleMap != null) {
            prikaziNaMapi();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        prikaziNaMapi();
    }

    private void prikaziNaMapi() {
        LatLng tacka = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(tacka).title(naslov));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tacka, 14f));
    }
}
