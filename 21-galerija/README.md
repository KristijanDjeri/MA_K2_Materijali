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

## Kompletan kod za `MainActivity.java`

### 1. Importi

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
```

### 2. Konstanta i launcher

```java
private static final int REQ_GALLERY = 103;

private final ActivityResultLauncher<String> pickImageLauncher =
        registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                prikaziZiroskopToast(); // opciono, iz 04-senzor-ziroskop/
            }
        });
```

### 3. Pokretanje (npr. dugi klik na imageButton ili posebno dugme)

```java
// Primer: dugi klik na kameru otvara galeriju
imageButton.setOnLongClickListener(v -> {
    otvoriGaleriju();
    return true;
});
```

### 4. Metode za dozvolu i otvaranje

```java
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
```

### 5. U `onRequestPermissionsResult`

```java
} else if (requestCode == REQ_GALLERY) {
    pickImageLauncher.launch("image/*");
}
```

---

## Alternativa

- `PickVisualMedia()` umesto `GetContent()` – noviji API za slike
- `setImageBitmap` umesto `setImageURI` – ako konvertuješ URI u Bitmap

---

## Checklist

- [ ] Dozvole u Manifest-u
- [ ] `pickImageLauncher` registrovan
- [ ] `launch("image/*")` otvara galeriju
- [ ] Slika u ImageView
