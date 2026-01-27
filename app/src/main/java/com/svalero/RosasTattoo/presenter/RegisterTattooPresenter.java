package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.model.RegisterTattooModel;

import java.util.List;

public class RegisterTattooPresenter implements RegisterTattooContract.Presenter {

    private RegisterTattooContract.View view;
    private RegisterTattooContract.Model model;

    public RegisterTattooPresenter(RegisterTattooContract.View view) {
        this.view = view;
        this.model = new RegisterTattooModel();
    }

    @Override
    public void registerTattoo(long clientId, long professionalId, String style, String description, String imageUrl) {
        if (style == null || style.trim().isEmpty()) {
            view.showError("El estilo es obligatorio");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            view.showError("La descripción es obligatoria");
            return;
        }

        model.registerTattoo(clientId, professionalId, style, description, imageUrl,
                new RegisterTattooContract.Model.OnRegisterTattooListener() {
                    @Override
                    public void onRegisterTattooSuccess(String message) {
                        view.showMessage(message);
                        view.clearForm();
                    }

                    @Override
                    public void onRegisterTattooError(String message) {
                        view.showError(message);
                    }
                });
    }

    @Override
    public void loadClients() {
        model.loadClients(new RegisterTattooContract.Model.OnLoadClientsListener() {
            @Override
            public void onLoadClientsSuccess(List<Client> clients) {
                view.showClients(clients);
            }

            @Override
            public void onLoadClientsError(String message) {
                view.showClientsError(message);
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
            public void onLoadProfessionalsError(String message) {
                view.showProfessionalsError(message);
            }
        });
    }
}
