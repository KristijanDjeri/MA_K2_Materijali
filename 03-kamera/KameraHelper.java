package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class KameraHelper {

    public static final int REQ_CAMERA = 101;

    public interface OnSlikaSnimljenaListener {
        void onSlikaSnimljena(Bitmap bitmap);
    }

    private final AppCompatActivity activity;
    private final ImageView imageView;
    private final OnSlikaSnimljenaListener listener;
    private final ActivityResultLauncher<Void> takePictureLauncher;

    public KameraHelper(AppCompatActivity activity, ImageView imageView,
                        OnSlikaSnimljenaListener listener) {
        this.activity = activity;
        this.imageView = imageView;
        this.listener = listener;
        this.takePictureLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                bitmap -> {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        if (listener != null) {
                            listener.onSlikaSnimljena(bitmap);
                        }
                    }
                });
    }

    public void pokreni() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
            return;
        }
        takePictureLauncher.launch(null);
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_CAMERA
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureLauncher.launch(null);
        }
    }
}
