package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Professional;

public interface RegisterProfessionalContract {

    interface View {
        void showMessage(String messageKey);
        void showError(String messageKey);
        void onProfessionalRegistered(Long professionalId);
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
            void onRegisterProfessionalSuccess(String messageKey, Professional professional);
            void onRegisterProfessionalError(String messageKey);
        }
    }
}