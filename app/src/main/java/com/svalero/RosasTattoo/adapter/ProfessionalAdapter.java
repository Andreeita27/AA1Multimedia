package com.svalero.RosasTattoo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.db.AppDatabase;
import com.svalero.RosasTattoo.domain.Professional;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ProfessionalViewHolder> {

    public interface OnProfessionalActionListener {
        void onEdit(Professional professional);
        void onDelete(Professional professional);
    }

    private List<Professional> professionals = new ArrayList<>();
    private final OnProfessionalActionListener listener;

    public ProfessionalAdapter(OnProfessionalActionListener listener) {
        this.listener = listener;
    }

    public void setData(List<Professional> professionals) {
        this.professionals = professionals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfessionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.professional_item, parent, false);
        return new ProfessionalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalViewHolder holder, int position) {
        Professional p = professionals.get(position);
        Context context = holder.itemView.getContext();

        holder.tvName.setText(p.getProfessionalName());
        holder.tvDesc.setText(p.getDescription());

        String extraInfo = context.getString(
                R.string.professional_extra_info,
                p.getYearsExperience(),
                p.isBooksOpened()
                        ? context.getString(R.string.yes)
                        : context.getString(R.string.no)
        );
        holder.tvExtra.setText(extraInfo);

        String imageUri = AppDatabase.getInstance(context)
                .localImageDao()
                .getImageUri("PROFESSIONAL", p.getId());

        if (imageUri != null && !imageUri.isEmpty()) {
            holder.ivAvatar.setImageURI(Uri.parse(imageUri));
        } else {
            holder.ivAvatar.setImageResource(R.drawable.ic_image_placeholder);
        }

        holder.btnEdit.setContentDescription(
                context.getString(R.string.edit_professional)
        );
        holder.btnDelete.setContentDescription(
                context.getString(R.string.delete_professional)
        );

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(p));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(p));
    }

    @Override
    public int getItemCount() {
        return professionals == null ? 0 : professionals.size();
    }

    static class ProfessionalViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvExtra;
        ImageButton btnEdit, btnDelete;
        ImageView ivAvatar;

        ProfessionalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProfessionalName);
            tvDesc = itemView.findViewById(R.id.tvProfessionalDesc);
            tvExtra = itemView.findViewById(R.id.tvProfessionalExtra);
            btnEdit = itemView.findViewById(R.id.btnEditProfessional);
            btnDelete = itemView.findViewById(R.id.btnDeleteProfessional);
            ivAvatar = itemView.findViewById(R.id.ivProfessionalAvatar);
        }
    }
}