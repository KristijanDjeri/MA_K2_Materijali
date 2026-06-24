# Room UPDATE – izmena posta u bazi

**Dodatni segment.** **Slično:** insert i delete iz `05-room-baza/`, ali **menja** postojeći red.

**Cilj:** Izmeni npr. `title` prvog posta u bazi (dugme ili Switch).

---

## Koji fajlovi se menjaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `PostDao.java` | `@Update` – **već u** `05-room-baza/PostDao.java` (kopiraj ceo fajl ako nemaš) |
| 2 | `PostRepository.java` | Metoda `izmeniTitlePrvogPosta()` – **već u** `07-ucitaj-10-postova/PostRepository.java` |
| 3 | `MainActivity.java` | Listener na dugme (primer ispod) |

---

## 1. `PostDao.java` – `@Update`

Ako si kopirao `PostDao.java` iz `05-room-baza/`, ovo **već postoji**:

```java
import androidx.room.Update;

@Update
void update(Post post);
```

Dodatno (opciono) u `PostDao.java`:

```java
@Update
void updateAll(List<Post> posts);

@Query("UPDATE posts SET title = :noviTitle WHERE rowid = (SELECT rowid FROM posts LIMIT 1)")
void updateTitleFirst(String noviTitle);
```

Vidi i `PostDaoUpdate.java` u ovom folderu.

---

## 2. MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.model.Post;
```

### Test – dugme dodaje „ (izmenjeno)“ na kraj naslova

```java
button.setOnClickListener(v -> {
    Post prvi = postRepository.getFirst();
    if (prvi != null) {
        postRepository.izmeniTitlePrvogPosta(prvi.getTitle() + " (izmenjeno)");
    }
});
```

> **Ne piši** `izmeniTitlePrvogPosta()` u MainActivity – metoda je u `PostRepository` i interno poziva `postDao.update()`.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Napomena

- `@Update` traži **primarni ključ** (`id`) – Room zna koji red da menja.
- `@Insert(onConflict = REPLACE)` ponekad zamenjuje UPDATE – ali eksplicitni `@Update` je jasniji na ispitu.

---

## Checklist

- [ ] `@Update` u `PostDao` (iz `05-room-baza/`)
- [ ] `PostRepository.izmeniTitlePrvogPosta()` pozvan iz listenera
- [ ] Proveri Toast-om ili `getFirst()` posle izmene

---

## Povezano

- Baza: `05-room-baza/`
- Alert pre izmene: `17-alert-dialog/`
