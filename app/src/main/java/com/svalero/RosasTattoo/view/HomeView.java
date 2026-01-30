package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.os.Bundle;

import com.svalero.RosasTattoo.R;

public class HomeView extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.home_title));
        }

        findViewById(R.id.btnShowroom).setOnClickListener(v ->
                startActivity(new Intent(this, TattooListView.class)));

        findViewById(R.id.btnProfessionals).setOnClickListener(v ->
                startActivity(new Intent(this, ProfessionalListView.class)));

        findViewById(R.id.btnSaved).setOnClickListener(v ->
                startActivity(new Intent(this, FavoriteTattooListView.class)));

        findViewById(R.id.btnAddClient).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterClientView.class)));
    }
}