package com.example.latihanujikompaket2.ui.datakelas;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Kelas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataKelasViewModel extends ViewModel {

    private String namaKelas, jurusan;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("Kelas");
    OnUpdateAddSuccess onUpdateAddSuccess;
    public MutableLiveData<ArrayList<Kelas>> kelasList = new MutableLiveData<>();

    public void setOnUpdateAddSuccess(OnUpdateAddSuccess onUpdateAddSuccess) {
        this.onUpdateAddSuccess = onUpdateAddSuccess;
    }

    public void setData(String namaKelas, String jurusan) {
        this.namaKelas = namaKelas;
        this.jurusan = jurusan;
    }

    public void tambahData(String idKelas) {
        String id;
        final String todo;
        if (idKelas != null) {
            id = idKelas;
            todo = "Edit";
        } else {
            id = ref.push().getKey();
            todo = "Tambah";
        }
        final Kelas kelas = new Kelas(id, namaKelas, jurusan);
        ref.child(id).setValue(kelas).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onUpdateAddSuccess.onSuccess(true, kelas, todo);
                } else {
                    onUpdateAddSuccess.onSuccess(false, null, todo);
                }
            }
        });

    }

    public void setDataKelas() {
        final ArrayList<Kelas> listKelas = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listKelas.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Kelas kelas = item.getValue(Kelas.class);
                    listKelas.add(kelas);
                }
                kelasList.postValue(listKelas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<ArrayList<Kelas>> getData() {
        return kelasList;
    }

    public void deleteData(Kelas data) {
        ref.child(data.getKeyKelas()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onUpdateAddSuccess.onSuccess(true, null, "Delete");
                }
            }
        });
    }


    interface OnUpdateAddSuccess {
        void onSuccess(boolean data, Kelas kelas, String todo);
    }

}
