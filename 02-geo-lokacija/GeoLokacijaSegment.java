// === DODAJ U MainActivity.java ===

// IMPORTI (na vrh fajla):
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

// POLJA U KLASI:
private static final int REQ_LOCATION = 100;
private FusedLocationProviderClient fusedLocationClient;
private TextView textView; // već imaš iz onCreate

// U onCreate(), posle findViewById:
fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
pokreniLokaciju();

// METODE:

private void pokreniLokaciju() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_LOCATION);
        return;
    }
    ucitajLokaciju();
}

private void ucitajLokaciju() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, location -> {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    textView.setText("Širina: " + lat + ", Dužina: " + lon);
                } else {
                    textView.setText("Lokacija nije dostupna");
                }
            });
}

@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQ_LOCATION
            && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        ucitajLokaciju();
    }
}
