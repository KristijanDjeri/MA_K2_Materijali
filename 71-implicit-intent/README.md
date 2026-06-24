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

## Alternativa: inline metode (bez helpera)

## Import

```java
import android.content.Intent;
import android.net.Uri;
```

---

## 1. Otvori URL u browseru (`ACTION_VIEW`)

```java
private void otvoriUrl(String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(intent);
}
```

Primer:

```java
button.setOnClickListener(v -> otvoriUrl("https://jsonplaceholder.typicode.com/posts/1"));
```

---

## 2. Podeli tekst (`ACTION_SEND`)

```java
private void podeliTekst(String tekst) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, tekst);
    startActivity(Intent.createChooser(intent, "Podeli preko"));
}
```

Primer – title prvog posta (preko `PostRepository`):

```java
Post prvi = postRepository.getFirst();
if (prvi != null) {
    ImplicitIntentHelper.podeliTekst(this, prvi.getTitle());
}
```

---

## 3. SMS (`ACTION_SENDTO` + `smsto:`)

```java
private void posaljiSms(String broj, String poruka) {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("smsto:" + broj));
    intent.putExtra("sms_body", poruka);
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    }
}
```

Primer:

```java
posaljiSms("0601234567", "Test poruka sa kolokvijuma");
```

---

## 4. Email (`ACTION_SENDTO` + `mailto:`)

```java
private void posaljiEmail(String adresa, String subject, String body) {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("mailto:" + adresa));
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, body);
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    }
}
```

Primer:

```java
posaljiEmail("profesor@example.com", "Kolokvijum", "Rad je završen.");
```

---

## 5. Mapa – koordinate (`geo:` URI)

```java
private void otvoriMapu(double lat, double lon) {
    Uri geoUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + lat + "," + lon);
    Intent intent = new Intent(Intent.ACTION_VIEW, geoUri);
    startActivity(intent);
}
```

> Za ugrađenu mapu u aplikaciji vidi `79-maps-google-osm/`.

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
