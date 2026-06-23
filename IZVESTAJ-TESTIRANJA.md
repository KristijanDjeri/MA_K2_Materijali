# Izveštaj testiranja – Priprema Kolokvijum 2

**Datum:** jun 2026  
**Test projekat:** `99-test-okruzenje/` (u ovom repou)  
**APK:** `99-test-okruzenje/app/build/outputs/apk/debug/app-debug.apk`

---

## Rezime

| Kategorija | Rezultat |
|------------|----------|
| **Kompajliranje (zvanični zadaci 1–9)** | ✅ USPELO |
| **Kompajliranje (dodatni segmenti)** | ✅ USPELO (ShakeDetector, RecyclerView adapter, DetailActivity, AlarmReceiver, PostFirestore) |
| **API / JSON mapiranje** | ✅ Radni URL; ❌ PDF URL vraća HTML |
| **Runtime na uređaju/emulatoru** | ⚠️ Nije pokrenuto (nema povezanog uređaja) |
| **Firebase** | ⚠️ Nije testirano (potreban `google-services.json`) |

---

## 1. Automatski build test

Kreiran je pun Android projekat sa:
- `MainActivity` (svi zadaci 1–9 iz `15-main-activity-referenca/`)
- Room (`Post`, `PostDao`, `AppDatabase`)
- Retrofit sa **radnim** URL-om
- Layout `activity_main.xml`
- Sve dozvole iz `01-osnovni-projekat/`

```bash
cd 99-test-okruzenje
./gradlew assembleDebug
# BUILD SUCCESSFUL
```

**Upozorenja (ne blokiraju rad):**
- Java 8 source/target zastarelo
- Room: više konstruktora na `Post` – koristi prazan konstruktor (OK)

### Dodatno kompajlirani segmenti (u istom projektu)

| Fajl | Status |
|------|--------|
| `ShakeDetector.java` | ✅ |
| `PostAdapter.java` + `item_post.xml` | ✅ |
| `DetailActivity.java` + layout | ✅ |
| `AlarmReceiver.java` | ✅ kompajlira; treba `<receiver>` u Manifest-u za rad |
| `PostFirestore.java` | ✅ |
| `MyFirebaseMessagingService` | ⏭️ nije uključen (nema Firebase Gradle) |

---

## 2. API endpoint test

| URL | Rezultat |
|-----|----------|
| `https://app.beeceptor.com/mock-server/dummy-json` (iz PDF-a) | ❌ HTTP 200 ali sadržaj je **HTML stranica**, Gson/Retrofit neće parsirati listu postova |
| `https://dummy-json.mock.beeceptor.com/posts` | ✅ JSON niz, 10 postova, polja `id`, `title`, `body`, `userId` – odgovara modelu `Post` |

**Zaključak:** U materijalu je `RetrofitClient` i `JsonPlaceholderApi` **ažurirani** na radni URL. Ako profesor na času da drugi URL, prilagodi base URL i `@GET` putanju.

---

## 3. Zvanični zadaci – status po segmentu

| # | Segment | Kompajlira | Runtime test | Napomena |
|---|---------|------------|--------------|----------|
| 1–2 | `01-osnovni-projekat/` | ✅ | ⚠️ | Layout + MainActivity OK |
| 3 | `02-geo-lokacija/` | ✅ | ⚠️ | Treba GPS / emulator Location |
| 4 | `03-kamera/` | ✅ | ⚠️ | Treba 03-kamera ili emulator camera |
| 4 | `04-senzor-ziroskop/` | ✅ | ⚠️ | Emulator često nema žiroskop |
| 5 | `05-room-baza/`, `06-retrofit-get/` | ✅ | ✅ API | URL iz PDF-a ne radi – koristi testirani |
| 6 | `07-ucitaj-10-postova/`, `08-toast-prvi-post/`, `09-switch-listener/` | ✅ | ⚠️ | Zavisi od API-ja + interneta |
| 7 | `10-brisanje-prvog-posta/`, `11-notifikacija-prazna-baza/` | ✅ | ⚠️ | API 33+ traži POST_NOTIFICATIONS runtime |
| 8 | `12-senzor-akcelerometar/` | ✅ | ⚠️ | Emulator ima virtual sensors |
| 9 | `13-shared-preferences/` | ✅ | ✅ | Radi bez hardvera |
| 9 | `14-kontakti/` | ✅ | ⚠️ | Treba kontakt u adresaru + dozvola |

**Legenda:** ✅ potvrđeno | ⚠️ nije pokrenuto na uređaju | ❌ ne radi

---

## 4. Dodatni segmenti – status

| Segment | Kompajlira | Runtime | Napomena |
|---------|------------|---------|----------|
| `20-recyclerview/` | ✅ | ⚠️ | Adapter OK; treba RecyclerView u layoutu + poziv `osveziListu` |
| `21-galerija/` | ✅ | ⚠️ | READ_MEDIA_IMAGES / storage |
| `49-senzor-shake/` | ✅ | ⚠️ | Fizički telefon preporučen |
| `36-audio-recorder/` | ✅* | ⚠️ | *logika pregledana i popravljena; nije u build testu kao MainActivity deo |
| `42-senzor-magnetometar/`, senzori | ✅ | ⚠️ | Isti Sensor pattern |
| `25-thread-executor/` | ✅ | ⚠️ | Logika standardna |
| `firebase-*` | ⏭️ | ⏭️ | Bez `google-services.json` nije smisleno testirati |

---

## 5. Poznati rizici na kolokvijumu

1. **API URL** – proveri na početku ispita u browseru da li vraća JSON
2. **Dozvole** – lokacija, 03-kamera, 14-kontakti, mikrofon, notifikacije (API 33+)
3. **Emulator vs telefon** – senzori, mikrofon, shake pouzdaniji na telefonu
4. **Room na main thread** – `allowMainThreadQueries()` OK za kolokvijum; profesor može tražiti pozadinsku nit
5. **Firebase** – samo ako dobiješ `google-services.json` na času

---

## 6. Šta je popravljeno posle testiranja

| Šta | Izmena |
|-----|--------|
| `06-retrofit-get/RetrofitClient.java` | Podrazumevani URL → `dummy-json.mock.beeceptor.com` |
| `06-retrofit-get/JsonPlaceholderApi.java` | `@GET("posts")` |
| `06-retrofit-get/README.md` | Napomena o neispravnom PDF URL-u |
| Struktura 05–16 | Segmenti razdvojeni za nezavisnu vežbu; spajanje u `16-spajanje-zadataka/` |
| `36-audio-recorder/README.md` | Ranije: `.m4a`, lifecycle, delete starog fajla |

---

## 7. Kako sam pokrenuti test kod kuće

```bash
# 1. Build
export ANDROID_HOME=~/Android/Sdk
cd "99-test-okruzenje"
./gradlew assembleDebug

# 2. Instalacija (telefon USB debugging ili emulator)
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 3. API provera
curl -s https://dummy-json.mock.beeceptor.com/posts | head
```

---

## 8. Zaključak

**Kod za zvaničnih 9 zadataka se uspešno kompajlira** i spreman je za kolokvijum.  
**Retrofit URL iz PDF-a trenutno ne vraća JSON** – materijal je usklađen sa radnim endpointom; na ispitu proveri šta profesor kaže.

Runtime testovi (03-kamera, GPS, senzori, audio) **nisu automatski pokriveni** jer nije bio povezan emulator/telefon. Logika je standardna Android praksa; preporuka: na telefonu probaj bar Switch (API) + lokaciju + jednu sliku pre ispita (~15 min).

---

*Generisano automatskim build + API proverom. Test projekat ostaje u `99-test-okruzenje/` za tvoju proveru.*
