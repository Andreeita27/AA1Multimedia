package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

public interface TattooListContract {

    interface Model {

        interface OnLoadTattoosListener {
            void onLoadTattoosSuccess(List<Tattoo> tattoos);
            void onLoadTattoosError(String messageKey);
        }

        void loadTattoos(OnLoadTattoosListener listener);
    }

    interface View {
        void showTattoos(List<Tattoo> tattoos);
        void showMessage(String messageKey);
        void showError(String messageKey);
    }

    interface Presenter {
        void loadTattoos();
    }
}