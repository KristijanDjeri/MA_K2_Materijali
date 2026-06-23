// === DODAJ U MainActivity.java ===

// IMPORTI:
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;

// POLJE:
private LocationCallback locationCallback;

// U onCreate() posle fusedLocationClient:
locationCallback = new LocationCallback() {
    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) return;
        Location loc = locationResult.getLastLocation();
        if (loc != null) {
            textView.setText("Širina: " + loc.getLatitude() + ", Dužina: " + loc.getLongitude());
        }
    }
};

// U onResume() (posle dozvole):
private void pokreniRealtimeLokaciju() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) return;

    LocationRequest request = new LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000)
            .setMinUpdateIntervalMillis(2000)
            .build();

    fusedLocationClient.requestLocationUpdates(request, locationCallback, getMainLooper());
}

// U onPause():
// fusedLocationClient.removeLocationUpdates(locationCallback);
