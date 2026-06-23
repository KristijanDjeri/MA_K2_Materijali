package com.example.kolokvijum2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "fcm_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String naslov = "FCM poruka";
        String tekst = "Nema sadržaja";

        if (remoteMessage.getNotification() != null) {
            naslov = remoteMessage.getNotification().getTitle();
            tekst = remoteMessage.getNotification().getBody();
        } else if (remoteMessage.getData().size() > 0) {
            tekst = remoteMessage.getData().get("poruka");
        }

        prikaziNotifikaciju(naslov, tekst);
    }

    private void prikaziNotifikaciju(String naslov, String tekst) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "FCM", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(naslov)
                .setContentText(tekst)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    public void onNewToken(String token) {
        android.util.Log.d("FCM_TOKEN", token);
    }
}
