# CheckBox – države (zadaci 6–7) – vežba F

**Cilj:**

- **Zadatak 6:** Prvi put kad se **čekira** `checkBoxDrzave` → GET sa API-ja, **sve države** u Room bazu
- **Zadatak 7:** Svaki **naredni** put kad se **čekira** → obriši **poslednju** državu, Toast: koliko je ostalo u bazi

---

## Logika (važno)

```
Prvi ček  → countryRepository.ucitajDrzaveSaApi()
            drzaveUcitane = true

Drugi+ ček → countryRepository.obrisiPoslednjuDrzavu()
             Toast: "Ostalo država u bazi: N"
```

Koristi zastavicu `drzaveUcitane` da razlikuješ prvi od narednih čekiranja.

Posle operacije **isključi** CheckBox (`setChecked(false)`), da sledeći klik ponovo okine `onCheckedChangeListener` sa `isChecked = true`.

---

## MainActivity – povezivanje

```java
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.CountryDao;
import com.example.kolokvijum2.helper.CountryRepository;

private CountryDao countryDao;
private CountryRepository countryRepository;
private boolean drzaveUcitane = false;

// onCreate:
countryDao = AppDatabase.getInstance(this).countryDao();
countryRepository = new CountryRepository(this, countryDao);

checkBoxDrzave.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if (!isChecked) {
        return;
    }

    if (!drzaveUcitane) {
        countryRepository.ucitajDrzaveSaApi(new CountryRepository.OnDoneListener() {
            @Override
            public void onSuccess(int count) {
                drzaveUcitane = true;
                checkBoxDrzave.setChecked(false);
            }

            @Override
            public void onFailure(String message) {
                // Rezerva ako proxy blokira mrežu – vidi 05-retrofit-drzave/
                countryRepository.ucitajDrzaveLokalno(new CountryRepository.OnDoneListener() {
                    @Override
                    public void onSuccess(int count) {
                        drzaveUcitane = true;
                        checkBoxDrzave.setChecked(false);
                    }

                    @Override
                    public void onFailure(String msg) {
                        checkBoxDrzave.setChecked(false);
                    }
                });
            }
        });
    } else {
        countryRepository.obrisiPoslednjuDrzavu();
        checkBoxDrzave.setChecked(false);
    }
});
```

---

## Objašnjenje korak po korak

| Korak | Šta se dešava |
|-------|----------------|
| 1 | Korisnik čekira CheckBox → `isChecked == true` |
| 2 | Ako `!drzaveUcitane` → Retrofit GET (ili lokalno ako mreža padne), `insertAll`, Toast „Učitano država: N“ |
| 3 | U callback-u `onSuccess` → `drzaveUcitane = true`, checkbox off |
| 4 | Sledeći put kad čekira → `getLast()` + `delete()` + Toast „Ostalo država: M“ |
| 5 | Checkbox opet off – spreman za novo brisanje |

---

## „Poslednja“ država

U `CountryDao`:

```java
@Query("SELECT * FROM countries ORDER BY id DESC LIMIT 1")
Country getLast();
```

Države upisane iz API-ja dobijaju auto-increment `id` redom. **Poslednja ubačena** = najveći `id`.

> **Alternativa:** `ORDER BY rowid DESC` – isti efekat za ovaj zadatak.

---

## Šta ako je baza prazna pri brisanju?

`obrisiPoslednjuDrzavu()` u `CountryRepository` i dalje prikazuje Toast sa `count()` → **0**.

---

## Alternativa: inline u MainActivity

```java
private boolean drzaveUcitane = false;

checkBoxDrzave.setOnCheckedChangeListener((v, checked) -> {
    if (!checked) return;

    if (!drzaveUcitane) {
        countryRepository.ucitajDrzaveSaApi(new CountryRepository.OnDoneListener() {
            @Override
            public void onSuccess(int count) {
                drzaveUcitane = true;
                checkBoxDrzave.setChecked(false);
            }
            @Override
            public void onFailure(String message) {
                checkBoxDrzave.setChecked(false);
            }
        });
    } else {
        Country poslednja = countryDao.getLast();
        if (poslednja != null) countryDao.delete(poslednja);
        Toast.makeText(MainActivity.this,
                "Ostalo država u bazi: " + countryDao.count(),
                Toast.LENGTH_SHORT).show();
        checkBoxDrzave.setChecked(false);
    }
});
```

---

## Test scenario

**Sa API-jem:**

1. Čekiraj **Države** → sačekaj mrežu → Toast „Učitano država: ~250“
2. Čekiraj ponovo → Toast „Ostalo država u bazi: 249“

**Sa lokalnim podacima** (`ucitajDrzaveLokalno`):

1. Prvi ček → Toast „Učitano država: 10“
2. Drugi ček → „Ostalo država u bazi: 9“
3. Treći ček → „8“, itd.

---

## Checklist

- [ ] Prvi ček učitava sve države u bazu
- [ ] Svaki sledeći ček briše poslednju
- [ ] Toast prikazuje tačan `count()`
- [ ] CheckBox se resetuje posle akcije

---

## Sledeći korak

[07-proksimitet/](../07-proksimitet/) – drugi TextView.
