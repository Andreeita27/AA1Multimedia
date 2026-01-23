package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.ProfessionalListContract;
import com.svalero.RosasTattoo.domain.Professional;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessionalListModel implements ProfessionalListContract.Model {

    @Override
    public void loadProfessionals(OnLoadProfessionalsListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.getProfessionals().enqueue(new Callback<List<Professional>>() {
            @Override
            public void onResponse(Call<List<Professional>> call, Response<List<Professional>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadSuccess(response.body());
                } else {
                    listener.onLoadError("No se ha podido cargar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<List<Professional>> call, Throwable t) {
                listener.onLoadError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void registerProfessional(Professional professional, OnOperationListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.registerProfessional(professional).enqueue(new Callback<Professional>() {
            @Override
            public void onResponse(Call<Professional> call, Response<Professional> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess("Profesional registrado correctamente");
                } else {
                    listener.onError("No se ha podido registrar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Professional> call, Throwable t) {
                listener.onError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void updateProfessional(long id, Professional professional, OnOperationListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.updateProfessional(id, professional).enqueue(new Callback<Professional>() {
            @Override
            public void onResponse(Call<Professional> call, Response<Professional> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess("Profesional actualizado correctamente");
                } else {
                    listener.onError("No se ha podido actualizar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Professional> call, Throwable t) {
                listener.onError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void deleteProfessional(long id, OnOperationListener listener) {
        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.deleteProfessional(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess("Profesional eliminado correctamente");
                } else {
                    listener.onError("No se ha podido eliminar (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError("No se ha podido conectar con el servidor");
            }
        });
    }
}
