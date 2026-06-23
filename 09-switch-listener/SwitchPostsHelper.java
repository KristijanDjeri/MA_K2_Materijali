package com.example.kolokvijum2.helper;

import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 09-switch-listener/ – spaja Switch ON/OFF sa repository + prefs + kontakti */
public class SwitchPostsHelper {

    private final AppCompatActivity activity;
    private final Switch switchPosts;
    private final PostRepository postRepository;
    private final SharedPreferencesHelper prefsHelper;
    private final KontaktiHelper kontaktiHelper;
    private final TextView textView;

    public SwitchPostsHelper(AppCompatActivity activity,
                             Switch switchPosts,
                             TextView textView,
                             PostRepository postRepository,
                             SharedPreferencesHelper prefsHelper,
                             KontaktiHelper kontaktiHelper) {
        this.activity = activity;
        this.switchPosts = switchPosts;
        this.textView = textView;
        this.postRepository = postRepository;
        this.prefsHelper = prefsHelper;
        this.kontaktiHelper = kontaktiHelper;

        switchPosts.setOnCheckedChangeListener(this::onSwitchChanged);
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            obradiSwitchOn();
        } else {
            obradiSwitchOff();
        }
    }

    private void obradiSwitchOn() {
        if (!postRepository.isPostsUcitani()) {
            postRepository.ucitajPostoveSaApi(new PostRepository.OnApiDoneListener() {
                @Override
                public void onSuccess(int count) {
                    Toast.makeText(activity, "Učitano " + count + " postova", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(activity, "Greška: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            postRepository.prikaziTitlePrvogPosta();
        }
    }

    private void obradiSwitchOff() {
        prefsHelper.sacuvajTextView(textView);
        kontaktiHelper.postaviImePrvogKontakta();
    }
}
