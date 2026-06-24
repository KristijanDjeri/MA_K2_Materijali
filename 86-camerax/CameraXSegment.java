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
                        // Opciono: ziroskop Toast (04-senzor-ziroskop/)
                        // prikaziZiroskopToast();
                    }
                });

// U onCreate(), posle findViewById:
// Dugi klik = CameraX (live preview), običan klik = stara kamera (03-kamera/)
imageButton.setOnLongClickListener(v -> {
    cameraXLauncher.launch(new Intent(this, CameraXActivity.class));
    return true;
});

// Napomena: CameraXActivity + CameraXFragment + layout fajlovi iz 86-camerax/
