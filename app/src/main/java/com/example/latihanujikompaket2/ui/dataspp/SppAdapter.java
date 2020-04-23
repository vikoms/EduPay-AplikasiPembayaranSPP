package com.example.latihanujikompaket2.ui.dataspp;

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
import com.example.latihanujikompaket2.entity.Spp;

import java.util.ArrayList;
import java.util.List;

public class SppAdapter extends RecyclerView.Adapter<SppAdapter.SppHolder> implements Filterable {

    ArrayList<Spp> sppList = new ArrayList<>();
    List<Spp> sppListFiltered = new ArrayList<>();
    OnItemClickCallback onItemClickCallback;


    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<Spp> dataSpp) {
        sppList.clear();
        sppListFiltered.clear();
        sppList.addAll(dataSpp);
        sppListFiltered.addAll(dataSpp);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new SppHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SppHolder holder, int position) {
        Spp spp = sppListFiltered.get(position);
        holder.bind(spp);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(sppListFiltered.get(holder.getAdapterPosition()), "Delete");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(sppListFiltered.get(holder.getAdapterPosition()), "Edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return sppListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    sppListFiltered = sppList;
                } else {
                    List<Spp> listFiltered = new ArrayList<>();
                    for (Spp row : sppList) {
                        if (row.getTahunSpp().toLowerCase().contains(key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }

                    sppListFiltered = listFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sppListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sppListFiltered = (List<Spp>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SppHolder extends RecyclerView.ViewHolder {
        TextView namaSpp;
        TextView nominalSpp;
        ImageView imgDelete;

        public SppHolder(@NonNull View itemView) {
            super(itemView);
            namaSpp = itemView.findViewById(R.id.tv_item_first_list);
            nominalSpp = itemView.findViewById(R.id.tv_item_second_list);
            imgDelete = itemView.findViewById(R.id.img_list_delete);
        }

        void bind(Spp spp) {
            namaSpp.setText("Tahun : " + spp.getTahunSpp());
            nominalSpp.setText("Nominal : " + spp.getNominal());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Spp data, String status);
    }
}
