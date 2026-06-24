package com.example.kolokvijum2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Full-screen host za {@link CameraXFragment}.
 * Vraća URI slike u pozivajuću aktivnost preko setResult.
 */
public class CameraXActivity extends AppCompatActivity implements CameraXFragment.OnPhotoReadyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerax);

        if (savedInstanceState == null) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fragmentContainer, new CameraXFragment());
            tx.commit();
        }
    }

    @Override
    public void onPhotoReady(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);
        setResult(RESULT_OK, data);
        finish();
    }
}
