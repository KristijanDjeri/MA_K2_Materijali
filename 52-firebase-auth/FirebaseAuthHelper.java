package com.example.kolokvijum2.helper;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** Folder: 52-firebase-auth/ */
public class FirebaseAuthHelper {

    private final AppCompatActivity activity;
    private final FirebaseAuth firebaseAuth;
    private final EditText editEmail;
    private final EditText editLozinka;

    public FirebaseAuthHelper(AppCompatActivity activity,
                              EditText editEmail,
                              EditText editLozinka,
                              Button btnRegistracija,
                              Button btnPrijava) {
        this.activity = activity;
        this.editEmail = editEmail;
        this.editLozinka = editLozinka;
        this.firebaseAuth = FirebaseAuth.getInstance();

        btnRegistracija.setOnClickListener(v -> registracija());
        btnPrijava.setOnClickListener(v -> prijava());

        FirebaseUser trenutni = firebaseAuth.getCurrentUser();
        if (trenutni != null) {
            Toast.makeText(activity, "Ulogovan: " + trenutni.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    public void registracija() {
        String email = editEmail.getText().toString().trim();
        String lozinka = editLozinka.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
            Toast.makeText(activity, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, lozinka)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Registracija uspešna", Toast.LENGTH_SHORT).show();
                    } else {
                        String greska = task.getException() != null
                                ? task.getException().getMessage() : "Greška";
                        Toast.makeText(activity, greska, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void prijava() {
        String email = editEmail.getText().toString().trim();
        String lozinka = editLozinka.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
            Toast.makeText(activity, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, lozinka)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Prijava uspešna", Toast.LENGTH_SHORT).show();
                    } else {
                        String greska = task.getException() != null
                                ? task.getException().getMessage() : "Greška";
                        Toast.makeText(activity, greska, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void odjava() {
        firebaseAuth.signOut();
        Toast.makeText(activity, "Odjavljen", Toast.LENGTH_SHORT).show();
    }
}
