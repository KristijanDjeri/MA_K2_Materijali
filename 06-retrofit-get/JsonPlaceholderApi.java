package com.example.kolokvijum2.api;

import com.example.kolokvijum2.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    // Testirano: vraća JSON niz sa poljima id, title, body, userId
    @GET("posts")
    Call<List<Post>> getPosts();

    // URL sa kolokvijum PDF-a (Beeceptor) – vidi RetrofitClientAlternativa.java:
    // baseUrl: https://app.beeceptor.com/
    // @GET("mock-server/dummy-json")
}
