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

### Importi

```java
import com.example.kolokvijum2.helper.AlertDialogHelper;
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.model.Post;
```

### Poziv pre brisanja (sa notifikacijom iz foldera 11)

```java
button.setOnClickListener(v ->
        AlertDialogHelper.potvrdiBrisanje(this, () ->
                postRepository.obrisiPrviPost(
                        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
                )
        )
);
```

### Informativna poruka

```java
Post prvi = postRepository.getFirst();
if (prvi != null) {
    AlertDialogHelper.info(this, "Prvi post: " + prvi.getTitle());
} else {
    AlertDialogHelper.info(this, "Nema postova u bazi");
}
```


---


## U Fragmentu

```java
new AlertDialog.Builder(requireContext())
        .setTitle("Brisanje")
        .setMessage("Obrisati?")
        .setPositiveButton("Da", (d, w) ->
                postRepository.obrisiPrviPost(null)
        )
        .setNegativeButton("Ne", null)
        .show();
```

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// Deo za MainActivity – potvrda pre brisanja (folder 17-alert-dialog/)

import androidx.appcompat.app.AlertDialog;

private void potvrdiBrisanje() {
    new AlertDialog.Builder(this)
            .setTitle("Brisanje")
            .setMessage("Obrisati prvi post iz baze?")
            .setPositiveButton("Da", (dialog, which) -> obrisiPrviPost())
            .setNegativeButton("Ne", null)
            .show();
}

// button.setOnClickListener(v -> potvrdiBrisanje());
```

## Checklist

- [ ] Dijalog se prikaže pre destruktivne akcije
- [ ] „Da“ poziva `PostRepository.obrisiPrviPost()`
- [ ] „Ne“ zatvara dijalog bez akcije

---

## Povezano

- Brisanje: `10-brisanje-prvog-posta/`
- Fragment: `19-fragment-primer/`
