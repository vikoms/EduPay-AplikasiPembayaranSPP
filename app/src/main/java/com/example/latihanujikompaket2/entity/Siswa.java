package com.example.latihanujikompaket2.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Siswa implements Parcelable {
    String nisn, nis, nama, email, keyKelas, alamat, phone, keySpp, password;

    public Siswa(String nisn, String nis, String nama, String email, String keyKelas, String alamat, String phone, String keySpp, String password) {
        this.nisn = nisn;
        this.nis = nis;
        this.nama = nama;
        this.email = email;
        this.keyKelas = keyKelas;
        this.alamat = alamat;
        this.phone = phone;
        this.keySpp = keySpp;
        this.password = password;
    }

    public Siswa() {
    }


    protected Siswa(Parcel in) {
        nisn = in.readString();
        nis = in.readString();
        nama = in.readString();
        email = in.readString();
        keyKelas = in.readString();
        alamat = in.readString();
        phone = in.readString();
        keySpp = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nisn);
        dest.writeString(nis);
        dest.writeString(nama);
        dest.writeString(email);
        dest.writeString(keyKelas);
        dest.writeString(alamat);
        dest.writeString(phone);
        dest.writeString(keySpp);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Siswa> CREATOR = new Creator<Siswa>() {
        @Override
        public Siswa createFromParcel(Parcel in) {
            return new Siswa(in);
        }

        @Override
        public Siswa[] newArray(int size) {
            return new Siswa[size];
        }
    };

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeyKelas() {
        return keyKelas;
    }

    public void setKeyKelas(String keyKelas) {
        this.keyKelas = keyKelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKeySpp() {
        return keySpp;
    }

    public void setKeySpp(String keySpp) {
        this.keySpp = keySpp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Creator<Siswa> getCREATOR() {
        return CREATOR;
    }
}
