package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Magnetometar – X, Y, Z u TextView.
 * Folder: 42-senzor-magnetometar/
 */
public class MagnetometarHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor magnetometer;
    private final TextView textView;

    public MagnetometarHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void onResume() {
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float mx = event.values[0];
            float my = event.values[1];
            float mz = event.values[2];
            textView.setText("Mag X:" + mx + " Y:" + my + " Z:" + mz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
