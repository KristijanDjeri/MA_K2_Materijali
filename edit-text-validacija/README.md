# EditText i validacija

**Slično:** UI komponente (zadatak 2) + logika pre upisa u bazu/API.

**Mogući zadatak:** Korisnik unese naslov u `EditText`. Ako je prazan → Toast greška, inače upiši u bazu.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Layout | `activity_main.xml` |
| Validacija | `MainActivity.java` |

## Fajlovi

- `edittext_layout_snippet.xml`
- `EditTextValidacijaSegment.java`

## Tipična validacija

- prazan unos
- minimalna dužina
- email format (`Patterns.EMAIL_ADDRESS.matcher(text).matches()`)

## Checklist

- [ ] `EditText` u layoutu
- [ ] `getText().toString().trim()`
- [ ] `isEmpty()` → Toast
- [ ] inače nastavi (insert / POST)
