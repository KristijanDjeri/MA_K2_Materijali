package com.example.kolokvijum2.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.db.CountryDao;
import com.example.kolokvijum2.model.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * vežba F – učitavanje država sa API-ja i rad sa bazom.
 */
public class CountryRepository {

    public interface OnDoneListener {
        void onSuccess(int count);
        void onFailure(String message);
    }

    private final Context context;
    private final CountryDao countryDao;

    public CountryRepository(Context context, CountryDao countryDao) {
        this.context = context;
        this.countryDao = countryDao;
    }

    public void ucitajDrzaveSaApi(OnDoneListener listener) {
        RetrofitClient.getApi().getCountriesRaw().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    notifyFailure(listener, "Neuspešan odgovor");
                    return;
                }
                try {
                    String raw = response.body().string();
                    // Beeceptor vraća nevalidan JSON: {name: 'RS', code: 'RS'}
                    String json = raw
                            .replace("{name:", "{\"name\":")
                            .replace(", code:", ", \"code\":")
                            .replace("'", "\"");
                    Type type = new TypeToken<List<Country>>() {}.getType();
                    List<Country> list = new Gson().fromJson(json, type);
                    if (list == null || list.isEmpty()) {
                        notifyFailure(listener, "Prazan odgovor");
                        return;
                    }
                    upisiDrzave(list, listener);
                } catch (IOException e) {
                    notifyFailure(listener, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyFailure(listener, t.getMessage());
            }
        });
    }

    /**
     * Rezerva ako Retrofit ne radi (proxy, DNS, firewall na fakultetu…).
     * Ručno unete države iz okruženja – bez mreže.
     */
    public void ucitajDrzaveLokalno(OnDoneListener listener) {
        List<Country> list = Arrays.asList(
                new Country("Srbija", "RS"),
                new Country("Crna Gora", "ME"),
                new Country("Bosna i Hercegovina", "BA"),
                new Country("Hrvatska", "HR"),
                new Country("Severna Makedonija", "MK"),
                new Country("Albanija", "AL"),
                new Country("Bugarska", "BG"),
                new Country("Rumunija", "RO"),
                new Country("Mađarska", "HU"),
                new Country("Slovenija", "SI")
        );
        upisiDrzave(list, listener);
    }

    private void upisiDrzave(List<Country> list, OnDoneListener listener) {
        countryDao.insertAll(list);
        int n = list.size();
        Toast.makeText(context, "Učitano država: " + n, Toast.LENGTH_SHORT).show();
        if (listener != null) {
            listener.onSuccess(n);
        }
    }

    public void obrisiPoslednjuDrzavu() {
        Country poslednja = countryDao.getLast();
        if (poslednja != null) {
            countryDao.delete(poslednja);
        }
        int ostalo = countryDao.count();
        Toast.makeText(context,
                "Ostalo država u bazi: " + ostalo, Toast.LENGTH_SHORT).show();
    }

    public int count() {
        return countryDao.count();
    }

    private void notifyFailure(OnDoneListener listener, String message) {
        Toast.makeText(context, "Greška: " + message, Toast.LENGTH_SHORT).show();
        if (listener != null) {
            listener.onFailure(message);
        }
    }
}
