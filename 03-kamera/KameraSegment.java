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
                // Pozovi žiroskop Toast (folder ziroskop/):
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
