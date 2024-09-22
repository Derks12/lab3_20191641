package com.example.lab3_20191641.services;

import com.example.lab3_20191641.dto.Perfil;
import com.example.lab3_20191641.dto.Tareas;
import com.example.lab3_20191641.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface retrofit1 {

    @FormUrlEncoded
    @POST("login")
    Call<User> LOGIN_CALL(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("logout")
    Call<User> LOGOUT_CALL(
            @Field("id") int id
    );


    @GET("users/{id}")
    Call<Perfil> getPerfilDetails(@Path("id") int id);

    @GET("todos/user/{id}")
    Call<List<Tareas>> getTareas(@Path("id") int userId);


}