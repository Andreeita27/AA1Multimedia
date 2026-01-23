package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.TattooDetailContract;
import com.svalero.RosasTattoo.model.TattooDetailModel;

public class TattooDetailPresenter implements TattooDetailContract.Presenter, TattooDetailContract.Model.OnDeleteTattooListener {

    private TattooDetailContract.View view;
    private TattooDetailContract.Model model;

    public TattooDetailPresenter(TattooDetailContract.View view) {
        this.view = view;
        this.model = new TattooDetailModel();
    }

    @Override
    public void deleteTattoo(long tattooId) {
        model.deleteTattoo(tattooId, this);
    }

    @Override
    public void onDeleteTattooSuccess(String message) {
        view.showMessage(message);
        view.closeView();
    }

    @Override
    public void onDeleteTattooError(String message) {
        view.showError(message);
    }
}