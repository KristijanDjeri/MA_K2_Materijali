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


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// POLJA:
private Sensor barometer;

// U onCreate():
barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

// U onResume():
if (barometer != null) {
    sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
    textView.setText("Pritisak: " + event.values[0] + " hPa");
}
```

## Checklist

- [ ] `BarometarHelper` u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] Vrednost u hPa
