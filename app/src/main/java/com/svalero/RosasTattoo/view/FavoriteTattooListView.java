package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.adapter.FavoriteTattooAdapter;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.db.FavoriteTattoo;

import java.util.List;

public class FavoriteTattooListView extends BaseView {

    private AppDatabase db;
    private FavoriteTattooAdapter adapter;

    private RecyclerView rvFavorites;
    private LinearLayout emptyStateFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tattoo_list_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_favorite));
        }

        db = AppDatabase.getInstance(this);

        rvFavorites = findViewById(R.id.rvFavorites);
        emptyStateFavorites = findViewById(R.id.emptyStateFavorites);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(false);
        rvFavorites.setLayoutManager(lm);

        adapter = new FavoriteTattooAdapter((item, checked) -> {
            item.setInstagram(checked);
            db.favoriteTattooDao().update(item);
        });

        rvFavorites.setAdapter(adapter);

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        List<FavoriteTattoo> favorites = db.favoriteTattooDao().getAll();
        adapter.setData(favorites);

        boolean isEmpty = (favorites == null || favorites.isEmpty());
        emptyStateFavorites.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvFavorites.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}