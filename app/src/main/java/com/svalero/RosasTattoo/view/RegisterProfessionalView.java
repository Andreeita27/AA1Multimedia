package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterProfessionalContract;
import com.svalero.RosasTattoo.presenter.RegisterProfessionalPresenter;

public class RegisterProfessionalView extends BaseView implements RegisterProfessionalContract.View {

    private EditText etName, etBirth, etDesc, etPhoto, etYears;
    private CheckBox cbBooks;
    private RegisterProfessionalContract.Presenter presenter;

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

        Button btnSave = findViewById(R.id.btnSaveProfessional);
        btnSave.setOnClickListener(v -> submit());
    }

    private void submit() {
        String name = etName.getText().toString().trim();
        String birth = etBirth.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String photo = etPhoto.getText().toString().trim();

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

        boolean booksOpened = cbBooks.isChecked();

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