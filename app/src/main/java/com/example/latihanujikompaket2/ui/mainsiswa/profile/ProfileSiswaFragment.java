package com.example.latihanujikompaket2.ui.mainsiswa.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Login;
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.entity.Spp;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.datasiswa.DataSiswaViewModel;
import com.example.latihanujikompaket2.ui.datasiswa.ManageDataSiswaActivity;
import com.example.latihanujikompaket2.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSiswaFragment extends Fragment implements View.OnClickListener {

    private TextView tvNamaHeader, tvNis, tvNama, tvNisn, tvEmail, tvKelas, tvAlamat, tvPhone, tvSpp;
    private Button btnUbahData;
    private ProgressBar pgDetailSiswa;
    private ProfileSiswaViewModel viewModel;
    private ImageView imgLogout;
    private UserPreference preference;
    private DataSiswaViewModel viewModelSiswa;
    private String idUser;

    public ProfileSiswaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            showLoading(true);
            Login user = preference.getUser();
            idUser = user.getIdUser();
            populateView();
            imgLogout.setOnClickListener(this);
            btnUbahData.setOnClickListener(this);
        }

    }

    private void populateView() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileSiswaViewModel.class);
        viewModel.setidSiswa(idUser);
        viewModel.setDataSiswa();
        viewModel.getDataSiswa().observe(getViewLifecycleOwner(), new Observer<Siswa>() {
            @Override
            public void onChanged(Siswa siswa) {
                if (siswa != null) {
                    tvNamaHeader.setText(siswa.getNama());
                    tvNis.setText(siswa.getNis());
                    tvNama.setText(siswa.getNama());
                    tvNisn.setText(siswa.getNisn());
                    tvEmail.setText(siswa.getEmail());
                    tvAlamat.setText(siswa.getAlamat());
                    tvPhone.setText(siswa.getPhone());
                    populateKelas(siswa.getKeyKelas());
                    populateSpp(siswa.getKeySpp());
                }
            }
        });
    }

    private void populateSpp(String keySpp) {
        viewModelSiswa = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        viewModelSiswa.setSpp(keySpp);
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

    private void populateKelas(String keyKelas) {
        viewModelSiswa = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        viewModelSiswa.setKelas(keyKelas);
        viewModelSiswa.getKelasSiswa().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    tvKelas.setText(s);
                }
            }
        });
    }

    private void init(View view) {
        tvNamaHeader = view.findViewById(R.id.tv_nama_header_detail_siswa);
        tvNis = view.findViewById(R.id.tv_nis_detail_siswa);
        tvNama = view.findViewById(R.id.tv_id_petugas);
        tvNisn = view.findViewById(R.id.tv_nisn_detail_siswa);
        tvEmail = view.findViewById(R.id.tv_email_detail_siswa);
        tvKelas = view.findViewById(R.id.tv_kelas_detail_siswa);
        tvAlamat = view.findViewById(R.id.tv_alamat_detail_siswa);
        tvPhone = view.findViewById(R.id.tv_phone_detail_siswa);
        tvSpp = view.findViewById(R.id.tv_spp_detail_siswa);
        btnUbahData = view.findViewById(R.id.btn_ubah_data_siswa);
        pgDetailSiswa = view.findViewById(R.id.pg_detail_data_siswa);
        imgLogout = view.findViewById(R.id.logout_siswa);
        preference = new UserPreference(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_siswa, container, false);
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
        if (view.getId() == R.id.logout_siswa) {
            final Login userNull = new Login();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Anda yakin akan logout?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            preference.setUser(userNull);
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

        } else if (view.getId() == R.id.btn_ubah_data_siswa) {
            showLoading(true);
            final Intent moveToEdit = new Intent(getActivity(), ManageDataSiswaActivity.class);
            viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileSiswaViewModel.class);
            viewModel.setidSiswa(idUser);
            viewModel.setDataSiswa();
            viewModel.getDataSiswa().observe(getViewLifecycleOwner(), new Observer<Siswa>() {
                @Override
                public void onChanged(Siswa siswa) {
                    if (siswa != null) {
                        showLoading(false);
                        moveToEdit.putExtra(ManageDataSiswaActivity.EXTRA_DATA, siswa);
                        moveToEdit.putExtra(ManageDataSiswaActivity.EXTRA_STATUS_USER, "Siswa");
                        startActivity(moveToEdit);
                    }
                }
            });
        }
    }
}
