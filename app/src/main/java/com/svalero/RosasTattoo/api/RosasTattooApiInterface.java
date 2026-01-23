package com.svalero.RosasTattoo.api;

import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RosasTattooApiInterface {

    @GET("tattoos")
    Call<List<Tattoo>> getTattoos();

    @POST("clients")
    Call<Client> registerClient(@Body Client client);

    @GET("professionals")
    Call<List<Professional>> getProfessionals();

    @POST("professionals")
    Call<Professional> registerProfessional(@Body Professional professional);

    @PUT("professionals/{id}")
    Call<Professional> updateProfessional(@Path("id") long id, @Body Professional professional);

    @DELETE("professionals/{id}")
    Call<Void> deleteProfessional(@Path("id") long id);
    @POST("tattoos")
    Call<Tattoo> registerTattoo(@Body Tattoo tattoo);

    @DELETE("tattoos/{id}")
    Call<Void> deleteTattoo(@Path("id") long id);
}

