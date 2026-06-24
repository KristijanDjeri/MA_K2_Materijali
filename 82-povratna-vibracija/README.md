# Povratna vibracija (haptic feedback)

**Dodatni segment.** Koristi sistemsku **VIBRATE** dozvolu – kratka vibracija kao povratna informacija (npr. kad nema više postova).

> **Napomena:** Folder se zove `82-povratna-vibracija` (ne „vibrator") – jasniji i neutralniji naziv.

**Slično:** kratki fizički odziv na događaj (kao zvuk ili Toast).

---

## Manifest

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

(Normalna dozvola – **ne** traži runtime na većini uređaja.)

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`VibracijaHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Kad je baza prazna: `VibracijaHelper.kratka(this)` |

---

## Kompletan kod – helper klasa

Kopiraj **`VibracijaHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.VibracijaHelper;
```

### Poziv (npr. kad nema postova)

```java
VibracijaHelper.kratka(this);
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===
// Priručnik: 33-povratna-vibracija/README.md

// IMPORTI:
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

// METODA:
private void povratnaVibracijaKratka() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        VibratorManager vm = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        vm.getDefaultVibrator().vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }
    }
}

// Pozovi npr. kad nema više postova:
// povratnaVibracijaKratka();
```

## Alternativa

- Kraće trajanje: `200` ms
- `VibrationEffect.createWaveform` – uzorak vibracije

---

## Checklist

- [ ] `VIBRATE` u Manifest-u
- [ ] Pozvano na jasan događaj (brisanje, greška, shake…)
