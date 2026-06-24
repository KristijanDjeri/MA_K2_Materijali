# Firebase Firestore – postovi u oblaku

**Dodatni segment.** **Slično:** Room + Retrofit (zadaci 5–7).

**Cilj:**
- Učitaj postove iz Firestore kolekcije `posts`
- Upiši novi post
- Obriši prvi dokument (kao zadatak 7)

---

## Preduslovi

- `51-firebase-setup/` – Firestore baza kreirana u konzoli
- `implementation 'com.google.firebase:53-firebase-firestore'` u Gradle

---

## Struktura u Firestore

Kolekcija: **`posts`**  
Svaki dokument ima polja (kao Room model):

| Polje | Tip |
|-------|-----|
| `id` | number |
| `title` | string |
| `body` | string |
| `userId` | number |

U konzoli možeš ručno dodati dokumente za test.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`FirestorePostsHelper.java`** | `app/.../helper/` |
| 2 | `07-ucitaj-10-postova/` | `PostRepository` (hibrid: Firestore → Room) |
| 3 | `MainActivity.java` | Polje + init u **`onCreate`** |

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import com.example.kolokvijum2.helper.FirestorePostsHelper;
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.PostRepository;
```

### Polje i init

```java
private FirestorePostsHelper firestoreHelper;

// onCreate, posle postRepository:
firestoreHelper = new FirestorePostsHelper(this, postRepository);

button.setOnClickListener(v -> firestoreHelper.ucitajPostove(
        new FirestorePostsHelper.OnCountListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this,
                        "Učitano " + count + " iz Firestore", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
));

// Toast prvog posta:
// firestoreHelper.prikaziTitlePrvog(null);

// Brisanje + notifikacija:
// firestoreHelper.obrisiPrvi(() -> NotifikacijaHelper.posaljiPraznaBaza(this));
```

> **Ne piši** `ucitajPostoveIzFirestore()` u MainActivity – logika je u `FirestorePostsHelper`.  
> Za Switch ON/OFF zameni `PostRepository` pozive u `SwitchPostsHelper` sa `FirestorePostsHelper` (ili hibrid: Firestore učitava → `postRepository.insertPosts`).

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## FirestorePostsHelper – metode (pozovi iz MainActivity)

| Metoda | Zamena za |
|--------|----------|
| `ucitajPostove(listener)` | Retrofit GET + Room insert (07) |
| `prikaziTitlePrvog(listener)` | Toast prvog posta (08) |
| `obrisiPrvi(onEmpty)` | Brisanje prvog (10) |
| `dodajPost(naslov, body, userId)` | Novi dokument u Firestore |

---

## Model klasa sa Firestore anotacijama (alternativa Map)

Kreiraj `PostFirestore.java`:

```java
package com.example.kolokvijum2.model;

public class PostFirestore {
    private int id;
    private String title;
    private String body;
    private int userId;

    public PostFirestore() {} // obavezan prazan konstruktor za Firestore

    public PostFirestore(int id, String title, String body, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
```

Čitanje:

```java
for (QueryDocumentSnapshot doc : snapshot) {
    PostFirestore p = doc.toObject(PostFirestore.class);
    if (p != null) {
        // koristi p.getTitle()
    }
}
```

---

## Hibrid: Firestore + Room (preporuka za kolokvijum)

1. Firestore GET → upiši u Room (`postDao.insertAll`)
2. Ostatak app koristi Room kao u zvaničnom zadatku
3. Profesor vidi da znaš i cloud i lokalnu bazu

```java
Post p = doc.toObject(Post.class);
if (p != null) postDao.insert(p);
```

*(Room `Post` mora imati prazan konstruktor – već ima.)*

---

## Checklist

- [ ] `FirestorePostsHelper` u paketu `helper`
- [ ] Kolekcija `posts` sa test podacima
- [ ] `.limit(10).get()` za učitavanje
- [ ] `.delete()` za brisanje
- [ ] Notifikacija kad je prazno (preko callback-a)

---

## Sledeći korak

**`54-firebase-fcm/`** – push notifikacije (opciono).
