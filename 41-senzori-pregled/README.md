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

## Zajednički obrazac (isti za SVE senzore)

Svaki senzor prati iste korake:

```java
// 1. Polje
private Sensor mojSenzor;

// 2. onCreate
mojSenzor = sensorManager.getDefaultSensor(Sensor.TYPE_...);

// 3. onResume
if (mojSenzor != null) {
    sensorManager.registerListener(this, mojSenzor, SensorManager.SENSOR_DELAY_NORMAL);
}

// 4. onPause
sensorManager.unregisterListener(this);

// 5. onSensorChanged
if (event.sensor.getType() == Sensor.TYPE_...) {
    float vrednost = event.values[0];
    // prikaži u TextView / Button / Toast
}
```

**MainActivity mora implementirati:** `implements SensorEventListener`

---

## Koji senzori postoje na uređaju?

```java
private void ispisiDostupneSenzore() {
    List<Sensor> lista = sensorManager.getSensorList(Sensor.TYPE_ALL);
    for (Sensor s : lista) {
        Log.d("SENZOR", s.getName() + " tip=" + s.getType());
    }
}
```

Pozovi u `onCreate` i pogledaj **Logcat** u Android Studio.

---

## Audio i haptic (hardver + dozvola, nisu senzori)

| Funkcija | Folder |
|----------|--------|
| Snimanje i puštanje zvuka | [36-audio-recorder/](../36-audio-recorder/) |
| Povratna vibracija (haptic) | [33-povratna-vibracija/](../33-povratna-vibracija/) |

---

## Redosled učenja

1. `04-senzor-ziroskop/` + `12-senzor-akcelerometar/` (kolokvijum)
2. `42-senzor-magnetometar/` (najčešća zamena)
3. `43-senzor-svetlosti/` ili `44-senzor-proksimiteta/` (jednostavni)
4. `49-senzor-shake/` (12-senzor-akcelerometar + prag – česta vežba)
5. `47-senzor-izvedeni/` ako traže orijentaciju
6. `36-audio-recorder/` ako traže mikrofon
