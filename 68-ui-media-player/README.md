# Media player UI – slika, video, audio

**Dodatni segment (UI).** **Slično:** `03-kamera/` (ImageView), `83-audio-recorder/` (MediaPlayer), ali ovde fokus na **prikaz i reprodukciju** gotovih medija u layoutu.

**Cilj:**
- **Slika** u `ImageView`
- **Video** u `VideoView` + kontrole
- **Audio** preko `MediaPlayer` (Pusti / Pauza)

> Za **snimanje** mikrofonom koristi `83-audio-recorder/`. Ovde samo puštaš već pripremljene fajlove.

---

## Preduslovi

- `01-osnovni-projekat/` urađen
- Test fajlovi u projektu (vidi ispod)

---

## Fajlovi koje dodaješ

| Fajl | Putanja |
|------|---------|
| Layout deo | `res/layout/activity_main.xml` |
| `sample_slika.jpg` | `res/drawable/` |
| `sample_video.mp4` | `res/raw/sample_video.mp4` |
| `sample_audio.mp3` | `res/raw/sample_audio.mp3` |
| `MediaPlayerHelper.java` | `.../helper/MediaPlayerHelper.java` |

---

## 1. Test mediji

U Android Studio:

1. **Slika:** kopiraj JPG/PNG u `res/drawable/` → ime `sample_slika` (Android automatski `R.drawable.sample_slika`)
2. **Video:** `res/raw/sample_video.mp4` (mora **mala slova** i `_`, bez razmaka)
3. **Audio:** `res/raw/sample_audio.mp3`

> Ako nemaš fajlove na kolokvijumu, koristi bilo koji mali mp3/mp4 sa telefona ili zameni sa URL-om za video (vidi Alternativa).

---

## 2. Layout – dodaj u `activity_main.xml`

Kopiraj iz `media_player_layout_snippet.xml` ili:

```xml
<ImageView
    android:id="@+id/imageViewMedia"
    android:layout_width="match_parent"
    android:layout_height="160dp"
    android:scaleType="centerCrop"
    android:contentDescription="Prikaz slike" />

<VideoView
    android:id="@+id/videoView"
    android:layout_width="match_parent"
    android:layout_height="200dp" />

<Button
    android:id="@+id/btnPlayVideo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Pusti video" />

<Button
    android:id="@+id/btnPustiAudio"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:text="Pusti audio" />

<Button
    android:id="@+id/btnPauzaAudio"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:text="Pauza" />
```

(`btnPustiAudio` i `btnPauzaAudio` stavi u horizontalni `LinearLayout` – vidi snippet.)

---

## Kompletan kod – helper klasa

Kopiraj **`MediaPlayerHelper.java`** iz ovog foldera u `app/.../helper/MediaPlayerHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

Vidi sekciju 3 ispod za pun primer sa lifecycle-om.

---

## 3. Kod u `MainActivity.java`

### Importi

```java
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import com.example.kolokvijum2.R;
import com.example.kolokvijum2.helper.MediaPlayerHelper;
```

### Polje i inicijalizacija

```java
private MediaPlayerHelper mediaPlayerHelper;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ImageView imageViewMedia = findViewById(R.id.imageViewMedia);
    VideoView videoView = findViewById(R.id.videoView);
    Button btnPlayVideo = findViewById(R.id.btnPlayVideo);
    Button btnPustiAudio = findViewById(R.id.btnPustiAudio);
    Button btnPauzaAudio = findViewById(R.id.btnPauzaAudio);

    mediaPlayerHelper = new MediaPlayerHelper(
            this, imageViewMedia, videoView,
            btnPlayVideo, btnPustiAudio, btnPauzaAudio
    );

    mediaPlayerHelper.prikaziSliku(R.drawable.sample_slika);
    mediaPlayerHelper.pripremiVideo(R.raw.sample_video);
    mediaPlayerHelper.pripremiAudio(R.raw.sample_audio);
}
```

### Lifecycle (obavezno)

```java
@Override
protected void onPause() {
    super.onPause();
    if (mediaPlayerHelper != null) {
        mediaPlayerHelper.onPause();
    }
}

@Override
protected void onDestroy() {
    if (mediaPlayerHelper != null) {
        mediaPlayerHelper.onDestroy();
    }
    super.onDestroy();
}
```

---

## Kako radi svaki deo

| Tip | Widget / klasa | Šta radi |
|-----|----------------|----------|
| **Slika** | `ImageView` | `setImageResource()` ili `setImageURI()` |
| **Video** | `VideoView` + `MediaController` | `setVideoURI()` → `start()` |
| **Audio** | `MediaPlayer` | `create()` → `start()` / `pause()` / `release()` |

### Video URI iz raw foldera

```java
String putanja = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
videoView.setVideoURI(Uri.parse(putanja));
```

### Slika iz galerije (posle `76-galerija/`)

```java
mediaPlayerHelper.prikaziSliku(uriIzGalerije);
```

---

## Alternativa: video sa interneta

Zahteva `INTERNET` dozvolu (već u `01-osnovni-projekat/AndroidManifest-dozvole.xml`):

```java
videoView.setVideoURI(Uri.parse("https://www.example.com/kratak_video.mp4"));
videoView.start();
```

---

## Poređenje sa srodnim segmentima

| Segment | Šta radi |
|---------|----------|
| `03-kamera/` | Slika sa kamere u `imageView` |
| `76-galerija/` | Izbor slike iz galerije |
| `83-audio-recorder/` | Snimanje + puštanje **svog** snimka |
| **Ovde** | UI za sliku + video + audio iz resursa |

---

## Checklist

- [ ] `sample_slika`, `sample_video`, `sample_audio` u projektu
- [ ] Layout: `imageViewMedia`, `videoView`, dugmad
- [ ] `MediaPlayerHelper` ili inline kod
- [ ] `onPause` / `onDestroy` – `release()` za MediaPlayer
- [ ] Video se vidi i pušta; audio reaguje na dugmad

---

## Povezano

- Galerija umesto drawable: `76-galerija/`
- Snimanje zvuka: `83-audio-recorder/`
- Kamera za sliku: `03-kamera/`
