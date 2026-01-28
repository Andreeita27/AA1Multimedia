package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.RegisterProfessionalContract;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.model.RegisterProfessionalModel;

public class RegisterProfessionalPresenter implements RegisterProfessionalContract.Presenter,
        RegisterProfessionalContract.Model.OnRegisterProfessionalListener {

    private final RegisterProfessionalContract.View view;
    private final RegisterProfessionalContract.Model model;

    public RegisterProfessionalPresenter(RegisterProfessionalContract.View view) {
        this.view = view;
        this.model = new RegisterProfessionalModel();
    }

    @Override
    public void registerProfessional(String professionalName,
                                     String birthDate,
                                     String description,
                                     String profilePhoto,
                                     int yearsExperience,
                                     boolean booksOpened) {

        if (professionalName == null || professionalName.trim().isEmpty()) {
            view.showError("El nombre es obligatorio");
            return;
        }

        model.registerProfessional(
                professionalName.trim(),
                birthDate,
                description,
                profilePhoto,
                yearsExperience,
                booksOpened,
                this
        );
    }

    @Override
    public void onRegisterProfessionalSuccess(String message, Professional professional) {
        view.showMessage(message);

        if (professional != null) {
            view.onProfessionalRegistered(professional.getId());
        } else {
            view.close();
        }
    }

    @Override
    public void onRegisterProfessionalError(String message) {
        view.showError(message);
    }
}