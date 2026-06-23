# Room baza – model i DAO (zadatak 5, deo 1)

**Cilj:** Napraviti **model posta** u bazi (Room) – bez mrežnog poziva.

Ovaj segment radi **samostalno**: samo kreira tabele i `PostDao`. API i upis postova su u sledećim folderima.

---

## Šta ti treba pre ovoga

- Gradle zavisnosti za **Room** (`01-osnovni-projekat/`)

---

## Koji fajlovi se prave (NOVI fajlovi)

| Fajl | Putanja u projektu |
|------|-------------------|
| Post.java | `app/src/main/java/com/example/kolokvijum2/model/Post.java` |
| PostDao.java | `app/src/main/java/com/example/kolokvijum2/db/PostDao.java` |
| AppDatabase.java | `app/src/main/java/com/example/kolokvijum2/db/AppDatabase.java` |

**Kako napraviti pakete:** desni klik na `com.example.kolokvijum2` → **New → Package** → `model`, pa `db`.

U ovom folderu su gotovi fajlovi: `Post.java`, `PostDao.java`, `AppDatabase.java`.

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

> **Alternativa:** `allowMainThreadQueries()` olakšava kolokvijum; u produkciji koristi pozadinsku nit → `25-thread-executor/`.

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

### (Opciono) Brzi test bez API-ja

```java
// Samo da proveriš da Room radi – obriši posle testa
postDao.insert(new Post(1, "Test naslov", "Test body", 1));
```

---

## Checklist

- [ ] 3 Java fajla kreirana
- [ ] Gradle Sync bez greške
- [ ] `postDao` inicijalizovan u MainActivity

---

## Sledeći koraci (nezavisno)

| Folder | Šta radi |
|--------|----------|
| **`06-retrofit-get/`** | Retrofit GET – test mreže (bez upisa u bazu) |
| **`07-ucitaj-10-postova/`** | API → upis 10 postova u bazu |
| **`08-toast-prvi-post/`** | Toast sa `title` prvog reda u bazi |

Na ispitu sve spajaš u **`16-spajanje-zadataka/`** ili **`15-main-activity-referenca/`**.
