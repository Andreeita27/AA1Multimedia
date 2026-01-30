package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.TattooDetailContract;
import com.svalero.RosasTattoo.domain.Tattoo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TattooDetailModel implements TattooDetailContract.Model {

    @Override
    public void deleteTattoo(long id, OnDeleteTattooListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.deleteTattoo(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onDeleteTattooSuccess("tattoo_deleted");
                } else {
                    listener.onDeleteTattooError("error_delete_tattoo_http");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteTattooError("error_server_connection");
            }
        });
    }

    @Override
    public void updateTattoo(long id, Tattoo tattoo, OnUpdateTattooListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.updateTattoo(id, tattoo).enqueue(new Callback<Tattoo>() {
            @Override
            public void onResponse(Call<Tattoo> call, Response<Tattoo> response) {
                if (response.isSuccessful()) {
                    listener.onUpdateTattooSuccess("tattoo_updated");
                } else {
                    listener.onUpdateTattooError("error_update_tattoo_http");
                }
            }

            @Override
            public void onFailure(Call<Tattoo> call, Throwable t) {
                listener.onUpdateTattooError("error_server_connection");
            }
        });
    }
}