package com.svalero.RosasTattoo.view;

import android.content.Intent;
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
import com.svalero.RosasTattoo.util.DateUtil;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_add_professional));
        }

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
                        } catch (Exception ignored) { }

                        selectedImageUri = uri.toString();
                        etPhoto.setText(selectedImageUri);

                        Toast.makeText(this, getString(R.string.image_selected), Toast.LENGTH_SHORT).show();
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
        String birthText = etBirth.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String photo = etPhoto.getText().toString().trim();
        boolean booksOpened = cbBooks.isChecked();

        String birthIso = "";
        if (!birthText.isEmpty()) {
            try {
                birthIso = DateUtil.toApiFormat(birthText);
            } catch (Exception e) {
                showError("error_invalid_date_format");
                return;
            }
        }

        int years = 0;
        String yearsText = etYears.getText().toString().trim();
        if (!yearsText.isEmpty()) {
            try {
                years = Integer.parseInt(yearsText);
            } catch (Exception e) {
                showError("error_years_must_be_number");
                return;
            }
        }

        presenter.registerProfessional(
                name,
                birthIso.isEmpty() ? null : birthIso,
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
    public void showMessage(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }
}