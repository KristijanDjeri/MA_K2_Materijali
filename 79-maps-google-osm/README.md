# Google Maps i OpenStreetMap (OSM)

**Dodatni segment.** **Slično:** geolokacija (`02-geo-lokacija/`) – prikaz pozicije, ali na **mapi** u aplikaciji.

**Cilj:** Prikaži tačku na mapi (lat/long iz Fused Location ili fiksne koordinate).

---

## Koja varijanta?

| | **OpenStreetMap (OSM)** | **Google Maps** |
|---|-------------------------|-----------------|
| API ključ | **Ne treba** | Obavezan (Google Cloud Console) |
| Gradle | `osmdroid-android` | `play-services-maps` |
| Na kolokvijumu | Brže za setup | Ako profesor da ključ |

**Preporuka za vežbu:** OSM (`MapsOsmSegment.java`).  
**Ako traže Google:** `MapsGoogleSegment.java`.

Gradle zavisnosti: `gradle-maps.txt` u ovom folderu.

---

# Varijanta A – OpenStreetMap (osmdroid)

## Gradle

```gradle
implementation 'org.osmdroid:osmdroid-android:6.1.18'
```

Sync.

## Manifest

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Layout – `activity_main.xml` (deо)

```xml
<org.osmdroid.views.MapView
    android:id="@+id/mapView"
    android:layout_width="match_parent"
    android:layout_height="300dp"
    android:layout_marginTop="8dp" />
```

## `MainActivity.java` – OSM (preporučeno: helper)

### Kompletan kod – helper klasa

Kopiraj **`MapsOsmHelper.java`** u `app/.../helper/`.

```java
import com.example.kolokvijum2.helper.MapsOsmHelper;
import org.osmdroid.views.MapView;

private MapsOsmHelper mapsOsmHelper;

// onCreate:
MapView mapView = findViewById(R.id.mapView);
mapsOsmHelper = new MapsOsmHelper(this, mapView);
mapsOsmHelper.prikaziTacku(44.7866, 20.4489, "Moja lokacija");

// onResume / onPause:
mapsOsmHelper.onResume();
mapsOsmHelper.onPause();
```

> Za stari inline primer pogledaj `MapsOsmSegment.java` / `MapsGoogleSegment.java`.

## Lifecycle (preko helpera)

```java
// onResume / onPause:
mapsOsmHelper.onResume();
mapsOsmHelper.onPause();
```

---

# Varijanta B – Google Maps

## Gradle

```gradle
implementation 'com.google.android.gms:play-services-maps:18.2.0'
```

## API ključ

1. [Google Cloud Console](https://console.cloud.google.com/) → Maps SDK for Android → API key  
2. U `AndroidManifest.xml`, unutar `<application>`:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TVOJ_API_KLJUC" />
```

## Layout – `res/layout/map_fragment.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/mapFragment"
    android:name="com.google.android.gms.maps.SupportMapFragment"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

U `activity_main.xml` uključi `<include layout="@layout/map_fragment" />` ili stavi fragment direktno.

## `MainActivity.java` – Google Maps (preporučeno: helper)

### Kompletan kod – helper klasa

Kopiraj **`MapsGoogleHelper.java`** u `app/.../helper/`.

```java
import com.example.kolokvijum2.helper.MapsGoogleHelper;

private MapsGoogleHelper mapsGoogleHelper;

// onCreate:
mapsGoogleHelper = new MapsGoogleHelper(this, R.id.mapFragment);
mapsGoogleHelper.setKoordinate(44.7866, 20.4489, "Beograd");
```

> **Ne** moraš `implements OnMapReadyCallback` u MainActivity – helper to radi umesto tebe.

## Povezivanje sa geolokacijom (zadatak 3)

Kad `02-geo-lokacija/` vrati lat/long, koristi te vrednosti umesto fiksnih koordinata za marker.

---

## Implicit Intent – spoljašnja mapa

Bez ugrađene mape: `71-implicit-intent/` → `geo:lat,lon`.

---

## Checklist

- [ ] OSM: `setUserAgentValue`, `onResume`/`onPause` na MapView
- [ ] Google: API ključ u Manifest-u
- [ ] Marker na tačnim koordinatama
- [ ] Zoom i centar postavljeni

---

## Povezano

- Lat/long: `02-geo-lokacija/`
- Realtime lokacija: `78-lokacija-realtime/`
- Spoljašnja mapa: `71-implicit-intent/`
