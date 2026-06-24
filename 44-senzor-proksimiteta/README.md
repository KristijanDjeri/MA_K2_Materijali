# Senzor proksimiteta (Proximity)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Detektuj da li je nešto blizu senzora (npr. telefon uz uvo).

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ProksimitetHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `proksimitetHelper = new ProksimitetHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `proksimitetHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `proksimitetHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`ProksimitetHelper.java`** iz ovog foldera u `app/.../helper/`.

Puna implementacija senzora je u helperu – **ne** u MainActivity.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.ProksimitetHelper;

private ProksimitetHelper proksimitetHelper;

// onCreate:
proksimitetHelper = new ProksimitetHelper(this, textView);

// onResume / onPause:
proksimitetHelper.onResume();
proksimitetHelper.onPause();
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// POLJA:
private Sensor proximitySensor;

// U onCreate():
proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

// U onResume():
if (proximitySensor != null) {
    sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
    float cm = event.values[0];
    textView.setText(cm < proximitySensor.getMaximumRange()
            ? "Blizu (" + cm + " cm)" : "Daleko");
}
```

## Checklist

- [ ] Helper u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] TextView prikazuje blizu/daleko

---

## Povezano

- Pregled senzora: `41-senzori-pregled/`
- Helper mapa: `HELPER-KLASE.md`
