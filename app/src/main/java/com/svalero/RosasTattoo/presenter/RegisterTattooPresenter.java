package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.domain.Tattoo;
import com.svalero.RosasTattoo.model.RegisterTattooModel;

import java.util.List;

public class RegisterTattooPresenter implements RegisterTattooContract.Presenter {

    private final RegisterTattooContract.View view;
    private final RegisterTattooContract.Model model;

    public RegisterTattooPresenter(RegisterTattooContract.View view) {
        this.view = view;
        this.model = new RegisterTattooModel();
    }

    @Override
    public void registerTattoo(long clientId, long professionalId, String style, String description,
                               String imageUrl, Double latitude, Double longitude) {

        if (style == null || style.trim().isEmpty()) {
            view.showError("error_style_required");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            view.showError("error_description_required");
            return;
        }

        model.registerTattoo(
                clientId,
                professionalId,
                style,
                description,
                imageUrl,
                latitude,
                longitude,
                new RegisterTattooContract.Model.OnRegisterTattooListener() {
                    @Override
                    public void onRegisterTattooSuccess(String messageKey, Tattoo tattoo) {
                        view.showMessage(messageKey);
                        if (tattoo != null) {
                            view.onTattooRegistered(tattoo.getId());
                        } else {
                            view.clearForm();
                        }
                    }

                    @Override
                    public void onRegisterTattooError(String messageKey) {
                        view.showError(messageKey);
                    }
                }
        );
    }

    @Override
    public void loadClients() {
        model.loadClients(new RegisterTattooContract.Model.OnLoadClientsListener() {
            @Override
            public void onLoadClientsSuccess(List<Client> clients) {
                view.showClients(clients);
            }

            @Override
            public void onLoadClientsError(String messageKey) {
                view.showClientsError(messageKey);
            }
        });
    }

    @Override
    public void loadProfessionals() {
        model.loadProfessionals(new RegisterTattooContract.Model.OnLoadProfessionalsListener() {
            @Override
            public void onLoadProfessionalsSuccess(List<Professional> professionals) {
                view.showProfessionals(professionals);
            }

            @Override
            public void onLoadProfessionalsError(String messageKey) {
                view.showProfessionalsError(messageKey);
            }
        });
    }
}