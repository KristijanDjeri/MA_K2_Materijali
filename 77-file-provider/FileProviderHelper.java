package com.example.kolokvijum2.helper;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Kamera pun rezolucija + deljenje slike.
 * Folder: 59-file-provider/
 */
public class FileProviderHelper {

    public static final String AUTHORITY = "com.example.kolokvijum2.fileprovider";

    private final AppCompatActivity activity;
    private final ImageView imageView;
    private final ActivityResultLauncher<Uri> takePictureLauncher;

    private Uri photoUri;
    private File photoFile;

    public FileProviderHelper(AppCompatActivity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
        this.takePictureLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (Boolean.TRUE.equals(success) && photoUri != null) {
                        imageView.setImageURI(photoUri);
                    }
                });
    }

    public void pokreniKameru() {
        try {
            photoFile = createImageFile();
            photoUri = FileProvider.getUriForFile(activity, AUTHORITY, photoFile);
            takePictureLauncher.launch(photoUri);
        } catch (IOException e) {
            Toast.makeText(activity, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void podeliSliku() {
        if (photoUri == null) {
            Toast.makeText(activity, "Nema slike", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(intent, "Podeli sliku"));
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", dir);
    }
}
