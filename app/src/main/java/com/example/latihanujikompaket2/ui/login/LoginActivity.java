package com.example.latihanujikompaket2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Login;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.admin.MainAdminActivity;
import com.example.latihanujikompaket2.ui.mainpetugas.MainPetugasActivity;
import com.example.latihanujikompaket2.ui.mainsiswa.MainSiswaActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginViewModel.OnLoginSuccessCallback {


    private LoginViewModel viewModel;
    private EditText edtIdUser, edtPass;
    private Button btnLogin;
    private ProgressBar pgLogin;
    private String idUser;
    private final String FIELD_REQUIRED = "Field TIdak Boleh Kosong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        showLoading(false);
        btnLogin.setOnClickListener(this);
        checkStatusLogin();
    }

    private void checkStatusLogin() {
        UserPreference preference = new UserPreference(this);
        Login user = preference.getUser();
        if (user != null) {
            String status = user.getStatus();
            if (status.equals("Petugas")) {
                startActivity(new Intent(this, MainPetugasActivity.class));
                finish();
                showLoading(false);
            } else if (status.equals("Siswa")) {
                startActivity(new Intent(this, MainSiswaActivity.class));
                finish();
                showLoading(false);
            } else if (status.equals("Admin")) {
                startActivity(new Intent(this, MainAdminActivity.class));
                finish();
                showLoading(false);
            }
        }
    }

    private void init() {
        edtIdUser = findViewById(R.id.edt_id_login);
        edtPass = findViewById(R.id.edt_password_login);
        btnLogin = findViewById(R.id.btn_login);
        pgLogin = findViewById(R.id.pg_login);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);

    }


    private void showLoading(boolean state) {
        if (state) {
            pgLogin.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
        } else {
            pgLogin.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            idUser = edtIdUser.getText().toString();
            String pass = edtPass.getText().toString();

            if (TextUtils.isEmpty(idUser)) {
                edtIdUser.setError(FIELD_REQUIRED);
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                edtPass.setError(FIELD_REQUIRED);
                return;
            }

            showLoading(true);
            viewModel.setData(idUser, pass);
            viewModel.login();
            viewModel.setOnLoginSuccessCallback(this);
        }
    }

    @Override
    public void onLoginSuccess(boolean state, String status) {
        Login user = new Login();
        if (state) {
            if (status.equals("Petugas")) {
                startActivity(new Intent(this, MainPetugasActivity.class));
                finish();
                showLoading(false);
            } else if (status.equals("Siswa")) {
                startActivity(new Intent(this, MainSiswaActivity.class));
                finish();
                showLoading(false);
            } else if (status.equals("Admin")) {
                startActivity(new Intent(this, MainAdminActivity.class));
                finish();
                showLoading(false);
            }

            UserPreference userPreference = new UserPreference(this);
            user.setIdUser(idUser);
            user.setStatus(status);
            userPreference.setUser(user);
        } else {
            if (status == null) {
                Toast.makeText(this, "Tidak ada user dengan id tersebut", Toast.LENGTH_SHORT).show();
                showLoading(false);
            } else {
                Toast.makeText(this, "password salah", Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        }
    }
}
