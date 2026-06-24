# Firebase – pregled i redosled

Firebase je Google-ova platforma za backend usluge. Na kolokvijumu može zameniti ili dopuniti **Retrofit + Room**.

## Folderi (radi redom)

| # | Folder | Šta radi |
|---|--------|----------|
| 1 | [51-firebase-setup](51-firebase-setup/) | Gradle, `google-services.json`, inicijalizacija |
| 2 | [52-firebase-auth](52-firebase-auth/) | Registracija i prijava (email/lozinka) |
| 3 | [53-firebase-firestore](53-firebase-firestore/) | Čitanje/pisanje postova u oblaku |
| 4 | [54-firebase-fcm](54-firebase-fcm/) | Push notifikacije (opciono) |

## Uporedba sa zvaničnim zadatkom

| Zvanični zadatak | Firebase ekvivalent |
|------------------|---------------------|
| Retrofit GET (zadatak 5) | `PostRepository` / `FirestorePostsHelper` |
| Room baza (zadatak 5–7) | Room + opciono Firestore hibrid |
| Switch učitaj 10 postova (zadatak 6) | `PostRepository` ili `FirestorePostsHelper` |
| Obriši prvi post (zadatak 7) | `PostRepository` ili `FirestorePostsHelper` |
| Auth | `FirebaseAuthHelper` |
| Notifikacija (zadatak 7) | FCM ili lokalna notifikacija |

## Šta ti treba pre početka

1. Google nalog
2. [Firebase Console](https://console.firebase.google.com/)
3. Android projekat **Kolokvijum2** u Android Studio

## Brzi start (5 koraka)

1. Firebase Console → **Add project** → ime npr. `Kolokvijum2`
2. **Add app** → Android → package: `com.example.kolokvijum2`
3. Preuzmi **`google-services.json`** → stavi u `app/`
4. Prati **`51-firebase-setup/`** za Gradle
5. Prati **`53-firebase-firestore/`** za podatke

## Napomena za kolokvijum

- Na času profesor može dati **svoj** `google-services.json` – bez njega Firebase ne radi.
- Za vežbu kod kuće napravi sopstveni Firebase projekat (besplatno).
- Internet mora biti uključen (`INTERNET` dozvola već postoji u `01-osnovni-projekat/`).
