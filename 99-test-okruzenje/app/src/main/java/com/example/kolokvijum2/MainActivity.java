package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.helper.AkcelerometarHelper;
import com.example.kolokvijum2.helper.CameraXHelper;
import com.example.kolokvijum2.helper.GeoLokacijaHelper;
import com.example.kolokvijum2.helper.KameraHelper;
import com.example.kolokvijum2.helper.KontaktiHelper;
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.PostRepository;
import com.example.kolokvijum2.helper.SharedPreferencesHelper;
import com.example.kolokvijum2.helper.SwitchPostsHelper;
import com.example.kolokvijum2.helper.ZiroskopHelper;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageButton imageButton;
    private ImageView imageView;
    private Switch switchPosts;
    private Button button;

    private GeoLokacijaHelper geoHelper;
    private KameraHelper kameraHelper;
    private CameraXHelper cameraXHelper;
    private ZiroskopHelper ziroskopHelper;
    private AkcelerometarHelper akcelerometarHelper;
    private PostRepository postRepository;
    private SharedPreferencesHelper prefsHelper;
    private KontaktiHelper kontaktiHelper;
    private SwitchPostsHelper switchPostsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        switchPosts = findViewById(R.id.switchPosts);
        button = findViewById(R.id.button);

        geoHelper = new GeoLokacijaHelper(this, textView);
        ziroskopHelper = new ZiroskopHelper(this);
        akcelerometarHelper = new AkcelerometarHelper(this, button);
        kameraHelper = new KameraHelper(this, imageView, bitmap -> ziroskopHelper.prikaziToast());
        cameraXHelper = new CameraXHelper(this, imageView, uri -> ziroskopHelper.prikaziToast());
        postRepository = new PostRepository(this, AppDatabase.getInstance(this).postDao());
        prefsHelper = new SharedPreferencesHelper(this);
        kontaktiHelper = new KontaktiHelper(this, textView);
        switchPostsHelper = new SwitchPostsHelper(this, switchPosts, textView,
                postRepository, prefsHelper, kontaktiHelper);

        NotifikacijaHelper.kreirajKanal(this);
        geoHelper.pokreni();

        imageButton.setOnClickListener(v -> kameraHelper.pokreni());
        imageButton.setOnLongClickListener(v -> {
            cameraXHelper.pokreni();
            return true;
        });
        button.setOnClickListener(v -> postRepository.obrisiPrviPost(
                () -> NotifikacijaHelper.posaljiPraznaBaza(this)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ziroskopHelper.onResume();
        akcelerometarHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ziroskopHelper.onPause();
        akcelerometarHelper.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0 || grantResults[0] != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geoHelper.onPermissionGranted(requestCode, grantResults);
        kameraHelper.onPermissionGranted(requestCode, grantResults);
        kontaktiHelper.onPermissionGranted(requestCode, grantResults);
    }
}
