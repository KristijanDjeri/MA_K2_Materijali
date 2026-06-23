# Kolokvijum2-test

Automatski generisan Android projekat za **proveru kompajliranja** materijala.

## Build

```bash
export ANDROID_HOME=~/Android/Sdk
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Šta sadrži

- Zvanični `MainActivity` (zadaci 1–9)
- Room + Retrofit (radni API URL)
- Dodatno kompajlirano: ShakeDetector, PostAdapter, DetailActivity, AlarmReceiver, PostFirestore

## Izveštaj

Detalji u [../IZVESTAJ-TESTIRANJA.md](../IZVESTAJ-TESTIRANJA.md)

**Napomena:** Ovaj folder služi za testiranje – na kolokvijum pravi svoj projekat po uputstvima u segment folderima.
