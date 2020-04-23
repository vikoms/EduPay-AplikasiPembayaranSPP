package com.example.latihanujikompaket2.ui.datasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.entity.Spp;

public class DetailSiswaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNamaHeader, tvNis, tvNama, tvNisn, tvEmail, tvKelas, tvAlamat, tvPhone, tvSpp;
    private ImageView imgBack;
    private Button btnUbahData;
    private ProgressBar pgDetailSiswa;
    private Siswa siswa;
    private DataSiswaViewModel viewModelSiswa;

    public static final String EXTRA_DATA = "extra_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_siswa);
        init();
        siswa = getIntent().getParcelableExtra(EXTRA_DATA);
        showLoading(true);
        if (siswa != null) {
            populateView();
        }

        imgBack.setOnClickListener(this);
        btnUbahData.setOnClickListener(this);
    }

    private void populateView() {
        tvNamaHeader.setText(siswa.getNama());
        tvNis.setText(siswa.getNis());
        tvNama.setText(siswa.getNama());
        tvNisn.setText(siswa.getNisn());
        tvEmail.setText(siswa.getEmail());
        tvAlamat.setText(siswa.getAlamat());
        tvPhone.setText(siswa.getPhone());

        viewModelSiswa = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        viewModelSiswa.setKelas(siswa.getKeyKelas());
        viewModelSiswa.getKelasSiswa().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    tvKelas.setText(s);
                    populateSpp();
                }else {
                    tvKelas.setText("Data kelas tidak ditemukan");
                    populateSpp();
                }
            }
        });

    }

    private void populateSpp() {
        viewModelSiswa = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        viewModelSiswa.setSpp(siswa.getKeySpp());
        viewModelSiswa.getSppSiswa().observe(this, new Observer<Spp>() {
            @Override
            public void onChanged(Spp spp) {
                if (spp != null) {
                    tvSpp.setText(String.format("Tahun %s", spp.getTahunSpp()));
                    showLoading(false);
                } else {
                    tvSpp.setText("Data SPP tidak ditemukan");
                    showLoading(false);
                }
            }
        });
    }

    private void init() {
        tvNamaHeader = findViewById(R.id.tv_nama_header_detail_siswa);
        tvNis = findViewById(R.id.tv_nis_detail_siswa);
        tvNama = findViewById(R.id.tv_id_petugas);
        tvNisn = findViewById(R.id.tv_nisn_detail_siswa);
        tvEmail = findViewById(R.id.tv_email_detail_siswa);
        tvKelas = findViewById(R.id.tv_kelas_detail_siswa);
        tvAlamat = findViewById(R.id.tv_alamat_detail_siswa);
        tvPhone = findViewById(R.id.tv_phone_detail_siswa);
        tvSpp = findViewById(R.id.tv_spp_detail_siswa);
        btnUbahData = findViewById(R.id.btn_ubah_data_siswa);
        imgBack = findViewById(R.id.img_back_detail_siswa);
        pgDetailSiswa = findViewById(R.id.pg_detail_data_siswa);


    }


    private void showLoading(boolean state) {
        if (state) {
            pgDetailSiswa.setVisibility(View.VISIBLE);
            btnUbahData.setVisibility(View.INVISIBLE);
        } else {
            pgDetailSiswa.setVisibility(View.INVISIBLE);
            btnUbahData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back_detail_siswa) {
            finish();
        } else if (view.getId() == R.id.btn_ubah_data_siswa) {
            Intent moveToEdit = new Intent(DetailSiswaActivity.this, ManageDataSiswaActivity.class);
            moveToEdit.putExtra(ManageDataSiswaActivity.EXTRA_DATA, siswa);
            moveToEdit.putExtra(ManageDataSiswaActivity.EXTRA_STATUS_USER, "Admin");
            startActivity(moveToEdit);
        }
    }
}
