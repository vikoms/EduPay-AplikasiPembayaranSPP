package com.example.latihanujikompaket2.ui.datakelas;

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
import com.example.latihanujikompaket2.entity.Kelas;

public class ManageDataKelasActivity extends AppCompatActivity implements View.OnClickListener, DataKelasViewModel.OnUpdateAddSuccess {

    private ImageView btnClose;
    private EditText edtNamaKelas, edtKompetensiKeahlian;
    private Button btnSimpanData;
    private ProgressBar pgManageKelas;
    private DataKelasViewModel viewModel;
    private String status, idKelas;

    public static final String EXTRA_DATA = "extra_data";
//    public static final int REQUEST_ADD = 100;
//    public static final int RESULT_ADD = 101;
//    public static final int REQUEST_UPDATE = 200;
//    public static final int RESULT_UPDATE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_data_kelas);
        init();
        showLoading(false);
        btnClose.setOnClickListener(this);
        btnSimpanData.setOnClickListener(this);

        Kelas kelas = getIntent().getParcelableExtra(EXTRA_DATA);
        if (kelas != null) {
            status = "Edit";
            idKelas = kelas.getKeyKelas();
            edtNamaKelas.setText(kelas.getNamaKelas());
            edtKompetensiKeahlian.setText(kelas.getJurusan());
        } else {
            status = "Add";
        }

    }

    private void init() {
        btnClose = findViewById(R.id.close_manage_petugas);
        edtNamaKelas = findViewById(R.id.edt_nama_petugas);
        edtKompetensiKeahlian = findViewById(R.id.edt_email_petugas);
        btnSimpanData = findViewById(R.id.btn_simpan_data_kelas);
        pgManageKelas = findViewById(R.id.pg_manage_data_kelas);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataKelasViewModel.class);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.close_manage_petugas) {
            finish();
        } else if (view.getId() == R.id.btn_simpan_data_kelas) {
            String namaKelas = edtNamaKelas.getText().toString();
            String komptensiKeahlian = edtKompetensiKeahlian.getText().toString();
            showLoading(true);
            if (status.equals("Add")) {
                tambahData(namaKelas, komptensiKeahlian);
            } else {
                updateData(namaKelas, komptensiKeahlian);
            }
        }
    }

    private void updateData(String namaKelas, String komptensiKeahlian) {
        viewModel.setData(namaKelas, komptensiKeahlian);
        viewModel.tambahData(idKelas);
        viewModel.setOnUpdateAddSuccess(this);
    }

    private void tambahData(String namaKelas, String komptensiKeahlian) {
        viewModel.setData(namaKelas, komptensiKeahlian);
        viewModel.tambahData(null);
        viewModel.setOnUpdateAddSuccess(this);
    }

    private void showLoading(boolean state) {
        if (state) {
            pgManageKelas.setVisibility(View.VISIBLE);
            btnSimpanData.setVisibility(View.INVISIBLE);
        } else {
            pgManageKelas.setVisibility(View.INVISIBLE);
            btnSimpanData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(boolean data, Kelas kelas, String todo) {
        if (data) {
            Toast.makeText(this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
            showLoading(true);
            finish();
        } else {
            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
        }
    }
}
