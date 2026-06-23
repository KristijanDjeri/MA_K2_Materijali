package com.example.kolokvijum2.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    public interface OnApiDoneListener {
        void onSuccess(int count);
        void onFailure(String message);
    }

    public interface OnEmptyListener {
        void onEmpty();
    }

    private final Context context;
    private final PostDao postDao;
    private boolean postsUcitani = false;

    public PostRepository(Context context, PostDao postDao) {
        this.context = context;
        this.postDao = postDao;
    }

    public boolean isPostsUcitani() {
        return postsUcitani;
    }

    public void ucitajPostoveSaApi(OnApiDoneListener listener) {
        RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> svi = response.body();
                    int n = Math.min(10, svi.size());
                    postDao.insertAll(svi.subList(0, n));
                    postsUcitani = true;
                    if (listener != null) {
                        listener.onSuccess(n);
                    }
                } else if (listener != null) {
                    listener.onFailure("Neuspešan odgovor");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }

    public void prikaziTitlePrvogPosta() {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            Toast.makeText(context, prvi.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
        }
    }

    public void obrisiPrviPost(OnEmptyListener onEmpty) {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            postDao.delete(prvi);
        }
        if (postDao.count() == 0 && onEmpty != null) {
            onEmpty.onEmpty();
        }
    }

    public void izmeniTitlePrvogPosta(String noviTitle) {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            prvi.setTitle(noviTitle);
            postDao.update(prvi);
            Toast.makeText(context, "Ažurirano: " + noviTitle, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nema postova", Toast.LENGTH_SHORT).show();
        }
    }

    public Post getFirst() {
        return postDao.getFirst();
    }

    public int count() {
        return postDao.count();
    }
}
