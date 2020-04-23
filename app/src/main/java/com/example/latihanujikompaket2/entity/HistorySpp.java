package com.example.latihanujikompaket2.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class HistorySpp implements Parcelable {
    String idPembayaran, keyPetugas, nisn, tglBayar, bulanBayar, tahunBayar, keySpp, namaSiswa;
    int jumlahBayar;

    public HistorySpp(String idPembayaran, String keyPetugas, String nisn, String tglBayar, String bulanBayar, String tahunBayar, String keySpp, String namaSiswa, int jumlahBayar) {
        this.idPembayaran = idPembayaran;
        this.keyPetugas = keyPetugas;
        this.nisn = nisn;
        this.tglBayar = tglBayar;
        this.bulanBayar = bulanBayar;
        this.tahunBayar = tahunBayar;
        this.keySpp = keySpp;
        this.namaSiswa = namaSiswa;
        this.jumlahBayar = jumlahBayar;
    }

    public HistorySpp() {
    }

    protected HistorySpp(Parcel in) {
        idPembayaran = in.readString();
        keyPetugas = in.readString();
        nisn = in.readString();
        tglBayar = in.readString();
        bulanBayar = in.readString();
        tahunBayar = in.readString();
        keySpp = in.readString();
        namaSiswa = in.readString();
        jumlahBayar = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPembayaran);
        dest.writeString(keyPetugas);
        dest.writeString(nisn);
        dest.writeString(tglBayar);
        dest.writeString(bulanBayar);
        dest.writeString(tahunBayar);
        dest.writeString(keySpp);
        dest.writeString(namaSiswa);
        dest.writeInt(jumlahBayar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistorySpp> CREATOR = new Creator<HistorySpp>() {
        @Override
        public HistorySpp createFromParcel(Parcel in) {
            return new HistorySpp(in);
        }

        @Override
        public HistorySpp[] newArray(int size) {
            return new HistorySpp[size];
        }
    };

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getKeyPetugas() {
        return keyPetugas;
    }

    public void setKeyPetugas(String keyPetugas) {
        this.keyPetugas = keyPetugas;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(String tglBayar) {
        this.tglBayar = tglBayar;
    }

    public String getBulanBayar() {
        return bulanBayar;
    }

    public void setBulanBayar(String bulanBayar) {
        this.bulanBayar = bulanBayar;
    }

    public String getTahunBayar() {
        return tahunBayar;
    }

    public void setTahunBayar(String tahunBayar) {
        this.tahunBayar = tahunBayar;
    }

    public String getKeySpp() {
        return keySpp;
    }

    public void setKeySpp(String keySpp) {
        this.keySpp = keySpp;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    public int getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(int jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }
}
