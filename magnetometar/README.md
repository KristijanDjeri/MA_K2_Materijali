# Magnetometar / kompas

**Slično:** Žiroskop i akcelerometar (zadatak 4 i 8) – isti `SensorEventListener` obrazac.

**Mogući zadatak:** Prikaži vrednosti magnetometra u TextView ili rotiraj strelicu (azimut).

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Senzor | `MainActivity.java` |

## Senzor

`Sensor.TYPE_MAGNETIC_FIELD` – vraća X, Y, Z (mikrotesla).

Za **azimut (kompas)** treba i akcelerometar + `SensorManager.getRotationMatrix()`.

## Fajlovi

- `MagnetometarSegment.java` – osnovni X,Y,Z
- `KompasSegment.java` – azimut u stepenima

## Checklist

- [ ] `getDefaultSensor(TYPE_MAGNETIC_FIELD)`
- [ ] Registracija u `onResume`
- [ ] `onSensorChanged` čita `event.values[0..2]`
