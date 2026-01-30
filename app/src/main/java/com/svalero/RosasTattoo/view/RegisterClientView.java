package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterClientContract;
import com.svalero.RosasTattoo.presenter.RegisterClientPresenter;

public class RegisterClientView extends BaseView implements RegisterClientContract.View {

    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etBirthDate;
    private CheckBox cbShowPhoto;
    private Button btnRegister;

    private RegisterClientContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_client));
        }

        etName = findViewById(R.id.etClientName);
        etSurname = findViewById(R.id.etClientSurname);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etBirthDate = findViewById(R.id.etBirthDate);
        cbShowPhoto = findViewById(R.id.cbShowPhoto);
        btnRegister = findViewById(R.id.btnRegisterClient);

        presenter = new RegisterClientPresenter(this);

        btnRegister.setOnClickListener(v -> presenter.registerClient(
                etName.getText().toString(),
                etSurname.getText().toString(),
                etEmail.getText().toString(),
                etPhone.getText().toString(),
                etBirthDate.getText().toString(),
                cbShowPhoto.isChecked()
        ));
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
    public void clearForm() {
        etName.setText("");
        etSurname.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etBirthDate.setText("");
        cbShowPhoto.setChecked(false);
    }
}