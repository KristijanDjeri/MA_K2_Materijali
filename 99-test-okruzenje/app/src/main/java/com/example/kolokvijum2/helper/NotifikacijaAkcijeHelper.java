package com.example.kolokvijum2.helper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.kolokvijum2.MainActivity;
import com.example.kolokvijum2.NotifikacijaAkcijaReceiver;

/** Notifikacija sa dugmadima – folder 39-notifikacija-akcije/ */
public class NotifikacijaAkcijeHelper {

    public static final int NOTIF_AKCIJE_ID = 101;

    public static void posaljiSaAkcijama(AppCompatActivity activity) {
        NotifikacijaHelper.kreirajKanal(activity);

        Intent otvoriIntent = new Intent(activity, NotifikacijaAkcijaReceiver.class);
        otvoriIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OTVORI);
        PendingIntent piOtvori = PendingIntent.getBroadcast(
                activity, 1, otvoriIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent obrisiIntent = new Intent(activity, NotifikacijaAkcijaReceiver.class);
        obrisiIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OBRISI);
        PendingIntent piObrisi = PendingIntent.getBroadcast(
                activity, 2, obrisiIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent contentIntent = new Intent(activity, MainActivity.class);
        PendingIntent piContent = PendingIntent.getActivity(
                activity, 0, contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NotifikacijaHelper.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Postovi")
                .setContentText("Imate nepročitanih postova")
                .setContentIntent(piContent)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_view, "Otvori", piOtvori)
                .addAction(android.R.drawable.ic_menu_delete, "Obriši", piObrisi);

        NotificationManagerCompat.from(activity).notify(NOTIF_AKCIJE_ID, builder.build());
    }

    public static void ukloni(Context context) {
        NotificationManagerCompat.from(context).cancel(NOTIF_AKCIJE_ID);
    }
}
