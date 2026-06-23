// === DODAJ U MainActivity.java ===
// Priručnik: 33-povratna-vibracija/README.md

// IMPORTI:
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

// METODA:
private void povratnaVibracijaKratka() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        VibratorManager vm = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        vm.getDefaultVibrator().vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }
    }
}

// Pozovi npr. kad nema više postova:
// povratnaVibracijaKratka();
