# Helper klase – mapa segmenata

Sve helper klase idu u paket **`com.example.kolokvijum2.helper`** u Android Studio projektu.

Konvencija lepljenja: vidi `01-osnovni-projekat/helper-paket-README-snippet.md`.

---

## Zvanični zadaci (01–16)

| Segment | Helper klasa | Fajl u materijalu | MainActivity – šta ostaje |
|---------|--------------|-------------------|---------------------------|
| 02 geo | `GeoLokacijaHelper` | `02-geo-lokacija/GeoLokacijaHelper.java` | polje, `onCreate` init, `onRequestPermissionsResult` |
| 03 kamera | `KameraHelper` | `03-kamera/KameraHelper.java` | polje, init u `onCreate`, listener na `imageButton` |
| 04 žiroskop | `ZiroskopHelper` | `04-senzor-ziroskop/ZiroskopHelper.java` | polje, `onResume`/`onPause`, callback iz kamere |
| 07–08–10 postovi | `PostRepository` | `07-ucitaj-10-postova/PostRepository.java` | Switch ON, brisanje, Toast |
| 11 notifikacija | `NotifikacijaHelper` | `11-notifikacija-prazna-baza/NotifikacijaHelper.java` | `kreirajKanal` u `onCreate`, poziv posle brisanja |
| 12 akcelerometar | `AkcelerometarHelper` | `12-senzor-akcelerometar/AkcelerometarHelper.java` | polje, `onResume`/`onPause` |
| 13 prefs | `SharedPreferencesHelper` | `13-shared-preferences/SharedPreferencesHelper.java` | polje, poziv u `obradiSwitchOff` |
| 14 kontakti | `KontaktiHelper` | `14-kontakti/KontaktiHelper.java` | polje, `onRequestPermissionsResult` |
| 17 alert | `AlertDialogHelper` | `17-alert-dialog/AlertDialogHelper.java` | statički poziv iz listenera |
| 18 update | `PostRepository` | (isti) | `postRepository.izmeniTitlePrvogPosta(...)` |

**Referenca (sve spojeno):** `15-main-activity-referenca/MainActivity.java` – tanak `MainActivity` + helperi.

---

## Dodatni segmenti (20–59)

| Segment | Helper klasa | Napomena |
|---------|--------------|----------|
| 36 audio | `AudioRecorder` | `onPause`/`onDestroy` u Activity |
| 48/49 shake | `ShakeDetector` | već zasebna klasa |
| 56 implicit | `ImplicitIntentHelper` | statičke metode |
| 59 file provider | `FileProviderHelper` | init u `onCreate`, kamera + share |
| 28 alarm | `AlarmReceiver` | BroadcastReceiver – zaseban fajl |
| 20 lista | `PostAdapter` | adapter – zaseban fajl |
| 22 intent | `DetailActivity` | druga aktivnost |

Segmenti bez helpera (samo UI u Activity): `55-checkbox-radiobutton`, `57-toolbar-options-menu` (meni mora u Activity), `30-spinner`.

---

## Primer – MainActivity sa helperima

```java
private GeoLokacijaHelper geoHelper;

@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    geoHelper = new GeoLokacijaHelper(this, textView);
    geoHelper.pokreni();
}

@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    geoHelper.onPermissionGranted(requestCode, grantResults);
}
```

Stari `*Segment.java` fajlovi ostaju kao skraćeni primeri; **preporuka: koristi helper klasu**.
