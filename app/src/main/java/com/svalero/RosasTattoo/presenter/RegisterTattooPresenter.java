package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.model.RegisterTattooModel;

public class RegisterTattooPresenter implements RegisterTattooContract.Presenter, RegisterTattooContract.Model.OnRegisterTattooListener {

    private RegisterTattooContract.View view;
    private RegisterTattooContract.Model model;

    public RegisterTattooPresenter(RegisterTattooContract.View view) {
        this.view = view;
        this.model = new RegisterTattooModel();
    }

    @Override
    public void registerTattoo(String style, String description, String imageUrl) {

        if (style == null || style.trim().isEmpty()) {
            view.showError("El estilo es obligatorio");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            view.showError("La descripción es obligatoria");
            return;
        }

        model.registerTattoo(style, description, imageUrl, this);
    }

    @Override
    public void onRegisterTattooSuccess(String message) {
        view.showMessage(message);
        view.clearForm();
    }

    @Override
    public void onRegisterTattooError(String message) {
        view.showError(message);
    }
}
