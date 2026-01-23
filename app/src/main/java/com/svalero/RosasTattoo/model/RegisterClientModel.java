package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.RegisterClientContract;
import com.svalero.RosasTattoo.domain.Client;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterClientModel implements RegisterClientContract.Model {

    @Override
    public void registerClient(String clientName, String clientSurname, String email, String phone,
                               String birthDate, boolean showPhoto,
                               OnRegisterClientListener listener) {

        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();

        Client client = Client.builder()
                .clientName(clientName)
                .clientSurname(clientSurname)
                .email(email)
                .phone(phone)
                .birthDate(birthDate == null || birthDate.isEmpty() ? null : birthDate)
                .showPhoto(showPhoto)
                .build();

        Call<Client> call = apiInterface.registerClient(client);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterClientSuccess("Cliente registrado correctamente");
                } else {
                    listener.onRegisterClientError("No se ha podido registrar el cliente (HTTP " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                listener.onRegisterClientError("No se ha podido conectar con el servidor");
            }
        });
    }
}