package com.example.kolokvijum2.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/** Proširene notifikacije – folder 40-notifikacija-prosirena/ */
public class NotifikacijaProsirenaHelper {

    public static final String CHANNEL_ID = NotifikacijaHelper.CHANNEL_ID;

    public static void posaljiBigText(Context context, String naslov, String dugiTekst) {
        NotifikacijaHelper.kreirajKanal(context);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
                .bigText(dugiTekst)
                .setBigContentTitle(naslov);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(naslov)
                .setContentText("Kratki pregled…")
                .setStyle(style)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(102, builder.build());
    }

    public static void posaljiInbox(Context context, String... linije) {
        NotifikacijaHelper.kreirajKanal(context);
        NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle()
                .setBigContentTitle("Poslednji postovi");
        for (String linija : linije) {
            inbox.addLine(linija);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Nova obaveštenja")
                .setContentText("Povuci za listu")
                .setStyle(inbox)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(104, builder.build());
    }

    public static void posaljiOngoing(Context context) {
        NotifikacijaHelper.kreirajKanal(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Snimanje u toku")
                .setContentText("Dodirni Stop u aplikaciji")
                .setOngoing(true);

        NotificationManagerCompat.from(context).notify(105, builder.build());
    }

    public static void ukloniOngoing(Context context) {
        NotificationManagerCompat.from(context).cancel(105);
    }
}
