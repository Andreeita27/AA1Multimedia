package com.svalero.RosasTattoo.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        }

        return super.onOptionsItemSelected(item);
    }

    protected String resolveMessage(String messageKey) {
        if (messageKey == null) {
            return getString(R.string.error_unknown);
        }

        int resId = getResources().getIdentifier(
                messageKey,
                "string",
                getPackageName()
        );

        if (resId != 0) {
            return getString(resId);
        }

        return messageKey;
    }

    protected void showToast(String messageKey) {
        Toast.makeText(this, resolveMessage(messageKey), Toast.LENGTH_SHORT).show();
    }
}