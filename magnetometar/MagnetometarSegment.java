// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;

// POLJE:
private Sensor magnetometer;

// U onCreate():
magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

// U onResume():
if (magnetometer != null) {
    sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged() dodaj:
else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
    float mx = event.values[0];
    float my = event.values[1];
    float mz = event.values[2];
    // npr. prikaži u TextView:
    // textView.setText("Mag X:" + mx + " Y:" + my + " Z:" + mz);
}
