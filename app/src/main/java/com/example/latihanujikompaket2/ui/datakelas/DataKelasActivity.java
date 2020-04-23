package com.example.latihanujikompaket2.ui.datakelas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Kelas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DataKelasActivity extends AppCompatActivity implements DataKelasViewModel.OnUpdateAddSuccess, KelasAdapter.OnItemClickCallback {

    private RecyclerView rvKelas;
    private KelasAdapter adapter;
    private FloatingActionButton fabKelas;
    private ProgressBar pgKelas;
    private DataKelasViewModel viewModel;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kelas);
        init();
        setupRecyclerView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Data Kelas");
        }

        fabKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToManage = new Intent(DataKelasActivity.this, ManageDataKelasActivity.class);
                startActivity(moveToManage);
            }
        });

        searchKelas();
    }

    private void searchKelas() {
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

    private void setupRecyclerView() {
        rvKelas.setLayoutManager(new LinearLayoutManager(this));
        rvKelas.setAdapter(adapter);

        viewModel.setDataKelas();
        viewModel.getData().observe(this, new Observer<ArrayList<Kelas>>() {
            @Override
            public void onChanged(ArrayList<Kelas> kelas) {
                if (kelas != null) {
                    adapter.setData(kelas);
                    showLoading(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void init() {
        rvKelas = findViewById(R.id.rv_data_kelas);
        fabKelas = findViewById(R.id.fab_tambah_data_kelas);
        adapter = new KelasAdapter();
        pgKelas = findViewById(R.id.pg_data_kelas);
        edtSearch = findViewById(R.id.edt_search_kelas);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataKelasViewModel.class);
        adapter.setOnItemClickCallback(this);
    }


    private void showLoading(boolean state) {
        if (state) {
            pgKelas.setVisibility(View.VISIBLE);
            rvKelas.setVisibility(View.INVISIBLE);
        } else {
            pgKelas.setVisibility(View.INVISIBLE);
            rvKelas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(boolean data, Kelas kelas, String todo) {
    }

    @Override
    public void onItemClicked(final Kelas data, String status) {
        if (status.equals("Delete")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DataKelasActivity.this)
                    .setTitle("Hapus data siswa")
                    .setMessage("Anda yakin akan menghapus data siswa : " + data.getNamaKelas())
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.deleteData(data);
                            viewModel.setOnUpdateAddSuccess(DataKelasActivity.this);
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
        }
        if (status.equals("Edit")) {
            Intent moveToManage = new Intent(DataKelasActivity.this, ManageDataKelasActivity.class);
            moveToManage.putExtra(ManageDataKelasActivity.EXTRA_DATA, data);
            startActivity(moveToManage);
        }
    }
}
