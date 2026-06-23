// === DODAJ U MainActivity.java ===

// IMPORTI:
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.lang.reflect.Type;
import java.util.List;

// U pozadinskoj niti (executor):
private void ucitajJsonOkHttp() {
    executor.execute(() -> {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://dummy-json.mock.beeceptor.com/posts")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type tip = new TypeToken<List<Post>>() {}.getType();
                    List<Post> postovi = gson.fromJson(json, tip);

                    mainHandler.post(() -> {
                        int n = Math.min(10, postovi.size());
                        postDao.insertAll(postovi.subList(0, n));
                        Toast.makeText(this, "Učitano OkHttp", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        } catch (Exception e) {
            mainHandler.post(() ->
                    Toast.makeText(this, "Greška", Toast.LENGTH_SHORT).show());
        }
    });
}
