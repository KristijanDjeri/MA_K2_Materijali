package com.example.kolokvijum2.helper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogHelper {

    public static void potvrdiBrisanje(AppCompatActivity activity, Runnable onPotvrda) {
        new AlertDialog.Builder(activity)
                .setTitle("Brisanje")
                .setMessage("Obrisati prvi post iz baze?")
                .setPositiveButton("Da", (d, w) -> onPotvrda.run())
                .setNegativeButton("Ne", null)
                .show();
    }

    public static void info(AppCompatActivity activity, String poruka) {
        new AlertDialog.Builder(activity)
                .setTitle("Obaveštenje")
                .setMessage(poruka)
                .setPositiveButton("OK", null)
                .show();
    }
}
