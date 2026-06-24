# Thread / Executor – Room u pozadini

**Dodatni segment.** **Slično:** Room operacije (zadaci 5–7), ali **bez** `allowMainThreadQueries()`.

**Cilj:** Baza se koristi u pozadinskoj niti; UI (Toast, notifikacija) na glavnoj niti.

---

## 1. U `AppDatabase.java` – ukloni (opciono) allowMainThreadQueries

```java
INSTANCE = Room.databaseBuilder(
        context.getApplicationContext(),
        AppDatabase.class,
        "kolokvijum_db"
).build();  // bez .allowMainThreadQueries()
```

> Za kolokvijum možeš ostaviti `allowMainThreadQueries()` – helper i dalje pokazuje znanje o nitima.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`ThreadExecutorHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | **`onDestroy`**: `threadHelper.shutdown()` |

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.helper.ThreadExecutorHelper;
```

### Polje i init

```java
private ThreadExecutorHelper threadHelper;

// onCreate, posle postDao:
threadHelper = new ThreadExecutorHelper(this, postDao);

button.setOnLongClickListener(v -> {
    threadHelper.prikaziTitlePrvogAsync();
    return true;
});

// Brisanje + notifikacija u pozadini:
button.setOnClickListener(v -> threadHelper.obrisiPrviAsync(
        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
));
```

### U `onDestroy`

```java
if (threadHelper != null) {
    threadHelper.shutdown();
}
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// POLJA:
private final ExecutorService executor = Executors.newSingleThreadExecutor();
private final Handler mainHandler = new Handler(Looper.getMainLooper());

// U onDestroy():
// executor.shutdown();

// PRIMER – učitaj postove u pozadini:
private void ucitajPostoveIzBazeUI() {
    executor.execute(() -> {
        List<Post> lista = postDao.getAll();
        mainHandler.post(() -> {
            if (!lista.isEmpty()) {
                Toast.makeText(this, lista.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
            // postAdapter.setPosts(lista);
        });
    });
}

// PRIMER – brisanje u pozadini:
private void obrisiPrviPostAsync() {
    executor.execute(() -> {
        Post prvi = postDao.getFirst();
        if (prvi != null) postDao.delete(prvi);
        int count = postDao.count();
        mainHandler.post(() -> {
            if (count == 0) {
                posaljiNotifikaciju("Nema više postova!");
            }
        });
    });
}
```

## Checklist

- [ ] `ThreadExecutorHelper` u paketu `helper`
- [ ] Room operacije u pozadini
- [ ] UI na glavnoj niti
- [ ] `shutdown()` u `onDestroy`
