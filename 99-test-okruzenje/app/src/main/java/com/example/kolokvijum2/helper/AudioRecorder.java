package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

/**
 * Snimanje i reprodukcija audio fajla (.m4a).
 * Folder: 36-audio-recorder/
 */
public class AudioRecorder {

    public static final int REQ_AUDIO = 106;

    private final AppCompatActivity activity;
    private final Button btnSnimi;
    private final Button btnPusti;
    private final String audioPutanja;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean snima = false;

    public AudioRecorder(AppCompatActivity activity, Button btnSnimi, Button btnPusti) {
        this.activity = activity;
        this.btnSnimi = btnSnimi;
        this.btnPusti = btnPusti;
        this.audioPutanja = new File(activity.getExternalFilesDir(null), "snimak.m4a")
                .getAbsolutePath();

        btnSnimi.setOnClickListener(v -> {
            if (!snima) {
                pokreniSnimanje();
            } else {
                zaustaviSnimanje();
            }
        });
        btnPusti.setOnClickListener(v -> pustiSnimak());
    }

    public void onPause() {
        if (snima) {
            zaustaviSnimanje();
        }
        zaustaviReprodukciju();
    }

    public void onDestroy() {
        onPause();
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_AUDIO
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            zapocniMediaRecorder();
        }
    }

    private void pokreniSnimanje() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
            return;
        }
        zapocniMediaRecorder();
    }

    private void zapocniMediaRecorder() {
        zaustaviReprodukciju();
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
            btnSnimi.setText("Stop");
            Toast.makeText(activity, "Snimanje...", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalStateException e) {
            oslobodiMediaRecorder();
            Toast.makeText(activity, "Greška snimanja", Toast.LENGTH_SHORT).show();
        }
    }

    private void zaustaviSnimanje() {
        if (mediaRecorder == null) {
            snima = false;
            btnSnimi.setText("Snimi");
            return;
        }
        try {
            mediaRecorder.stop();
        } catch (RuntimeException e) {
            new File(audioPutanja).delete();
            Toast.makeText(activity, "Snimak prekratak", Toast.LENGTH_SHORT).show();
        } finally {
            oslobodiMediaRecorder();
        }
        snima = false;
        btnSnimi.setText("Snimi");
        if (new File(audioPutanja).exists()) {
            Toast.makeText(activity, "Snimljeno", Toast.LENGTH_SHORT).show();
        }
    }

    private void oslobodiMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void pustiSnimak() {
        if (snima) {
            Toast.makeText(activity, "Prvo zaustavi snimanje", Toast.LENGTH_SHORT).show();
            return;
        }
        File f = new File(audioPutanja);
        if (!f.exists() || f.length() == 0) {
            Toast.makeText(activity, "Nema snimka", Toast.LENGTH_SHORT).show();
            return;
        }
        zaustaviReprodukciju();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioPutanja);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> zaustaviReprodukciju());
        } catch (IOException e) {
            zaustaviReprodukciju();
            Toast.makeText(activity, "Greška reprodukcije", Toast.LENGTH_SHORT).show();
        }
    }

    private void zaustaviReprodukciju() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException ignored) {
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
