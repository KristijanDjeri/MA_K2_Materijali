# Senzor proksimiteta (Proximity)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Detektuj da li je nešto blizu senzora (npr. telefon uz uvo).

---

## Kompletan kod za `MainActivity.java`

### 1. Polje

```java
private Sensor proximitySensor;
```

### 2. U `onCreate`

```java
proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
```

### 3. U `onResume`

```java
if (proximitySensor != null) {
    sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### 4. U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
    float distance = event.values[0];
    float maxRange = proximitySensor.getMaximumRange();
    if (distance < maxRange) {
        textView.setText("Blizu (proximity)");
    } else {
        textView.setText("Daleko (proximity)");
    }
}
```

> **Objašnjenje:** Većina uređaja vraća `0` kad je blizu, `maxRange` kad je daleko.

---

## Alternativa

- Ugasiti ekran kad je blizu – zahteva `FLAG_KEEP_SCREEN_ON` / PowerManager – naprednije
- Toast umesto TextView

---

## Checklist

- [ ] `TYPE_PROXIMITY`
- [ ] Poređenje sa `getMaximumRange()`
