# EditText i validacija unosa

**Dodatni segment.** **Slično:** UI komponente (zadatak 2) + upis u bazu.

**Cilj:** Korisnik unese naslov; ako je prazan → greška; inače → upis u Room preko `PostRepository`.

---

## 1. U `activity_main.xml` dodaj

```xml
<EditText
    android:id="@+id/editTextNaslov"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Unesite naslov"
    android:inputType="text" />
```

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`EditTextValidacijaHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | Poziv `editTextHelper.dodajPost()` |

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import android.widget.EditText;
import com.example.kolokvijum2.helper.EditTextValidacijaHelper;
import com.example.kolokvijum2.helper.PostRepository;
```

### Polja

```java
private EditTextValidacijaHelper editTextHelper;
```

### U `onCreate`

```java
EditText editTextNaslov = findViewById(R.id.editTextNaslov);
editTextHelper = new EditTextValidacijaHelper(editTextNaslov, postRepository);

switchPosts.setOnLongClickListener(v -> {
    editTextHelper.dodajPost();
    return true;
});
```

> Validacija i `postDao.insert` su u `PostRepository.dodajIzNaslova()` – **ne piši** `dodajPostIzUnosa()` u MainActivity.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.text.TextUtils;
import android.widget.EditText;

// POLJE:
private EditText editTextNaslov;

// U onCreate():
editTextNaslov = findViewById(R.id.editTextNaslov);

// METODA:
private void dodajPostIzUnosa() {
    String naslov = editTextNaslov.getText().toString().trim();

    if (TextUtils.isEmpty(naslov)) {
        Toast.makeText(this, "Naslov ne sme biti prazan!", Toast.LENGTH_SHORT).show();
        return;
    }

    if (naslov.length() < 3) {
        Toast.makeText(this, "Naslov mora imati bar 3 karaktera", Toast.LENGTH_SHORT).show();
        return;
    }

    Post post = new Post((int) System.currentTimeMillis(), naslov, "", 1);
    postDao.insert(post);
  editTextNaslov.setText("");
    Toast.makeText(this, "Post dodat", Toast.LENGTH_SHORT).show();
}
```

## Checklist

- [ ] `EditTextValidacijaHelper` u paketu `helper`
- [ ] `PostRepository` inicijalizovan
- [ ] Prazan naslov → Toast greška
- [ ] Uspešan unos → polje se briše
