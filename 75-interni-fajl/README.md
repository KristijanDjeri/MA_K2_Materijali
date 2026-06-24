# Interni fajl – čuvanje teksta

**Dodatni segment.** **Slično:** SharedPreferences (zadatak 9).

**Cilj:** Sačuvaj tekst u fajl `podaci.txt` u internom skladištu aplikacije.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`InterniFajlHelper.java`** | Novi fajl → `app/.../helper/` (kopiraj iz ovog foldera) |
| 2 | `MainActivity.java` | Na klik ili u `SwitchPostsHelper` OFF toku: `InterniFajlHelper.sacuvaj(this, textView.getText().toString());` |
| 3 | `MainActivity.java` | Pri pokretanju (opciono): `String s = InterniFajlHelper.ucitaj(this);` |

---

## Kompletan kod – helper klasa

Kopiraj **`InterniFajlHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.InterniFajlHelper;
```

### Čuvanje i čitanje

```java
InterniFajlHelper.sacuvaj(this, textView.getText().toString());
String ucitano = InterniFajlHelper.ucitaj(this);
if (!ucitano.isEmpty()) {
    textView.setText(ucitano);
}
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- `getExternalFilesDir(null)` – eksterni storage, i dalje privatno za app
- SharedPreferences – jednostavnije za mali tekst

---

## Checklist

- [ ] `openFileOutput` / `openFileInput`
- [ ] Nema posebne Manifest dozvole za interni fajl
