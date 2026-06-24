# Geolokacija (zadatak 3)

**Cilj:** U `TextView` prikaži geografsku **širinu** i **dužinu** uređaja.

---

## Šta ti treba pre ovoga

- Završen folder `01-osnovni-projekat/`
- Dozvole `ACCESS_FINE_LOCATION` i `ACCESS_COARSE_LOCATION` u Manifest-u
- Gradle zavisnost `play-services-location`

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`GeoLokacijaHelper.java`** | **Novi fajl** → `app/.../helper/GeoLokacijaHelper.java` (kopiraj iz ovog foldera) |
| 2 | `MainActivity.java` | Polje: `private GeoLokacijaHelper geoHelper;` (iznad `onCreate`) |
| 3 | `MainActivity.java` | **`onCreate`**, posle `findViewById`: `geoHelper = new GeoLokacijaHelper(this, textView);` |
| 4 | `MainActivity.java` | **`onCreate`**: `geoHelper.pokreni();` |
| 5 | `MainActivity.java` | **`onRequestPermissionsResult`**: `geoHelper.onPermissionGranted(requestCode, grantResults);` |


---

## Kompletan kod – helper klasa

Kopiraj **`GeoLokacijaHelper.java`** iz ovog foldera u paket `helper`.

---

## MainActivity – samo povezivanje

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
geoHelper = new GeoLokacijaHelper(this, textView);
geoHelper.pokreni();
```

### U `onRequestPermissionsResult` (proširi postojeći, ne pravi novi od nule)

```java
geoHelper.onPermissionGranted(requestCode, grantResults);
```

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
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
```

## Kako testirati

1. Pokreni aplikaciju na emulatoru ili telefonu
2. Kad traži dozvolu za lokaciju → klikni **Allow**
3. U TextView treba da piše npr. `Širina: 44.81..., Dužina: 20.46...`

**Na emulatoru:** klikni `...` pored emulatora → **Location** → unesi koordinate → **Set Location**.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `getLastLocation()` – jednokratno | `requestLocationUpdates()` – lokacija uživo → pogledaj folder `78-lokacija-realtime/` |
| Samo `ACCESS_FINE_LOCATION` | Možeš tražiti i `ACCESS_COARSE_LOCATION` |
| Fused Location Provider | Stariji `LocationManager` – ne preporučujem, ali profesor može pomenuti |

---

## Checklist

- [ ] `GeoLokacijaHelper` u paketu `helper`
- [ ] `geoHelper = new GeoLokacijaHelper(this, textView)` u `onCreate`
- [ ] `geoHelper.pokreni()` pozvan u `onCreate`
- [ ] `geoHelper.onPermissionGranted(...)` u `onRequestPermissionsResult`
- [ ] TextView prikazuje širinu i dužinu

---

## Sledeći korak

Folder **`03-kamera/`** za zadatak 4.
