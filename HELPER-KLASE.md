# Helper klase – mapa segmenata

Sve helper klase idu u paket **`com.example.kolokvijum2.helper`**.

Konvencija: `01-osnovni-projekat/helper-paket-README-snippet.md`

---

## Pravilo lifecycle-a

Helper koji koristi senzor, mapu, lokaciju u realnom vremenu ili audio **mora** imati `onResume()` / `onPause()` (i ponekad `onDestroy()`).

**MainActivity nikad ne implementira `SensorEventListener`** – samo poziva:

```java
@Override protected void onResume() {
    super.onResume();
    ziroskopHelper.onResume();
    akcelerometarHelper.onResume();
    magnetometarHelper.onResume(); // ako koristiš
}

@Override protected void onPause() {
    super.onPause();
    ziroskopHelper.onPause();
    akcelerometarHelper.onPause();
    magnetometarHelper.onPause();
}
```

---

## Kompletna tabela helper klasa

| Segment | Helper | Lifecycle metode |
|---------|--------|------------------|
| 02 geo | `GeoLokacijaHelper` | — (+ `onPermissionGranted`) |
| 03 kamera | `KameraHelper` | — (+ permission) |
| 04 žiroskop | `ZiroskopHelper` | `onResume`, `onPause` |
| 07–18 postovi | `PostRepository` | — |
| 09 switch | `SwitchPostsHelper` | listener u konstruktoru |
| 11 notifikacija | `NotifikacijaHelper` | statičke metode |
| 12 akcelerometar | `AkcelerometarHelper` | `onResume`, `onPause` |
| 13 prefs | `SharedPreferencesHelper` | — |
| 14 kontakti | `KontaktiHelper` | — (+ permission) |
| 17 alert | `AlertDialogHelper` | statičke metode |
| 21 galerija | `GalerijaHelper` | — (+ permission) |
| 24 webview | `WebViewHelper` | — |
| 26 interni fajl | `InterniFajlHelper` | statičke metode |
| 28 alarm | `AlarmHelper` | — |
| 29 poziv | `PozivHelper` | statičke metode |
| 30 spinner | `SpinnerHelper` | — (listener u konstruktoru) |
| 31 date picker | `DatePickerHelper` | — |
| 32 okhttp | `OkHttpHelper` | `shutdown()` u `onDestroy` |
| 33 vibracija | `VibracijaHelper` | statičke metode |
| 34 lokacija RT | `LokacijaRealtimeHelper` | `onResume`, `onPause` |
| 36 audio | `AudioRecorder` | `onPause`, `onDestroy`, permission |
| 42 magnetometar | `MagnetometarHelper` | `onResume`, `onPause` |
| 42 kompas | `KompasHelper` | `onResume`, `onPause` |
| 43 svetlosti | `SvetlostiHelper` | `onResume`, `onPause` |
| 44 proksimitet | `ProksimitetHelper` | `onResume`, `onPause` |
| 45 barometar | `BarometarHelper` | `onResume`, `onPause` |
| 46 koraci | `KoraciHelper` | `onResume`, `onPause`, permission |
| 47 izvedeni | `IzvedeniSenzorHelper` | `onResume`, `onPause` |
| 48 vlaga/temp | `VlagaTemperaturaHelper` | `onResume`, `onPause` |
| 49 shake | `ShakeHelper` + `ShakeDetector` | `onResume`, `onPause` |
| 55 checkbox/radio | `CheckBoxRadioHelper` | — |
| 56 implicit | `ImplicitIntentHelper` | statičke metode |
| 58 OSM mapa | `MapsOsmHelper` | `onResume`, `onPause` |
| 58 Google mapa | `MapsGoogleHelper` | — (callback `onMapReady`) |
| 59 file provider | `FileProviderHelper` | — |

Zasebne klase (ne helper): `PostAdapter`, `DetailActivity`, `AlarmReceiver`, `HomeFragment`, Firebase servisi.

---

## Primer MainActivity (magnetometar + zvanični zadaci)

```java
public class MainActivity extends AppCompatActivity {

    private ZiroskopHelper ziroskopHelper;
    private AkcelerometarHelper akcelerometarHelper;
    private MagnetometarHelper magnetometarHelper; // dodatni segment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        ziroskopHelper = new ZiroskopHelper(this);
        akcelerometarHelper = new AkcelerometarHelper(this, button);
        magnetometarHelper = new MagnetometarHelper(this, textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ziroskopHelper.onResume();
        akcelerometarHelper.onResume();
        magnetometarHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ziroskopHelper.onPause();
        akcelerometarHelper.onPause();
        magnetometarHelper.onPause();
    }
}
```

**Referenca zvaničnih zadataka:** `15-main-activity-referenca/MainActivity.java`

Stari `*Segment.java` fajlovi su zastareli – koristi helper `.java` fajlove iz foldera segmenta.
