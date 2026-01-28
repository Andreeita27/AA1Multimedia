package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.domain.Client;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.presenter.RegisterTattooPresenter;

import java.util.ArrayList;
import java.util.List;

public class RegisterTattooView extends BaseView implements RegisterTattooContract.View {

    private EditText etStyle;
    private EditText etDescription;
    private EditText etImageUrl;
    private EditText etLatitude;
    private EditText etLongitude;

    private Button btnRegisterTattoo;
    private AutoCompleteTextView actvClient;
    private AutoCompleteTextView actvProfessional;
    private RegisterTattooContract.Presenter presenter;

    private List<Client> clientList = new ArrayList<>();
    private List<Professional> professionalList = new ArrayList<>();
    private Client selectedClient;
    private Professional selectedProfessional;

    private static final double DEFAULT_LAT = 41.648823;
    private static final double DEFAULT_LON = -0.889085;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tattoo_view);

        actvClient = findViewById(R.id.actvClient);
        actvProfessional = findViewById(R.id.actvProfessional);
        etStyle = findViewById(R.id.etStyle);
        etDescription = findViewById(R.id.etTattooDescription);
        etImageUrl = findViewById(R.id.etImageUrl);

        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);

        btnRegisterTattoo = findViewById(R.id.btnRegisterTattoo);

        presenter = new RegisterTattooPresenter(this);

        presenter.loadClients();
        presenter.loadProfessionals();

        btnRegisterTattoo.setOnClickListener(v -> {
            String style = etStyle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String imageUrl = etImageUrl.getText().toString().trim();

            if (selectedClient == null || selectedProfessional == null) {
                Toast.makeText(this, "Selecciona cliente y profesional", Toast.LENGTH_SHORT).show();
                return;
            }

            Double latitude = parseDoubleOrNull(etLatitude.getText().toString().trim());
            Double longitude = parseDoubleOrNull(etLongitude.getText().toString().trim());

            if (latitude == null) latitude = DEFAULT_LAT;
            if (longitude == null) longitude = DEFAULT_LON;

            if (latitude < -90 || latitude > 90) {
                Toast.makeText(this, "Latitud inválida (-90 a 90)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (longitude < -180 || longitude > 180) {
                Toast.makeText(this, "Longitud inválida (-180 a 180)", Toast.LENGTH_SHORT).show();
                return;
            }

            presenter.registerTattoo(
                    selectedClient.getId(),
                    selectedProfessional.getId(),
                    style,
                    description,
                    imageUrl,
                    latitude,
                    longitude
            );
        });
    }

    private Double parseDoubleOrNull(String value) {
        if (value == null || value.isEmpty()) return null;

        value = value.replace(",", ".");

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void showClients(List<Client> clients) {
        clientList.clear();
        clientList.addAll(clients);

        ArrayAdapter<Client> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                clientList
        );
        actvClient.setAdapter(adapter);

        actvClient.setOnItemClickListener((parent, view, position, id) -> {
            selectedClient = (Client) parent.getItemAtPosition(position);
        });
    }

    @Override
    public void showProfessionals(List<Professional> professionals) {
        professionalList.clear();
        professionalList.addAll(professionals);

        ArrayAdapter<Professional> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                professionalList
        );
        actvProfessional.setAdapter(adapter);

        actvProfessional.setOnItemClickListener((parent, view, position, id) -> {
            selectedProfessional = (Professional) parent.getItemAtPosition(position);
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (message != null && message.toLowerCase().contains("registrado")) {
            finish();
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showClientsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProfessionalsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearForm() {
        etStyle.setText("");
        etDescription.setText("");
        etImageUrl.setText("");

        if (etLatitude != null) etLatitude.setText("");
        if (etLongitude != null) etLongitude.setText("");

        actvClient.setText("", false);
        actvProfessional.setText("", false);
        selectedClient = null;
        selectedProfessional = null;
    }
}