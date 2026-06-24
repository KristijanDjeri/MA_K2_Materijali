# Notifikacija – proširena (BigText, slika, lista)

**Cilj:** Notifikacija koja prikaže **više sadržaja** – duži tekst, sliku ili listu stavki.

---

## Preduslovi

- [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) – `NotifikacijaHelper`
- **`NotifikacijaProsirenaHelper.java`** iz ovog foldera

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`NotifikacijaProsirenaHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `NotifikacijaHelper.kreirajKanal(this)` |
| 3 | `MainActivity.java` | Test pozivi ispod |

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.NotifikacijaProsirenaHelper;
```

### U `onCreate`

```java
NotifikacijaHelper.kreirajKanal(this);

button.setOnLongClickListener(v -> {
    NotifikacijaProsirenaHelper.posaljiBigText(this,
            "Novi post",
            "Ovo je duži tekst posta koji ne stane u jednu liniju…");
    return true;
});

// Inbox primer:
// NotifikacijaProsirenaHelper.posaljiInbox(this,
//         "1. Prvi post", "2. Drugi post", "3. Treći post");

// Ongoing (npr. sa 83-audio-recorder/):
// NotifikacijaProsirenaHelper.posaljiOngoing(this);
// NotifikacijaProsirenaHelper.ukloniOngoing(this);
```

> **Ne piši** `posaljiBigTextNotifikaciju()` u MainActivity – metode su u helperu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `NotifikacijaProsirenaHelper` u paketu `helper`
- [ ] Kanal kreiran pre slanja
- [ ] Različit `notify(id)` za svaki tip
- [ ] `ukloniOngoing` kad ongoing završi

---

## Povezano

- Osnovna: [38-notifikacija-osnovna](../38-notifikacija-osnovna/)
- Akcije: [39-notifikacija-akcije](../39-notifikacija-akcije/)
