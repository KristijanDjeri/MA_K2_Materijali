# Notifikacija – osnovna

**Jednostavna notifikacija:** ikonica + naslov + kratak tekst.  
Isti princip kao u [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) (zadatak 7).

---

## Preduslovi

- Dozvola `POST_NOTIFICATIONS` u Manifest-u (API 33+ runtime)
- Folder [37-notifikacije-pregled](../37-notifikacije-pregled/) – pregled svih tipova
- **`NotifikacijaHelper.java`** iz `11-notifikacija-prazna-baza/` (proširen za osnovnu notifikaciju)

---

## Korak 1: Manifest

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`NotifikacijaHelper.java`** | Kopiraj iz `11-notifikacija-prazna-baza/` → `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `NotifikacijaHelper.kreirajKanal(this)` + `proveriDozvolu(this)` |
| 3 | `MainActivity.java` | Test poziv: `NotifikacijaHelper.posaljiOsnovnu(...)` |

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

// Test – npr. dugi klik na button:
button.setOnLongClickListener(v -> {
    NotifikacijaHelper.posaljiOsnovnu(this,
            "Naslov obaveštenja", "Kratak tekst poruke");
    return true;
});
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// IMPORTI:
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final String CHANNEL_ID = "posts_channel";
private static final int REQ_NOTIF = 110;

// U onCreate():
kreirajOsnovniKanal();
proveriDozvoluZaNotifikacije();

// Test – npr. dugi klik na button:
button.setOnLongClickListener(v -> {
    posaljiOsnovnuNotifikaciju("Naslov obaveštenja", "Kratak tekst poruke");
    return true;
});

// METODE:

private void kreirajOsnovniKanal() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Postovi", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }
}

private void proveriDozvoluZaNotifikacije() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
        }
    }
}

private void posaljiOsnovnuNotifikaciju(String naslov, String tekst) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(naslov)
            .setContentText(tekst)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(100, builder.build());
}
```

## Objašnjenje polja

| Metoda | Značenje |
|--------|----------|
| `kreirajKanal` | NotificationChannel (Android 8+) |
| `proveriDozvolu` | POST_NOTIFICATIONS na API 33+ |
| `posaljiOsnovnu` | Builder + `notify()` |

---

## Klik na notifikaciju otvara app (opciono)

Za `PendingIntent` vidi [39-notifikacija-akcije](../39-notifikacija-akcije/).

---

## Checklist

- [ ] `NotifikacijaHelper` u paketu `helper`
- [ ] Kanal kreiran u `onCreate`
- [ ] API 33+: runtime dozvola
- [ ] `posaljiOsnovnu(...)` radi

---

## Sledeći korak

- Dugmad na notifikaciji → [39-notifikacija-akcije](../39-notifikacija-akcije/)
- Duži tekst / slika → [40-notifikacija-prosirena](../40-notifikacija-prosirena/)
