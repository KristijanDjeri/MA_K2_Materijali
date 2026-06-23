// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// POLJA:
private final ExecutorService executor = Executors.newSingleThreadExecutor();
private final Handler mainHandler = new Handler(Looper.getMainLooper());

// U onDestroy():
// executor.shutdown();

// PRIMER – učitaj postove u pozadini:
private void ucitajPostoveIzBazeUI() {
    executor.execute(() -> {
        List<Post> lista = postDao.getAll();
        mainHandler.post(() -> {
            if (!lista.isEmpty()) {
                Toast.makeText(this, lista.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
            // postAdapter.setPosts(lista);
        });
    });
}

// PRIMER – brisanje u pozadini:
private void obrisiPrviPostAsync() {
    executor.execute(() -> {
        Post prvi = postDao.getFirst();
        if (prvi != null) postDao.delete(prvi);
        int count = postDao.count();
        mainHandler.post(() -> {
            if (count == 0) {
                posaljiNotifikaciju("Nema više postova!");
            }
        });
    });
}
