# Toast sa naslovom prvog posta (zadatak 6, deo 2)

**Cilj:** Prikaži **Toast** sa `title` **prvog posta u tabeli** (prvi red, ne nužno `id = 1`).

Radi **samostalno** – samo čita iz Room baze. Ne zove API.

---

## Šta ti treba pre ovoga

- `05-room-baza/` – `postDao`
- `07-ucitaj-10-postova/` – **`PostRepository`** (već ima metodu `prikaziTitlePrvogPosta()`)
- U bazi bar jedan post (ručno `insert` ili prethodno `07-ucitaj-10-postova/`)

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

### U `onCreate` (samostalni test)

```java
// postRepository već inicijalizovan u 07-ucitaj-10-postova/
button.setOnClickListener(v -> postRepository.prikaziTitlePrvogPosta());
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.widget.Toast;
import com.example.kolokvijum2.model.Post;

// U onCreate():
button.setOnClickListener(v -> prikaziTitlePrvogPosta());

// METODA:

private void prikaziTitlePrvogPosta() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
    }
}
```

## Brzi test bez API-ja

U `onCreate`, pre listenera:

```java
postDao.insert(new Post(99, "Probni naslov", "body", 1));
```

Klik na dugme → Toast **"Probni naslov"**.

---

## Na ispitu (Switch)

U `09-switch-listener/` `SwitchPostsHelper` poziva `postRepository.prikaziTitlePrvogPosta()` kada je `isPostsUcitani() == true` i Switch je ON.

---

## Checklist

- [ ] `PostRepository.prikaziTitlePrvogPosta()` pozvan iz listenera
- [ ] `getFirst()` vraća prvi red tabele
- [ ] Toast prikazuje `getTitle()`
- [ ] Poruka kad je baza prazna

---

## Sledeći korak

**`09-switch-listener/`** – spaja prvi put (učitaj) i drugi put (Toast) na Switch ON.
