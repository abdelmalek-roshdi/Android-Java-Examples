package com.example.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
   @GET("/bins/cj2br")
    Call<List<UserModel>> geAllUsers();
}
