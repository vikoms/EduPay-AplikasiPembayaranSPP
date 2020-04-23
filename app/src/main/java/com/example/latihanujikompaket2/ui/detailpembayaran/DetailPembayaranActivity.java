package com.example.latihanujikompaket2.ui.detailpembayaran;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.latihanujikompaket2.R;
import com.example.latihanujikompaket2.entity.HistorySpp;
import com.example.latihanujikompaket2.entity.Petugas;
import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.ui.history.HistoryViewModel;

public class DetailPembayaranActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvNama, tvNisn, tvTanggal, tvBulan, tvTahun, tvPetugas, tvJumlah;
    private ImageView imgClose;
    private Button btnHapus;
    private ProgressBar pgPembayaran;

    public static final String EXTRA_DATA = "extra_data";
    private HistoryViewModel viewModel;
    private HistorySpp spp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_pembayaran);
        init();

        spp = getIntent().getParcelableExtra(EXTRA_DATA);

        if (spp != null) {
            loadNamaSiswa();
            populateView();
        }

        imgClose.setOnClickListener(this);
        btnHapus.setOnClickListener(this);
    }

    private void loadNamaPetugas() {
        viewModel.setNamaPetugas(spp.getKeyPetugas());
        viewModel.getDataPetugas().observe(this, new Observer<Petugas>() {
            @Override
            public void onChanged(Petugas petugas) {
                if (petugas != null) {
                    tvPetugas.setText(String.format(getResources().getString(R.string.petugas_pembayaran), String.valueOf(petugas.getNamaPetugas())));
                    showLoading(false);
                } else {
                    tvPetugas.setText(" ");
                    showLoading(false);
                    return;
                }
            }
        });
    }

    private void loadNamaSiswa() {
        viewModel.setNamaSiswa(spp.getNisn());
        viewModel.getDataSiswa().observe(this, new Observer<Siswa>() {
            @Override
            public void onChanged(Siswa siswa) {
                if (siswa != null) {
                    tvNama.setText(String.format(getResources().getString(R.string.nama_pembayaran), siswa.getNama()));
                    loadNamaPetugas();
                } else {
                    tvNama.setText("Data siswa telah dihapus");
                    showLoading(false);
                    loadNamaPetugas();
                    return;
                }
            }
        });
    }

    private void populateView() {

        tvJumlah.setText(String.format(getResources().getString(R.string.jumlah_pembayaran), String.valueOf(spp.getJumlahBayar())));
        tvNisn.setText(String.format("NISN : %s", String.valueOf(spp.getNisn())));
        tvBulan.setText(String.format(getResources().getString(R.string.bulan_pembayaran), String.valueOf(spp.getBulanBayar())));
        tvTanggal.setText(String.format(getResources().getString(R.string.tanggal_pembayaran), String.valueOf(spp.getTglBayar())));
        tvTahun.setText(String.format(getResources().getString(R.string.tahun_pembayaran), String.valueOf(spp.getTahunBayar())));


    }

    private void init() {
        tvNama = findViewById(R.id.tv_nama_pembayaran);
        tvNisn = findViewById(R.id.tv_nisn_pembayaran);
        tvTanggal = findViewById(R.id.tv_tanggal_pembayaran);
        tvBulan = findViewById(R.id.tv_bulan_pembayaran);
        tvTahun = findViewById(R.id.tv_tahun_pembayaran);
        tvPetugas = findViewById(R.id.tv_petugas_pembayaran);
        tvJumlah = findViewById(R.id.tv_jumlah_pembayaran);
        imgClose = findViewById(R.id.close_detail_pembayaran);
        btnHapus = findViewById(R.id.btn_hapus_pembayaran);
        pgPembayaran = findViewById(R.id.pg_pembayaran);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);

    }


    private void showLoading(boolean state) {
        if (state) {
            pgPembayaran.setVisibility(View.VISIBLE);
            btnHapus.setVisibility(View.INVISIBLE);
        } else {
            pgPembayaran.setVisibility(View.INVISIBLE);
            btnHapus.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_detail_pembayaran:
                finish();
                break;
            case R.id.btn_hapus_pembayaran:
                deleteData();
                break;
        }
    }

    private void deleteData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Hapus data siswa")
                .setMessage("Anda yakin akan menghapus data pembayaran dengan nama siswa : " + tvNama.getText().toString())
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deletePembayaran(spp.getIdPembayaran());
                        Toast.makeText(DetailPembayaranActivity.this, "Data Pembayaran terhapus", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
