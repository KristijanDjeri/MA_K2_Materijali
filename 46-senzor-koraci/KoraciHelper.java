package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/** Folder: 46-senzor-koraci/ */
public class KoraciHelper implements SensorEventListener {

    public static final int REQ_ACTIVITY = 107;

    private final AppCompatActivity activity;
    private final SensorManager sensorManager;
    private final Sensor stepCounterSensor;
    private final TextView textView;
    private boolean registrovan = false;

    public KoraciHelper(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
        this.sensorManager = (SensorManager) activity.getSystemService(AppCompatActivity.SENSOR_SERVICE);
        this.stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public void onResume() {
        if (stepCounterSensor == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQ_ACTIVITY);
                return;
            }
        }
        registruj();
    }

    public void onPause() {
        if (registrovan) {
            sensorManager.unregisterListener(this);
            registrovan = false;
        }
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_ACTIVITY
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registruj();
        }
    }

    private void registruj() {
        if (stepCounterSensor != null && !registrovan) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            registrovan = true;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            textView.setText("Koraci: " + (int) event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
