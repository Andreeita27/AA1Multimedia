package com.svalero.RosasTattoo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.db.FavoriteTattoo;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.ArrayList;
import java.util.List;

public class TattooAdapter extends RecyclerView.Adapter<TattooAdapter.TattooViewHolder> {

    private List<Tattoo> tattoos = new ArrayList<>();
    private AppDatabase db;

    public TattooAdapter(AppDatabase db) {
        this.db = db;
    }

    public void setData(List<Tattoo> tattoos) {
        this.tattoos = tattoos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TattooViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tattoo_item, parent, false);
        return new TattooViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TattooViewHolder holder, int position) {
        Tattoo tattoo = tattoos.get(position);

        holder.tvStyle.setText(tattoo.getStyle());
        holder.tvDesc.setText(tattoo.getTattooDescription());

        Glide.with(holder.itemView.getContext())
                .load(tattoo.getImageUrl())
                .centerCrop()
                .into(holder.ivTattoo);

        holder.cbFavorite.setOnCheckedChangeListener(null);

        boolean isFav = db.favoriteTattooDao()
                .getByTattooId(tattoo.getId()) != null;

        holder.cbFavorite.setChecked(isFav);

        holder.cbFavorite.setOnCheckedChangeListener((buttonView, checked) -> {
            if (checked) {
                FavoriteTattoo existing =
                        db.favoriteTattooDao().getByTattooId(tattoo.getId());

                if (existing == null) {
                    FavoriteTattoo fav = new FavoriteTattoo();
                    fav.setTattooId(tattoo.getId());
                    fav.setInstagram(false);
                    db.favoriteTattooDao().insert(fav);
                }
            } else {
                db.favoriteTattooDao().deleteByTattooId(tattoo.getId());
            }
        });
        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(holder.itemView.getContext(),
                    com.svalero.RosasTattoo.view.TattooDetailView.class);

            intent.putExtra(com.svalero.RosasTattoo.view.TattooDetailView.EXTRA_TATTOO_ID, tattoo.getId());
            intent.putExtra(com.svalero.RosasTattoo.view.TattooDetailView.EXTRA_TATTOO_STYLE, tattoo.getStyle());
            intent.putExtra(com.svalero.RosasTattoo.view.TattooDetailView.EXTRA_TATTOO_DESC, tattoo.getTattooDescription());
            intent.putExtra(com.svalero.RosasTattoo.view.TattooDetailView.EXTRA_TATTOO_IMAGE, tattoo.getImageUrl());

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tattoos == null ? 0 : tattoos.size();
    }

    static class TattooViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTattoo;
        TextView tvStyle, tvDesc;
        CheckBox cbFavorite;

        TattooViewHolder(@NonNull View itemView) {
            super(itemView);
            cbFavorite = itemView.findViewById(R.id.cbFavorite);
            ivTattoo = itemView.findViewById(R.id.ivTattoo);
            tvStyle = itemView.findViewById(R.id.tvStyle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
