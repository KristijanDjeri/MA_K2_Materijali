# Toast sa naslovom prvog posta (zadatak 6, deo 2)

**Cilj:** Prikaži **Toast** sa `title` **prvog posta u tabeli** (prvi red, ne nužno `id = 1`).

Radi **samostalno** – samo čita iz Room baze. Ne zove API.

---

## Šta ti treba pre ovoga

- `05-room-baza/` – `postDao`
- U bazi bar jedan post (ručno `insert` ili prethodno `07-ucitaj-10-postova/`)

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | Metoda `prikaziTitlePrvogPosta()` + listener |

---

## Kompletan kod

### Importi

```java
import android.widget.Toast;
import com.example.kolokvijum2.model.Post;
```

### U `onCreate` (samostalni test)

```java
// Npr. drugo dugme ili isto dugme posle što si učitao postove
button.setOnClickListener(v -> prikaziTitlePrvogPosta());
```

### Metoda

```java
private void prikaziTitlePrvogPosta() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
    }
}
```

---

## Brzi test bez API-ja

U `onCreate`, pre listenera:

```java
postDao.insert(new Post(99, "Probni naslov", "body", 1));
```

Klik na dugme → Toast **"Probni naslov"**.

---

## Na ispitu (Switch)

U `09-switch-listener/` pozivaš ovu metodu kada je `postsUcitani == true` i Switch je ON.

---

## Checklist

- [ ] `getFirst()` vraća prvi red tabele
- [ ] Toast prikazuje `getTitle()`
- [ ] Poruka kad je baza prazna

---

## Sledeći korak

**`09-switch-listener/`** – spaja prvi put (učitaj) i drugi put (Toast) na Switch ON.
