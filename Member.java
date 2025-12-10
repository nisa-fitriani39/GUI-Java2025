package model;

import java.time.LocalDate;

public class Member {

    private String id; // id_member
    private String nama;
    private int usia;
    private String alamat;
    private String Telepon;
    private String email;
    private String JenisMember;
    private String jenisKelamin;
    private LocalDate tanggalDaftar;
    private String catatan;

    // Constructor kosong
    public Member() {
    }

    // Constructor lengkap
    public Member(String id, String nama, int usia, String alamat, String Telepon,
            String email, String JenisMember, String jenisKelamin,
            LocalDate tanggalDaftar, String catatan) {

        this.id = id;
        this.nama = nama;
        this.usia = usia;
        this.alamat = alamat;
        this.Telepon = Telepon;
        this.email = email;
        this.JenisMember = JenisMember;
        this.jenisKelamin = jenisKelamin;
        this.tanggalDaftar = tanggalDaftar;
        this.catatan = catatan;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String Telepon) {
        this.Telepon = Telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisMember() {
        return JenisMember;
    }

    public void setJenisMember(String JenisMember) {
        this.JenisMember = JenisMember;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
