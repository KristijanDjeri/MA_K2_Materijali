// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;

// METODA – notifikacija za 10 sekundi:
private void zakaziAlarm() {
    Intent intent = new Intent(this, AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
    );

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    long kada = SystemClock.elapsedRealtime() + 10_000; // 10 sekundi

    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, kada, pendingIntent);
    Toast.makeText(this, "Alarm zakazan za 10s", Toast.LENGTH_SHORT).show();
}
