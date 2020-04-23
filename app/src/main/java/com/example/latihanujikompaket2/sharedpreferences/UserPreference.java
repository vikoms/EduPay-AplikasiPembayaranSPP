package com.example.latihanujikompaket2.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.latihanujikompaket2.entity.Login;

public class UserPreference {

    private static final String PREFS_NAME = "user_preferences";


    private static final String ID_USER = "id_user";
    private static final String STATUS = "STATUS";

    private final SharedPreferences sharedPreferences;

    public UserPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setUser(Login user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_USER, user.getIdUser());
        editor.putString(STATUS, user.getStatus());
        editor.apply();
    }

    public Login getUser() {
        Login user = new Login();
        user.setIdUser(sharedPreferences.getString(ID_USER, ""));
        user.setStatus(sharedPreferences.getString(STATUS, ""));
        return user;
    }

}
