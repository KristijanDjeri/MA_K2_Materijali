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

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- `MaterialDatePicker` – Material biblioteka, više setup-a
- Čuvanje datuma u SharedPreferences

---

## Checklist

- [ ] DatePickerDialog kreiran i `show()` pozvan
- [ ] Callback postavlja TextView
- [ ] Nema posebnih dozvola
