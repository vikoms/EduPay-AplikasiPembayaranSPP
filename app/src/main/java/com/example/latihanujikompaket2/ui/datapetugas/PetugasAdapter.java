package com.example.latihanujikompaket2.ui.datapetugas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Petugas;

import java.util.ArrayList;
import java.util.List;

public class PetugasAdapter extends RecyclerView.Adapter<PetugasAdapter.PetugasHolder> implements Filterable {
    ArrayList<Petugas> petugasList = new ArrayList<>();
    List<Petugas> petugasListFiltered = new ArrayList<>();
    OnItemClickCallback onItemClickCallback;

    public void
    setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<Petugas> dataPetugas) {
        petugasList.clear();
        petugasListFiltered.clear();
        petugasList.addAll(dataPetugas);
        petugasListFiltered.addAll(dataPetugas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetugasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new PetugasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PetugasHolder holder, int position) {
        Petugas petugas = petugasListFiltered.get(position);
        holder.bind(petugas);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(petugasListFiltered.get(holder.getAdapterPosition()), "Delete");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(petugasListFiltered.get(holder.getAdapterPosition()), "Edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return petugasListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    petugasListFiltered = petugasList;
                } else {
                    List<Petugas> listFiltered = new ArrayList<>();
                    for (Petugas row : petugasList) {
                        if (row.getNamaPetugas().toLowerCase().contains(key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }

                    petugasListFiltered = listFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = petugasListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                petugasListFiltered = (List<Petugas>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class PetugasHolder extends RecyclerView.ViewHolder {

        TextView namaPetugas;
        TextView levelPetugas;
        ImageView imgDelete;

        public PetugasHolder(@NonNull View itemView) {
            super(itemView);
            namaPetugas = itemView.findViewById(R.id.tv_item_first_list);
            levelPetugas = itemView.findViewById(R.id.tv_item_second_list);
            imgDelete = itemView.findViewById(R.id.img_list_delete);
        }

        void bind(Petugas petugas) {
            namaPetugas.setText(petugas.getNamaPetugas());
            levelPetugas.setText(petugas.getLevel());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Petugas data, String status);
    }
}
