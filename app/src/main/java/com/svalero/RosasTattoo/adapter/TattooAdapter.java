package com.svalero.RosasTattoo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.domain.Tattoo;

import java.util.ArrayList;
import java.util.List;

public class TattooAdapter extends RecyclerView.Adapter<TattooAdapter.TattooViewHolder> {

    private List<Tattoo> tattoos = new ArrayList<>();

    public void setData(List<Tattoo> tattoos) {
        this.tattoos = tattoos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TattooViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_tattoo_detail_view, parent, false);
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
    }

    @Override
    public int getItemCount() {
        return tattoos == null ? 0 : tattoos.size();
    }

    static class TattooViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTattoo;
        TextView tvStyle, tvDesc;

        TattooViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTattoo = itemView.findViewById(R.id.ivTattoo);
            tvStyle = itemView.findViewById(R.id.tvStyle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
