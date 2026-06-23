# EditText i validacija unosa

**Dodatni segment.** **Slično:** UI komponente (zadatak 2) + upis u bazu.

**Cilj:** Korisnik unese naslov; ako je prazan → greška; inače → upis u Room.

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

## 2. U `MainActivity.java`

### Importi

```java
import android.text.TextUtils;
import android.widget.EditText;
```

### Polje

```java
private EditText editTextNaslov;
```

### U `onCreate`

```java
editTextNaslov = findViewById(R.id.editTextNaslov);
```

### Validacija i insert

```java
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

### Poziv (npr. dugi klik na Switch)

```java
switchPosts.setOnLongClickListener(v -> {
    dodajPostIzUnosa();
    return true;
});
```

---

## Alternativa

- Email validacija: `Patterns.EMAIL_ADDRESS.matcher(email).matches()`
- `TextInputLayout` + error poruka – Material Design, više XML-a

---

## Checklist

- [ ] EditText u layoutu
- [ ] `trim()` pre provere
- [ ] `isEmpty()` → Toast i `return`
- [ ] Uspešan unos → `postDao.insert`
