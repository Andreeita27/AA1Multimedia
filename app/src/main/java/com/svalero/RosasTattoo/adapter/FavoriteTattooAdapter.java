package com.svalero.RosasTattoo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.db.FavoriteTattoo;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTattooAdapter extends RecyclerView.Adapter<FavoriteTattooAdapter.ViewHolder> {

    public interface Listener {
        void onInstagramChanged(FavoriteTattoo item, boolean checked);
    }

    private List<FavoriteTattoo> data = new ArrayList<>();
    private final Listener listener;

    public FavoriteTattooAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<FavoriteTattoo> newData) {
        data.clear();
        if (newData != null) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_tattoo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteTattoo item = data.get(position);

        holder.tvTattooId.setText("Tattoo #" + item.getTattooId());

        holder.cbInstagram.setOnCheckedChangeListener(null);
        holder.cbInstagram.setChecked(item.isInstagram());

        holder.cbInstagram.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setInstagram(isChecked);
            if (listener != null) listener.onInstagramChanged(item, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTattooId;
        CheckBox cbInstagram;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTattooId = itemView.findViewById(R.id.tvTattooId);
            cbInstagram = itemView.findViewById(R.id.cbInstagram);
        }
    }
}
