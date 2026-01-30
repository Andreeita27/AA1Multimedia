package com.svalero.RosasTattoo.model;

import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.contract.RegisterProfessionalContract;
import com.svalero.RosasTattoo.domain.Professional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProfessionalModel implements RegisterProfessionalContract.Model {

    @Override
    public void registerProfessional(String professionalName,
                                     String birthDate,
                                     String description,
                                     String profilePhoto,
                                     int yearsExperience,
                                     boolean booksOpened,
                                     OnRegisterProfessionalListener listener) {

        RosasTattooApiInterface apiInterface = RosasTattooApi.buildInstance();

        Professional professional = Professional.builder()
                .professionalName(professionalName)
                .birthDate(birthDate == null || birthDate.isEmpty() ? null : birthDate)
                .description(description)
                .profilePhoto(profilePhoto == null || profilePhoto.isEmpty() ? null : profilePhoto)
                .yearsExperience(yearsExperience)
                .booksOpened(booksOpened)
                .build();

        Call<Professional> call = apiInterface.registerProfessional(professional);

        call.enqueue(new Callback<Professional>() {
            @Override
            public void onResponse(Call<Professional> call, Response<Professional> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterProfessionalSuccess(
                            "professional_registered",
                            response.body()
                    );
                } else {
                    listener.onRegisterProfessionalError(
                            "error_register_professional_http"
                    );
                }
            }

            @Override
            public void onFailure(Call<Professional> call, Throwable t) {
                listener.onRegisterProfessionalError("error_server_connection");
            }
        });
    }
}