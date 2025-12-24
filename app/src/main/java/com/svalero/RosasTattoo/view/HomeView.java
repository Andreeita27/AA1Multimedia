package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svalero.RosasTattoo.R;

public class HomeView extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_home);

        findViewById(R.id.btnShowroom).setOnClickListener(v ->
                startActivity(new Intent(this, TattooListView.class)));

        findViewById(R.id.btnProfessionals).setOnClickListener(v ->
                startActivity(new Intent(this, ProfessionalListView.class)));

        findViewById(R.id.btnSaved).setOnClickListener(v ->
                startActivity(new Intent(this, FavoriteTattooListView.class)));

        findViewById(R.id.btnMap).setOnClickListener(v ->
                startActivity(new Intent(this, ConventionMapView.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            return true;

        } else if (id == R.id.menu_showroom) {
            startActivity(new Intent(this, TattooListView.class));
            return true;

        } else if (id == R.id.menu_professionals) {
            startActivity(new Intent(this, ProfessionalListView.class));
            return true;

        } else if (id == R.id.menu_favorite) {
            startActivity(new Intent(this, FavoriteTattooListView.class));
            return true;

        } else if (id == R.id.menu_map) {
            startActivity(new Intent(this, ConventionMapView.class));
            return true;

        } else if (id == R.id.menu_client) {
            startActivity(new Intent(this, RegisterClientView.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}