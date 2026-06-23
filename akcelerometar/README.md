# Akcelerometar (zadatak 8)

**Cilj:** Tekst na `Button` prikazuje vrednosti akcelerometra u **realnom vremenu**.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Senzor + ažuriranje dugmeta | `MainActivity.java` |

## Kako napraviti

1. `Sensor.TYPE_ACCELEROMETER`
2. U `onSensorChanged` ažuriraj tekst dugmeta:  
   `button.setText("X: " + x + " Y: " + y + " Z: " + z)`
3. Možeš koristiti **isti** `SensorEventListener` kao za žiroskop – proveri `event.sensor.getType()`

## Fajlovi

- `AkcelerometarSegment.java`

## Checklist

- [ ] `SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)`
- [ ] Registracija u `onResume`
- [ ] `button.setText(...)` u `onSensorChanged`
- [ ] Ne zaboravi `onClick` za brisanje posta (zadatak 7) – oba na istom button-u

## Savet

Ako koristiš jedan listener za žiroskop i akcelerometar, registruj **oba** senzora u `onResume`.
