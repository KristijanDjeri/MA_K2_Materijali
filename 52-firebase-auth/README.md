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

## Korak 2: Kompletan kod u `MainActivity.java`

### 1. Importi

```java
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
```

### 2. Polja

```java
private FirebaseAuth firebaseAuth;
private EditText editEmail;
private EditText editLozinka;
private Button btnRegistracija;
private Button btnPrijava;
```

### 3. U `onCreate`

```java
firebaseAuth = FirebaseAuth.getInstance();

editEmail = findViewById(R.id.editEmail);
editLozinka = findViewById(R.id.editLozinka);
btnRegistracija = findViewById(R.id.btnRegistracija);
btnPrijava = findViewById(R.id.btnPrijava);

btnRegistracija.setOnClickListener(v -> registracija());
btnPrijava.setOnClickListener(v -> prijava());

// Provera da li je već ulogovan
FirebaseUser trenutni = firebaseAuth.getCurrentUser();
if (trenutni != null) {
    Toast.makeText(this, "Ulogovan: " + trenutni.getEmail(), Toast.LENGTH_SHORT).show();
}
```

### 4. Registracija

```java
private void registracija() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();

    if (email.isEmpty() || lozinka.isEmpty()) {
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
```

### 5. Prijava

```java
private void prijava() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();

    if (email.isEmpty() || lozinka.isEmpty()) {
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

### 6. Odjava (opciono)

```java
private void odjava() {
    firebaseAuth.signOut();
    Toast.makeText(this, "Odjavljen", Toast.LENGTH_SHORT).show();
}
```

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

## Checklist

- [ ] Email/Password uključen u Firebase Console
- [ ] `52-firebase-auth` u Gradle
- [ ] Registracija i prijava rade
- [ ] Toast na uspeh i grešku

---

## Sledeći korak

**`53-firebase-firestore/`** – čuvanje postova u oblaku.
