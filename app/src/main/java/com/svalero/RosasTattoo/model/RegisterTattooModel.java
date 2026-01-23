package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.domain.Tattoo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTattooModel implements RegisterTattooContract.Model {

    @Override
    public void registerTattoo(String style, String description, String imageUrl, OnRegisterTattooListener listener) {

        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();

        Tattoo tattoo = Tattoo.builder()
                .style(style)
                .tattooDescription(description)
                .imageUrl(imageUrl)
                .build();

        Call<Tattoo> call = apiInterface.registerTattoo(tattoo);

        call.enqueue(new Callback<Tattoo>() {

            @Override
            public void onResponse(Call<Tattoo> call, Response<Tattoo> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterTattooSuccess("Tatuaje registrado correctamente");
                } else {
                    String error = "HTTP " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            error += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}

                    listener.onRegisterTattooError("No se ha podido registrar el tatuaje: " + error);
                }
            }

            @Override
            public void onFailure(Call<Tattoo> call, Throwable t) {
                listener.onRegisterTattooError("No se ha podido conectar con el servidor");
            }
        });
    }
}