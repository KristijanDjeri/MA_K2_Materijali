# Magnetometar i kompas

**Dodatni segment.** **Slično:** žiroskop i 12-senzor-akcelerometar (zadaci 4, 8).

---

## Deo A: Magnetometar (X, Y, Z) – osnovno

### Polje u `MainActivity`

```java
private Sensor magnetometer;
```

### U `onCreate`

```java
magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
```

### U `onResume`

```java
if (magnetometer != null) {
    sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
}
```

### U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
    float mx = event.values[0];
    float my = event.values[1];
    float mz = event.values[2];
    textView.setText("Mag X:" + mx + " Y:" + my + " Z:" + mz);
}
```

> **Alternativa:** Toast umesto TextView – ako ne želiš da pregaziš lokaciju/kontakt.

---

## Deo B: Kompas (azimut u stepenima) – naprednije

Za azimut trebaju **oba**: 12-senzor-akcelerometar + 42-senzor-magnetometar.

### Polja

```java
private float[] gravity = new float[3];
private float[] geomagnetic = new float[3];
private float[] rotationMatrix = new float[9];
private float[] orientation = new float[3];
```

### U `onSensorChanged` (zameni ili dopuni logiku)

```java
@Override
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        gravity = event.values.clone();
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        button.setText("X: " + ax + " Y: " + ay + " Z: " + az);
    } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
        geomagnetic = event.values.clone();
    } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        gyroX = event.values[0];
        gyroY = event.values[1];
        gyroZ = event.values[2];
    }

    if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
        SensorManager.getOrientation(rotationMatrix, orientation);
        float azimutRad = orientation[0];
        float azimutStepeni = (float) Math.toDegrees(azimutRad);
        // Opciono prikaži negde:
        // textView.setText("Azimut: " + azimutStepeni + "°");
    }
}
```

### Registracija u `onResume`

Moraš registrovati **12-senzor-akcelerometar**, **42-senzor-magnetometar** i **žiroskop** (ako koristiš sve).

---

## Checklist

- [ ] `TYPE_MAGNETIC_FIELD` senzor
- [ ] Registrovan u onResume
- [ ] (Kompas) Oba senzora + `getRotationMatrix`
