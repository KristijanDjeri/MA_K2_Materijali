// === DODAJ U MainActivity.java ===

// IMPORTI:
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// METODA:
private void posaljiPostNaServer() {
    Post novi = new Post(0, "Naslov sa uređaja", "Tekst posta", 1);

    RetrofitClient.getApi().createPost(novi).enqueue(new Callback<Post>() {
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if (response.isSuccessful()) {
                Toast.makeText(MainActivity.this, "POST uspešan", Toast.LENGTH_SHORT).show();
                // opciono: upiši i u lokalnu bazu
                // postDao.insert(response.body());
            }
        }

        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            Toast.makeText(MainActivity.this, "POST greška", Toast.LENGTH_SHORT).show();
        }
    });
}

// U JsonPlaceholderApi dodaj:
// @POST("posts")
// Call<Post> createPost(@Body Post post);
