# Lokacija u realnom vremenu

**Dodatni segment.** **Alternativa** `getLastLocation()` iz zadatka 3.

**Cilj:** TextView se ažurira dok se uređaj kreće.

---

## Preduslovi

- `02-geo-lokacija/` – FusedLocationProviderClient već postoji
- Iste dozvole za lokaciju

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`LokacijaRealtimeHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | **`onResume`**: `lokacijaRealtimeHelper.onResume()` |
| 4 | `MainActivity.java` | **`onPause`**: `lokacijaRealtimeHelper.onPause()` |

---

## Kompletan kod – helper klasa

Kopiraj **`LokacijaRealtimeHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.LokacijaRealtimeHelper;
```

### Polje i lifecycle

```java
private LokacijaRealtimeHelper lokacijaRealtimeHelper;

// onCreate:
lokacijaRealtimeHelper = new LokacijaRealtimeHelper(this, textView);

// onResume:
lokacijaRealtimeHelper.onResume();

// onPause:
lokacijaRealtimeHelper.onPause();
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Razlika od zadatka 3

| getLastLocation | requestLocationUpdates |
|-----------------|------------------------|
| jednom pri startu | stalno osvežava |
| jednostavnije | više koda, troši bateriju |

> **Napomena:** Na kolokvijumu koristi ono što zadatak traži. Ovo je zamena ako profesor kaže „uživo".

---

## Checklist

- [ ] LocationCallback definisan
- [ ] requestLocationUpdates u onResume
- [ ] removeLocationUpdates u onPause
