# MainActivity – referenca (sve u jednom)

Kompletan primer `MainActivity.java` koji spaja sve segmente iz ostalih foldera.

## Kako koristiti

1. Napravi projekat po uputstvu u `osnovni-projekat/`
2. Kopiraj fajlove iz `retrofit-room/` u odgovarajuće pakete
3. Zameni package name u svim fajlovima ako nije `com.example.kolokvijum2`
4. Kopiraj `MainActivity.java` iz ovog foldera
5. Uporedi sa segmentima ako nešto fali

## Mapiranje zadataka → metode

| Zadatak | Metoda / deo koda |
|---------|-------------------|
| 3 Lokacija | `pokreniLokaciju()`, `ucitajLokaciju()` |
| 4 Kamera | `pokreniKameru()`, `takePictureLauncher` |
| 4 Žiroskop | `prikaziZiroskopToast()`, `onSensorChanged` (GYRO) |
| 5 Baza + API | `AppDatabase`, `RetrofitClient` |
| 6 Switch ON | `obradiSwitchOn()`, `ucitajPostoveSaApi()` |
| 7 Button | `obrisiPrviPost()`, `posaljiNotifikaciju()` |
| 8 Akcelerometar | `onSensorChanged` (ACCEL) → `button.setText` |
| 9 Switch OFF | `obradiSwitchOff()`, SharedPreferences + kontakti |

## Fajlovi

- `MainActivity.java` – kompletna aktivnost
