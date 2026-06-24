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

> **Ne piši** `posaljiNotifikacijuSaAkcijama()` u MainActivity – logika je u helperu.

---

## `NotifikacijaAkcijaReceiver.java`

Kopiraj iz ovog foldera. Registruj u Manifest-u:

```xml
<receiver android:name=".NotifikacijaAkcijaReceiver" android:exported="false" />
```

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Checklist

- [ ] `NotifikacijaAkcijeHelper` u paketu `helper`
- [ ] Receiver u Manifest-u
- [ ] `PendingIntent` sa `FLAG_IMMUTABLE`
- [ ] `addAction` za svako dugme

---

## Sledeći korak

[40-notifikacija-prosirena](../40-notifikacija-prosirena/) – BigText, BigPicture, Inbox stil.
