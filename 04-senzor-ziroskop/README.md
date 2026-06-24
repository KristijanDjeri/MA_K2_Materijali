# Žiroskop (zadatak 4 – deo 2)

**Cilj:** Prikaži **Toast** sa očitanjem žiroskopa po **X, Y, Z** osi.

Na ispitu: Toast posle nove slike u `ImageView` (spajanje sa `03-kamera/` u `16-spajanje-zadataka/`).

**Samostalna vežba:** Testiraj Toast na **dugme** – bez kamere.

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – layout
- `ZiroskopHelper` – **ne** dodaj `implements SensorEventListener` u MainActivity

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

## Kompletan kod – helper klasa

Kopiraj **`ZiroskopHelper.java`** iz ovog foldera u `app/.../helper/ZiroskopHelper.java`.

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

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

// MainActivity implementira SensorEventListener:
// public class MainActivity extends AppCompatActivity implements SensorEventListener {

// POLJA:
private SensorManager sensorManager;
private Sensor gyroscope;
private float gyroX, gyroY, gyroZ;

// U onCreate():
sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

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
    // prazno
}

private void prikaziZiroskopToast() {
    String poruka = "Žiroskop X: " + gyroX + ", Y: " + gyroY + ", Z: " + gyroZ;
    Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show();
}
```

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| Čuvanje X,Y,Z u poljima | Toast direktno u `onSensorChanged` – ali bi se prikazivao stalno, ne samo pri slici |
| `SENSOR_DELAY_NORMAL` | `SENSOR_DELAY_FASTEST` – brže očitavanje |
| Jedan `SensorEventListener` | Odvojeni listener samo za žiroskop – čistije ako imaš više senzora |

> **Važno:** Kad dodaš **12-senzor-akcelerometar/**, u istom `onSensorChanged` dodaješ `else if` za `TYPE_ACCELEROMETER`.

---

## Checklist

- [ ] `ZiroskopHelper` u paketu `helper`
- [ ] Žiroskop registrovan u `onResume` (preko helpera)
- [ ] Odjavljen u `onPause` (preko helpera)
- [ ] `ziroskopHelper.prikaziToast()` radi (dugme ili posle kamere)
- [ ] Toast prikazuje X, Y, Z

---

## Napomena za emulator

Emulator često **nema** žiroskop. Na fizičkom telefonu radi pouzdano. Za kolokvijum je dovoljno da kod postoji i da Toast iskoči (vrednosti mogu biti 0 na emulatoru).

---

## Sledeći korak

Folder **`05-room-baza/`** za zadatak 5 (Room model).
