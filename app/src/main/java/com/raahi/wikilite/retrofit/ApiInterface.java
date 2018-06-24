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
    /*@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("read/api/v2.0/signup")
    Call<CommonResponse> signUp(@Field("countryCode") String countryCode, @Field("mobile") String mobileNumber);*/

    @Headers("Content-Type: application/json")
    @GET("api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description")
    Call<ResultData> getSearchData(@QueryMap Map<String, String> params);

    /*@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("write/api/v2.0/insert_feedback")
    Call<ErrorCode> insertFeedBack(@Field("name") String name, @Field("email") String email, @Field("issue") String issue, @Field("imageUrl") String imageURL, @Field("helpTypeId") int helpTypeId);

    //http://dev-apiv2.penmyplan.com/write/api/v2.0/signup
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("write/api/v2.0/signup")
    Call<LoginResponse> signUp(@Body SignUpData signUpData);

    @Headers("Content-Type: application/json")
    @GET("read/api/v2.0/get_home_page/1")
    Call<ExploreObject> getExploreData();

    @Headers("Content-Type: application/json")
    @POST("vendor/read/api/v2.0/search_hotel_name/")
    Call<HotelDataObject> getHotelList(@Body HotelQueryData hotelQueryData);*/
}
