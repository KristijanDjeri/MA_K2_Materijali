package com.example.kolokvijum2.helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** vežba F – folder 07-proksimitet/ */
public class ProksimitetHelper implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor proximitySensor;
    private final TextView textView;

    public ProksimitetHelper(AppCompatActivity activity, TextView textView) {
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    public void onResume() {
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            textView.setText("Proksimitet senzor nije dostupan");
        }
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float cm = event.values[0];
            if (cm < proximitySensor.getMaximumRange()) {
                textView.setText("Proksimitet: " + cm + " cm (blizu)");
            } else {
                textView.setText("Proksimitet: daleko");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
