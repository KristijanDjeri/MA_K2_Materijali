# Switch i postovi (zadatak 6)

**Cilj:**
- **Prvi put** Switch ON → preuzmi sa API-ja i upiši **prvih 10** postova u bazu
- **Svaki sledeći put** Switch ON → Toast sa `title` **prvog posta u tabeli** (prvi red, ne nužno id=1)

---

## Šta ti treba pre ovoga

- `retrofit-room/` – `PostDao`, `RetrofitClient`, `postDao` u MainActivity
- `switchPosts` u layoutu

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | Switch listener, flag, Retrofit callback |

---

## Kompletan kod za `MainActivity.java` (deo za Switch ON)

### 1. Importi

```java
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
```

### 2. Polja

```java
private Switch switchPosts;
private boolean postsUcitani = false;
// postDao već imaš iz retrofit-room/
```

### 3. U `onCreate`, posle `findViewById` i `postDao = ...`

```java
switchPosts.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if (isChecked) {
        obradiSwitchOn();
    } else {
        obradiSwitchOff(); // folder shared-preferences/ + kontakti/
    }
});
```

> **Napomena:** `obradiSwitchOff()` dodaješ iz zadatka 9. Do tada možeš staviti praznu metodu.

### 4. Metoda za Switch ON

```java
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
```

### 5. Retrofit GET + upis 10 postova

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

### 6. Privremeno za Switch OFF (dok ne uradiš zadatak 9)

```java
private void obradiSwitchOff() {
    // Popuni iz foldera shared-preferences/ i kontakti/
}
```

---

## Logika u rečima

1. `postsUcitani = false` na početku
2. Prvi put ON → API → uzmi listu → `subList(0, min(10, size))` → `insertAll` → `postsUcitani = true`
3. Drugi put ON → `postDao.getFirst()` → Toast sa `getTitle()`
4. OFF → zadatak 9

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `boolean postsUcitani` | Proveri `postDao.count() == 0` umesto flag-a |
| `subList(0, n)` | For petlja `for (i=0; i<10 && i<svi.size(); i++)` |
| Toast posle učitavanja | Bez Toast-a – zadatak to ne traži eksplicitno, ali pomaže pri testu |

---

## Checklist

- [ ] `postsUcitani` flag
- [ ] Prvi ON učitava tačno 10 (ili manje ako API vrati manje)
- [ ] Drugi ON prikazuje title prvog reda u bazi
- [ ] OFF poziva `obradiSwitchOff()`

---

## Sledeći korak

- Zadatak 7: **`brisanje-notifikacije/`**
- Zadatak 9: **`shared-preferences/`** + **`kontakti/`**
