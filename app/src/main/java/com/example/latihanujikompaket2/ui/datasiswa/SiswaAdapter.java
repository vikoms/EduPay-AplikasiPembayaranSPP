package com.example.latihanujikompaket2.ui.datasiswa;

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
import com.example.latihanujikompaket2.entity.Siswa;

import java.util.ArrayList;
import java.util.List;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.SiswaHolder> implements Filterable {

    ArrayList<Siswa> siswaList = new ArrayList<>();
    List<Siswa> siswaListFiltered = new ArrayList<>();
    OnItemClickCallback onItemClickCallback;

    public SiswaAdapter() {
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<Siswa> dataSiswa) {
        siswaList.clear();
        siswaListFiltered.clear();
        siswaList.addAll(dataSiswa);
        siswaListFiltered.addAll(dataSiswa);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SiswaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new SiswaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SiswaHolder holder, int position) {
        Siswa siswa = siswaListFiltered.get(position);
        holder.bind(siswa);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(siswaListFiltered.get(holder.getAdapterPosition()), "Show");
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(siswaListFiltered.get(holder.getAdapterPosition()), "Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return siswaListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    siswaListFiltered = siswaList;
                } else {
                    List<Siswa> listFiltered = new ArrayList<>();
                    for (Siswa row : siswaList) {
                        if (row.getNama().toLowerCase().contains(key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }

                    siswaListFiltered = listFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = siswaListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                siswaListFiltered = (List<Siswa>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class SiswaHolder extends RecyclerView.ViewHolder {

        TextView namaSiswa;
        TextView nisnSiswa;
        ImageView imgDelete;

        public SiswaHolder(@NonNull View itemView) {
            super(itemView);
            namaSiswa = itemView.findViewById(R.id.tv_item_first_list);
            nisnSiswa = itemView.findViewById(R.id.tv_item_second_list);
            imgDelete = itemView.findViewById(R.id.img_list_delete);

        }

        void bind(final Siswa siswa) {
            namaSiswa.setText(siswa.getNama());
            nisnSiswa.setText("NISN : " + siswa.getNisn());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Siswa data, String status);
    }
}



