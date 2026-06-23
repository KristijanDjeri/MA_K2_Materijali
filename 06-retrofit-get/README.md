# Retrofit GET (zadatak 5, deo 2)

**Cilj:** Podesiti **Retrofit** za **GET** zahtev i **testirati** da API vraća JSON.

Ovaj segment radi **samostalno** – ne upisuje u Room. Samo proverava mrežu (npr. Toast sa brojem postova).

> **Testirano (jun 2026):** URL iz PDF-a vraća **HTML**. Radni endpoint:  
> `https://dummy-json.mock.beeceptor.com/posts`

---

## Šta ti treba pre ovoga

- Gradle zavisnosti za **Retrofit** (`01-osnovni-projekat/`)
- Dozvola `INTERNET` u Manifest-u

---

## Koji fajlovi se prave

| Fajl | Putanja |
|------|---------|
| JsonPlaceholderApi.java | `.../api/JsonPlaceholderApi.java` |
| RetrofitClient.java | `.../api/RetrofitClient.java` |

Gotovi fajlovi u ovom folderu. Za radni URL koristi **`JsonPlaceholderApi-alternativa.java`** i **`RetrofitClientAlternativa.java`** (preimenuj u glavna imena).

---

## Fajl 1: `JsonPlaceholderApi.java`

```java
package com.example.kolokvijum2.api;

import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("posts")
    Call<List<Post>> getPosts();
}
```

---

## Fajl 2: `RetrofitClient.java`

```java
package com.example.kolokvijum2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://dummy-json.mock.beeceptor.com/";
    private static Retrofit retrofit;

    public static JsonPlaceholderApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(JsonPlaceholderApi.class);
    }
}
```

> Za URL iz PDF-a koristi `https://app.beeceptor.com/` i `@GET("mock-server/dummy-json")` – proveri na ispitu da li radi.

---

## Samostalni test u `MainActivity` (dugme ili Switch)

Ovaj primer **ne koristi** `postDao`. Samo GET + Toast.

### Importi

```java
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
```

### Metoda

```java
private void testirajGet() {
    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                int n = response.body().size();
                Toast.makeText(MainActivity.this,
                        "API vratio " + n + " postova", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Neuspešan odgovor", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
```

### Poziv (privremeno dugme u layoutu ili postojeći `button`)

```java
button.setOnClickListener(v -> testirajGet());
```

---

## API odgovor (format)

```json
[
  { "userId": 1, "id": 1, "title": "...", "body": "..." }
]
```

Gson mapira na klasu `Post` iz `05-room-baza/`.

---

## Checklist

- [ ] Retrofit + Gson u Gradle-u
- [ ] `INTERNET` u Manifest-u
- [ ] GET vraća listu (Toast sa brojem)
- [ ] Na ispitu spoji sa `07-ucitaj-10-postova/` za upis u bazu

---

## Sledeći korak

**`07-ucitaj-10-postova/`** – isti GET, ali upis prvih 10 u Room.
