# RecyclerView (lista postova iz baze)

**Slično:** Room baza + prikaz podataka (kao zadaci 5–7, ali sa listom umesto Toast-a).

**Mogući zadatak:** Prikaži sve postove iz baze u `RecyclerView`. Klik na stavku → Toast sa `title`.

## Gde u projektu

| Fajl | Putanja |
|------|---------|
| Layout stavke | `res/layout/item_post.xml` |
| Layout aktivnosti | `res/layout/activity_main.xml` (dodaj RecyclerView) |
| Adapter | `.../adapter/PostAdapter.java` |
| Logika | `MainActivity.java` |

## Gradle

U `dependencies` dodaj:

```gradle
implementation 'androidx.recyclerview:recyclerview:1.3.2'
```

## Koraci

1. Kreiraj `item_post.xml` sa jednim `TextView` (title)
2. Kreiraj `PostAdapter` koji prima `List<Post>`
3. U layout dodaj `RecyclerView` sa `android:id="@+id/recyclerView"`
4. U `onCreate`: `recyclerView.setLayoutManager(new LinearLayoutManager(this))`
5. Učitaj iz baze: `postDao.getAll()` → `adapter.notifyDataSetChanged()`

## Fajlovi u folderu

- `item_post.xml`
- `PostAdapter.java`
- `RecyclerViewSegment.java`

## Checklist

- [ ] `RecyclerView` + `LinearLayoutManager`
- [ ] ViewHolder u adapteru
- [ ] `onBindViewHolder` postavlja title
- [ ] Opciono: klik → Toast
