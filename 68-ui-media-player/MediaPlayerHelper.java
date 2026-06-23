package com.example.kolokvijum2.helper;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * UI za media player: slika (ImageView), video (VideoView), audio (MediaPlayer).
 * Folder: 68-ui-media-player/
 */
public class MediaPlayerHelper {

    private final AppCompatActivity activity;
    private final ImageView imageViewMedia;
    private final VideoView videoView;
    private final Button btnPlayVideo;
    private final Button btnPustiAudio;
    private final Button btnPauzaAudio;

    private MediaPlayer mediaPlayer;

    public MediaPlayerHelper(AppCompatActivity activity,
                             ImageView imageViewMedia,
                             VideoView videoView,
                             Button btnPlayVideo,
                             Button btnPustiAudio,
                             Button btnPauzaAudio) {
        this.activity = activity;
        this.imageViewMedia = imageViewMedia;
        this.videoView = videoView;
        this.btnPlayVideo = btnPlayVideo;
        this.btnPustiAudio = btnPustiAudio;
        this.btnPauzaAudio = btnPauzaAudio;

        btnPlayVideo.setEnabled(false);
        btnPlayVideo.setOnClickListener(v -> videoView.start());
        btnPustiAudio.setOnClickListener(v -> pustiAudio());
        btnPauzaAudio.setOnClickListener(v -> pauzirajAudio());
    }

    /** Slika iz drawable resursa (npr. R.drawable.sample_slika). */
    public void prikaziSliku(int drawableResId) {
        imageViewMedia.setImageResource(drawableResId);
    }

    /** Slika iz URI-ja (npr. posle galerije ili kamere). */
    public void prikaziSliku(Uri uri) {
        imageViewMedia.setImageURI(uri);
    }

    /** Video iz res/raw (npr. R.raw.sample_video). */
    public void pripremiVideo(int rawResId) {
        String putanja = "android.resource://" + activity.getPackageName() + "/" + rawResId;
        videoView.setVideoURI(Uri.parse(putanja));
        MediaController controller = new MediaController(activity);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);
        videoView.setOnPreparedListener(mp -> btnPlayVideo.setEnabled(true));
        videoView.requestFocus();
    }

    /** Audio iz res/raw (npr. R.raw.sample_audio). */
    public void pripremiAudio(int rawResId) {
        oslobodiAudio();
        mediaPlayer = MediaPlayer.create(activity, rawResId);
        if (mediaPlayer == null) {
            Toast.makeText(activity, "Audio fajl nije pronađen", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaPlayer.setOnCompletionListener(mp -> Toast.makeText(
                activity, "Audio završen", Toast.LENGTH_SHORT).show());
    }

    private void pustiAudio() {
        if (mediaPlayer == null) {
            Toast.makeText(activity, "Nema učitanog audio fajla", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pauzirajAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void onPause() {
        pauzirajAudio();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    public void onDestroy() {
        onPause();
        oslobodiAudio();
        videoView.stopPlayback();
    }

    private void oslobodiAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
