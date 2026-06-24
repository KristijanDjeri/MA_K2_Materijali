# CheckBox i RadioButton

**Dodatni segment.** **Slično:** Switch (`09-switch-listener/`) – sluša promenu stanja UI kontrole.

**Cilj:** Korisnik bira opcije; prikaži izbor u `TextView`.

---

## Layout – dodaj u `activity_main.xml`

Kopiraj iz `ui_checkbox_radiobutton_snippet.xml` ili:

```xml
<CheckBox
    android:id="@+id/checkBoxNotifikacije"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Uključi notifikacije"
    android:layout_marginTop="8dp" />

<RadioGroup
    android:id="@+id/radioGroupSort"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:layout_marginTop="8dp">

    <RadioButton
        android:id="@+id/radioPoId"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Po ID"
        android:checked="true" />

    <RadioButton
        android:id="@+id/radioPoTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Po naslovu" />

</RadioGroup>
```

---

## Kompletan kod – helper klasa

Kopiraj **`CheckBoxRadioHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.kolokvijum2.R;
import com.example.kolokvijum2.helper.CheckBoxRadioHelper;
```

### U `onCreate`

```java
CheckBox checkBoxNotifikacije = findViewById(R.id.checkBoxNotifikacije);
RadioGroup radioGroupSort = findViewById(R.id.radioGroupSort);
RadioButton radioPoId = findViewById(R.id.radioPoId);
RadioButton radioPoTitle = findViewById(R.id.radioPoTitle);

new CheckBoxRadioHelper(
        checkBoxNotifikacije, radioGroupSort, textView,
        R.id.radioPoId, R.id.radioPoTitle
);
```

> **Alternativa:** inline listeneri ispod.

---

## Alternativa: inline u `MainActivity.java`

### Importi

```java
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
```

### Polja

```java
private CheckBox checkBoxNotifikacije;
private RadioGroup radioGroupSort;
private RadioButton radioPoId;
private RadioButton radioPoTitle;
```

### U `onCreate`

```java
checkBoxNotifikacije = findViewById(R.id.checkBoxNotifikacije);
radioGroupSort = findViewById(R.id.radioGroupSort);
radioPoId = findViewById(R.id.radioPoId);
radioPoTitle = findViewById(R.id.radioPoTitle);

checkBoxNotifikacije.setOnCheckedChangeListener((buttonView, isChecked) -> {
    textView.setText(isChecked ? "Notifikacije: uključene" : "Notifikacije: isključene");
});

radioGroupSort.setOnCheckedChangeListener((group, checkedId) -> {
    if (checkedId == R.id.radioPoId) {
        textView.setText("Sortiranje: po ID");
    } else if (checkedId == R.id.radioPoTitle) {
        textView.setText("Sortiranje: po naslovu");
    }
});
```

### Čitanje stanja (npr. pre akcije)

```java
boolean notifUkljucene = checkBoxNotifikacije.isChecked();
boolean sortPoTitle = radioPoTitle.isChecked();
```

---

## Primer povezivanja sa zadatkom

```java
button.setOnClickListener(v -> {
    if (checkBoxNotifikacije.isChecked()) {
        postRepository.obrisiPrviPost(
                () -> NotifikacijaHelper.posaljiPraznaBaza(this)
        );
    } else {
        Toast.makeText(this, "Notifikacije isključene – brisanje preskočeno", Toast.LENGTH_SHORT).show();
    }
});
```

---

## Checklist

- [ ] CheckBox listener menja TextView
- [ ] RadioGroup – tačno jedan RadioButton aktivan
- [ ] `isChecked()` / `getCheckedRadioButtonId()` radi

---

## Povezano

- Switch: `09-switch-listener/`
- Spinner (padajuća lista): `63-ui-spinner/`
