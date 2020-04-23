package com.example.latihanujikompaket2.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends ViewModel {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("Users");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    private String idUser, pass;
    private OnLoginSuccessCallback onLoginSuccessCallback;

    public void setOnLoginSuccessCallback(OnLoginSuccessCallback onLoginSuccessCallback) {
        this.onLoginSuccessCallback = onLoginSuccessCallback;
    }

    public void setData(String idUser, String pass) {
        this.idUser = idUser;
        this.pass = pass;
    }

    public void login() {
        reference.child("Petugas").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child("password").getValue().toString();
                    if (password.equals(pass)) {
                        onLoginSuccessCallback.onLoginSuccess(true, "Petugas");
                    } else {
                        onLoginSuccessCallback.onLoginSuccess(false, "Petugas");
                    }
                } else if (!dataSnapshot.exists()) {
                    loginSiswa();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginSiswa() {
        reference.child("Siswa").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child("password").getValue().toString();
                    if (password.equals(pass)) {
                        onLoginSuccessCallback.onLoginSuccess(true, "Siswa");
                    } else {
                        onLoginSuccessCallback.onLoginSuccess(false, "Siswa");
                    }
                } else if (!dataSnapshot.exists()) {
                    loginAdmin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginAdmin() {
        reference.child("admin").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child("pass").getValue().toString();
                    if (password.equals(pass)) {
                        onLoginSuccessCallback.onLoginSuccess(true, "Admin");
                    } else {
                        onLoginSuccessCallback.onLoginSuccess(false, "Admin");
                    }
                } else if (!dataSnapshot.exists()) {
                    onLoginSuccessCallback.onLoginSuccess(false, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    interface OnLoginSuccessCallback {
        void onLoginSuccess(boolean state, String status);
    }

}
