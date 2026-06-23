// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

// MainActivity implementira SensorEventListener:
// public class MainActivity extends AppCompatActivity implements SensorEventListener {

// POLJA:
private SensorManager sensorManager;
private Sensor gyroscope;
private float gyroX, gyroY, gyroZ;

// U onCreate():
sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

@Override
protected void onResume() {
    super.onResume();
    if (gyroscope != null) {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
}

@Override
protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
}

@Override
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        gyroX = event.values[0];
        gyroY = event.values[1];
        gyroZ = event.values[2];
    }
}

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // prazno
}

private void prikaziZiroskopToast() {
    String poruka = "Žiroskop X: " + gyroX + ", Y: " + gyroY + ", Z: " + gyroZ;
    Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show();
}
