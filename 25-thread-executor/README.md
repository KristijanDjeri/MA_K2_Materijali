# Thread / Executor – Room u pozadini

**Dodatni segment.** **Slično:** Room operacije (zadaci 5–7), ali **bez** `allowMainThreadQueries()`.

**Cilj:** Baza se koristi u pozadinskoj niti; UI (Toast, adapter) na glavnoj niti.

---

## 1. U `AppDatabase.java` – ukloni (opciono) allowMainThreadQueries

```java
INSTANCE = Room.databaseBuilder(
        context.getApplicationContext(),
        AppDatabase.class,
        "kolokvijum_db"
).build();  // bez .allowMainThreadQueries()
```

> **Napomena:** Za kolokvijum možeš ostaviti `allowMainThreadQueries()` i ipak pokazati da znaš Thread – profesor može tražiti jedno ili drugo.

---

## 2. U `MainActivity.java`

### Importi

```java
import android.os.Handler;
import android.os.Looper;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
```

### Polja

```java
private final ExecutorService executor = Executors.newSingleThreadExecutor();
private final Handler mainHandler = new Handler(Looper.getMainLooper());
```

### U `onDestroy`

```java
@Override
protected void onDestroy() {
    super.onDestroy();
    executor.shutdown();
}
```

---

## 3. Primer: učitaj postove i prikaži Toast

```java
private void ucitajPostoveIzBazeAsync() {
    executor.execute(() -> {
        List<Post> lista = postDao.getAll();
        mainHandler.post(() -> {
            if (!lista.isEmpty()) {
                Toast.makeText(this, lista.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    });
}
```

---

## 4. Primer: brisanje + notifikacija

```java
private void obrisiPrviPostAsync() {
    executor.execute(() -> {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            postDao.delete(prvi);
        }
        int count = postDao.count();
        mainHandler.post(() -> {
            if (count == 0) {
                posaljiNotifikaciju("Nema više postova!");
            }
        });
    });
}
```

> **Alternativa:** `runOnUiThread(() -> { ... })` umesto `mainHandler.post`.

---

## Checklist

- [ ] Room operacije u `executor.execute`
- [ ] Toast/UI u `mainHandler.post` ili `runOnUiThread`
- [ ] `executor.shutdown()` u onDestroy
