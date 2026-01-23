package com.svalero.RosasTattoo.contract;

public interface TattooDetailContract {

    interface Model {
        interface OnDeleteTattooListener {
            void onDeleteTattooSuccess(String message);
            void onDeleteTattooError(String message);
        }

        void deleteTattoo(long tattooId, OnDeleteTattooListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void closeView();
    }

    interface Presenter {
        void deleteTattoo(long tattooId);
    }
}
