package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 45-senzor-barometar/ */
public class BarometarHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor barometer;
    private final TextView textView;

    public BarometarHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    public void onResume() {
        if (barometer != null) {
            sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            textView.setText("Pritisak: " + event.values[0] + " hPa");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
