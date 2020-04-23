package com.example.latihanujikompaket2.ui.datapetugas;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Petugas;

public class ManageDataPetugasActivity extends AppCompatActivity implements View.OnClickListener, DataPetugasViewModel.OnSuccessCallback {

    private ImageView btnClose;
    private Button btnSimpan, btnChangePass;
    private EditText edtNama, edtPass, edtNip;
    private Spinner spinnerLevel;
    private TextView tvAlertLevel, tvTitlePass;
    private ProgressBar pgPetugas;
    private DataPetugasViewModel viewModel;

    public static final String EXTRA_DATA = "extra_data";
    private final String FIELD_REQUIRED = "Field TIdak Boleh Kosong";
    private final String FIELD_DIGIT_ONLY = "Hanya boleh diisi numerik";
    private final String FIELD_MIN_CHAR = "Password min 6 karakter";
    private Petugas petugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage_data_petugas);
        init();
        showLoading(false);

        petugas = getIntent().getParcelableExtra(EXTRA_DATA);
        if (petugas != null) {
            edtNip.setEnabled(false);
            btnChangePass.setVisibility(View.VISIBLE);
            populateView();
        }

        btnSimpan.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void populateView() {
        tvTitlePass.setVisibility(View.GONE);
        edtPass.setVisibility(View.GONE);
        tvAlertLevel.setVisibility(View.VISIBLE);
        btnChangePass.setVisibility(View.VISIBLE);
        edtNama.setText(petugas.getNamaPetugas());
        edtNip.setText(petugas.getIdPetugas());
        tvAlertLevel.setText(String.format(getResources().getString(R.string.data_sebelumnya), petugas.getLevel()));
        btnChangePass.setOnClickListener(this);
    }

    private void init() {
        btnClose = findViewById(R.id.close_manage_petugas);
        btnSimpan = findViewById(R.id.btn_ubah_data_siswa);
        edtNama = findViewById(R.id.edt_nama_petugas);
        edtPass = findViewById(R.id.edt_pass_petugas);
        edtNip = findViewById(R.id.edt_nip_petugas);
        pgPetugas = findViewById(R.id.pg_manage_data_petugas);
        spinnerLevel = findViewById(R.id.spinner_level_petugas);
        tvAlertLevel = findViewById(R.id.tv_alert_level);
        tvTitlePass = findViewById(R.id.tv_title_password_petugas);
        btnChangePass = findViewById(R.id.btn_petugas_change_password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levels_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataPetugasViewModel.class);

    }

    private void showLoading(boolean state) {
        if (state) {
            pgPetugas.setVisibility(View.VISIBLE);
            btnSimpan.setVisibility(View.INVISIBLE);
        } else {
            pgPetugas.setVisibility(View.INVISIBLE);
            btnSimpan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_ubah_data_siswa) {
            String nama = edtNama.getText().toString();
            String level = spinnerLevel.getSelectedItem().toString();
            String pass = petugas != null ? petugas.getPassword() : edtPass.getText().toString();
            String nip = edtNip.getText().toString();

            if (TextUtils.isEmpty(nama)) {
                edtNama.setError(FIELD_REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(nip)) {
                edtNip.setError(FIELD_REQUIRED);
                return;
            }
            if (!TextUtils.isDigitsOnly(nip)) {
                edtNip.setError(FIELD_DIGIT_ONLY);
                return;
            }
            if (petugas == null) {
                if (pass.length() < 6) {
                    edtPass.setError(FIELD_MIN_CHAR);
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    edtPass.setError(FIELD_REQUIRED);
                    return;
                }
            }
            showLoading(true);
            simpaData(level, nama, pass, nip);
        } else if (view.getId() == R.id.close_manage_petugas) {
            finish();
        } else if (view.getId() == R.id.btn_petugas_change_password) {
            showDialog();
        }
    }

    private void showDialog() {
        final Dialog myDialog = new Dialog(ManageDataPetugasActivity.this);
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

                if (!oldPass.equals(petugas.getPassword())) {
                    Toast.makeText(ManageDataPetugasActivity.this, "Data password sebelumnya tidak sama", Toast.LENGTH_SHORT).show();
                    pgChangePass.setVisibility(View.GONE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                } else if (oldPass.equals(petugas.getPassword())) {
                    petugas.setPassword(newPass);
                    viewModel.changePassword(petugas);
                    viewModel.setOnSuccessCallback(ManageDataPetugasActivity.this);
                    myDialog.dismiss();
                }
            }
        });
    }


    private void simpaData(String level, String nama, String pass, String nip) {
        viewModel.setData(level, nama, pass, nip);

        if (petugas != null) {
            viewModel.editPetugas(petugas.getIdPetugas());
        } else if (petugas == null) {
            viewModel.simpanData();
        }

        viewModel.setOnSuccessCallback(this);
    }

    @Override
    public void onSuccess(boolean state, String where) {
        if (state) {
            if (where.equals("ganti_password")) {
                Toast.makeText(this, "Ubah password berhasil", Toast.LENGTH_SHORT).show();
                showLoading(false);
                finish();
            } else {
                Toast.makeText(this, "Menyimpan data user berhasil", Toast.LENGTH_SHORT).show();
                showLoading(false);
                finish();
            }
        } else if (state == false) {
            Toast.makeText(this, "Nip sudah ada", Toast.LENGTH_SHORT).show();
            showLoading(false);
        }
    }
}
