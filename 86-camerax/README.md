# CameraX – kamera unutar aplikacije (dodatni segment)

**Cilj:** Kamera **unutar aplikacije** sa **live preview**-om (slično Instagram story-ju), umesto spoljašnje kamere iz `03-kamera/`.

| | `03-kamera/` | **Ovaj segment** |
|---|-------------|------------------|
| UI | Spoljašnja kamera (sistem) | Preview u aplikaciji |
| API | `TakePicturePreview()` | CameraX (`Preview` + `ImageCapture`) |
| Rezolucija | Manja (preview bitmap) | Puna (JPEG fajl) |
| Čuvanje | Samo u `ImageView` | Folder aplikacije ili galerija |

---

## Šta ti treba pre ovoga

- `01-osnovni-projekat/` – layout sa `imageButton` i `imageView`
- Dozvola `CAMERA` u Manifest-u
- Gradle zavisnosti za **CameraX** (ispod)

---

## Korak 1: Gradle (`app/build.gradle`)

U `dependencies { ... }` dodaj:

```gradle
def camerax_version = "1.3.1"
implementation "androidx.camera:camera-core:${camerax_version}"
implementation "androidx.camera:camera-camera2:${camerax_version}"
implementation "androidx.camera:camera-lifecycle:${camerax_version}"
implementation "androidx.camera:camera-view:${camerax_version}"
```

Klikni **Sync Now**.

---

## Korak 2: Manifest

Dozvola `CAMERA` već postoji iz `01-osnovni-projekat/`.

Unutar `<application>`, **pored** `MainActivity`, dodaj:

```xml
<activity
    android:name=".CameraXActivity"
    android:exported="false"
    android:screenOrientation="portrait"
    android:theme="@style/Theme.AppCompat.NoActionBar" />
```

> Sa **minSdk 30** dozvola `WRITE_EXTERNAL_STORAGE` za galeriju **nije potrebna** – koristi se `MediaStore` sa `RELATIVE_PATH` (API 29+).

---

## Korak 3: Layout fajlovi

| Fajl | Putanja u projektu |
|------|-------------------|
| `fragment_camerax.xml` | `res/layout/fragment_camerax.xml` |
| `activity_camerax.xml` | `res/layout/activity_camerax.xml` |

Kopiraj iz ovog foldera.

---

## Korak 4: Java fajlovi

| Fajl | Putanja |
|------|---------|
| `CameraXFragment.java` | `app/.../CameraXFragment.java` |
| `CameraXActivity.java` | `app/.../CameraXActivity.java` |
| **`CameraXHelper.java`** | `app/.../helper/CameraXHelper.java` |

Gotovi fajlovi u ovom folderu.

---

## Koji fajlovi se menjaju u MainActivity

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `CameraXHelper.java` | Novi fajl u `helper/` |
| 2 | `MainActivity.java` | Polje: `private CameraXHelper cameraXHelper;` |
| 3 | `MainActivity.java` | Init u `onCreate` (primer ispod) |

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.CameraXHelper;
```

### Polje

```java
private CameraXHelper cameraXHelper;
```

### U `onCreate`, posle `findViewById`

```java
cameraXHelper = new CameraXHelper(this, imageView, uri -> {
    // Opciono: ziroskopHelper.prikaziToast(); — vidi 04-senzor-ziroskop/
});

// Dugi klik na imageButton = CameraX (live preview)
// Običan klik ostaje za KameraHelper iz 03-kamera/
imageButton.setOnLongClickListener(v -> {
    cameraXHelper.pokreni();
    return true;
});
```

> **Savet:** Na kolokvijumu zadatak 4 koristi `03-kamera/` (`TakePicturePreview`). CameraX je **dodatna** varijanta za vežbu.

---

## Kako radi CameraX (ukratko)

| Deo | Značenje |
|-----|----------|
| `PreviewView` | Live prikaz kamere na ekranu |
| `ImageCapture` | Snima JPEG u fajl |
| `ProcessCameraProvider` | Povezuje kameru sa lifecycle-om |
| `bindToLifecycle()` | Kamera radi dok je fragment vidljiv |
| Dijalog „Sačuvaj sliku" | Folder aplikacije / galerija / vrati u app |

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.kolokvijum2.CameraXActivity;

// POLJA:
private ImageButton imageButton;
private ImageView imageView;

private final ActivityResultLauncher<Intent> cameraXLauncher =
        registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK
                            && result.getData() != null
                            && result.getData().getData() != null) {
                        Uri uri = result.getData().getData();
                        imageView.setImageURI(uri);
                    }
                });

// U onCreate(), posle findViewById:
imageButton.setOnLongClickListener(v -> {
    cameraXLauncher.launch(new Intent(this, CameraXActivity.class));
    return true;
});

// Napomena: CameraXActivity + CameraXFragment + layout fajlovi iz 86-camerax/
```

Kompletan CameraX kod (preview, snimanje, čuvanje) ostaje u **`CameraXFragment.java`** – ne dupliraj ga u MainActivity.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `CameraXActivity` + Fragment | CameraX direktno u `MainActivity` (bez fragmenta) |
| Dugi klik na `imageButton` | Posebno dugme „Story kamera" u layoutu |
| `03-kamera/` TakePicturePreview | Brže za kolokvijum, manje koda |
| Čuvanje u galeriju | Samo `getExternalFilesDir()` – bez MediaStore |

---

## Checklist

- [ ] CameraX zavisnosti u Gradle-u (Sync uspeo)
- [ ] `CameraXActivity` u Manifest-u
- [ ] Layout + `CameraXFragment` + `CameraXActivity` kopirani
- [ ] `CameraXHelper` u paketu `helper`
- [ ] Dugi klik otvara live preview
- [ ] Snimljena slika se vidi u `imageView`

---

## Povezani segmenti

- **`03-kamera/`** – zvanični zadatak 4 (spoljašnja kamera)
- **`76-galerija/`** – izbor slike iz galerije
- **`77-file-provider/`** – deljenje snimljene slike
- **`19-fragment-primer/`** – Fragment osnove
