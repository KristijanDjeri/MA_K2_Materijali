# AlarmManager + notifikacija

**Dodatni segment.** **Slično:** notifikacije (zadatak 7), ali zakazane na vreme.

**Cilj:** Posle 10 sekundi pošalji notifikaciju „Prošlo je zakazano vreme!".

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `AlarmReceiver.java` | `.../AlarmReceiver.java` |
| Manifest | `<receiver>` unutar application |
| `MainActivity.java` | zakazivanje alarma |
| **`AlarmHelper.java`** | `app/.../helper/` (opciono, preporučeno) |

---

## Kompletan kod – helper klasa

Kopiraj **`AlarmHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.AlarmReceiver;
import com.example.kolokvijum2.helper.AlarmHelper;
```

### U `onCreate` ili na dugme

```java
AlarmHelper alarmHelper = new AlarmHelper(this, AlarmReceiver.class);
alarmHelper.zakaziZaSekundi(10);
```


---

## 1. `AlarmReceiver.java` (ceo fajl)

```java
package com.example.kolokvijum2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarm_channel";
    private static final int NOTIF_ID = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Alarmi", NotificationManager.IMPORTANCE_DEFAULT);
            context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Podsetnik")
                .setContentText("Prošlo je zakazano vreme!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIF_ID, builder.build());
    }
}
```

---

## 2. `AndroidManifest.xml` – unutar `<application>`

```xml
<receiver
    android:name=".AlarmReceiver"
    android:exported="false" />
```

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
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
```

## Alternativa

- `setExact()` – tačnije vreme, može tražiti dodatnu dozvolu na novijem Androidu
- `WorkManager` – modernija zamena za periodične poslove

---

## Checklist

- [ ] AlarmReceiver kreiran
- [ ] Receiver u Manifest-u
- [ ] PendingIntent sa FLAG_IMMUTABLE (API 31+)
- [ ] Notifikacija stiže posle 10s
