# Brzi vodič za kolokvijum (1 strana)

## 1. Novi projekat
- Ime: **Kolokvijum2**, aktivnost: **MainActivity**, minSdk **28**
- Layout: `01-osnovni-projekat/activity_main.xml`
- Gradle: `01-osnovni-projekat/gradle-zavisnosti.txt`
- Dozvole: `01-osnovni-projekat/AndroidManifest-dozvole.xml`

## 2. Zadaci → folder

| # | Zahtev | Folder |
|---|--------|--------|
| 3 | Lat/long u TextView | `02-geo-lokacija/` |
| 4a | Kamera → ImageView | `03-kamera/` |
| 4b | Toast žiroskop pri novoj slici | `04-senzor-ziroskop/` |
| 5 | Room + Retrofit GET | `05-retrofit-room/` |
| 6 | Switch ON: 10 postova / Toast title | `06-switch-postovi/` |
| 7 | Button: briši prvi, notifikacija | `07-brisanje-notifikacije/` |
| 8 | Button tekst = akcelerometar | `08-senzor-akcelerometar/` |
| 9 | Switch OFF: SharedPrefs + kontakt | `09-shared-preferences/` + `10-kontakti/` |

## 3. Redosled kopiranja u MainActivity

1. Polja + findViewById
2. Baza: `AppDatabase.getInstance(this).postDao()`
3. Geolokacija
4. Kamera + žiroskop
5. Switch listener (ON i OFF)
6. Button click (brisanje)
7. Senzori u onResume/onPause/onSensorChanged

## 4. Runtime dozvole (ne zaboravi!)

- Lokacija → `ACCESS_FINE_LOCATION`
- Kamera → `CAMERA`
- Kontakti → `READ_CONTACTS`
- Notifikacije (API 33+) → `POST_NOTIFICATIONS`

## 5. Kompletan primer

`11-main-activity-referenca/MainActivity.java` – sve spojeno.

## 6. Dodatno (ako profesor promeni zadatak)

| Ako traži… | Folder |
|------------|--------|
| Listu postova | `20-recyclerview/` |
| Galeriju umesto kamere | `21-galerija/` |
| Drugu aktivnost | `22-intent-druga-aktivnost/` |
| POST umesto GET | `23-retrofit-post/` |
| Bazu u pozadini | `25-thread-executor/` |
| Još senzor | `40-senzori-pregled/` → pojedinačni folder |
| Audio snimanje | `36-audio-recorder/` |
| Shake / tresenje | `48-senzor-shake/` |
| Firebase oblak | `50-firebase/` → `51-firebase-setup/` |
| **Fragmenti** | `90-fragments-prirucnik/` |
| Mapa foldera | `KONVENCIJA-FOLDERA.md` |

Detaljnije: `DODATNI-SEGMENTI.md`

## 7. Test pre kolokvijuma

- [ ] Emulator: GPS uključen, lokacija postavljena
- [ ] Bar jedan kontakt u Contacts
- [ ] Internet radi (API)
- [ ] Dozvole odobrene pri prvom pokretanju
