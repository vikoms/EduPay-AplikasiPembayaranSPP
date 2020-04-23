package com.example.latihanujikompaket2.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Petugas implements Parcelable {
    String idPetugas, password, namaPetugas, level;

    public Petugas(String idPetugas, String password, String namaPetugas, String level) {
        this.idPetugas = idPetugas;
        this.password = password;
        this.namaPetugas = namaPetugas;
        this.level = level;
    }

    public Petugas() {
    }

    protected Petugas(Parcel in) {
        idPetugas = in.readString();
        password = in.readString();
        namaPetugas = in.readString();
        level = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPetugas);
        dest.writeString(password);
        dest.writeString(namaPetugas);
        dest.writeString(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Petugas> CREATOR = new Creator<Petugas>() {
        @Override
        public Petugas createFromParcel(Parcel in) {
            return new Petugas(in);
        }

        @Override
        public Petugas[] newArray(int size) {
            return new Petugas[size];
        }
    };

    public String getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(String idPetugas) {
        this.idPetugas = idPetugas;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
