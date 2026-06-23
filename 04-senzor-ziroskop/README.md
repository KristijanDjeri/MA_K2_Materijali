# Žiroskop (zadatak 4 – deo 2)

**Cilj:** Prikaži **Toast** sa očitanjem žiroskopa po **X, Y, Z** osi.

Na ispitu: Toast posle nove slike u `ImageView` (spajanje sa `03-kamera/` u `16-spajanje-zadataka/`).

**Samostalna vežba:** Testiraj Toast na **dugme** – bez kamere.

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – layout
- `MainActivity` implementira `SensorEventListener`

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ZiroskopHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje: `private ZiroskopHelper ziroskopHelper;` |
| 3 | `MainActivity.java` | **`onCreate`**: `ziroskopHelper = new ZiroskopHelper(this);` |
| 4 | `MainActivity.java` | **`onResume`**: `ziroskopHelper.onResume();` |
| 5 | `MainActivity.java` | **`onPause`**: `ziroskopHelper.onPause();` |
| 6 | Kamera (opciono) | U `KameraHelper` callback: `ziroskopHelper.prikaziToast();` |

> **Ne** dodaj `implements SensorEventListener` u MainActivity.

---

## MainActivity – povezivanje (helper)

```java
import com.example.kolokvijum2.helper.ZiroskopHelper;

private ZiroskopHelper ziroskopHelper;

// onCreate, posle findViewById:
ziroskopHelper = new ZiroskopHelper(this);

// onResume:
ziroskopHelper.onResume();

// onPause:
ziroskopHelper.onPause();

// posle kamere (KameraHelper listener):
ziroskopHelper.prikaziToast();
```

---

## Kompletan kod za `MainActivity.java` (inline varijanta – zastarelo)

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

### 8a. Samostalni test (bez kamere)

```java
button.setOnClickListener(v -> prikaziZiroskopToast());
```

### 8b. Na ispitu – posle kamere (`16-spajanje-zadataka/`)

U callback-u iz `03-kamera/`, posle `setImageBitmap`:

```java
prikaziZiroskopToast();
```

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Čuvanje X,Y,Z u poljima | Toast direktno u `onSensorChanged` – ali bi se prikazivao stalno, ne samo pri slici |
| `SENSOR_DELAY_NORMAL` | `SENSOR_DELAY_FASTEST` – brže očitavanje |
| Jedan `SensorEventListener` | Odvojeni listener samo za žiroskop – čistije ako imaš više senzora |

> **Važno:** Kad dodaš **12-senzor-akcelerometar/**, u istom `onSensorChanged` dodaješ `else if` za `TYPE_ACCELEROMETER`.

---

## Checklist

- [ ] Klasa implementira `SensorEventListener`
- [ ] Žiroskop registrovan u `onResume`
- [ ] Odjavljen u `onPause`
- [ ] `prikaziZiroskopToast()` radi (dugme ili posle kamere)
- [ ] Toast prikazuje X, Y, Z

---

## Napomena za emulator

Emulator često **nema** žiroskop. Na fizičkom telefonu radi pouzdano. Za kolokvijum je dovoljno da kod postoji i da Toast iskoči (vrednosti mogu biti 0 na emulatoru).

---

## Sledeći korak

Folder **`05-room-baza/`** za zadatak 5 (Room model).
