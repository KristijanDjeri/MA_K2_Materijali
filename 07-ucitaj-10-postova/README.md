# Učitavanje 10 postova sa API-ja (zadatak 6, deo 1)

**Cilj:** Preuzmi listu sa API-ja i upiši **prvih 10** postova u Room bazu.

Radi **samostalno** – okida se **dugmetom** (ne Switch-om). Switch logiku dodaješ kasnije u `09-switch-listener/`.

---

## Šta ti treba pre ovoga

- `05-room-baza/` – `PostDao`, `postDao` u MainActivity
- `06-retrofit-get/` – `RetrofitClient`, `JsonPlaceholderApi`
- Dozvola `INTERNET`

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`PostRepository.java`** | Novi fajl → `app/.../helper/PostRepository.java` |
| 2 | `MainActivity.java` | Polje: `private PostRepository postRepository;` |
| 3 | `MainActivity.java` | **`onCreate`**: `postRepository = new PostRepository(this, postDao);` |
| 4 | `MainActivity.java` | **`onCreate`**, listener: `button.setOnClickListener(v -> postRepository.ucitajPostoveSaApi(...));` |

---

## Kompletan kod (inline varijanta)

### Importi

```java
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
```

### Polje (opciono – za kasniji Switch)

```java
private boolean postsUcitani = false;
```

### U `onCreate`

```java
// Samostalni test – bilo koje dugme, npr. button ili poseban Button u layoutu
button.setOnClickListener(v -> ucitajPostoveSaApi());
```

### Metoda za učitavanje

```java
private void ucitajPostoveSaApi() {
    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Post> svi = response.body();
                int n = Math.min(10, svi.size());
                postDao.insertAll(svi.subList(0, n));
                postsUcitani = true;
                Toast.makeText(MainActivity.this,
                        "Učitano " + n + " postova", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
```

---

## Logika u rečima

1. `enqueue` – asinhroni GET
2. `Math.min(10, svi.size())` – najviše 10 redova
3. `postDao.insertAll(subList(0, n))` – upis u bazu
4. `postsUcitani = true` – kasnije koristi `09-switch-listener/`

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `boolean postsUcitani` | `postDao.count() > 0` umesto flag-a |
| Dugme za test | Na ispitu poziva Switch ON (prvi put) |
| `subList(0, n)` | For petlja `for (i = 0; i < 10 && i < svi.size(); i++)` |

---

## Checklist

- [ ] GET uspešan (internet uključen)
- [ ] U bazu ide tačno 10 (ili manje ako API vrati manje)
- [ ] Radi bez Switch-a

---

## Sledeći korak

**`08-toast-prvi-post/`** – prikaži `title` prvog reda iz baze (nezavisno od API-ja).
