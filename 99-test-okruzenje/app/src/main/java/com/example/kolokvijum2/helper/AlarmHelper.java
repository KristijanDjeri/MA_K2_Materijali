package com.example.kolokvijum2.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 28-alarm-notifikacija/ – zakazuje AlarmReceiver */
public class AlarmHelper {

    private final AppCompatActivity activity;
    private final Class<?> receiverClass;

    public AlarmHelper(AppCompatActivity activity, Class<?> receiverClass) {
        this.activity = activity;
        this.receiverClass = receiverClass;
    }

    public void zakaziZaSekundi(int sekundi) {
        Intent intent = new Intent(activity, receiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                activity, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        long kada = SystemClock.elapsedRealtime() + sekundi * 1000L;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, kada, pendingIntent);

        Toast.makeText(activity, "Alarm zakazan za " + sekundi + "s", Toast.LENGTH_SHORT).show();
    }
}
