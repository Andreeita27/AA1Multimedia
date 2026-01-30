package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTattooModel implements RegisterTattooContract.Model {

    @Override
    public void registerTattoo(long clientId, long professionalId, String style, String description,
                               String imageUrl, Double latitude, Double longitude,
                               OnRegisterTattooListener listener) {

        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Tattoo tattoo = Tattoo.builder()
                .clientId(clientId)
                .professionalId(professionalId)
                .tattooDate(today)
                .style(style)
                .tattooDescription(description)
                .imageUrl(imageUrl)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        Call<Tattoo> call = apiInterface.registerTattoo(tattoo);

        call.enqueue(new Callback<Tattoo>() {
            @Override
            public void onResponse(Call<Tattoo> call, Response<Tattoo> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterTattooSuccess("tattoo_registered", response.body());
                } else {
                    listener.onRegisterTattooError("error_register_tattoo_http");
                }
            }

            @Override
            public void onFailure(Call<Tattoo> call, Throwable t) {
                listener.onRegisterTattooError("error_server_connection");
            }
        });
    }

    @Override
    public void loadClients(OnLoadClientsListener listener) {
        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();
        Call<List<Client>> call = apiInterface.getClients();

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadClientsSuccess(response.body());
                } else {
                    listener.onLoadClientsError("error_load_clients");
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                listener.onLoadClientsError("error_server_connection");
            }
        });
    }

    @Override
    public void loadProfessionals(OnLoadProfessionalsListener listener) {
        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();
        Call<List<Professional>> call = apiInterface.getProfessionals();

        call.enqueue(new Callback<List<Professional>>() {
            @Override
            public void onResponse(Call<List<Professional>> call, Response<List<Professional>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadProfessionalsSuccess(response.body());
                } else {
                    listener.onLoadProfessionalsError("error_load_professionals");
                }
            }

            @Override
            public void onFailure(Call<List<Professional>> call, Throwable t) {
                listener.onLoadProfessionalsError("error_server_connection");
            }
        });
    }
}