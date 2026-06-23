# Priprema za Kolokvijum 2 – Mobilne Aplikacije

Materijal za kolokvijum: gotovi segmenti koda i uputstva po temama.  
**Android Studio**, **minSdk 28** (Android 9+).

## Struktura foldera

| Folder | Šta pokriva (zadaci) |
|--------|----------------------|
| [osnovni-projekat](osnovni-projekat/) | Novi projekat, MainActivity, layout (zadaci 1–2) |
| [geo-lokacija](geo-lokacija/) | Prikaz širine i dužine u TextView (zadatak 3) |
| [kamera](kamera/) | Slikanje i prikaz u ImageView (zadatak 4) |
| [ziroskop](ziroskop/) | Toast sa X, Y, Z pri zameni slike (zadatak 4) |
| [retrofit-room](retrofit-room/) | API, model, baza, Retrofit GET (zadatak 5) |
| [switch-postovi](switch-postovi/) | Switch ON – učitavanje 10 postova / Toast title (zadatak 6) |
| [brisanje-notifikacije](brisanje-notifikacije/) | Brisanje prvog posta, notifikacija (zadatak 7) |
| [akcelerometar](akcelerometar/) | Tekst dugmeta = vrednosti akcelerometra (zadatak 8) |
| [shared-preferences](shared-preferences/) | Čuvanje TextView teksta (zadatak 9) |
| [kontakti](kontakti/) | Ime prvog kontakta u TextView (zadatak 9) |
| [main-activity-referenca](main-activity-referenca/) | Spojeni MainActivity primer (sve u jednom) |

### Dodatni segmenti (mogući na kolokvijumu)

Pogledaj **[DODATNI-SEGMENTI.md](DODATNI-SEGMENTI.md)** za pregled verovatnoće.

| Folder | Tema |
|--------|------|
| [recyclerview](recyclerview/) | Lista postova iz baze |
| [galerija](galerija/) | Izbor slike iz galerije |
| [intent-druga-aktivnost](intent-druga-aktivnost/) | Druga aktivnost + Intent extras |
| [retrofit-post](retrofit-post/) | POST zahtev |
| [webview](webview/) | WebView / browser Intent |
| [magnetometar](magnetometar/) | Magnetometar / kompas |
| [thread-executor](thread-executor/) | Pozadinska nit za Room |
| [interni-fajl](interni-fajl/) | Čuvanje u interni fajl |
| [edit-text-validacija](edit-text-validacija/) | EditText + validacija |
| [alarm-notifikacija](alarm-notifikacija/) | AlarmManager + notifikacija |
| [poziv-telefon](poziv-telefon/) | Broj iz kontakta / poziv |
| [spinner](spinner/) | Padajuća lista |
| [date-picker](date-picker/) | DatePicker / TimePicker |
| [okhttp-json](okhttp-json/) | HTTP bez Retrofit-a |
| [lokacija-realtime](lokacija-realtime/) | Lokacija uživo |
| [progress-bar](progress-bar/) | ProgressBar pri učitavanju |
| [vibrator](vibrator/) | Vibracija |
| [audio-recorder](audio-recorder/) | Snimanje i reprodukcija zvuka |
| [senzori-pregled](senzori-pregled/) | Mapa svih senzora |
| [senzor-svetlosti](senzor-svetlosti/) | Senzor svetlosti (lux) |
| [senzor-proksimiteta](senzor-proksimiteta/) | Senzor blizine |
| [barometar](barometar/) | Pritisak vazduha |
| [brojac-koraka](brojac-koraka/) | Step counter / detector |
| [senzori-izvedeni](senzori-izvedeni/) | Gravity, linear accel, rotation vector |
| [senzor-vlage-temperature](senzor-vlage-temperature/) | Vlaga i temperatura |
| [shake-senzor](shake-senzor/) | Detekcija tresenja (akcelerometar) |
| [firebase](firebase/) | Pregled Firebase segmenata |
| [firebase-setup](firebase-setup/) | Gradle + google-services.json |
| [firebase-auth](firebase-auth/) | Email/lozinka prijava |
| [firebase-firestore](firebase-firestore/) | Postovi u oblaku |
| [firebase-fcm](firebase-fcm/) | Push notifikacije |

## Redosled rada na kolokvijumu

1. Kreiraj projekat **Kolokvijum2** → pogledaj `osnovni-projekat/`
2. Dodaj dozvole u `AndroidManifest.xml` (svaki folder navodi svoje)
3. Dodaj zavisnosti u `build.gradle` → `osnovni-projekat/gradle-zavisnosti.txt`
4. Za svaki zadatak kopiraj segmente iz odgovarajućeg foldera u projekat
5. Na kraju uporedi sa `main-activity-referenca/MainActivity.java` ako nešto ne radi

## API endpoint

```
GET https://app.beeceptor.com/mock-server/dummy-json
```

Odgovor je JSON niz objekata sa poljima poput `id`, `title`, `body`, itd. (prilagodi model prema stvarnom odgovoru).

## Dozvole (sve u Manifest-u)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
<!-- Android 13+ za notifikacije -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Napomene

- Na Android 6+ dozvole za lokaciju, kameru i kontakte traži **u runtime-u** (primeri u folderima).
- Notifikacije na API 26+ zahtevaju **NotificationChannel** (`brisanje-notifikacije/`).
- Room i Retrofit rade na pozadinskoj niti (`ExecutorService` ili `new Thread()`).
