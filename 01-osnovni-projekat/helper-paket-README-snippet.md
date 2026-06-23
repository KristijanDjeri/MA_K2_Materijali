# Helper + lifecycle – šablon za MainActivity

Svaki helper sa senzorom/mapom/audio ima **pune metode** u svojoj klasi.  
MainActivity **samo delegira**:

```java
@Override
protected void onResume() {
    super.onResume();
    // pozovi onResume za SVE aktivne helpere:
    ziroskopHelper.onResume();
    akcelerometarHelper.onResume();
    // magnetometarHelper.onResume();
    // mapsOsmHelper.onResume();
    // lokacijaRealtimeHelper.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    ziroskopHelper.onPause();
    akcelerometarHelper.onPause();
    // ...
}

@Override
protected void onDestroy() {
    super.onDestroy();
    // audioRecorder.onDestroy();
    // okHttpHelper.shutdown();
}

@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    geoHelper.onPermissionGranted(requestCode, grantResults);
    kameraHelper.onPermissionGranted(requestCode, grantResults);
    kontaktiHelper.onPermissionGranted(requestCode, grantResults);
    galerijaHelper.onPermissionGranted(requestCode, grantResults);
    koraciHelper.onPermissionGranted(requestCode, grantResults);
    audioRecorder.onPermissionGranted(requestCode, grantResults);
}
```

Mapa svih helper fajlova: **`HELPER-KLASE.md`**
