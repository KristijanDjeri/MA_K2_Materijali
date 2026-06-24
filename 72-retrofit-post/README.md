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


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// METODA:
private void posaljiPostNaServer() {
    Post novi = new Post(0, "Naslov sa uređaja", "Tekst posta", 1);

    RetrofitClient.getApi().createPost(novi).enqueue(new Callback<Post>() {
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if (response.isSuccessful()) {
                Toast.makeText(MainActivity.this, "POST uspešan", Toast.LENGTH_SHORT).show();
                // opciono: upiši i u lokalnu bazu
                // postDao.insert(response.body());
            }
        }

        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            Toast.makeText(MainActivity.this, "POST greška", Toast.LENGTH_SHORT).show();
        }
    });
}

// U JsonPlaceholderApi dodaj:
// @POST("posts")
// Call<Post> createPost(@Body Post post);
```

## Checklist

- [ ] `@POST` u `JsonPlaceholderApi`
- [ ] `PostRepository.posaljiPostNaServer()` pozvan
- [ ] Toast na uspeh/grešku
