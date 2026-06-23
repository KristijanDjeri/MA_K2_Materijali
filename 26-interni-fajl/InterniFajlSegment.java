// === DODAJ U MainActivity.java ===

// IMPORTI:
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// METODE:

private void sacuvajUTxtFajl(String tekst) {
    try (FileOutputStream fos = openFileOutput("podaci.txt", MODE_PRIVATE)) {
        fos.write(tekst.getBytes(StandardCharsets.UTF_8));
        Toast.makeText(this, "Sačuvano u fajl", Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        Toast.makeText(this, "Greška pisanja", Toast.LENGTH_SHORT).show();
    }
}

private String ucitajIzTxtFajla() {
    StringBuilder sb = new StringBuilder();
    try (FileInputStream fis = openFileInput("podaci.txt");
         BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
        String linija;
        while ((linija = reader.readLine()) != null) {
            sb.append(linija);
        }
    } catch (Exception e) {
        return "";
    }
    return sb.toString();
}

// Primer u onCreate:
// String sacuvano = ucitajIzTxtFajla();
// if (!sacuvano.isEmpty()) textView.setText(sacuvano);

// Primer pri Switch OFF (umesto ili pored SharedPreferences):
// sacuvajUTxtFajl(textView.getText().toString());
