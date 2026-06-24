# Room baza – model država (zadatak 5, deo 1) – vežba F

**Cilj:** Napraviti **Room model za države** (`Country`) – bez mrežnog poziva (to je u sledećem folderu).

API vraća objekte sa poljima `name` i `code`. U bazi dodajemo auto-generisan `id` za primarni ključ.

---

## Novi fajlovi

| Fajl | Putanja |
|------|---------|
| `Country.java` | `app/.../model/Country.java` |
| `CountryDao.java` | `app/.../db/CountryDao.java` |
| `AppDatabase.java` | `app/.../db/AppDatabase.java` |

Kreiraj pakete: desni klik → **New → Package** → `model`, pa `db`.

Kopiraj fajlove iz ovog foldera.

---

## Country.java

```java
@Entity(tableName = "countries")
public class Country {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String code;
    // konstruktori + getteri/setteri
}
```

---

## CountryDao.java – bitne metode

| Metoda | Svrha |
|--------|-------|
| `insertAll(List<Country>)` | Upis svih država sa API-ja (zadatak 6) |
| `getLast()` | Poslednja država po `id` (zadatak 7) |
| `delete(Country)` | Brisanje jedne države |
| `count()` | Broj redova za Toast (zadatak 7) |

---

## AppDatabase.java

```java
@Database(entities = {Country.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
    // getInstance(Context) – singleton
}
```

Ime baze: npr. `"kolokvijum_f_db"`.

> **`allowMainThreadQueries()`** – dovoljno za vežbu; u produkciji koristi pozadinsku nit.

---

## MainActivity – inicijalizacija

```java
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.CountryDao;

private CountryDao countryDao;

// onCreate:
countryDao = AppDatabase.getInstance(this).countryDao();
```

---

## (Opciono) Brzi test bez API-ja

```java
Country c = new Country();
c.setName("Srbija");
c.setCode("RS");
countryDao.insert(c);
```

Proveri u Logcat-u ili kasnije preko Toast-a iz zadatka 7.

---

## Checklist

- [ ] `Country`, `CountryDao`, `AppDatabase` kreirani
- [ ] Gradle Sync bez greške
- [ ] `countryDao` inicijalizovan u `onCreate`

---

## Sledeći korak

[05-retrofit-drzave/](../05-retrofit-drzave/) – Retrofit GET ka `/countries`.
