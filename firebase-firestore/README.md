# Firebase Firestore – postovi u oblaku

**Dodatni segment.** **Slično:** Room + Retrofit (zadaci 5–7).

**Cilj:**
- Učitaj postove iz Firestore kolekcije `posts`
- Upiši novi post
- Obriši prvi dokument (kao zadatak 7)

---

## Preduslovi

- `firebase-setup/` – Firestore baza kreirana u konzoli
- `implementation 'com.google.firebase:firebase-firestore'` u Gradle

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

## Kompletan kod za `MainActivity.java`

### 1. Importi

```java
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;
```

### 2. Polje

```java
private FirebaseFirestore firestore;
```

### 3. U `onCreate`

```java
firestore = FirebaseFirestore.getInstance();
```

---

## Učitaj postove (zamena za Retrofit GET + Room insert)

```java
private void ucitajPostoveIzFirestore() {
    firestore.collection("posts")
            .limit(10)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    QuerySnapshot snapshot = task.getResult();
                    int broj = 0;
                    for (QueryDocumentSnapshot doc : snapshot) {
                        String title = doc.getString("title");
                        // Opciono: upiši i u lokalni Room
                        // Post p = doc.toObject(Post.class);
                        // postDao.insert(p);
                        broj++;
                    }
                    postsUcitani = true;
                    Toast.makeText(this, "Učitano " + broj + " postova", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Firestore greška", Toast.LENGTH_SHORT).show();
                }
            });
}
```

Pozovi iz `obradiSwitchOn()` umesto `ucitajPostoveSaApi()`:

```java
if (!postsUcitani) {
    ucitajPostoveIzFirestore();
}
```

---

## Toast sa title prvog posta (zadatak 6 – drugi put ON)

```java
private void prikaziPrviPostIzFirestore() {
    firestore.collection("posts")
            .limit(1)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    QueryDocumentSnapshot doc = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                    String title = doc.getString("title");
                    Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nema postova", Toast.LENGTH_SHORT).show();
                }
            });
}
```

---

## Dodaj novi post u Firestore

```java
private void dodajPostUFirestore(String naslov, String body, int userId) {
    Map<String, Object> post = new HashMap<>();
    post.put("id", System.currentTimeMillis());
    post.put("title", naslov);
    post.put("body", body);
    post.put("userId", userId);

    firestore.collection("posts")
            .add(post)
            .addOnSuccessListener(ref ->
                    Toast.makeText(this, "Post dodat: " + ref.getId(), Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e ->
                    Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show());
}
```

> **Napomena:** `.add()` generiše auto ID dokumenta. Za fiksni `id` koristi `.document("123").set(post)`.

---

## Obriši prvi post (zadatak 7 – Firestore varijanta)

```java
private void obrisiPrviPostFirestore() {
    firestore.collection("posts")
            .limit(1)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    String docId = task.getResult().getDocuments().get(0).getId();
                    firestore.collection("posts").document(docId)
                            .delete()
                            .addOnSuccessListener(v -> proveriPraznuKolekciju());
                }
            });
}

private void proveriPraznuKolekciju() {
    firestore.collection("posts")
            .limit(1)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && (task.getResult() == null || task.getResult().isEmpty())) {
                    posaljiNotifikaciju("Nema više postova!");
                }
            });
}
```

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

- [ ] Firestore baza u konzoli
- [ ] Kolekcija `posts` sa test podacima
- [ ] `.limit(10).get()` za učitavanje
- [ ] `.delete()` za brisanje
- [ ] Notifikacija kad je prazno

---

## Sledeći korak

**`firebase-fcm/`** – push notifikacije (opciono).
