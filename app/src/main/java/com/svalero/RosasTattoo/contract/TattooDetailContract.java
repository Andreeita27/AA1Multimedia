package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Tattoo;

public interface TattooDetailContract {

    interface Model {

        interface OnDeleteTattooListener {
            void onDeleteTattooSuccess(String message);
            void onDeleteTattooError(String message);
        }

        interface OnUpdateTattooListener {
            void onUpdateTattooSuccess(String message);
            void onUpdateTattooError(String message);
        }

        void deleteTattoo(long tattooId, OnDeleteTattooListener listener);
        void updateTattoo(long id, Tattoo tattoo, OnUpdateTattooListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void closeView();
        void onTattooUpdated(String message);
    }

    interface Presenter {
        void deleteTattoo(long tattooId);
        void updateTattoo(long id, Tattoo tattoo);
    }
}
