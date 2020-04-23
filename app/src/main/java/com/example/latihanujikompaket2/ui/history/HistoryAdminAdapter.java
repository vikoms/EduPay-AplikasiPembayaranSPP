package com.example.latihanujikompaket2.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.HistorySpp;

import java.util.ArrayList;

public class HistoryAdminAdapter extends RecyclerView.Adapter<HistoryAdminAdapter.HistoryAdminHolder> {

    private ArrayList<HistorySpp> historyList = new ArrayList<>();
    private HistoryViewModel viewModel;
    OnClickCallback onClickCallback;

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }


    public void setViewModel(HistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setData(ArrayList<HistorySpp> dataHistory) {
        historyList.clear();
        historyList.addAll(dataHistory);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HistoryAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history_spp, parent, false);
        return new HistoryAdminHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdminHolder holder, int position) {
        HistorySpp spp = historyList.get(position);
        holder.bind(spp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCallback.onClicked(historyList.get(holder.getAdapterPosition()), "Detail");
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


    public class HistoryAdminHolder extends RecyclerView.ViewHolder {
        TextView namaSiswa, nominalBayar, tglBayar;

        public HistoryAdminHolder(@NonNull View itemView) {
            super(itemView);
            namaSiswa = itemView.findViewById(R.id.tv_nama_list_history);
            nominalBayar = itemView.findViewById(R.id.tv_nominal_list_history);
            tglBayar = itemView.findViewById(R.id.tv_tanggal_list_history);
        }

        void bind(HistorySpp spp) {
            namaSiswa.setText(spp.getNamaSiswa());
            nominalBayar.setText(String.format("Rp. %s", spp.getJumlahBayar()));
            tglBayar.setText(spp.getTglBayar());
        }
    }

    interface OnClickCallback {
        void onClicked(HistorySpp data, String action);
    }
}
