package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.TattooDetailContract;
import com.svalero.RosasTattoo.domain.Tattoo;
import com.svalero.RosasTattoo.model.TattooDetailModel;

public class TattooDetailPresenter implements TattooDetailContract.Presenter,
        TattooDetailContract.Model.OnDeleteTattooListener,
        TattooDetailContract.Model.OnUpdateTattooListener {

    private final TattooDetailContract.View view;
    private final TattooDetailContract.Model model;

    public TattooDetailPresenter(TattooDetailContract.View view) {
        this.view = view;
        this.model = new TattooDetailModel();
    }

    @Override
    public void deleteTattoo(long tattooId) {
        model.deleteTattoo(tattooId, this);
    }

    @Override
    public void onDeleteTattooSuccess(String messageKey) {
        view.showMessage(messageKey);
        view.closeView();
    }

    @Override
    public void onDeleteTattooError(String messageKey) {
        view.showError(messageKey);
    }

    @Override
    public void updateTattoo(long id, Tattoo tattoo) {
        model.updateTattoo(id, tattoo, this);
    }

    @Override
    public void onUpdateTattooSuccess(String messageKey) {
        view.onTattooUpdated(messageKey);
    }

    @Override
    public void onUpdateTattooError(String messageKey) {
        view.showError(messageKey);
    }
}