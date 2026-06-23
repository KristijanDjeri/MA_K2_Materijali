package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/** Folder: 21-galerija/ */
public class GalerijaHelper {

    public static final int REQ_GALLERY = 103;

    public interface OnSlikaIzabranaListener {
        void onSlikaIzabrana(Uri uri);
    }

    private final AppCompatActivity activity;
    private final ImageView imageView;
    private final OnSlikaIzabranaListener listener;
    private final ActivityResultLauncher<String> pickImageLauncher;

    public GalerijaHelper(AppCompatActivity activity, ImageView imageView,
                          OnSlikaIzabranaListener listener) {
        this.activity = activity;
        this.imageView = imageView;
        this.listener = listener;
        this.pickImageLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                        if (listener != null) {
                            listener.onSlikaIzabrana(uri);
                        }
                    }
                });
    }

    public void otvori() {
        if (!imaDozvolu()) {
            traziDozvolu();
            return;
        }
        pickImageLauncher.launch("image/*");
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_GALLERY
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageLauncher.launch("image/*");
        }
    }

    private boolean imaDozvolu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void traziDozvolu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQ_GALLERY);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_GALLERY);
        }
    }
}
