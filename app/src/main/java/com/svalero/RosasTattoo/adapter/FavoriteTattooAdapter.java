package com.svalero.RosasTattoo.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class FavoriteTattooAdapter extends RecyclerView.Adapter<FavoriteTattooAdapter.ViewHolder> {

    public interface Listener {
        void onInstagramChanged(FavoriteTattoo item, boolean checked);
    }

    private final List<FavoriteTattoo> data = new ArrayList<>();
    private final Listener listener;

    public FavoriteTattooAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<FavoriteTattoo> newData) {
        data.clear();
        if (newData != null) data.addAll(newData);
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
        Context context = holder.itemView.getContext();

        String style = safe(item.getStyle(), context.getString(R.string.no_style));
        holder.tvTattooId.setText(style);

        String desc = safe(item.getTattooDescription(), context.getString(R.string.no_description));
        String date = safe(item.getTattooDate(), context.getString(R.string.no_date));
        holder.tvTattooInfo.setText(desc + " · " + date);

        AppDatabase db = AppDatabase.getInstance(context);
        String localUri = db.localImageDao().getImageUri("TATTOO", item.getTattooId());

        String toLoad = (localUri != null && !localUri.trim().isEmpty())
                ? localUri
                : item.getImageUrl();

        Glide.with(context)
                .load(toLoad)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.ivTattooImage);

        holder.cbInstagram.setOnCheckedChangeListener(null);
        holder.cbInstagram.setChecked(item.isInstagram());
        holder.cbInstagram.setContentDescription(
                context.getString(R.string.instagram_checkbox_description)
        );

        holder.cbInstagram.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setInstagram(isChecked);
            if (listener != null) listener.onInstagramChanged(item, isChecked);
        });
    }

    private String safe(String value, String fallback) {
        return (value != null && !value.trim().isEmpty()) ? value : fallback;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTattooImage;
        TextView tvTattooId;
        TextView tvTattooInfo;
        CheckBox cbInstagram;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTattooImage = itemView.findViewById(R.id.ivTattooImage);
            tvTattooId = itemView.findViewById(R.id.tvTattooId);
            tvTattooInfo = itemView.findViewById(R.id.tvTattooInfo);
            cbInstagram = itemView.findViewById(R.id.cbInstagram);
        }
    }
}