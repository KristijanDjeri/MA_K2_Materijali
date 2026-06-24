# Brojač koraka (Step Counter)

**Dodatni segment.** **Slično:** ostali senzori, ali broj se **akumulira** od boot-a.

**Cilj:** Prikaži ukupan broj koraka od poslednjeg paljenja uređaja.

---

## Korak 1: Manifest (Android 10+)

```xml
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

Na API 28–29 obično **nije** potrebna ova dozvola.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`KoraciHelper.java`** | `app/.../helper/` |
| 2 | `MainActivity.java` | **`onCreate`**: `koraciHelper = new KoraciHelper(this, textView);` |
| 3 | `MainActivity.java` | **`onResume`**: `koraciHelper.onResume();` |
| 4 | `MainActivity.java` | **`onPause`**: `koraciHelper.onPause();` |
| 5 | `MainActivity.java` | **`onRequestPermissionsResult`**: `koraciHelper.onPermissionGranted(...)` |

---

## Kompletan kod – helper klasa

Kopiraj **`KoraciHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

```java
import com.example.kolokvijum2.helper.KoraciHelper;

private KoraciHelper koraciHelper;

// onCreate:
koraciHelper = new KoraciHelper(this, textView);

// onResume / onPause:
koraciHelper.onResume();
koraciHelper.onPause();

// onRequestPermissionsResult (proširi postojeći):
if (koraciHelper != null) {
    koraciHelper.onPermissionGranted(requestCode, grantResults);
}
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa: STEP_DETECTOR

```java
// TYPE_STEP_DETECTOR – jedan event = jedan korak
else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
    brojKoraka++;
    button.setText("Koraci: " + brojKoraka);
}
```

Potrebno polje `private int brojKoraka = 0;` koje sam uvećavaš.

---

## Checklist

- [ ] `TYPE_STEP_COUNTER` ili `TYPE_STEP_DETECTOR`
- [ ] `ACTIVITY_RECOGNITION` na API 29+
- [ ] Prikaz na Button ili TextView
