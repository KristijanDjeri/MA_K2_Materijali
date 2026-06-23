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

> **Alternativa (stariji način):** metode direktno u `MainActivity` – vidi `GeoLokacijaSegment.java` (samo komentari).

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

## Kompletan kod za `MainActivity.java` (deo za geolokaciju – inline varijanta)

Ako ne koristiš helper, metode idu **na dno klase** (pre zatvarajuće `}`):

### 1. Importi (na vrh fajla, pored ostalih importa)

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
```

### 2. Konstanta i polja (unutar klase, iznad `onCreate`)

```java
private static final int REQ_LOCATION = 100;

private FusedLocationProviderClient fusedLocationClient;
// textView već imaš iz osnovnog projekta
```

### 3. U `onCreate`, posle `findViewById` linija

```java
fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
pokreniLokaciju();
```

### 4. Metode (dodaj na dno klase, pre zatvarajuće `}`)

```java
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
```

### 5. Obrada dozvole (dodaj metodu u klasu)

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQ_LOCATION
            && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        ucitajLokaciju();
    }
}
```

> **Napomena:** Kasnije ćeš u istu `onRequestPermissionsResult` dodavati i `REQ_CAMERA`, `REQ_CONTACTS` itd. – koristi `switch` ili više `if` grananja.

---

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

- [ ] Importi dodati
- [ ] `fusedLocationClient` inicijalizovan u `onCreate`
- [ ] `pokreniLokaciju()` pozvan u `onCreate`
- [ ] `onRequestPermissionsResult` postoji
- [ ] TextView prikazuje širinu i dužinu

---

## Sledeći korak

Folder **`03-kamera/`** za zadatak 4.
