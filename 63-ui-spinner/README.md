# Spinner – padajuća lista

**Dodatni segment.** **Slično:** Switch i ostale UI komponente (zadatak 2).

**Cilj:** Spinner sa opcijama; izbor → Toast.

---

## 1. U `res/values/strings.xml` dodaj

```xml
<string-array name="spinner_opcije">
    <item>Svi postovi</item>
    <item>Prvih 5</item>
    <item>Prvih 10</item>
</string-array>
```

---

## 2. U `activity_main.xml` dodaj

```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

---

## Kompletan kod – helper klasa

Kopiraj **`SpinnerHelper.java`** iz ovog foldera u `app/.../helper/SpinnerHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import android.widget.Spinner;
import com.example.kolokvijum2.R;
import com.example.kolokvijum2.helper.SpinnerHelper;
```

### U `onCreate`

```java
Spinner spinner = findViewById(R.id.spinner);
new SpinnerHelper(this, spinner, textView, R.array.spinner_opcije);
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- Lista iz koda: `new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaStringova)`
- Filtriranje postova po izboru + RecyclerView

---

## Checklist

- [ ] `string-array` u strings.xml
- [ ] Spinner u layoutu
- [ ] ArrayAdapter postavljen
- [ ] OnItemSelectedListener radi
