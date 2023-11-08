package com.example.projectesupermercat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    Call<Usuari> login(@Body Usuari usuari);

    @GET("/consultarProductes")
    Call<List<Producte>> getProductes();

    @POST("/addComandes")
    Call<Void> enviarComanda(@Body Comanda comanda);

    @POST("/registrarUsuari")
    Call<Void> registrarUsuari(@Body Usuari usuari);

    @POST("/getComandes")
    Call<List<Comanda>> rebreComandes(@Body Usuari usuari);

    @GET("/consultarCategories")
    Call<List<Categoria>> getCategories();
    @POST("/actualitzarUsuari")
    Call<Void> updateUsuari(@Body Usuari data);
}
