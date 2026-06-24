# Barometar (pritisak vazduha)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Prikaži atmosferski pritisak u hPa (hektopaskalima).

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`BarometarHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `barometarHelper = new BarometarHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `barometarHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `barometarHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`BarometarHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.BarometarHelper;

private BarometarHelper barometarHelper;

// onCreate:
barometarHelper = new BarometarHelper(this, textView);

// onResume / onPause:
barometarHelper.onResume();
barometarHelper.onPause();
```

> **Alternativa:** inline kod ispod (zastarelo).

---

## Alternativa: inline u `MainActivity.java` (zastarelo)

### 1. Polje

```java
private Sensor pressureSensor;
```

### 2. U `onCreate`

```java
pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
```

### 3. U `onResume`

```java
if (pressureSensor != null) {
    sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### 4. U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
    float pritisak = event.values[0]; // hPa
    textView.setText("Pritisak: " + pritisak + " hPa");
}
```

> **Napomena:** Nema na svakom telefonu. Ako je `pressureSensor == null`, prikaži Toast „Nema barometra".

---

## Checklist

- [ ] `BarometarHelper` u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] Vrednost u hPa
