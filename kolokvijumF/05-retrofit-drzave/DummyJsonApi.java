package com.example.kolokvijum2.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DummyJsonApi {

    @GET("countries")
    Call<ResponseBody> getCountriesRaw();
}
