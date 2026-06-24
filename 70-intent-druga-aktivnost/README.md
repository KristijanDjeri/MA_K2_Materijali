# Druga aktivnost + Intent (prenos podataka)

**Dodatni segment.** **Slično:** prenos podataka kao SharedPreferences, ali na drugi ekran.

**Cilj:** Klik otvara `DetailActivity` i prosleđuje npr. `title` posta.

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `activity_detail.xml` | `res/layout/activity_detail.xml` |
| `DetailActivity.java` | `.../DetailActivity.java` |
| **`DetailIntentHelper.java`** | `.../helper/DetailIntentHelper.java` |
| Manifest unos | unutar `<application>` |

---

## 1. Layout i `DetailActivity`

Kopiraj **`activity_detail.xml`** i **`DetailActivity.java`** iz ovog foldera.

---

## 2. `AndroidManifest.xml` – unutar `<application>`

```xml
<activity
    android:name=".DetailActivity"
    android:exported="false" />
```

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.DetailIntentHelper;
import com.example.kolokvijum2.helper.PostRepository;
```

### U `onCreate`

```java
switchPosts.setOnLongClickListener(v -> {
    DetailIntentHelper.otvoriDetaljPrvogPosta(this, postRepository);
    return true;
});

// ili sa fiksnim naslovom:
// DetailIntentHelper.otvoriDetalj(this, "Probni naslov");
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.Intent;

// METODA (npr. dugme ili dugi klik na post):
private void otvoriDetalj(String naslov) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra("naslov", naslov);
    startActivity(intent);
}

// Primer poziva:
// Post prvi = postDao.getFirst();
// if (prvi != null) otvoriDetalj(prvi.getTitle());
```

## Checklist

- [ ] `DetailIntentHelper` u paketu `helper`
- [ ] DetailActivity u Manifest-u
- [ ] `putExtra` / `getStringExtra` rade
