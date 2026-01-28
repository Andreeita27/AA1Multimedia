package com.svalero.RosasTattoo.contract;

public interface RegisterProfessionalContract {

    interface View {
        void showMessage(String message);
        void showError(String error);
        void close();
    }

    interface Presenter {
        void registerProfessional(String professionalName,
                                  String birthDate,
                                  String description,
                                  String profilePhoto,
                                  int yearsExperience,
                                  boolean booksOpened);
    }

    interface Model {
        void registerProfessional(String professionalName,
                                  String birthDate,
                                  String description,
                                  String profilePhoto,
                                  int yearsExperience,
                                  boolean booksOpened,
                                  OnRegisterProfessionalListener listener);

        interface OnRegisterProfessionalListener {
            void onRegisterProfessionalSuccess(String message);
            void onRegisterProfessionalError(String message);
        }
    }
}