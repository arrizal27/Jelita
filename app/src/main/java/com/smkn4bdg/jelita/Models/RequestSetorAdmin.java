package com.smkn4bdg.jelita.Models;

public class RequestSetorAdmin {
    private String id;
    private String nama_user;
    private String nama_pengepul;
    private String status;
    private String tanggal_setor;
    private String alamat_user;
    private String jenis_pembayaran;
    private double total_uang;
    private String no_telp_pengepul;
    private String foto;

    public RequestSetorAdmin(String id,String nama_user, String nama_pengepul, String status, String tanggal_setor, String alamat_user, String jenis_pembayaran, double total_uang, String no_telp_pengepul, String foto) {
        this.id = id;
        this.nama_user = nama_user;
        this.nama_pengepul = nama_pengepul;
        this.status = status;
        this.tanggal_setor = tanggal_setor;
        this.alamat_user = alamat_user;
        this.jenis_pembayaran = jenis_pembayaran;
        this.total_uang = total_uang;
        this.no_telp_pengepul = no_telp_pengepul;
        this.foto = foto;
    }

    public RequestSetorAdmin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_pengepul() {
        return nama_pengepul;
    }

    public void setNama_pengepul(String nama_pengepul) {
        this.nama_pengepul = nama_pengepul;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_setor() {
        return tanggal_setor;
    }

    public void setTanggal_setor(String tanggal_setor) {
        this.tanggal_setor = tanggal_setor;
    }

    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    public String getJenis_pembayaran() {
        return jenis_pembayaran;
    }

    public void setJenis_pembayaran(String jenis_pembayaran) {
        this.jenis_pembayaran = jenis_pembayaran;
    }

    public double getTotal_uang() {
        return total_uang;
    }

    public void setTotal_uang(double total_uang) {
        this.total_uang = total_uang;
    }

    public String getNo_telp_pengepul() {
        return no_telp_pengepul;
    }

    public void setNo_telp_pengepul(String no_telp_pengepul) {
        this.no_telp_pengepul = no_telp_pengepul;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
