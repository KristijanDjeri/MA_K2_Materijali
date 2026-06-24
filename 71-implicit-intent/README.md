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

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// Implicit Intent primeri (folder 56-implicit-intent/)

import android.content.Intent;
import android.net.Uri;

private void otvoriUrl(String url) {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
}

private void podeliTekst(String tekst) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, tekst);
    startActivity(Intent.createChooser(intent, "Podeli"));
}

private void posaljiSms(String broj, String poruka) {
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + broj));
    intent.putExtra("sms_body", poruka);
    startActivity(intent);
}

private void posaljiEmail(String adresa, String subject, String body) {
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + adresa));
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, body);
    startActivity(intent);
}

private void otvoriMapu(double lat, double lon) {
    Uri geo = Uri.parse("geo:" + lat + "," + lon + "?q=" + lat + "," + lon);
    startActivity(new Intent(Intent.ACTION_VIEW, geo));
}
```

## Checklist

- [ ] `Uri.parse` ispravan format
- [ ] `createChooser` za share (opciono, lepše UX)
- [ ] `resolveActivity` pre SMS/email (emulator možda nema app)

---

## Povezano

- Explicit Intent: `70-intent-druga-aktivnost/`
- Poziv: `81-poziv-telefon/`
- Mape u app: `79-maps-google-osm/`
