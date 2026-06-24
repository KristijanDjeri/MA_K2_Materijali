# AlertDialog – potvrda i poruke

**Dodatni segment.** **Slično:** Toast (poruka korisniku), ali sa **dugmadima** (OK / Otkaži).

**Cilj:** Pre brisanja posta prikaži dijalog „Da li ste sigurni?“; korisnik potvrdi ili otkaže.

---

## Koji fajlovi se menjaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`AlertDialogHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Pre brisanja: `AlertDialogHelper.potvrdiBrisanje(...)` |

---

## Kompletan kod – helper klasa

Kopiraj **`AlertDialogHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.AlertDialogHelper;
```

### Poziv pre brisanja

```java
button.setOnClickListener(v ->
        AlertDialogHelper.potvrdiBrisanje(this, this::obrisiPrviPost)
);
```

### Informativna poruka

```java
AlertDialogHelper.info(this, "Baza je prazna");
```

> **Alternativa:** inline `AlertDialog.Builder` ispod.

---

## Alternativa: inline u `MainActivity.java`

### Import

```java
import androidx.appcompat.app.AlertDialog;
```

### Varijanta A – potvrda brisanja (najčešće na ispitu)

```java
private void potvrdiBrisanje() {
    new AlertDialog.Builder(this)
            .setTitle("Brisanje")
            .setMessage("Obrisati prvi post iz baze?")
            .setPositiveButton("Da", (dialog, which) -> obrisiPrviPost())
            .setNegativeButton("Ne", null)
            .show();
}
```

Poziv umesto direktnog brisanja:

```java
button.setOnClickListener(v -> potvrdiBrisanje());
```

> `obrisiPrviPost()` već imaš iz `10-brisanje-prvog-posta/`.

### Varijanta B – samo informativni dijalog

```java
private void prikaziInfo(String tekst) {
    new AlertDialog.Builder(this)
            .setTitle("Obaveštenje")
            .setMessage(tekst)
            .setPositiveButton("OK", null)
            .show();
}
```

Primer:

```java
Post prvi = postDao.getFirst();
if (prvi != null) {
    prikaziInfo("Prvi post: " + prvi.getTitle());
} else {
    prikaziInfo("Nema postova u bazi");
}
```

### Varijanta C – tri dugmeta (retko)

```java
new AlertDialog.Builder(this)
        .setTitle("Izbor")
        .setMessage("Šta želiš?")
        .setPositiveButton("Obriši", (d, w) -> obrisiPrviPost())
        .setNeutralButton("Prikaži", (d, w) -> prikaziTitlePrvogPosta())
        .setNegativeButton("Otkaži", null)
        .show();
```

---

## U Fragmentu

```java
new AlertDialog.Builder(requireContext())
        .setTitle("Brisanje")
        .setMessage("Obrisati?")
        .setPositiveButton("Da", (d, w) -> obrisiPrviPost())
        .setNegativeButton("Ne", null)
        .show();
```

---

## Checklist

- [ ] Dijalog se prikaže pre destruktivne akcije
- [ ] „Da“ poziva pravu metodu
- [ ] „Ne“ zatvara dijalog bez akcije

---

## Povezano

- Brisanje: `10-brisanje-prvog-posta/`
- Fragment: `19-fragment-primer/`
