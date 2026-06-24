package com.example.kolokvijum2.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Folder: 06-retrofit-get/ – samostalni GET test bez Room-a */
public class RetrofitGetHelper {

    public interface Listener {
        void onSuccess(int count);
        void onFailure(String message);
    }

    public static void testGet(Context context, Listener listener) {
        RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int n = response.body().size();
                    if (listener != null) {
                        listener.onSuccess(n);
                    } else {
                        Toast.makeText(context, "API vratio " + n + " postova", Toast.LENGTH_SHORT).show();
                    }
                } else if (listener != null) {
                    listener.onFailure("Neuspešan odgovor");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure(t.getMessage());
                } else {
                    Toast.makeText(context, "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
