// OpenStreetMap – osmdroid (folder 58-maps-google-osm/)
// Gradle: implementation 'org.osmdroid:osmdroid-android:6.1.18'

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

private MapView mapView;

// onCreate, pre findViewById za mapu:
Configuration.getInstance().setUserAgentValue(getPackageName());

mapView = findViewById(R.id.mapView);
mapView.setMultiTouchControls(true);
mapView.getController().setZoom(15.0);

double lat = 44.7866;
double lon = 20.4489;
GeoPoint tacka = new GeoPoint(lat, lon);
mapView.getController().setCenter(tacka);

Marker marker = new Marker(mapView);
marker.setPosition(tacka);
marker.setTitle("Lokacija");
mapView.getOverlays().add(marker);
mapView.invalidate();

@Override
public void onResume() {
    super.onResume();
    if (mapView != null) mapView.onResume();
}

@Override
public void onPause() {
    super.onPause();
    if (mapView != null) mapView.onPause();
}
