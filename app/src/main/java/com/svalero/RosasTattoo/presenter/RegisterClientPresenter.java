package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterClientContract;
import com.svalero.RosasTattoo.model.RegisterClientModel;

import java.time.LocalDate;

public class RegisterClientPresenter implements RegisterClientContract.Presenter, RegisterClientContract.Model.OnRegisterClientListener {

    private RegisterClientContract.View view;
    private RegisterClientContract.Model model;

    public RegisterClientPresenter(RegisterClientContract.View view) {
        this.view = view;
        this.model = new RegisterClientModel();
    }

    @Override
    public void registerClient(String clientName, String clientSurname, String email, String phone,
                               String birthDate, boolean showPhoto) {

        if (clientName == null || clientName.trim().isEmpty()) {
            view.showError("El nombre es obligatorio");
            return;
        }

        if (clientSurname == null || clientSurname.trim().isEmpty()) {
            view.showError("El apellido es obligatorio");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            view.showError("El email es obligatorio");
            return;
        }

        if (birthDate != null && !birthDate.trim().isEmpty()) {
            try {
                LocalDate.parse(birthDate.trim()); // espera YYYY-MM-DD
            } catch (Exception e) {
                view.showError("La fecha debe tener formato YYYY-MM-DD");
                return;
            }
        }

        model.registerClient(clientName.trim(), clientSurname.trim(), email.trim(),
                phone == null ? "" : phone.trim(),
                birthDate == null ? "" : birthDate.trim(),
                showPhoto,
                this);
    }

    @Override
    public void onRegisterClientSuccess(String message) {
        view.showMessage(message);
        view.clearForm();
    }

    @Override
    public void onRegisterClientError(String message) {
        view.showError(message);
    }
}