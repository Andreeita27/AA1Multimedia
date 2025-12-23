package com.svalero.RosasTattoo.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RosasTattooApi {

    public static RosasTattooApiInterface buildInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Emulador -> tu PC
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RosasTattooApiInterface.class);
    }
}

