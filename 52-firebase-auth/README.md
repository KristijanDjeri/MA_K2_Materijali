# Firebase Authentication (email / lozinka)

**Dodatni segment.** **Slično:** runtime dozvole + async callback (kao Retrofit).

**Cilj:** Registracija i prijava korisnika; Toast sa uspehom/greškom.

---

## Preduslovi

- `51-firebase-setup/` završen
- U `app/build.gradle`: `implementation 'com.google.firebase:52-firebase-auth'`
- U Firebase Console → **Authentication** → **Get started** → uključi **Email/Password**

---

## Korak 1: Layout – dodaj u `activity_main.xml`

```xml
<EditText
    android:id="@+id/editEmail"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Email"
    android:inputType="textEmailAddress" />

<EditText
    android:id="@+id/editLozinka"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Lozinka"
    android:inputType="textPassword" />

<Button
    android:id="@+id/btnRegistracija"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Registracija" />

<Button
    android:id="@+id/btnPrijava"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Prijava" />
```

> **Alternativa:** Posebna `LoginActivity` – folder `70-intent-druga-aktivnost/`.

---

## Korak 2: MainActivity – samo povezivanje (preporučeno)

Kopiraj **`FirebaseAuthHelper.java`** iz ovog foldera u `app/.../helper/`.

### Import

```java
import android.widget.Button;
import android.widget.EditText;
import com.example.kolokvijum2.helper.FirebaseAuthHelper;
```

### U `onCreate`, posle `findViewById`

```java
EditText editEmail = findViewById(R.id.editEmail);
EditText editLozinka = findViewById(R.id.editLozinka);
Button btnRegistracija = findViewById(R.id.btnRegistracija);
Button btnPrijava = findViewById(R.id.btnPrijava);

FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper(
        this, editEmail, editLozinka, btnRegistracija, btnPrijava);
```

> Listeneri na dugmad su **u konstruktoru** helpera – ne piši `registracija()` / `prijava()` u MainActivity.

---


## Alternativne metode prijave

| Metoda | Firebase |
|--------|----------|
| Email/lozinka | `signInWithEmailAndPassword` (gore) |
| Anonimno | `signInAnonymously()` – bez UI |
| Google | Zahteva dodatni setup – retko na kolokvijumu |

Anonimna prijava (jedna linija u onCreate za test):

```java
firebaseAuth.signInAnonymously()
        .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Anonimni user", Toast.LENGTH_SHORT).show();
            }
        });
```

U konzoli: Authentication → Sign-in method → **Anonymous** → Enable.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// POLJA:
private FirebaseAuth firebaseAuth;
private EditText editEmail;
private EditText editLozinka;

// U onCreate():
firebaseAuth = FirebaseAuth.getInstance();
editEmail = findViewById(R.id.editEmail);
editLozinka = findViewById(R.id.editLozinka);
Button btnRegistracija = findViewById(R.id.btnRegistracija);
Button btnPrijava = findViewById(R.id.btnPrijava);

btnRegistracija.setOnClickListener(v -> firebaseRegistracija());
btnPrijava.setOnClickListener(v -> firebasePrijava());

FirebaseUser trenutni = firebaseAuth.getCurrentUser();
if (trenutni != null) {
    Toast.makeText(this, "Ulogovan: " + trenutni.getEmail(), Toast.LENGTH_SHORT).show();
}

// METODE:

private void firebaseRegistracija() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
        Toast.makeText(this, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
        return;
    }
    firebaseAuth.createUserWithEmailAndPassword(email, lozinka)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Registracija uspešna", Toast.LENGTH_SHORT).show();
                } else {
                    String greska = task.getException() != null
                            ? task.getException().getMessage() : "Greška";
                    Toast.makeText(this, greska, Toast.LENGTH_SHORT).show();
                }
            });
}

private void firebasePrijava() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
        Toast.makeText(this, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
        return;
    }
    firebaseAuth.signInWithEmailAndPassword(email, lozinka)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Prijava uspešna", Toast.LENGTH_SHORT).show();
                } else {
                    String greska = task.getException() != null
                            ? task.getException().getMessage() : "Greška";
                    Toast.makeText(this, greska, Toast.LENGTH_SHORT).show();
                }
            });
}
```

## Checklist

- [ ] `FirebaseAuthHelper` u paketu `helper`
- [ ] Email/Password uključen u Firebase Console
- [ ] Registracija i prijava rade

---

## Sledeći korak

**`53-firebase-firestore/`** – čuvanje postova u oblaku.
