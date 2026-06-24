# Senzor svetlosti (Light)

**Dodatni segment.** Helper: **`SvetlostiHelper.java`**

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`SvetlostiHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `svetlostiHelper = new SvetlostiHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `svetlostiHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `svetlostiHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`SvetlostiHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.SvetlostiHelper;

private SvetlostiHelper svetlostiHelper;

// onCreate:
svetlostiHelper = new SvetlostiHelper(this, textView);

// onResume:
svetlostiHelper.onResume();

// onPause:
svetlostiHelper.onPause();
```

Puna implementacija senzora je u **`SvetlostiHelper.java`** – ne u MainActivity.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// MainActivity implementira SensorEventListener (pored žiroskopa/akcelerometra)

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;

// POLJA:
private Sensor lightSensor;

// U onCreate():
lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

// U onResume():
if (lightSensor != null) {
    sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged() dodaj:
} else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
    textView.setText("Svetlina: " + event.values[0] + " lux");
}
```

## Checklist

- [ ] Helper u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] TextView prikazuje lux

---

## Povezano

- **`HELPER-KLASE.md`**
- **`42-senzor-magnetometar/`** – isti obrazac
