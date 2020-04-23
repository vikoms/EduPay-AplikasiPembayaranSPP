package com.example.latihanujikompaket2.ui.mainpetugas.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Petugas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePetugasViewModel extends ViewModel {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("Users").child("Petugas");
    private String id;
    private MutableLiveData<Petugas> petugas = new MutableLiveData<>();

    public void setProfileId(String id) {
        this.id = id;
    }

    public void setDataPetugas() {
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Petugas entity = dataSnapshot.getValue(Petugas.class);
                    petugas.postValue(entity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<Petugas> getDataPetugas() {
        return petugas;
    }


}
