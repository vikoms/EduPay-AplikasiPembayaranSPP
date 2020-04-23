package com.example.latihanujikompaket2.ui.mainpetugas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.latihanujikompaket2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPetugasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_petugas);
        BottomNavigationView navView = findViewById(R.id.nav_view_petugas);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_history_petugas, R.id.navigation_entry_petugas, R.id.navigation_profile_petugas)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_petugas);
        NavigationUI.setupWithNavController(navView, navController);

    }


}
