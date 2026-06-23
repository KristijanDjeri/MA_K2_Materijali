# Osnovni projekat i UI (zadaci 1–2)

Ovaj folder je **prvi korak**. Ovde praviš projekat, layout sa svim dugmićima i podešavaš Gradle + Manifest.  
Bez ovoga ne možeš raditi ostale segmente.

---

## Šta tačno radi ovaj deo

1. Kreira Android projekat **Kolokvijum2**
2. Glavna aktivnost se zove **MainActivity**
3. Na ekranu su komponente **jedna ispod druge**: TextView → ImageButton → ImageView → Switch → Button

---

## Korak 1: Napravi projekat u Android Studio

1. Otvori **Android Studio**
2. **File → New → New Project**
3. Izaberi **Empty Views Activity** (ili **Empty Activity**)
4. Popuni:
   - **Name:** `Kolokvijum2`
   - **Package name:** `com.example.kolokvijum2` *(ako profesor ne traži drugačije)*
   - **Language:** `Java`
   - **Minimum SDK:** `API 28` (Android 9.0)
5. Klikni **Finish**

Android Studio automatski kreira `MainActivity.java` i `activity_main.xml`.

---

## Korak 2: Zameni layout (`activity_main.xml`)

**Putanja u projektu:**  
`app/src/main/res/layout/activity_main.xml`

Obriši sav postojeći sadržaj i nalepi **ceo** ovaj kod:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Lokacija..."
        android:textSize="16sp"
        android:paddingBottom="8dp" />

    <ImageButton
        android:id="@+id/imageButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@android:drawable/ic_menu_camera"
        android:contentDescription="Slikaj"
        android:paddingBottom="8dp" />

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:scaleType="centerCrop"
        android:background="#EEEEEE"
        android:contentDescription="Fotografija"
        android:paddingBottom="8dp" />

    <Switch
        android:id="@+id/switchPosts"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Postovi"
        android:paddingBottom="8dp" />

    <Button
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Akcelerometar" />

</LinearLayout>
```

**Objašnjenje ID-jeva** (koristićeš ih u `MainActivity` sa `findViewById`):
- `textView` – prikaz lokacije / teksta
- `imageButton` – dugme za kameru
- `imageView` – prikaz slike
- `switchPosts` – Switch za postove
- `button` – dugme (`12-senzor-akcelerometar` tekst; `10-brisanje-prvog-posta` klik na ispitu)

---

## Korak 3: Podesi Gradle (`app/build.gradle`)

**Putanja:** `app/build.gradle` (Module :app)

Nađi blok `dependencies { ... }` i **dodaj** ove linije unutra:

```gradle
// Retrofit (GET zahtev ka API-ju)
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// Room (baza postova)
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'

// Geolokacija (Fused Location Provider)
implementation 'com.google.android.gms:play-services-location:21.0.1'

// RecyclerView (lista postova – dodatni segment, opciono za sada)
implementation 'androidx.recyclerview:recyclerview:1.3.2'
```

Proveri da u istom fajlu postoji i ovo (obično već postoji):

```gradle
android {
    compileSdk 34   // ili noviji, npr. 35

    defaultConfig {
        applicationId "com.example.kolokvijum2"
        minSdk 28
        targetSdk 34
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

Klikni **Sync Now** kad Android Studio ponudi.

> **Alternativa:** Verzije biblioteka mogu biti malo drugačije (npr. `2.11.0` za Retrofit). Za kolokvijum je dovoljno da Sync prođe bez greške.

---

## Korak 4: Dodaj dozvole u Manifest

**Putanja:** `app/src/main/AndroidManifest.xml`

Unutar `<manifest ...>` taga, **iznad** `<application ...>`, dodaj:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />

<uses-feature android:name="android.hardware.camera" android:required="false" />
<uses-feature android:name="android.hardware.microphone" android:required="false" />
```

Primer kako manifest treba da izgleda (skraćeno):

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />

    <uses-feature android:name="android.hardware.camera" android:required="false" />
    <uses-feature android:name="android.hardware.microphone" android:required="false" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.Kolokvijum2">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>
</manifest>
```

> **Napomena:** `POST_NOTIFICATIONS` je obavezna runtime dozvola na Android 13+. `VIBRATE` je za dodatni segment 82-povratna-vibracija – možeš je ostaviti unapred.

---

## Korak 5: Početni `MainActivity.java`

**Putanja:**  
`app/src/main/java/com/example/kolokvijum2/MainActivity.java`

Za sada dovoljno da **povežeš view-ove**. Ostatak logike dolazi iz drugih foldera.

```java
package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageButton imageButton;
    private ImageView imageView;
    private Switch switchPosts;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        switchPosts = findViewById(R.id.switchPosts);
        button = findViewById(R.id.button);
    }
}
```

> **Alternativa:** `findViewById` možeš pisati i bez polja u klasi, direktno u listenerima. Ovako je preglednije kad dodaješ ostale segmente.

---

## Checklist pre prelaska na sledeći segment

- [ ] Projekat se zove Kolokvijum2
- [ ] `MainActivity` postoji
- [ ] Layout ima svih 5 komponenti jedna ispod druge
- [ ] Gradle Sync je uspeo
- [ ] Dozvole su u Manifest-u
- [ ] Aplikacija se pokreće bez crash-a

---

## Česte greške

| Greška | Rešenje |
|--------|---------|
| `Cannot resolve symbol R` | Sačekaj Gradle Sync ili **Build → Rebuild Project** |
| Crveni `findViewById` | Proveri da li se `android:id` u XML-u poklapa |
| Sync failed za Room | Proveri da imaš `annotationProcessor` liniju |

---

## Sledeći korak

Nakon ovoga idi na folder **`02-geo-lokacija/`** za zadatak 3.
