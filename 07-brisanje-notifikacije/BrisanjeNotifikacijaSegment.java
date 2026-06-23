// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Button;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

// POLJA:
private static final String CHANNEL_ID = "posts_channel";
private static final int NOTIF_ID = 1;
private Button button;

// U onCreate(), posle findViewById:
kreirajNotificationChannel();
button.setOnClickListener(v -> obrisiPrviPost());

// METODE:

private void kreirajNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Postovi",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}

private void obrisiPrviPost() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        postDao.delete(prvi);
    }

    if (postDao.count() == 0) {
        posaljiNotifikaciju("Nema više postova!");
    }
}

private void posaljiNotifikaciju(String tekst) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Obaveštenje")
            .setContentText(tekst)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
}

// Napomena: na API 33+ dodaj runtime dozvolu POST_NOTIFICATIONS pre notify()
