# Priprema za Kolokvijum 2 – Mobilne Aplikacije

Materijal za kolokvijum: gotovi segmenti koda i uputstva po temama.  
**Android Studio**, **minSdk 28** (Android 9+).

**Organizacija foldera:** [KONVENCIJA-FOLDERA.md](KONVENCIJA-FOLDERA.md)  
**Grafičke komponente (UI):** [GRAFICKI-SEGMENTI.md](GRAFICKI-SEGMENTI.md) – **60–68**  
**Helper klase:** [HELPER-KLASE.md](HELPER-KLASE.md) – logika van `MainActivity` (copy-paste helper → tanak `MainActivity`)  
**Spajanje za ispit:** [16-spajanje-zadataka](16-spajanje-zadataka/)  
**Fragmenti na ispitu:** [90-fragments-prirucnik](90-fragments-prirucnik/)  
**Testiranje:** [IZVESTAJ-TESTIRANJA.md](IZVESTAJ-TESTIRANJA.md) · projekat `99-test-okruzenje/`

---

## Zvanični zadaci (01–16)

Segmenti su **razdvojeni** da svaki radi samostalno. PDF zadaci 5–9 imaju više foldera.

| Folder | Šta pokriva |
|--------|-------------|
| [01-osnovni-projekat](01-osnovni-projekat/) | Projekat, layout (zadaci 1–2) |
| [02-geo-lokacija](02-geo-lokacija/) | Širina/dužina (zadatak 3) |
| [03-kamera](03-kamera/) | Slikanje (zadatak 4) |
| [04-senzor-ziroskop](04-senzor-ziroskop/) | Žiroskop Toast (zadatak 4) |
| [05-room-baza](05-room-baza/) | Room model + DAO (zadatak 5) |
| [06-retrofit-get](06-retrofit-get/) | Retrofit GET test (zadatak 5) |
| [07-ucitaj-10-postova](07-ucitaj-10-postova/) | API → 10 u bazi (zadatak 6) |
| [08-toast-prvi-post](08-toast-prvi-post/) | Toast title prvog reda (zadatak 6) |
| [09-switch-listener](09-switch-listener/) | Switch ON/OFF spajanje (zadaci 6, 9) |
| [10-brisanje-prvog-posta](10-brisanje-prvog-posta/) | Brisanje prvog reda (zadatak 7) |
| [11-notifikacija-prazna-baza](11-notifikacija-prazna-baza/) | Notifikacija (zadatak 7) |
| [12-senzor-akcelerometar](12-senzor-akcelerometar/) | Akcelerometar (zadatak 8) |
| [13-shared-preferences](13-shared-preferences/) | SharedPreferences (zadatak 9) |
| [14-kontakti](14-kontakti/) | Kontakti (zadatak 9) |
| [15-main-activity-referenca](15-main-activity-referenca/) | Sve spojeno u jednom fajlu |
| [16-spajanje-zadataka](16-spajanje-zadataka/) | Kako spojiti segmente za ispit |

### Proširenje zvaničnih (17–19)

| Folder | Šta pokriva |
|--------|-------------|
| [17-alert-dialog](17-alert-dialog/) | AlertDialog – potvrda, poruke |
| [18-room-update](18-room-update/) | Room UPDATE |
| [19-fragment-primer](19-fragment-primer/) | Gotov Fragment primer |

---

## Grafičke / UI komponente (60–68)

Odvojeno od senzora i logike podataka.

| Folder | Šta pokriva |
|--------|-------------|
| [60-ui-recyclerview](60-ui-recyclerview/) | RecyclerView – lista postova |
| [61-ui-webview](61-ui-webview/) | WebView |
| [62-ui-edit-text-validacija](62-ui-edit-text-validacija/) | EditText + validacija |
| [63-ui-spinner](63-ui-spinner/) | Spinner |
| [64-ui-date-picker](64-ui-date-picker/) | Date/Time picker |
| [65-ui-progress-bar](65-ui-progress-bar/) | ProgressBar |
| [66-ui-checkbox-radiobutton](66-ui-checkbox-radiobutton/) | CheckBox, RadioButton |
| [67-ui-toolbar-options-menu](67-ui-toolbar-options-menu/) | Toolbar / Options Menu |
| [68-ui-media-player](68-ui-media-player/) | Media player – slika, video, audio |

Detalji: [GRAFICKI-SEGMENTI.md](GRAFICKI-SEGMENTI.md)

---

## Logika / podaci / platforma (70–79)

| Folder | Šta pokriva |
|--------|-------------|
| [70-intent-druga-aktivnost](70-intent-druga-aktivnost/) | Explicit Intent, druga aktivnost |
| [71-implicit-intent](71-implicit-intent/) | Share, SMS, email, URL |
| [72-retrofit-post](72-retrofit-post/) | Retrofit POST |
| [73-okhttp-json](73-okhttp-json/) | OkHttp JSON |
| [74-thread-executor](74-thread-executor/) | Pozadinska nit (Room) |
| [75-interni-fajl](75-interni-fajl/) | Interni fajl |
| [76-galerija](76-galerija/) | Galerija / izbor slike |
| [77-file-provider](77-file-provider/) | FileProvider + kamera u fajl |
| [78-lokacija-realtime](78-lokacija-realtime/) | Lokacija u realnom vremenu |
| [79-maps-google-osm](79-maps-google-osm/) | Google Maps / OpenStreetMap |

---

## Servisi / hardver (80–85)

| Folder | Šta pokriva |
|--------|-------------|
| [80-alarm-notifikacija](80-alarm-notifikacija/) | Alarm + notifikacija |
| [81-poziv-telefon](81-poziv-telefon/) | Poziv / dialer |
| [82-povratna-vibracija](82-povratna-vibracija/) | Vibracija (haptic) |
| [83-audio-recorder](83-audio-recorder/) | Audio snimanje |
| [84-content-provider](84-content-provider/) | ContentProvider – postovi iz Room |
| [85-broadcast-receiver](85-broadcast-receiver/) | BroadcastReceiver – custom broadcast |

---

## Senzori (41–49) · Notifikacije (37–40) · Firebase (50–54)

Puna tabela: **[KONVENCIJA-FOLDERA.md](KONVENCIJA-FOLDERA.md)**  
Verovatnoća tema: **[DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md)**

---

## Redosled učenja

1. `01-osnovni-projekat/` → projekat Kolokvijum2  
2. Zadaci `02`–`14` **pojedinačno** (svaki sa svojim test okidačem)  
3. `16-spajanje-zadataka/` → spoji kao na ispitu  
4. Uporedi sa `15-main-activity-referenca/`

---

## API (testirano)

Radni endpoint u `06-retrofit-get/`:

```
GET https://jsonplaceholder.typicode.com/posts
```

Alternativa (mock kolokvijum): `https://dummy-json.mock.beeceptor.com/posts`

URL iz PDF-a može vratiti HTML – proveri na početku ispita.

---

## Dozvole (Manifest)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```
