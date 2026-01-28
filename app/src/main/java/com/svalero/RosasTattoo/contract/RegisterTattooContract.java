package com.svalero.RosasTattoo.contract;

import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

public interface RegisterTattooContract {

    interface Model {

        interface OnRegisterTattooListener {
            void onRegisterTattooSuccess(String message, Tattoo tattoo);
            void onRegisterTattooError(String message);
        }

        void registerTattoo(long clientId, long professionalId, String style, String description,
                            String imageUrl, Double latitude, Double longitude,
                            OnRegisterTattooListener listener);

        interface OnLoadClientsListener {
            void onLoadClientsSuccess(List<Client> clients);
            void onLoadClientsError(String message);
        }

        interface OnLoadProfessionalsListener {
            void onLoadProfessionalsSuccess(List<Professional> professionals);
            void onLoadProfessionalsError(String message);
        }

        void loadClients(OnLoadClientsListener listener);
        void loadProfessionals(OnLoadProfessionalsListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void clearForm();
        void showClients(List<Client> clients);
        void showProfessionals(List<Professional> professionals);
        void showClientsError(String message);
        void showProfessionalsError(String message);
        void onTattooRegistered(long tattooId);
    }

    interface Presenter {
        void registerTattoo(long clientId, long professionalId, String style, String description,
                            String imageUrl, Double latitude, Double longitude);
        void loadClients();
        void loadProfessionals();
    }
}
