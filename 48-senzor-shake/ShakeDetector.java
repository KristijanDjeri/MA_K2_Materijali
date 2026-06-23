package com.example.kolokvijum2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

/**
 * Detekcija tresenja preko akcelerometra.
 * Kompletno uputstvo: README.md u folderu shake-senzor/
 */
public class ShakeDetector {

    public interface OnShakeListener {
        void onShake();
    }

    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int SHAKE_COOLDOWN_MS = 1000;

    private final OnShakeListener listener;
    private long poslednjiShakeVreme = 0;

    public ShakeDetector(OnShakeListener listener) {
        this.listener = listener;
    }

    public void obradiSensorEvent(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float ubrzanje = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = Math.abs(ubrzanje - SensorManager.GRAVITY_EARTH);

        if (delta > SHAKE_THRESHOLD) {
            long sada = System.currentTimeMillis();
            if (sada - poslednjiShakeVreme > SHAKE_COOLDOWN_MS) {
                poslednjiShakeVreme = sada;
                listener.onShake();
            }
        }
    }
}
