package com.example.kolokvijum2.helper;

import android.content.Context;
import android.text.TextUtils;
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

    public List<Post> getAll() {
        return postDao.getAll();
    }

    public void insertPosts(List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return;
        }
        int n = Math.min(10, posts.size());
        postDao.insertAll(posts.subList(0, n));
        postsUcitani = true;
    }

    public void setPostsUcitani(boolean postsUcitani) {
        this.postsUcitani = postsUcitani;
    }

    /** Validacija + insert – za 62-ui-edit-text-validacija */
    public boolean dodajIzNaslova(String naslov) {
        String trimmed = naslov != null ? naslov.trim() : "";
        if (TextUtils.isEmpty(trimmed)) {
            Toast.makeText(context, "Naslov ne sme biti prazan!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (trimmed.length() < 3) {
            Toast.makeText(context, "Naslov mora imati bar 3 karaktera", Toast.LENGTH_SHORT).show();
            return false;
        }
        Post post = new Post((int) System.currentTimeMillis(), trimmed, "", 1);
        postDao.insert(post);
        Toast.makeText(context, "Post dodat", Toast.LENGTH_SHORT).show();
        return true;
    }

    /** Retrofit POST – za 72-retrofit-post */
    public void posaljiPostNaServer(OnApiDoneListener listener) {
        Post novi = new Post(0, "Naslov sa uređaja", "Tekst posta", 1);
        RetrofitClient.getApi().createPost(novi).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        postDao.insert(response.body());
                    }
                    if (listener != null) {
                        listener.onSuccess(1);
                    }
                } else if (listener != null) {
                    listener.onFailure("Neuspešan POST");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}
