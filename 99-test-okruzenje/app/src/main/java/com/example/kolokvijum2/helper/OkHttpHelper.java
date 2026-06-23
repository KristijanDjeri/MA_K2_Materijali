package com.example.kolokvijum2.helper;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** Folder: 32-okhttp-json/ */
public class OkHttpHelper {

    public interface OnDoneListener {
        void onSuccess(int count);
        void onFailure(String message);
    }

    private final Context context;
    private final PostDao postDao;
    private final ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public OkHttpHelper(Context context, PostDao postDao, ProgressBar progressBar) {
        this.context = context;
        this.postDao = postDao;
        this.progressBar = progressBar;
    }

    public void ucitajPostove(String url, OnDoneListener listener) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    obavesti(listener, false, 0, "Neuspešan odgovor");
                    return;
                }
                String json = response.body().string();
                Gson gson = new Gson();
                Type tip = new TypeToken<List<Post>>() {}.getType();
                List<Post> svi = gson.fromJson(json, tip);
                int n = Math.min(10, svi.size());
                postDao.insertAll(svi.subList(0, n));
                obavesti(listener, true, n, null);
            } catch (IOException e) {
                obavesti(listener, false, 0, e.getMessage());
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    private void obavesti(OnDoneListener listener, boolean ok, int count, String err) {
        android.os.Handler handler = new android.os.Handler(context.getMainLooper());
        handler.post(() -> {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            if (listener != null) {
                if (ok) {
                    listener.onSuccess(count);
                } else {
                    listener.onFailure(err);
                }
            }
        });
    }
}
