package com.example.kolokvijum2.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Folder: 74-thread-executor/ – Room operacije u pozadini */
public class ThreadExecutorHelper {

    private final Context context;
    private final PostDao postDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ThreadExecutorHelper(Context context, PostDao postDao) {
        this.context = context;
        this.postDao = postDao;
    }

    public void prikaziTitlePrvogAsync() {
        executor.execute(() -> {
            Post prvi = postDao.getFirst();
            mainHandler.post(() -> {
                if (prvi != null) {
                    Toast.makeText(context, prvi.getTitle(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void obrisiPrviAsync(PostRepository.OnEmptyListener onEmpty) {
        executor.execute(() -> {
            Post prvi = postDao.getFirst();
            if (prvi != null) {
                postDao.delete(prvi);
            }
            boolean empty = postDao.count() == 0;
            mainHandler.post(() -> {
                if (empty && onEmpty != null) {
                    onEmpty.onEmpty();
                }
            });
        });
    }

    public void ucitajSveAsync(Runnable onUiDone) {
        executor.execute(() -> {
            List<Post> lista = postDao.getAll();
            mainHandler.post(() -> {
                if (!lista.isEmpty()) {
                    Toast.makeText(context, lista.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }
                if (onUiDone != null) {
                    onUiDone.run();
                }
            });
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
