package com.example.latihanujikompaket2.ui.mainsiswa.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.HistorySpp;
import com.example.latihanujikompaket2.entity.Login;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSiswaFragment extends Fragment {

    private RecyclerView rvHistorySiswa;
    private ProgressBar pgHistory;
    private TextView tvNama;
    private FloatingActionButton fabRefresh;
    private HomeSiswaAdapter homeAdapter;
    private UserPreference preference;
    private HomeSiswaViewModel viewModel;

    public HomeSiswaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHistorySiswa = view.findViewById(R.id.rv_history_spp);
        homeAdapter = new HomeSiswaAdapter();
        pgHistory = view.findViewById(R.id.pg_history);
        fabRefresh = view.findViewById(R.id.fab_refresh_history);
        tvNama = view.findViewById(R.id.tv_nama_user);
        preference = new UserPreference(getActivity());
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeSiswaViewModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            Login user = preference.getUser();
            tvNama.setText(String.format("NISN : %s", user.getIdUser()));
            showLoading(true);
            rvHistorySiswa.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvHistorySiswa.setHasFixedSize(true);
            rvHistorySiswa.setAdapter(homeAdapter);
            viewModel.setIdSiswa(user.getIdUser());
            viewModel.setDataSpp();
            viewModel.getDataHistory().observe(getViewLifecycleOwner(), new Observer<ArrayList<HistorySpp>>() {
                @Override
                public void onChanged(ArrayList<HistorySpp> historySpps) {
                    if (historySpps != null) {
                        homeAdapter.setData(historySpps);
                        showLoading(false);
                    }
                }
            });
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            pgHistory.setVisibility(View.VISIBLE);
            rvHistorySiswa.setVisibility(View.INVISIBLE);
        } else {
            pgHistory.setVisibility(View.INVISIBLE);
            rvHistorySiswa.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_siswa, container, false);
    }
}
