# Switch listener (zadatak 6 + 9 – spajanje)

**Cilj:** `Switch` sa `onCheckedChangeListener` – **ON** poziva logiku iz drugih foldera, **OFF** poziva zadatak 9.

Ovaj segment **ne sadrži** Retrofit ni SQL – samo **usmerava** pozive ka metodama koje već imaš:

| Switch | Pozovi metodu iz foldera |
|--------|--------------------------|
| ON (prvi put) | `07-ucitaj-10-postova/` → `ucitajPostoveSaApi()` |
| ON (drugi+ put) | `08-toast-prvi-post/` → `prikaziTitlePrvogPosta()` |
| OFF | `13-shared-preferences/` + `14-kontakti/` → `obradiSwitchOff()` |

---

## Šta ti treba pre ovoga

- Metode iz `07-`, `08-`, `13-`, `14-` (mogu biti prazne dok ne uradiš zadatak 9)
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

### U `onCreate` (posle `postDao` i `findViewById`)

```java
PostRepository postRepository = new PostRepository(this, postDao);
SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(this);
KontaktiHelper kontaktiHelper = new KontaktiHelper(this, textView);

new SwitchPostsHelper(this, switchPosts, textView,
        postRepository, prefsHelper, kontaktiHelper);
```

> **Alternativa:** inline `OnCheckedChangeListener` ispod.

---

## Alternativa: inline u `MainActivity.java`

### Importi

```java
import android.widget.CompoundButton;
import android.widget.Switch;
```

### Polja

```java
private Switch switchPosts;
private boolean postsUcitani = false;
// postDao već iz 05-room-baza/
```

### U `onCreate`

```java
switchPosts.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if (isChecked) {
        obradiSwitchOn();
    } else {
        obradiSwitchOff();
    }
});
```

### Switch ON – samo grananje

```java
private void obradiSwitchOn() {
    if (!postsUcitani) {
        ucitajPostoveSaApi();      // iz 07-ucitaj-10-postova/
    } else {
        prikaziTitlePrvogPosta();  // iz 08-toast-prvi-post/
    }
}
```

> U `ucitajPostoveSaApi()` na kraju uspešnog upisa postavi `postsUcitani = true` (već je u folderu 07).

### Switch OFF – delegacija

```java
private void obradiSwitchOff() {
    // Kopiraj telo iz 13-shared-preferences/ i 14-kontakti/
    sacuvajBrojPostova();
    postaviImePrvogKontakta();
}
```

Dok ne uradiš zadatak 9, može ostati prazno:

```java
private void obradiSwitchOff() {
    // TODO: 13-shared-preferences + 14-kontakti
}
```

---

## Testiranje po delovima

1. Samo **07** – dugme učitava postove (bez Switch-a)
2. Samo **08** – dugme prikazuje Toast (bez Switch-a)
3. **09** – ukloni test listenere sa dugmeta, sve ide preko Switch-a

---

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
