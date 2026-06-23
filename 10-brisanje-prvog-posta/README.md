# Brisanje prvog posta (zadatak 7, deo 1)

**Cilj:** Klik na `Button` → obriši post na **prvoj poziciji** u bazi (prvi red).

Radi **samostalno** – **bez notifikacije**. Kad tabela ostane prazna, notifikaciju dodaješ iz `11-notifikacija-prazna-baza/`.

---

## Šta ti treba pre ovoga

- `05-room-baza/` – `postDao`
- `button` u layoutu
- U bazi bar jedan post (npr. iz `07-ucitaj-10-postova/`)

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | `obrisiPrviPost()` + listener na dugme |

---

## Kompletan kod

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

    if (postDao.count() == 0) {
        posaljiNotifikacijuPrazneBaze(); // iz 11-notifikacija-prazna-baza/
    }
}
```

### Privremeno (dok ne uradiš folder 11)

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
| `getFirst()` + `delete(prvi)` | `postDao.deleteFirst()` – jedna SQL komanda |

---

## Checklist

- [ ] Klik briše prvi red (ne post sa fiksnim id=1)
- [ ] Radi bez notifikacije (ili sa 11)
- [ ] Ne meša se sa senzorom u ovom koraku

---

## Sledeći korak

**`11-notifikacija-prazna-baza/`** – notifikacija "Nema više postova!"
