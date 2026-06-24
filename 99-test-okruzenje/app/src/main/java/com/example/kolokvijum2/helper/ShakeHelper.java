package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 49-senzor-shake/ */
public class ShakeHelper implements SensorEventListener {

    public interface OnShakeListener extends ShakeDetector.OnShakeListener {
    }

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final ShakeDetector shakeDetector;

    public ShakeHelper(AppCompatActivity activity, OnShakeListener listener) {
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.shakeDetector = new ShakeDetector(listener);
    }

    public void onResume() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        shakeDetector.obradiSensorEvent(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
