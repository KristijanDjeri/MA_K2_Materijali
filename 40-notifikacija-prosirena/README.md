# Notifikacija – proširena (BigText, slika, lista)

**Cilj:** Notifikacija koja prikaže **više sadržaja** – duži tekst, sliku ili listu stavki.

---

## Preduslovi

- [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) – `NotifikacijaHelper`
- **`NotifikacijaProsirenaHelper.java`** iz ovog foldera

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`NotifikacijaProsirenaHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `NotifikacijaHelper.kreirajKanal(this)` |
| 3 | `MainActivity.java` | Test pozivi ispod |

---

## MainActivity – samo povezivanje (preporučeno)

### Importi

```java
import com.example.kolokvijum2.helper.NotifikacijaHelper;
import com.example.kolokvijum2.helper.NotifikacijaProsirenaHelper;
```

### U `onCreate`

```java
NotifikacijaHelper.kreirajKanal(this);

button.setOnLongClickListener(v -> {
    NotifikacijaProsirenaHelper.posaljiBigText(this,
            "Novi post",
            "Ovo je duži tekst posta koji ne stane u jednu liniju…");
    return true;
});

// Inbox primer:
// NotifikacijaProsirenaHelper.posaljiInbox(this,
//         "1. Prvi post", "2. Drugi post", "3. Treći post");

// Ongoing (npr. sa 83-audio-recorder/):
// NotifikacijaProsirenaHelper.posaljiOngoing(this);
// NotifikacijaProsirenaHelper.ukloniOngoing(this);
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

// U onCreate() – primeri test poziva:
button.setOnLongClickListener(v -> {
    posaljiBigTextNotifikaciju("Naslov", "Ovo je duži tekst koji se vidi kad povučeš notifikaciju...");
    return true;
});

// METODE:

private void posaljiBigTextNotifikaciju(String naslov, String dugiTekst) {
    kreirajOsnovniKanal();
    NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
            .bigText(dugiTekst)
            .setBigContentTitle(naslov);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(naslov)
            .setContentText("Kratki pregled…")
            .setStyle(style)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(102, builder.build());
}

private void posaljiInboxNotifikaciju(String... linije) {
    kreirajOsnovniKanal();
    NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle()
            .setBigContentTitle("Poslednji postovi");
    for (String linija : linije) {
        inbox.addLine(linija);
    }
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Nova obaveštenja")
            .setContentText("Povuci za listu")
            .setStyle(inbox)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(104, builder.build());
}
```

## Checklist

- [ ] `NotifikacijaProsirenaHelper` u paketu `helper`
- [ ] Kanal kreiran pre slanja
- [ ] Različit `notify(id)` za svaki tip
- [ ] `ukloniOngoing` kad ongoing završi

---

## Povezano

- Osnovna: [38-notifikacija-osnovna](../38-notifikacija-osnovna/)
- Akcije: [39-notifikacija-akcije](../39-notifikacija-akcije/)
