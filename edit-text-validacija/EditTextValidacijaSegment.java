// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.text.TextUtils;
import android.widget.EditText;

// POLJE:
private EditText editTextNaslov;

// U onCreate():
editTextNaslov = findViewById(R.id.editTextNaslov);

// METODA:
private void dodajPostIzUnosa() {
    String naslov = editTextNaslov.getText().toString().trim();

    if (TextUtils.isEmpty(naslov)) {
        Toast.makeText(this, "Naslov ne sme biti prazan!", Toast.LENGTH_SHORT).show();
        return;
    }

    if (naslov.length() < 3) {
        Toast.makeText(this, "Naslov mora imati bar 3 karaktera", Toast.LENGTH_SHORT).show();
        return;
    }

    Post post = new Post((int) System.currentTimeMillis(), naslov, "", 1);
    postDao.insert(post);
  editTextNaslov.setText("");
    Toast.makeText(this, "Post dodat", Toast.LENGTH_SHORT).show();
}
