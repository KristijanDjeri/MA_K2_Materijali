# Brisanje posta i notifikacija (zadatak 7)

**Cilj:** Klik na `Button` → obriši post na **prvoj poziciji** u bazi.  
Ako nema više postova → notifikacija **"Nema više postova!"**

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Klik listener | `MainActivity.java` |
| Notification channel | `MainActivity.java` (API 26+) |
| Dozvola notifikacija | `POST_NOTIFICATIONS` (API 33+) |

## Kako napraviti

1. `button.setOnClickListener` – **pazi:** dugme takođe prikazuje akcelerometar (zadatak 8), oba mogu u istom listeneru ili odvojeno
2. `postDao.deleteFirst()` ili: `Post p = postDao.getFirst(); if (p != null) postDao.delete(p);`
3. Proveri `postDao.count() == 0` → pošalji notifikaciju

### NotificationChannel (Android 8+)

Obavezno kreiraj kanal pre slanja notifikacije.

### Runtime dozvola (Android 13+)

`POST_NOTIFICATIONS` – traži pre slanja ako API >= 33.

## Fajlovi

- `BrisanjeNotifikacijaSegment.java`

## Checklist

- [ ] Brisanje prvog reda (ne po id=1)
- [ ] Provera prazne tabele
- [ ] `NotificationCompat.Builder`
- [ ] `NotificationManager.notify()`
- [ ] Kanal za API 26+
