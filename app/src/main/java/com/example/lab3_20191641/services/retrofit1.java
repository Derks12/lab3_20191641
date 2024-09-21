package com.example.lab3_20191641.services;

import com.example.lab3_20191641.dto.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface retrofit1 {

    @FormUrlEncoded
    @POST("login")
    Call<User> LOGIN_CALL(
            @Field("username") String username,
            @Field("password") String password
    );
}