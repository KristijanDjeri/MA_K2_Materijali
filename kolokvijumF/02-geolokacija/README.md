# Geolokacija (zadatak 3) – vežba F

**Cilj:** U **prvom** TextView-u (`textViewLokacija`) prikaži geografsku **širinu** i **dužinu** uređaja.

---

## Šta ti treba pre ovoga

- Završen [01-osnovni-projekat/](../01-osnovni-projekat/)
- Dozvole za lokaciju u Manifest-u
- `play-services-location` u Gradle-u

---

## Koji fajlovi se dodaju / menjaju

| Korak | Fajl | Gde |
|-------|------|-----|
| 1 | `GeoLokacijaHelper.java` | Novi → `app/.../helper/` (kopiraj iz ovog foldera) |
| 2 | `MainActivity.java` | Polje + init u `onCreate` |
| 3 | `MainActivity.java` | `onRequestPermissionsResult` |

---

## MainActivity – povezivanje

### Import

```java
import com.example.kolokvijum2.helper.GeoLokacijaHelper;
```

### Polje

```java
private GeoLokacijaHelper geoHelper;
```

### U `onCreate`, posle `findViewById`

```java
geoHelper = new GeoLokacijaHelper(this, textViewLokacija);
geoHelper.pokreni();
```

### U `onRequestPermissionsResult`

```java
geoHelper.onPermissionGranted(requestCode, grantResults);
```

---

## Kompletan kod – helper

Kopiraj **`GeoLokacijaHelper.java`** iz ovog foldera.

Ista logika kao kod geolokacije u drugim segmentima – ovde je TextView `textViewLokacija`.

---

## Alternativa: inline u MainActivity

```java
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

private static final int REQ_LOCATION = 100;
private FusedLocationProviderClient fusedLocationClient;

// onCreate:
fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
pokreniGeolokaciju();

private void pokreniGeolokaciju() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
        return;
    }
    fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
        if (location != null) {
            textViewLokacija.setText("Širina: " + location.getLatitude()
                    + ", Dužina: " + location.getLongitude());
        } else {
            textViewLokacija.setText("Lokacija nije dostupna");
        }
    });
}

// onRequestPermissionsResult:
if (requestCode == REQ_LOCATION && grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    pokreniGeolokaciju();
}
```

---

## Kako testirati

1. Pokreni app → dozvoli lokaciju
2. U `textViewLokacija` treba: `Širina: 44.81..., Dužina: 20.46...`
3. **Emulator:** `...` → **Location** → unesi koordinate → **Set Location**

---

## Checklist

- [ ] `GeoLokacijaHelper` u paketu `helper`
- [ ] `textViewLokacija` prikazuje širinu i dužinu
- [ ] Runtime dozvola radi

---

## Sledeći korak

[03-audio-snimanje/](../03-audio-snimanje/) – dugme Snimi + prvi CheckBox.
