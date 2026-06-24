# Vežba F – priručnik

Zasebna vežba u projektu **Kolokvijum2**: geolokacija, audio snimanje, Room + Retrofit sa **državama**, CheckBox logika i proximity senzor.

**Android Studio**, **minSdk 30**, **targetSdk 36**, **Java**.

---

## Pregled zadataka

| # | Zadatak | Folder u ovom priručniku |
|---|---------|--------------------------|
| 1–2 | Projekat `Kolokvijum2`, `MainActivity`, layout (2× CheckBox, Button „Snimi“, 2× TextView) | [01-osnovni-projekat/](01-osnovni-projekat/) |
| 3 | Geografska širina i dužina u **prvom** TextView-u | [02-geolokacija/](02-geolokacija/) |
| 4 | Dugme **Snimi** → snimanje zvuka; prvi CheckBox → stop + čuvanje u `files/` | [03-audio-snimanje/](03-audio-snimanje/) |
| 5 | Room model **država** + Retrofit GET ka Beeceptor API-ju | [04-room-drzave/](04-room-drzave/) + [05-retrofit-drzave/](05-retrofit-drzave/) |
| 6 | Drugi CheckBox → učitaj sve države sa API-ja u bazu | [06-checkbox-drzave/](06-checkbox-drzave/) |
| 7 | Svaki **naredni** ček drugog CheckBox-a → obriši poslednju državu + Toast sa brojem | [06-checkbox-drzave/](06-checkbox-drzave/) |
| 8 | **Drugi** TextView → očitavanje proximity senzora | [07-proksimitet/](07-proksimitet/) |

**Kompletan spojeni primer:** [15-main-activity-referenca/](15-main-activity-referenca/)

---

## Redosled učenja

```
01-osnovni-projekat
    ↓
02-geolokacija          (prvi TextView)
    ↓
03-audio-snimanje       (Button + prvi CheckBox)
    ↓
04-room-drzave          (Country, DAO, AppDatabase)
    ↓
05-retrofit-drzave      (GET /countries)
    ↓
06-checkbox-drzave      (drugi CheckBox – učitavanje + brisanje)
    ↓
07-proksimitet          (drugi TextView)
    ↓
15-main-activity-referenca   (sve u jednom fajlu – uporedi)
```

Svaki korak radi **samostalno** dok ne dođeš do referentnog `MainActivity`.

---

## API – Beeceptor

Stranica mock servera: `https://app.beeceptor.com/mock-server/dummy-json`  
Stvarni REST poziv ide na:

```
https://dummy-json.mock.beeceptor.com/countries
```

Odgovor (skraćeno):

```json
[
  { "name": "Serbia", "code": "RS" },
  { "name": "Germany", "code": "DE" }
]
```

> **Važno:** Beeceptor vraća **nevalidan JSON** (`{name: 'RS', code: 'RS'}` – bez navodnika oko ključeva). U [05-retrofit-drzave/](05-retrofit-drzave/) objašnjeno je rešenje (`ResponseBody` + preprocesiranje stringa pre Gson-a). Ako API vrati ispravan JSON, možeš koristiti običan `Call<List<Country>>`.

> **Ako Retrofit ne radi** (proxy na fakultetu, DNS…): u [05-retrofit-drzave/](05-retrofit-drzave/) ima **`ucitajDrzaveLokalno()`** – 10 ručno unetih država iz okruženja (Srbija i susedi). Zadaci 6–7 rade isto sa tom bazom.

---

## Mapa UI elemenata

```
┌─────────────────────────────────────┐
│  textViewLokacija    ← zadatak 3    │
│  checkBoxSnimanje    ← zadatak 4    │
│  buttonSnimi         ← zadatak 4    │
│  checkBoxDrzave      ← zadaci 6–7   │
│  textViewProksimitet ← zadatak 8    │
└─────────────────────────────────────┘
```

---

## Gradle zavisnosti (sve odjednom)

U `app/build.gradle` → `dependencies`:

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'

implementation 'com.google.android.gms:play-services-location:21.0.1'
```

---

## Dozvole u Manifest-u

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />

<uses-feature android:name="android.hardware.microphone" android:required="false" />
```

---

## Checklist

- [ ] Projekat `Kolokvijum2`, `MainActivity`
- [ ] Layout: 2 CheckBox-a, Button „Snimi“, 2 TextView-a (vertikalno)
- [ ] Prvi TextView: širina + dužina
- [ ] Snimi → snima, dugme disabled; CheckBox → stop, fajl u `getFilesDir()`, dugme enabled
- [ ] `Country` u Room + `CountryDao` + `AppDatabase`
- [ ] Retrofit GET `/countries` sa Beeceptor baze **ili** `ucitajDrzaveLokalno()` ako mreža ne radi
- [ ] Prvi ček drugog CheckBox-a: sve države u bazu
- [ ] Svaki sledeći ček: obriši poslednju + Toast sa `count()`
- [ ] Drugi TextView: proximity vrednost
