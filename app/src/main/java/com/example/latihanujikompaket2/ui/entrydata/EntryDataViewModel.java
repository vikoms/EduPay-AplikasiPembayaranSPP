package com.example.latihanujikompaket2.ui.entrydata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.HistorySpp;
import com.example.latihanujikompaket2.entity.Siswa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EntryDataViewModel extends ViewModel {
    private String keyPetugas, nisn, tglBayar, bulanBayar, tahunBayar;
    private int jumlahBayar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Users").child("Siswa");
    private DatabaseReference refPembayaran = database.getReference("Pembayaran");
    private MutableLiveData<Siswa> siswa = new MutableLiveData<>();
    private OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public void setData(String keyPetugas, String nisn, String tglBayar, String bulanBayar, String tahunBayar, int jumlahBayar) {
        this.keyPetugas = keyPetugas;
        this.nisn = nisn;
        this.tglBayar = tglBayar;
        this.bulanBayar = bulanBayar;
        this.tahunBayar = tahunBayar;
        this.jumlahBayar = jumlahBayar;
    }


    public void simpanData() {
        ref.child(nisn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final String idSpp = dataSnapshot.child("keySpp").getValue().toString();
                    String namaSiswa = dataSnapshot.child("nama").getValue().toString();
                    String id = refPembayaran.push().getKey();
                    HistorySpp pembayaran = new HistorySpp(id, keyPetugas, nisn, tglBayar, bulanBayar, tahunBayar, idSpp, namaSiswa, jumlahBayar);
                    refPembayaran.child(id).setValue(pembayaran).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                onCallBack.callBack(true, "SimpanData");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setDataSiswa(String nisn) {
        ref.child(nisn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Siswa itemSiswa = dataSnapshot.getValue(Siswa.class);
                    siswa.postValue(itemSiswa);
                } else if (!dataSnapshot.exists()) {
                    siswa.postValue(null);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<Siswa> getSiswa() {
        return siswa;
    }

    interface OnCallBack {
        void callBack(boolean state, String status);
    }


}
