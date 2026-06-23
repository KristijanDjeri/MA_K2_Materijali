# Dodatni segmenti – verovatnoća na kolokvijumu

Ovi folderi pokrivaju teme **slične težine i principa** kao zvanični zadaci, ali nisu eksplicitno u PDF-u.

## Novo dodato (17–19, 55–59) – česte varijante na ispitu

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [17-alert-dialog](17-alert-dialog/) | Potvrda pre brisanja | 7, 10 |
| [18-room-update](18-room-update/) | Izmena posta u bazi | 5, 6, 7 |
| [19-fragment-primer](19-fragment-primer/) | Fragment umesto Activity | sve |
| [55-checkbox-radiobutton](55-checkbox-radiobutton/) | UI izbor (pored Switch-a) | 6, 9 |
| [56-implicit-intent](56-implicit-intent/) | Share, SMS, browser, geo | 9, 22 |
| [57-toolbar-options-menu](57-toolbar-options-menu/) | Meni u action bar-u | 5–7 |
| [58-maps-google-osm](58-maps-google-osm/) | Mapa umesto TextView lat/long | 3 |
| [59-file-provider](59-file-provider/) | Kamera pun rez + deljenje slike | 4 |

## Visoka verovatnoća (isti obrazac kao na kolokvijumu)

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [20-recyclerview](20-recyclerview/) | Lista postova iz baze | 5, 6, 7 |
| [21-galerija](21-galerija/) | Izbor slike umesto kamere | 4 |
| [25-thread-executor](25-thread-executor/) | Room bez main thread | 5, 6, 7 |
| [23-retrofit-post](23-retrofit-post/) | Par uz GET | 5 |
| [22-intent-druga-aktivnost](22-intent-druga-aktivnost/) | Prenos podataka | 9 |
| [27-edit-text-validacija](27-edit-text-validacija/) | Unos + provera | 2 |
| [35-progress-bar](35-progress-bar/) | Učitavanje sa API-ja | 5 |

## Srednja verovatnoća

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [42-senzor-magnetometar](42-senzor-magnetometar/) | Još jedan senzor | 4, 8 |
| [34-lokacija-realtime](34-lokacija-realtime/) | Varijanta lokacije | 3 |
| [26-interni-fajl](26-interni-fajl/) | Čuvanje podataka | 9 |
| [32-okhttp-json](32-okhttp-json/) | API bez Retrofit-a | 5 |
| [24-webview](24-webview/) | Prikaz web sadržaja | 5 |
| [28-alarm-notifikacija](28-alarm-notifikacija/) | Zakazana notifikacija | 7 |
| [29-poziv-telefon](29-poziv-telefon/) | Kontakti + Intent | 9 |
| [30-spinner](30-spinner/) | UI izbor | 2 |
| [31-date-picker](31-date-picker/) | Dijalog datum/vreme | 2 |

## Niža verovatnoća (ali brzo za naučiti)

| Folder | Napomena |
|--------|----------|
| [33-povratna-vibracija](33-povratna-vibracija/) | Kratka vibracija uz događaj |
| [36-audio-recorder](36-audio-recorder/) | MediaRecorder + MediaPlayer |
| [43-senzor-svetlosti](43-senzor-svetlosti/) | Lux vrednost |
| [44-senzor-proksimiteta](44-senzor-proksimiteta/) | Blizu/daleko |
| [45-senzor-barometar](45-senzor-barometar/) | Pritisak u hPa |
| [46-senzor-koraci](46-senzor-koraci/) | Koraci + ACTIVITY_RECOGNITION |
| [47-senzor-izvedeni](47-senzor-izvedeni/) | Gravity, rotation vector… |
| [48-senzor-vlage-temperature](48-senzor-vlage-temperature/) | Retko na telefonima |
| [49-senzor-shake](49-senzor-shake/) | Tresenje preko akcelerometra |
| [51-firebase-setup](51-firebase-setup/) | Firebase projekat + Gradle |
| [53-firebase-firestore](53-firebase-firestore/) | Oblak umesto Retrofit/Room |

**Pregled svih senzora:** [41-senzori-pregled](41-senzori-pregled/)  
**Pregled Firebase:** [50-firebase](50-firebase/)  
**Fragment adaptacija:** [90-fragments-prirucnik](90-fragments-prirucnik/) + [19-fragment-primer](19-fragment-primer/)

## Kako učiti

1. Prvo savladaj **zvanične** foldere (geo, 03-kamera, retrofit, switch…)
2. Dodaj **17–19** (alert, update, fragment) i **20-recyclerview**, **21-galerija**
3. **55–59** pročitaj README – pokrivaju najčešće „iznenadne“ varijante
4. Ostalo – dovoljno je znati gde kopirati kod

## Mapa principa

```
Dozvole (runtime)     → geo, 03-kamera, 21-galerija, 14-kontakti, poziv
Senzori               → 04-senzor-ziroskop, 12-senzor-akcelerometar, 42-senzor-magnetometar, 41-senzori-pregled/
Audio                 → 36-audio-recorder/
Mreža + JSON          → retrofit, 23-retrofit-post, 32-okhttp-json, 24-webview
Perzistencija         → room, 18-room-update, 13-shared-preferences, 26-interni-fajl, 53-firebase-firestore/
Firebase              → 51-firebase-setup/, 52-firebase-auth/, 54-firebase-fcm/
UI komponente         → osnovni layout, 55-checkbox-radiobutton, 30-spinner, edit-text, 31-date-picker, 57-toolbar-options-menu
Navigacija            → 22-intent-druga-aktivnost, 56-implicit-intent
Fragmenti             → 19-fragment-primer, 90-fragments-prirucnik
Mape / fajlovi        → 58-maps-google-osm, 59-file-provider, 03-kamera
Dijalozi              → 17-alert-dialog, 31-date-picker
Pozadina              → 25-thread-executor, 35-progress-bar
Obaveštenja           → 11-notifikacija-prazna-baza, 28-alarm-notifikacija
```
