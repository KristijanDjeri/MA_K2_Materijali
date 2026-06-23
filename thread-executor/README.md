# Thread / Executor (pozadinska nit za bazu)

**Slično:** Room operacije (zadatak 5–7). Profesor može zabraniti `allowMainThreadQueries()`.

**Mogući zadatak:** Učitavanje i brisanje postova mora ići u pozadini, UI ažuriraj na glavnoj niti.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Pozadinske operacije | `MainActivity.java` |

## Obrazac

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
Handler handler = new Handler(Looper.getMainLooper());

executor.execute(() -> {
    // Room ovde
    List<Post> lista = postDao.getAll();
    handler.post(() -> {
        // UI ovde (Toast, adapter, TextView)
    });
});
```

## Fajlovi

- `ThreadExecutorSegment.java`

## Checklist

- [ ] Room **bez** `allowMainThreadQueries()` u produkciji
- [ ] `ExecutorService` ili `new Thread()`
- [ ] UI samo na main thread (`runOnUiThread` ili `Handler`)

## Alternativa

`AsyncTask` je zastareo – ne koristi na novim projektima.
