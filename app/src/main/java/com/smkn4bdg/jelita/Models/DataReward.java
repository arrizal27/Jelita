package com.smkn4bdg.jelita.Models;

public class DataReward {
    private String idreward,nama,harga,foto;

    public DataReward(String idreward,String nama,String harga,String foto){
        this.idreward = idreward;
        this.nama = nama;
        this.harga = harga;
        this.foto = foto;
    }

    public String getIdreward() {
        return idreward;
    }

    public void setIdreward(String idreward) {
        this.idreward = idreward;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
