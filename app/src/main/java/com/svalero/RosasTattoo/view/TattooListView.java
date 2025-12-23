package com.svalero.RosasTattoo.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.adapter.TattooAdapter;
import com.svalero.RosasTattoo.api.RosasTattooApi;
import com.svalero.RosasTattoo.api.RosasTattooApiInterface;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TattooListView extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tattoo_list_view);

        RecyclerView rv = findViewById(R.id.rvTattoos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        TattooAdapter adapter = new TattooAdapter();
        rv.setAdapter(adapter);

        RosasTattooApiInterface api = RosasTattooApi.buildInstance();
        api.getTattoos().enqueue(new Callback<List<Tattoo>>() {
            @Override
            public void onResponse(Call<List<Tattoo>> call, Response<List<Tattoo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body());
                } else {
                    Toast.makeText(TattooListView.this, "HTTP error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tattoo>> call, Throwable t) {
                Toast.makeText(TattooListView.this, "Connection error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}