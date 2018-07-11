package com.daniel.test_reign.core.retrofit.methods;

import com.daniel.test_reign.core.models.HomeObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMethods {

    @GET("search_by_date?query=android")
    Call<HomeObject> getData();

}
