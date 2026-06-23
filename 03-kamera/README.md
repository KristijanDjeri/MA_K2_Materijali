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

## Kompletan kod za `MainActivity.java` (inline varijanta)

### 1. Importi

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
```

### 2. Konstanta i polja

```java
private static final int REQ_CAMERA = 101;

// imageButton i imageView već imaš iz osnovnog projekta

private final ActivityResultLauncher<Void> takePictureLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                // Na ispitu: prikaziZiroskopToast(); — vidi 04-senzor-ziroskop/ i 16-spajanje-zadataka/
            }
        });
```

> **Samostalna vežba:** Ovaj segment radi **bez** žiroskopa – samo slika u `ImageView`. Toast sa žiroskopom spajaš kasnije (`16-spajanje-zadataka/`).

### 3. U `onCreate`, posle `findViewById`

```java
imageButton.setOnClickListener(v -> pokreniKameru());
```

### 4. Metoda za pokretanje kamere

```java
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
```

### 5. U `onRequestPermissionsResult` dodaj granu za kameru

Ako već imaš metodu za lokaciju, proširi je ovako:

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    if (requestCode == REQ_LOCATION) {
        ucitajLokaciju();
    } else if (requestCode == REQ_CAMERA) {
        takePictureLauncher.launch(null);
    }
}
```

---

## Šta radi svaka linija (ukratko)

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

- [ ] `REQ_CAMERA` konstanta
- [ ] `takePictureLauncher` registrovan
- [ ] Klik na `imageButton` poziva `pokreniKameru()`
- [ ] Runtime dozvola za kameru
- [ ] Slika se vidi u `imageView`

---

## Sledeći korak

Folder **`04-senzor-ziroskop/`** – Toast sa X, Y, Z posle svake nove slike.
