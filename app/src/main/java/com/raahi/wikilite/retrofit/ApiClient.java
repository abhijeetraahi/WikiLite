package com.raahi.wikilite.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Raahi on 24-06-2018.
 */

public class ApiClient {
    //http://en.wikipedia.org/w/api.php?action=opensearch&search="+newText+"&limit=8&namespace=0&format=json", "GET", null, null
    public static final String BASE_URL = "https://en.wikipedia.org//w/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
