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
                    listener.onDeleteTattooSuccess("Tatuaje eliminado correctamente");
                } else {
                    listener.onDeleteTattooError("No se ha podido eliminar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteTattooError("No se ha podido conectar con el servidor");
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
                    listener.onUpdateTattooSuccess("Tatuaje actualizado correctamente");
                } else {
                    listener.onUpdateTattooError("No se ha podido actualizar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Tattoo> call, Throwable t) {
                listener.onUpdateTattooError("No se ha podido conectar con el servidor");
            }
        });
    }
}