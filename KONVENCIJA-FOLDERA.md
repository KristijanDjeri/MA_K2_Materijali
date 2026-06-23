# Konvencija naziva foldera

Svi segmenti imaju prefiks **`NN-`** (dvocifrena enumeracija) radi reda na disku i u štampanom materijalu.

## Pravila imenovanja

| Pravilo | Primer |
|---------|--------|
| Prefiks broja + crtica | `01-osnovni-projekat` |
| Mala slova, crtice umesto razmaka | `07-brisanje-notifikacije` |
| **Notifikacije** (dodatno) | `37-notifikacije-pregled` … `40-notifikacija-prosirena` |
| **Senzori** počinju sa `senzor-` | `04-senzor-ziroskop`, `43-senzor-svetlosti` |
| Firebase grupa | `50-firebase` … `54-firebase-fcm` |
| Test projekat | `99-test-okruzenje` |
| Priručnici (bez koda za kolokvijum) | `90-…` |

**Ne koristimo** kolokvijalne ili dvosmislene nazive (npr. stari folder `vibrator` → sada **`33-povratna-vibracija`**).

---

## Enumeracija – kompletna tabela

### 01–11 Zvanični zadaci (PDF)

| Folder | Naziv | Zadatak |
|--------|-------|---------|
| [01-osnovni-projekat](01-osnovni-projekat/) | Osnovni projekat | 1–2 |
| [02-geo-lokacija](02-geo-lokacija/) | Geolokacija | 3 |
| [03-kamera](03-kamera/) | Kamera | 4 |
| [04-senzor-ziroskop](04-senzor-ziroskop/) | Senzor – žiroskop | 4 |
| [05-retrofit-room](05-retrofit-room/) | Retrofit + Room | 5 |
| [06-switch-postovi](06-switch-postovi/) | Switch i postovi | 6 |
| [07-brisanje-notifikacije](07-brisanje-notifikacije/) | Brisanje i notifikacija | 7 |
| [08-senzor-akcelerometar](08-senzor-akcelerometar/) | Senzor – akcelerometar | 8 |
| [09-shared-preferences](09-shared-preferences/) | SharedPreferences | 9 |
| [10-kontakti](10-kontakti/) | Kontakti | 9 |
| [11-main-activity-referenca](11-main-activity-referenca/) | MainActivity referenca | sve |

### 20–36 Dodatni segmenti (UI, mreža, storage…)

| Folder | Naziv |
|--------|-------|
| [20-recyclerview](20-recyclerview/) | RecyclerView |
| [21-galerija](21-galerija/) | Galerija |
| [22-intent-druga-aktivnost](22-intent-druga-aktivnost/) | Druga aktivnost |
| [23-retrofit-post](23-retrofit-post/) | Retrofit POST |
| [24-webview](24-webview/) | WebView |
| [25-thread-executor](25-thread-executor/) | Pozadinska nit |
| [26-interni-fajl](26-interni-fajl/) | Interni fajl |
| [27-edit-text-validacija](27-edit-text-validacija/) | EditText validacija |
| [28-alarm-notifikacija](28-alarm-notifikacija/) | Alarm notifikacija |
| [29-poziv-telefon](29-poziv-telefon/) | Poziv telefon |
| [30-spinner](30-spinner/) | Spinner |
| [31-date-picker](31-date-picker/) | Date/Time picker |
| [32-okhttp-json](32-okhttp-json/) | OkHttp JSON |
| [33-povratna-vibracija](33-povratna-vibracija/) | Povratna vibracija (haptic) |
| [34-lokacija-realtime](34-lokacija-realtime/) | Lokacija realtime |
| [35-progress-bar](35-progress-bar/) | ProgressBar |
| [36-audio-recorder](36-audio-recorder/) | Audio snimanje |

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
| [42-senzor-magnetometar](42-senzor-magnetometar/) | Senzor – magnetometar |
| [43-senzor-svetlosti](43-senzor-svetlosti/) | Senzor – svetlosti |
| [44-senzor-proksimiteta](44-senzor-proksimiteta/) | Senzor – proksimiteta |
| [45-senzor-barometar](45-senzor-barometar/) | Senzor – barometar |
| [46-senzor-koraci](46-senzor-koraci/) | Senzor – koraci |
| [47-senzor-izvedeni](47-senzor-izvedeni/) | Senzori izvedeni (gravity, rotation…) |
| [48-senzor-vlage-temperature](48-senzor-vlage-temperature/) | Senzor – vlage/temperature |
| [49-senzor-shake](49-senzor-shake/) | Senzor – shake (preko akcelerometra) |

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

## Koreni dokumenti (bez broja)

| Fajl | Svrha |
|------|-------|
| [README.md](README.md) | Glavni ulaz |
| [BRZI-VODIC.md](BRZI-VODIC.md) | Jedna strana za štampu |
| [DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md) | Verovatnoća tema |
| [IZVESTAJ-TESTIRANJA.md](IZVESTAJ-TESTIRANJA.md) | Šta je automatski testirano |

---

## Kako naći segment na kolokvijumu

1. Pogledaj broj zadatka u PDF-u → tabela **01–11** gore
2. Ako je senzor a nije u PDF-u → **41–49**
3. Ako traži notifikacije → **37–40**
4. Ako traži Fragment → **[90-fragments-prirucnik](90-fragments-prirucnik/)**
5. Ako traži Firebase → **50–54**
