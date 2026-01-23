package com.svalero.RosasTattoo.contract;

public interface RegisterClientContract {

    interface Model {
        interface OnRegisterClientListener {
            void onRegisterClientSuccess(String message);
            void onRegisterClientError(String message);
        }

        void registerClient(String clientName, String clientSurname, String email, String phone,
                            String birthDate, boolean showPhoto,
                            OnRegisterClientListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void clearForm();
    }

    interface Presenter {
        void registerClient(String clientName, String clientSurname, String email, String phone,
                            String birthDate, boolean showPhoto);
    }
}