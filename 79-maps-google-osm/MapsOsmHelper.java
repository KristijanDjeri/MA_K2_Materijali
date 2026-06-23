package com.example.kolokvijum2.helper;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 58-maps-google-osm/ – OpenStreetMap (osmdroid) */
public class MapsOsmHelper {

    private final AppCompatActivity activity;
    private final MapView mapView;

    public MapsOsmHelper(AppCompatActivity activity, MapView mapView) {
        this.activity = activity;
        this.mapView = mapView;
        Configuration.getInstance().setUserAgentValue(activity.getPackageName());
        mapView.setMultiTouchControls(true);
    }

    public void prikaziTacku(double lat, double lon, String naslov) {
        mapView.getController().setZoom(15.0);
        GeoPoint tacka = new GeoPoint(lat, lon);
        mapView.getController().setCenter(tacka);

        Marker marker = new Marker(mapView);
        marker.setPosition(tacka);
        marker.setTitle(naslov);
        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }
}
