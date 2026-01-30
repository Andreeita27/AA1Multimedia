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
            view.showError("error_name_required");
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
    public void onRegisterProfessionalSuccess(String messageKey, Professional professional) {
        view.showMessage(messageKey);

        if (professional != null) {
            view.onProfessionalRegistered(professional.getId());
        } else {
            view.close();
        }
    }

    @Override
    public void onRegisterProfessionalError(String messageKey) {
        view.showError(messageKey);
    }
}