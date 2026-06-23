# Barometar (pritisak vazduha)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Prikaži atmosferski pritisak u hPa (hektopaskalima).

---

**Helper (ceo kod):** `BarometarHelper.java` – `onResume()` / `onPause()`. **`HELPER-KLASE.md`**

```java
barometarHelper = new BarometarHelper(this, textView);
```

---

## Inline varijanta (zastarelo)

## Kompletan kod za `MainActivity.java`

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

> **Napomena:** Nema na svakom telefonu. Ako je `pressureSensor == null`, prikaži Toast "Nema barometra".

### 5. (Opciono) Provera dostupnosti

```java
if (pressureSensor == null) {
    Toast.makeText(this, "Barometar nije dostupan", Toast.LENGTH_SHORT).show();
}
```

---

## Alternativa

- Izračunaj nadmorsku visinu iz pritiska – formula, retko na kolokvijumu

---

## Checklist

- [ ] `TYPE_PRESSURE`
- [ ] Vrednost u hPa
