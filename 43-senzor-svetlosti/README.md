# Senzor svetlosti (Light)

**Dodatni segment.** Helper: **`SvetlostiHelper.java`**

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`SvetlostiHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `svetlostiHelper = new SvetlostiHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `svetlostiHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `svetlostiHelper.onPause();` |

---

## Kompletan kod – helper klasa

Kopiraj **`SvetlostiHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.SvetlostiHelper;

private SvetlostiHelper svetlostiHelper;

// onCreate:
svetlostiHelper = new SvetlostiHelper(this, textView);
```

Puna implementacija senzora je u **`SvetlostiHelper.java`** – ne u MainActivity.

---

## Checklist

- [ ] Helper u paketu `helper`
- [ ] `onResume` / `onPause` u MainActivity
- [ ] TextView prikazuje lux

---

## Povezano

- **`HELPER-KLASE.md`**
- **`42-senzor-magnetometar/`** – isti obrazac
