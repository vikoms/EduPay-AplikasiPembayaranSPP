package com.example.latihanujikompaket2.ui.datasiswa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Kelas;
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.entity.Spp;
import com.example.latihanujikompaket2.ui.datakelas.DataKelasViewModel;
import com.example.latihanujikompaket2.ui.dataspp.DataSppViewModel;
import com.example.latihanujikompaket2.ui.mainsiswa.MainSiswaActivity;

import java.util.ArrayList;

public class ManageDataSiswaActivity extends AppCompatActivity implements View.OnClickListener, DataSiswaViewModel.OnSuccessCallBack {

    private EditText edtNisn, edtNis, edtEmail, edtNama, edtAlamat, edtPhone, edtPass;
    private Spinner spinnerKelas, spinnerSpp;
    private TextView tvTitlePass, tvAlertSpp, tvAlertKelas;
    private Button btnSimpan, btnGantiPassword;
    private ProgressBar pgSiswa;
    private DataSiswaViewModel viewModelSiswa;
    private DataKelasViewModel viewModelKelas;
    private DataSppViewModel viewModelSpp;

    private final String FIELD_REQUIRED = "Field tidak boleh kosong";
    private final String FIELD_MIN_CHAR = "Password min 6 karakter";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_STATUS_USER = "EXTRA_STATUS_USER";
    private String idSiswa, status, statusUser;
    private Siswa siswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data_siswa);
        init();
        showLoading(true);
        setUpSpinner();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambah/Ubah Data Siswa");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        siswa = getIntent().getParcelableExtra(EXTRA_DATA);
        statusUser = getIntent().getStringExtra(EXTRA_STATUS_USER);
        if (siswa != null) {
            status = "Edit";
            idSiswa = siswa.getNisn();
            edtNisn.setEnabled(false);
            edtPass.setVisibility(View.GONE);
            tvTitlePass.setVisibility(View.GONE);
            tvAlertKelas.setVisibility(View.VISIBLE);
            tvAlertSpp.setVisibility(View.VISIBLE);
            btnGantiPassword.setVisibility(View.VISIBLE);
            btnGantiPassword.setOnClickListener(this);
            populateKelasAndSpp();
            populateView(siswa);
        } else {
            status = "Add";
            idSiswa = null;
        }

        btnSimpan.setOnClickListener(this);
    }

    private void populateKelasAndSpp() {
        viewModelSiswa.setKelas(siswa.getKeyKelas());
        viewModelSiswa.getKelasSiswa().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    tvAlertKelas.setText(String.format(getResources().getString(R.string.data_sebelumnya), s));
                }
            }
        });

        viewModelSiswa.setSpp(siswa.getKeySpp());
        viewModelSiswa.getSppSiswa().observe(this, new Observer<Spp>() {
            @Override
            public void onChanged(Spp spp) {
                if (spp != null) {
                    tvAlertSpp.setText(String.format(getResources().getString(R.string.data_sebelumnya), "Tahun " + spp.getTahunSpp()));
                }
            }
        });
    }

    private void populateView(Siswa siswa) {
        edtNisn.setText(siswa.getNisn());
        edtNis.setText(siswa.getNis());
        edtEmail.setText(siswa.getEmail());
        edtNama.setText(siswa.getNama());
        edtAlamat.setText(siswa.getAlamat());
        edtPhone.setText(siswa.getPhone());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        edtNisn = findViewById(R.id.edt_nisn_siswa);
        edtNis = findViewById(R.id.edt_nis_siswa);
        edtEmail = findViewById(R.id.edt_email_siswa);
        edtNama = findViewById(R.id.edt_nama_siswa);
        edtAlamat = findViewById(R.id.edt_alamat_siswa);
        edtPhone = findViewById(R.id.edt_telp_siswa);
        edtPass = findViewById(R.id.edt_pass_siswa);
        pgSiswa = findViewById(R.id.pg_manage_data_siswa);
        btnSimpan = findViewById(R.id.btn_simpan_data_siswa);
        spinnerKelas = findViewById(R.id.spinner_kelas_siswa);
        spinnerSpp = findViewById(R.id.spinner_spp_siswa);
        tvTitlePass = findViewById(R.id.tv_title_pass);
        tvAlertKelas = findViewById(R.id.tv_alert_kelas);
        tvAlertSpp = findViewById(R.id.tv_alert_spp);
        btnGantiPassword = findViewById(R.id.btn_siswa_change_password);


        viewModelSiswa = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        viewModelSpp = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSppViewModel.class);
        viewModelKelas = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataKelasViewModel.class);
    }


    private void showLoading(boolean state) {
        if (state) {
            pgSiswa.setVisibility(View.VISIBLE);
            btnSimpan.setVisibility(View.INVISIBLE);
        } else {
            pgSiswa.setVisibility(View.INVISIBLE);
            btnSimpan.setVisibility(View.VISIBLE);
        }
    }

    private void setUpSpinner() {

        viewModelKelas.setDataKelas();
        viewModelKelas.getData().observe(this, new Observer<ArrayList<Kelas>>() {
            @Override
            public void onChanged(ArrayList<Kelas> kelas) {
                if (kelas != null) {
                    ArrayAdapter<Kelas> adapterKelas = new ArrayAdapter<>(ManageDataSiswaActivity.this, android.R.layout.simple_spinner_item, kelas);
                    adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKelas.setAdapter(adapterKelas);

                    setupSpinnerSPP();
                }
            }
        });
    }

    private void setupSpinnerSPP() {

        viewModelSpp.setDataSpp();
        viewModelSpp.getDataSpp().observe(this, new Observer<ArrayList<Spp>>() {
            @Override
            public void onChanged(ArrayList<Spp> spps) {
                if (spps != null) {
                    ArrayAdapter<Spp> adapterSpp = new ArrayAdapter<>(ManageDataSiswaActivity.this, android.R.layout.simple_spinner_item, spps);
                    adapterSpp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSpp.setAdapter(adapterSpp);
                    showLoading(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_simpan_data_siswa) {
            showLoading(true);
            String nisn = edtNisn.getText().toString().trim();
            String nis = edtNis.getText().toString().trim();
            String nama = edtNama.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String pass = status.equals("Edit") ? siswa.getPassword() : edtPass.getText().toString().trim();
            String alamat = edtAlamat.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            Kelas kelas = (Kelas) spinnerKelas.getSelectedItem();
            String keyKelas = kelas.getKeyKelas();
            Spp spp = (Spp) spinnerSpp.getSelectedItem();
            String keySpp = spp.getKeySpp();

            if (nisn.isEmpty()) {
                edtNisn.setText(FIELD_REQUIRED);
                return;
            }
            if (nis.isEmpty()) {
                edtNis.setText(FIELD_REQUIRED);
                return;
            }
            if (nama.isEmpty()) {
                edtNama.setText(FIELD_REQUIRED);
                return;
            }
            if (email.isEmpty()) {
                edtEmail.setText(FIELD_REQUIRED);
                return;
            }
            if (status.equals("Add")) {
                if (pass.isEmpty()) {
                    edtPass.setText(FIELD_REQUIRED);
                    return;
                }

                if (pass.length() < 6) {
                    edtPass.setError(FIELD_MIN_CHAR);
                    return;
                }
            }
            if (alamat.isEmpty()) {
                edtAlamat.setText(FIELD_REQUIRED);
                return;
            }
            if (phone.isEmpty()) {
                edtPass.setText(FIELD_REQUIRED);
                return;
            }

            viewModelSiswa.setData(nisn, nis, nama, email, keyKelas, alamat, phone, keySpp, pass);

            if (status.equals("Edit")) {
                viewModelSiswa.editData(idSiswa);
            } else {
                viewModelSiswa.simpanData();
            }

            viewModelSiswa.setOnSuccessCallBack(this);

        } else if (view.getId() == R.id.btn_siswa_change_password) {
            showDialog();
        }
    }

    private void showDialog() {
        final Dialog myDialog = new Dialog(ManageDataSiswaActivity.this);
        myDialog.setContentView(R.layout.dialog_change_password);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText edtNewPassword = myDialog.findViewById(R.id.edt_new_password);
        final EditText edtOldPassword = myDialog.findViewById(R.id.edt_old_password);
        final Button btnChangePassword = myDialog.findViewById(R.id.btn_simpan_password);
        final ProgressBar pgChangePass = myDialog.findViewById(R.id.pg_change_password);

        myDialog.show();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgChangePass.setVisibility(View.VISIBLE);
                btnChangePassword.setVisibility(View.INVISIBLE);

                String oldPass = edtOldPassword.getText().toString();
                String newPass = edtNewPassword.getText().toString();

                if (TextUtils.isEmpty(newPass)) {
                    edtNewPassword.setError(FIELD_REQUIRED);
                    edtNewPassword.setFocusable(true);

                    pgChangePass.setVisibility(View.GONE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                    return;
                }

                if (newPass.length() < 6) {
                    edtNewPassword.setError(FIELD_MIN_CHAR);
                    edtNewPassword.setFocusable(true);

                    pgChangePass.setVisibility(View.GONE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                    return;
                }

                if (!oldPass.equals(siswa.getPassword())) {
                    Toast.makeText(ManageDataSiswaActivity.this, "Data password sebelumnya tidak sama", Toast.LENGTH_SHORT).show();
                    pgChangePass.setVisibility(View.GONE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                } else if (oldPass.equals(siswa.getPassword())) {
                    siswa.setPassword(newPass);
                    viewModelSiswa.changePassword(siswa);
                    viewModelSiswa.setOnSuccessCallBack(ManageDataSiswaActivity.this);
                    myDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onSuccess(boolean state) {
        if (state) {
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            if (statusUser.equals("Siswa")) {
                startActivity(new Intent(ManageDataSiswaActivity.this, MainSiswaActivity.class));
            } else {
                startActivity(new Intent(ManageDataSiswaActivity.this, DataSiswaActivity.class));
            }
            finish();
            showLoading(false);
        } else if (!state) {
            edtNisn.setError("NISN sudah ada");
            showLoading(false);
        }

    }

}
