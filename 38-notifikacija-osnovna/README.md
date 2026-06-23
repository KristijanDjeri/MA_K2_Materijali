# Notifikacija – osnovna

**Jednostavna notifikacija:** ikonica + naslov + kratak tekst.  
Isti princip kao u [07-brisanje-notifikacije](../07-brisanje-notifikacije/) (zadatak 7).

---

## Preduslovi

- Dozvola `POST_NOTIFICATIONS` u Manifest-u (API 33+ runtime)
- Folder [37-notifikacije-pregled](../37-notifikacije-pregled/) – pregled svih tipova

---

## Korak 1: Manifest

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

---

## Korak 2: Kompletan kod u `MainActivity.java`

### Importi

```java
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
```

### Konstante

```java
private static final String CHANNEL_ID = "osnovni_channel";
private static final int NOTIF_ID = 100;
private static final int REQ_NOTIF = 110;
```

### U `onCreate`

```java
kreirajOsnovniKanal();
proveriDozvoluZaNotifikacije();

// Test – npr. dugi klik na button:
// button.setOnLongClickListener(v -> { posaljiOsnovnuNotifikaciju(); return true; });
```

### NotificationChannel (jednom)

```java
private void kreirajOsnovniKanal() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Osnovne notifikacije",
                NotificationManager.IMPORTANCE_DEFAULT  // vidljivo, bez forsiranog heads-up
        );
        channel.setDescription("Kanal za obične poruke");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}
```

> **Alternativa:** `IMPORTANCE_HIGH` – notifikacija iskače preko ekrana (heads-up).

### Runtime dozvola (API 33+)

```java
private void proveriDozvoluZaNotifikacije() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
        }
    }
}
```

### Slanje osnovne notifikacije

```java
private void posaljiOsnovnuNotifikaciju() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)  // obavezna mala ikonica
            .setContentTitle("Naslov obaveštenja")
            .setContentText("Kratak tekst poruke")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);  // nestaje kad korisnik tapne

    NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
}
```

---

## Objašnjenje polja

| Metoda | Značenje |
|--------|----------|
| `setSmallIcon` | **Obavezno** – bela silueta u status baru |
| `setContentTitle` | Naslov u panelu |
| `setContentText` | Glavni tekst |
| `setAutoCancel(true)` | Nestaje posle klika |
| `notify(id, ...)` | `id` razlikuje notifikacije (isti id = zamena stare) |

---

## Klik na notifikaciju otvara app (opciono)

```java
import android.app.PendingIntent;
import android.content.Intent;

Intent intent = new Intent(this, MainActivity.class);
intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
PendingIntent pi = PendingIntent.getActivity(
        this, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
);
builder.setContentIntent(pi);
```

> Detaljnije akcije i više dugmadi: [39-notifikacija-akcije](../39-notifikacija-akcije/)

---

## Checklist

- [ ] Kanal kreiran u `onCreate`
- [ ] `setSmallIcon` postavljen
- [ ] API 33+: runtime dozvola
- [ ] `notify()` pozvan

---

## Sledeći korak

- Dugmad na notifikaciji → [39-notifikacija-akcije](../39-notifikacija-akcije/)
- Duži tekst / slika → [40-notifikacija-prosirena](../40-notifikacija-prosirena/)
