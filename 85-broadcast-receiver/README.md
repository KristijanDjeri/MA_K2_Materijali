# BroadcastReceiver – primalac emitovanog Intent-a

**Dodatni segment.** **Slično:** `80-alarm-notifikacija/` (`AlarmReceiver`), ali ovde **ti** šalješ broadcast iz aplikacije.

**Cilj:** Posle učitavanja postova pošalji broadcast; `PostUpdateReceiver` prikaže Toast.

---

## Preduslovi

- `05-room-baza/` + `07-ucitaj-10-postova/` – `PostRepository`
- **`BroadcastHelper.java`** iz ovog foldera

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `PostUpdateReceiver.java` | `.../PostUpdateReceiver.java` |
| **`BroadcastHelper.java`** | `.../helper/BroadcastHelper.java` |
| Manifest | `<receiver>` unutar application |

---

## 1. `PostUpdateReceiver.java`

Kopiraj iz ovog foldera.

---

## 2. Manifest

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

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.BroadcastHelper;
import com.example.kolokvijum2.helper.PostRepository;
```

### Posle uspešnog učitavanja postova

```java
postRepository.ucitajPostoveSaApi(new PostRepository.OnApiDoneListener() {
    @Override
    public void onSuccess(int count) {
        BroadcastHelper.posaljiPostsUpdated(MainActivity.this);
        Toast.makeText(MainActivity.this, "Učitano " + count, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) { }
});
```

> **Ne piši** `obavestiDaSuPostoviUcitani()` u MainActivity – koristi `BroadcastHelper.posaljiPostsUpdated()`.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `BroadcastHelper` + `PostUpdateReceiver` u projektu
- [ ] Receiver u Manifest-u
- [ ] `sendBroadcast` posle upisa u bazu
