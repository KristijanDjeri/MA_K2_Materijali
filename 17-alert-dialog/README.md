# AlertDialog – potvrda i poruke

**Dodatni segment.** **Slično:** Toast (poruka korisniku), ali sa **dugmadima** (OK / Otkaži).

**Cilj:** Pre brisanja posta prikaži dijalog „Da li ste sigurni?“; korisnik potvrdi ili otkaže.

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | `AlertDialog` pre `obrisiPrviPost()` |

---

## Kompletan kod

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
