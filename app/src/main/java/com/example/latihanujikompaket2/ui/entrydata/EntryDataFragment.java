package com.example.latihanujikompaket2.ui.entrydata;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.Login;
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.entity.Spp;
import com.example.latihanujikompaket2.sharedpreferences.UserPreference;
import com.example.latihanujikompaket2.ui.datasiswa.DataSiswaViewModel;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntryDataFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, EntryDataViewModel.OnCallBack {

    private EditText edtNisn, edtTanggal, edtTahun, edtBulan, edtJumlah;
    private ProgressBar pgEntryData;
    private Button btnSimpanData,btnCekSpp;
    private DataSiswaViewModel siswaViewModel;
    private EntryDataViewModel entryDataViewModel;
    private UserPreference preference;


    public EntryDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtBulan = view.findViewById(R.id.edt_bulan_entry_data);
        edtTanggal = view.findViewById(R.id.edt_tanggal_entry_data);
        edtTahun = view.findViewById(R.id.edt_tahun_entry_data);
        edtNisn = view.findViewById(R.id.edt_nisn_entry_data);
        edtJumlah = view.findViewById(R.id.edt_jumlah_entry_data);
        pgEntryData = view.findViewById(R.id.pg_entry_data);
        btnSimpanData = view.findViewById(R.id.btn_simpan_data_pembayaran);
        btnCekSpp = view.findViewById(R.id.btn_cek_spp);
        siswaViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSiswaViewModel.class);
        entryDataViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(EntryDataViewModel.class);
        preference = new UserPreference(getActivity());
        edtTanggal.setFocusable(false);
        edtTahun.setFocusable(false);
        edtBulan.setFocusable(false);
        edtJumlah.setFocusable(false);
        edtJumlah.setClickable(true);
        edtTanggal.setClickable(true);
        edtJumlah.setText("0");
        onNisnTyped();
        
        btnSimpanData.setOnClickListener(this);
        edtTanggal.setOnClickListener(this);
        btnCekSpp.setOnClickListener(this);

    }

    private void onNisnTyped() {
        edtNisn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtJumlah.setText("0");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_data, container, false);
    }

    @Override
    public void onClick(View view) {
        final String FIELD_REQUIRED = "Field tidak boleh kosong";
        switch (view.getId()) {
            case R.id.edt_tanggal_entry_data:
                showDatePickerDialog();
                break;
            case R.id.btn_cek_spp:
                String nisn = edtNisn.getText().toString();
                if (TextUtils.isEmpty(nisn)) {
                    edtNisn.setError(FIELD_REQUIRED);
                    return;
                }
                edtJumlah.setText("0");
                showLoading(true);
                setJumlahPembayaran(nisn);
                break;
            case R.id.btn_simpan_data_pembayaran:
                String nisnPembayaran = edtNisn.getText().toString();
                String tanggal = edtTanggal.getText().toString();
                String tahunArray[] = tanggal.split("-");
                String tahun = tahunArray[0];
                String bulan = edtBulan.getText().toString();
                int jumlah = Integer.parseInt(edtJumlah.getText().toString());

                if (TextUtils.isEmpty(nisnPembayaran)) {
                    edtNisn.setError(FIELD_REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(tanggal)) {
                    edtTanggal.setError(FIELD_REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(bulan)) {
                    edtBulan.setError(FIELD_REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(tahun)) {
                    edtTahun.setError(FIELD_REQUIRED);
                    return;
                }
                if (jumlah == 0) {
                    edtJumlah.setError(FIELD_REQUIRED);
                    return;
                }

                showLoading(true);
                simpanData(nisnPembayaran, tanggal, bulan, jumlah, tahun);

        }
    }

    private void simpanData(String nisn, String tanggal, String bulan, int jumlah, String tahun) {
        Login user = preference.getUser();
        String idPetugas = user.getIdUser();
        entryDataViewModel.setData(idPetugas, nisn, tanggal, bulan, tahun, jumlah);
        entryDataViewModel.simpanData();
        entryDataViewModel.setOnCallBack(this);
    }

    private void setJumlahPembayaran(String nisn) {
        entryDataViewModel.setDataSiswa(nisn);
        entryDataViewModel.getSiswa().observe(getViewLifecycleOwner(), new Observer<Siswa>() {
            @Override
            public void onChanged(Siswa siswa) {
                if (siswa != null) {
                    siswaViewModel.setSpp(siswa.getKeySpp());
                    siswaViewModel.getSppSiswa().observe(getViewLifecycleOwner(), new Observer<Spp>() {
                        @Override
                        public void onChanged(Spp spp) {
                            if (spp != null) {
                                edtJumlah.setText("0");
                                edtJumlah.setText(String.valueOf(spp.getNominal()));
                                showLoading(false);
                            } else {
                                edtJumlah.setText("0");
                                edtJumlah.setText("Data Spp tidak ditemukan periksa kembali data siswa tersebut");
                                btnSimpanData.setClickable(false);
                                btnSimpanData.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_button_logout));
                                showLoading(false);
                            }
                        }
                    });
                } else if (siswa == null) {
                    Toast.makeText(getContext(), "Siswa dengan nip tersebut tidak ada", Toast.LENGTH_SHORT).show();
                    edtJumlah.setText("0");
                    btnSimpanData.setClickable(false);
                    btnSimpanData.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_button_logout));
                    showLoading(false);
                    showLoading(false);
                }
            }
        });
        entryDataViewModel.setOnCallBack(this);
    }

    private void showDatePickerDialog() {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private String getMonthName(int month, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] monthNames = symbols.getMonths();
        return monthNames[month];
    }


    private void showLoading(boolean state) {
        if (state) {
            pgEntryData.setVisibility(View.VISIBLE);
            btnSimpanData.setVisibility(View.INVISIBLE);
        } else {
            pgEntryData.setVisibility(View.INVISIBLE);
            btnSimpanData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        edtTanggal.setText(dateFormat.format(calendar.getTime()));
        edtBulan.setText(getMonthName(month, Locale.getDefault()));
        edtTahun.setText(String.format("Tahun : %s", year));
    }

    @Override
    public void callBack(boolean state, String status) {
        if (state) {
            if (status.equals("SimpanData")) {
                Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                edtNisn.setText("");
                edtJumlah.setText("");
                edtTahun.setText("");
                edtTanggal.setText("");
                edtBulan.setText("");
                showLoading(false);
            }
        } else {
            if (status.equals("SimpanData")) {
                showLoading(false);
            }
        }
    }

}
