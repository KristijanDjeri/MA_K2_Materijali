# Lokacija u realnom vremenu

**Slično:** Geolokacija (zadatak 3), ali sa `requestLocationUpdates` umesto `getLastLocation`.

**Mogući zadatak:** TextView se ažurira dok se korisnik kreće.

## Fajlovi

- `LokacijaRealtimeSegment.java`

## Razlika

| getLastLocation | requestLocationUpdates |
|-----------------|------------------------|
| jednokratno | kontinuirano |
| može biti null | dobija nove koordinate |

## Checklist

- [ ] `LocationCallback`
- [ ] `requestLocationUpdates` u `onResume`
- [ ] `removeLocationUpdates` u `onPause`
