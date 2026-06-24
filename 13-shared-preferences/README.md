# SharedPreferences (zadatak 9 – deo 1)

**Cilj:** Kada se Switch prebaci na **OFF**, sačuvaj sadržaj `TextView`-a u SharedPreferences pod ključem **`tekst`**.

---

## Šta ti treba pre ovoga

- `09-switch-listener/` – `SwitchPostsHelper` poziva `prefsHelper` na Switch OFF
- `textView` u layoutu

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`SharedPreferencesHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `09-switch-listener/` | `SwitchPostsHelper` automatski poziva `prefsHelper.sacuvajTextView(textView)` na Switch OFF |

---

## Kompletan kod – helper klasa

Kopiraj **`SharedPreferencesHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.SharedPreferencesHelper;
```

### Polje

```java
private SharedPreferencesHelper prefsHelper;
```

### U `onCreate`

```java
prefsHelper = new SharedPreferencesHelper(this);
```

### Na Switch OFF

Kad u `09-switch-listener/` inicijalizuješ `SwitchPostsHelper`, čuvanje se dešava automatski:

```java
// unutar SwitchPostsHelper.obradiSwitchOff():
prefsHelper.sacuvajTextView(textView);
```

Ručni test (bez Switch-a):

```java
button.setOnClickListener(v -> prefsHelper.sacuvajTextView(textView));
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.SharedPreferences;

// POLJE:
private SharedPreferences prefs;

// U onCreate():
prefs = getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE);

// METODA (poziva se iz switch listenera kada je isChecked == false):

private void obradiSwitchOff() {
    // 1. Sačuvaj TextView u SharedPreferences
    String trenutniTekst = textView.getText().toString();
    prefs.edit().putString("tekst", trenutniTekst).apply();

    // 2. Zameni TextView imenom prvog kontakta (folder 10-kontakti/)
    postaviImePrvogKontakta();
}
```

## Objašnjenje linija

| Linija | Značenje |
|--------|----------|
| `getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE)` | Otvara fajl sa podešavanjima samo za tvoju app |
| `edit().putString("tekst", ...)` | Upisuje string pod ključem `tekst` |
| `.apply()` | Asinhrono čuva (preporučeno) |
| `.commit()` | **Alternativa** – sinhrono, vraća boolean |

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| SharedPreferences | Interni fajl → folder `75-interni-fajl/` |
| Ključ `"tekst"` | Bilo koji string, ali zadatak traži `"tekst"` |
| `apply()` | `commit()` |

---

## Checklist

- [ ] `prefsHelper` inicijalizovan u `onCreate`
- [ ] Switch OFF (preko `SwitchPostsHelper`) poziva `sacuvajTextView`
- [ ] `putString("tekst", ...)` sa sadržajem TextView-a

---

## Sledeći korak

Folder **`14-kontakti/`** – deo 2 zadatka 9 (ime prvog kontakta u TextView).
