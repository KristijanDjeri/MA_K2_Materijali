package com.example.kolokvijum2.helper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum2.adapter.PostAdapter;

/** Folder: 60-ui-recyclerview/ – lista postova iz PostRepository */
public class RecyclerViewPostsHelper {

    private final PostRepository postRepository;
    private final PostAdapter postAdapter;

    public RecyclerViewPostsHelper(RecyclerView recyclerView, PostRepository postRepository) {
        this.postRepository = postRepository;
        this.postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(postAdapter);
        osvezi();
    }

    public void osvezi() {
        postAdapter.setPosts(postRepository.getAll());
    }

    public PostAdapter getAdapter() {
        return postAdapter;
    }
}
