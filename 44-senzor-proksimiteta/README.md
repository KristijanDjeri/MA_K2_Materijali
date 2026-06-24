# Senzor proksimiteta (Proximity)

**Dodatni segment.** **Slično:** ostali senzori.

**Cilj:** Detektuj da li je nešto blizu senzora (npr. telefon uz uvo).

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ProksimitetHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `proksimitetHelper = new ProksimitetHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `proksimitetHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `proksimitetHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`ProksimitetHelper.java`** iz ovog foldera u `app/.../helper/`.

Puna implementacija senzora je u helperu – **ne** u MainActivity.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.ProksimitetHelper;

private ProksimitetHelper proksimitetHelper;

// onCreate:
proksimitetHelper = new ProksimitetHelper(this, textView);

// onResume / onPause:
proksimitetHelper.onResume();
proksimitetHelper.onPause();
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] Helper u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] TextView prikazuje blizu/daleko

---

## Povezano

- Pregled senzora: `41-senzori-pregled/`
- Helper mapa: `HELPER-KLASE.md`
