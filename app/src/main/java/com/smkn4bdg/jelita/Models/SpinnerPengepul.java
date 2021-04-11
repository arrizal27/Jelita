package com.smkn4bdg.jelita.Models;

public class SpinnerPengepul {
    private String id_pengepul;
    private String nama_pengepul;
    private String no_telp;
    private String alamat;

    public SpinnerPengepul(){

    }
    public SpinnerPengepul(String id_pengepul, String nama_pengepul, String no_telp, String alamat) {
        this.id_pengepul = id_pengepul;
        this.nama_pengepul = nama_pengepul;
       this.no_telp = no_telp;
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_pengepul() {
        return id_pengepul;
    }

    public void setId_pengepul(String id_pengepul) {
        this.id_pengepul = id_pengepul;
    }

    public String getNama_pengepul() {
        return nama_pengepul;
    }

    public String setNama_pengepul(String nama_pengepul) {
        this.nama_pengepul = nama_pengepul;
        return nama_pengepul;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String setNo_telp(String no_telp) {
        this.no_telp = no_telp;
        return no_telp;
    }


    @Override
    public String toString() {
        return nama_pengepul;
    }
    public String noString(){
        return no_telp;
    }
    public String beString(){
        return id_pengepul;
    }
}
