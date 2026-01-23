package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.ProfessionalListContract;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.model.ProfessionalListModel;

import java.time.LocalDate;
import java.util.List;

public class ProfessionalListPresenter implements ProfessionalListContract.Presenter,
        ProfessionalListContract.Model.OnLoadProfessionalsListener,
        ProfessionalListContract.Model.OnOperationListener {

    private final ProfessionalListContract.View view;
    private final ProfessionalListContract.Model model;

    public ProfessionalListPresenter(ProfessionalListContract.View view) {
        this.view = view;
        this.model = new ProfessionalListModel();
    }

    @Override
    public void loadProfessionals() {
        model.loadProfessionals(this);
    }

    @Override
    public void registerProfessional(Professional professional) {
        if (professional.getProfessionalName() == null || professional.getProfessionalName().trim().isEmpty()) {
            view.showError("El nombre es obligatorio");
            return;
        }
        if (professional.getDescription() == null || professional.getDescription().trim().isEmpty()) {
            view.showError("La descripción es obligatoria");
            return;
        }

        if (professional.getBirthDate() != null && !professional.getBirthDate().trim().isEmpty()) {
            try { LocalDate.parse(professional.getBirthDate().trim()); }
            catch (Exception e) {
                view.showError("Fecha inválida (YYYY-MM-DD)");
                return;
            }
        }

        model.registerProfessional(professional, this);
    }

    @Override
    public void updateProfessional(long id, Professional professional) {
        if (professional.getProfessionalName() == null || professional.getProfessionalName().trim().isEmpty()) {
            view.showError("El nombre es obligatorio");
            return;
        }
        if (professional.getDescription() == null || professional.getDescription().trim().isEmpty()) {
            view.showError("La descripción es obligatoria");
            return;
        }
        if (professional.getBirthDate() != null && !professional.getBirthDate().trim().isEmpty()) {
            try { LocalDate.parse(professional.getBirthDate().trim()); }
            catch (Exception e) {
                view.showError("Fecha inválida (YYYY-MM-DD)");
                return;
            }
        }

        model.updateProfessional(id, professional, this);
    }

    @Override
    public void deleteProfessional(long id) {
        model.deleteProfessional(id, this);
    }

    @Override
    public void onLoadSuccess(List<Professional> professionals) {
        view.showProfessionals(professionals);
    }

    @Override
    public void onLoadError(String message) {
        view.showError(message);
    }

    @Override
    public void onSuccess(String message) {
        view.showMessage(message);
        view.refreshList();
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }
}