# Retrofit GET – države (zadatak 5, deo 2) – vežba F

**Cilj:** Podesiti **Retrofit** za **GET** zahtev ka Beeceptor mock API-ju i dobiti listu država.

---

## URL

| Šta | Vrednost |
|-----|----------|
| Stranica mock servera | `https://app.beeceptor.com/mock-server/dummy-json` |
| **BASE_URL** za Retrofit | `https://dummy-json.mock.beeceptor.com/` |
| Endpoint | `@GET("countries")` |

Puna adresa: `https://dummy-json.mock.beeceptor.com/countries`

---

## Novi fajlovi

| Fajl | Putanja |
|------|---------|
| `DummyJsonApi.java` | `app/.../api/DummyJsonApi.java` |
| `RetrofitClient.java` | `app/.../api/RetrofitClient.java` |
| `CountryRepository.java` | `app/.../helper/CountryRepository.java` |

---

## DummyJsonApi.java

```java
@GET("countries")
Call<ResponseBody> getCountriesRaw();
```

Koristimo `ResponseBody` jer Beeceptor ponekad vraća JSON sa **jednostrukim navodnicima** (`'`), što običan Gson ne parsira.

---

## Problem sa JSON formatom

Stvarni odgovor može izgledati ovako (pogrešan JSON):

```
[{name: 'Serbia', code: 'RS'}, ...]
```

**Rešenje u `CountryRepository`:**

```java
String raw = response.body().string();
String json = raw
        .replace("{name:", "{\"name\":")
        .replace(", code:", ", \"code\":")
        .replace("'", "\"");
Type type = new TypeToken<List<Country>>() {}.getType();
List<Country> list = new Gson().fromJson(json, type);
```

Samo `replace("'", "\"")` **nije dovoljno** – imena polja (`name`, `code`) takođe moraju biti u navodnicima.

Ako API vrati ispravan JSON sa `"`, možeš pojednostaviti na:

```java
@GET("countries")
Call<List<Country>> getCountries();
```

---

## CountryRepository – učitavanje u bazu

Metoda `ucitajDrzaveSaApi()`:

1. Pozove Retrofit GET
2. Parsira listu `Country`
3. `countryDao.insertAll(list)`
4. Toast sa brojem učitanih (opciono)

---

## MainActivity – samo test (pre CheckBox-a)

```java
import com.example.kolokvijum2.helper.CountryRepository;

private CountryRepository countryRepository;

// onCreate:
countryRepository = new CountryRepository(this, countryDao);

// privremeni test na dugme (obriši kad uradiš zadatak 6):
buttonSnimi.setOnClickListener(v -> countryRepository.ucitajDrzaveSaApi());
```

Kad završiš zadatak 6, logiku prebaci na **drugi CheckBox** (folder 06).

---

## Greška: „Unable to resolve host“ / proxy na fakultetu

Retrofit ponekad **uopšte ne uspe** – proxy, firewall ili DNS blokiraju spoljne mock domene.

**Proveri redom:**

1. `INTERNET` u Manifest-u
2. Chrome na emulatoru – ima li mreže?
3. Cold Boot emulatora

Ako i dalje ne radi → koristi **ručno unete države** (bez Retrofit-a). Zadatak 6 i 7 rade isto: upis u bazu pri prvom čeku, brisanje poslednje pri sledećim.

---

## Alternativa: ručno unete države (bez mreže)

U `CountryRepository` već postoji metoda **`ucitajDrzaveLokalno()`** sa 10 država iz okruženja:

| Država | Kod |
|--------|-----|
| Srbija | RS |
| Crna Gora | ME |
| Bosna i Hercegovina | BA |
| Hrvatska | HR |
| Severna Makedonija | MK |
| Albanija | AL |
| Bugarska | BG |
| Rumunija | RO |
| Mađarska | HU |
| Slovenija | SI |

### Varijanta A – samo lokalno (najbrže na kolokvijumu)

U `MainActivity`, umesto `ucitajDrzaveSaApi`, pozovi:

```java
countryRepository.ucitajDrzaveLokalno(new CountryRepository.OnDoneListener() {
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
```

### Varijanta B – prvo API, pa lokalno ako padne

U `onFailure` callback-u prebaci na lokalno:

```java
if (!drzaveUcitane) {
    countryRepository.ucitajDrzaveSaApi(new CountryRepository.OnDoneListener() {
        @Override
        public void onSuccess(int count) {
            drzaveUcitane = true;
            checkBoxDrzave.setChecked(false);
        }

        @Override
        public void onFailure(String message) {
            // proxy / mreža ne radi – rezerva
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
}
```

### Varijanta C – inline u MainActivity (bez Repository metode)

```java
import com.example.kolokvijum2.model.Country;
import java.util.Arrays;

private void ucitajDrzaveRucno() {
    countryDao.insertAll(Arrays.asList(
            new Country("Srbija", "RS"),
            new Country("Crna Gora", "ME"),
            new Country("Bosna i Hercegovina", "BA"),
            new Country("Hrvatska", "HR"),
            new Country("Severna Makedonija", "MK"),
            new Country("Albanija", "AL"),
            new Country("Bugarska", "BG"),
            new Country("Rumunija", "RO"),
            new Country("Mađarska", "HU"),
            new Country("Slovenija", "SI")
    ));
    drzaveUcitane = true;
    Toast.makeText(this,
            "Učitano država: " + countryDao.count(), Toast.LENGTH_SHORT).show();
    checkBoxDrzave.setChecked(false);
}
```

Zadatak 7 (brisanje poslednje + Toast) radi **identično** – baza je ista, samo je manje redova (10 umesto ~250).

> Retrofit klasa (`DummyJsonApi`, `RetrofitClient`) može ostati u projektu da pokažeš zadatak 5 – čak i ako na kraju koristiš lokalne podatke.

## Checklist

- [ ] Retrofit + Gson u Gradle-u
- [ ] `BASE_URL` = `https://dummy-json.mock.beeceptor.com/`
- [ ] GET `/countries` vraća listu
- [ ] Parsiranje radi (sa ili bez zamene `'` → `"`)
- [ ] (Opciono) `ucitajDrzaveLokalno()` radi ako mreža ne prolazi

---

## Sledeći korak

[06-checkbox-drzave/](../06-checkbox-drzave/) – drugi CheckBox: učitavanje + brisanje poslednje.
