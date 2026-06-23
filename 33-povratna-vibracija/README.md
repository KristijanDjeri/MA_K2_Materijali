# Vibrator

**Dodatni segment.** Kratka povratna informacija (npr. kad nema više postova).

---

## Manifest

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

(Normalna dozvola – **ne** traži se u runtime.)

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
private void vibracijaKratka() {
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

### Poziv u `obrisiPrviPost` kad je count == 0

```java
if (postDao.count() == 0) {
    vibracijaKratka();
    posaljiNotifikaciju("Nema više postova!");
}
```

---

## Alternativa

- Kraće trajanje: `200` ms umesto `500`
- `VibrationEffect.createWaveform` – pattern vibracije

---

## Checklist

- [ ] VIBRATE u Manifest-u
- [ ] vibracijaKratka() pozvana na događaj
