package com.smkn4bdg.jelita.Models;

public class RequestSetorPengepul {
    private String id;
    private String id_user;
    private String nama_user;
    private String alamat_user;
    private String no_telp_user;
    private String tanggal_setor;
    private String foto;
    private String jenis_bayar;
    private String alasan;
    private double total_uang;
    private String status;
    public RequestSetorPengepul(){}

    public RequestSetorPengepul(String id,String id_user, String nama_user, String alamat_user, String no_telp_user, String tanggal_setor, String foto, String jenis_bayar, String alasan, double total_uang, String status) {
        this.id = id;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.alamat_user = alamat_user;
        this.no_telp_user = no_telp_user;
        this.tanggal_setor = tanggal_setor;
        this.foto = foto;
        this.jenis_bayar = jenis_bayar;
        this.alasan = alasan;
        this.total_uang = total_uang;
        this.status = status;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
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

    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    public String getNo_telp_user() {
        return no_telp_user;
    }

    public void setNo_telp_user(String no_telp_user) {
        this.no_telp_user = no_telp_user;
    }

    public String getTanggal_setor() {
        return tanggal_setor;
    }

    public void setTanggal_setor(String tanggal_setor) {
        this.tanggal_setor = tanggal_setor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJenis_bayar() {
        return jenis_bayar;
    }

    public void setJenis_bayar(String jenis_bayar) {
        this.jenis_bayar = jenis_bayar;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
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
