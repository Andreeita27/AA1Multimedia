package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.TattooDetailContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TattooDetailModel implements TattooDetailContract.Model {

    @Override
    public void deleteTattoo(long tattooId, OnDeleteTattooListener listener) {

        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();
        Call<Void> call = apiInterface.deleteTattoo(tattooId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onDeleteTattooSuccess("Tatuaje eliminado correctamente");
                } else {
                    listener.onDeleteTattooError("No se ha podido eliminar el tatuaje");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteTattooError("No se ha podido conectar con el servidor");
            }
        });
    }
}
