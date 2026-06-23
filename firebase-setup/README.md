# Firebase – podešavanje projekta (setup)

**Obavezno prvi korak** pre Auth, Firestore ili FCM.

---

## Korak 1: Firebase Console

1. Idi na https://console.firebase.google.com/
2. **Add project** (Dodaj projekat)
3. Ime: `Kolokvijum2` → Continue → (Analytics opciono) → Create
4. Klikni **Android ikonicu** da dodaš Android app
5. Unesi:
   - **Android package name:** `com.example.kolokvijum2` *(mora biti isto kao u projektu!)*
   - App nickname: `Kolokvijum2`
6. **Register app**
7. Preuzmi **`google-services.json`**
8. Kopiraj fajl u: **`app/google-services.json`** (direktno u `app/` folder, NE u `src`)

Klikni Next → Next → Continue to console.

---

## Korak 2: Root `build.gradle` (Project level)

**Putanja:** `build.gradle` (ili `build.gradle.kts` u root-u projekta)

U bloku `buildscript { dependencies { } }` dodaj:

```gradle
buildscript {
    dependencies {
        classpath 'com.android.tools.build:gradle:8.2.0'  // verzija može biti drugačija
        classpath 'com.google.gms:google-services:4.4.2'  // DODAJ OVO
    }
}
```

> **Alternativa (noviji projekti):** U `settings.gradle` sa `pluginManagement`:
> ```gradle
> plugins {
>     id 'com.google.gms.google-services' version '4.4.2' apply false
> }
> ```

---

## Korak 3: App `build.gradle` (Module :app)

**Putanja:** `app/build.gradle`

### Na VRHU fajla (posle ostalih `apply plugin` linija):

```gradle
apply plugin: 'com.android.application'
apply plugin: 'com.google.gms.google-services'   // DODAJ OVO
```

### U `dependencies { }` dodaj Firebase BOM:

```gradle
// Firebase – BOM drži verzije usklađenim
implementation platform('com.google.firebase:firebase-bom:33.7.0')
implementation 'com.google.firebase:firebase-analytics'

// Za Auth (folder firebase-auth/):
implementation 'com.google.firebase:firebase-auth'

// Za Firestore (folder firebase-firestore/):
implementation 'com.google.firebase:firebase-firestore'

// Za FCM notifikacije (folder firebase-fcm/):
implementation 'com.google.firebase:firebase-messaging'
```

> **Napomena:** Sa BOM-om ne pišeš verziju na svakom `firebase-*` – BOM je određuje.  
> **Alternativa:** Bez BOM-a: `implementation 'com.google.firebase:firebase-firestore:25.1.1'` itd.

Klikni **Sync Now**.

---

## Korak 4: Provera `google-services.json`

Fajl mora biti na putanji:

```
Kolokvijum2/
  app/
    google-services.json    ← OVDE
    build.gradle
    src/
      main/
        ...
```

U Android Studio u Project view: **Android** → `app` → vidiš `google-services.json`.

---

## Korak 5: Internet dozvola

Već postoji u `osnovni-projekat/`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Korak 6: (Opciono) Provera da Firebase radi

U `MainActivity.onCreate` privremeno dodaj:

```java
import com.google.firebase.FirebaseApp;

// na početak onCreate:
FirebaseApp.initializeApp(this);
```

Ako app krene bez crash-a posle Sync-a, setup je OK. `initializeApp` se često poziva automatski – eksplicitni poziv je opcion.

---

## Firestore pravila (za testiranje)

U Firebase Console → **Firestore Database** → **Create database** → **Test mode** (za vežbu).

Za produkciju pravila nisu `allow read, write: if true` – ali za kolokvijum/vežbu je dovoljno.

Primer test pravila:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

> **Pažnja:** Test mode ističe za 30 dana – za kolokvijum napravi bazu blizu datuma ispita ili podesi pravila ručno.

---

## Česte greške

| Greška | Rešenje |
|--------|---------|
| `google-services.json is missing` | Fajl mora biti u `app/`, ne u root-u |
| `No matching client found for package name` | Package u Firebase mora = `applicationId` u Gradle |
| Sync failed | Proveri `google-services` classpath i plugin |
| `PERMISSION_DENIED` u Firestore | Uključi test mode ili ispravna pravila |

---

## Checklist

- [ ] Firebase projekat kreiran
- [ ] `google-services.json` u `app/`
- [ ] `google-services` plugin u oba Gradle fajla
- [ ] Firebase BOM zavisnosti dodate
- [ ] Gradle Sync uspeo
- [ ] Firestore baza kreirana u konzoli (za firestore folder)

---

## Sledeći korak

- Autentifikacija: **`firebase-auth/`**
- Baza postova: **`firebase-firestore/`**
