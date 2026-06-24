# FileProvider – kamera u punoj rezoluciji i deljenje fajla

**Dodatni segment.** **Slično:** `03-kamera/` (`TakePicturePreview`), ali slika se **čuva u fajl** i može se **deliti** drugim aplikacijama.

**Cilj:** Snimi sliku u app-specific storage, prikaži u `ImageView`, opciono podeli preko implicit Intent-a.

---

## Fajlovi

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `file_paths.xml` | `res/xml/file_paths.xml` (kopiraj iz ovog foldera) |
| 2 | `AndroidManifest.xml` | `<provider>` unutar `<application>` |
| 3 | **`FileProviderHelper.java`** | `app/.../helper/FileProviderHelper.java` |
| 4 | `MainActivity.java` | Polje + init u **`onCreate`**, listener na kameru |

Gotovi: `file_paths.xml`, `AndroidManifest-provider.xml`, `FileProviderHelper.java`.

---

## Kompletan kod – helper klasa

Kopiraj **`FileProviderHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.FileProviderHelper;

private FileProviderHelper fileProviderHelper;

// onCreate, posle findViewById:
fileProviderHelper = new FileProviderHelper(this, imageView);
imageButton.setOnClickListener(v -> fileProviderHelper.pokreniKameru());
// opciono share: fileProviderHelper.podeliSliku();
```

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

## 3. Deljenje slike (opciono)

```java
// posle snimanja:
fileProviderHelper.podeliSliku();
```

> Metoda `podeliSliku()` je u `FileProviderHelper` – ne piši je u MainActivity.

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
- Implicit share: `71-implicit-intent/`
- Galerija: `76-galerija/`
