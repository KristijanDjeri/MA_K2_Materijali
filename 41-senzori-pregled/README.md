# Pregled svih senzora

Ovaj folder je **mapa** – svaki senzor ima **helper klasu** sa punim `onResume()` / `onPause()`.

> **Obrazac:** kopiraj `XxxHelper.java` u paket `helper`, u MainActivity samo pozovi lifecycle.  
> Mapa: **`HELPER-KLASE.md`**

---

## Već na kolokvijumu (zvanično)

| Senzor | Folder |
|--------|--------|
| Žiroskop | [04-senzor-ziroskop/](../04-senzor-ziroskop/) |
| Akcelerometar | [12-senzor-akcelerometar/](../12-senzor-akcelerometar/) |
| **Shake (tresenje)** | [49-senzor-shake/](../49-senzor-shake/) – preko akcelerometra |

## Dodatni senzori (novi folderi)

| Senzor | Android tip | Folder |
|--------|-------------|--------|
| Magnetometar / kompas | `TYPE_MAGNETIC_FIELD` | [42-senzor-magnetometar/](../42-senzor-magnetometar/) |
| Svetlina (lux) | `TYPE_LIGHT` | [43-senzor-svetlosti/](../43-senzor-svetlosti/) |
| Proksimitet | `TYPE_PROXIMITY` | [44-senzor-proksimiteta/](../44-senzor-proksimiteta/) |
| Barometar (pritisak) | `TYPE_PRESSURE` | [45-senzor-barometar/](../45-senzor-barometar/) |
| Brojač koraka | `TYPE_STEP_COUNTER` | [46-senzor-koraci/](../46-senzor-koraci/) |
| Detektor koraka | `TYPE_STEP_DETECTOR` | [46-senzor-koraci/](../46-senzor-koraci/) (alternativa) |
| Gravity | `TYPE_GRAVITY` | [47-senzor-izvedeni/](../47-senzor-izvedeni/) |
| Linear acceleration | `TYPE_LINEAR_ACCELERATION` | [47-senzor-izvedeni/](../47-senzor-izvedeni/) |
| Rotation vector | `TYPE_ROTATION_VECTOR` | [47-senzor-izvedeni/](../47-senzor-izvedeni/) |
| Vlaga | `TYPE_RELATIVE_HUMIDITY` | [48-senzor-vlage-temperature/](../48-senzor-vlage-temperature/) |
| Temperatura | `TYPE_AMBIENT_TEMPERATURE` | [48-senzor-vlage-temperature/](../48-senzor-vlage-temperature/) |

---

## Zajednički obrazac (helper – preporučeno)

Svaki senzor ima **helper klasu** u paketu `helper`. U `MainActivity` samo:

```java
private SvetlostiHelper svetlostiHelper;

// onCreate:
svetlostiHelper = new SvetlostiHelper(this, textView);

// onResume / onPause:
svetlostiHelper.onResume();
svetlostiHelper.onPause();
```

**MainActivity NE implementira** `SensorEventListener` – helper to radi interno.

---

## Koji senzori postoje na uređaju? (opciono)

Za debug listu senzora pogledaj `SenzoriPregledSegment.java` u ovom folderu (Logcat, nije deo MainActivity zadatka).

---

## Audio i haptic (hardver + dozvola, nisu senzori)

| Funkcija | Folder |
|----------|--------|
| Snimanje i puštanje zvuka | [83-audio-recorder/](../83-audio-recorder/) |
| Povratna vibracija (haptic) | [82-povratna-vibracija/](../82-povratna-vibracija/) |

---

## Redosled učenja

1. `04-senzor-ziroskop/` + `12-senzor-akcelerometar/` (kolokvijum)
2. `42-senzor-magnetometar/` (najčešća zamena)
3. `43-senzor-svetlosti/` ili `44-senzor-proksimiteta/` (jednostavni)
4. `49-senzor-shake/` (12-senzor-akcelerometar + prag – česta vežba)
5. `47-senzor-izvedeni/` ako traže orijentaciju
6. `83-audio-recorder/` ako traže mikrofon
