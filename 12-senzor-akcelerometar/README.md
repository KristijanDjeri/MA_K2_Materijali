# Akcelerometar (zadatak 8)

**Cilj:** Tekst na `Button` prikazuje vrednosti akcelerometra u **realnom vremenu** (X, Y, Z).

Radi **samostalno** – samo senzor i `setText` na dugmetu. **Brisanje posta** je u `10-brisanje-prvog-posta/`; na ispitu se spaja u `16-spajanje-zadataka/`.

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – `button` u layoutu
- `04-senzor-ziroskop/ZiroskopHelper` – oba helpera, **bez** `SensorEventListener` u Activity

---

## Gde nalepiti kod (helper)

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`AkcelerometarHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `akcelerometarHelper = new AkcelerometarHelper(this, button);` |
| 3 | `MainActivity.java` | **`onResume`**: `akcelerometarHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `akcelerometarHelper.onPause();` |

```java
import com.example.kolokvijum2.helper.AkcelerometarHelper;

private AkcelerometarHelper akcelerometarHelper;
```

Brisanje posta: **`onCreate`** → `button.setOnClickListener` (zadatak 10) – odvojeno od senzora u vežbi.

---

## Kompletan kod – helper klasa

Kopiraj **`AkcelerometarHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

Vidi tabelu iznad i lifecycle u `onResume` / `onPause`.

---

## Alternativa: inline u `MainActivity.java` (zastarelo)

Ako nemaš helper, klasa implementira `SensorEventListener` u MainActivity.

### 1. Importi

```java
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
```

### 2. Polja

```java
private SensorManager sensorManager;
private Sensor accelerometer;
```

### 3. U `onCreate`

```java
sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
```

### 4. Životni ciklus

```java
@Override
protected void onResume() {
    super.onResume();
    if (accelerometer != null) {
        sensorManager.registerListener(sensorListener, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}

@Override
protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(sensorListener);
}
```

### 5. Listener (odvojen ili `this` ako već imaš žiroskop)

```java
private final SensorEventListener sensorListener = new SensorEventListener() {
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            button.setText("X: " + ax + " Y: " + ay + " Z: " + az);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
};
```

> Ako već imaš žiroskop u istoj klasi, u **jednom** `onSensorChanged` dodaj `else if` za `TYPE_ACCELEROMETER` – ne pravi drugi listener bez potrebe.

### 6. Bez klika na dugme u ovom koraku

**Ne** dodaj `setOnClickListener` ovde. Brisanje testiraš u `10-brisanje-prvog-posta/`.

---

## Ako imaš i žiroskop (zadatak 4)

```java
@Override
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        gyroX = event.values[0];
        gyroY = event.values[1];
        gyroZ = event.values[2];
    } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        button.setText("X: " + ax + " Y: " + ay + " Z: " + az);
    }
}
```

U `onResume` registruj **oba** senzora.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Tekst na `Button` | `TextView` pored dugmeta |
| `setText` u svakom eventu | Throttle – ređe osvežavanje |

---

## Checklist

- [ ] Akcelerometar registrovan u `onResume`
- [ ] Odjavljen u `onPause`
- [ ] Tekst dugmeta se menja u realnom vremenu
- [ ] Bez brisanja u ovom koraku

---

## Sledeći korak

Spajanje dugmeta (klik + senzor): **`16-spajanje-zadataka/`**  
Zadatak 9: **`13-shared-preferences/`** + **`14-kontakti/`**
