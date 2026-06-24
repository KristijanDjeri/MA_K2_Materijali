# Shake senzor (detekcija tresenja)

**Dodatni segment.** **Ne postoji** poseban `Sensor.TYPE_SHAKE` – tresenje se detektuje pomoću **akcelerometra** koji već koristiš u zadatku 8.

**Cilj:** Kada korisnik protrese telefon → Toast „Tresenje detektovano!" (ili neka druga akcija).

**Helper (ceo kod):** `ShakeHelper.java` + `ShakeDetector.java` – **`onResume()` / `onPause()`** u MainActivity. Vidi **`HELPER-KLASE.md`**.

```java
shakeHelper = new ShakeHelper(this, () -> Toast.makeText(this, "Tresenje!", Toast.LENGTH_SHORT).show());
// onResume: shakeHelper.onResume();
// onPause: shakeHelper.onPause();
```

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ShakeHelper.java`** + **`ShakeDetector.java`** | `app/.../helper/` (oba fajla) |
| 2 | `MainActivity.java` | **`onCreate`**: `shakeHelper = new ShakeHelper(this, callback);` |
| 3 | `MainActivity.java` | **`onResume`**: `shakeHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `shakeHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`ShakeHelper.java`** i **`ShakeDetector.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.ShakeHelper;

private ShakeHelper shakeHelper;

// onCreate:
shakeHelper = new ShakeHelper(this, () ->
        Toast.makeText(this, "Tresenje detektovano!", Toast.LENGTH_SHORT).show()
);

// onResume / onPause:
shakeHelper.onResume();
shakeHelper.onPause();
```

> Za stari inline primer pogledaj `ShakeSegment.java` u istom folderu.

---

## Preduslovi

- `12-senzor-akcelerometar/` – shake koristi akcelerometar preko `ShakeHelper` (ne dodaješ `SensorEventListener` u MainActivity)

---

## Primer: shake briše prvi post (kombinacija sa zadatkom 7)

U `onCreate`, u callback-u `ShakeHelper`-a:

```java
shakeHelper = new ShakeHelper(this, () -> {
    Toast.makeText(this, "Shake – brišem prvi post", Toast.LENGTH_SHORT).show();
    postRepository.obrisiPrviPost(
            () -> NotifikacijaHelper.posaljiPraznaBaza(this)
    );
});
```

---

## Podešavanje osetljivosti

| Vrednost | Efekat |
|----------|--------|
| Manji `SHAKE_THRESHOLD` (npr. 8) | Osetljivije – lakše okine |
| Veći `SHAKE_THRESHOLD` (npr. 15) | Treba jače tresenje |
| Manji `SHAKE_COOLDOWN_MS` | Češći shake eventi |
| `SENSOR_DELAY_GAME` | Brže očitavanje – bolje za shake |

Prag i cooldown podešavaš u `ShakeDetector.java` (u paketu `helper`), ne u MainActivity.

> **Pažnja:** Brži delay troši više baterije. Za kolokvijum je `SENSOR_DELAY_NORMAL` dovoljan.

---

## Testiranje

1. Pokreni app na **fizičkom telefonu** (emulator teško simulira shake)
2. Protresi telefon
3. Treba da iskoči Toast

Na emulatoru: Extended Controls → Sensors → pomeri vrednosti akcelerometra naglo.

---

## Checklist

- [ ] `ShakeHelper` + `ShakeDetector` u paketu `helper`
- [ ] `shakeHelper.onResume()` / `onPause()` u MainActivity
- [ ] Callback u konstruktoru `ShakeHelper` radi željenu akciju

---

## Česte greške

| Problem | Rešenje |
|---------|---------|
| Ništa se ne dešava | Smanji `SHAKE_THRESHOLD` ili tresi jače |
| Toast non-stop | Povećaj `SHAKE_COOLDOWN_MS` ili prag |
| Konflikt sa tekstom na Button | Oba mogu raditi – shake je u istom `onSensorChanged` |

---

## Sledeći korak

Poveži sa drugim segmentima po potrebi: `82-povratna-vibracija/`, `11-notifikacija-prazna-baza/`, `09-switch-listener/`.
