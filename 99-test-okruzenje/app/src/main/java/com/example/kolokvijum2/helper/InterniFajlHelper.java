package com.example.kolokvijum2.helper;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InterniFajlHelper {

    private static final String IME_FAJLA = "podaci.txt";

    public static void sacuvaj(Context context, String tekst) {
        try (FileOutputStream fos = context.openFileOutput(IME_FAJLA, Context.MODE_PRIVATE)) {
            fos.write(tekst.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(context, "Sačuvano u fajl", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Greška pisanja", Toast.LENGTH_SHORT).show();
        }
    }

    public static String ucitaj(Context context) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(IME_FAJLA);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }
}
