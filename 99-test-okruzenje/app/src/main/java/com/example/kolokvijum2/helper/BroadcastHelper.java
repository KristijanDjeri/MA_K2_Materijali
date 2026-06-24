package com.example.kolokvijum2.helper;

import android.content.Context;
import android.content.Intent;

import com.example.kolokvijum2.PostUpdateReceiver;

/** Folder: 85-broadcast-receiver/ */
public class BroadcastHelper {

    public static void posaljiPostsUpdated(Context context) {
        Intent intent = new Intent(PostUpdateReceiver.ACTION_POSTS_UPDATED);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }
}
