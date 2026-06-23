package com.example.kolokvijum2.api;

import com.example.kolokvijum2.model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceholderApiPost {

  // Primer za jsonplaceholder (test):
  // baseUrl: https://jsonplaceholder.typicode.com/
  @POST("posts")
  Call<Post> createPost(@Body Post post);
}
