// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.view.View;
import android.widget.ProgressBar;

// POLJE:
private ProgressBar progressBar;

// U onCreate():
progressBar = findViewById(R.id.progressBar);

// U Retrofit pozivu:
private void ucitajPostoveSaProgressom() {
    progressBar.setVisibility(View.VISIBLE);

    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            progressBar.setVisibility(View.GONE);
            // ... ostatak logike
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
        }
    });
}
