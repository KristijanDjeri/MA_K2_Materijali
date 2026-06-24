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

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `EditTextValidacijaHelper` u paketu `helper`
- [ ] `PostRepository` inicijalizovan
- [ ] Prazan naslov → Toast greška
- [ ] Uspešan unos → polje se briše
