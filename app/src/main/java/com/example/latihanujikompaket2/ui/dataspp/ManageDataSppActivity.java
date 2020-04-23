package com.example.latihanujikompaket2.ui.dataspp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Spp;

public class ManageDataSppActivity extends AppCompatActivity implements View.OnClickListener, DataSppViewModel.OnUpdateAddSuccess {

    private ImageView btnClose;
    private Button btnSimpan;
    private EditText edtTahun, edtNominal;
    private ProgressBar pgSpp;
    private DataSppViewModel viewModel;
    private String idKelas, status;
    public static final String EXTRA_DATA = "Extra_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_data_spp);
        init();
        showLoading(false);

        Spp spp = getIntent().getParcelableExtra(EXTRA_DATA);
        if (spp != null) {
            edtTahun.setText(spp.getTahunSpp());
            edtNominal.setText(String.valueOf(spp.getNominal()));
            idKelas = spp.getKeySpp();
            status = "Edit";
        } else {
            status = "Add";
        }

        btnSimpan.setOnClickListener(this);
        btnClose.setOnClickListener(this);

    }

    private void init() {
        btnClose = findViewById(R.id.close_manage_petugas);
        btnSimpan = findViewById(R.id.btn_simpan_data_spp);
        edtTahun = findViewById(R.id.edt_nama_petugas);
        edtNominal = findViewById(R.id.edt_email_petugas);
        pgSpp = findViewById(R.id.pg_manage_data_spp);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSppViewModel.class);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_simpan_data_spp) {
            String tahun = edtTahun.getText().toString().trim();
            int nominal = Integer.parseInt(edtNominal.getText().toString().trim());
            showLoading(true);
            if (status.equals("Add")) {
                tambahData(tahun, nominal);
            } else {
                updateData(tahun, nominal);
            }
        } else if (id == R.id.close_manage_petugas) {
            finish();
        }
    }

    private void updateData(String tahun, int nominal) {
        viewModel.setData(tahun, nominal);
        viewModel.simpanData(idKelas);
        viewModel.setOnUpdateAddSuccess(this);
    }

    private void tambahData(String tahun, int nominal) {
        viewModel.setData(tahun, nominal);
        viewModel.simpanData(null);
        viewModel.setOnUpdateAddSuccess(this);
    }

    private void showLoading(boolean state) {
        if (state) {
            pgSpp.setVisibility(View.VISIBLE);
            btnSimpan.setVisibility(View.INVISIBLE);
        } else {
            pgSpp.setVisibility(View.INVISIBLE);
            btnSimpan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(boolean state) {
        if (state) {
            Toast.makeText(this, "Simpan Data Berhasil", Toast.LENGTH_SHORT).show();
            showLoading(true);
            finish();
        }
    }
}
