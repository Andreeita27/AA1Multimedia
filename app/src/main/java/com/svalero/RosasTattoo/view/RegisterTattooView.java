package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.RegisterTattooContract;
import com.svalero.RosasTattoo.presenter.RegisterTattooPresenter;

public class RegisterTattooView extends BaseView implements RegisterTattooContract.View {

    private EditText etStyle;
    private EditText etDescription;
    private EditText etImageUrl;
    private Button btnRegisterTattoo;

    private RegisterTattooContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tattoo_view);

        etStyle = findViewById(R.id.etStyle);
        etDescription = findViewById(R.id.etTattooDescription);
        etImageUrl = findViewById(R.id.etImageUrl);
        btnRegisterTattoo = findViewById(R.id.btnRegisterTattoo);

        presenter = new RegisterTattooPresenter(this);

        btnRegisterTattoo.setOnClickListener(v -> {
            String style = etStyle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String imageUrl = etImageUrl.getText().toString().trim();

            presenter.registerTattoo(style, description, imageUrl);
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearForm() {
        etStyle.setText("");
        etDescription.setText("");
        etImageUrl.setText("");
    }
}