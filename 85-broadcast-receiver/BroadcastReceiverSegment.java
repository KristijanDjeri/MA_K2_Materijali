// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.Intent;
import com.example.kolokvijum2.PostUpdateReceiver;

// METODA – pošalji broadcast posle učitavanja postova u bazu:
private void obavestiDaSuPostoviUcitani() {
    Intent intent = new Intent(PostUpdateReceiver.ACTION_POSTS_UPDATED);
    intent.setPackage(getPackageName()); // obavezno od API 26 za manifest receiver
    sendBroadcast(intent);
}

// Pozovi na kraju metode koja upisuje postove (npr. posle postDao.insertAll):
// obavestiDaSuPostoviUcitani();
