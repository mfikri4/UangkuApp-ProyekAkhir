package com.example.uangku;

public class modelpemasukan {

    private String key;

    private String catatan, deskripsi,jumlah;

    public modelpemasukan(String catatan, String deskripsi, String jumlah) {
        this.catatan = catatan;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
    }

    modelpemasukan(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
