# Dodatni segmenti – verovatnoća na kolokvijumu

Ovi folderi pokrivaju teme **slične težine i principa** kao zvanični zadaci, ali nisu eksplicitno u PDF-u.

## Visoka verovatnoća (isti obrazac kao na kolokvijumu)

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [recyclerview](recyclerview/) | Lista postova iz baze | 5, 6, 7 |
| [galerija](galerija/) | Izbor slike umesto kamere | 4 |
| [thread-executor](thread-executor/) | Room bez main thread | 5, 6, 7 |
| [retrofit-post](retrofit-post/) | Par uz GET | 5 |
| [intent-druga-aktivnost](intent-druga-aktivnost/) | Prenos podataka | 9 |
| [edit-text-validacija](edit-text-validacija/) | Unos + provera | 2 |
| [progress-bar](progress-bar/) | Učitavanje sa API-ja | 5 |

## Srednja verovatnoća

| Folder | Zašto bi pitao | Sličan zadatku |
|--------|----------------|----------------|
| [magnetometar](magnetometar/) | Još jedan senzor | 4, 8 |
| [lokacija-realtime](lokacija-realtime/) | Varijanta lokacije | 3 |
| [interni-fajl](interni-fajl/) | Čuvanje podataka | 9 |
| [okhttp-json](okhttp-json/) | API bez Retrofit-a | 5 |
| [webview](webview/) | Prikaz web sadržaja | 5 |
| [alarm-notifikacija](alarm-notifikacija/) | Zakazana notifikacija | 7 |
| [poziv-telefon](poziv-telefon/) | Kontakti + Intent | 9 |
| [spinner](spinner/) | UI izbor | 2 |
| [date-picker](date-picker/) | Dijalog datum/vreme | 2 |

## Niža verovatnoća (ali brzo za naučiti)

| Folder | Napomena |
|--------|----------|
| [vibrator](vibrator/) | Kratka vibracija uz događaj |

## Kako učiti

1. Prvo savladaj **zvanične** foldere (geo, kamera, retrofit, switch…)
2. Dodaj **recyclerview**, **galerija**, **thread-executor** – najčešće varijante
3. Ostalo pročitaj README – dovoljno je znati gde kopirati kod

## Mapa principa

```
Dozvole (runtime)     → geo, kamera, galerija, kontakti, poziv
Senzori               → ziroskop, akcelerometar, magnetometar
Mreža + JSON          → retrofit, retrofit-post, okhttp-json, webview
Perzistencija         → room, shared-preferences, interni-fajl
UI komponente         → osnovni layout, spinner, edit-text, date-picker
Navigacija            → intent-druga-aktivnost
Pozadina              → thread-executor, progress-bar
Obaveštenja           → brisanje-notifikacije, alarm-notifikacija
```
