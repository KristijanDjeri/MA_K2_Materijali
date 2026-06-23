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
