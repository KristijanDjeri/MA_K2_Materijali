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

## Gde nalepiti kod (preporučeno: `OkHttpHelper`)

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `ProgressBar` | `activity_main.xml` (iznad) |
| 2 | **`OkHttpHelper.java`** | Iz `73-okhttp-json/` → `app/.../helper/` |
| 3 | `MainActivity.java` | `okHttpHelper = new OkHttpHelper(this, postDao, progressBar);` |

`OkHttpHelper` **sam** prikazuje/sakriva ProgressBar – ne pišeš logiku u MainActivity.

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import android.widget.ProgressBar;
import com.example.kolokvijum2.helper.OkHttpHelper;
```

### U `onCreate`

```java
ProgressBar progressBar = findViewById(R.id.progressBar);
OkHttpHelper okHttpHelper = new OkHttpHelper(this, postDao, progressBar);

button.setOnClickListener(v -> okHttpHelper.ucitajPostove(
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
));
```

Vidi pun primer u **`73-okhttp-json/`**.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.view.View;
import android.widget.ProgressBar;

// POLJE:
private ProgressBar progressBar;

// U onCreate():
progressBar = findViewById(R.id.progressBar);

// U Retrofit pozivu:
private void ucitajPostoveSaProgressom() {
    progressBar.setVisibility(View.VISIBLE);

    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            progressBar.setVisibility(View.GONE);
            // ... ostatak logike
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
        }
    });
}
```

## Alternativa

- `ProgressDialog` – zastarelo, ne koristi
- `SwipeRefreshLayout` – povlačenje za osvežavanje liste

---

## Checklist

- [ ] ProgressBar u layoutu, početno `gone`
- [ ] `OkHttpHelper` prima `progressBar` u konstruktoru
- [ ] `VISIBLE` pre poziva, `GONE` u callback-u (automatski u helperu)
