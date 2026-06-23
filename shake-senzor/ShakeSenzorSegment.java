// === DODAJ U MainActivity.java (Varijanta A – bez posebne klase) ===

// POLJA:
// private static final float SHAKE_THRESHOLD = 12.0f;
// private static final int SHAKE_COOLDOWN_MS = 1000;
// private long poslednjiShakeVreme = 0;

// U onSensorChanged, unutar TYPE_ACCELEROMETER grane:
// detektujShake(x, y, z);

// METODE – vidi README.md za pun kod:
// detektujShake(float x, float y, float z)
// onShakeDetektovan()

// === Varijanta C – sa ShakeDetector.java ===

// import com.example.kolokvijum2.ShakeDetector;
// private ShakeDetector shakeDetector;

// onCreate:
// shakeDetector = new ShakeDetector(() ->
//     Toast.makeText(this, "Tresenje detektovano!", Toast.LENGTH_SHORT).show()
// );

// onSensorChanged (akcelerometar):
// shakeDetector.obradiSensorEvent(event);
