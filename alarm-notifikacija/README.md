# AlarmManager + notifikacija

**Slično:** Notifikacije (zadatak 7), ali zakazane na vreme.

**Mogući zadatak:** Posle 10 sekundi pošalji notifikaciju „Podsetnik“.

## Gde u projektu

| Fajl | Putanja |
|------|---------|
| Receiver | `.../AlarmReceiver.java` |
| Zakazivanje | `MainActivity.java` |
| Manifest | registruj receiver |

## Koraci

1. Kreiraj `BroadcastReceiver` koji šalje notifikaciju
2. `AlarmManager.setExact()` ili `set()` sa `PendingIntent`
3. U manifestu: `<receiver android:name=".AlarmReceiver" />`

## Fajlovi

- `AlarmReceiver.java`
- `AlarmSegment.java`
- `AndroidManifest-receiver.xml`

## Checklist

- [ ] `AlarmManager` iz `ALARM_SERVICE`
- [ ] `PendingIntent.getBroadcast`
- [ ] Receiver u manifestu
- [ ] NotificationChannel (API 26+)

## Napomena

Na API 31+ za tačne alarme može trebati `SCHEDULE_EXACT_ALARM` – za kolokvijum obično dovoljno `set()` sa relativnim vremenom.
