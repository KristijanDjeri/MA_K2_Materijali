# Osnovni projekat i UI (zadaci 1–2) – vežba F

**Cilj:** Kreirati projekat **Kolokvijum2** sa **MainActivity** i layoutom za ovu vežbu.

---

## Korak 1: Novi projekat u Android Studio

1. **File → New → New Project**
2. **Empty Views Activity** (ili Empty Activity)
3. Popuni:
   - **Name:** `Kolokvijum2`
   - **Package:** `com.example.kolokvijum2`
   - **Language:** Java
   - **Minimum SDK:** API 30
   - **Target SDK:** API 36
4. **Finish**

---

## Korak 2: Layout – `activity_main.xml`

**Putanja:** `app/src/main/res/layout/activity_main.xml`

Kopiraj iz ovog foldera fajl **`activity_main.xml`** ili nalepi:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textViewLokacija"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Lokacija..."
        android:textSize="16sp"
        android:paddingBottom="8dp" />

    <CheckBox
        android:id="@+id/checkBoxSnimanje"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Zaustavi snimanje"
        android:paddingBottom="8dp" />

    <Button
        android:id="@+id/buttonSnimi"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Snimi"
        android:paddingBottom="8dp" />

    <CheckBox
        android:id="@+id/checkBoxDrzave"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Države"
        android:paddingBottom="8dp" />

    <TextView
        android:id="@+id/textViewProksimitet"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Proksimitet..."
        android:textSize="16sp" />

</LinearLayout>
```

### ID-jevi (koristi u `MainActivity`)

| `@+id/...` | Zadatak |
|-----------|---------|
| `textViewLokacija` | 3 – geolokacija |
| `checkBoxSnimanje` | 4 – stop snimanja |
| `buttonSnimi` | 4 – start snimanja |
| `checkBoxDrzave` | 6–7 – API + brisanje |
| `textViewProksimitet` | 8 – proximity |

---

## Korak 3: Gradle (`app/build.gradle`)

U `dependencies { }` dodaj (vidi i glavni [README.md](../README.md)):

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'
implementation 'com.google.android.gms:play-services-location:21.0.1'
```

**Sync Now.**

---

## Korak 4: Manifest dozvole

Kopiraj iz **`AndroidManifest-dozvole.xml`** u `app/src/main/AndroidManifest.xml` (iznad `<application>`).

---

## Korak 5: Kostur `MainActivity.java`

Kopiraj **`MainActivity-kostur.java`** u projekat kao `MainActivity.java`  
(ili preimenuj sadržaj – package ostaje `com.example.kolokvijum2`).

Kostur sadrži:

- `findViewById` za svih 5 komponenti
- prazne `onResume`, `onPause`, `onDestroy`, `onRequestPermissionsResult`

U narednim folderima **dodaješ po jedan blok** koda.

---

## Checklist

- [ ] Projekat se zove Kolokvijum2
- [ ] Glavna aktivnost: MainActivity
- [ ] 2 CheckBox-a, 1 Button („Snimi“), 2 TextView-a – vertikalno
- [ ] Gradle Sync OK
- [ ] Aplikacija se pokreće bez crash-a

---

## Sledeći korak

[02-geolokacija/](../02-geolokacija/) – prikaži koordinate u `textViewLokacija`.
