# Switch listener (zadatak 6 + 9 – spajanje)

**Cilj:** `Switch` sa `onCheckedChangeListener` – **ON** poziva logiku iz drugih foldera, **OFF** poziva zadatak 9.

Ovaj segment **ne sadrži** Retrofit ni SQL – samo **usmerava** pozive ka helper klasama:

| Switch | Pozovi (preko `SwitchPostsHelper`) |
|--------|-------------------------------------|
| ON (prvi put) | `PostRepository.ucitajPostoveSaApi()` |
| ON (drugi+ put) | `PostRepository.prikaziTitlePrvogPosta()` |
| OFF | `SharedPreferencesHelper` + `KontaktiHelper` |

---

## Šta ti treba pre ovoga

- `07-ucitaj-10-postova/` – `PostRepository`
- `08-toast-prvi-post/`, `13-shared-preferences/`, `14-kontakti/` – metode su u helper klasama
- `switchPosts` u layoutu (`01-osnovni-projekat/`)

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`SwitchPostsHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Zavisnosti: `PostRepository`, `SharedPreferencesHelper`, `KontaktiHelper` |
| 3 | `MainActivity.java` | U **`onCreate`**: `new SwitchPostsHelper(...)` – listener se registruje u helperu |

---

## Kompletan kod – helper klasa

Kopiraj **`SwitchPostsHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import com.example.kolokvijum2.helper.KontaktiHelper;
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.helper.SharedPreferencesHelper;
import com.example.kolokvijum2.helper.SwitchPostsHelper;
```

### Polja

```java
private PostRepository postRepository;
private SharedPreferencesHelper prefsHelper;
private KontaktiHelper kontaktiHelper;
```

### U `onCreate` (posle `postDao` i `findViewById`)

```java
postRepository = new PostRepository(this, postDao);
prefsHelper = new SharedPreferencesHelper(this);
kontaktiHelper = new KontaktiHelper(this, textView);

new SwitchPostsHelper(this, switchPosts, textView,
        postRepository, prefsHelper, kontaktiHelper);
```


## Testiranje po delovima

1. Samo **07** – dugme učitava postove (bez Switch-a)
2. Samo **08** – dugme prikazuje Toast (bez Switch-a)
3. **09** – ukloni test listenere sa dugmeta, sve ide preko Switch-a

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// POLJA:
private Switch switchPosts;
private PostDao postDao;
private boolean postsUcitani = false;

// U onCreate(), posle inicijalizacije baze:
AppDatabase db = AppDatabase.getInstance(this);
postDao = db.postDao();

switchPosts.setOnCheckedChangeListener((buttonView, isChecked) -> {
  if (isChecked) {
    obradiSwitchOn();
  } else {
    obradiSwitchOff(); // iz foldera 09-shared-preferences + 10-kontakti
  }
});

// METODE:

private void obradiSwitchOn() {
    if (!postsUcitani) {
        ucitajPostoveSaApi();
    } else {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
        }
    }
}

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

// obradiSwitchOff() definisan u 09-shared-preferences/KontaktiSegment kombinaciji
```

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `postsUcitani` flag | `postDao.count() == 0` za prvi ON |
| Jedan Switch | Dva Switch-a na vežbi – retko |

---

## Checklist

- [ ] ON prvi put → API + 10 postova
- [ ] ON drugi put → Toast title
- [ ] OFF → SharedPreferences + kontakt (zadatak 9)

---

## Sledeći korak

Zadatak 7: **`10-brisanje-prvog-posta/`** + **`11-notifikacija-prazna-baza/`**
