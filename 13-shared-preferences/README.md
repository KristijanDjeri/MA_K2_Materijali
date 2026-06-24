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

> **Alternativa:** inline kod ispod ili `SharedPreferencesSegment.java`.

---

## Alternativa: inline u `MainActivity.java`

### 1. Import

```java
import android.content.SharedPreferences;
```

### 2. Polje

```java
private SharedPreferences prefs;
```

### 3. U `onCreate`

```java
prefs = getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE);
```

### 4. Metoda `obradiSwitchOff()` (ceo deo za čuvanje)

```java
private void obradiSwitchOff() {
  // 1. Sačuvaj trenutni tekst TextView-a
    String trenutniTekst = textView.getText().toString();
    prefs.edit().putString("tekst", trenutniTekst).apply();

    // 2. Zameni TextView imenom prvog kontakta (folder 14-kontakti/)
    postaviImePrvogKontakta();
}
```

### 5. (Opciono) Učitavanje pri pokretanju – zadatak ne traži, ali korisno

```java
// U onCreate, posle prefs = ...
String sacuvano = prefs.getString("tekst", "");
if (!sacuvano.isEmpty()) {
    // Ne postavljaj ovde ako lokacija treba da pregazi – zavisi od redosleda
}
```

---

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
