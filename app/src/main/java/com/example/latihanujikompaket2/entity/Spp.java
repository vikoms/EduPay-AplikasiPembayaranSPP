package com.example.latihanujikompaket2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Spp implements Parcelable {
    String keySpp, tahunSpp;
    int nominal;

    public Spp() {
    }

    public Spp(String keySpp, String tahunSpp, int nominal) {
        this.keySpp = keySpp;
        this.tahunSpp = tahunSpp;
        this.nominal = nominal;
    }

    protected Spp(Parcel in) {
        keySpp = in.readString();
        tahunSpp = in.readString();
        nominal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keySpp);
        dest.writeString(tahunSpp);
        dest.writeInt(nominal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Spp> CREATOR = new Creator<Spp>() {
        @Override
        public Spp createFromParcel(Parcel in) {
            return new Spp(in);
        }

        @Override
        public Spp[] newArray(int size) {
            return new Spp[size];
        }
    };

    public String getKeySpp() {
        return keySpp;
    }

    public void setKeySpp(String keySpp) {
        this.keySpp = keySpp;
    }

    public String getTahunSpp() {
        return tahunSpp;
    }

    public void setTahunSpp(String tahunSpp) {
        this.tahunSpp = tahunSpp;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @NonNull
    @Override
    public String toString() {
        return tahunSpp;
    }
}
