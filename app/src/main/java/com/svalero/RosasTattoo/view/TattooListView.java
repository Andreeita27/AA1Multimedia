package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.adapter.TattooAdapter;
import com.svalero.RosasTattoo.contract.TattooListContract;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.domain.Tattoo;
import com.svalero.RosasTattoo.presenter.TattooListPresenter;

import java.util.List;

public class TattooListView extends BaseView implements TattooListContract.View {

    private RecyclerView recyclerView;
    private TattooAdapter tattooAdapter;

    private TattooListContract.Presenter presenter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tattoo_list_view);

        recyclerView = findViewById(R.id.rvTattoos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        tattooAdapter = new TattooAdapter(db);
        recyclerView.setAdapter(tattooAdapter);

        presenter = new TattooListPresenter(this);
        presenter.loadTattoos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTattoos();
    }

    @Override
    public void showTattoos(List<Tattoo> tattoos) {
        tattooAdapter.setData(tattoos);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}