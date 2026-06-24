# Lokacija u realnom vremenu

**Dodatni segment.** **Alternativa** `getLastLocation()` iz zadatka 3.

**Cilj:** TextView se ažurira dok se uređaj kreće.

---

## Preduslovi

- `02-geo-lokacija/` – FusedLocationProviderClient već postoji
- Iste dozvole za lokaciju

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`LokacijaRealtimeHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | **`onResume`**: `lokacijaRealtimeHelper.onResume()` |
| 4 | `MainActivity.java` | **`onPause`**: `lokacijaRealtimeHelper.onPause()` |

---

## Kompletan kod – helper klasa

Kopiraj **`LokacijaRealtimeHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.LokacijaRealtimeHelper;
```

### Polje i lifecycle

```java
private LokacijaRealtimeHelper lokacijaRealtimeHelper;

// onCreate:
lokacijaRealtimeHelper = new LokacijaRealtimeHelper(this, textView);

// onResume:
lokacijaRealtimeHelper.onResume();

// onPause:
lokacijaRealtimeHelper.onPause();
```


---


## Razlika od zadatka 3

| getLastLocation | requestLocationUpdates |
|-----------------|------------------------|
| jednom pri startu | stalno osvežava |
| jednostavnije | više koda, troši bateriju |

> **Napomena:** Na kolokvijumu koristi ono što zadatak traži. Ovo je zamena ako profesor kaže „uživo".

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
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
```

## Checklist

- [ ] LocationCallback definisan
- [ ] requestLocationUpdates u onResume
- [ ] removeLocationUpdates u onPause
