# Vibrator

**Slično:** Senzori / hardver – kratka povratna informacija korisniku.

**Mogući zadatak:** Pri brisanju poslednjeg posta – vibracija 500 ms.

## Manifest

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

(Normalno dozvola, ne traži runtime.)

## Fajlovi

- `VibratorSegment.java`

## Checklist

- [ ] `Vibrator` servis ili `VibratorManager` (API 31+)
- [ ] `vibrate(VibrationEffect.createOneShot(500, DEFAULT_AMPLITUDE))`
