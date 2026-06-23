# Rotation Vector, Gravity, Linear Acceleration

**Dodatni segment.** **Slično:** žiroskop + akcelerometar, ali Android **već spaja** podatke u izvedene senzore.

---

## Pregled senzora

| Senzor | Tip | Šta daje |
|--------|-----|----------|
| Rotation Vector | `TYPE_ROTATION_VECTOR` | Orijentacija uređaja (kvaternioni) |
| Gravity | `TYPE_GRAVITY` | Gravitaciona komponenta (bez pokreta) |
| Linear Acceleration | `TYPE_LINEAR_ACCELERATION` | Ubrzanje bez gravitacije |
| Gyroscope | `TYPE_GYROSCOPE` | Već u `ziroskop/` |
| Accelerometer | `TYPE_ACCELEROMETER` | Već u `akcelerometar/` |

---

## Deo A: Gravity i Linear Acceleration (najjednostavnije)

### Polja

```java
private Sensor gravitySensor;
private Sensor linearAccelSensor;
```

### U `onCreate`

```java
gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
linearAccelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
```

### U `onResume`

```java
if (gravitySensor != null) {
    sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}
if (linearAccelSensor != null) {
    sensorManager.registerListener(this, linearAccelSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
    float gx = event.values[0];
    float gy = event.values[1];
    float gz = event.values[2];
    // textView.setText("Gravity X:" + gx + " Y:" + gy + " Z:" + gz);
}
else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
    float lx = event.values[0];
    float ly = event.values[1];
    float lz = event.values[2];
    button.setText("Lin X:" + lx + " Y:" + ly + " Z:" + lz);
}
```

> **Napomena:** Ovi senzori možda ne postoje na starijim uređajima – proveri `!= null`.

---

## Deo B: Rotation Vector (orijentacija u stepenima)

### Polje

```java
private Sensor rotationVectorSensor;
private float[] rotationMatrix = new float[9];
private float[] orientationAngles = new float[3];
```

### U `onCreate`

```java
rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
```

### U `onResume`

```java
if (rotationVectorSensor != null) {
    sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
    SensorManager.getOrientation(rotationMatrix, orientationAngles);
    float azimut = (float) Math.toDegrees(orientationAngles[0]);
    float pitch = (float) Math.toDegrees(orientationAngles[1]);
    float roll = (float) Math.toDegrees(orientationAngles[2]);
    textView.setText("Azimut: " + azimut + "° Pitch: " + pitch + "° Roll: " + roll + "°");
}
```

> **Alternativa:** Kompas iz `magnetometar/KompasSegment.java` koristi akcelerometar + magnetometar umesto rotation vector-a.

---

## Deo C: Sve senzore u jednom `onSensorChanged` (šablon)

```java
@Override
public void onSensorChanged(SensorEvent event) {
    switch (event.sensor.getType()) {
        case Sensor.TYPE_GYROSCOPE:
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];
            break;
        case Sensor.TYPE_ACCELEROMETER:
            button.setText("X:" + event.values[0] + " Y:" + event.values[1] + " Z:" + event.values[2]);
            break;
        case Sensor.TYPE_LIGHT:
            // lux
            break;
        case Sensor.TYPE_PROXIMITY:
            // blizu/daleko
            break;
        case Sensor.TYPE_PRESSURE:
            // hPa
            break;
        case Sensor.TYPE_STEP_COUNTER:
            // koraci
            break;
        case Sensor.TYPE_GRAVITY:
        case Sensor.TYPE_LINEAR_ACCELERATION:
        case Sensor.TYPE_ROTATION_VECTOR:
            // vidi gore
            break;
    }
}
```

> **Napomena:** `switch` na `event.sensor.getType()` radi na API 28+. Ne registruj senzore koje ne koristiš – troše bateriju.

---

## Checklist

- [ ] Svaki senzor: `getDefaultSensor` + provera null
- [ ] Registracija u onResume, odjava u onPause
- [ ] Jedan `onSensorChanged` sa grananjem po tipu
