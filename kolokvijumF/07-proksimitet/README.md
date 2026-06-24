# Senzor proksimiteta (zadatak 8) – vežba F

**Cilj:** U **drugom** TextView-u (`textViewProksimitet`) prikazati očitavanje **proximity** senzora.

---

## Koji fajlovi se dodaju

| Korak | Fajl | Gde |
|-------|------|-----|
| 1 | `ProksimitetHelper.java` | `app/.../helper/` |
| 2 | `MainActivity.java` | init + `onResume` / `onPause` |

---

## MainActivity – povezivanje

```java
import com.example.kolokvijum2.helper.ProksimitetHelper;

private ProksimitetHelper proksimitetHelper;

// onCreate:
proksimitetHelper = new ProksimitetHelper(this, textViewProksimitet);

// onResume:
proksimitetHelper.onResume();

// onPause:
proksimitetHelper.onPause();
```

---

## Šta prikazuje TextView

Helper prikazuje vrednost u centimetrima i tekst **Blizu** / **Daleko**:

```
Blizu (0.0 cm)
```

ili

```
Daleko
```

Dovoljno je da se vidi **brojčana vrednost** senzora – format možeš prilagoditi u helperu:

```java
textView.setText("Proksimitet: " + cm + " cm");
```

---

## Kompletan kod

Kopiraj **`ProksimitetHelper.java`** iz ovog foldera.

---

## Alternativa: inline

```java
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;

    // onCreate:
    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    // onResume:
    if (proximitySensor != null) {
        sensorManager.registerListener(this, proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // onPause:
    sensorManager.unregisterListener(this);

  @Override
  public void onSensorChanged(SensorEvent event) {
      if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
          float cm = event.values[0];
          textViewProksimitet.setText("Proksimitet: " + cm + " cm");
      }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
```

---

## Testiranje

- **Telefon:** pokrij gornji deo ekrana (blizu senzora poziva) → vrednost pada na ~0
- **Emulator:** Extended Controls → **Sensors** → Proximity – pomeri klizač

---

## Checklist

- [ ] `onResume` registruje, `onPause` odjavljuje listener
- [ ] `textViewProksimitet` se ažurira uživo
- [ ] Nema crash-a ako senzor ne postoji (helper proverava `null`)

---

## Sledeći korak

[15-main-activity-referenca/](../15-main-activity-referenca/) – sve spojeno u jednom fajlu.
