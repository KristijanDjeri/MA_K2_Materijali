# Priprema za Kolokvijum 2 – Mobilne Aplikacije

Materijal za kolokvijum: gotovi segmenti koda i uputstva po temama.  
**Android Studio**, **minSdk 28** (Android 9+).

**Organizacija foldera:** [KONVENCIJA-FOLDERA.md](KONVENCIJA-FOLDERA.md) (enumeracija `01-`, `senzor-`, …)  
**Fragmenti na ispitu:** [90-fragments-prirucnik](90-fragments-prirucnik/)  
**Testiranje:** [IZVESTAJ-TESTIRANJA.md](IZVESTAJ-TESTIRANJA.md) · projekat `99-test-okruzenje/`

---

## Zvanični zadaci (01–11)

| Folder | Šta pokriva |
|--------|-------------|
| [01-osnovni-projekat](01-osnovni-projekat/) | Projekat, layout (zadaci 1–2) |
| [02-geo-lokacija](02-geo-lokacija/) | Širina/dužina (zadatak 3) |
| [03-kamera](03-kamera/) | Slikanje (zadatak 4) |
| [04-senzor-ziroskop](04-senzor-ziroskop/) | Žiroskop Toast (zadatak 4) |
| [05-retrofit-room](05-retrofit-room/) | API + baza (zadatak 5) |
| [06-switch-postovi](06-switch-postovi/) | Switch postovi (zadatak 6) |
| [07-brisanje-notifikacije](07-brisanje-notifikacije/) | Brisanje + notifikacija (zadatak 7) |
| [08-senzor-akcelerometar](08-senzor-akcelerometar/) | Akcelerometar (zadatak 8) |
| [09-shared-preferences](09-shared-preferences/) | SharedPreferences (zadatak 9) |
| [10-kontakti](10-kontakti/) | Kontakti (zadatak 9) |
| [11-main-activity-referenca](11-main-activity-referenca/) | Sve u jednom MainActivity |

## Dodatno (20–54) i priručnici (90, 99)

Puna tabela: **[KONVENCIJA-FOLDERA.md](KONVENCIJA-FOLDERA.md)**  
Notifikacije: **37–40** · Verovatnoća tema: **[DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md)**

---

## Redosled na kolokvijumu

1. `01-osnovni-projekat/` → projekat Kolokvijum2  
2. Dozvole + Gradle iz istog foldera  
3. Zadaci 02–10 po brojevima  
4. Uporedi sa `11-main-activity-referenca/`  
5. Ako traže Fragmente → `90-fragments-prirucnik/`

---

## API (testirano)

Radni endpoint u `05-retrofit-room/`:

```
GET https://dummy-json.mock.beeceptor.com/posts
```

URL iz PDF-a može vratiti HTML – proveri na početku ispita.

---

## Dozvole (Manifest)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```
