package com.svalero.RosasTattoo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.adapter.ProfessionalAdapter;
import com.svalero.RosasTattoo.contract.ProfessionalListContract;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.db.LocalImage;
import com.svalero.RosasTattoo.domain.Professional;
import com.svalero.RosasTattoo.presenter.ProfessionalListPresenter;
import com.svalero.RosasTattoo.util.DateUtil;

import java.util.List;

public class ProfessionalListView extends BaseView implements ProfessionalListContract.View,
        ProfessionalAdapter.OnProfessionalActionListener {

    private RecyclerView rvProfessionals;
    private ProfessionalAdapter adapter;
    private ProfessionalListContract.Presenter presenter;
    private ActivityResultLauncher<String[]> pickImageLauncher;
    private EditText currentPhotoEditText;
    private Long currentProfessionalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_list_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_professionals));
        }

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

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null && currentProfessionalId != null) {

                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (Exception ignored) { }

                        String uriString = uri.toString();

                        if (currentPhotoEditText != null) {
                            currentPhotoEditText.setText(uriString);
                        }

                        AppDatabase.getInstance(this)
                                .localImageDao()
                                .upsert(new LocalImage("PROFESSIONAL", currentProfessionalId, uriString));

                        Toast.makeText(this, getString(R.string.image_selected), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }
        );
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
    public void showMessage(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_LONG).show();
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
                .setMessage(getString(R.string.confirm_delete_professional))
                .setPositiveButton(getString(R.string.delete), (d, w) ->
                        presenter.deleteProfessional(professional.getId()))
                .setNegativeButton(getString(R.string.cancel), null)
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
            Toast.makeText(this, getString(R.string.use_add_button_to_create_professional), Toast.LENGTH_LONG).show();
            return;
        }

        etName.setText(existing.getProfessionalName());

        String birthUi = DateUtil.toUiFormat(existing.getBirthDate());
        etBirth.setText(birthUi);

        etDesc.setText(existing.getDescription());
        etYears.setText(String.valueOf(existing.getYearsExperience()));
        cbBooks.setChecked(existing.isBooksOpened());

        String localUri = AppDatabase.getInstance(this)
                .localImageDao()
                .getImageUri("PROFESSIONAL", existing.getId());

        if (localUri != null && !localUri.isEmpty()) {
            etPhoto.setText(localUri);
        } else {
            etPhoto.setText(existing.getProfilePhoto());
        }

        etPhoto.setFocusable(false);
        etPhoto.setClickable(true);
        etPhoto.setOnClickListener(v -> {
            currentPhotoEditText = etPhoto;
            currentProfessionalId = existing.getId();
            pickImageLauncher.launch(new String[]{"image/*"});
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.edit_professional_title))
                .setView(view)
                .setPositiveButton(getString(R.string.save), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String birthText = etBirth.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String photo = etPhoto.getText().toString().trim();
            String yearsText = etYears.getText().toString().trim();
            boolean books = cbBooks.isChecked();

            String birthIso = birthText;
            if (!birthText.isEmpty()) {
                try {
                    birthIso = DateUtil.toApiFormat(birthText);
                } catch (Exception e) {
                    showError("error_invalid_date_format");
                    return;
                }
            }

            int years = 0;
            if (!yearsText.isEmpty()) {
                try {
                    years = Integer.parseInt(yearsText);
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.error_years_must_be_number), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            Professional p = Professional.builder()
                    .professionalName(name)
                    .birthDate(birthIso.isEmpty() ? null : birthIso)
                    .description(desc.isEmpty() ? null : desc)
                    .profilePhoto(photo.isEmpty() ? null : photo)
                    .booksOpened(books)
                    .yearsExperience(years)
                    .build();

            presenter.updateProfessional(existing.getId(), p);
            dialog.dismiss();
        }));

        dialog.setOnDismissListener(d -> {
            currentPhotoEditText = null;
            currentProfessionalId = null;
        });

        dialog.show();
    }
}