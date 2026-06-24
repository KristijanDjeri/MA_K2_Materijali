# Galerija – izbor slike iz galerije

**Dodatni segment.** **Slično:** `03-kamera/` (ActivityResult + dozvola + ImageView).

**Cilj:** Korisnik bira sliku iz galerije umesto da slika kamerom.

---

## Manifest – dodaj u `<manifest>` (pored ostalih dozvola)

```xml
<!-- Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<!-- Android 9–12 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`GalerijaHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | Klik na dugme: `galerijaHelper.otvori()` |
| 4 | `MainActivity.java` | **`onRequestPermissionsResult`**: `galerijaHelper.onPermissionGranted(...)` |

---

## Kompletan kod – helper klasa

Kopiraj **`GalerijaHelper.java`** iz ovog foldera u `app/.../helper/GalerijaHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.GalerijaHelper;
```

### Polje

```java
private GalerijaHelper galerijaHelper;
```

### U `onCreate`

```java
galerijaHelper = new GalerijaHelper(this, imageView, uri -> {
    // opciono: dodatna logika posle izbora slike
});
imageButton.setOnClickListener(v -> galerijaHelper.otvori());
```

### U `onRequestPermissionsResult`

```java
if (galerijaHelper != null) {
    galerijaHelper.onPermissionGranted(requestCode, grantResults);
}
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final int REQ_GALLERY = 103;

private final ActivityResultLauncher<String> pickImageLauncher =
        registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                prikaziZiroskopToast(); // isto kao posle kamere
            }
        });

// METODE:

private void otvoriGaleriju() {
    if (!imaDozvoluZaGaleriju()) {
        traziDozvoluZaGaleriju();
        return;
    }
    pickImageLauncher.launch("image/*");
}

private boolean imaDozvoluZaGaleriju() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED;
    }
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED;
}

private void traziDozvoluZaGaleriju() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQ_GALLERY);
    } else {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_GALLERY);
    }
}

// U onRequestPermissionsResult:
// case REQ_GALLERY: pickImageLauncher.launch("image/*"); break;
```

## Alternativa

- `PickVisualMedia()` umesto `GetContent()` – noviji API za slike
- `setImageBitmap` umesto `setImageURI` – ako konvertuješ URI u Bitmap

---

## Checklist

- [ ] Dozvole u Manifest-u
- [ ] `pickImageLauncher` registrovan
- [ ] `launch("image/*")` otvara galeriju
- [ ] Slika u ImageView
