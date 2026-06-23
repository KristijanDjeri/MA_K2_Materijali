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
Na API 28+ (tvoj minSdk) **uvek** koristi `requestPermissions` pre snimanja.

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

## Korak 5: Kompletan kod u `MainActivity.java`

### 1. Importi

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
```

### 2. Konstante i polja

```java
private static final int REQ_AUDIO = 106;

private Button btnSnimi;
private Button btnPusti;

private MediaRecorder mediaRecorder;
private MediaPlayer mediaPlayer;
private boolean snima = false;
private String audioPutanja;
```

### 3. U `onCreate`

```java
btnSnimi = findViewById(R.id.btnSnimi);
btnPusti = findViewById(R.id.btnPusti);

File audioFajl = new File(getExternalFilesDir(null), "snimak.m4a");
audioPutanja = audioFajl.getAbsolutePath();

btnSnimi.setOnClickListener(v -> {
    if (!snima) {
        pokreniSnimanje();
    } else {
        zaustaviSnimanje();
    }
});

btnPusti.setOnClickListener(v -> pustiSnimak());
```

### 4. Pokretanje snimanja (sa dozvolom)

```java
private void pokreniSnimanje() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO);
        return;
    }
    zapocniMediaRecorder();
}
```

### 5. MediaRecorder – redosled metoda je obavezan

Android zahteva ovaj redosled:

1. `setAudioSource` – odakle zvuk (mikrofon)
2. `setOutputFormat` – format kontejnera
3. `setAudioEncoder` – kako se kompresuje zvuk
4. `setOutputFile` – putanja fajla
5. `prepare` – priprema
6. `start` – počinje snimanje

```java
private void zapocniMediaRecorder() {
    // Zaustavi reprodukciju ako još svira
    zaustaviReprodukciju();

    // Obriši stari snimak da novi ne bude pokvaren / prazan
    File stari = new File(audioPutanja);
    if (stari.exists()) {
        stari.delete();
    }

    try {
        // API 31+: konstruktor sa Context-om (preporučeno)
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
    } catch (IOException e) {
        oslobodiMediaRecorder();
        Toast.makeText(this, "Greška snimanja: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    } catch (IllegalStateException e) {
        oslobodiMediaRecorder();
        Toast.makeText(this, "MediaRecorder nije spreman", Toast.LENGTH_SHORT).show();
    }
}
```

### 6. Zaustavljanje snimanja

```java
private void zaustaviSnimanje() {
    if (mediaRecorder == null) {
        snima = false;
        btnSnimi.setText("Snimi");
        return;
    }

    try {
        mediaRecorder.stop();
    } catch (RuntimeException e) {
        // Dešava se ako je snimak prekratak (< ~1s) ili je prepare propao
        // Obriši pokvareni fajl
        new File(audioPutanja).delete();
        Toast.makeText(this, "Snimak prekratak ili neispravan", Toast.LENGTH_SHORT).show();
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
```

**Zašto `try/catch` oko `stop()`?**  
Ako korisnik pritisne Stop posle samo pola sekunde, `stop()` baca `RuntimeException` i app može da padne bez `catch`-a.

### 7. Reprodukcija

```java
private void pustiSnimak() {
    if (snima) {
        Toast.makeText(this, "Prvo zaustavi snimanje", Toast.LENGTH_SHORT).show();
        return;
    }

    File f = new File(audioPutanja);
    if (!f.exists() || f.length() == 0) {
        Toast.makeText(this, "Nema snimka – prvo snimi", Toast.LENGTH_SHORT).show();
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
```

**Zašto `prepare()` pre `start()`?**  
`MediaPlayer` mora da učita fajl sa diska pre puštanja. Za lokalne fajlove `prepare()` je dovoljan (za mrežne URL koristi se `prepareAsync()`).

### 8. Dozvola – callback

U `onRequestPermissionsResult`:

```java
} else if (requestCode == REQ_AUDIO) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        zapocniMediaRecorder();
    } else {
        Toast.makeText(this, "Bez dozvole za mikrofon nema snimanja", Toast.LENGTH_SHORT).show();
    }
}
```

### 9. Životni ciklus – ne zaboravi

Ako korisnik izađe iz aplikacije dok snima, moraš osloboditi mikrofon.

```java
@Override
protected void onPause() {
    super.onPause();
    if (snima) {
        zaustaviSnimanje();
    }
    zaustaviReprodukciju();
}

@Override
protected void onDestroy() {
    if (snima) {
        zaustaviSnimanje();
    } else {
        oslobodiMediaRecorder();
    }
    zaustaviReprodukciju();
    super.onDestroy();
}
```

> **Zašto?** Ako ostaviš `MediaRecorder` aktivan, mikrofon ostaje zauzet i drugi delovi app-a ili sistem mogu da odbiju snimanje.

---

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

- Sačuvaj trajanje snimka u `SharedPreferences` – folder `09-shared-preferences/`
- Pošalji fajl na server – folder `23-retrofit-post/` ili Firebase Storage (nije u materijalu)
