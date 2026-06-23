# SharedPreferences (zadatak 9 – deo 1)

**Cilj:** Kada se Switch prebaci na **OFF**, sačuvaj sadržaj `TextView`-a u SharedPreferences pod ključem **`tekst`**.

---

## Šta ti treba pre ovoga

- `switchPosts` listener iz `06-switch-postovi/`
- `textView` u layoutu

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | SharedPreferences init + `obradiSwitchOff()` |

---

## Kompletan kod za `MainActivity.java`

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

    // 2. Zameni TextView imenom prvog kontakta (folder 10-kontakti/)
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
| SharedPreferences | Interni fajl → folder `26-interni-fajl/` |
| Ključ `"tekst"` | Bilo koji string, ali zadatak traži `"tekst"` |
| `apply()` | `commit()` |

---

## Checklist

- [ ] `prefs` inicijalizovan u `onCreate`
- [ ] Switch OFF poziva `obradiSwitchOff()`
- [ ] `putString("tekst", ...)` sa sadržajem TextView-a

---

## Sledeći korak

Folder **`10-kontakti/`** – deo 2 zadatka 9 (ime prvog kontakta u TextView).
