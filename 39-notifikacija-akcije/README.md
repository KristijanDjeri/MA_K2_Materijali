# Notifikacija – akcije (dugmad)

**Cilj:** Notifikacija sa **jednim ili više dugmadi** (npr. „Otvori“, „Obriši“, „Odustani“).  
Klik na dugme pokreće `PendingIntent` (Broadcast ili Activity).

---

## Preduslovi

- [38-notifikacija-osnovna](../38-notifikacija-osnovna/) – kanal i osnovni builder
- Razumevanje `PendingIntent` i `BroadcastReceiver` (za akcije u pozadini)

---

## Korak 1: Akcije u Manifest-u (opciono za Broadcast)

Ako akcija ide na `BroadcastReceiver`:

```xml
<receiver android:name=".NotifikacijaAkcijaReceiver" android:exported="false" />
```

---

## Korak 2: `NotifikacijaAkcijaReceiver.java` (ceo fajl)

```java
package com.example.kolokvijum2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotifikacijaAkcijaReceiver extends BroadcastReceiver {

    public static final String ACTION_OTVORI = "com.example.kolokvijum2.NOTIF_OTVORI";
    public static final String ACTION_OBRISI = "com.example.kolokvijum2.NOTIF_OBRISI";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        switch (intent.getAction()) {
            case ACTION_OTVORI:
                Toast.makeText(context, "Akcija: Otvori", Toast.LENGTH_SHORT).show();
                // ili startActivity...
                break;
            case ACTION_OBRISI:
                Toast.makeText(context, "Akcija: Obriši", Toast.LENGTH_SHORT).show();
                // npr. obrisiPrviPost() – pozovi servis ili sačuvaj u DB
                break;
        }
    }
}
```

> **Alternativa:** Akcija direktno otvara `MainActivity` preko `PendingIntent.getActivity()` – bez Receiver-a.

---

## Korak 3: Notifikacija sa dugmadima u `MainActivity.java`

### Importi

```java
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
```

### Konstante (pored osnovnih)

```java
private static final int NOTIF_AKCIJE_ID = 101;
```

### Metoda

```java
private void posaljiNotifikacijuSaAkcijama() {
    // PendingIntent za akciju „Otvori"
    Intent otvoriIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    otvoriIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OTVORI);
    PendingIntent piOtvori = PendingIntent.getBroadcast(
            this, 1, otvoriIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
    );

    // PendingIntent za akciju „Obriši"
    Intent obrisiIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    obrisiIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OBRISI);
    PendingIntent piObrisi = PendingIntent.getBroadcast(
            this, 2, obrisiIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
    );

    // Klik na celu notifikaciju → otvori MainActivity
    Intent contentIntent = new Intent(this, MainActivity.class);
    PendingIntent piContent = PendingIntent.getActivity(
            this, 0, contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
    );

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Postovi")
            .setContentText("Imate nepročitanih postova")
            .setContentIntent(piContent)
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_menu_view, "Otvori", piOtvori)
            .addAction(android.R.drawable.ic_menu_delete, "Obriši", piObrisi)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat.from(this).notify(NOTIF_AKCIJE_ID, builder.build());
}
```

---

## Objašnjenje `addAction`

```java
.addAction(ikona, "Tekst dugmeta", pendingIntent)
```

- **ikona** – mala ikonica pored teksta (može sistemska `android.R.drawable...`)
- **pendingIntent** – šta se desi kad korisnik tapne dugme
- Svaka akcija treba **jedinstveni** request code u `PendingIntent` (ovde 1, 2, 0)

---

## Akcija koja otvara drugu aktivnost

```java
Intent i = new Intent(this, DetailActivity.class);
i.putExtra("naslov", "Iz notifikacije");
PendingIntent pi = PendingIntent.getActivity(this, 3, i,
        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
builder.addAction(0, "Detalj", pi);  // 0 = bez ikone
```

---

## Uklanjanje notifikacije iz akcije

U `onReceive`:

```java
import androidx.core.app.NotificationManagerCompat;

NotificationManagerCompat.from(context).cancel(NOTIF_AKCIJE_ID);
```

---

## Checklist

- [ ] `NotificationChannel` postoji (isti `CHANNEL_ID`)
- [ ] `PendingIntent` sa `FLAG_IMMUTABLE` (API 31+)
- [ ] Receiver u Manifest-u (ako koristiš Broadcast)
- [ ] Različiti request code za svaki PendingIntent
- [ ] `addAction` za svako dugme

---

## Sledeći korak

[40-notifikacija-prosirena](../40-notifikacija-prosirena/) – BigText, BigPicture, Inbox stil.
