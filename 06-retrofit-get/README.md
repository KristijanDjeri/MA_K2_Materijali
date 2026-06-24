# Retrofit GET (zadatak 5, deo 2)

**Cilj:** Podesiti **Retrofit** za **GET** zahtev i **testirati** da API vraća JSON.

Ovaj segment radi **samostalno** – ne upisuje u Room. Samo proverava mrežu (npr. Toast sa brojem postova).

> **Podrazumevani URL (jun 2026):** `https://jsonplaceholder.typicode.com/posts`  
> Isti format JSON-a (`id`, `title`, `body`, `userId`) – pouzdaniji na emulatoru.  
> Beeceptor mock (iz PDF-a): `RetrofitClientAlternativa.java` → `dummy-json.mock.beeceptor.com`

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

Gotovi fajlovi u ovom folderu. **Beeceptor** (mock sa ispita) je u `RetrofitClientAlternativa.java`.

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

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
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

> Za URL sa kolokvijuma (Beeceptor): zameni sa `RetrofitClientAlternativa.java` ili `https://app.beeceptor.com/` + `@GET("mock-server/dummy-json")` – proveri na ispitu.

---

## Greška: „Unable to resolve host …“

Poruka znači da **telefon/emulator ne može da pronađe server** (DNS/mreža), ne da je Retrofit pogrešno podešen.

**Proveri redom:**

1. **INTERNET dozvola** u `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```
2. **Internet na emulatoru** – otvori Chrome na emulatoru i učitaj bilo koji sajt.
3. **Koristi podrazumevani URL** iz ovog foldera (`jsonplaceholder.typicode.com`) – često radi kad Beeceptor ne prolazi DNS na emulatoru.
4. **Emulator:** Device Manager → ⋮ na emulatoru → **Cold Boot** ili **Wipe Data**.
5. **Fizički telefon** – uključen Wi‑Fi/mobilni; VPN/firewall ponekad blokira mock domene.
6. Na **kolokvijumu** – ako profesor da drugi URL, zameni samo `BASE_URL` i `@GET(...)` u API interfejsu.

---

## MainActivity – samo povezivanje

Ovaj primer **ne koristi** `postDao`. Samo GET + Toast.

### Import

```java
import com.example.kolokvijum2.helper.RetrofitGetHelper;
```

### U `onCreate`

```java
button.setOnClickListener(v -> RetrofitGetHelper.testGet(this,
        new RetrofitGetHelper.Listener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this,
                        "API vratio " + count + " postova", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this,
                        "Greška: " + message, Toast.LENGTH_SHORT).show();
            }
        }
));
```

Kopiraj **`RetrofitGetHelper.java`** iz ovog foldera u `app/.../helper/`.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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
