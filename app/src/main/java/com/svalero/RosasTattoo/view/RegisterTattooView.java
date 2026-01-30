package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.db.LocalImage;
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

    private final List<Client> clientList = new ArrayList<>();
    private final List<Professional> professionalList = new ArrayList<>();
    private Client selectedClient;
    private Professional selectedProfessional;

    private static final double DEFAULT_LAT = 41.648823;
    private static final double DEFAULT_LON = -0.889085;

    private String selectedImageUri;

    private ActivityResultLauncher<String[]> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tattoo_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_add_tattoo));
        }

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

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (Exception ignored) { }

                        selectedImageUri = uri.toString();
                        etImageUrl.setText(selectedImageUri);

                        Toast.makeText(this, getString(R.string.image_selected), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        etImageUrl.setFocusable(false);
        etImageUrl.setClickable(true);
        etImageUrl.setOnClickListener(v -> pickImageLauncher.launch(new String[]{"image/*"}));

        btnRegisterTattoo.setOnClickListener(v -> {
            String style = etStyle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String imageUrl = etImageUrl.getText().toString().trim();

            if (selectedClient == null || selectedProfessional == null) {
                Toast.makeText(this, getString(R.string.error_select_client_and_professional), Toast.LENGTH_SHORT).show();
                return;
            }

            Double latitude = parseDoubleOrNull(etLatitude.getText().toString().trim());
            Double longitude = parseDoubleOrNull(etLongitude.getText().toString().trim());

            if (latitude == null) latitude = DEFAULT_LAT;
            if (longitude == null) longitude = DEFAULT_LON;

            if (latitude < -90 || latitude > 90) {
                Toast.makeText(this, getString(R.string.error_invalid_latitude), Toast.LENGTH_SHORT).show();
                return;
            }
            if (longitude < -180 || longitude > 180) {
                Toast.makeText(this, getString(R.string.error_invalid_longitude), Toast.LENGTH_SHORT).show();
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

        actvClient.setOnItemClickListener((parent, view, position, id) ->
                selectedClient = (Client) parent.getItemAtPosition(position));
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

        actvProfessional.setOnItemClickListener((parent, view, position, id) ->
                selectedProfessional = (Professional) parent.getItemAtPosition(position));
    }

    @Override
    public void onTattooRegistered(long tattooId) {
        if (selectedImageUri != null && !selectedImageUri.isEmpty()) {
            AppDatabase.getInstance(this)
                    .localImageDao()
                    .upsert(new LocalImage("TATTOO", tattooId, selectedImageUri));
        }
        finish();
    }

    @Override
    public void showMessage(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showClientsError(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProfessionalsError(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
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
        selectedImageUri = null;
    }
}