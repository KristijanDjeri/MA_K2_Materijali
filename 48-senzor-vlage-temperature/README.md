# Senzor vlage (Humidity) i temperature (Ambient)

**Dodatni segment.** Retki na telefonima, ali se ponekad pominju na vežbama.

**Helper (ceo kod):** `VlagaTemperaturaHelper.java` – oba senzora, `onResume()` / `onPause()`.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`VlagaTemperaturaHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `vlagaHelper = new VlagaTemperaturaHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `vlagaHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `vlagaHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`VlagaTemperaturaHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.VlagaTemperaturaHelper;

private VlagaTemperaturaHelper vlagaHelper;

// onCreate:
vlagaHelper = new VlagaTemperaturaHelper(this, textView);

// onResume / onPause:
vlagaHelper.onResume();
vlagaHelper.onPause();
```


---


## Senzor vlage

### Polje i init

```java
private Sensor humiditySensor;

// onCreate:
humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
```

### onResume

```java
if (humiditySensor != null) {
    sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### onSensorChanged

```java
else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
    float vlaga = event.values[0]; // procenat
    textView.setText("Vlaga: " + vlaga + " %");
}
```

---

## Senzor temperature (ambient)

```java
private Sensor tempSensor;

// onCreate:
tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

// onSensorChanged:
else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
    float temp = event.values[0]; // °C
    textView.setText("Temperatura: " + temp + " °C");
}
```

> **Napomena:** Većina telefona **nema** ove senzore. Uvek proveri:
```java
if (humiditySensor == null) {
    Toast.makeText(this, "Nema senzora vlage", Toast.LENGTH_SHORT).show();
}
```

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// POLJA:
private Sensor tempSensor;
private Sensor humiditySensor;

// U onCreate():
tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

// U onResume():
if (tempSensor != null) {
    sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
if (humiditySensor != null) {
    sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
    textView.setText("Temperatura: " + event.values[0] + " °C");
} else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
    textView.setText("Vlažnost: " + event.values[0] + " %");
}
```

## Alternativa

- Temperatura baterije: `Intent.ACTION_BATTERY_CHANGED` – nije senzor okoline, ali se ponekad koristi kao zamena

---

## Checklist

- [ ] Provera `getDefaultSensor != null`
- [ ] Ne očekuj da radi na emulatoru
