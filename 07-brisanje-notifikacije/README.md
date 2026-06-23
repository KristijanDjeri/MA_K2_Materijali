# Brisanje posta i notifikacija (zadatak 7)

**Cilj:**
- Klik na `Button` → obriši post na **prvoj poziciji** u bazi
- Ako nema više postova → notifikacija sa tekstom **"Nema više postova!"**

---

## Šta ti treba pre ovoga

- `05-retrofit-room/` – `postDao`
- `button` u layoutu
- Dozvola `POST_NOTIFICATIONS` (Android 13+)

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | NotificationChannel, brisanje, notifikacija |

---

## Kompletan kod za `MainActivity.java`

### 1. Importi

```java
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
```

### 2. Konstante i polja

```java
private static final String CHANNEL_ID = "posts_channel";
private static final int NOTIF_ID = 1;
// button i postDao već postoje
```

### 3. U `onCreate`, posle `findViewById`

```java
kreirajNotificationChannel();
button.setOnClickListener(v -> obrisiPrviPost());
```

> **Napomena:** Ako na istom dugmetu imaš i 08-senzor-akcelerometar tekst, **jedan** `setOnClickListener` – ne pozivaj ga dva puta. Akcelerometar menja tekst iz senzora, klik i dalje briše.

### 4. NotificationChannel (obavezno Android 8+)

```java
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
```

### 5. Brisanje prvog posta

```java
private void obrisiPrviPost() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        postDao.delete(prvi);
    }

    if (postDao.count() == 0) {
        posaljiNotifikaciju("Nema više postova!");
    }
}
```

### 6. Slanje notifikacije

```java
private void posaljiNotifikaciju(String tekst) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Obaveštenje")
            .setContentText(tekst)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
}
```

### 7. (Opciono) Runtime dozvola za notifikacije – Android 13+

```java
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

private static final int REQ_NOTIFICATIONS = 105;

private void proveriNotifikacijeDozvolu() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQ_NOTIFICATIONS);
        }
    }
}
```

Pozovi `proveriNotifikacijeDozvolu()` u `onCreate`. U `onRequestPermissionsResult` dodaj `case REQ_NOTIFICATIONS`.

> **Napomena:** Na Android 9–12 ova dozvola nije potrebna. Ipak je dobro da kod postoji.

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `getFirst()` + `delete(prvi)` | `postDao.deleteFirst()` – jedna SQL komanda |
| `NotificationCompat` | Stari `Notification.Builder` – ne koristi |
| Ikonica `ic_dialog_info` | Sopstvena ikonica u `res/drawable` |

---

## Checklist

- [ ] NotificationChannel kreiran u `onCreate`
- [ ] Klik briše prvi red (ne post sa id=1)
- [ ] Kad je tabela prazna → notifikacija "Nema više postova!"
- [ ] Na API 33+ tražiš POST_NOTIFICATIONS

---

## Sledeći korak

Za druge tipove notifikacija pogledaj **[37-notifikacije-pregled](../37-notifikacije-pregled/)** (osnovna, akcije, proširena).

Zadatak 8: **[08-senzor-akcelerometar](../08-senzor-akcelerometar/)** (tekst na istom dugmetu).
