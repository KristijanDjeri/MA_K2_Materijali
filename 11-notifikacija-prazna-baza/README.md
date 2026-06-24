# Notifikacija kad nema postova (zadatak 7, deo 2)

**Cilj:** Kad je baza postova **prazna**, pošalji notifikaciju sa tekstom **"Nema više postova!"**

Radi **samostalno** – možeš testirati bez brisanja (npr. poziv posle ručnog brisanja svih redova).

---

## Šta ti treba pre ovoga

- Dozvola `POST_NOTIFICATIONS` (Android 13+)
- Poziv iz `10-brisanje-prvog-posta/` kada je `postDao.count() == 0`

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`NotifikacijaHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | U `onCreate`: `NotifikacijaHelper.kreirajKanal(this)` |
| 3 | `MainActivity.java` | Kad je baza prazna: `NotifikacijaHelper.posaljiPraznaBaza(this)` |

---

## Kompletan kod – helper klasa

Kopiraj **`NotifikacijaHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.NotifikacijaHelper;
```

### U `onCreate`

```java
NotifikacijaHelper.kreirajKanal(this);
NotifikacijaHelper.proveriDozvolu(this);
```

### Kad je baza prazna (preko PostRepository callback-a)

U `10-brisanje-prvog-posta/` već koristiš:

```java
button.setOnClickListener(v -> postRepository.obrisiPrviPost(
        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
));
```

> **Ne piši** proveru `postDao.count() == 0` u MainActivity – `PostRepository.obrisiPrviPost()` poziva callback kad je baza prazna.

### Samostalni test (bez brisanja)

```java
// Privremeno u onCreate – obriši sve postove pa pozovi notifikaciju
postDao.deleteFirst(); // ponovi dok count != 0, ili obriši sve
if (postDao.count() == 0) {
    NotifikacijaHelper.posaljiPraznaBaza(this);
}
```

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Samostalni test

Vidi primer iznad u sekciji „Kad je baza prazna“.

---

## Više o notifikacijama

Pregled tipova: **`37-notifikacije-pregled/`** · akcije · proširena.

---

## Checklist

- [ ] NotificationChannel u `onCreate`
- [ ] Tekst tačno "Nema više postova!"
- [ ] API 33+ traži POST_NOTIFICATIONS

---

## Sledeći korak

**`12-senzor-akcelerometar/`** (zadatak 8)
