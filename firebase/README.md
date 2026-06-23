# Firebase – pregled i redosled

Firebase je Google-ova platforma za backend usluge. Na kolokvijumu može zameniti ili dopuniti **Retrofit + Room**.

## Folderi (radi redom)

| # | Folder | Šta radi |
|---|--------|----------|
| 1 | [firebase-setup](firebase-setup/) | Gradle, `google-services.json`, inicijalizacija |
| 2 | [firebase-auth](firebase-auth/) | Registracija i prijava (email/lozinka) |
| 3 | [firebase-firestore](firebase-firestore/) | Čitanje/pisanje postova u oblaku |
| 4 | [firebase-fcm](firebase-fcm/) | Push notifikacije (opciono) |

## Uporedba sa zvaničnim zadatkom

| Zvanični zadatak | Firebase ekvivalent |
|------------------|---------------------|
| Retrofit GET (zadatak 5) | Firestore `collection("posts").get()` |
| Room baza (zadatak 5–7) | Firestore kolekcija `posts` |
| Switch učitaj 10 postova (zadatak 6) | Firestore query `.limit(10)` |
| Obriši prvi post (zadatak 7) | Firestore `delete()` na dokumentu |
| Notifikacija (zadatak 7) | FCM ili lokalna notifikacija |

## Šta ti treba pre početka

1. Google nalog
2. [Firebase Console](https://console.firebase.google.com/)
3. Android projekat **Kolokvijum2** u Android Studio

## Brzi start (5 koraka)

1. Firebase Console → **Add project** → ime npr. `Kolokvijum2`
2. **Add app** → Android → package: `com.example.kolokvijum2`
3. Preuzmi **`google-services.json`** → stavi u `app/`
4. Prati **`firebase-setup/`** za Gradle
5. Prati **`firebase-firestore/`** za podatke

## Napomena za kolokvijum

- Na času profesor može dati **svoj** `google-services.json` – bez njega Firebase ne radi.
- Za vežbu kod kuće napravi sopstveni Firebase projekat (besplatno).
- Internet mora biti uključen (`INTERNET` dozvola već postoji u `osnovni-projekat/`).
