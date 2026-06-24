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

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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
