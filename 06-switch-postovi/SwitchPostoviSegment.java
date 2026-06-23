// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// POLJA:
private Switch switchPosts;
private PostDao postDao;
private boolean postsUcitani = false;

// U onCreate(), posle inicijalizacije baze:
AppDatabase db = AppDatabase.getInstance(this);
postDao = db.postDao();

switchPosts.setOnCheckedChangeListener((buttonView, isChecked) -> {
  if (isChecked) {
    obradiSwitchOn();
  } else {
    obradiSwitchOff(); // iz foldera 09-shared-preferences + 10-kontakti
  }
});

// METODE:

private void obradiSwitchOn() {
    if (!postsUcitani) {
        ucitajPostoveSaApi();
    } else {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
        }
    }
}

private void ucitajPostoveSaApi() {
    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Post> svi = response.body();
                int n = Math.min(10, svi.size());
                postDao.insertAll(svi.subList(0, n));
                postsUcitani = true;
                Toast.makeText(MainActivity.this,
                        "Učitano " + n + " postova", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

// obradiSwitchOff() definisan u 09-shared-preferences/KontaktiSegment kombinaciji
