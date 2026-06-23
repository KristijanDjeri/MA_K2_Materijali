package com.example.kolokvijum2.api;

import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    // Za kolokvijum URL:
    @GET("mock-server/dummy-json")
    Call<List<Post>> getPosts();

    // Alternativa (sa RetrofitClientAlternativa):
    // @GET("posts")
    // Call<List<Post>> getPosts();
}
