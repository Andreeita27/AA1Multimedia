package com.svalero.RosasTattoo.contract;

public interface RegisterClientContract {

    interface Model {

        interface OnRegisterClientListener {
            void onRegisterClientSuccess(String messageKey);
            void onRegisterClientError(String messageKey);
        }

        void registerClient(String clientName, String clientSurname, String email, String phone,
                            String birthDate, boolean showPhoto,
                            OnRegisterClientListener listener);
    }

    interface View {
        void showMessage(String messageKey);
        void showError(String messageKey);
        void clearForm();
    }

    interface Presenter {
        void registerClient(String clientName, String clientSurname, String email, String phone,
                            String birthDate, boolean showPhoto);
    }
}