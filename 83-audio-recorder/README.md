# Audio snimanje i reprodukcija

**Dodatni segment.** **Slično:** 03-kamera (runtime dozvola + hardver + fajl na disku).

**Cilj:**
- Dugme **Snimi** → počne snimanje mikrofonom (`MediaRecorder`)
- Dugme **Stop** (isto dugme) → zaustavi i sačuvaj fajl
- Dugme **Pusti** → reprodukuj poslednji snimak (`MediaPlayer`)

---

## Kako ovo radi (ukratko)

```
[Korisnik] → Snimi
    → provera RECORD_AUDIO dozvole
    → MediaRecorder čita mikrofon
    → piše u fajl na disku (npr. snimak.m4a)

[Korisnik] → Stop
    → mediaRecorder.stop() + release()
    → fajl ostaje sačuvan

[Korisnik] → Pusti
    → MediaPlayer učitava taj fajl i pušta zvuk
```

**MediaRecorder** = snima. **MediaPlayer** = pušta. To su dve odvojene klase.

---

## Preduslovi

- `01-osnovni-projekat/` urađen
- Fizički telefon **ili** emulator sa uključenim mikrofonom
- Manifest dozvola `RECORD_AUDIO` (već u `01-osnovni-projekat/AndroidManifest-dozvole.xml`)

---

## Korak 1: Manifest

U `AndroidManifest.xml` (ako već nije):

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

Ovo je **opasna dozvola** – mora se tražiti u runtime-u na Android 6+ (API 23+).  
Na **minSdk 30** **uvek** koristi `requestPermissions` pre snimanja.

> **Ne treba** `WRITE_EXTERNAL_STORAGE` jer čuvamo u privatni folder aplikacije.

---

## Korak 2: Layout – dodaj u `activity_main.xml`

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:paddingBottom="8dp">

    <Button
        android:id="@+id/btnSnimi"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Snimi" />

    <Button
        android:id="@+id/btnPusti"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Pusti" />

</LinearLayout>
```

---

## Korak 3: Format fajla – važno

U originalnoj verziji ekstenzija je bila `.mp3`, ali encoder **AAC + MPEG_4** ne pravi pravi MP3 fajl.

| Postavka | Vrednost | Ekstenzija |
|----------|----------|------------|
| `OutputFormat.MPEG_4` + `AudioEncoder.AAC` | Preporučeno | **`.m4a`** |
| `OutputFormat.THREE_GPP` + `AudioEncoder.AMR_NB` | Stariji, manji fajl | **`.3gp`** |

Koristimo **`.m4a`** – `MediaPlayer` ga pouzdano pušta.

> **Alternativa:** Ako profesor eksplicitno traži `.mp3`, mora se drugačiji encoder/biblioteka – `MediaRecorder` na Androidu **ne garantuje** MP3 na svim uređajima.

---

## Korak 4: Gde se čuva fajl

```java
File audioFajl = new File(getExternalFilesDir(null), "snimak.m4a");
audioPutanja = audioFajl.getAbsolutePath();
```

**`getExternalFilesDir(null)`** = folder tipa:
`/storage/emulated/0/Android/data/com.example.kolokvijum2/files/snimak.m4a`

- Privatno je za tvoju aplikaciju
- Drugi app-ovi ne vide fajl
- Briše se kad korisnik deinstalira aplikaciju

> **Alternativa:** `getFilesDir()` – interni storage, ista logika.  
> **Alternativa:** `getCacheDir()` – sistem može obrisati kad mu zafali prostora.

---

## Korak 5: Kompletan kod – helper klasa (`AudioRecorder`)

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`AudioRecorder.java`** | Novi fajl → `app/.../helper/AudioRecorder.java` |
| 2 | `MainActivity.java` | Polje: `private AudioRecorder audioRecorder;` |
| 3 | `MainActivity.java` | **`onCreate`**, posle `findViewById` za `btnSnimi`/`btnPusti`: `audioRecorder = new AudioRecorder(this, btnSnimi, btnPusti);` |
| 4 | `MainActivity.java` | **`onPause`**: `audioRecorder.onPause();` |
| 5 | `MainActivity.java` | **`onDestroy`**: `audioRecorder.onDestroy();` |
| 6 | `MainActivity.java` | **`onRequestPermissionsResult`**: `audioRecorder.onPermissionGranted(requestCode, grantResults);` |

```java
import com.example.kolokvijum2.helper.AudioRecorder;

private AudioRecorder audioRecorder;

// onCreate:
audioRecorder = new AudioRecorder(this, btnSnimi, btnPusti);
```

> Listeneri na dugmad su **unutar** `AudioRecorder` konstruktora – ne dodaj ih ponovo u MainActivity.

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
package com.example.kolokvijum2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

/**
 * Zastarelo – koristi {@link com.example.kolokvijum2.helper.AudioRecorder} (AudioRecorder.java u ovom folderu).
 * Ovaj fajl ostaje kao skraćena referenca.
 */
@Deprecated
public class AudioRecorderSegment extends AppCompatActivity {

    private static final int REQ_AUDIO = 106;

    private Button btnSnimi;
    private Button btnPusti;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean snima = false;
    private String audioPutanja;

  // Primer inicijalizacije u onCreate MainActivity-ja:
  // btnSnimi = findViewById(R.id.btnSnimi);
  // btnPusti = findViewById(R.id.btnPusti);
  // audioPutanja = new File(getExternalFilesDir(null), "snimak.m4a").getAbsolutePath();
  // btnSnimi.setOnClickListener(v -> { if (!snima) pokreniSnimanje(); else zaustaviSnimanje(); });
  // btnPusti.setOnClickListener(v -> pustiSnimak());

    private void pokreniSnimanje() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
            return;
        }
        zapocniMediaRecorder();
    }

    private void zapocniMediaRecorder() {
        zaustaviReprodukciju();

        File stari = new File(audioPutanja);
        if (stari.exists()) {
            stari.delete();
        }

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
            btnSnimi.setText("Stop");
            Toast.makeText(this, "Snimanje...", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalStateException e) {
            oslobodiMediaRecorder();
            Toast.makeText(this, "Greška snimanja", Toast.LENGTH_SHORT).show();
        }
    }

    private void zaustaviSnimanje() {
        if (mediaRecorder == null) {
            snima = false;
            btnSnimi.setText("Snimi");
            return;
        }
        try {
            mediaRecorder.stop();
        } catch (RuntimeException e) {
            new File(audioPutanja).delete();
            Toast.makeText(this, "Snimak prekratak", Toast.LENGTH_SHORT).show();
        } finally {
            oslobodiMediaRecorder();
        }
        snima = false;
        btnSnimi.setText("Snimi");
        if (new File(audioPutanja).exists()) {
            Toast.makeText(this, "Snimljeno", Toast.LENGTH_SHORT).show();
        }
    }

    private void oslobodiMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void pustiSnimak() {
        if (snima) {
            Toast.makeText(this, "Prvo zaustavi snimanje", Toast.LENGTH_SHORT).show();
            return;
        }
        File f = new File(audioPutanja);
        if (!f.exists() || f.length() == 0) {
            Toast.makeText(this, "Nema snimka", Toast.LENGTH_SHORT).show();
            return;
        }
        zaustaviReprodukciju();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioPutanja);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> zaustaviReprodukciju());
        } catch (IOException e) {
            zaustaviReprodukciju();
            Toast.makeText(this, "Greška reprodukcije", Toast.LENGTH_SHORT).show();
        }
    }

    private void zaustaviReprodukciju() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException ignored) {
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
```

## Objašnjenje svake klase

| Klasa | Uloga |
|-------|--------|
| `MediaRecorder` | Snima zvuk sa mikrofona u fajl |
| `MediaPlayer` | Pušta audio fajl (ili stream) |
| `AudioRecord` | **Alternativa** – sirovi audio uzorci, više koda, retko na kolokvijumu |

---

## Testiranje

### Na telefonu
1. Pokreni app → Snimi → dozvoli mikrofon
2. Govori 2–3 sekunde → Stop
3. Pusti → čuješ snimak

### Na emulatoru
1. Emulator toolbar → **...** → **Microphone**
2. Uključi **Virtual microphone** ili feed with host mic
3. Ako nema zvuka, probaj na fizičkom uređaju – emulator je nepouzdan za audio

### Provera da li fajl postoji (Logcat)

```java
Log.d("AUDIO", "Putanja: " + audioPutanja + " postoji=" + new File(audioPutanja).exists());
```

---

## Šta je popravljeno u odnosu na staru verziju

| Problem | Rešenje |
|---------|---------|
| Ekstenzija `.mp3` sa AAC encoderom | Promenjeno u **`.m4a`** (ispravan format) |
| Novi snimak preko starog fajla | **`delete()`** starog pre snimanja |
| `stop()` ruši app | **`try/catch RuntimeException`** |
| Puštanje tokom snimanja | Provera `if (snima)` |
| Reprodukcija više puta | `zaustaviReprodukciju()` pre novog `MediaPlayer` |
| Curenje mikrofona | `onPause` / `onDestroy` oslobađaju recorder i player |
| API 31+ | `new MediaRecorder(this)` |
| Prazan fajl | Provera `f.length() == 0` |

---

## Checklist

- [ ] `RECORD_AUDIO` u Manifest-u
- [ ] Runtime dozvola pre `start()`
- [ ] Fajl `.m4a` (ili `.3gp` sa AMR)
- [ ] Redosled: source → format → encoder → file → prepare → start
- [ ] `stop()` + `release()` pri zaustavljanju
- [ ] `onPause` / `onDestroy` čiste resurse
- [ ] MediaPlayer: `setDataSource` → `prepare` → `start`

---

## Česte greške

| Problem | Uzrok | Rešenje |
|---------|-------|---------|
| `prepare failed -2147483648` | Loša putanja ili nema dozvole | Dozvola + `getExternalFilesDir` |
| Crash na `stop()` | Snimak < 1 sekunda | `catch RuntimeException` |
| Tišina pri reprodukciji | Pogrešna ekstenzija / prazan fajl | `.m4a`, proveri `length()` |
| `MediaRecorder: Error` | Stari recorder nije `release()`-ovan | `oslobodiMediaRecorder()` u catch |
| Drugi app ne snima | Mikrofon zauzet | `onPause` zaustavi snimanje |

---

## Sledeći korak

- Sačuvaj trajanje snimka u `SharedPreferences` – folder `13-shared-preferences/`
- Pošalji fajl na server – folder `72-retrofit-post/` ili Firebase Storage (nije u materijalu)
