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

> **Ne piši** `kreirajOsnovniKanal()` / `posaljiOsnovnuNotifikaciju()` u MainActivity – sve je u `NotifikacijaHelper`.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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
