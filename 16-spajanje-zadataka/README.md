# Spajanje segmenata za pun kolokvijum

Zadaci 1–9 na ispitu koriste **isti** `MainActivity` i **iste** UI elemente. Segmenti `01`–`14` su namerno **razdvojeni** da svaki radi samostalno. Ovaj folder objašnjava **kako ih spojiti** kao na kolokvijumu.

Kompletan primer: **`15-main-activity-referenca/MainActivity.java`**.

---

## Redosled učenja (nezavisno)

| # | Folder | Test okidač |
|---|--------|-------------|
| 1 | `01-osnovni-projekat` | Layout |
| 2 | `02-geo-lokacija` | TextView koordinate |
| 3 | `03-kamera` | ImageButton → slika |
| 4 | `04-senzor-ziroskop` | Toast X,Y,Z (vidi ispod) |
| 5 | `05-room-baza` | `postDao` init |
| 6 | `06-retrofit-get` | Toast broj postova sa API-ja |
| 7 | `07-ucitaj-10-postova` | Dugme → 10 u bazi |
| 8 | `08-toast-prvi-post` | Dugme → Toast title |
| 9 | `09-switch-listener` | Switch ON/OFF |
| 10 | `10-brisanje-prvog-posta` | Dugme → briši prvi |
| 11 | `11-notifikacija-prazna-baza` | Prazna baza → notifikacija |
| 12 | `12-senzor-akcelerometar` | Tekst na dugmetu |
| 13 | `13-shared-preferences` | Switch OFF deo 1 |
| 14 | `14-kontakti` | Switch OFF deo 2 |

---

## Šta se na ispitu deli isti widget

### 1. Kamera + žiroskop (zadatak 4)

**Vežba:** `03-kamera` bez Toast-a · `04-senzor-ziroskop` sa test dugmetom.

**Ispit:** U callback-u `KameraHelper`, posle slike:

```java
ziroskopHelper.prikaziToast(); // iz 04-senzor-ziroskop/
```

### 2. Switch (zadaci 6 i 9)

**Vežba:** `07` i `08` na **dugmad**.

**Ispit:** Ukloni test listenere sa dugmeta; sve preko `09-switch-listener/`.

### 3. Isto dugme – brisanje + akcelerometar (zadaci 7 i 8)

**Vežba:** `10` samo klik · `12` samo tekst iz senzora (bez klika na istom dugmetu).

**Ispit:** Jedan `button` – tekst iz `AkcelerometarHelper`, klik preko `PostRepository`:

```java
// onCreate (10 + 11):
NotifikacijaHelper.kreirajKanal(this);
button.setOnClickListener(v -> postRepository.obrisiPrviPost(
        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
));
// AkcelerometarHelper u onResume/onPause menja tekst dugmeta
```

Tekst se menja iz senzora; **klik** i dalje briše i šalje notifikaciju.

### 4. Brisanje + notifikacija (zadatak 7)

**Vežba:** folderi `10` i `11` odvojeno.

**Ispit:** `PostRepository.obrisiPrviPost()` sa callback-om:

```java
postRepository.obrisiPrviPost(() -> NotifikacijaHelper.posaljiPraznaBaza(this));
```

### 5. Switch OFF (zadatak 9)

**Vežba:** `13` i `14` posebno (npr. dva dugmeta).

**Ispit:** `SwitchPostsHelper` iz `09-switch-listener/` – OFF deo poziva `SharedPreferencesHelper` + `KontaktiHelper`.

---

## Minimalna mapa u jednom MainActivity (helper pristup)

```
onCreate()
  ├── postDao init (05)
  ├── postRepository = new PostRepository(...) (07)
  ├── NotifikacijaHelper.kreirajKanal (11)
  ├── SwitchPostsHelper (09) – ON/OFF logika
  ├── button → postRepository.obrisiPrviPost + NotifikacijaHelper (10+11)
  ├── imageButton → kameraHelper.pokreni() (03)
  ├── ziroskopHelper, akcelerometarHelper init (04, 12)
  └── geoHelper, prefsHelper, kontaktiHelper...

onResume/onPause
  └── ziroskopHelper, akcelerometarHelper.onResume/onPause
```

Kompletan primer: **`15-main-activity-referenca/MainActivity.java`**

---

## Kada koristiti referencu

1. Uradi segmente **pojedinačno** i proveri da svaki radi.
2. Spoji po tabeli iznad.
3. Uporedi sa **`15-main-activity-referenca/`** – diff u Android Studio.

---

## Alternativa: inline implementacija u MainActivity

> Ako helper klase ne rade ili ne želiš paket `helper/`, spoji segmente tako što **lepiš inline kod** iz svakog foldera u jedan `MainActivity.java`.

Svaki segment README (npr. `02-geo-lokacija/`, `03-kamera/`, …) ima sekciju **„Alternativa: inline implementacija u MainActivity“** sa gotovim delovima koda.

**Redosled lepljenja:**

1. Polja i importi iz svih segmenata (ukloni duplikate)
2. `onCreate` – spoji init blokove (geo, kamera, postDao, switch listener, …)
3. `onResume` / `onPause` – registracija svih senzora
4. `onSensorChanged` – jedan metod sa `if/else` po tipu senzora
5. `onRequestPermissionsResult` – `switch` po `requestCode`

**Kompletan inline primer** (zadaci 1–9): u **`15-main-activity-referenca/README.md`** → sekcija „Stari inline primer“ (u `<details>` bloku).

**Primer spajanja kamere + žiroskopa (inline):**

```java
private final ActivityResultLauncher<Void> takePictureLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                prikaziZiroskopToast(); // metoda iz 04-senzor-ziroskop/
            }
        });
```

**Primer spajanja dugmeta (inline):**

```java
// Tekst menja akcelerometar u onSensorChanged (12-senzor-akcelerometar/)
// Klik briše + notifikacija (10 + 11):
button.setOnClickListener(v -> obrisiPrviPostINotifikacija());
```

---

## Fragmenti

Ako traže Fragment umesto Activity → **`90-fragments-prirucnik/`** (isti segmenti, drugi kontekst: `requireContext()`).
