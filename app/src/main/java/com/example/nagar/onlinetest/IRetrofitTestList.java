package com.example.nagar.onlinetest;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IRetrofitTestList {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("ftest/")
    Call<List<FacultyTestQuesList>> postRawJSON(@Body JsonObject locationPost);
}
