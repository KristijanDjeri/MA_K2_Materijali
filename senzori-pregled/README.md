# Pregled svih senzora

Ovaj folder je **mapa** – svaki senzor ima svoj folder sa kompletnim kodom u README.

---

## Već na kolokvijumu (zvanično)

| Senzor | Folder |
|--------|--------|
| Žiroskop | [ziroskop/](../ziroskop/) |
| Akcelerometar | [akcelerometar/](../akcelerometar/) |

---

## Dodatni senzori (novi folderi)

| Senzor | Android tip | Folder |
|--------|-------------|--------|
| Magnetometar / kompas | `TYPE_MAGNETIC_FIELD` | [magnetometar/](../magnetometar/) |
| Svetlina (lux) | `TYPE_LIGHT` | [senzor-svetlosti/](../senzor-svetlosti/) |
| Proksimitet | `TYPE_PROXIMITY` | [senzor-proksimiteta/](../senzor-proksimiteta/) |
| Barometar (pritisak) | `TYPE_PRESSURE` | [barometar/](../barometar/) |
| Brojač koraka | `TYPE_STEP_COUNTER` | [brojac-koraka/](../brojac-koraka/) |
| Detektor koraka | `TYPE_STEP_DETECTOR` | [brojac-koraka/](../brojac-koraka/) (alternativa) |
| Gravity | `TYPE_GRAVITY` | [senzori-izvedeni/](../senzori-izvedeni/) |
| Linear acceleration | `TYPE_LINEAR_ACCELERATION` | [senzori-izvedeni/](../senzori-izvedeni/) |
| Rotation vector | `TYPE_ROTATION_VECTOR` | [senzori-izvedeni/](../senzori-izvedeni/) |
| Vlaga | `TYPE_RELATIVE_HUMIDITY` | [senzor-vlage-temperature/](../senzor-vlage-temperature/) |
| Temperatura | `TYPE_AMBIENT_TEMPERATURE` | [senzor-vlage-temperature/](../senzor-vlage-temperature/) |

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

## Audio (nije senzor, ali sličan princip – hardver + dozvola)

| Funkcija | Folder |
|----------|--------|
| Snimanje i puštanje zvuka | [audio-recorder/](../audio-recorder/) |

---

## Redosled učenja

1. `ziroskop/` + `akcelerometar/` (kolokvijum)
2. `magnetometar/` (najčešća zamena)
3. `senzor-svetlosti/` ili `senzor-proksimiteta/` (jednostavni)
4. `senzori-izvedeni/` ako traže orijentaciju
5. `audio-recorder/` ako traže mikrofon
