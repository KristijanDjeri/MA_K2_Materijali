# Implicit Intent – browser, share, SMS, email

**Dodatni segment.** **Slično:** `70-intent-druga-aktivnost/` (explicit Intent), ali **Android bira** aplikaciju (browser, Gmail, Messages…).

**Cilj:** Otvori URL, podeli tekst, pošalji SMS ili email – bez pisanja sopstvene aktivnosti.

---

## Manifest

Za direktan poziv telefona vidi `81-poziv-telefon/`. Za ostale implicit intent-e **nije** posebna dozvola (browser, share, dialer sa brojem).

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ImplicitIntentHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**, listener na dugme (primer ispod) |

### Import

```java
import com.example.kolokvijum2.helper.ImplicitIntentHelper;
```

### Primer u `onCreate`

```java
button.setOnClickListener(v ->
        ImplicitIntentHelper.otvoriUrl(this, "https://jsonplaceholder.typicode.com/posts/1"));

// ili podeli title posta:
button.setOnClickListener(v ->
        ImplicitIntentHelper.podeliTitlePrvogPosta(this, postRepository.getFirst()));
```

---

## Kompletan kod – helper klasa

Kopiraj **`ImplicitIntentHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

Vidi tabelu i primer iznad.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## ImplicitIntentHelper – dostupne metode

| Metoda | Šta radi |
|--------|----------|
| `otvoriUrl(context, url)` | Browser (`ACTION_VIEW`) |
| `podeliTekst(context, tekst)` | Share (`ACTION_SEND`) |
| `podeliTitlePrvogPosta(context, post)` | Share naslova posta |
| `posaljiSms(context, broj, poruka)` | SMS (`smsto:`) |
| `posaljiEmail(context, adresa, subject, body)` | Email (`mailto:`) |
| `otvoriMapu(context, lat, lon)` | Mapa (`geo:` URI) |

---

## Explicit vs implicit

| Tip | Primer | Kada |
|-----|--------|------|
| **Explicit** | `new Intent(this, DetailActivity.class)` | Tvoja aktivnost |
| **Implicit** | `ACTION_VIEW`, `ACTION_SEND` | Sistem bira app |

---

## Checklist

- [ ] `Uri.parse` ispravan format
- [ ] `createChooser` za share (opciono, lepše UX)
- [ ] `resolveActivity` pre SMS/email (emulator možda nema app)

---

## Povezano

- Explicit Intent: `70-intent-druga-aktivnost/`
- Poziv: `81-poziv-telefon/`
- Mape u app: `79-maps-google-osm/`
