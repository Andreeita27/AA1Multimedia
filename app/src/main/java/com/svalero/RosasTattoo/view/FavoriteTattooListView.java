package com.svalero.RosasTattoo.view;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tattoo_list_view);

        db = AppDatabase.getInstance(this);

        RecyclerView rv = findViewById(R.id.rvFavorites);

        LinearLayoutManager lm =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(false);
        rv.setLayoutManager(lm);

        adapter = new FavoriteTattooAdapter((item, checked) -> {
            item.setInstagram(checked);
            db.favoriteTattooDao().update(item);
        });

        rv.setAdapter(adapter);

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
    }
}