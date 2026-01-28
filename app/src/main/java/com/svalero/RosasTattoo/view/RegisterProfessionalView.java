package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterProfessionalContract;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.db.LocalImage;
import com.svalero.RosasTattoo.presenter.RegisterProfessionalPresenter;

public class RegisterProfessionalView extends BaseView implements RegisterProfessionalContract.View {

    private EditText etName, etBirth, etDesc, etPhoto, etYears;
    private CheckBox cbBooks;
    private RegisterProfessionalContract.Presenter presenter;
    private String selectedImageUri;

    private ActivityResultLauncher<String[]> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_professional_view);

        presenter = new RegisterProfessionalPresenter(this);

        etName = findViewById(R.id.etProfessionalName);
        etBirth = findViewById(R.id.etProfessionalBirthDate);
        etDesc = findViewById(R.id.etProfessionalDescription);
        etPhoto = findViewById(R.id.etProfessionalPhoto);
        etYears = findViewById(R.id.etProfessionalYears);
        cbBooks = findViewById(R.id.cbProfessionalBooksOpened);

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {

                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (Exception ignored) {
                        }

                        selectedImageUri = uri.toString();
                        etPhoto.setText(selectedImageUri);
                        showMessage("Imagen seleccionada");
                    }
                }
        );

        etPhoto.setFocusable(false);
        etPhoto.setClickable(true);
        etPhoto.setOnClickListener(v -> pickImageLauncher.launch(new String[]{"image/*"}));

        Button btnSave = findViewById(R.id.btnSaveProfessional);
        btnSave.setOnClickListener(v -> submit());
    }

    private void submit() {
        String name = etName.getText().toString().trim();
        String birth = etBirth.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String photo = etPhoto.getText().toString().trim();
        boolean booksOpened = cbBooks.isChecked();

        int years = 0;
        String yearsText = etYears.getText().toString().trim();
        if (!yearsText.isEmpty()) {
            try {
                years = Integer.parseInt(yearsText);
            } catch (Exception e) {
                showError("Años de experiencia debe ser un número");
                return;
            }
        }

        presenter.registerProfessional(
                name,
                birth.isEmpty() ? null : birth,
                desc.isEmpty() ? null : desc,
                photo.isEmpty() ? null : photo,
                years,
                booksOpened
        );
    }

    @Override
    public void onProfessionalRegistered(Long professionalId) {
        if (selectedImageUri != null && !selectedImageUri.isEmpty()) {
            AppDatabase db = AppDatabase.getInstance(this);
            db.localImageDao().upsert(new LocalImage("PROFESSIONAL", professionalId, selectedImageUri));
        }
        close();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }
}