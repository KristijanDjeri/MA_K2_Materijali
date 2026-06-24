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

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- `PickVisualMedia()` umesto `GetContent()` – noviji API za slike
- `setImageBitmap` umesto `setImageURI` – ako konvertuješ URI u Bitmap

---

## Checklist

- [ ] Dozvole u Manifest-u
- [ ] `pickImageLauncher` registrovan
- [ ] `launch("image/*")` otvara galeriju
- [ ] Slika u ImageView
