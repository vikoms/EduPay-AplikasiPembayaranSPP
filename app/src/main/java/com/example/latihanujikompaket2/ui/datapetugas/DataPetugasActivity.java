package com.example.latihanujikompaket2.ui.datapetugas;

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
import com.example.latihanujikompaket2.entity.Petugas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DataPetugasActivity extends AppCompatActivity {
    private RecyclerView rvPetugas;
    private PetugasAdapter adapter;
    private FloatingActionButton fabPetugas;
    private ProgressBar pgPetugas;
    private DataPetugasViewModel viewModel;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_petugas);
        init();
        setupRecyclerView();
        showLoading(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Data Petugas");
        }
        fabPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToManage = new Intent(DataPetugasActivity.this, ManageDataPetugasActivity.class);
                startActivity(moveToManage);
            }
        });

        searchPetugas();
    }

    private void searchPetugas() {
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
        rvPetugas = findViewById(R.id.rv_data_petugas);
        fabPetugas = findViewById(R.id.fab_tambah_data_petugas);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataPetugasViewModel.class);
        adapter = new PetugasAdapter();
        pgPetugas = findViewById(R.id.pg_data_petugas);
        edtSearch = findViewById(R.id.edt_search_petugas);

        adapter.setOnItemClickCallback(new PetugasAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(final Petugas data, String status) {
                if (status.equals("Delete")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataPetugasActivity.this)
                            .setTitle("Hapus data siswa")
                            .setMessage("Anda yakin akan menghapus data Petugas  : " + data.getNamaPetugas())
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    viewModel.deleteData(data);
                                    Toast.makeText(DataPetugasActivity.this, "hapus data berhasil", Toast.LENGTH_SHORT).show();
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
                } else if (status.equals("Edit")) {
                    Intent moveToManage = new Intent(DataPetugasActivity.this, ManageDataPetugasActivity.class);
                    moveToManage.putExtra(ManageDataPetugasActivity.EXTRA_DATA, data);
                    startActivity(moveToManage);
                }
            }
        });
    }

    private void setupRecyclerView() {
        rvPetugas.setLayoutManager(new LinearLayoutManager(this));
        rvPetugas.setAdapter(adapter);

        viewModel.setDataPetugas();
        viewModel.getDataPetugas().observe(this, new Observer<ArrayList<Petugas>>() {
            @Override
            public void onChanged(ArrayList<Petugas> petugases) {
                if (petugases != null) {
                    adapter.setData(petugases);
                    showLoading(false);
                }
            }
        });

    }

    private void showLoading(boolean state) {
        if (state) {
            pgPetugas.setVisibility(View.VISIBLE);
            rvPetugas.setVisibility(View.INVISIBLE);
        } else {
            pgPetugas.setVisibility(View.INVISIBLE);
            rvPetugas.setVisibility(View.VISIBLE);
        }
    }
}
