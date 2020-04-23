package com.example.latihanujikompaket2.ui.history;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.detailpembayaran.DetailPembayaranActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, HistoryAdminAdapter.OnClickCallback {

    public HistoryFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvHistoryAdmin;
    private HistoryAdminAdapter historyAdapter;
    private Button btnPilihTahun;
    private ProgressBar pgHistory;
    private UserPreference sharedPreferences;
    private HistoryViewModel viewModel;
    private FloatingActionButton fabRefresh;
    private TextView tvTahun, tvnamauser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            setupRecyclerView();
            tvTahun.setText("");
            btnPilihTahun.setOnClickListener(this);
            fabRefresh.setOnClickListener(this);
            Login user = sharedPreferences.getUser();
            tvnamauser.setText(String.format(getContext().getResources().getString(R.string.text_hello_admin), user.getStatus()));

        }
    }

    private void setupRecyclerView() {
        final ArrayList<Siswa> listSiswa = new ArrayList<>();
        rvHistoryAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHistoryAdmin.setAdapter(historyAdapter);
        rvHistoryAdmin.setHasFixedSize(true);
        viewModel.setDataHistory();
        viewModel.getDataHistory().observe(getViewLifecycleOwner(), new Observer<ArrayList<HistorySpp>>() {
            @Override
            public void onChanged(ArrayList<HistorySpp> historySpps) {
                if (historySpps != null) {
                    historyAdapter.setData(historySpps);
                    showLoading(false);
                } else {
                    Toast.makeText(getContext(), "Data tidak ada", Toast.LENGTH_SHORT).show();
                    showLoading(false);
                }
            }
        });

        historyAdapter.setOnClickCallback(this);
    }

    private void init(View view) {
        rvHistoryAdmin = view.findViewById(R.id.rv_history_spp);
        btnPilihTahun = view.findViewById(R.id.btn_pilih_tahun);
        historyAdapter = new HistoryAdminAdapter();
        pgHistory = view.findViewById(R.id.pg_history);
        fabRefresh = view.findViewById(R.id.fab_refresh_history);
        tvTahun = view.findViewById(R.id.tv_tahun);
        tvnamauser = view.findViewById(R.id.tv_nama_user);
        sharedPreferences = new UserPreference(getActivity());
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }


    private void showLoading(boolean state) {
        if (state) {
            pgHistory.setVisibility(View.VISIBLE);
            rvHistoryAdmin.setVisibility(View.INVISIBLE);
        } else {
            pgHistory.setVisibility(View.INVISIBLE);
            rvHistoryAdmin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pilih_tahun) {
            showDatePickerDialog();
        } else if (view.getId() == R.id.fab_refresh_history) {
            setupRecyclerView();
            tvTahun.setText("");
        }
    }

    private void showDatePickerDialog() {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, this, mYear, mMonth, mDay);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        showLoading(true);
        viewModel.setDataHistoryByTahun(String.valueOf(year));
        viewModel.getDataHistory().observe(getViewLifecycleOwner(), new Observer<ArrayList<HistorySpp>>() {
            @Override
            public void onChanged(ArrayList<HistorySpp> historySpps) {
                if (historySpps != null) {
                    if (historySpps != null) {
                        historyAdapter.setData(historySpps);
                        showLoading(false);
                    } else if (historySpps == null) {
                        Toast.makeText(getContext(), "Data tidak ada", Toast.LENGTH_SHORT).show();
                        showLoading(false);
                    }
                }
            }
        });

        tvTahun.setText(String.format(getContext().getResources().getString(R.string.text_tahun_history), String.valueOf(year)));

    }

    @Override
    public void onClicked(HistorySpp data, String action) {
        if (action.equals("Detail")) {
            Intent moveToDetail = new Intent(getActivity(), DetailPembayaranActivity.class);
            moveToDetail.putExtra(DetailPembayaranActivity.EXTRA_DATA, data);
            getActivity().startActivity(moveToDetail);
        }
    }


}
