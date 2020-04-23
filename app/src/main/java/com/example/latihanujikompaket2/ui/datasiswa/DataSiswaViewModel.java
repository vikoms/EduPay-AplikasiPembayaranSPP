package com.example.latihanujikompaket2.ui.datasiswa;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Siswa;
import com.example.latihanujikompaket2.entity.Spp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataSiswaViewModel extends ViewModel {
    private String nisn, nis, nama, email, keyKelas, alamat, phone, keySpp, password, cek;
    private OnSuccessCallBack onSuccessCallBack;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Users").child("Siswa");
    private DatabaseReference refKelas = database.getReference("Kelas");
    private DatabaseReference refSpp = database.getReference("SPP");

    private MutableLiveData<ArrayList<Siswa>> siswaList = new MutableLiveData<>();
    private MutableLiveData<String> kelasSiswa = new MutableLiveData<>();
    private MutableLiveData<Spp> sppSiswa = new MutableLiveData<>();

    public void setOnSuccessCallBack(OnSuccessCallBack onSuccessCallBack) {
        this.onSuccessCallBack = onSuccessCallBack;
    }

    public void setData(String nisn, String nis, String nama, String email, String keyKelas, String alamat, String phone, String keySpp, String password) {
        this.nisn = nisn;
        this.nis = nis;
        this.nama = nama;
        this.email = email;
        this.keyKelas = keyKelas;
        this.alamat = alamat;
        this.phone = phone;
        this.keySpp = keySpp;
        this.password = password;
    }

    public void simpanData() {

        ref.child(nisn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    onSuccessCallBack.onSuccess(false);
                    return;
                }
                Siswa siswa = new Siswa(nisn, nis, nama, email, keyKelas, alamat, phone, keySpp, password);
                ref.child(nisn).setValue(siswa).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onSuccessCallBack.onSuccess(true);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void editData(String idSiswa) {
        Siswa siswa = new Siswa(idSiswa, nis, nama, email, keyKelas, alamat, phone, keySpp, password);
        ref.child(idSiswa).setValue(siswa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onSuccessCallBack.onSuccess(true);
                }
            }
        });
    }

    public void setDataSiswa() {
        final ArrayList<Siswa> listSiswa = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSiswa.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Siswa siswa = item.getValue(Siswa.class);
                    listSiswa.add(siswa);
                }
                siswaList.postValue(listSiswa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setKelas(String idKelas) {
        refKelas.child(idKelas).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String kelas = dataSnapshot.child("namaKelas").getValue().toString();
                    kelasSiswa.postValue(kelas);
                } else {
                    kelasSiswa.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setSpp(String idSpp) {
        refSpp.child(idSpp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Spp entitySpp = new Spp();
                    String tahunSpp = dataSnapshot.child("tahunSpp").getValue().toString();
                    int nominalSpp = Integer.parseInt(dataSnapshot.child("nominal").getValue().toString());
                    entitySpp.setTahunSpp(tahunSpp);
                    entitySpp.setNominal(nominalSpp);
                    sppSiswa.postValue(entitySpp);
                } else {
                    sppSiswa.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteSiswa(Siswa data) {
        ref.child(data.getNisn()).removeValue();
    }


    public void changePassword(Siswa siswa) {
        ref.child(siswa.getNisn()).setValue(siswa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onSuccessCallBack.onSuccess(true);
                }
            }
        });
    }


    public LiveData<String> getKelasSiswa() {
        return kelasSiswa;
    }

    public LiveData<Spp> getSppSiswa() {
        return sppSiswa;
    }

    public LiveData<ArrayList<Siswa>> getDataSiswa() {
        return siswaList;
    }


    public interface OnSuccessCallBack {
        void onSuccess(boolean state);
    }

    public interface SppInterface {
        void getDataSPP(Spp spp);
    }




}
