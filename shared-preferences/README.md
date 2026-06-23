# SharedPreferences (zadatak 9 – deo 1)

**Cilj:** Kada se Switch prebaci na **OFF**, sačuvaj sadržaj `TextView`-a u SharedPreferences pod ključem **`tekst`**.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Čuvanje | `MainActivity.java` |

## Kako napraviti

```java
SharedPreferences prefs = getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE);
prefs.edit().putString("tekst", textView.getText().toString()).apply();
```

Pozovi u `obradiSwitchOff()` **pre** nego što zameniš tekst kontaktom.

## Fajlovi

- `SharedPreferencesSegment.java`

## Učitavanje (opciono)

Pri pokretanju možeš pročitati sačuvani tekst:

```java
String sacuvano = prefs.getString("tekst", "");
```

Zadatak to ne traži eksplicitno, ali može biti korisno za test.

## Checklist

- [ ] Switch OFF → `putString("tekst", ...)`
- [ ] `apply()` ili `commit()`
