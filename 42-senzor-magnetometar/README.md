# Magnetometar i kompas

**Dodatni segment.** **Slično:** žiroskop i `12-senzor-akcelerometar/`.

**Cilj:** Magnetometar (X,Y,Z) ili kompas (azimut) – **puna logika u helper klasi**, ne u `MainActivity`.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`MagnetometarHelper.java`** ili **`KompasHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | Polje: `private MagnetometarHelper magnetometarHelper;` |
| 3 | `MainActivity.java` | **`onCreate`**, posle `findViewById`: `magnetometarHelper = new MagnetometarHelper(this, textView);` |
| 4 | `MainActivity.java` | **`onResume`**: `magnetometarHelper.onResume();` |
| 5 | `MainActivity.java` | **`onPause`**: `magnetometarHelper.onPause();` |

> **Ne** implementiraj `SensorEventListener` u MainActivity – helper to radi sam.

---

## Kompletan kod – helper klasa

Kopiraj **`MagnetometarHelper.java`** i/ili **`KompasHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## Deo A: Magnetometar – povezivanje u MainActivity

Kopiraj **`MagnetometarHelper.java`** iz ovog foldera.

### MainActivity – samo povezivanje

```java
import com.example.kolokvijum2.helper.MagnetometarHelper;

private MagnetometarHelper magnetometarHelper;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.textView);
    // ... ostali findViewById ...

    magnetometarHelper = new MagnetometarHelper(this, textView);
}

@Override
protected void onResume() {
    super.onResume();
    // drugi helperi (npr. ziroskopHelper.onResume()) ...
    magnetometarHelper.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    magnetometarHelper.onPause();
    // drugi helperi onPause ...
}
```

---

## Deo B: Kompas (azimut)

Koristi **`KompasHelper.java`** – sam registruje akcelerometar **i** magnetometar.

```java
private KompasHelper kompasHelper;

// onCreate:
kompasHelper = new KompasHelper(this, textView);

// onResume / onPause – isto kao gore
kompasHelper.onResume();
kompasHelper.onPause();
```

> **Ne koristi** istovremeno `MagnetometarHelper` i `KompasHelper` na istom TextView.

---

## Više helpera u istom Activity

```java
@Override
protected void onResume() {
    super.onResume();
    ziroskopHelper.onResume();
    akcelerometarHelper.onResume();
    magnetometarHelper.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    ziroskopHelper.onPause();
    akcelerometarHelper.onPause();
    magnetometarHelper.onPause();
}
```

Svaki helper ima **svoj** `SensorEventListener` – ne treba `implements SensorEventListener` na Activity.

---

## Checklist

- [ ] Helper klasa u paketu `helper`
- [ ] `onResume()` / `onPause()` pozvani iz MainActivity
- [ ] TextView prikazuje Mag X/Y/Z ili azimut

---

## Povezano

- Mapa svih helpera: **`HELPER-KLASE.md`**
- Akcelerometar: `12-senzor-akcelerometar/AkcelerometarHelper.java`
