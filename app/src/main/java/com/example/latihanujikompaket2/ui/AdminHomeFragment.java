package com.example.latihanujikompaket2.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Login;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.datakelas.DataKelasActivity;
import com.example.latihanujikompaket2.ui.datapetugas.DataPetugasActivity;
import com.example.latihanujikompaket2.ui.datasiswa.DataSiswaActivity;
import com.example.latihanujikompaket2.ui.dataspp.DataSppActivity;
import com.example.latihanujikompaket2.ui.login.LoginActivity;

public class AdminHomeFragment extends Fragment implements View.OnClickListener {

    private CardView cvSiswa;
    private CardView cvKelas;
    private CardView cvPetugas;
    private CardView cvSpp;
    private ImageView imgLogout;
    private UserPreference sharedPreferences;
    TextView textView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvSiswa = view.findViewById(R.id.cv_data_siswa);
        cvPetugas = view.findViewById(R.id.cv_data_petugas);
        cvKelas = view.findViewById(R.id.cv_data_kelas);
        cvSpp = view.findViewById(R.id.cv_data_spp);
        sharedPreferences = new UserPreference(getActivity());
        textView = view.findViewById(R.id.tv_nama_user);
        imgLogout = view.findViewById(R.id.logout_admin);

        cvSiswa.setOnClickListener(this);
        cvPetugas.setOnClickListener(this);
        cvKelas.setOnClickListener(this);
        cvSpp.setOnClickListener(this);
        imgLogout.setOnClickListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            textView.setText("Hai Admin");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.cv_data_siswa:
                startActivity(new Intent(getActivity(), DataSiswaActivity.class));
                break;
            case R.id.cv_data_kelas:
                startActivity(new Intent(getActivity(), DataKelasActivity.class));
                break;
            case R.id.cv_data_petugas:
                startActivity(new Intent(getActivity(), DataPetugasActivity.class));
                break;
            case R.id.cv_data_spp:
                startActivity(new Intent(getActivity(), DataSppActivity.class));
                break;
            case R.id.logout_admin:
                final Login userNull = new Login();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Anda yakin akan logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences.setUser(userNull);
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            default:
                break;
        }
    }
}
