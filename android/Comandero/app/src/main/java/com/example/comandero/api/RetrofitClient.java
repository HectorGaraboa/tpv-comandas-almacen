package com.example.comandero.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static String BASE_URL = "http://192.168.1.30:8080/";

    public static void setBaseUrl(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        BASE_URL = url;
        retrofit = null;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
