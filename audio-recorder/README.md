# Audio snimanje i reprodukcija

**Dodatni segment.** **Slično:** kamera (dozvola + hardver + fajl).

**Cilj:**
- Dugme **Snimi** → snimi audio u fajl pomoću `MediaRecorder`
- Dugme **Pusti** → reprodukuj snimak pomoću `MediaPlayer`

---

## Preduslovi

- `osnovni-projekat/` urađen
- Emulator ili telefon sa mikrofonom

---

## Korak 1: Manifest dozvole

U `AndroidManifest.xml`, ispod ostalih dozvola:

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

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

> **Alternativa:** Jedno dugme koje menja tekst Snimi/Stop – više logike, manje UI-a.

---

## Korak 3: Kompletan kod u `MainActivity.java`

### 1. Importi

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.Button;
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

### 3. U `onCreate`, posle `findViewById` za ostale view-ove

```java
btnSnimi = findViewById(R.id.btnSnimi);
btnPusti = findViewById(R.id.btnPusti);

File audioFajl = new File(getExternalFilesDir(null), "snimak.mp3");
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

### 4. Runtime dozvola

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

private void zapocniMediaRecorder() {
    try {
        mediaRecorder = new MediaRecorder();
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
        Toast.makeText(this, "Greška snimanja", Toast.LENGTH_SHORT).show();
    }
}

private void zaustaviSnimanje() {
    if (mediaRecorder != null) {
        try {
            mediaRecorder.stop();
        } catch (RuntimeException e) {
            // može pasti ako je snimak prekratak
        }
        mediaRecorder.release();
        mediaRecorder = null;
    }
    snima = false;
    btnSnimi.setText("Snimi");
    Toast.makeText(this, "Snimljeno: " + audioPutanja, Toast.LENGTH_SHORT).show();
}
```

### 5. Reprodukcija

```java
private void pustiSnimak() {
    File f = new File(audioPutanja);
    if (!f.exists()) {
        Toast.makeText(this, "Nema snimka", Toast.LENGTH_SHORT).show();
        return;
    }
    if (mediaPlayer != null) {
        mediaPlayer.release();
    }
    try {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioPutanja);
        mediaPlayer.prepare();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    } catch (IOException e) {
        Toast.makeText(this, "Greška reprodukcije", Toast.LENGTH_SHORT).show();
    }
}
```

### 6. U `onRequestPermissionsResult` dodaj

```java
} else if (requestCode == REQ_AUDIO) {
    zapocniMediaRecorder();
}
```

### 7. U `onDestroy` oslobodi resurse

```java
@Override
protected void onDestroy() {
    super.onDestroy();
    if (mediaRecorder != null) {
        mediaRecorder.release();
        mediaRecorder = null;
    }
    if (mediaPlayer != null) {
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
```

---

## Gde se čuva fajl

`getExternalFilesDir(null)/snimak.mp3` – privatno za aplikaciju, **ne treba** `WRITE_EXTERNAL_STORAGE` na novijem Androidu.

> **Alternativa:** `getCacheDir()` – privremeni fajl.  
> **Alternativa:** `MediaStore` – deljeni audio fajlovi, više koda.

---

## Alternativna implementacija: AudioRecord

`MediaRecorder` je jednostavniji za kolokvijum. `AudioRecord` daje sirove uzorke – profesor retko traži na ovom nivou.

---

## Checklist

- [ ] `RECORD_AUDIO` u Manifest-u
- [ ] Runtime dozvola
- [ ] MediaRecorder: source, format, encoder, outputFile
- [ ] `prepare()` + `start()` / `stop()` + `release()`
- [ ] MediaPlayer za puštanje
- [ ] `release()` u onDestroy

---

## Česte greške

| Problem | Rešenje |
|---------|---------|
| Crash pri `stop()` | Snimak prekratak – uhvati `RuntimeException` |
| Nema zvuka na emulatoru | Uključi mikrofon u emulator settings |
| `prepare failed` | Proveri dozvolu i putanju fajla |
