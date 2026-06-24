// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

// POLJA:
private static final int REQ_LOCATION = 100;
private TextView textView;
private FusedLocationProviderClient fusedLocationClient;

// U onCreate(), posle findViewById:
fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
pokreniGeolokaciju();

// METODE:

private void pokreniGeolokaciju() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
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
                    textView.setText("Širina: " + location.getLatitude()
                            + ", Dužina: " + location.getLongitude());
                } else {
                    textView.setText("Lokacija nije dostupna");
                }
            });
}

// U onRequestPermissionsResult dodaj:
// if (requestCode == REQ_LOCATION
//         && grantResults.length > 0
//         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//     ucitajLokaciju();
// }
