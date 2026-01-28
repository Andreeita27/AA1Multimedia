package com.svalero.RosasTattoo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.adapter.ProfessionalAdapter;
import com.svalero.RosasTattoo.contract.ProfessionalListContract;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.presenter.ProfessionalListPresenter;

import java.util.List;

public class ProfessionalListView extends BaseView implements ProfessionalListContract.View,
        ProfessionalAdapter.OnProfessionalActionListener {

    private RecyclerView rvProfessionals;
    private ProfessionalAdapter adapter;
    private ProfessionalListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_list_view);

        rvProfessionals = findViewById(R.id.rvProfessionals);
        rvProfessionals.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProfessionalAdapter(this);
        rvProfessionals.setAdapter(adapter);

        presenter = new ProfessionalListPresenter(this);
        presenter.loadProfessionals();

        findViewById(R.id.btnAddProfessional).setOnClickListener(v -> {
            Intent intent = new Intent(ProfessionalListView.this, RegisterProfessionalView.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadProfessionals();
    }

    @Override
    public void showProfessionals(List<Professional> professionals) {
        adapter.setData(professionals);
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
    public void refreshList() {
        presenter.loadProfessionals();
    }

    @Override
    public void onEdit(Professional professional) {
        showProfessionalDialog(professional);
    }

    @Override
    public void onDelete(Professional professional) {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres borrar este profesional?")
                .setPositiveButton("Borrar", (d, w) -> presenter.deleteProfessional(professional.getId()))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showProfessionalDialog(Professional existing) {
        View view = getLayoutInflater().inflate(R.layout.dialog_professional, null);

        EditText etName = view.findViewById(R.id.etProfessionalName);
        EditText etBirth = view.findViewById(R.id.etProfessionalBirthDate);
        EditText etDesc = view.findViewById(R.id.etProfessionalDescription);
        EditText etPhoto = view.findViewById(R.id.etProfessionalPhoto);
        EditText etYears = view.findViewById(R.id.etProfessionalYears);
        CheckBox cbBooks = view.findViewById(R.id.cbProfessionalBooksOpened);

        if (existing == null) {
            showError("Para crear un profesional usa el botón Añadir");
            return;
        }

        etName.setText(existing.getProfessionalName());
        etBirth.setText(existing.getBirthDate());
        etDesc.setText(existing.getDescription());
        etPhoto.setText(existing.getProfilePhoto());
        etYears.setText(String.valueOf(existing.getYearsExperience()));
        cbBooks.setChecked(existing.isBooksOpened());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Editar profesional")
                .setView(view)
                .setPositiveButton("Guardar", null)
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String birth = etBirth.getText().toString().trim(); // "yyyy-MM-dd" o vacío
            String desc = etDesc.getText().toString().trim();
            String photo = etPhoto.getText().toString().trim();
            String yearsText = etYears.getText().toString().trim();
            boolean books = cbBooks.isChecked();

            int years = 0;
            if (!yearsText.isEmpty()) {
                try { years = Integer.parseInt(yearsText); }
                catch (Exception e) {
                    showError("Años de experiencia debe ser un número");
                    return;
                }
            }

            Professional p = Professional.builder()
                    .professionalName(name)
                    .birthDate(birth.isEmpty() ? null : birth)
                    .description(desc)
                    .profilePhoto(photo.isEmpty() ? null : photo)
                    .booksOpened(books)
                    .yearsExperience(years)
                    .build();

            presenter.updateProfessional(existing.getId(), p);
            dialog.dismiss();
        }));

        dialog.show();
    }
}