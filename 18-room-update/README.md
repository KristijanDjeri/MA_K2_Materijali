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


---


## Napomena

- `@Update` traži **primarni ključ** (`id`) – Room zna koji red da menja.
- `@Insert(onConflict = REPLACE)` ponekad zamenjuje UPDATE – ali eksplicitni `@Update` je jasniji na ispitu.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// Deo za MainActivity (folder 18-room-update/)

private void izmeniTitlePrvogPosta(String noviTitle) {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        prvi.setTitle(noviTitle);
        postDao.update(prvi);
        Toast.makeText(this, "Ažurirano: " + noviTitle, Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Nema postova", Toast.LENGTH_SHORT).show();
    }
}
```

## Checklist

- [ ] `@Update` u `PostDao` (iz `05-room-baza/`)
- [ ] `PostRepository.izmeniTitlePrvogPosta()` pozvan iz listenera
- [ ] Proveri Toast-om ili `getFirst()` posle izmene

---

## Povezano

- Baza: `05-room-baza/`
- Alert pre izmene: `17-alert-dialog/`
