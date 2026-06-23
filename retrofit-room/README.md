# Retrofit + Room baza (zadatak 5)

**Cilj:** Napraviti **model posta** u bazi i podesiti **Retrofit** za **GET** zahtev na:  
`https://app.beeceptor.com/mock-server/dummy-json`

---

## Šta ti treba pre ovoga

- Gradle zavisnosti za Retrofit i Room (`osnovni-projekat/`)
- Dozvola `INTERNET` u Manifest-u

---

## Koji fajlovi se prave (NOVI fajlovi)

| Fajl | Putanja u projektu |
|------|-------------------|
| Post.java | `app/src/main/java/com/example/kolokvijum2/model/Post.java` |
| PostDao.java | `app/src/main/java/com/example/kolokvijum2/db/PostDao.java` |
| AppDatabase.java | `app/src/main/java/com/example/kolokvijum2/db/AppDatabase.java` |
| JsonPlaceholderApi.java | `app/src/main/java/com/example/kolokvijum2/api/JsonPlaceholderApi.java` |
| RetrofitClient.java | `app/src/main/java/com/example/kolokvijum2/api/RetrofitClient.java` |

**Kako napraviti pakete:** desni klik na `com.example.kolokvijum2` → **New → Package** → npr. `model`, pa `db`, pa `api`.

---

## Fajl 1: `Post.java` (ceo kod)

```java
package com.example.kolokvijum2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey
    private int id;

    private String title;
    private String body;
    private int userId;

    public Post() {
    }

    public Post(int id, String title, String body, int userId) {
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

---

## Fajl 2: `PostDao.java` (ceo kod)

```java
package com.example.kolokvijum2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kolokvijum2.model.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Query("SELECT * FROM posts LIMIT 1")
    Post getFirst();

    @Query("SELECT * FROM posts")
    List<Post> getAll();

    @Query("SELECT COUNT(*) FROM posts")
    int count();

    @Delete
    void delete(Post post);

    @Query("DELETE FROM posts WHERE rowid IN (SELECT rowid FROM posts LIMIT 1)")
    void deleteFirst();
}
```

---

## Fajl 3: `AppDatabase.java` (ceo kod)

```java
package com.example.kolokvijum2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kolokvijum2.model.Post;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract PostDao postDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "kolokvijum_db"
                    ).allowMainThreadQueries()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
```

> **Alternativa:** `allowMainThreadQueries()` olakšava kolokvijum, ali u „pravoj" aplikaciji koristi pozadinsku nit → folder `thread-executor/`.

---

## Fajl 4: `JsonPlaceholderApi.java` (ceo kod)

```java
package com.example.kolokvijum2.api;

import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("mock-server/dummy-json")
    Call<List<Post>> getPosts();
}
```

---

## Fajl 5: `RetrofitClient.java` (ceo kod)

```java
package com.example.kolokvijum2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://app.beeceptor.com/";
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

---

## Deo u `MainActivity.java` (inicijalizacija baze)

### Importi

```java
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;
```

### Polje

```java
private PostDao postDao;
```

### U `onCreate`

```java
postDao = AppDatabase.getInstance(this).postDao();
```

GET poziv i upis u bazu ide u folderu **`switch-postovi/`** (zadatak 6).

---

## Alternativa ako URL ne radi

Ako mock server ne vrati JSON, koristi:

**RetrofitClient.java:**
```java
private static final String BASE_URL = "https://dummy-json.mock.beeceptor.com/";
```

**JsonPlaceholderApi.java:**
```java
@GET("posts")
Call<List<Post>> getPosts();
```

---

## API odgovor (format)

```json
[
  { "userId": 1, "id": 1, "title": "...", "body": "..." }
]
```

Gson automatski mapira na klasu `Post`. Dodatna polja u JSON-u (npr. `link`) se ignorišu.

---

## Checklist

- [ ] 5 novih Java fajlova kreirano
- [ ] Package imena ispravna (`com.example.kolokvijum2...`)
- [ ] Gradle Sync bez greške
- [ ] `postDao` inicijalizovan u MainActivity

---

## Sledeći korak

Folder **`switch-postovi/`** za zadatak 6.
