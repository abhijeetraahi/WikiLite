package com.raahi.wikilite.retrofit;

import com.raahi.wikilite.model.ResultData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by Raahi on 24-06-2018.
 */

public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @GET("api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description")
    Call<ResultData> getSearchData(@QueryMap Map<String, String> params);
}
