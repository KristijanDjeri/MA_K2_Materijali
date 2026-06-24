# Žiroskop (zadatak 4 – deo 2)

**Cilj:** Prikaži **Toast** sa očitanjem žiroskopa po **X, Y, Z** osi.

Na ispitu: Toast posle nove slike u `ImageView` (spajanje sa `03-kamera/` u `16-spajanje-zadataka/`).

**Samostalna vežba:** Testiraj Toast na **dugme** – bez kamere.

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – layout
- `ZiroskopHelper` – **ne** dodaj `implements SensorEventListener` u MainActivity

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ZiroskopHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje: `private ZiroskopHelper ziroskopHelper;` |
| 3 | `MainActivity.java` | **`onCreate`**: `ziroskopHelper = new ZiroskopHelper(this);` |
| 4 | `MainActivity.java` | **`onResume`**: `ziroskopHelper.onResume();` |
| 5 | `MainActivity.java` | **`onPause`**: `ziroskopHelper.onPause();` |
| 6 | Kamera (opciono) | U `KameraHelper` callback: `ziroskopHelper.prikaziToast();` |

> **Ne** dodaj `implements SensorEventListener` u MainActivity.

---

## Kompletan kod – helper klasa

Kopiraj **`ZiroskopHelper.java`** iz ovog foldera u `app/.../helper/ZiroskopHelper.java`.

---

## MainActivity – povezivanje (helper)

```java
import com.example.kolokvijum2.helper.ZiroskopHelper;

private ZiroskopHelper ziroskopHelper;

// onCreate, posle findViewById:
ziroskopHelper = new ZiroskopHelper(this);

// onResume:
ziroskopHelper.onResume();

// onPause:
ziroskopHelper.onPause();

// posle kamere (KameraHelper listener):
ziroskopHelper.prikaziToast();
```

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Čuvanje X,Y,Z u poljima | Toast direktno u `onSensorChanged` – ali bi se prikazivao stalno, ne samo pri slici |
| `SENSOR_DELAY_NORMAL` | `SENSOR_DELAY_FASTEST` – brže očitavanje |
| Jedan `SensorEventListener` | Odvojeni listener samo za žiroskop – čistije ako imaš više senzora |

> **Važno:** Kad dodaš **12-senzor-akcelerometar/**, u istom `onSensorChanged` dodaješ `else if` za `TYPE_ACCELEROMETER`.

---

## Checklist

- [ ] `ZiroskopHelper` u paketu `helper`
- [ ] Žiroskop registrovan u `onResume` (preko helpera)
- [ ] Odjavljen u `onPause` (preko helpera)
- [ ] `ziroskopHelper.prikaziToast()` radi (dugme ili posle kamere)
- [ ] Toast prikazuje X, Y, Z

---

## Napomena za emulator

Emulator često **nema** žiroskop. Na fizičkom telefonu radi pouzdano. Za kolokvijum je dovoljno da kod postoji i da Toast iskoči (vrednosti mogu biti 0 na emulatoru).

---

## Sledeći korak

Folder **`05-room-baza/`** za zadatak 5 (Room model).
