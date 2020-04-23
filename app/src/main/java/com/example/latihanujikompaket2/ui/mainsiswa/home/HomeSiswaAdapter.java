package com.example.latihanujikompaket2.ui.mainsiswa.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.HistorySpp;

import java.util.ArrayList;

public class HomeSiswaAdapter extends RecyclerView.Adapter<HomeSiswaAdapter.HomeSiswaHolder> {

    private ArrayList<HistorySpp> historyList = new ArrayList<>();

    public void setData(ArrayList<HistorySpp> dataHistory) {
        historyList.clear();
        historyList.addAll(dataHistory);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeSiswaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history_spp_siswa, parent, false);
        return new HomeSiswaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSiswaHolder holder, int position) {
        HistorySpp spp = historyList.get(position);
        holder.bind(spp);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HomeSiswaHolder extends RecyclerView.ViewHolder {
        TextView bulanBayar, nominalBayar, tglBayar;

        public HomeSiswaHolder(@NonNull View itemView) {
            super(itemView);
            bulanBayar = itemView.findViewById(R.id.tv_bulan_list_history);
            nominalBayar = itemView.findViewById(R.id.tv_nominal_list_history);
            tglBayar = itemView.findViewById(R.id.tv_tanggal_list_history);
        }

        void bind(HistorySpp spp) {
            bulanBayar.setText(spp.getBulanBayar());
            nominalBayar.setText(String.format("Rp. %s", spp.getJumlahBayar()));
            tglBayar.setText(spp.getTglBayar());
        }
    }
}
