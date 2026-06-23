// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

// POLJA (pored žiroskopa):
private Sensor accelerometer;

// U onCreate():
accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

// U onResume() dodaj registraciju:
if (accelerometer != null) {
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged() proširi switch/if:

@Override
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        gyroX = event.values[0];
        gyroY = event.values[1];
        gyroZ = event.values[2];
    } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        button.setText("X: " + ax + " Y: " + ay + " Z: " + az);
    }
}

// button.setOnClickListener za brisanje ostaje iz 07-brisanje-notifikacije/
