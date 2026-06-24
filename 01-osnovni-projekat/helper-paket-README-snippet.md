# Helper + lifecycle – šablon za MainActivity

Početni **kostur** metoda (`onResume`, `onPause`, `onDestroy`, `onRequestPermissionsResult`) već postoji u **`01-osnovni-projekat/MainActivity.java`**. U segmentima samo dodaješ linije unutra.

Svaki helper ima **pune metode** u svojoj klasi.  
MainActivity **ne implementira** poslovnu logiku – samo:

1. `new XxxHelper(...)` u `onCreate`
2. listeneri koji pozivaju **postojeće metode** helpera (npr. `postRepository.ucitajPostoveSaApi()`)
3. delegiranje lifecycle-a (`onResume`, `onPause`, `onDestroy`, dozvole)

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // findViewById ...

    postRepository = new PostRepository(this, postDao);
    button.setOnClickListener(v -> postRepository.ucitajPostoveSaApi(listener));
    // ostali helperi – samo init + poziv metode
}
```

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

---

## Alternativa: bez helper klase

Ako helper ne radi, u README svakog segmenta pogledaj sekciju **„Alternativa: inline implementacija u MainActivity“** – sav kod ide direktno u Activity. Za spajanje svih zadataka inline vidi **`16-spajanje-zadataka/`** i **`15-main-activity-referenca/README.md`**.
