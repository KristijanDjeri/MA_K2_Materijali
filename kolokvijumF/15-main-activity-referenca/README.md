# MainActivity referenca – vežba F (sve spojeno)

Kompletan primer koji spaja **zadatke 1–8** u jednom `MainActivity.java`.

Uporedi svoj kod sa fajlom **`MainActivity.java`** u ovom folderu (Android Studio → Compare With).

---

## Mapa inicijalizacije

```
onCreate()
  ├── findViewById (5 komponenti)
  ├── geoHelper → textViewLokacija (zadatak 3)
  ├── audioHelper → buttonSnimi + checkBoxSnimanje (zadatak 4)
  ├── countryDao + countryRepository (zadaci 5–7)
  ├── checkBoxDrzave listener (zadaci 6–7)
  └── proksimitetHelper → textViewProksimitet (zadatak 8)

onResume()  → proksimitetHelper.onResume()
onPause()   → audioHelper.onPause(), proksimitetHelper.onPause()
onDestroy() → audioHelper.onDestroy()
onRequestPermissionsResult → geo + audio
```

---

## Fajlovi koje moraš imati u projektu

| Paket | Fajlovi |
|-------|---------|
| `helper` | `GeoLokacijaHelper`, `AudioSnimanjeHelper`, `CountryRepository`, `ProksimitetHelper` |
| `model` | `Country` |
| `db` | `CountryDao`, `AppDatabase` |
| `api` | `DummyJsonApi`, `RetrofitClient` |
| `res/layout` | `activity_main.xml` iz `01-osnovni-projekat/` |

---

## Redosled kopiranja (ako gradiš od nule)

1. `01-osnovni-projekat/` – layout + kostur
2. Helperi i modeli iz foldera `02`–`07`
3. Zameni `MainActivity.java` ovim primerom **ili** dodaj blokove jedan po jedan

---

## Checklist – sve spojeno

- [ ] Projekat Kolokvijum2, MainActivity
- [ ] UI: 2 CheckBox, Button Snimi, 2 TextView
- [ ] Lokacija u prvom TextView-u
- [ ] Snimi disabled tokom snimanja; CheckBox stop + files/
- [ ] Country u Room + Retrofit GET
- [ ] Prvi ček Države → sve u bazu
- [ ] Naredni čekovi → briši poslednju + Toast count
- [ ] Proksimitet u drugom TextView-u
