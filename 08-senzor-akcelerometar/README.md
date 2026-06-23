# Akcelerometar (zadatak 8)

**Cilj:** Tekst na `Button` prikazuje vrednosti akcelerometra u **realnom vremenu** (X, Y, Z).

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – `button` u layoutu
- `04-senzor-ziroskop/` – već imaš `SensorManager`, `SensorEventListener`, `onResume`/`onPause`

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | Dodaješ 08-senzor-akcelerometar u postojeći senzor kod |

> **Pažnja:** Isti `button` koristiš i za **brisanje posta** (zadatak 7). Oba `setOnClickListener` ne možeš pozvati – spoji logiku u jedan listener ili koristi različita dugmad. Primer ispod zadržava brisanje na klik.

---

## Kompletan kod za `MainActivity.java` (deo za 08-senzor-akcelerometar)

### 1. Importi (većina već postoji iz žiroskopa)

```java
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
```

### 2. Polje (pored `gyroscope`)

```java
private Sensor accelerometer;
```

### 3. U `onCreate`, posle inicijalizacije žiroskopa

```java
accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
```

### 4. U `onResume` dodaj registraciju (pored žiroskopa)

```java
@Override
protected void onResume() {
    super.onResume();
    if (gyroscope != null) {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
    if (accelerometer != null) {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
```

### 5. Proširi `onSensorChanged`

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

### 6. Klik na dugme (brisanje posta – zadatak 7)

Tekst se menja iz senzora, ali klik i dalje briše post. **Jedan** listener:

```java
button.setOnClickListener(v -> obrisiPrviPost());
```

Metodu `obrisiPrviPost()` dodaješ iz foldera `07-brisanje-notifikacije/`.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Tekst na `Button` | Tekst u `TextView` – ako profesor dozvoli |
| `setText` u `onSensorChanged` | Ažuriranje na svakih N ms – ređe osvežavanje |
| Isti listener za 2 senzora | Dva odvojena `SensorEventListener` objekta |

---

## Checklist

- [ ] `accelerometer` inicijalizovan
- [ ] Registrovan u `onResume`
- [ ] `onSensorChanged` ažurira tekst dugmeta
- [ ] Klik na dugme i dalje radi brisanje (ako je zadatak 7 urađen)

---

## Sledeći korak

Ako nisi – folder **`07-brisanje-notifikacije/`** za zadatak 7.
