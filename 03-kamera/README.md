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


---

## Šta radi helper (ukratko)

| Linija | Značenje |
|--------|----------|
| `TakePicturePreview()` | Otvara kameru i vraća **Bitmap** (manja slika, dovoljno za kolokvijum) |
| `registerForActivityResult(...)` | Moderni način umesto starog `onActivityResult` |
| `takePictureLauncher.launch(null)` | Pokreće kameru |
| `imageView.setImageBitmap(bitmap)` | Prikazuje sliku u ImageView |

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final int REQ_CAMERA = 101;
private ImageButton imageButton;
private ImageView imageView;

private final ActivityResultLauncher<Void> takePictureLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                // Pozovi žiroskop Toast (folder 04-senzor-ziroskop/):
                prikaziZiroskopToast();
            }
        });

// U onCreate(), posle findViewById:
imageButton.setOnClickListener(v -> pokreniKameru());

// METODE:

private void pokreniKameru() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQ_CAMERA);
        return;
    }
    takePictureLauncher.launch(null);
}

// U onRequestPermissionsResult dodaj i REQ_CAMERA:
// if (requestCode == REQ_CAMERA && grantResults[0] == PERMISSION_GRANTED) {
//     takePictureLauncher.launch(null);
// }
```

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
