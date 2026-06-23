# Helper klase – konvencija (kopiraj u projekat)

Svaki segment može imati **zasebnu helper klasu** umesto metoda u `MainActivity`.

## Paket u Android Studio projektu

```
app/src/main/java/com/example/kolokvijum2/helper/
```

Desni klik na `com.example.kolokvijum2` → **New → Package** → `helper`  
Zatim kopiraj `.java` fajl iz foldera segmenta u taj paket.

## Šablon – gde nalepiti u MainActivity

| Korak | Gde u `MainActivity.java` | Primer |
|-------|---------------------------|--------|
| 1. Import | vrh fajla | `import com.example.kolokvijum2.helper.GeoLokacijaHelper;` |
| 2. Polje | iznad `onCreate` | `private GeoLokacijaHelper geoHelper;` |
| 3. Inicijalizacija | **`onCreate`**, posle `findViewById` | `geoHelper = new GeoLokacijaHelper(this, textView);` |
| 4. Pokretanje | **`onCreate`** ili listener | `geoHelper.pokreni();` |
| 5. Lifecycle | `onResume` / `onPause` / `onDestroy` | `sensorsHelper.onResume();` |
| 6. Dozvole | **`onRequestPermissionsResult`** | `geoHelper.onPermissionGranted(requestCode, grantResults);` |

**Pravilo:** `MainActivity` drži samo UI (`findViewById`, listeneri) i poziva helper – poslovna logika je u helper klasi.
