# Shake senzor (detekcija tresenja)

**Dodatni segment.** **Ne postoji** poseban `Sensor.TYPE_SHAKE` – tresenje se detektuje pomoću **akcelerometra** koji već koristiš u zadatku 8.

**Cilj:** Kada korisnik protrese telefon → Toast „Tresenje detektovano!" (ili neka druga akcija).

---

## Kako radi (ukratko)

1. Akcelerometar šalje X, Y, Z u `onSensorChanged`
2. Izračunaš ukupno ubrzanje: `√(x² + y² + z²)`
3. Oduzmeš gravitaciju (~9.81) da dobiješ „shake jačinu"
4. Ako pređe **prag** i prošlo je dovoljno vremena od prošlog tresenja → **shake**

> **Alternativa:** Koristi `TYPE_LINEAR_ACCELERATION` – gravitacija je već oduzeta, jednostavniji prag.

---

## Preduslovi

- `04-senzor-ziroskop/` i `08-senzor-akcelerometar/` – već imaš `SensorManager`, `SensorEventListener`, 08-senzor-akcelerometar u `onResume`

---

## Kompletan kod za `MainActivity.java`

### 1. Importi (većina postoji)

```java
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;
```

### 2. Konstante i polja

```java
private static final float SHAKE_THRESHOLD = 12.0f;   // prag – podesi po osećaju
private static final int SHAKE_COOLDOWN_MS = 1000;      // min razmak između shake-ova

private long poslednjiShakeVreme = 0;
```

> **Napomena:** `SHAKE_THRESHOLD = 12.0f` radi sa običnim akcelerometrom (posle oduzimanja gravitacije). Za `LINEAR_ACCELERATION` koristi manji prag, npr. `4.0f`.

### 3. U `onSensorChanged` – zameni ili dopuni deo za 08-senzor-akcelerometar

**Varijanta A – običan 08-senzor-akcelerometar (preporučeno, već ga imaš):**

```java
else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
    float x = event.values[0];
    float y = event.values[1];
    float z = event.values[2];

    // Tekst dugmeta (zadatak 8) – može ostati
    button.setText("X: " + x + " Y: " + y + " Z: " + z);

    // Shake detekcija
    detektujShake(x, y, z);
}
```

### 4. Metoda za detekciju tresenja

```java
private void detektujShake(float x, float y, float z) {
    float ubrzanje = (float) Math.sqrt(x * x + y * y + z * z);
    float delta = Math.abs(ubrzanje - SensorManager.GRAVITY_EARTH);

    if (delta > SHAKE_THRESHOLD) {
        long sada = System.currentTimeMillis();
        if (sada - poslednjiShakeVreme > SHAKE_COOLDOWN_MS) {
            poslednjiShakeVreme = sada;
            onShakeDetektovan();
        }
    }
}

private void onShakeDetektovan() {
    Toast.makeText(this, "Tresenje detektovano!", Toast.LENGTH_SHORT).show();
    // Opciono: vibracija, osveži listu, obriši post...
    // vibracijaKratka(); // iz foldera 33-povratna-vibracija/
}
```

---

## Varijanta B – Linear Acceleration (alternativa)

Ako registruješ `TYPE_LINEAR_ACCELERATION` (folder `47-senzor-izvedeni/`):

```java
private Sensor linearAccelSensor;

// onCreate:
linearAccelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

// onResume:
if (linearAccelSensor != null) {
    sensorManager.registerListener(this, linearAccelSensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// onSensorChanged:
else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
    float x = event.values[0];
    float y = event.values[1];
    float z = event.values[2];
    float sila = (float) Math.sqrt(x * x + y * y + z * z);
    if (sila > 4.0f) { // manji prag jer nema gravitacije
        long sada = System.currentTimeMillis();
        if (sada - poslednjiShakeVreme > SHAKE_COOLDOWN_MS) {
            poslednjiShakeVreme = sada;
            onShakeDetektovan();
        }
    }
}
```

> **Napomena:** Na nekim uređajima `LINEAR_ACCELERATION` ne postoji – Varijanta A je pouzdanija.

---

## Varijanta C – Odvojena klasa (čistiji kod, opciono)

Kreiraj `ShakeDetector.java`:

```java
package com.example.kolokvijum2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class ShakeDetector {

    public interface OnShakeListener {
        void onShake();
    }

    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int SHAKE_COOLDOWN_MS = 1000;

    private final OnShakeListener listener;
    private long poslednjiShakeVreme = 0;

    public ShakeDetector(OnShakeListener listener) {
        this.listener = listener;
    }

    public void obradiSensorEvent(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float ubrzanje = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = Math.abs(ubrzanje - SensorManager.GRAVITY_EARTH);

        if (delta > SHAKE_THRESHOLD) {
            long sada = System.currentTimeMillis();
            if (sada - poslednjiShakeVreme > SHAKE_COOLDOWN_MS) {
                poslednjiShakeVreme = sada;
                listener.onShake();
            }
        }
    }
}
```

U `MainActivity`:

```java
private ShakeDetector shakeDetector;

// onCreate:
shakeDetector = new ShakeDetector(() ->
        Toast.makeText(this, "Tresenje detektovano!", Toast.LENGTH_SHORT).show()
);

// onSensorChanged (08-senzor-akcelerometar):
shakeDetector.obradiSensorEvent(event);
```

---

## Primer: shake briše prvi post (kombinacija sa zadatkom 7)

```java
private void onShakeDetektovan() {
    Toast.makeText(this, "Shake – brišem prvi post", Toast.LENGTH_SHORT).show();
    obrisiPrviPost(); // iz 07-brisanje-notifikacije/
}
```

---

## Podešavanje osetljivosti

| Vrednost | Efekat |
|----------|--------|
| Manji `SHAKE_THRESHOLD` (npr. 8) | Osetljivije – lakše okine |
| Veći `SHAKE_THRESHOLD` (npr. 15) | Treba jače tresenje |
| Manji `SHAKE_COOLDOWN_MS` | Češći shake eventi |
| `SENSOR_DELAY_GAME` | Brže očitavanje – bolje za shake |

U `onResume` za shake možeš koristiti brži delay:

```java
sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
```

> **Pažnja:** Brži delay troši više baterije. Za kolokvijum je `SENSOR_DELAY_NORMAL` dovoljan.

---

## Testiranje

1. Pokreni app na **fizičkom telefonu** (emulator teško simulira shake)
2. Protresi telefon
3. Treba da iskoči Toast

Na emulatoru: Extended Controls → Sensors → pomeri vrednosti akcelerometra naglo.

---

## Checklist

- [ ] Akcelerometar registrovan u `onResume`
- [ ] `detektujShake()` pozvan iz `onSensorChanged`
- [ ] Prag + cooldown da ne okida 100 puta u sekundi
- [ ] `onShakeDetektovan()` radi željenu akciju

---

## Česte greške

| Problem | Rešenje |
|---------|---------|
| Ništa se ne dešava | Smanji `SHAKE_THRESHOLD` ili tresi jače |
| Toast non-stop | Povećaj `SHAKE_COOLDOWN_MS` ili prag |
| Konflikt sa tekstom na Button | Oba mogu raditi – shake je u istom `onSensorChanged` |

---

## Sledeći korak

Poveži sa drugim segmentima po potrebi: `33-povratna-vibracija/`, `07-brisanje-notifikacije/`, `06-switch-postovi/`.
