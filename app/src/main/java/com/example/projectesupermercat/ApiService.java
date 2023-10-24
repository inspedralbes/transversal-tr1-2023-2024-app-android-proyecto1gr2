package com.example.projectesupermercat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    Call<Usuari> login(@Body Usuari usuari);
}
