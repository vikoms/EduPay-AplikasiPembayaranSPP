package com.example.latihanujikompaket2.ui.mainsiswa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.latihanujikompaket2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainSiswaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_siswa);

        BottomNavigationView navView = findViewById(R.id.nav_view_siswa);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_siswa, R.id.navigation_profile_siswa)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_siswa);
        NavigationUI.setupWithNavController(navView, navController);

    }
}
