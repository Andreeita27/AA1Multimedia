package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.RosasTattoo.R;

public class BaseView extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem addTattooItem = menu.findItem(R.id.menu_add_tattoo);

        // Solo visible en Showroom
        if (addTattooItem != null) {
            addTattooItem.setVisible(this instanceof TattooListView);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            startActivity(new Intent(this, HomeView.class));
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

        } else if (id == R.id.menu_client) {
            startActivity(new Intent(this, RegisterClientView.class));
            return true;

        } else if (id == R.id.menu_add_tattoo) {
            startActivity(new Intent(this, RegisterTattooView.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}