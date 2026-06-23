# Retrofit POST zahtev

**Dodatni segment.** **Slično:** Retrofit GET (zadatak 5).

**Cilj:** Pošalji novi post na server metodom POST.

---

## 1. U `JsonPlaceholderApi.java` dodaj metodu

```java
import retrofit2.http.Body;
import retrofit2.http.POST;

@POST("posts")
Call<Post> createPost(@Body Post post);
```

> **Napomena:** Za test često koriste `https://jsonplaceholder.typicode.com/` kao base URL. Za kolokvijum mock možda ne čuva POST – dovoljan je Toast "Poslato" u `onResponse`.

---

## 2. (Opciono) Drugi RetrofitClient za test

```java
private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
```

**Alternativa:** Zadrži isti client – zavisi šta profesor da na času.

---

## 3. U `MainActivity.java`

### Importi (već imaš iz 09-switch-listener)

```java
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
```

### Metoda

```java
private void posaljiPostNaServer() {
    Post novi = new Post(0, "Naslov sa uređaja", "Tekst posta", 1);

    RetrofitClient.getApi().createPost(novi).enqueue(new Callback<Post>() {
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if (response.isSuccessful()) {
                Toast.makeText(MainActivity.this, "POST uspešan", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    postDao.insert(response.body());
                }
            }
        }

        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            Toast.makeText(MainActivity.this, "POST greška", Toast.LENGTH_SHORT).show();
        }
    });
}
```

### Poziv (npr. dugi klik na button)

```java
button.setOnLongClickListener(v -> {
    posaljiPostNaServer();
    return true;
});
```

---

## Alternativa

- `@FormUrlEncoded` + `@Field` – slanje forme umesto JSON
- OkHttp bez Retrofit-a → folder `73-okhttp-json/`

---

## Checklist

- [ ] `@POST` u interfejsu
- [ ] `@Body Post post`
- [ ] `enqueue` sa callback-om
