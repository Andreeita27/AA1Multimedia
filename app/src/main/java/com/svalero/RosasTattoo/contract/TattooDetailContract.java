package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Tattoo;

public interface TattooDetailContract {

    interface Model {

        interface OnDeleteTattooListener {
            void onDeleteTattooSuccess(String messageKey);
            void onDeleteTattooError(String messageKey);
        }

        interface OnUpdateTattooListener {
            void onUpdateTattooSuccess(String messageKey);
            void onUpdateTattooError(String messageKey);
        }

        void deleteTattoo(long tattooId, OnDeleteTattooListener listener);
        void updateTattoo(long id, Tattoo tattoo, OnUpdateTattooListener listener);
    }

    interface View {
        void showMessage(String messageKey);
        void showError(String messageKey);
        void closeView();
        void onTattooUpdated(String messageKey);
    }

    interface Presenter {
        void deleteTattoo(long tattooId);
        void updateTattoo(long id, Tattoo tattoo);
    }
}
