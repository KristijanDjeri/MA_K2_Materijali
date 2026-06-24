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
| 1 | **`PostRepository.java`** | Novi fajl → `app/.../helper/PostRepository.java` (kopiraj iz ovog foldera) |
| 2 | `MainActivity.java` | Polje: `private PostRepository postRepository;` |
| 3 | `MainActivity.java` | **`onCreate`**, posle `postDao`: `postRepository = new PostRepository(this, postDao);` |
| 4 | `MainActivity.java` | **`onCreate`**, listener na dugme (primer ispod) |

---

## Kompletan kod – helper klasa

Kopiraj **`PostRepository.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje

### Importi

```java
import android.widget.Toast;
import com.example.kolokvijum2.helper.PostRepository;
```

### Polje

```java
private PostRepository postRepository;
```

### U `onCreate`, posle `postDao` init

```java
postRepository = new PostRepository(this, postDao);

button.setOnClickListener(v -> postRepository.ucitajPostoveSaApi(
        new PostRepository.OnApiDoneListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this,
                        "Učitano " + count + " postova", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this,
                        "Greška: " + message, Toast.LENGTH_SHORT).show();
            }
        }
));
```


---

## PostRepository – sve metode na jednom mestu

Kopiraj **`PostRepository.java`** iz `07-ucitaj-10-postova/`. MainActivity **ne dodaje** ove metode – samo poziva:

| Metoda | Segment |
|--------|---------|
| `ucitajPostoveSaApi(listener)` | 07, Switch ON (prvi put) |
| `prikaziTitlePrvogPosta()` | 08, Switch ON (drugi put) |
| `obrisiPrviPost(onEmpty)` | 10, 11, 17 |
| `izmeniTitlePrvogPosta(novi)` | 18 |
| `getFirst()` / `getAll()` / `count()` | 70, 60, 71 |
| `dodajIzNaslova(naslov)` | 62 (preko `EditTextValidacijaHelper`) |
| `posaljiPostNaServer(listener)` | 72 |
| `insertPosts(lista)` | 53 Firestore hibrid |

---

## Logika u rečima (unutar `PostRepository`, ne u MainActivity)

1. `enqueue` – asinhroni GET
2. `Math.min(10, svi.size())` – najviše 10 redova
3. `postDao.insertAll(subList(0, n))` – upis u bazu
4. `postsUcitani` flag – kasnije koristi `09-switch-listener/`

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// POLJE:
private boolean postsUcitani = false;

// U onCreate(), posle postDao init:
button.setOnClickListener(v -> ucitajPostoveSaApi());

// METODA:

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

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `PostRepository.isPostsUcitani()` | `postDao.count() > 0` umesto flag-a |
| Dugme za test | Na ispitu poziva Switch ON (prvi put) |
| `subList(0, n)` | For petlja `for (i = 0; i < 10 && i < svi.size(); i++)` |

---

## Checklist

- [ ] `PostRepository.java` kopiran u paket `helper` (metoda `ucitajPostoveSaApi` već unutra)
- [ ] U `onCreate` samo: `new PostRepository(...)` + listener koji poziva `ucitajPostoveSaApi`
- [ ] GET uspešan (internet uključen)
- [ ] U bazu ide tačno 10 (ili manje ako API vrati manje)
- [ ] Radi bez Switch-a

---

## Sledeći korak

**`08-toast-prvi-post/`** – prikaži `title` prvog reda iz baze (nezavisno od API-ja).
