# Brisanje prvog posta (zadatak 7, deo 1)

**Cilj:** Klik na `Button` → obriši post na **prvoj poziciji** u bazi (prvi red).

Radi **samostalno** – **bez notifikacije**. Kad tabela ostane prazna, notifikaciju dodaješ iz `11-notifikacija-prazna-baza/`.

---

## Šta ti treba pre ovoga

- `05-room-baza/` – `postDao`
- `07-ucitaj-10-postova/` – **`PostRepository`** (već ima metodu `obrisiPrviPost()`)
- `button` u layoutu
- U bazi bar jedan post (npr. iz `07-ucitaj-10-postova/`)

---

## Koji fajlovi se menjaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `PostRepository.java` | Već iz `07-ucitaj-10-postova/` – **ne dodaješ** novu metodu |
| 2 | `MainActivity.java` | Listener na dugme (primer ispod) |

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.PostRepository;
```

### U `onCreate` – samo brisanje (dok ne uradiš folder 11)

```java
button.setOnClickListener(v -> postRepository.obrisiPrviPost(null));
```

### U `onCreate` – sa notifikacijom (posle `11-notifikacija-prazna-baza/`)

```java
import com.example.kolokvijum2.helper.NotifikacijaHelper;

// u onCreate, jednom:
NotifikacijaHelper.kreirajKanal(this);

button.setOnClickListener(v -> postRepository.obrisiPrviPost(
        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
));
```

> **Ne piši** `obrisiPrviPost()` u MainActivity – metoda je u `PostRepository`. Callback `onEmpty` se poziva kad je `count() == 0`.

---

## Alternativa: inline u `MainActivity.java` (zastarelo)

### Importi

```java
import com.example.kolokvijum2.model.Post;
```

### U `onCreate`

```java
button.setOnClickListener(v -> obrisiPrviPost());
```

### Metoda za brisanje

```java
private void obrisiPrviPost() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        postDao.delete(prvi);
    }
    // Notifikacija → folder 11
}
```

---

## Važno: odvojeno od akcelerometra

Na kolokvijumu je **isto dugme** i za brisanje i za tekst akcelerometra – to spajaš u **`16-spajanje-zadataka/`**.

Za vežbu po segmentima:

- **`12-senzor-akcelerometar/`** – samo menja tekst dugmeta (bez klika)
- **Ovaj folder** – samo `setOnClickListener` za brisanje

Ne testiraj oba na istom dugmetu dok ne vežbaš spajanje.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `PostRepository.obrisiPrviPost()` | `postDao.deleteFirst()` – jedna SQL komanda u DAO-u |

---

## Checklist

- [ ] `PostRepository.obrisiPrviPost()` pozvan iz listenera
- [ ] Klik briše prvi red (ne post sa fiksnim id=1)
- [ ] Radi bez notifikacije (ili sa 11)
- [ ] Ne meša se sa senzorom u ovom koraku

---

## Sledeći korak

**`11-notifikacija-prazna-baza/`** – notifikacija "Nema više postova!"
