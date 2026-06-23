# Brzi vodič za kolokvijum (1 strana)

## 1. Novi projekat
- Ime: **Kolokvijum2**, aktivnost: **MainActivity**, minSdk **28**
- Layout: `osnovni-projekat/activity_main.xml`
- Gradle: `osnovni-projekat/gradle-zavisnosti.txt`
- Dozvole: `osnovni-projekat/AndroidManifest-dozvole.xml`

## 2. Zadaci → folder

| # | Zahtev | Folder |
|---|--------|--------|
| 3 | Lat/long u TextView | `geo-lokacija/` |
| 4a | Kamera → ImageView | `kamera/` |
| 4b | Toast žiroskop pri novoj slici | `ziroskop/` |
| 5 | Room + Retrofit GET | `retrofit-room/` |
| 6 | Switch ON: 10 postova / Toast title | `switch-postovi/` |
| 7 | Button: briši prvi, notifikacija | `brisanje-notifikacije/` |
| 8 | Button tekst = akcelerometar | `akcelerometar/` |
| 9 | Switch OFF: SharedPrefs + kontakt | `shared-preferences/` + `kontakti/` |

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

`main-activity-referenca/MainActivity.java` – sve spojeno.

## 6. Dodatno (ako profesor promeni zadatak)

| Ako traži… | Folder |
|------------|--------|
| Listu postova | `recyclerview/` |
| Galeriju umesto kamere | `galerija/` |
| Drugu aktivnost | `intent-druga-aktivnost/` |
| POST umesto GET | `retrofit-post/` |
| Bazu u pozadini | `thread-executor/` |
| Još senzor | `senzori-pregled/` → pojedinačni folder |
| Audio snimanje | `audio-recorder/` |

Detaljnije: `DODATNI-SEGMENTI.md`

## 7. Test pre kolokvijuma

- [ ] Emulator: GPS uključen, lokacija postavljena
- [ ] Bar jedan kontakt u Contacts
- [ ] Internet radi (API)
- [ ] Dozvole odobrene pri prvom pokretanju
