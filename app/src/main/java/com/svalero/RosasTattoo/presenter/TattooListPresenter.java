package com.svalero.RosasTattoo.presenter;

import com.svalero.RosasTattoo.contract.TattooListContract;
import com.svalero.RosasTattoo.domain.Tattoo;
import com.svalero.RosasTattoo.model.TattooListModel;

import java.util.List;

public class TattooListPresenter implements TattooListContract.Presenter, TattooListContract.Model.OnLoadTattoosListener {

    private TattooListContract.View view;
    private TattooListContract.Model model;

    public TattooListPresenter(TattooListContract.View view) {
        this.view = view;
        this.model = new TattooListModel();
    }

    @Override
    public void loadTattoos() {
        model.loadTattoos(this);
    }

    @Override
    public void onLoadTattoosSuccess(List<Tattoo> tattoos) {
        view.showTattoos(tattoos);
    }

    @Override
    public void onLoadTattoosError(String message) {
        view.showError(message);
    }
}