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

> **Ne piši** `ExecutorService` / `Handler` u MainActivity – sve je u `ThreadExecutorHelper`.

---

## Alternativa: inline u `MainActivity.java` (zastarelo)

Vidi `ThreadExecutorSegment.java` u ovom folderu.

---

## Checklist

- [ ] `ThreadExecutorHelper` u paketu `helper`
- [ ] Room operacije u pozadini
- [ ] UI na glavnoj niti
- [ ] `shutdown()` u `onDestroy`
