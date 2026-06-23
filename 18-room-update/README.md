# Room UPDATE – izmena posta u bazi

**Dodatni segment.** **Slično:** insert i delete iz `05-room-baza/`, ali **menja** postojeći red.

**Cilj:** Izmeni npr. `title` prvog posta u bazi (dugme ili Switch).

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `PostDao.java` | Dodaješ `@Update` |
| `MainActivity.java` | Metoda `izmeniPrviPost()` |

---

## 1. Proširi `PostDao.java`

```java
import androidx.room.Update;

@Update
void update(Post post);

@Update
void updateAll(List<Post> posts);
```

---

## 2. Kod u `MainActivity.java`

### Import

```java
import com.example.kolokvijum2.model.Post;
import android.widget.Toast;
```

### Izmena title prvog posta

```java
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

### Test – dugme dodaje „ (izmenjeno)“ na kraj naslova

```java
button.setOnClickListener(v -> {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        izmeniTitlePrvogPosta(prvi.getTitle() + " (izmenjeno)");
    }
});
```

### Varijanta – `@Query` UPDATE (jedna SQL komanda)

U `PostDao`:

```java
@Query("UPDATE posts SET title = :noviTitle WHERE rowid = (SELECT rowid FROM posts LIMIT 1)")
void updateTitleFirst(String noviTitle);
```

Poziv:

```java
postDao.updateTitleFirst("Novi naslov");
```

---

## Napomena

- `@Update` traži **primarni ključ** (`id`) – Room zna koji red da menja.
- `@Insert(onConflict = REPLACE)` ponekad zamenjuje UPDATE – ali eksplicitni `@Update` je jasniji na ispitu.

---

## Checklist

- [ ] `@Update` u DAO-u
- [ ] Učitaj post → promeni polje → `postDao.update(post)`
- [ ] Proveri Toast-om ili `getFirst()` posle izmene

---

## Povezano

- Baza: `05-room-baza/`
- Alert pre izmene: `17-alert-dialog/`
