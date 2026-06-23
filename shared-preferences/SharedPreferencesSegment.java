// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.SharedPreferences;

// POLJE:
private SharedPreferences prefs;

// U onCreate():
prefs = getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE);

// METODA (poziva se iz switch listenera kada je isChecked == false):

private void obradiSwitchOff() {
    // 1. Sačuvaj TextView u SharedPreferences
    String trenutniTekst = textView.getText().toString();
    prefs.edit().putString("tekst", trenutniTekst).apply();

    // 2. Zameni TextView imenom prvog kontakta (folder kontakti/)
    postaviImePrvogKontakta();
}
