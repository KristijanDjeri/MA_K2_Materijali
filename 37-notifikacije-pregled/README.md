# Notifikacije – pregled svih tipova

Mapa notifikacija u materijalu. **Zadatak 7** koristi [10-brisanje-prvog-posta](../10-brisanje-prvog-posta/) + [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/).

---

## Koji folder za koji tip

| Tip | Folder | Kada koristiti |
|-----|--------|----------------|
| **Osnovna** (naslov + tekst) | [38-notifikacija-osnovna](../38-notifikacija-osnovna/) | Jednostavan Toast u status baru |
| **Zadatak 7** (prazna baza) | [10-brisanje-prvog-posta](../10-brisanje-prvog-posta/) + [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) | Kolokvijum |
| **Sa akcijama** (dugmad) | [39-notifikacija-akcije](../39-notifikacija-akcije/) | „Obriši“, „Otvori“, „Odustani“ |
| **Proširena** (big text, slika, lista) | [40-notifikacija-prosirena](../40-notifikacija-prosirena/) | Duži tekst, BigPicture |
| **Zakazana** (AlarmManager) | [80-alarm-notifikacija](../80-alarm-notifikacija/) | Posle X sekundi |
| **Push sa servera** | [54-firebase-fcm](../54-firebase-fcm/) | Firebase FCM |

---

## Obavezno za sve (Android 8+)

1. **NotificationChannel** – `NotifikacijaHelper.kreirajKanal(this)` u `onCreate`
2. **NotificationCompat.Builder** – gradi notifikaciju (u helperima)
3. **NotificationManagerCompat.notify()** – prikazuje je

### Android 13+ (API 33)

Runtime dozvola – **`NotifikacijaHelper.proveriDozvolu(this)`** u `onCreate`.

---

## Uporedna tabela

| Feature | Osnovna | Akcije | Proširena |
|---------|---------|--------|-----------|
| Naslov + kratak tekst | ✅ | ✅ | ✅ |
| Dugmad | ❌ | ✅ | ✅ |
| Duži tekst (BigText) | ❌ | ❌ | ✅ |
| Slika (BigPicture) | ❌ | ❌ | ✅ |
| Klik otvara app | opciono | ✅ | ✅ |
| PendingIntent | opciono | ✅ obavezno za akcije | ✅ |

---

## Redosled učenja

1. [38-notifikacija-osnovna](../38-notifikacija-osnovna/) – razumevanje kanala i buildera
2. [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) – zadatak 7
3. [39-notifikacija-akcije](../39-notifikacija-akcije/) – dugmad
4. [40-notifikacija-prosirena](../40-notifikacija-prosirena/) – stilovi

---

## Česte greške

| Problem | Rešenje |
|---------|---------|
| Notifikacija se ne vidi | Kanal kreiran? POST_NOTIFICATIONS na API 33+? |
| Akcija ne radi | `PendingIntent` sa `FLAG_IMMUTABLE` |
| Klik na notifikaciju ništa ne radi | Dodaj `setContentIntent(pendingIntent)` |
