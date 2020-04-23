package com.example.latihanujikompaket2.ui.datapetugas;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Petugas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataPetugasViewModel extends ViewModel {
    private String level, nama, pass, nip;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Users").child("Petugas");
    private OnSuccessCallback onSuccessCallback;
    private MutableLiveData<ArrayList<Petugas>> petugasList = new MutableLiveData<>();

    public void setOnSuccessCallback(OnSuccessCallback onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
    }

    public void setData(String level, String nama, String pass, String nip) {
        this.level = level;
        this.pass = pass;
        this.nama = nama;
        this.nip = nip;
    }

    public void simpanData() {
        ref.child(nip).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    onSuccessCallback.onSuccess(false, "checkStatus");
                    return;
                }
                Petugas petugas = new Petugas(nip, pass, nama, level);
                ref.child(nip).setValue(petugas).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onSuccessCallback.onSuccess(true, "saveData");
                            return;
                        } else {
                            onSuccessCallback.onSuccess(false, "saveData");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteData(Petugas data) {
        ref.child(data.getIdPetugas()).removeValue();
    }

    public void setDataPetugas() {
        final ArrayList<Petugas> listPetugas = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPetugas.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Petugas petugas = item.getValue(Petugas.class);
                    listPetugas.add(petugas);
                }

                petugasList.postValue(listPetugas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changePassword(Petugas petugas) {
        ref.child(petugas.getIdPetugas()).setValue(petugas).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onSuccessCallback.onSuccess(true, "ganti_password");
            }
        });
    }

    public LiveData<ArrayList<Petugas>> getDataPetugas() {
        return petugasList;
    }

    public void editPetugas(String idPetugas) {
        Petugas petugas = new Petugas(idPetugas, pass, nama, level);
        ref.child(idPetugas).setValue(petugas).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onSuccessCallback.onSuccess(true, "saveData");
                    return;
                } else {
                    onSuccessCallback.onSuccess(false, "saveData");
                }
            }
        });
    }


    interface OnSuccessCallback {
        void onSuccess(boolean state, String where);
    }

}
