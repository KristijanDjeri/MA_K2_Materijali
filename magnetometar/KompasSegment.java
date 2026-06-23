// === KOMPAS (azimut) – naprednija varijanta ===

// IMPORTI:
import android.hardware.SensorManager;

// POLJA:
private float[] gravity = new float[3];
private float[] geomagnetic = new float[3];
private float[] rotationMatrix = new float[9];
private float[] orientation = new float[3];

// U onSensorChanged:
if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
    gravity = event.values.clone();
} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
    geomagnetic = event.values.clone();
}

if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
    SensorManager.getOrientation(rotationMatrix, orientation);
    float azimutRad = orientation[0];
    float azimutStepeni = (float) Math.toDegrees(azimutRad);
    // textView.setText("Azimut: " + azimutStepeni + "°");
}

// Registruj OBA senzora u onResume!
