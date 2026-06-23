package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 48-senzor-vlage-temperature/ – TYPE_AMBIENT_TEMPERATURE ili TYPE_RELATIVE_HUMIDITY */
public class VlagaTemperaturaHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor tempSensor;
    private final Sensor humiditySensor;
    private final TextView textView;

    public VlagaTemperaturaHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        this.humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    public void onResume() {
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humiditySensor != null) {
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            textView.setText("Temperatura: " + event.values[0] + " °C");
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            textView.setText("Vlažnost: " + event.values[0] + " %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
