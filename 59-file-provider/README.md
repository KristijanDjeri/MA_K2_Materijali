# FileProvider – kamera u punoj rezoluciji i deljenje fajla

**Dodatni segment.** **Slično:** `03-kamera/` (`TakePicturePreview`), ali slika se **čuva u fajl** i može se **deliti** drugim aplikacijama.

**Cilj:** Snimi sliku u app-specific storage, prikaži u `ImageView`, opciono podeli preko implicit Intent-a.

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `file_paths.xml` | `res/xml/file_paths.xml` |
| `AndroidManifest.xml` | `<provider>` unutar `<application>` |
| `MainActivity.java` | `TakePicture()` + URI |

Gotovi: `file_paths.xml`, `AndroidManifest-provider.xml`, `FileProviderSegment.java`.

---

## 1. `res/xml/file_paths.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-files-path
        name="images"
        path="Pictures/" />
    <cache-path
        name="cache_images"
        path="/" />
</paths>
```

> `external-files-path` → `getExternalFilesDir(Environment.DIRECTORY_PICTURES)`  
> Ne treba `WRITE_EXTERNAL_STORAGE` na Android 10+ za app-specific folder.

---

## 2. `AndroidManifest.xml` – unutar `<application>`

```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

**Važno:** `authorities` mora biti **`tvoj.package.name.fileprovider`** – u kodu koristi isti string.

---

## 3. `MainActivity.java`

### Importi

```java
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
```

### Polja

```java
private static final int REQ_CAMERA = 101;
private static final String AUTHORITY = "com.example.kolokvijum2.fileprovider";

private Uri photoUri;
private File photoFile;

private final ActivityResultLauncher<Uri> takePictureFullLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
            if (Boolean.TRUE.equals(success) && photoUri != null) {
                imageView.setImageURI(photoUri);
            }
        });
```

### Kreiranje fajla za sliku

```java
private File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(new Date());
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    return File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
}
```

### Pokretanje kamere

```java
private void pokreniKameruPunaRezolucija() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
        return;
    }
    try {
        photoFile = createImageFile();
        photoUri = FileProvider.getUriForFile(this, AUTHORITY, photoFile);
        takePictureFullLauncher.launch(photoUri);
    } catch (IOException e) {
        Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
```

### Listener

```java
imageButton.setOnClickListener(v -> pokreniKameruPunaRezolucija());
```

### Dozvola – u `onRequestPermissionsResult`

```java
} else if (requestCode == REQ_CAMERA) {
    pokreniKameruPunaRezolucija();
}
```

---

## 4. Deljenje slike (implicit Intent + FileProvider)

```java
private void podeliSliku() {
    if (photoUri == null) {
        Toast.makeText(this, "Nema slike", Toast.LENGTH_SHORT).show();
        return;
    }
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("image/jpeg");
    intent.putExtra(Intent.EXTRA_STREAM, photoUri);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(intent, "Podeli sliku"));
}
```

---

## FileProvider vs TakePicturePreview

| | `TakePicturePreview` | `TakePicture` + FileProvider |
|---|---------------------|------------------------------|
| Kod | Manje | Više |
| Rezolucija | Manja | Puna |
| Fajl na disku | Ne | Da |
| Deljenje | Teže | Lako |

---

## Checklist

- [ ] `file_paths.xml` postoji
- [ ] `<provider>` u Manifest-u, `authorities` = package + `.fileprovider`
- [ ] `FileProvider.getUriForFile` sa istim authority-jem
- [ ] `TakePicture()` launcher prima `Uri`, ne `null`
- [ ] `FLAG_GRANT_READ_URI_PERMISSION` pri share

---

## Povezano

- Brza kamera: `03-kamera/`
- Implicit share: `56-implicit-intent/`
- Galerija: `21-galerija/`
