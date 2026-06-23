package com.example.kolokvijum2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Alternativa ako URL sa kolokvijuma ne vrati JSON.
 * U JsonPlaceholderApi stavi: @GET("posts")
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
