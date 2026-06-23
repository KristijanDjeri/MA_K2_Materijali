# BroadcastReceiver – primalac emitovanog Intent-a

**Dodatni segment.** **Slično:** `80-alarm-notifikacija/` (`AlarmReceiver`), ali ovde **ti** šalješ broadcast iz aplikacije.

> **Napomena:** Ispravan Android termin je **BroadcastReceiver**, ne „ContentReceiver“. ContentProvider (`84-content-provider/`) služi za podatke; BroadcastReceiver služi za događaje (Intent).

**Cilj:** Posle učitavanja postova pošalji broadcast; `PostUpdateReceiver` prikaže Toast.

---

## Preduslovi

- `05-room-baza/` + učitavanje postova
- Razumevanje Intent-a (`70-intent-druga-aktivnost/`)

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `PostUpdateReceiver.java` | `.../PostUpdateReceiver.java` |
| Manifest | `<receiver>` unutar application |
| `MainActivity.java` | `sendBroadcast(...)` |

---

## 1. `PostUpdateReceiver.java` (ceo fajl)

```java
package com.example.kolokvijum2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PostUpdateReceiver extends BroadcastReceiver {

    public static final String ACTION_POSTS_UPDATED =
            "com.example.kolokvijum2.POSTS_UPDATED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_POSTS_UPDATED.equals(intent.getAction())) {
            Toast.makeText(context, "Lista postova osvežena", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

## 2. `AndroidManifest.xml` – unutar `<application>`

```xml
<receiver
    android:name=".PostUpdateReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="com.example.kolokvijum2.POSTS_UPDATED" />
    </intent-filter>
</receiver>
```

---

## 3. Slanje broadcast-a iz `MainActivity.java`

### Importi

```java
import android.content.Intent;
import com.example.kolokvijum2.PostUpdateReceiver;
```

### Metoda

```java
private void obavestiDaSuPostoviUcitani() {
    Intent intent = new Intent(PostUpdateReceiver.ACTION_POSTS_UPDATED);
    intent.setPackage(getPackageName()); // obavezno od API 26 za manifest receiver
    sendBroadcast(intent);
}
```

Pozovi na kraju metode koja upisuje postove u bazu:

```java
postDao.insertAll(postovi);
obavestiDaSuPostoviUcitani();
```

> **API 26+:** `intent.setPackage(getPackageName())` – bez toga manifest receiver ne prima custom broadcast.

---

## Razlika: Alarm vs custom broadcast

| | `80-alarm-notifikacija/` | Ovde |
|--|--------------------------|------|
| Ko šalje | `AlarmManager` + `PendingIntent` | `sendBroadcast()` iz Activity |
| Šta radi receiver | Notifikacija posle 10 s | Toast odmah |
| Registracija | `<receiver>` u Manifest-u | Isto |

---

## Dinamička registracija (alternativa)

Bez Manifest-a, samo dok je Activity aktivna:

```java
// polje:
private PostUpdateReceiver postUpdateReceiver;

// u onCreate:
postUpdateReceiver = new PostUpdateReceiver();
IntentFilter filter = new IntentFilter(PostUpdateReceiver.ACTION_POSTS_UPDATED);
registerReceiver(postUpdateReceiver, filter);

// u onDestroy:
unregisterReceiver(postUpdateReceiver);
```

Na kolokvijumu je dovoljna **statička** registracija u Manifest-u.

---

## Checklist

- [ ] `BroadcastReceiver` klasa sa `onReceive`
- [ ] `<receiver>` u Manifest-u
- [ ] `sendBroadcast` sa istom akcijom kao u `intent-filter`
- [ ] Receiver radi i kad Activity nije na ekranu (Manifest registracija)

---

## Povezano

- Zakazani alarm: `80-alarm-notifikacija/`
- Notifikacija sa dugmadima: `39-notifikacija-akcije/`
- ContentProvider za podatke: `84-content-provider/`
