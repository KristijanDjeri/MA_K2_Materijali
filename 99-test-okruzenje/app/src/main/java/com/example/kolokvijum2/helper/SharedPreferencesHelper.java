package com.example.kolokvijum2.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class SharedPreferencesHelper {

    private static final String PREFS_NAME = "kolokvijum_prefs";
    private static final String KEY_TEKST = "tekst";

    private final SharedPreferences prefs;

    public SharedPreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void sacuvajTextView(TextView textView) {
        prefs.edit().putString(KEY_TEKST, textView.getText().toString()).apply();
    }

    public String getSacuvaniTekst() {
        return prefs.getString(KEY_TEKST, "");
    }
}
