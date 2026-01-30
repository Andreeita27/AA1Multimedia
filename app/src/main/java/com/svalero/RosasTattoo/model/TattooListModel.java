package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.TattooListContract;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TattooListModel implements TattooListContract.Model {

    @Override
    public void loadTattoos(OnLoadTattoosListener listener) {
        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();
        Call<List<Tattoo>> call = apiInterface.getTattoos();

        call.enqueue(new Callback<List<Tattoo>>() {
            @Override
            public void onResponse(Call<List<Tattoo>> call, Response<List<Tattoo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadTattoosSuccess(response.body());
                } else {
                    listener.onLoadTattoosError("error_load_tattoos");
                }
            }

            @Override
            public void onFailure(Call<List<Tattoo>> call, Throwable t) {
                listener.onLoadTattoosError("error_server_connection");
            }
        });
    }
}