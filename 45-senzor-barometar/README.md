# Barometar (pritisak vazduha)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Prikaži atmosferski pritisak u hPa (hektopaskalima).

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`BarometarHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `barometarHelper = new BarometarHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `barometarHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `barometarHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`BarometarHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.BarometarHelper;

private BarometarHelper barometarHelper;

// onCreate:
barometarHelper = new BarometarHelper(this, textView);

// onResume / onPause:
barometarHelper.onResume();
barometarHelper.onPause();
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `BarometarHelper` u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] Vrednost u hPa
