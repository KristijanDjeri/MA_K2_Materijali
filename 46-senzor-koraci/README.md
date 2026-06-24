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

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`KoraciHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `koraciHelper = new KoraciHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `koraciHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `koraciHelper.onPause();` |
| 5 | `MainActivity.java` | **`onRequestPermissionsResult`**: `koraciHelper.onPermissionGranted(...)` |

---

## Kompletan kod – helper klasa

Kopiraj **`KoraciHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.KoraciHelper;

private KoraciHelper koraciHelper;

// onCreate:
koraciHelper = new KoraciHelper(this, textView);

// onResume / onPause:
koraciHelper.onResume();
koraciHelper.onPause();

// onRequestPermissionsResult (proširi postojeći):
if (koraciHelper != null) {
    koraciHelper.onPermissionGranted(requestCode, grantResults);
}
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final int REQ_ACTIVITY = 107;
private Sensor stepCounterSensor;
private boolean koraciRegistrovan = false;

// U onCreate():
stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

// U onResume():
pokreniKorake();

// U onPause():
if (koraciRegistrovan) {
    sensorManager.unregisterListener(this, stepCounterSensor);
    koraciRegistrovan = false;
}

// METODE:

private void pokreniKorake() {
    if (stepCounterSensor == null) return;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQ_ACTIVITY);
            return;
        }
    }
    if (!koraciRegistrovan) {
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        koraciRegistrovan = true;
    }
}

// U onRequestPermissionsResult:
// if (requestCode == REQ_ACTIVITY && grantResults[0] == PERMISSION_GRANTED) pokreniKorake();

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
    textView.setText("Koraci: " + (int) event.values[0]);
}
```

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
