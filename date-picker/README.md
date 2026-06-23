# DatePicker i TimePicker

**Dodatni segment.** **Slično:** UI dijalozi (Toast, notifikacija).

**Cilj:** Dugme otvara kalendar; izabrani datum u TextView.

---

## Kompletan kod za `MainActivity.java`

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
