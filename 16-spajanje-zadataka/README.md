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

**Ispit:** U callback-u kamere, posle `setImageBitmap`:

```java
prikaziZiroskopToast(); // iz 04-senzor-ziroskop/
```

### 2. Switch (zadaci 6 i 9)

**Vežba:** `07` i `08` na **dugmad**.

**Ispit:** Ukloni test listenere sa dugmeta; sve preko `09-switch-listener/`.

### 3. Isto dugme – brisanje + akcelerometar (zadaci 7 i 8)

**Vežba:** `10` samo klik · `12` samo tekst iz senzora (bez klika na istom dugmetu).

**Ispit:** Jedan `button`:

```java
// onSensorChanged (12):
button.setText("X: " + ax + " Y: " + ay + " Z: " + az);

// onCreate (10 + 11):
button.setOnClickListener(v -> obrisiPrviPost());
```

Tekst se menja iz senzora; **klik** i dalje briše i šalje notifikaciju.

### 4. Brisanje + notifikacija (zadatak 7)

**Vežba:** folderi `10` i `11` odvojeno.

**Ispit:** U `obrisiPrviPost()` na kraju:

```java
if (postDao.count() == 0) {
    posaljiNotifikacijuPrazneBaze();
}
```

### 5. Switch OFF (zadatak 9)

**Vežba:** `13` i `14` posebno (npr. dva dugmeta).

**Ispit:** `obradiSwitchOff()` u `09-switch-listener/` poziva obe metode.

---

## Minimalna mapa metoda u jednom MainActivity

```
onCreate()
  ├── postDao init (05)
  ├── kreirajNotificationChannel (11)
  ├── switchPosts listener (09)
  ├── button click → obrisiPrviPost (10+11)
  ├── imageButton → kamera (03)
  └── senzori register (04, 12)

ucitajPostoveSaApi()     ← 07
prikaziTitlePrvogPosta() ← 08
obradiSwitchOn/Off()     ← 09, 13, 14
obrisiPrviPost()         ← 10, 11
prikaziZiroskopToast()   ← 04
onSensorChanged()        ← 04, 12
```

---

## Kada koristiti referencu

1. Uradi segmente **pojedinačno** i proveri da svaki radi.
2. Spoji po tabeli iznad.
3. Uporedi sa **`15-main-activity-referenca/`** – diff u Android Studio.

---

## Fragmenti

Ako traže Fragment umesto Activity → **`90-fragments-prirucnik/`** (isti segmenti, drugi kontekst: `requireContext()`).
