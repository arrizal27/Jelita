package com.smkn4bdg.jelita.Models;

public class RequestSetorUser {
    private String id;
    private String id_pengepul;
    private String nama_pengepul;
    private String no_telp_pengepul;
    private String alamat_user;
    private String tanggal_setor;
    private String foto_bukti;
    private String jenis_pembayaran;
    private String alasantolak;
    private double total_uang;
    private String status;

    public RequestSetorUser(){

    }

    public RequestSetorUser(String id,String id_pengepul, String nama_pengepul, String no_telp_pengepul, String alamat_user, String tanggal_setor, String foto_bukti, String jenis_pembayaran, String alasantolak, double total_uang, String status) {
        this.id = id;
        this.id_pengepul = id_pengepul;
        this.nama_pengepul = nama_pengepul;
        this.no_telp_pengepul = no_telp_pengepul;
        this.alamat_user = alamat_user;
        this.tanggal_setor = tanggal_setor;
        this.foto_bukti = foto_bukti;
        this.jenis_pembayaran = jenis_pembayaran;
        this.alasantolak = alasantolak;
        this.total_uang = total_uang;
        this.status = status;
    }

    public String getId_pengepul() {
        return id_pengepul;
    }

    public void setId_pengepul(String id_pengepul) {
        this.id_pengepul = id_pengepul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_pengepul() {
        return nama_pengepul;
    }

    public void setNama_pengepul(String nama_pengepul) {
        this.nama_pengepul = nama_pengepul;
    }

    public String getNo_telp_pengepul() {
        return no_telp_pengepul;
    }

    public void setNo_telp_pengepul(String no_telp_pengepul) {
        this.no_telp_pengepul = no_telp_pengepul;
    }

    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    public String getTanggal_setor() {
        return tanggal_setor;
    }

    public void setTanggal_setor(String tanggal_setor) {
        this.tanggal_setor = tanggal_setor;
    }

    public String getFoto_bukti() {
        return foto_bukti;
    }

    public void setFoto_bukti(String foto_bukti) {
        this.foto_bukti = foto_bukti;
    }

    public String getJenis_pembayaran() {
        return jenis_pembayaran;
    }

    public void setJenis_pembayaran(String jenis_pembayaran) {
        this.jenis_pembayaran = jenis_pembayaran;
    }

    public String getAlasantolak() {
        return alasantolak;
    }

    public void setAlasantolak(String alasantolak) {
        this.alasantolak = alasantolak;
    }

    public double getTotal_uang() {
        return total_uang;
    }

    public void setTotal_uang(double total_uang) {
        this.total_uang = total_uang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}