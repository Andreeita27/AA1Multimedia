package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Professional;
import java.util.List;

public interface ProfessionalListContract {

    interface Model {

        interface OnLoadProfessionalsListener {
            void onLoadSuccess(List<Professional> professionals);
            void onLoadError(String messageKey);
        }

        interface OnOperationListener {
            void onSuccess(String messageKey);
            void onError(String messageKey);
        }

        void loadProfessionals(OnLoadProfessionalsListener listener);
        void registerProfessional(Professional professional, OnOperationListener listener);
        void updateProfessional(long id, Professional professional, OnOperationListener listener);
        void deleteProfessional(long id, OnOperationListener listener);
    }

    interface View {
        void showProfessionals(List<Professional> professionals);
        void showMessage(String messageKey);
        void showError(String messageKey);
        void refreshList();
    }

    interface Presenter {
        void loadProfessionals();
        void registerProfessional(Professional professional);
        void updateProfessional(long id, Professional professional);
        void deleteProfessional(long id);
    }
}