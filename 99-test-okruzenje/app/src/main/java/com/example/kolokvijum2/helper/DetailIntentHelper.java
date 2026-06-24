package com.example.kolokvijum2.helper;

import android.content.Context;
import android.content.Intent;

import com.example.kolokvijum2.DetailActivity;

/** Folder: 70-intent-druga-aktivnost/ */
public class DetailIntentHelper {

    public static void otvoriDetalj(Context context, String naslov) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("naslov", naslov);
        context.startActivity(intent);
    }

    public static void otvoriDetaljPrvogPosta(Context context, PostRepository postRepository) {
        if (postRepository.getFirst() != null) {
            otvoriDetalj(context, postRepository.getFirst().getTitle());
        }
    }
}
