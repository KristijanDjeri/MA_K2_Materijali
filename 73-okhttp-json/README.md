# OkHttp – ručno čitanje JSON-a

**Dodatni segment.** **Alternativa** Retrofit-u (zadatak 5).

**Cilj:** Preuzmi JSON sa URL-a u pozadini, parsiraj Gson-om, upiši u Room.

---

## 1. Gradle (`app/build.gradle`)

```gradle
implementation 'com.squareup.okhttp3:okhttp:4.12.0'
implementation 'com.google.code.gson:gson:2.10.1'
```

Sync Now.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `app/build.gradle` | OkHttp + Gson u `dependencies` |
| 2 | **`OkHttpHelper.java`** | Novi fajl → `app/.../helper/` |
| 3 | `MainActivity.java` | Polje + init u **`onCreate`**, poziv `ucitajPostove()` |
| 4 | `MainActivity.java` | **`onDestroy`**: `okHttpHelper.shutdown()` |

---

## Kompletan kod – helper klasa

Kopiraj **`OkHttpHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.OkHttpHelper;
```

### Polje i poziv

```java
private OkHttpHelper okHttpHelper;

// onCreate (posle postDao i progressBar):
okHttpHelper = new OkHttpHelper(this, postDao, progressBar);
okHttpHelper.ucitajPostove(
        "https://dummy-json.mock.beeceptor.com/posts",
        new OkHttpHelper.OnDoneListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this, "Učitano " + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
);
```

### U `onDestroy`

```java
if (okHttpHelper != null) {
    okHttpHelper.shutdown();
}
```

> **Alternativa:** inline OkHttp ispod.

---

## 2. Alternativa: inline u `MainActivity.java`

### Importi

```java
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.lang.reflect.Type;
import java.util.List;
```

### Polja (iz 74-thread-executor – ili kreiraj ovde)

```java
private final ExecutorService executor = Executors.newSingleThreadExecutor();
private final Handler mainHandler = new Handler(Looper.getMainLooper());
```

### Metoda

```java
private void ucitajJsonOkHttp() {
    executor.execute(() -> {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://dummy-json.mock.beeceptor.com/posts")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type tip = new TypeToken<List<Post>>() {}.getType();
                    List<Post> postovi = gson.fromJson(json, tip);

                    mainHandler.post(() -> {
                        int n = Math.min(10, postovi.size());
                        postDao.insertAll(postovi.subList(0, n));
                        postsUcitani = true;
                        Toast.makeText(this, "Učitano OkHttp", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        } catch (Exception e) {
            mainHandler.post(() ->
                    Toast.makeText(this, "Greška", Toast.LENGTH_SHORT).show());
        }
    });
}
```

### Poziv umesto Retrofit-a (u `SwitchPostsHelper` ili test dugme)

```java
okHttpHelper.ucitajPostove(
        "https://dummy-json.mock.beeceptor.com/posts",
        new OkHttpHelper.OnDoneListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this, "Učitano " + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
);
```

> Za Switch OFF/ON logiku i dalje koristi `SwitchPostsHelper` + `PostRepository` – zameni samo učitavanje sa API-ja.

---

## Alternativa

- Retrofit – manje koda, preporučeno na kolokvijumu
- `HttpURLConnection` – stariji Java API

---

## Checklist

- [ ] OkHttp + Gson u Gradle
- [ ] `execute()` u pozadini
- [ ] UI na main thread
- [ ] TypeToken za `List<Post>`
