package com.example.latihanujikompaket2.ui.mainpetugas.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.latihanujikompaket2.entity.Petugas;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.datapetugas.ManageDataPetugasActivity;
import com.example.latihanujikompaket2.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePetugasFragment extends Fragment implements View.OnClickListener {

    private TextView tvNama, tvId, tvLevel;
    private Button btnLogout, btnEditProfil;
    private ProgressBar pgProfilePetugas;
    private UserPreference preference;
    private ProfilePetugasViewModel viewModel;

    public ProfilePetugasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNama = view.findViewById(R.id.tv_nama_petugas);
        tvId = view.findViewById(R.id.tv_id_petugas);
        tvLevel = view.findViewById(R.id.tv_level_petugas);
        btnLogout = view.findViewById(R.id.btn_logout_petugas);
        btnEditProfil = view.findViewById(R.id.btn_ubah_data_petugas);
        pgProfilePetugas = view.findViewById(R.id.pg_profile_petugas);
        preference = new UserPreference(getActivity());
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfilePetugasViewModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            showLoading(true);
            btnLogout.setOnClickListener(this);
            btnEditProfil.setOnClickListener(this);
            Login user = preference.getUser();
            viewModel.setProfileId(user.getIdUser());
            populateView();

        }
    }

    private void populateView() {
        viewModel.setDataPetugas();
        viewModel.getDataPetugas().observe(getViewLifecycleOwner(), new Observer<Petugas>() {
            @Override
            public void onChanged(Petugas petugas) {
                if (petugas != null) {
                    tvNama.setText(petugas.getNamaPetugas());
                    tvId.setText(petugas.getIdPetugas());
                    tvLevel.setText(petugas.getLevel());
                    showLoading(false);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_petugas, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_logout_petugas) {
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

        } else if (view.getId() == R.id.btn_ubah_data_petugas) {
            showLoading(true);
            viewModel.setDataPetugas();
            viewModel.getDataPetugas().observe(getViewLifecycleOwner(), new Observer<Petugas>() {
                @Override
                public void onChanged(Petugas petugas) {
                    if (petugas != null) {
                        showLoading(false);
                        Intent moveToManage = new Intent(getActivity(), ManageDataPetugasActivity.class);
                        moveToManage.putExtra(ManageDataPetugasActivity.EXTRA_DATA, petugas);
                        startActivity(moveToManage);
                    }
                }
            });
        }
    }


    private void showLoading(boolean state) {
        if (state) {
            pgProfilePetugas.setVisibility(View.VISIBLE);
            btnEditProfil.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        } else {
            pgProfilePetugas.setVisibility(View.INVISIBLE);
            btnEditProfil.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        }
    }
}
