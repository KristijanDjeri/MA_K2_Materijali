// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import com.example.kolokvijum2.R;
import com.example.kolokvijum2.helper.MediaPlayerHelper;

// POLJE:
private MediaPlayerHelper mediaPlayerHelper;

// U onCreate():
ImageView imageViewMedia = findViewById(R.id.imageViewMedia);
VideoView videoView = findViewById(R.id.videoView);
Button btnPlayVideo = findViewById(R.id.btnPlayVideo);
Button btnPustiAudio = findViewById(R.id.btnPustiAudio);
Button btnPauzaAudio = findViewById(R.id.btnPauzaAudio);

mediaPlayerHelper = new MediaPlayerHelper(
        this, imageViewMedia, videoView,
        btnPlayVideo, btnPustiAudio, btnPauzaAudio
);

// Primer: fajlovi u res/drawable i res/raw
mediaPlayerHelper.prikaziSliku(R.drawable.sample_slika);
mediaPlayerHelper.pripremiVideo(R.raw.sample_video);
mediaPlayerHelper.pripremiAudio(R.raw.sample_audio);

// LIFECYCLE u MainActivity:
@Override protected void onPause() {
    super.onPause();
    if (mediaPlayerHelper != null) {
        mediaPlayerHelper.onPause();
    }
}

@Override protected void onDestroy() {
    if (mediaPlayerHelper != null) {
        mediaPlayerHelper.onDestroy();
    }
    super.onDestroy();
}
