package com.example.latihanujikompaket2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Kelas implements Parcelable {
    private String keyKelas, namaKelas, jurusan;

    public Kelas(String keyKelas, String namaKelas, String jurusan) {
        this.keyKelas = keyKelas;
        this.namaKelas = namaKelas;
        this.jurusan = jurusan;
    }

    public Kelas() {
    }

    protected Kelas(Parcel in) {
        keyKelas = in.readString();
        namaKelas = in.readString();
        jurusan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyKelas);
        dest.writeString(namaKelas);
        dest.writeString(jurusan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Kelas> CREATOR = new Creator<Kelas>() {
        @Override
        public Kelas createFromParcel(Parcel in) {
            return new Kelas(in);
        }

        @Override
        public Kelas[] newArray(int size) {
            return new Kelas[size];
        }
    };

    public String getKeyKelas() {
        return keyKelas;
    }

    public void setKeyKelas(String keyKelas) {
        this.keyKelas = keyKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }


    @NonNull
    @Override
    public String toString() {
        return namaKelas;
    }
}
