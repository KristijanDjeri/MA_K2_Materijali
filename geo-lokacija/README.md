# Geolokacija (zadatak 3)

**Cilj:** U `TextView` prikazati geografsku širinu i dužinu uređaja.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Kod u aktivnosti | `MainActivity.java` |
| Dozvole | `AndroidManifest.xml` (već u `osnovni-projekat/`) |

## Kako napraviti

### 1. Dozvole u manifestu

Već dodato: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`.

### 2. Runtime dozvola (Android 6+)

Pre čitanja lokacije pozovi `requestPermissions` – primer u `GeoLokacijaSegment.java`.

### 3. Fused Location Provider

1. Zavisnost: `play-services-location` (u `gradle-zavisnosti.txt`)
2. U `onCreate` pozovi `pokreniLokaciju()` iz segmenta
3. Rezultat ide u `textView.setText("Širina: " + lat + ", Dužina: " + lon)`

## Fajlovi

- `GeoLokacijaSegment.java` – kopiraj metode u `MainActivity`

## Na kolokvijumu – brzi checklist

- [ ] Dozvole u manifestu
- [ ] `requestPermissions` za lokaciju
- [ ] `FusedLocationProviderClient` + `getLastLocation()` ili `requestLocationUpdates`
- [ ] Format: `"Širina: X, Dužina: Y"` u TextView

## Napomena

Na emulatoru: **Extended Controls → Location** da postaviš koordinate.  
Na uređaju mora biti uključen GPS.
