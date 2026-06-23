package com.example.kolokvijum2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Alternativa: mock sa kolokvijuma (Beeceptor).
 * U JsonPlaceholderApi ostaje: @GET("posts")
 * Ako dobiješ "Unable to resolve host" na emulatoru, koristi glavni RetrofitClient (typicode).
 */
public class RetrofitClientAlternativa {

    private static final String BASE_URL = "https://dummy-json.mock.beeceptor.com/";
    private static Retrofit retrofit;

    public static JsonPlaceholderApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(JsonPlaceholderApi.class);
    }
}
