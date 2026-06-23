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

## 3. U `MainActivity.java` (ceo deo)

### Importi

```java
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
```

### U `onCreate`

```java
Spinner spinner = findViewById(R.id.spinner);
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this,
        R.array.spinner_opcije,
        android.R.layout.simple_spinner_item
);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);

spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String izbor = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Izabrano: " + izbor, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});
```

---

## Alternativa

- Lista iz koda: `new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaStringova)`
- Filtriranje postova po izboru + RecyclerView

---

## Checklist

- [ ] `string-array` u strings.xml
- [ ] Spinner u layoutu
- [ ] ArrayAdapter postavljen
- [ ] OnItemSelectedListener radi
