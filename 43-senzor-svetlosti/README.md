# Senzor svetlosti (Light)

**Dodatni segment.** **Slično:** žiroskop, 12-senzor-akcelerometar (SensorEventListener).

**Cilj:** Prikaži jačinu svetlosti u lux vrednostima u TextView.

---

## Preduslovi

- `MainActivity` već implementira `SensorEventListener` (folder `04-senzor-ziroskop/`)

---

## Kompletan kod za `MainActivity.java`

### 1. Polje

```java
private Sensor lightSensor;
```

### 2. U `onCreate`

```java
lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
```

### 3. U `onResume`

```java
if (lightSensor != null) {
    sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### 4. U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
    float lux = event.values[0];
    textView.setText("Svetlina: " + lux + " lux");
}
```

> **Napomena:** Na emulatoru pokrij/otkrij senzor u Extended Controls → Sensors.

---

## Alternativa

- Promeni pozadinu ekrana kad je tamno (`lux < 10`)
- Ne diraj TextView ako ga koristi lokacija – koristi Toast ili drugi TextView

---

## Checklist

- [ ] `TYPE_LIGHT` senzor
- [ ] Registrovan u onResume
- [ ] Prikaz lux vrednosti
