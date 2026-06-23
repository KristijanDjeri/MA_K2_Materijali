package com.example.kolokvijum2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotifikacijaAkcijaReceiver extends BroadcastReceiver {

    public static final String ACTION_OTVORI = "com.example.kolokvijum2.NOTIF_OTVORI";
    public static final String ACTION_OBRISI = "com.example.kolokvijum2.NOTIF_OBRISI";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        switch (intent.getAction()) {
            case ACTION_OTVORI:
                Toast.makeText(context, "Akcija: Otvori", Toast.LENGTH_SHORT).show();
                break;
            case ACTION_OBRISI:
                Toast.makeText(context, "Akcija: Obriši", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
