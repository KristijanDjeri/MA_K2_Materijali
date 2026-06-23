// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.Intent;

// METODA (npr. dugme ili dugi klik na post):
private void otvoriDetalj(String naslov) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra("naslov", naslov);
    startActivity(intent);
}

// Primer poziva:
// Post prvi = postDao.getFirst();
// if (prvi != null) otvoriDetalj(prvi.getTitle());
