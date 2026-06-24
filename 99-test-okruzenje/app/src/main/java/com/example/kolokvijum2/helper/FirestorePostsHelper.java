package com.example.kolokvijum2.helper;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.example.kolokvijum2.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Folder: 53-firebase-firestore/ – zamena za Retrofit + Room (opciono hibrid) */
public class FirestorePostsHelper {

    public interface OnCountListener {
        void onSuccess(int count);
        void onFailure(String message);
    }

    public interface OnTitleListener {
        void onTitle(String title);
        void onEmpty();
        void onFailure(String message);
    }

    public interface OnEmptyListener {
        void onEmpty();
    }

    private final AppCompatActivity activity;
    private final FirebaseFirestore firestore;
    private final PostRepository postRepository;
    private boolean postsUcitani = false;

    public FirestorePostsHelper(AppCompatActivity activity, PostRepository postRepository) {
        this.activity = activity;
        this.postRepository = postRepository;
        this.firestore = FirebaseFirestore.getInstance();
    }

    public boolean isPostsUcitani() {
        return postsUcitani;
    }

    public void ucitajPostove(OnCountListener listener) {
        firestore.collection("posts")
                .limit(10)
                .get()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Post> lista = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Post p = doc.toObject(Post.class);
                            if (p != null) {
                                lista.add(p);
                            }
                        }
                        postRepository.insertPosts(lista);
                        postsUcitani = true;
                        if (listener != null) {
                            listener.onSuccess(lista.size());
                        }
                    } else if (listener != null) {
                        listener.onFailure("Firestore greška");
                    }
                });
    }

    public void prikaziTitlePrvog(OnTitleListener listener) {
        firestore.collection("posts")
                .limit(1)
                .get()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        QueryDocumentSnapshot doc = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                        String title = doc.getString("title");
                        if (listener != null) {
                            listener.onTitle(title != null ? title : "");
                        } else {
                            Toast.makeText(activity, title, Toast.LENGTH_SHORT).show();
                        }
                    } else if (listener != null) {
                        listener.onEmpty();
                    }
                });
    }

    public void obrisiPrvi(OnEmptyListener onEmpty) {
        firestore.collection("posts")
                .limit(1)
                .get()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        String docId = task.getResult().getDocuments().get(0).getId();
                        firestore.collection("posts").document(docId)
                                .delete()
                                .addOnSuccessListener(v -> proveriPraznu(onEmpty));
                    }
                });
    }

    private void proveriPraznu(OnEmptyListener onEmpty) {
        firestore.collection("posts")
                .limit(1)
                .get()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful() && (task.getResult() == null || task.getResult().isEmpty())) {
                        if (onEmpty != null) {
                            onEmpty.onEmpty();
                        }
                    }
                });
    }

    public void dodajPost(String naslov, String body, int userId) {
        Map<String, Object> post = new HashMap<>();
        post.put("id", System.currentTimeMillis());
        post.put("title", naslov);
        post.put("body", body);
        post.put("userId", userId);

        firestore.collection("posts")
                .add(post)
                .addOnSuccessListener(ref ->
                        Toast.makeText(activity, "Post dodat: " + ref.getId(), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
