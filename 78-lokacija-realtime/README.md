# Lokacija u realnom vremenu

**Dodatni segment.** **Alternativa** `getLastLocation()` iz zadatka 3.

**Cilj:** TextView se ažurira dok se uređaj kreće.

---

## Preduslovi

- `02-geo-lokacija/` – FusedLocationProviderClient već postoji
- Iste dozvole za lokaciju

---

## Kompletan kod za `MainActivity.java`

### Importi

```java
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;
import android.location.Location;
```

### Polje

```java
private LocationCallback locationCallback;
```

### U `onCreate` (posle fusedLocationClient)

```java
locationCallback = new LocationCallback() {
    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) return;
        Location loc = locationResult.getLastLocation();
        if (loc != null) {
            textView.setText("Širina: " + loc.getLatitude()
                    + ", Dužina: " + loc.getLongitude());
        }
    }
};
```

### Pokretanje u `onResume` (posle dozvole)

```java
private void pokreniRealtimeLokaciju() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    LocationRequest request = new LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000)
            .setMinUpdateIntervalMillis(2000)
            .build();
    fusedLocationClient.requestLocationUpdates(request, locationCallback, getMainLooper());
}
```

Pozovi `pokreniRealtimeLokaciju()` u `onResume` umesto jednokratnog `ucitajLokaciju()`.

### Zaustavljanje u `onPause`

```java
@Override
protected void onPause() {
    super.onPause();
    fusedLocationClient.removeLocationUpdates(locationCallback);
    sensorManager.unregisterListener(this);
}
```

---

## Razlika od zadatka 3

| getLastLocation | requestLocationUpdates |
|-----------------|------------------------|
| jednom pri startu | stalno osvežava |
| jednostavnije | više koda, troši bateriju |

> **Napomena:** Na kolokvijumu koristi ono što zadatak traži. Ovo je zamena ako profesor kaže „uživo".

---

## Checklist

- [ ] LocationCallback definisan
- [ ] requestLocationUpdates u onResume
- [ ] removeLocationUpdates u onPause
