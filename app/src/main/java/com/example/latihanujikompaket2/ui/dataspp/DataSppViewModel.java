package com.example.latihanujikompaket2.ui.dataspp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.latihanujikompaket2.entity.Spp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataSppViewModel extends ViewModel {
    private String tahun;
    private int nominal;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("SPP");
    OnUpdateAddSuccess onUpdateAddSuccess;

    private MutableLiveData<ArrayList<Spp>> sppList = new MutableLiveData<>();

    public void setOnUpdateAddSuccess(OnUpdateAddSuccess onUpdateAddSuccess) {
        this.onUpdateAddSuccess = onUpdateAddSuccess;
    }

    public void setData(String tahun, int nominal) {
        this.tahun = tahun;
        this.nominal = nominal;
    }

    public void simpanData(String idSpp) {
        String key;

        if (idSpp != null) {
            key = idSpp;
        } else {
            key = ref.push().getKey();
        }

        final Spp spp = new Spp(key, tahun, nominal);

        ref.child(key).setValue(spp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onUpdateAddSuccess.onSuccess(true);
                } else {
                    onUpdateAddSuccess.onSuccess(false);
                }
            }
        });
    }

    public void setDataSpp() {
        final ArrayList<Spp> listSpp = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSpp.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Spp spp = item.getValue(Spp.class);
                    listSpp.add(spp);
                }
                sppList.postValue(listSpp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledSPP", "onCancelled: get data failed");
            }
        });
    }

    public void deleteData(Spp data) {
        ref.child(data.getKeySpp()).removeValue();
    }

    public LiveData<ArrayList<Spp>> getDataSpp() {
        return sppList;
    }

    interface OnUpdateAddSuccess {
        void onSuccess(boolean state);
    }

}
