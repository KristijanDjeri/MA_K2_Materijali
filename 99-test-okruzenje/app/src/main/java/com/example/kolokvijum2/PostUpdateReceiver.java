package com.example.kolokvijum2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * BroadcastReceiver – prima emitovani Intent u pozadini.
 * (Nije „ContentReceiver“ – to nije Android termin.)
 * Folder: 85-broadcast-receiver/
 */
public class PostUpdateReceiver extends BroadcastReceiver {

    public static final String ACTION_POSTS_UPDATED =
            "com.example.kolokvijum2.POSTS_UPDATED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_POSTS_UPDATED.equals(intent.getAction())) {
            Toast.makeText(context, "Lista postova osvežena", Toast.LENGTH_SHORT).show();
        }
    }
}
