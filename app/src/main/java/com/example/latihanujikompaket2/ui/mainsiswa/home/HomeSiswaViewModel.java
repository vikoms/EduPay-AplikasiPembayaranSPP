package com.example.latihanujikompaket2.ui.mainsiswa.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.HistorySpp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeSiswaViewModel extends ViewModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Pembayaran");
    private MutableLiveData<ArrayList<HistorySpp>> listHistory = new MutableLiveData<>();

    private String idSiswa;

    public void setIdSiswa(String idSiswa) {
        this.idSiswa = idSiswa;
    }

    public void setDataSpp() {
        final ArrayList<HistorySpp> historySpps = new ArrayList<>();
        ref.orderByChild("nisn").equalTo(idSiswa).addValueEventListener(new ValueEventListener() {
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

    public LiveData<ArrayList<HistorySpp>> getDataHistory() {
        return listHistory;
    }
}
