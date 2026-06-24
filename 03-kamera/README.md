# Kamera (zadatak 4 – deo 1)

**Cilj:** Klik na `ImageButton` → otvori kameru → snimljena slika se prikaže u `ImageView`.

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – layout sa `imageButton` i `imageView`
- Dozvola `CAMERA` u Manifest-u

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`KameraHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** (posle `findViewById`) |
| 3 | `MainActivity.java` | **`onCreate`**: `imageButton.setOnClickListener(v -> kameraHelper.pokreni());` |
| 4 | `MainActivity.java` | **`onRequestPermissionsResult`**: `kameraHelper.onPermissionGranted(...)` |
| 5 | Callback žiroskop | U konstruktoru: `bitmap -> ziroskopHelper.prikaziToast()` (opciono, zadatak 4) |

---

## Kompletan kod – helper klasa

Kopiraj **`KameraHelper.java`** iz ovog foldera u `app/.../helper/KameraHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.KameraHelper;
```

### Polje

```java
private KameraHelper kameraHelper;
```

### U `onCreate`, posle `findViewById`

```java
kameraHelper = new KameraHelper(this, imageView, bitmap -> {
    // Na ispitu: ziroskopHelper.prikaziToast(); — vidi 04-senzor-ziroskop/
});
imageButton.setOnClickListener(v -> kameraHelper.pokreni());
```

### U `onRequestPermissionsResult` (proširi postojeći)

```java
if (kameraHelper != null) {
    kameraHelper.onPermissionGranted(requestCode, grantResults);
}
```

> Za stari inline primer pogledaj `GeoLokacijaSegment.java` u istom folderu.

---

## Šta radi helper (ukratko)

| Linija | Značenje |
|--------|----------|
| `TakePicturePreview()` | Otvara kameru i vraća **Bitmap** (manja slika, dovoljno za kolokvijum) |
| `registerForActivityResult(...)` | Moderni način umesto starog `onActivityResult` |
| `takePictureLauncher.launch(null)` | Pokreće kameru |
| `imageView.setImageBitmap(bitmap)` | Prikazuje sliku u ImageView |

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `TakePicturePreview()` – brzo, bez fajla | `TakePicture()` + `FileProvider` – puna rezolucija, više koda |
| Bitmap u ImageView | `setImageURI(uri)` – ako čuvaš sliku u fajl |
| Kamera | Galerija – folder `76-galerija/` |

---

## Checklist

- [ ] `KameraHelper` u paketu `helper`
- [ ] `kameraHelper = new KameraHelper(...)` u `onCreate`
- [ ] `imageButton.setOnClickListener(v -> kameraHelper.pokreni())`
- [ ] `kameraHelper.onPermissionGranted(...)` u `onRequestPermissionsResult`
- [ ] Slika se vidi u `imageView`

---

## Sledeći korak

Folder **`04-senzor-ziroskop/`** – Toast sa X, Y, Z posle svake nove slike.
