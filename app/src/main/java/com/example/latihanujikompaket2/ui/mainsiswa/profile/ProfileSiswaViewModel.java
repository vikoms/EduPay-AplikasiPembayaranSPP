package com.example.latihanujikompaket2.ui.mainsiswa.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Siswa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSiswaViewModel extends ViewModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("Users").child("Siswa");
    private MutableLiveData<Siswa> siswa = new MutableLiveData<>();
    private String idSiswa;

    public void setidSiswa(String idSiswa) {
        this.idSiswa = idSiswa;
    }

    public void setDataSiswa() {
        reference.child(idSiswa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Siswa entity = dataSnapshot.getValue(Siswa.class);
                    siswa.postValue(entity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<Siswa> getDataSiswa() {
        return siswa;
    }
}
