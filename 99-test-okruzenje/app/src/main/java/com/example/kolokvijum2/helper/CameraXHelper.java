package com.example.kolokvijum2.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum2.CameraXActivity;

/**
 * Pokreće CameraX ekran (live preview unutar aplikacije).
 * Folder: 86-camerax/
 */
public class CameraXHelper {

    public interface OnSlikaSnimljenaListener {
        void onSlikaSnimljena(Uri uri);
    }

    private final AppCompatActivity activity;
    private final ImageView imageView;
    private final OnSlikaSnimljenaListener listener;
    private final ActivityResultLauncher<Intent> cameraXLauncher;

    public CameraXHelper(AppCompatActivity activity, ImageView imageView,
                          OnSlikaSnimljenaListener listener) {
        this.activity = activity;
        this.imageView = imageView;
        this.listener = listener;
        this.cameraXLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK
                            && result.getData() != null
                            && result.getData().getData() != null) {
                        Uri uri = result.getData().getData();
                        imageView.setImageURI(uri);
                        if (listener != null) {
                            listener.onSlikaSnimljena(uri);
                        }
                    }
                });
    }

    public void pokreni() {
        cameraXLauncher.launch(new Intent(activity, CameraXActivity.class));
    }
}
