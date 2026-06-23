package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 43-senzor-svetlosti/ */
public class SvetlostiHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor lightSensor;
    private final TextView textView;

    public SvetlostiHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onResume() {
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("Svetlina: " + event.values[0] + " lux");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
