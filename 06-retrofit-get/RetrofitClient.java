package com.example.kolokvijum2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Podrazumevano: stabilan javni API (radi i na emulatoru). Beeceptor → RetrofitClientAlternativa.
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
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
