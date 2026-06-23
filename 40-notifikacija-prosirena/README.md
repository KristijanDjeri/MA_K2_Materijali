# Notifikacija – proširena (BigText, slika, lista)

**Cilj:** Notifikacija koja prikaže **više sadržaja** – duži tekst, sliku ili listu stavki.  
Korisno kad poruka ne stane u jedan red.

---

## Preduslovi

- [38-notifikacija-osnovna](../38-notifikacija-osnovna/) – kanal i builder
- Za sliku: `Bitmap` ili `largeIcon` (npr. iz `ImageView` / resursa)

---

## 1. BigTextStyle – duži tekst

Korisnik povuče notifikaciju nadole da vidi ceo tekst.

```java
import androidx.core.app.NotificationCompat;

private void posaljiBigTextNotifikaciju() {
    String dugiTekst = "Ovo je duži tekst posta koji ne stane u jednu liniju. "
            + "BigTextStyle omogućava prikaz celog sadržaja kada korisnik "
            + "proširi notifikaciju prstom.";

    NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle()
            .bigText(dugiTekst)
            .setBigContentTitle("Naslov proširene notifikacije")
            .setSummaryText("Rezime");

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Novi post")
            .setContentText("Kratki pregled…")  // vidi se pre proširenja
            .setStyle(bigStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);

    NotificationManagerCompat.from(this).notify(102, builder.build());
}
```

---

## 2. BigPictureStyle – slika

```java
import android.graphics.BitmapFactory;

private void posaljiBigPictureNotifikaciju() {
    Bitmap slika = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
    // Alternativa: Bitmap iz ImageView / kamere

    NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle()
            .bigPicture(slika)
            .setBigContentTitle("Fotografija")
            .setSummaryText("Snimak sa kamere");

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_camera)
            .setContentTitle("Nova slika")
            .setContentText("Pogledaj fotografiju")
            .setLargeIcon(slika)  // mala slika desno pre proširenja
            .setStyle(picStyle)
            .setAutoCancel(true);

    NotificationManagerCompat.from(this).notify(103, builder.build());
}
```

> Ako nemaš `ic_launcher_foreground`, koristi `android.R.drawable.ic_menu_gallery` sa `BitmapFactory` ili preskoči `largeIcon`.

---

## 3. InboxStyle – lista linija (npr. naslovi postova)

```java
private void posaljiInboxNotifikaciju() {
    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
            .setBigContentTitle("Poslednji postovi")
            .addLine("1. Introduction to AI")
            .addLine("2. Web Development with React")
            .addLine("3. Data Science Fundamentals")
            .setSummaryText("+7 još");

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("3 nova posta")
            .setContentText("Povuci za listu")
            .setStyle(inboxStyle)
            .setAutoCancel(true);

    NotificationManagerCompat.from(this).notify(104, builder.build());
}
```

> **Alternativa:** Za prave postove iz baze – u petlji `addLine(post.getTitle())`.

---

## 4. Ongoing notifikacija (traje – npr. snimanje)

Ne može se swipe-om obrisati dok je aktivna:

```java
private void posaljiOngoingNotifikaciju() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("Snimanje u toku")
            .setContentText("Dodirni Stop u aplikaciji")
            .setOngoing(true)   // korisnik ne može da je skloni
            .setOnlyAlertOnce(true);

    NotificationManagerCompat.from(this).notify(105, builder.build());
}

private void ukloniOngoingNotifikaciju() {
    NotificationManagerCompat.from(this).cancel(105);
}
```

Poveži sa [36-audio-recorder](../36-audio-recorder/): `notify` pri startu snimanja, `cancel` pri stopu.

---

## 5. Progress bar u notifikaciji (preuzimanje)

```java
private void posaljiProgressNotifikaciju(int procenat) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle("Preuzimanje postova")
            .setContentText(procenat + "%")
            .setProgress(100, procenat, false)  // max, trenutno, indeterminate=false
            .setOngoing(true);

    NotificationManagerCompat.from(this).notify(106, builder.build());
}

// Kad završi:
// builder.setProgress(0, 0, false).setOngoing(false).setContentText("Gotovo");
```

---

## 6. Kombinacija sa akcijama

Možeš spojiti `BigTextStyle` i `addAction` – vidi [39-notifikacija-akcije](../39-notifikacija-akcije/).

```java
builder.setStyle(bigStyle)
       .addAction(android.R.drawable.ic_menu_delete, "Obriši", piObrisi);
```

---

## Checklist

- [ ] `setStyle(...)` za prošireni prikaz
- [ ] Različit `notify(id)` za svaki tip
- [ ] `setOngoing(true)` samo kad mora da traje
- [ ] `cancel(id)` kad ongoing završi

---

## Povezano

- Pregled: [37-notifikacije-pregled](../37-notifikacije-pregled/)
- Osnovna: [38-notifikacija-osnovna](../38-notifikacija-osnovna/)
- Akcije: [39-notifikacija-akcije](../39-notifikacija-akcije/)
