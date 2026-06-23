# Žiroskop (zadatak 4 – deo 2)

**Cilj:** Svaki put kad se slika u `ImageView` **zameni**, prikaži **Toast** sa očitanjem žiroskopa po **X, Y, Z** osi.

---

## Šta ti treba pre ovoga

- Folder `03-kamera/` – u callback-u kamere pozivaš `prikaziZiroskopToast()`
- `MainActivity` mora implementirati `SensorEventListener`

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | Senzor, listener, Toast metoda, onResume/onPause |

---

## Kompletan kod za `MainActivity.java` (deo za žiroskop)

### 1. Importi

```java
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
```

### 2. Promeni deklaraciju klase

**Umesto:**
```java
public class MainActivity extends AppCompatActivity {
```

**Stavi:**
```java
public class MainActivity extends AppCompatActivity implements SensorEventListener {
```

### 3. Polja (unutar klase)

```java
private SensorManager sensorManager;
private Sensor gyroscope;
private float gyroX, gyroY, gyroZ;
```

### 4. U `onCreate`, posle `findViewById`

```java
sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
```

### 5. Metode životnog ciklusa

```java
@Override
protected void onResume() {
    super.onResume();
    if (gyroscope != null) {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
}

@Override
protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
}
```

### 6. Callback senzora

```java
@Override
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        gyroX = event.values[0];
        gyroY = event.values[1];
        gyroZ = event.values[2];
    }
}

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // prazno – mora postojati jer implementiraš interfejs
}
```

### 7. Toast metoda (poziva se iz kamere)

```java
private void prikaziZiroskopToast() {
    String poruka = "Žiroskop X: " + gyroX + ", Y: " + gyroY + ", Z: " + gyroZ;
    Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show();
}
```

### 8. U launcher-u kamere (već bi trebalo da imaš)

```java
private final ActivityResultLauncher<Void> takePictureLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                prikaziZiroskopToast(); // OVO MORA BITI OVDE
            }
        });
```

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Čuvanje X,Y,Z u poljima | Toast direktno u `onSensorChanged` – ali bi se prikazivao stalno, ne samo pri slici |
| `SENSOR_DELAY_NORMAL` | `SENSOR_DELAY_FASTEST` – brže očitavanje |
| Jedan `SensorEventListener` | Odvojeni listener samo za žiroskop – čistije ako imaš više senzora |

> **Važno:** Kad dodaš **08-senzor-akcelerometar** (`08-senzor-akcelerometar/`), u istom `onSensorChanged` dodaješ `else if` za `TYPE_ACCELEROMETER`. Ne pravi dva listenera osim ako moraš.

---

## Checklist

- [ ] Klasa implementira `SensorEventListener`
- [ ] Žiroskop registrovan u `onResume`
- [ ] Odjavljen u `onPause`
- [ ] `prikaziZiroskopToast()` pozvan posle `setImageBitmap`
- [ ] Toast prikazuje X, Y, Z

---

## Napomena za emulator

Emulator često **nema** žiroskop. Na fizičkom telefonu radi pouzdano. Za kolokvijum je dovoljno da kod postoji i da Toast iskoči (vrednosti mogu biti 0 na emulatoru).

---

## Sledeći korak

Folder **`05-retrofit-room/`** za zadatak 5 (baza + API).
