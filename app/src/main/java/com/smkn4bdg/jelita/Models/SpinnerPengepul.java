package com.smkn4bdg.jelita.Models;

public class SpinnerPengepul {
    private String id;
    private String nama;
    private String no_telp;
    private String alamat;
    private String jenis_kel;
    private String email;
    private String foto;
    private String kecamatan;
    private String kelurahan;
    private String kota;
    private String password;
    private String username;


    public SpinnerPengepul() {
    }

    public SpinnerPengepul(String id, String nama, String no_telp, String alamat, String jenis_kel, String email, String foto, String kecamatan, String kelurahan, String kota, String password, String username) {
        this.id = id;
        this.nama = nama;
        this.no_telp = no_telp;
        this.alamat = alamat;
        this.jenis_kel = jenis_kel;
        this.email = email;
        this.foto = foto;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.kota = kota;
        this.password = password;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJenis_kel() {
        return jenis_kel;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public String getKota() {
        return kota;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "SpinnerPengepul [id=" + id + ", nama=" + nama + ", no_telp=" + no_telp + ", alamat=" + alamat + ", jenis_kel=" + jenis_kel + ", email=" + email + ", foto=" + foto + ", kecamatan=" + kecamatan + ", kelurahan=" + kelurahan + ", kota=" + kota + ", password=" + password + ", username=" + username + "]";
    }
}


//    @Override
//    public String toString() {
//        return nama_pengepul;
//    }
//}
//    public String noString(){
//        return no_telp;
//    }
//    public String beString(){
//        return id_pengepul;
//    }
//}
