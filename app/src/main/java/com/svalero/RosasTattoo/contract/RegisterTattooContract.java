package com.svalero.RosasTattoo.contract;

public interface RegisterTattooContract {

    interface Model {

        interface OnRegisterTattooListener {
            void onRegisterTattooSuccess(String message);
            void onRegisterTattooError(String message);
        }

        void registerTattoo(String style, String description, String imageUrl, OnRegisterTattooListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void clearForm();
    }

    interface Presenter {
        void registerTattoo(String style, String description, String imageUrl);
    }
}
