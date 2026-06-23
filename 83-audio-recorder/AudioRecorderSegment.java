package com.example.kolokvijum2;

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
 * Zastarelo – koristi {@link com.example.kolokvijum2.helper.AudioRecorder} (AudioRecorder.java u ovom folderu).
 * Ovaj fajl ostaje kao skraćena referenca.
 */
@Deprecated
public class AudioRecorderSegment extends AppCompatActivity {

    private static final int REQ_AUDIO = 106;

    private Button btnSnimi;
    private Button btnPusti;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean snima = false;
    private String audioPutanja;

  // Primer inicijalizacije u onCreate MainActivity-ja:
  // btnSnimi = findViewById(R.id.btnSnimi);
  // btnPusti = findViewById(R.id.btnPusti);
  // audioPutanja = new File(getExternalFilesDir(null), "snimak.m4a").getAbsolutePath();
  // btnSnimi.setOnClickListener(v -> { if (!snima) pokreniSnimanje(); else zaustaviSnimanje(); });
  // btnPusti.setOnClickListener(v -> pustiSnimak());

    private void pokreniSnimanje() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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
                mediaRecorder = new MediaRecorder(this);
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
            Toast.makeText(this, "Snimanje...", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalStateException e) {
            oslobodiMediaRecorder();
            Toast.makeText(this, "Greška snimanja", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Snimak prekratak", Toast.LENGTH_SHORT).show();
        } finally {
            oslobodiMediaRecorder();
        }
        snima = false;
        btnSnimi.setText("Snimi");
        if (new File(audioPutanja).exists()) {
            Toast.makeText(this, "Snimljeno", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Prvo zaustavi snimanje", Toast.LENGTH_SHORT).show();
            return;
        }
        File f = new File(audioPutanja);
        if (!f.exists() || f.length() == 0) {
            Toast.makeText(this, "Nema snimka", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Greška reprodukcije", Toast.LENGTH_SHORT).show();
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
