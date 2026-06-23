package com.example.kolokvijum2.helper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ZiroskopHelper implements SensorEventListener {

    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor gyroscope;
    private float gyroX, gyroY, gyroZ;

    public ZiroskopHelper(AppCompatActivity activity) {
        this.context = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void onResume() {
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
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
    }

    public void prikaziToast() {
        Toast.makeText(context,
                "Žiroskop X: " + gyroX + ", Y: " + gyroY + ", Z: " + gyroZ,
                Toast.LENGTH_SHORT).show();
    }
}
