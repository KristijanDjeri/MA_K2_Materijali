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

---

## Kompletan kod – helper klasa

Kopiraj **`AkcelerometarHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.AkcelerometarHelper;
```

### Polje

```java
private AkcelerometarHelper akcelerometarHelper;
```

### U `onCreate`, posle `findViewById`

```java
akcelerometarHelper = new AkcelerometarHelper(this, button);
```

### Lifecycle

```java
@Override
protected void onResume() {
    super.onResume();
    akcelerometarHelper.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    akcelerometarHelper.onPause();
}
```

Brisanje posta: **`onCreate`** → `button.setOnClickListener` (zadatak 10) – odvojeno od senzora u vežbi.

> **Ne** dodaj `implements SensorEventListener` u MainActivity.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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
