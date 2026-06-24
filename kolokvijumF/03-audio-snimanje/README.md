# Audio snimanje (zadatak 4) – vežba F

**Cilj:**

1. Klik na **Snimi** → počne snimanje zvuka, dugme postane **onemogućeno** (`enabled = false`)
2. Klik na **prvi CheckBox** (`checkBoxSnimanje`) → zaustavi snimanje, sačuvaj fajl u **`files`** direktorijum aplikacije, **ponovo omogući** dugme

> Razlika od `83-audio-recorder/`: tu dugme i startuje i zaustavlja. Ovde je **CheckBox** za stop.

---

## Gde se čuva fajl

Zadatak traži **`files` direktorijum** → koristi `getFilesDir()`:

```java
new File(activity.getFilesDir(), "snimak.m4a")
```

Putanja na uređaju (primer):

```
/data/data/com.example.kolokvijum2/files/snimak.m4a
```

> **Alternativa:** `getExternalFilesDir(null)` – spoljašnji „files“ pod `Android/data/...`. U ovoj vežbi koristi **`getFilesDir()`** jer zadatak traži „files direktorijum“ aplikacije.

---

## Tok rada

```
[Snimi] ──► provera RECORD_AUDIO
         ──► MediaRecorder.start()
         ──► buttonSnimi.setEnabled(false)

[checkBoxSnimanje = checked] ──► stop + release
                              ──► fajl sačuvan
                              ──► buttonSnimi.setEnabled(true)
                              ──► checkbox unchecked (opciono)
```

---

## Koji fajlovi se dodaju

| Korak | Fajl | Gde |
|-------|------|-----|
| 1 | `AudioSnimanjeHelper.java` | `app/.../helper/` |
| 2 | `MainActivity.java` | init + lifecycle |

---

## MainActivity – povezivanje

```java
import com.example.kolokvijum2.helper.AudioSnimanjeHelper;

private AudioSnimanjeHelper audioHelper;

// onCreate:
audioHelper = new AudioSnimanjeHelper(this, buttonSnimi, checkBoxSnimanje);

// onPause:
audioHelper.onPause();

// onDestroy:
audioHelper.onDestroy();

// onRequestPermissionsResult:
audioHelper.onPermissionGranted(requestCode, grantResults);
```

Helper **sam** postavlja listenere na dugme i CheckBox – ne dodaj ih ponovo u Activity.

---

## Format fajla

| Postavka | Vrednost |
|----------|----------|
| `OutputFormat` | `MPEG_4` |
| `AudioEncoder` | `AAC` |
| Ekstenzija | `.m4a` |

---

## Kompletan kod

Kopiraj **`AudioSnimanjeHelper.java`** iz ovog foldera.

---

## Alternativa: inline u MainActivity

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;

private static final int REQ_AUDIO = 106;
private MediaRecorder mediaRecorder;
private boolean snima = false;
private String audioPutanja;

// onCreate:
audioPutanja = new File(getFilesDir(), "snimak.m4a").getAbsolutePath();
buttonSnimi.setOnClickListener(v -> pokreniSnimanje());
checkBoxSnimanje.setOnCheckedChangeListener((v, checked) -> {
    if (checked && snima) {
        zaustaviSnimanje();
        checkBoxSnimanje.setChecked(false);
    }
});

private void pokreniSnimanje() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
        return;
    }
    zapocniRecorder();
}

private void zapocniRecorder() {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = new MediaRecorder(this);
        } else {
            mediaRecorder = new MediaRecorder();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(audioPutanja);
        mediaRecorder.prepare();
        mediaRecorder.start();
        snima = true;
        buttonSnimi.setEnabled(false);
        Toast.makeText(this, "Snimanje...", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
        oslobodiRecorder();
        Toast.makeText(this, "Greška snimanja", Toast.LENGTH_SHORT).show();
    }
}

private void zaustaviSnimanje() {
    if (mediaRecorder == null) return;
    try {
        mediaRecorder.stop();
    } catch (RuntimeException ignored) {
    } finally {
        oslobodiRecorder();
    }
    snima = false;
    buttonSnimi.setEnabled(true);
    Toast.makeText(this, "Snimljeno u files/", Toast.LENGTH_SHORT).show();
}

private void oslobodiRecorder() {
    if (mediaRecorder != null) {
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
```

---

## Testiranje

1. **Snimi** → dozvoli mikrofon → dugme sivo
2. Govori 2–3 sekunde
3. Čekiraj **Zaustavi snimanje** → dugme opet aktivno
4. U **Device File Explorer**: `data/data/com.example.kolokvijum2/files/snimak.m4a`

**Emulator:** `...` → **Microphone** → uključi virtualni mikrofon.

---

## Checklist

- [ ] `RECORD_AUDIO` u Manifest-u + runtime
- [ ] Snimi onemogućava dugme tokom snimanja
- [ ] CheckBox zaustavlja i čuva u `getFilesDir()`
- [ ] Posle stopa dugme ponovo enabled
- [ ] `onPause` / `onDestroy` oslobađaju `MediaRecorder`

---

## Sledeći korak

[04-room-drzave/](../04-room-drzave/) – Room model za države.
