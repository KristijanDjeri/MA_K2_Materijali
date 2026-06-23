# Povratna vibracija (haptic feedback)

**Dodatni segment.** Koristi sistemsku **VIBRATE** dozvolu – kratka vibracija kao povratna informacija (npr. kad nema više postova).

> **Napomena:** Folder se zove `33-povratna-vibracija` (ne „vibrator") – jasniji i neutralniji naziv.

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
| 1 | `MainActivity.java` | Metoda `vibracija()` – **dno klase** |
| 2 | `MainActivity.java` | Poziv unutar `PostRepository.obrisiPrviPost` callback-a kad je baza prazna, ili u `onEmpty` listeneru |

---

## Kompletan kod za `MainActivity.java`

### Importi

```java
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
```

### Metoda

```java
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
```

> U **Fragmentu** koristi `requireContext().getSystemService(...)` umesto `getSystemService`.

### Poziv (npr. kad nema postova)

```java
if (postDao.count() == 0) {
    povratnaVibracijaKratka();
    posaljiNotifikaciju("Nema više postova!");
}
```

---

## Alternativa

- Kraće trajanje: `200` ms
- `VibrationEffect.createWaveform` – uzorak vibracije

---

## Checklist

- [ ] `VIBRATE` u Manifest-u
- [ ] Pozvano na jasan događaj (brisanje, greška, shake…)
