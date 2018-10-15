package com.androidtraining.randomuserlist.api;

import com.androidtraining.randomuserlist.modules.RandomResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomAPI {
    //https://randomuser.me/api?results={count}
    @GET("api")
    Call<RandomResponse> getRandomUsers(@Query("results") int count);

}