# Dodatni segmenti – verovatnoća na kolokvijumu

Ovi folderi pokrivaju teme **slične težine i principa** kao zvanični zadaci, ali nisu eksplicitno u PDF-u.

**Grafičke komponente** su u **60–68** (`ui-` prefiks).  
**Logika / podaci** u **70–79**. **Servisi** u **80–85**. **Senzori** u **41–49**.

---

## Novo dodato (17–19, 66–67, 71, 77, 79) – česte varijante na ispitu

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [17-alert-dialog](17-alert-dialog/) | Potvrda pre brisanja | 7, 10 |
| [18-room-update](18-room-update/) | Izmena posta u bazi | 5, 6, 7 |
| [19-fragment-primer](19-fragment-primer/) | Fragment umesto Activity | sve |
| [66-ui-checkbox-radiobutton](66-ui-checkbox-radiobutton/) | UI izbor (pored Switch-a) | 6, 9 |
| [71-implicit-intent](71-implicit-intent/) | Share, SMS, browser, geo | 9, 70 |
| [67-ui-toolbar-options-menu](67-ui-toolbar-options-menu/) | Meni u action bar-u | 5–7 |
| [79-maps-google-osm](79-maps-google-osm/) | Mapa umesto TextView lat/long | 3 |
| [77-file-provider](77-file-provider/) | Kamera pun rez + deljenje slike | 4 |

## Visoka verovatnoća (isti obrazac kao na kolokvijumu)

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [60-ui-recyclerview](60-ui-recyclerview/) | Lista postova iz baze | 5, 6, 7 |
| [76-galerija](76-galerija/) | Izbor slike umesto kamere | 4 |
| [74-thread-executor](74-thread-executor/) | Room bez main thread | 5, 6, 7 |
| [72-retrofit-post](72-retrofit-post/) | Par uz GET | 5 |
| [70-intent-druga-aktivnost](70-intent-druga-aktivnost/) | Prenos podataka | 9 |
| [62-ui-edit-text-validacija](62-ui-edit-text-validacija/) | Unos + provera | 2 |
| [65-ui-progress-bar](65-ui-progress-bar/) | Učitavanje sa API-ja | 5 |
| [68-ui-media-player](68-ui-media-player/) | Slika, video, audio UI | 2, 4 |

## Srednja verovatnoća

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [42-senzor-magnetometar](42-senzor-magnetometar/) | Još jedan senzor | 4, 8 |
| [78-lokacija-realtime](78-lokacija-realtime/) | Varijanta lokacije | 3 |
| [75-interni-fajl](75-interni-fajl/) | Čuvanje podataka | 9 |
| [73-okhttp-json](73-okhttp-json/) | API bez Retrofit-a | 5 |
| [61-ui-webview](61-ui-webview/) | Prikaz web sadržaja | 5 |
| [80-alarm-notifikacija](80-alarm-notifikacija/) | Zakazana notifikacija | 7 |
| [81-poziv-telefon](81-poziv-telefon/) | Kontakti + Intent | 9 |
| [63-ui-spinner](63-ui-spinner/) | UI izbor | 2 |
| [64-ui-date-picker](64-ui-date-picker/) | Dijalog datum/vreme | 2 |

## Niža verovatnoća (ali brzo za naučiti)

| Folder | Napomena |
|--------|----------|
| [82-povratna-vibracija](82-povratna-vibracija/) | Kratka vibracija uz događaj |
| [83-audio-recorder](83-audio-recorder/) | MediaRecorder + MediaPlayer |
| [84-content-provider](84-content-provider/) | Sopstveni ContentProvider (Room → content://) |
| [85-broadcast-receiver](85-broadcast-receiver/) | BroadcastReceiver (sendBroadcast) |
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
2. Dodaj **17–19** (alert, update, fragment) i **60-ui-recyclerview**, **76-galerija**
3. **66–67, 71, 77, 79** pročitaj README – pokrivaju najčešće „iznenadne“ varijante
4. Ostalo – dovoljno je znati gde kopirati kod

## Mapa principa

```
Dozvole (runtime)     → geo, 03-kamera, 76-galerija, 14-kontakti, 81-poziv-telefon
Senzori               → 04-senzor-ziroskop, 12-senzor-akcelerometar, 42-senzor-magnetometar, 41-senzori-pregled/
Audio                 → 83-audio-recorder/
Mreža + JSON          → retrofit, 72-retrofit-post, 73-okhttp-json, 61-ui-webview
Perzistencija         → room, 18-room-update, 13-shared-preferences, 75-interni-fajl, InterniFajlHelper
Helper klase          → HELPER-KLASE.md (logika van MainActivity)
Firebase              → 51-firebase-setup/, 52-firebase-auth/, 54-firebase-fcm/
UI komponente         → 60–68 (ui-): checkbox, spinner, edit-text, date-picker, toolbar, recyclerview, media-player…
Navigacija            → 70-intent-druga-aktivnost, 71-implicit-intent
Fragmenti             → 19-fragment-primer, 90-fragments-prirucnik
Mape / fajlovi        → 79-maps-google-osm, 77-file-provider, 03-kamera
Dijalozi              → 17-alert-dialog, 64-ui-date-picker
Pozadina              → 74-thread-executor, 65-ui-progress-bar
Obaveštenja           → 11-notifikacija-prazna-baza, 80-alarm-notifikacija
Android komponente    → 84-content-provider, 85-broadcast-receiver
```
