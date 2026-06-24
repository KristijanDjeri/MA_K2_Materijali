# Notifikacija – akcije (dugmad)

**Cilj:** Notifikacija sa **jednim ili više dugmadi** (npr. „Otvori“, „Obriši“, „Odustani“).

---

## Preduslovi

- [38-notifikacija-osnovna](../38-notifikacija-osnovna/) – `NotifikacijaHelper.kreirajKanal`
- **`NotifikacijaAkcijaReceiver.java`** i **`NotifikacijaAkcijeHelper.java`** iz ovog foldera

---

## Fajlovi

| Korak | Fajl | Putanja |
|-------|------|---------|
| 1 | `NotifikacijaAkcijaReceiver.java` | `com.example.kolokvijum2` |
| 2 | **`NotifikacijaAkcijeHelper.java`** | `app/.../helper/` |
| 3 | Manifest | `<receiver>` – vidi `AndroidManifest-receiver.xml` |

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.NotifikacijaAkcijeHelper;
import com.example.kolokvijum2.helper.NotifikacijaHelper;
```

### U `onCreate`

```java
NotifikacijaHelper.kreirajKanal(this);

button.setOnLongClickListener(v -> {
    NotifikacijaAkcijeHelper.posaljiSaAkcijama(this);
    return true;
});
```


---

## `NotifikacijaAkcijaReceiver.java`

Kopiraj iz ovog foldera. Registruj u Manifest-u:

```xml
<receiver android:name=".NotifikacijaAkcijaReceiver" android:exported="false" />
```

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.kolokvijum2.NotifikacijaAkcijaReceiver;

// POLJE:
private static final int NOTIF_AKCIJE_ID = 101;

// U onCreate() – test poziv:
button.setOnLongClickListener(v -> {
    posaljiNotifikacijuSaAkcijama();
    return true;
});

// METODA:

private void posaljiNotifikacijuSaAkcijama() {
    kreirajOsnovniKanal(); // iz 38-notifikacija-osnovna/

    Intent otvoriIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    otvoriIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OTVORI);
    PendingIntent piOtvori = PendingIntent.getBroadcast(
            this, 1, otvoriIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent obrisiIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    obrisiIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OBRISI);
    PendingIntent piObrisi = PendingIntent.getBroadcast(
            this, 2, obrisiIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent contentIntent = new Intent(this, MainActivity.class);
    PendingIntent piContent = PendingIntent.getActivity(
            this, 0, contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Postovi")
            .setContentText("Imate nepročitanih postova")
            .setContentIntent(piContent)
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_menu_view, "Otvori", piOtvori)
            .addAction(android.R.drawable.ic_menu_delete, "Obriši", piObrisi);

    NotificationManagerCompat.from(this).notify(NOTIF_AKCIJE_ID, builder.build());
}
```

## Checklist

- [ ] `NotifikacijaAkcijeHelper` u paketu `helper`
- [ ] Receiver u Manifest-u
- [ ] `PendingIntent` sa `FLAG_IMMUTABLE`
- [ ] `addAction` za svako dugme

---

## Sledeći korak

[40-notifikacija-prosirena](../40-notifikacija-prosirena/) – BigText, BigPicture, Inbox stil.
