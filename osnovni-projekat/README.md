# Osnovni projekat i UI (zadaci 1–2)

## Šta uraditi

1. **File → New → New Project**
2. Izaberi **Empty Views Activity** (ili Empty Activity)
3. **Name:** `Kolokvijum2`
4. **Package name:** npr. `com.example.kolokvijum2`
5. **Language:** Java
6. **Minimum SDK:** API 28 (Android 9.0)
7. Glavna aktivnost mora biti **`MainActivity`**

## Gde se fajlovi nalaze u projektu

| Fajl | Putanja u Android Studio projektu |
|------|-----------------------------------|
| Layout | `app/src/main/res/layout/activity_main.xml` |
| MainActivity | `app/src/main/java/.../MainActivity.java` |
| Manifest | `app/src/main/AndroidManifest.xml` |
| Gradle (app) | `app/build.gradle` |

## Koraci

### 1. Layout (`activity_main.xml`)

Kopiraj sadržaj iz `activity_main.xml` u ovom folderu.  
Komponente **jedna ispod druge**: TextView → ImageButton → ImageView → Switch → Button.

### 2. Gradle zavisnosti

U `app/build.gradle`, u bloku `dependencies { }`, dodaj sadržaj iz `gradle-zavisnosti.txt`.

U `android { }` bloku proveri:

```gradle
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

Klikni **Sync Now**.

### 3. AndroidManifest

Dodaj dozvole iz `AndroidManifest-dozvole.xml` u manifest (ispod `<manifest>` taga, pre `<application>`).

### 4. MainActivity – početak

U `onCreate` samo poveži view-ove:

```java
TextView textView = findViewById(R.id.textView);
ImageButton imageButton = findViewById(R.id.imageButton);
ImageView imageView = findViewById(R.id.imageView);
Switch switchPosts = findViewById(R.id.switchPosts);
Button button = findViewById(R.id.button);
```

Ostala logika ide iz ostalih foldera.

## Fajlovi u ovom folderu

- `activity_main.xml` – kompletan layout
- `gradle-zavisnosti.txt` – Retrofit, Room, Location
- `AndroidManifest-dozvole.xml` – sve potrebne dozvole
