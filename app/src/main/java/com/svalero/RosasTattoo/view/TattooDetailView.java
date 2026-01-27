package com.svalero.RosasTattoo.view;

import com.svalero.RosasTattoo.db.AppDatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.TattooDetailContract;
import com.svalero.RosasTattoo.presenter.TattooDetailPresenter;

public class TattooDetailView extends BaseView implements TattooDetailContract.View {

    public static final String EXTRA_TATTOO_ID = "tattoo_id";
    public static final String EXTRA_TATTOO_STYLE = "tattoo_style";
    public static final String EXTRA_TATTOO_DESC = "tattoo_desc";
    public static final String EXTRA_TATTOO_IMAGE = "tattoo_image";

    private ImageView ivTattoo;
    private TextView tvStyle;
    private TextView tvDesc;
    private ImageButton btnEditTattoo;
    private ImageButton btnDeleteTattoo;

    private long tattooId;
    private String style;
    private String desc;
    private String imageUrl;

    private TattooDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tattoo_detail_view);

        ivTattoo = findViewById(R.id.ivTattoo);
        tvStyle = findViewById(R.id.tvStyle);
        tvDesc = findViewById(R.id.tvDesc);
        btnEditTattoo = findViewById(R.id.btnEditTattoo);
        btnDeleteTattoo = findViewById(R.id.btnDeleteTattoo);

        btnDeleteTattoo.setOnClickListener(v -> showDeleteDialog());

        btnEditTattoo.setOnClickListener(v ->
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
        );

        presenter = new TattooDetailPresenter(this);

        Intent intent = getIntent();
        tattooId = intent.getLongExtra(EXTRA_TATTOO_ID, -1);
        style = intent.getStringExtra(EXTRA_TATTOO_STYLE);
        desc = intent.getStringExtra(EXTRA_TATTOO_DESC);
        imageUrl = intent.getStringExtra(EXTRA_TATTOO_IMAGE);

        tvStyle.setText(style);
        tvDesc.setText(desc);

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(ivTattoo);
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> presenter.deleteTattoo(tattooId))
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (message != null && message.toLowerCase().contains("elim")) {
            AppDatabase db = AppDatabase.getInstance(this);
            db.favoriteTattooDao().deleteByTattooId(tattooId);

            finish();
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        finish();
    }
}