# Povratna vibracija (haptic feedback)

**Dodatni segment.** Koristi sistemsku **VIBRATE** dozvolu – kratka vibracija kao povratna informacija (npr. kad nema više postova).

> **Napomena:** Folder se zove `82-povratna-vibracija` (ne „vibrator") – jasniji i neutralniji naziv.

**Slično:** kratki fizički odziv na događaj (kao zvuk ili Toast).

---

## Manifest

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

(Normalna dozvola – **ne** traži runtime na većini uređaja.)

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`VibracijaHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Kad je baza prazna: `VibracijaHelper.kratka(this)` |

---

## Kompletan kod – helper klasa

Kopiraj **`VibracijaHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.VibracijaHelper;
```

### Poziv (npr. kad nema postova)

```java
VibracijaHelper.kratka(this);
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- Kraće trajanje: `200` ms
- `VibrationEffect.createWaveform` – uzorak vibracije

---

## Checklist

- [ ] `VIBRATE` u Manifest-u
- [ ] Pozvano na jasan događaj (brisanje, greška, shake…)
