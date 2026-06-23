# Senzor vlage (Humidity) i temperature (Ambient)

**Dodatni segment.** Retki na telefonima, ali se ponekad pominju na vežbama.

**Helper (ceo kod):** `VlagaTemperaturaHelper.java` – oba senzora, `onResume()` / `onPause()`.

```java
vlagaHelper = new VlagaTemperaturaHelper(this, textView);
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

## Alternativa

- Temperatura baterije: `Intent.ACTION_BATTERY_CHANGED` – nije senzor okoline, ali se ponekad koristi kao zamena

---

## Checklist

- [ ] Provera `getDefaultSensor != null`
- [ ] Ne očekuj da radi na emulatoru
