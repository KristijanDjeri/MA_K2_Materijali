// FileProvider + TakePicture (folder 59-file-provider/)

import android.net.Uri;
import android.os.Environment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

private static final String AUTHORITY = "com.example.kolokvijum2.fileprovider";

private Uri photoUri;
private File photoFile;

private final ActivityResultLauncher<Uri> takePictureFullLauncher =
        registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
            if (Boolean.TRUE.equals(success) && photoUri != null) {
                imageView.setImageURI(photoUri);
            }
        });

private File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(new Date());
    File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    return File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", dir);
}

private void pokreniKameruPunaRezolucija() {
    try {
        photoFile = createImageFile();
        photoUri = FileProvider.getUriForFile(this, AUTHORITY, photoFile);
        takePictureFullLauncher.launch(photoUri);
    } catch (IOException e) {
        Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

private void podeliSliku() {
    if (photoUri == null) return;
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("image/jpeg");
    intent.putExtra(Intent.EXTRA_STREAM, photoUri);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(intent, "Podeli sliku"));
}
