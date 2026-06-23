package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 47-senzor-izvedeni/ – TYPE_GRAVITY */
public class IzvedeniSenzorHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor gravitySensor;
    private final TextView textView;

    public IzvedeniSenzorHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    public void onResume() {
        if (gravitySensor != null) {
            sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float gx = event.values[0];
            float gy = event.values[1];
            float gz = event.values[2];
            textView.setText("Gravity X:" + gx + " Y:" + gy + " Z:" + gz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
