# Notifikacija kad nema postova (zadatak 7, deo 2)

**Cilj:** Kad je baza postova **prazna**, pošalji notifikaciju sa tekstom **"Nema više postova!"**

Radi **samostalno** – možeš testirati bez brisanja (npr. poziv posle ručnog brisanja svih redova).

---

## Šta ti treba pre ovoga

- Dozvola `POST_NOTIFICATIONS` (Android 13+)
- Poziv iz `10-brisanje-prvog-posta/` kada je `postDao.count() == 0`

---

## Koji fajlovi se menjaju

| Fajl | Šta radiš |
|------|-----------|
| `MainActivity.java` | NotificationChannel, `posaljiNotifikacijuPrazneBaze()` |

---

## Kompletan kod

### Importi

```java
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
```

### Konstante

```java
private static final String CHANNEL_ID = "posts_channel";
private static final int NOTIF_ID = 1;
```

### U `onCreate`

```java
kreirajNotificationChannel();
proveriNotifikacijeDozvolu(); // Android 13+
```

### NotificationChannel

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

### Slanje notifikacije

```java
private void posaljiNotifikacijuPrazneBaze() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Obaveštenje")
            .setContentText("Nema više postova!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
}
```

### Runtime dozvola – Android 13+

```java
import android.Manifest;
import android.content.pm.PackageManager;
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

---

## Samostalni test

```java
// Privremeno u onCreate – obriši sve postove pa pozovi notifikaciju
postDao.deleteFirst(); // ponovi dok count != 0, ili obriši sve
if (postDao.count() == 0) {
    posaljiNotifikacijuPrazneBaze();
}
```

---

## Više o notifikacijama

Pregled tipova: **`37-notifikacije-pregled/`** · akcije · proširena.

---

## Checklist

- [ ] NotificationChannel u `onCreate`
- [ ] Tekst tačno "Nema više postova!"
- [ ] API 33+ traži POST_NOTIFICATIONS

---

## Sledeći korak

**`12-senzor-akcelerometar/`** (zadatak 8)
