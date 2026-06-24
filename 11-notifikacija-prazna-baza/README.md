# Notifikacija kad nema postova (zadatak 7, deo 2)

**Cilj:** Kad je baza postova **prazna**, pošalji notifikaciju sa tekstom **"Nema više postova!"**

Radi **samostalno** – možeš testirati bez brisanja (npr. poziv posle ručnog brisanja svih redova).

---

## Šta ti treba pre ovoga

- Dozvola `POST_NOTIFICATIONS` (Android 13+)
- Poziv iz `10-brisanje-prvog-posta/` kada je `postDao.count() == 0`

---

## Koji fajlovi se menjaju / dodaju

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`NotifikacijaHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | U `onCreate`: `NotifikacijaHelper.kreirajKanal(this)` |
| 3 | `MainActivity.java` | Kad je baza prazna: `NotifikacijaHelper.posaljiPraznaBaza(this)` |

---

## Kompletan kod – helper klasa

Kopiraj **`NotifikacijaHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.NotifikacijaHelper;
```

### U `onCreate`

```java
NotifikacijaHelper.kreirajKanal(this);
NotifikacijaHelper.proveriDozvolu(this);
```

### Kad je baza prazna (preko PostRepository callback-a)

U `10-brisanje-prvog-posta/` već koristiš:

```java
button.setOnClickListener(v -> postRepository.obrisiPrviPost(
        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
));
```

> **Ne piši** proveru `postDao.count() == 0` u MainActivity – `PostRepository.obrisiPrviPost()` poziva callback kad je baza prazna.

### Samostalni test (bez brisanja)

```java
// Privremeno u onCreate – obriši sve postove pa pozovi notifikaciju
postDao.deleteFirst(); // ponovi dok count != 0, ili obriši sve
if (postDao.count() == 0) {
    NotifikacijaHelper.posaljiPraznaBaza(this);
}
```

---

## Alternativa: inline u `MainActivity.java`

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

Vidi primer iznad u sekciji „Kad je baza prazna“.

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
