# Galerija – izbor slike (umesto kamere)

**Slično:** `kamera/` – isti `ActivityResultLauncher` obrazac, druga dozvola/intent.

**Mogući zadatak:** Dugme otvara galeriju, izabrana slika u `ImageView`.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Logika | `MainActivity.java` |
| Dozvola (API 33+) | `READ_MEDIA_IMAGES` |
| Dozvola (API 28–32) | `READ_EXTERNAL_STORAGE` |

## Manifest

```xml
<!-- Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<!-- Android 9–12 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

## Fajlovi

- `GalerijaSegment.java`

## Razlika od kamere

| Kamera | Galerija |
|--------|----------|
| `TakePicturePreview()` | `GetContent()` ili `PickVisualMedia()` |
| `CAMERA` dozvola | `READ_MEDIA_IMAGES` / storage |

## Checklist

- [ ] `registerForActivityResult(GetContent(), ...)`
- [ ] `launcher.launch("image/*")`
- [ ] `imageView.setImageURI(uri)`
