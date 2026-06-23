# Firebase Cloud Messaging (FCM) – push notifikacije

**Dodatni segment.** **Slično:** lokalne notifikacije (`11-notifikacija-prazna-baza/`), ali poruka stiže sa servera.

**Cilj:** Primi push notifikaciju; prikaži je pomoću `NotificationCompat`.

---

## Preduslovi

- `51-firebase-setup/` završen
- `implementation 'com.google.firebase:firebase-messaging'` u Gradle

---

## Korak 1: Servis za primanje poruka

Kreiraj **`app/src/main/java/.../MyFirebaseMessagingService.java`**:

```java
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
        // Token za slanje poruka ovom uređaju – pošalji na svoj server ili loguj
        android.util.Log.d("FCM_TOKEN", token);
    }
}
```

---

## Korak 2: `AndroidManifest.xml`

Unutar `<application>`:

```xml
<service
    android:name=".MyFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

---

## Korak 3: Token u `MainActivity` (opciono)

```java
import com.google.firebase.messaging.FirebaseMessaging;
import android.util.Log;

// u onCreate:
FirebaseMessaging.getInstance().getToken()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Log.d("FCM", "Token: " + token);
            }
        });
```

Token kopiraj iz **Logcat** za test slanje iz konzole.

---

## Korak 4: Android 13+ dozvola

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

Runtime dozvola – već imaš u `11-notifikacija-prazna-baza/`.

---

## Korak 5: Test iz Firebase Console

1. Firebase Console → **Messaging** (ili Engage → Messaging)
2. **Create campaign** → **Firebase Notification messages**
3. Unesi naslov i tekst
4. **Send test message** → nalepi FCM token iz Logcat-a
5. Pokreni app na uređaju → treba da stigne notifikacija

> **Napomena:** App može biti u pozadini ili foreground. Na nekim verzijama foreground prikazuje samo `onMessageReceived` – naš kod pravi lokalnu notifikaciju.

---

## Alternativa

- Samo **lokalne** notifikacije bez FCM → `11-notifikacija-prazna-baza/` (dovoljno za zadatak 7)
- **Data payload** umesto notification payload – fleksibilnije, više koda

---

## Checklist

- [ ] `MyFirebaseMessagingService` kreiran
- [ ] Servis registrovan u Manifest-u
- [ ] `firebase-messaging` u Gradle
- [ ] Token u Logcat-u
- [ ] Test poruka iz konzole stiže

---

## Napomena za kolokvijum

Zvanični zadatak 7 traži **lokalnu** notifikaciju – FCM je dodatna tema. Ako profesor kaže „Firebase notifikacije", koristi ovaj folder.
