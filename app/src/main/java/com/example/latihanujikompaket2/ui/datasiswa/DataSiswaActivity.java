package com.example.latihanujikompaket2.ui.datasiswa;

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
import com.example.latihanujikompaket2.entity.Siswa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DataSiswaActivity extends AppCompatActivity implements SiswaAdapter.OnItemClickCallback {

    private RecyclerView rvSiswa;
    private SiswaAdapter adapter;
    private FloatingActionButton fabSiswa;
    private ProgressBar pgSiswa;
    private EditText edtSearch;

    private DataSiswaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_siswa);
        init();
        setupRecyclerView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Data Siswa");
        }

        adapter.setOnItemClickCallback(this);
        fabSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToManage = new Intent(DataSiswaActivity.this, ManageDataSiswaActivity.class);
                moveToManage.putExtra(ManageDataSiswaActivity.EXTRA_STATUS_USER, "Admin");
                startActivity(moveToManage);
            }
        });

        searchSiswa();
    }

    private void searchSiswa() {
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
        rvSiswa = findViewById(R.id.rv_data_siswa);
        fabSiswa = findViewById(R.id.fab_tambah_data_siswa);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        adapter = new SiswaAdapter();
        pgSiswa = findViewById(R.id.pg_data_siswa);
        edtSearch = findViewById(R.id.edt_search_siswa);

    }


    private void setupRecyclerView() {
        rvSiswa.setLayoutManager(new LinearLayoutManager(this));
        rvSiswa.setAdapter(adapter);

        viewModel.setDataSiswa();
        viewModel.getDataSiswa().observe(this, new Observer<ArrayList<Siswa>>() {
            @Override
            public void onChanged(ArrayList<Siswa> siswas) {
                if (siswas != null) {
                    adapter.setData(siswas);
                    showLoading(false);
                }
            }
        });

    }

    private void showLoading(boolean state) {
        if (state) {
            pgSiswa.setVisibility(View.VISIBLE);
            rvSiswa.setVisibility(View.INVISIBLE);
        } else {
            pgSiswa.setVisibility(View.INVISIBLE);
            rvSiswa.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(final Siswa data, String status) {
        if (status.equals("Show")) {
            Intent moveToDetail = new Intent(DataSiswaActivity.this, DetailSiswaActivity.class);
            moveToDetail.putExtra(DetailSiswaActivity.EXTRA_DATA, data);
            startActivity(moveToDetail);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Hapus data siswa")
                    .setMessage("Anda yakin akan menghapus data siswa : " + data.getNama())
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.deleteSiswa(data);
                            Toast.makeText(DataSiswaActivity.this, "Data siswa terhapus", Toast.LENGTH_SHORT).show();
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
    }
}
