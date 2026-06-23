# ProgressBar – indikator učitavanja

**Dodatni segment.** **Slično:** Retrofit callback (zadatak 5).

**Cilj:** Prikaži ProgressBar dok traje API poziv; sakrij kad završi.

---

## 1. U `activity_main.xml` dodaj

```xml
<ProgressBar
    android:id="@+id/progressBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="gone" />
```

`gone` = nevidljiv i ne zauzima prostor na početku.

---

## 2. U `MainActivity.java`

### Importi

```java
import android.view.View;
import android.widget.ProgressBar;
```

### Polje

```java
private ProgressBar progressBar;
```

### U `onCreate`

```java
progressBar = findViewById(R.id.progressBar);
```

### U Retrofit metodi (`ucitajPostoveSaApi`)

Zameni ili dopuni postojeći callback:

```java
private void ucitajPostoveSaApi() {
    progressBar.setVisibility(View.VISIBLE);

    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            progressBar.setVisibility(View.GONE);
            if (response.isSuccessful() && response.body() != null) {
                List<Post> svi = response.body();
                int n = Math.min(10, svi.size());
                postDao.insertAll(svi.subList(0, n));
                postsUcitani = true;
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "API greška", Toast.LENGTH_SHORT).show();
        }
    });
}
```

---

## Alternativa

- `ProgressDialog` – zastarelo, ne koristi
- `SwipeRefreshLayout` – povlačenje za osvežavanje liste

---

## Checklist

- [ ] ProgressBar u layoutu, početno `gone`
- [ ] `VISIBLE` pre enqueue
- [ ] `GONE` u onResponse i onFailure
