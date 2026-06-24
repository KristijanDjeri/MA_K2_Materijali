# Helper klase – mapa segmenata

Sve helper klase idu u paket **`com.example.kolokvijum2.helper`**.

Konvencija: `01-osnovni-projekat/helper-paket-README-snippet.md`

**Dokumentacija:** u svakom segment README prvo ide **helper pristup** (`MainActivity` samo povezuje helper), a zatim sekcija **„Alternativa: inline implementacija u MainActivity“** sa kompletnim kodom za slučaj da helper ne radi. Izvorni snippeti su i u `*Segment.java` fajlovima u istom folderu.

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
| 06 retrofit GET test | `RetrofitGetHelper` | statičke metode |
| 07–18 postovi | `PostRepository` | — |
| 09 switch | `SwitchPostsHelper` | listener u konstruktoru |
| 11 notifikacija | `NotifikacijaHelper` | statičke metode |
| 38–40 notifikacije | `NotifikacijaHelper`, `NotifikacijaAkcijeHelper`, `NotifikacijaProsirenaHelper` | statičke |
| **60** recycler | `RecyclerViewPostsHelper` | `osvezi()` posle insert/delete |
| **62** edit validacija | `EditTextValidacijaHelper` | — |
| **70** detail intent | `DetailIntentHelper` | statičke metode |
| **72** retrofit POST | `PostRepository.posaljiPostNaServer()` | — |
| **74** thread | `ThreadExecutorHelper` | `shutdown()` u `onDestroy` |
| **85** broadcast | `BroadcastHelper` | statičke metode |
| **52** firebase auth | `FirebaseAuthHelper` | listeneri u konstruktoru |
| **53** firestore | `FirestorePostsHelper` | — |
| 12 akcelerometar | `AkcelerometarHelper` | `onResume`, `onPause` |
| 13 prefs | `SharedPreferencesHelper` | — |
| 14 kontakti | `KontaktiHelper` | — (+ permission) |
| 17 alert | `AlertDialogHelper` | statičke metode |
| **61** webview | `WebViewHelper` | — |
| **63** spinner | `SpinnerHelper` | — (listener u konstruktoru) |
| **64** date picker | `DatePickerHelper` | — |
| **66** checkbox/radio | `CheckBoxRadioHelper` | — |
| **68** media player | `MediaPlayerHelper` | `onPause`, `onDestroy` |
| **75** interni fajl | `InterniFajlHelper` | statičke metode |
| **76** galerija | `GalerijaHelper` | — (+ permission) |
| **73** okhttp | `OkHttpHelper` | `shutdown()` u `onDestroy` |
| **78** lokacija RT | `LokacijaRealtimeHelper` | `onResume`, `onPause` |
| **79** OSM mapa | `MapsOsmHelper` | `onResume`, `onPause` |
| **79** Google mapa | `MapsGoogleHelper` | — (callback `onMapReady`) |
| **77** file provider | `FileProviderHelper` | — |
| **71** implicit | `ImplicitIntentHelper` | statičke metode |
| **80** alarm | `AlarmHelper` | — |
| **81** poziv | `PozivHelper` | statičke metode |
| **82** vibracija | `VibracijaHelper` | statičke metode |
| **83** audio | `AudioRecorder` | `onPause`, `onDestroy`, permission |
| **84** content provider | `ContentProviderHelper` | statičke metode |
| 42 magnetometar | `MagnetometarHelper` | `onResume`, `onPause` |
| 42 kompas | `KompasHelper` | `onResume`, `onPause` |
| 43 svetlosti | `SvetlostiHelper` | `onResume`, `onPause` |
| 44 proksimitet | `ProksimitetHelper` | `onResume`, `onPause` |
| 45 barometar | `BarometarHelper` | `onResume`, `onPause` |
| 46 koraci | `KoraciHelper` | `onResume`, `onPause`, permission |
| 47 izvedeni | `IzvedeniSenzorHelper` | `onResume`, `onPause` |
| 48 vlaga/temp | `VlagaTemperaturaHelper` | `onResume`, `onPause` |
| 49 shake | `ShakeHelper` + `ShakeDetector` | `onResume`, `onPause` |

Zasebne klase (ne helper): `PostAdapter`, `DetailActivity`, `AlarmReceiver`, `PostContentProvider`, `PostUpdateReceiver`, `HomeFragment`, Firebase servisi.

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

Stari `*Segment.java` fajlovi služe kao **inline alternativa** – u README-u je uvek prvo **helper** pristup (vidi `02-geo-lokacija/` kao obrazac).
