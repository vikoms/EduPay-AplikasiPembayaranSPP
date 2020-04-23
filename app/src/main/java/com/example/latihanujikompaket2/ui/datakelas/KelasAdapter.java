package com.example.latihanujikompaket2.ui.datakelas;

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
import com.example.latihanujikompaket2.entity.Kelas;

import java.util.ArrayList;
import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.KelasHolder> implements Filterable {

    private ArrayList<Kelas> kelasList = new ArrayList<>();
    private List<Kelas> kelasListFiltered = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<Kelas> dataKelas) {
        kelasList.clear();
        kelasListFiltered.clear();
        kelasList.addAll(dataKelas);
        kelasListFiltered.addAll(dataKelas);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public KelasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new KelasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KelasHolder holder, int position) {
        final Kelas kelas = kelasListFiltered.get(position);
        holder.bind(kelas);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(kelasListFiltered.get(holder.getAdapterPosition()), "Delete");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(kelasListFiltered.get(holder.getAdapterPosition()), "Edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return kelasListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    kelasListFiltered = kelasList;
                } else {
                    List<Kelas> listFiltered = new ArrayList<>();
                    for (Kelas row : kelasList) {
                        if (row.getNamaKelas().toLowerCase().contains(key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }

                    kelasListFiltered = listFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = kelasListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                kelasListFiltered = (List<Kelas>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class KelasHolder extends RecyclerView.ViewHolder {
        TextView namaKelas;
        TextView jurusan;
        ImageView imgDelete;

        private KelasHolder(@NonNull View itemView) {
            super(itemView);
            namaKelas = itemView.findViewById(R.id.tv_item_first_list);
            jurusan = itemView.findViewById(R.id.tv_item_second_list);
            imgDelete = itemView.findViewById(R.id.img_list_delete);
        }

        void bind(Kelas kelas) {
            namaKelas.setText(kelas.getNamaKelas());
            jurusan.setText(kelas.getJurusan());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Kelas data, String status);
    }
}
