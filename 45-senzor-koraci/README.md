# Brojač koraka (Step Counter)

**Dodatni segment.** **Slično:** ostali senzori, ali broj se **akumulira** od boot-a.

**Cilj:** Prikaži ukupan broj koraka od poslednjeg paljenja uređaja.

---

## Korak 1: Manifest (Android 10+)

```xml
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

Na API 28–29 obično **nije** potrebna ova dozvola.

---

## Kompletan kod za `MainActivity.java`

### 1. Importi

```java
import android.os.Build;
```

### 2. Konstanta i polje

```java
private static final int REQ_ACTIVITY = 107;
private Sensor stepCounterSensor;
```

### 3. U `onCreate`

```java
stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
```

### 4. U `onResume` – sa proverom dozvole na API 29+

```java
@Override
protected void onResume() {
    super.onResume();
    // ... ostali senzori ...
    if (stepCounterSensor != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQ_ACTIVITY);
            } else {
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}
```

### 5. U `onSensorChanged`

```java
else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
    float koraci = event.values[0];
    button.setText("Koraci: " + (int) koraci);
}
```

> **Napomena:** `TYPE_STEP_COUNTER` vraća ukupan broj od boot-a, ne korake u hodu. Za korake „uživo" postoji `TYPE_STEP_DETECTOR` (po jedan event po koraku).

### 6. U `onRequestPermissionsResult`

```java
} else if (requestCode == REQ_ACTIVITY) {
    if (stepCounterSensor != null) {
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
```

---

## Alternativa: STEP_DETECTOR

```java
// TYPE_STEP_DETECTOR – jedan event = jedan korak
else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
    brojKoraka++;
    button.setText("Koraci: " + brojKoraka);
}
```

Potrebno polje `private int brojKoraka = 0;` koje sam uvećavaš.

---

## Checklist

- [ ] `TYPE_STEP_COUNTER` ili `TYPE_STEP_DETECTOR`
- [ ] `ACTIVITY_RECOGNITION` na API 29+
- [ ] Prikaz na Button ili TextView
