# DatePicker i TimePicker

**Dodatni segment.** **Slično:** UI dijalozi (Toast, notifikacija).

**Cilj:** Dugme otvara kalendar; izabrani datum u TextView.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`DatePickerHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | Dugme/long click: `datePickerHelper.otvoriDatePicker()` |

---

## Kompletan kod – helper klasa

Kopiraj **`DatePickerHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.DatePickerHelper;
```

### Polje i init

```java
private DatePickerHelper datePickerHelper;

// onCreate:
datePickerHelper = new DatePickerHelper(this, textView);
button.setOnLongClickListener(v -> {
    datePickerHelper.otvoriDatePicker();
    return true;
});
// ili vreme:
// datePickerHelper.otvoriTimePicker();
```

> **Alternativa:** inline dijalozi ispod.

---

## Alternativa: inline u `MainActivity.java`

### Importi

```java
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
```

### DatePicker

```java
private void otvoriDatePicker() {
    Calendar c = Calendar.getInstance();
    int godina = c.get(Calendar.YEAR);
    int mesec = c.get(Calendar.MONTH);
    int dan = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog dialog = new DatePickerDialog(this,
            (view, y, m, d) -> {
                String datum = d + "." + (m + 1) + "." + y;
                textView.setText("Datum: " + datum);
            },
            godina, mesec, dan);
    dialog.show();
}
```

### TimePicker (opciono)

```java
private void otvoriTimePicker() {
    Calendar c = Calendar.getInstance();
    int sat = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);

    TimePickerDialog dialog = new TimePickerDialog(this,
            (view, h, m) -> {
                String vreme = h + ":" + String.format("%02d", m);
                textView.setText("Vreme: " + vreme);
            },
            sat, min, true);
    dialog.show();
}
```

### Poziv

```java
// imageButton.setOnLongClickListener(v -> { otvoriDatePicker(); return true; });
```

---

## Alternativa

- `MaterialDatePicker` – Material biblioteka, više setup-a
- Čuvanje datuma u SharedPreferences

---

## Checklist

- [ ] DatePickerDialog kreiran i `show()` pozvan
- [ ] Callback postavlja TextView
- [ ] Nema posebnih dozvola
