# Brzi vodič za kolokvijum (1 strana)

## 1. Novi projekat
- Ime: **Kolokvijum2**, aktivnost: **MainActivity**, minSdk **28**
- Layout: `01-osnovni-projekat/activity_main.xml`
- Gradle: `01-osnovni-projekat/gradle-zavisnosti.txt`
- Dozvole: `01-osnovni-projekat/AndroidManifest-dozvole.xml`

## 2. Zadaci → folder (nezavisni segmenti)

| # | Zahtev | Folder(i) |
|---|--------|-----------|
| 3 | Lat/long u TextView | `02-geo-lokacija/` |
| 4a | Kamera → ImageView | `03-kamera/` |
| 4b | Toast žiroskop | `04-senzor-ziroskop/` |
| 5a | Room model + DAO | `05-room-baza/` |
| 5b | Retrofit GET | `06-retrofit-get/` |
| 6a | Učitaj 10 postova | `07-ucitaj-10-postova/` |
| 6b | Toast title prvog reda | `08-toast-prvi-post/` |
| 6c | Switch ON/OFF | `09-switch-listener/` |
| 7a | Briši prvi post | `10-brisanje-prvog-posta/` |
| 7b | Notifikacija prazna baza | `11-notifikacija-prazna-baza/` |
| 8 | Akcelerometar na dugmetu | `12-senzor-akcelerometar/` |
| 9 | Switch OFF | `13-shared-preferences/` + `14-kontakti/` |

**Spajanje za ispit:** `16-spajanje-zadataka/`

## 3. Redosled učenja

1. Svaki segment **posebno** (dugme/Switch test)
2. Spoji po `16-spajanje-zadataka/`
3. Uporedi sa `15-main-activity-referenca/`

## 4. Runtime dozvole (ne zaboravi!)

- Lokacija → `ACCESS_FINE_LOCATION`
- Kamera → `CAMERA`
- Kontakti → `READ_CONTACTS`
- Notifikacije (API 33+) → `POST_NOTIFICATIONS`

## 5. Kompletan primer

`15-main-activity-referenca/MainActivity.java` – sve spojeno.

## 6. Dodatno (ako profesor promeni zadatak)

| Ako traži… | Folder |
|------------|--------|
| Listu postova | `20-recyclerview/` |
| Galeriju umesto kamere | `21-galerija/` |
| Drugu aktivnost | `22-intent-druga-aktivnost/` |
| POST umesto GET | `23-retrofit-post/` |
| Bazu u pozadini | `25-thread-executor/` |
| Još senzor | `41-senzori-pregled/` → pojedinačni folder |
| Notifikacije (osnovna, akcije…) | `37-notifikacije-pregled/` |
| Audio snimanje | `36-audio-recorder/` |
| Shake / tresenje | `49-senzor-shake/` |
| AlertDialog / Room UPDATE | `17-alert-dialog/`, `18-room-update/` |
| Fragment primer | `19-fragment-primer/` |
| CheckBox / RadioButton | `55-checkbox-radiobutton/` |
| Share, SMS, email | `56-implicit-intent/` |
| Toolbar / meni | `57-toolbar-options-menu/` |
| Mape (OSM / Google) | `58-maps-google-osm/` |
| FileProvider + kamera | `59-file-provider/` |
| Firebase oblak | `50-firebase/` → `51-firebase-setup/` |
| **Fragmenti** | `90-fragments-prirucnik/` |
| Mapa foldera | `KONVENCIJA-FOLDERA.md` |

Detaljnije: `DODATNI-SEGMENTI.md`

## 7. Test pre kolokvijuma

- [ ] Emulator: GPS uključen, lokacija postavljena
- [ ] Bar jedan kontakt u Contacts
- [ ] Internet radi (API)
- [ ] Dozvole odobrene pri prvom pokretanju
