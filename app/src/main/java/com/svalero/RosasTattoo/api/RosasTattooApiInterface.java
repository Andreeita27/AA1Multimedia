package com.svalero.RosasTattoo.api;

import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RosasTattooApiInterface {

    @GET("tattoos")
    Call<List<Tattoo>> getTattoos();
}

