# Žiroskop (zadatak 4 – deo 2)

**Cilj:** Svaki put kada se slika u `ImageView` zameni, prikaži **Toast** sa očitanjem žiroskopa po **X, Y, Z** osi.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Senzor + Toast | `MainActivity.java` |

## Kako napraviti

1. `SensorManager` + `Sensor.TYPE_GYROSCOPE`
2. Registruj listener u `onResume`, odjavi u `onPause`
3. Čuvaj poslednje vrednosti u poljima `gyroX`, `gyroY`, `gyroZ`
4. Metoda `prikaziZiroskopToast()` – poziva se iz kamere callback-a posle `setImageBitmap`

## Fajlovi

- `ZiroskopSegment.java`

## Napomena

Emulator možda nema žiroskop – na fizičkom uređaju radi pouzdano.  
Toast format: `"Žiroskop X: ..., Y: ..., Z: ..."`

## Checklist

- [ ] `SensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)`
- [ ] `registerListener` u `onResume`
- [ ] `unregisterListener` u `onPause`
- [ ] Toast pozvan posle zamene slike
