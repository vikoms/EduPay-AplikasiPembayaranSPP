package com.example.latihanujikompaket2.ui.history;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.HistorySpp;
import com.example.latihanujikompaket2.entity.Petugas;
import com.example.latihanujikompaket2.entity.Siswa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Pembayaran");
    private DatabaseReference refSiswa = database.getReference("Users").child("Siswa");
    private DatabaseReference refPetugas = database.getReference("Users").child("Petugas");
    private MutableLiveData<ArrayList<HistorySpp>> listHistory = new MutableLiveData<>();
    private MutableLiveData<Siswa> siswa = new MutableLiveData<>();
    private MutableLiveData<Petugas> petugas = new MutableLiveData<>();

    public void setDataHistory() {
        final ArrayList<HistorySpp> historySpps = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historySpps.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    HistorySpp spp = item.getValue(HistorySpp.class);
                    historySpps.add(spp);
                }
                listHistory.postValue(historySpps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledHistory", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public void setDataHistoryByTahun(String tahun) {

        final ArrayList<HistorySpp> historySpps = new ArrayList<>();
        ref.orderByChild("tahunBayar").equalTo(tahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historySpps.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    HistorySpp spp = item.getValue(HistorySpp.class);
                    historySpps.add(spp);
                }
                listHistory.postValue(historySpps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledHistory", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public void setNamaSiswa(String nisn) {
        refSiswa.child(nisn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Siswa entity = dataSnapshot.getValue(Siswa.class);
                    siswa.postValue(entity);
                } else if (!dataSnapshot.exists()) {
                    siswa.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setNamaPetugas(String keyPetugas) {
        refPetugas.child(keyPetugas).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Petugas entity = dataSnapshot.getValue(Petugas.class);
                    petugas.postValue(entity);
                } else if (!dataSnapshot.exists()) {
                    petugas.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void deletePembayaran(String idPembayaran) {
        ref.child(idPembayaran).removeValue();
    }

    public LiveData<ArrayList<HistorySpp>> getDataHistory() {
        return listHistory;
    }

    public LiveData<Siswa> getDataSiswa() {
        return siswa;
    }

    public LiveData<Petugas> getDataPetugas() {
        return petugas;
    }

}
