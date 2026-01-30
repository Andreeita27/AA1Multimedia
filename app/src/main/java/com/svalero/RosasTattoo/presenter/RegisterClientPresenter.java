package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterClientContract;
import com.svalero.RosasTattoo.model.RegisterClientModel;

import java.time.LocalDate;

public class RegisterClientPresenter implements RegisterClientContract.Presenter,
        RegisterClientContract.Model.OnRegisterClientListener {

    private final RegisterClientContract.View view;
    private final RegisterClientContract.Model model;

    public RegisterClientPresenter(RegisterClientContract.View view) {
        this.view = view;
        this.model = new RegisterClientModel();
    }

    @Override
    public void registerClient(String clientName, String clientSurname, String email, String phone,
                               String birthDateIso, boolean showPhoto) {

        if (clientName == null || clientName.trim().isEmpty()) {
            view.showError("error_client_name_required");
            return;
        }

        if (clientSurname == null || clientSurname.trim().isEmpty()) {
            view.showError("error_client_surname_required");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            view.showError("error_client_email_required");
            return;
        }

        if (birthDateIso != null && !birthDateIso.trim().isEmpty()) {
            try {
                LocalDate.parse(birthDateIso.trim());
            } catch (Exception e) {
                view.showError("error_invalid_date_format");
                return;
            }
        }

        model.registerClient(
                clientName.trim(),
                clientSurname.trim(),
                email.trim(),
                phone == null ? "" : phone.trim(),
                birthDateIso == null ? "" : birthDateIso.trim(),
                showPhoto,
                this
        );
    }

    @Override
    public void onRegisterClientSuccess(String messageKey) {
        view.showMessage(messageKey);
        view.clearForm();
    }

    @Override
    public void onRegisterClientError(String messageKey) {
        view.showError(messageKey);
    }
}