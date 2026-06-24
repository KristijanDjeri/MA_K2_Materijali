package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

/**
 * vežba F – zadatak 4.
 * Button startuje snimanje (disabled tokom snimanja).
 * CheckBox zaustavlja i čuva u getFilesDir().
 */
public class AudioSnimanjeHelper {

    public static final int REQ_AUDIO = 106;

    private final AppCompatActivity activity;
    private final Button buttonSnimi;
    private final CheckBox checkBoxSnimanje;
    private final String audioPutanja;

    private MediaRecorder mediaRecorder;
    private boolean snima = false;

    public AudioSnimanjeHelper(AppCompatActivity activity,
                               Button buttonSnimi,
                               CheckBox checkBoxSnimanje) {
        this.activity = activity;
        this.buttonSnimi = buttonSnimi;
        this.checkBoxSnimanje = checkBoxSnimanje;
        this.audioPutanja = new File(activity.getFilesDir(), "snimak.m4a")
                .getAbsolutePath();

        buttonSnimi.setOnClickListener(v -> pokreniSnimanje());

        checkBoxSnimanje.setOnCheckedChangeListener((v, isChecked) -> {
            if (isChecked && snima) {
                zaustaviSnimanje();
                checkBoxSnimanje.setChecked(false);
            }
        });
    }

    public boolean isSnima() {
        return snima;
    }

    public void onPause() {
        if (snima) {
            zaustaviSnimanje();
            checkBoxSnimanje.setChecked(false);
        }
    }

    public void onDestroy() {
        onPause();
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_AUDIO
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            zapocniRecorder();
        }
    }

    private void pokreniSnimanje() {
        if (snima) {
            return;
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
            return;
        }
        zapocniRecorder();
    }

    private void zapocniRecorder() {
        File stari = new File(audioPutanja);
        if (stari.exists()) {
            stari.delete();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                mediaRecorder = new MediaRecorder(activity);
            } else {
                mediaRecorder = new MediaRecorder();
            }
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(audioPutanja);
            mediaRecorder.prepare();
            mediaRecorder.start();

            snima = true;
            buttonSnimi.setEnabled(false);
            Toast.makeText(activity, "Snimanje...", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalStateException e) {
            oslobodiRecorder();
            snima = false;
            buttonSnimi.setEnabled(true);
            Toast.makeText(activity, "Greška snimanja", Toast.LENGTH_SHORT).show();
        }
    }

    private void zaustaviSnimanje() {
        if (mediaRecorder == null) {
            snima = false;
            buttonSnimi.setEnabled(true);
            return;
        }
        try {
            mediaRecorder.stop();
        } catch (RuntimeException e) {
            new File(audioPutanja).delete();
            Toast.makeText(activity, "Snimak prekratak", Toast.LENGTH_SHORT).show();
        } finally {
            oslobodiRecorder();
        }
        snima = false;
        buttonSnimi.setEnabled(true);
        if (new File(audioPutanja).exists()) {
            Toast.makeText(activity, "Sačuvano u files/", Toast.LENGTH_SHORT).show();
        }
    }

    private void oslobodiRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
