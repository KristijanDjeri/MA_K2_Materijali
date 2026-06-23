# Switch i postovi (zadatak 6)

**Cilj:**
- **Prvi put** kada se Switch uključi (ON): preuzmi sa API-ja i upiši **prvih 10** postova u bazu
- **Svaki sledeći put** ON: Toast sa `title` **prvog posta u tabeli** (ne nužno id=1, već prvi red)

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Logika Switch-a | `MainActivity.java` |
| Baza + API | folder `retrofit-room/` |

## Kako napraviti

1. Flag `boolean postsUcitani = false` – da znaš da li je prvi put
2. `switchPosts.setOnCheckedChangeListener`
3. Kada je `isChecked == true`:
   - Ako `!postsUcitani` → Retrofit `enqueue` → uzmi listu → `subList(0, 10)` → `dao.insertAll` → `postsUcitani = true`
   - Inače → `Post prvi = dao.getFirst()` → Toast sa `prvi.getTitle()`
4. Kada je OFF → logika iz `shared-preferences/` i `kontakti/`

## Fajlovi

- `SwitchPostoviSegment.java`

## Retrofit callback (kratko)

```java
RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
    @Override
    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
            List<Post> svi = response.body();
            int doKoliko = Math.min(10, svi.size());
            dao.insertAll(svi.subList(0, doKoliko));
            postsUcitani = true;
        }
    }
    @Override
    public void onFailure(Call<List<Post>> call, Throwable t) { }
});
```

## Checklist

- [ ] Flag za prvo učitavanje
- [ ] Samo 10 postova (`subList(0, Math.min(10, size))`)
- [ ] Drugi put ON → `getFirst()` + Toast title
- [ ] Internet dozvola u manifestu
