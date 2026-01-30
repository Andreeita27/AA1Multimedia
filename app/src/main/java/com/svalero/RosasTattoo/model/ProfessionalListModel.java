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
                    listener.onLoadError("error_load_professionals_http");
                }
            }

            @Override
            public void onFailure(Call<List<Professional>> call, Throwable t) {
                listener.onLoadError("error_server_connection");
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
                    listener.onSuccess("professional_registered");
                } else {
                    listener.onError("error_register_professional_http");
                }
            }

            @Override
            public void onFailure(Call<Professional> call, Throwable t) {
                listener.onError("error_server_connection");
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
                    listener.onSuccess("professional_updated");
                } else {
                    listener.onError("error_update_professional_http");
                }
            }

            @Override
            public void onFailure(Call<Professional> call, Throwable t) {
                listener.onError("error_server_connection");
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
                    listener.onSuccess("professional_deleted");
                } else {
                    listener.onError("error_delete_professional_http");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError("error_server_connection");
            }
        });
    }
}
