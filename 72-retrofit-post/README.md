# Retrofit POST zahtev

**Dodatni segment.** **Slično:** Retrofit GET (zadatak 5).

**Cilj:** Pošalji novi post na server metodom POST – logika u **`PostRepository`**.

---

## 1. U `JsonPlaceholderApi.java` dodaj metodu

```java
import retrofit2.http.Body;
import retrofit2.http.POST;

@POST("posts")
Call<Post> createPost(@Body Post post);
```

> Već uključeno u `06-retrofit-get/JsonPlaceholderApi.java` ako si kopirao ceo fajl.

---

## 2. MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.PostRepository;
```

### U `onCreate`

```java
button.setOnLongClickListener(v -> {
    postRepository.posaljiPostNaServer(new PostRepository.OnApiDoneListener() {
        @Override
        public void onSuccess(int count) {
            Toast.makeText(MainActivity.this, "POST uspešan", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(String message) {
            Toast.makeText(MainActivity.this, "POST greška: " + message, Toast.LENGTH_SHORT).show();
        }
    });
    return true;
});
```

> **Ne piši** `posaljiPostNaServer()` u MainActivity – metoda je u `PostRepository` (folder `07-ucitaj-10-postova/`).

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `@POST` u `JsonPlaceholderApi`
- [ ] `PostRepository.posaljiPostNaServer()` pozvan
- [ ] Toast na uspeh/grešku
