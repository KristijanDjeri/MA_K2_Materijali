# RecyclerView – lista postova iz baze

**Dodatni segment** (nije u PDF-u, ali česta varijanta).  
**Slično:** Room baza + prikaz podataka (zadaci 5–7).

**Cilj:** Prikaži sve postove iz baze u listi. Klik na stavku → Toast sa naslovom.

---

## Preduslovi

- `05-room-baza/` + `07-ucitaj-10-postova/` – `PostRepository`
- Gradle: `implementation 'androidx.recyclerview:recyclerview:1.3.2'`

---

## Fajlovi koje praviš / kopiraš

| Fajl | Putanja |
|------|---------|
| `item_post.xml` | `res/layout/item_post.xml` |
| `PostAdapter.java` | `.../adapter/PostAdapter.java` |
| **`RecyclerViewPostsHelper.java`** | `.../helper/RecyclerViewPostsHelper.java` |
| Izmene u `activity_main.xml` | dodaj RecyclerView |

---

## 1. `res/layout/item_post.xml` (ceo fajl)

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tvTitle"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="12dp"
    android:textSize="16sp" />
```

---

## 2. U `activity_main.xml` dodaj

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="200dp" />
```

---

## 3. `PostAdapter.java`

Kopiraj **`PostAdapter.java`** iz ovog foldera u `app/.../adapter/`.

---

## 4. MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import androidx.recyclerview.widget.RecyclerView;
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.helper.RecyclerViewPostsHelper;
```

### Polja

```java
private RecyclerViewPostsHelper recyclerViewPostsHelper;
```

### U `onCreate`

```java
RecyclerView recyclerView = findViewById(R.id.recyclerView);
recyclerViewPostsHelper = new RecyclerViewPostsHelper(recyclerView, postRepository);
```

### Posle insert/delete

```java
recyclerViewPostsHelper.osvezi();
```

Primer u callback-u `PostRepository`:

```java
postRepository.ucitajPostoveSaApi(new PostRepository.OnApiDoneListener() {
    @Override
    public void onSuccess(int count) {
        recyclerViewPostsHelper.osvezi();
    }
    @Override
    public void onFailure(String message) { }
});
```

> **Ne piši** `osveziListuPostova()` u MainActivity – `RecyclerViewPostsHelper.osvezi()` čita iz `PostRepository.getAll()`.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `RecyclerViewPostsHelper` + `PostAdapter` u projektu
- [ ] RecyclerView u layoutu
- [ ] `osvezi()` posle insert/delete
