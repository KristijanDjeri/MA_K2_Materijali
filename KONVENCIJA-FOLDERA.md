# Konvencija naziva foldera

Svi segmenti imaju prefiks **`NN-`** (dvocifrena enumeracija) radi reda na disku i u štampanom materijalu.

## Pravila imenovanja

| Pravilo | Primer |
|---------|--------|
| Prefiks broja + crtica | `01-osnovni-projekat` |
| **Kod** – bez broja foldera | `@+id/spinner`, `R.array.spinner_opcije` (ne `30-spinner`) |
| **UI komponente** – prefiks `ui-` | `63-ui-spinner`, `66-ui-checkbox-radiobutton` |
| **Senzori** – prefiks `senzor-` | `04-senzor-ziroskop`, `43-senzor-svetlosti` |
| **Notifikacije** | `37-notifikacije-pregled` … `40-notifikacija-prosirena` |
| **Firebase** | `50-firebase` … `54-firebase-fcm` |
| Test projekat | `99-test-okruzenje` |
| Priručnici | `90-…` |

**Ne koristimo** kolokvijalne ili dvosmislene nazive (npr. stari `vibrator` → sada **`82-povratna-vibracija`**).

Broj foldera (`NN-`) **ne prenosi se u kod** – varijable, `@+id/`, `R.array.*` i importi koriste semantička imena (`spinner`, `recyclerview`), ne `30-spinner` ili `androidx.20-recyclerview`.

---

## Grupe po nameni (brzi pregled)

| Opseg | Grupa | Šta sadrži |
|-------|--------|------------|
| **01–16** | Zvanični zadaci (PDF) | Layout, geo, kamera, senzori u zadatku, Room, API, Switch… |
| **17–19** | Proširenje zvaničnih | AlertDialog, Room UPDATE, Fragment |
| **60–67** | **Grafičke / UI komponente** | RecyclerView, WebView, EditText, Spinner, meni, ProgressBar… |
| **70–79** | **Logika / podaci / platforma** | Intent, Retrofit POST, OkHttp, nit, fajl, galerija, mape, lokacija… |
| **80–85** | **Servisi / hardver (ne senzor)** | Alarm, poziv, vibracija, audio, ContentProvider, BroadcastReceiver… |
| **37–40** | Notifikacije | Osnovna, akcije, proširena |
| **41–49** | **Senzori** (dodatni) | Magnetometar, svetlost, shake… |
| **50–54** | Firebase | Auth, Firestore, FCM |
| **90, 99** | Priručnici / test | Fragmenti, build test |

Detaljnije: [GRAFICKI-SEGMENTI.md](GRAFICKI-SEGMENTI.md) · [DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md)

---

## Enumeracija – kompletna tabela

### 01–16 Zvanični zadaci (PDF)

Spajanje nezavisnih koraka: [16-spajanje-zadataka](16-spajanje-zadataka/).

| Folder | Naziv | Zadatak |
|--------|-------|---------|
| [01-osnovni-projekat](01-osnovni-projekat/) | Osnovni projekat | 1–2 |
| [02-geo-lokacija](02-geo-lokacija/) | Geolokacija | 3 |
| [03-kamera](03-kamera/) | Kamera | 4 |
| [04-senzor-ziroskop](04-senzor-ziroskop/) | Senzor – žiroskop | 4 |
| [05-room-baza](05-room-baza/) | Room model + DAO | 5 |
| [06-retrofit-get](06-retrofit-get/) | Retrofit GET | 5 |
| [07-ucitaj-10-postova](07-ucitaj-10-postova/) | API → 10 postova | 6 |
| [08-toast-prvi-post](08-toast-prvi-post/) | Toast prvog posta | 6 |
| [09-switch-listener](09-switch-listener/) | Switch listener | 6, 9 |
| [10-brisanje-prvog-posta](10-brisanje-prvog-posta/) | Brisanje prvog reda | 7 |
| [11-notifikacija-prazna-baza](11-notifikacija-prazna-baza/) | Notifikacija | 7 |
| [12-senzor-akcelerometar](12-senzor-akcelerometar/) | Senzor – akcelerometar | 8 |
| [13-shared-preferences](13-shared-preferences/) | SharedPreferences | 9 |
| [14-kontakti](14-kontakti/) | Kontakti | 9 |
| [15-main-activity-referenca](15-main-activity-referenca/) | MainActivity referenca | sve |
| [16-spajanje-zadataka](16-spajanje-zadataka/) | Kako spojiti segmente | — |

### 17–19 Proširenje zvaničnih tema

| Folder | Naziv |
|--------|-------|
| [17-alert-dialog](17-alert-dialog/) | AlertDialog (UI dijalog) |
| [18-room-update](18-room-update/) | Room UPDATE |
| [19-fragment-primer](19-fragment-primer/) | Fragment – gotov primer |

### 60–67 Grafičke / UI komponente

| Folder | Naziv |
|--------|-------|
| [60-ui-recyclerview](60-ui-recyclerview/) | RecyclerView – lista |
| [61-ui-webview](61-ui-webview/) | WebView |
| [62-ui-edit-text-validacija](62-ui-edit-text-validacija/) | EditText validacija |
| [63-ui-spinner](63-ui-spinner/) | Spinner |
| [64-ui-date-picker](64-ui-date-picker/) | Date/Time picker |
| [65-ui-progress-bar](65-ui-progress-bar/) | ProgressBar |
| [66-ui-checkbox-radiobutton](66-ui-checkbox-radiobutton/) | CheckBox i RadioButton |
| [67-ui-toolbar-options-menu](67-ui-toolbar-options-menu/) | Toolbar / Options Menu |

### 70–79 Logika / podaci / platforma

| Folder | Naziv |
|--------|-------|
| [70-intent-druga-aktivnost](70-intent-druga-aktivnost/) | Druga aktivnost (explicit Intent) |
| [71-implicit-intent](71-implicit-intent/) | Implicit Intent (share, SMS, URL…) |
| [72-retrofit-post](72-retrofit-post/) | Retrofit POST |
| [73-okhttp-json](73-okhttp-json/) | OkHttp JSON |
| [74-thread-executor](74-thread-executor/) | Pozadinska nit |
| [75-interni-fajl](75-interni-fajl/) | Interni fajl |
| [76-galerija](76-galerija/) | Galerija / izbor slike |
| [77-file-provider](77-file-provider/) | FileProvider + kamera u fajl |
| [78-lokacija-realtime](78-lokacija-realtime/) | Lokacija u realnom vremenu |
| [79-maps-google-osm](79-maps-google-osm/) | Google Maps / OpenStreetMap |

### 80–85 Servisi i hardver (ne UI, ne senzor)

| Folder | Naziv |
|--------|-------|
| [80-alarm-notifikacija](80-alarm-notifikacija/) | Alarm + notifikacija |
| [81-poziv-telefon](81-poziv-telefon/) | Poziv / dialer |
| [82-povratna-vibracija](82-povratna-vibracija/) | Vibracija (haptic) |
| [83-audio-recorder](83-audio-recorder/) | Audio snimanje |
| [84-content-provider](84-content-provider/) | ContentProvider – sopstveni provider |
| [85-broadcast-receiver](85-broadcast-receiver/) | BroadcastReceiver – custom broadcast |

### 37–40 Notifikacije

| Folder | Naziv |
|--------|-------|
| [37-notifikacije-pregled](37-notifikacije-pregled/) | Pregled notifikacija |
| [38-notifikacija-osnovna](38-notifikacija-osnovna/) | Osnovna notifikacija |
| [39-notifikacija-akcije](39-notifikacija-akcije/) | Notifikacija sa dugmadima |
| [40-notifikacija-prosirena](40-notifikacija-prosirena/) | BigText, slika, progress |

### 41–49 Senzori (dodatni)

| Folder | Naziv |
|--------|-------|
| [41-senzori-pregled](41-senzori-pregled/) | Pregled svih senzora |
| [42-senzor-magnetometar](42-senzor-magnetometar/) | Magnetometar / kompas |
| [43-senzor-svetlosti](43-senzor-svetlosti/) | Senzor svetlosti |
| [44-senzor-proksimiteta](44-senzor-proksimiteta/) | Proksimitet |
| [45-senzor-barometar](45-senzor-barometar/) | Barometar |
| [46-senzor-koraci](46-senzor-koraci/) | Brojač koraka |
| [47-senzor-izvedeni](47-senzor-izvedeni/) | Izvedeni senzori |
| [48-senzor-vlage-temperature](48-senzor-vlage-temperature/) | Vlaga / temperatura |
| [49-senzor-shake](49-senzor-shake/) | Shake (akcelerometar) |

### 50–54 Firebase

| Folder | Naziv |
|--------|-------|
| [50-firebase](50-firebase/) | Firebase pregled |
| [51-firebase-setup](51-firebase-setup/) | Firebase setup |
| [52-firebase-auth](52-firebase-auth/) | Firebase Auth |
| [53-firebase-firestore](53-firebase-firestore/) | Firestore |
| [54-firebase-fcm](54-firebase-fcm/) | FCM notifikacije |

### 90–99 Posebno

| Folder | Naziv |
|--------|-------|
| [90-fragments-prirucnik](90-fragments-prirucnik/) | Priručnik – Fragmenti |
| [99-test-okruzenje](99-test-okruzenje/) | Test okruženje (build) |

---

## Koreni dokumenti

| Fajl | Svrha |
|------|-------|
| [README.md](README.md) | Glavni ulaz |
| [GRAFICKI-SEGMENTI.md](GRAFICKI-SEGMENTI.md) | UI komponente (60–67) |
| [DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md) | Logika, servisi, verovatnoća |
| [HELPER-KLASE.md](HELPER-KLASE.md) | Helper klase |
| [BRZI-VODIC.md](BRZI-VODIC.md) | Jedna strana za štampu |

---

## Kako naći segment na kolokvijumu

1. Broj zadatka u PDF-u → **01–16**
2. **Spinner, meni, lista…** → **60–67** (`ui-`)
3. **Senzor** → **04, 12** (zvanično) ili **41–49** (`senzor-`)
4. **API, Intent, fajl, mapa…** → **70–79**
5. **Alarm, audio, poziv, provider…** → **80–85**
6. **Notifikacije** → **37–40** · **Firebase** → **50–54**
