package com.example.latihanujikompaket2.ui.dataspp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Spp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DataSppActivity extends AppCompatActivity {
    private RecyclerView rvSpp;
    private SppAdapter adapter;
    private FloatingActionButton fabSpp;
    private ProgressBar pgSpp;
    private DataSppViewModel viewModel;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_spp);

        init();
        setupRecyclerView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Data SPP");
        }

        fabSpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToManage = new Intent(DataSppActivity.this, ManageDataSppActivity.class);
                startActivity(moveToManage);
            }
        });
        searchSpp();
    }

    private void searchSpp() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        rvSpp = findViewById(R.id.rv_data_spp);
        fabSpp = findViewById(R.id.fab_tambah_data_spp);
        adapter = new SppAdapter();
        pgSpp = findViewById(R.id.pg_data_spp);
        edtSearch = findViewById(R.id.edt_search_spp);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSppViewModel.class);
        adapter.setOnItemClickCallback(new SppAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(final Spp data, String status) {
                if (status.equals("Delete")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataSppActivity.this)
                            .setTitle("Hapus data siswa")
                            .setMessage("Anda yakin akan menghapus data SPP Tahun : " + data.getTahunSpp())
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    viewModel.deleteData(data);
                                    Toast.makeText(DataSppActivity.this, "hapus data berhasil", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Intent moveToManage = new Intent(DataSppActivity.this, ManageDataSppActivity.class);
                    moveToManage.putExtra(ManageDataSppActivity.EXTRA_DATA, data);
                    startActivity(moveToManage);
                }
            }
        });
    }

    private void setupRecyclerView() {
        rvSpp.setLayoutManager(new LinearLayoutManager(this));
        rvSpp.setAdapter(adapter);

        viewModel.setDataSpp();
        viewModel.getDataSpp().observe(this, new Observer<ArrayList<Spp>>() {
            @Override
            public void onChanged(ArrayList<Spp> spps) {
                if (spps != null) {
                    adapter.setData(spps);
                    showLoading(false);
                }
            }
        });

    }


    private void showLoading(boolean state) {
        if (state) {
            pgSpp.setVisibility(View.VISIBLE);
            rvSpp.setVisibility(View.INVISIBLE);
        } else {
            pgSpp.setVisibility(View.INVISIBLE);
            rvSpp.setVisibility(View.VISIBLE);
        }
    }
}
