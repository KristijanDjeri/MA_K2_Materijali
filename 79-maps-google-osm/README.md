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

> **Alternativa:** inline osmdroid ispod.

## `MainActivity.java` – OSM (inline)

```java
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

// onCreate, pre setContentView:
Configuration.getInstance().setUserAgentValue(getPackageName());

MapView mapView = findViewById(R.id.mapView);
mapView.setMultiTouchControls(true);
mapView.getController().setZoom(15.0);

double lat = 44.7866;  // Beograd – ili iz Fused Location
double lon = 20.4489;
GeoPoint tačka = new GeoPoint(lat, lon);
mapView.getController().setCenter(tačka);

Marker marker = new Marker(mapView);
marker.setPosition(tačka);
marker.setTitle("Moja lokacija");
mapView.getOverlays().add(marker);
mapView.invalidate();
```

## Lifecycle (obavezno za osmdroid)

```java
@Override
public void onResume() {
    super.onResume();
    MapView mapView = findViewById(R.id.mapView);
    if (mapView != null) mapView.onResume();
}

@Override
public void onPause() {
    super.onPause();
    MapView mapView = findViewById(R.id.mapView);
    if (mapView != null) mapView.onPause();
}
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

## `MainActivity.java` – Google Maps (inline)

```java
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng beograd = new LatLng(44.7866, 20.4489);
        googleMap.addMarker(new MarkerOptions().position(beograd).title("Beograd"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beograd, 14f));
    }
}
```

---

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
