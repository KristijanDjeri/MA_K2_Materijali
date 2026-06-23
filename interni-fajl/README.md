# Interni fajl (čuvanje teksta/slike)

**Slično:** SharedPreferences (zadatak 9), ali za veći sadržaj ili fajl na disku.

**Mogući zadatak:** Sačuvaj sadržaj TextView u interni fajl `podaci.txt`. Pri pokretanju učitaj.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Čitanje/pisanje | `MainActivity.java` |
| Fajl | `getFilesDir()/podaci.txt` (privatno za app) |

## API

- `openFileOutput("podaci.txt", MODE_PRIVATE)` – pisanje
- `openFileInput("podaci.txt")` – čitanje
- `FileOutputStream` / `BufferedReader`

## Fajlovi

- `InterniFajlSegment.java`

## Checklist

- [ ] `openFileOutput` + `write`
- [ ] `openFileInput` + čitanje linija
- [ ] Nema posebne dozvole (interni storage)

## Razlika od SharedPreferences

| SharedPreferences | Interni fajl |
|-------------------|--------------|
| ključ-vrednost | proizvoljan tekst/binarno |
| mali podaci | veći sadržaj |
