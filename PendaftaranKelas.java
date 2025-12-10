package model;

import java.time.LocalDate;

/**
 * Model class untuk data pendaftaran kelas oleh member.
 */
public class PendaftaranKelas {

    private String id;
    private String memberId;
    private String memberNama;
    private String kelasId;
    private String kelasNama;
    private LocalDate tanggalDaftar;
    private String catatan;

    // Constructor kosong
    public PendaftaranKelas() {
    }

    // Constructor lengkap
    public PendaftaranKelas(String id, String memberId, String memberNama,
            String kelasId, String kelasNama,
            LocalDate tanggalDaftar, String catatan) {
        this.id = id;
        this.memberId = memberId;
        this.memberNama = memberNama;
        this.kelasId = kelasId;
        this.kelasNama = kelasNama;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberNama() {
        return memberNama;
    }

    public void setMemberNama(String memberNama) {
        this.memberNama = memberNama;
    }

    public String getKelasId() {
        return kelasId;
    }

    public void setKelasId(String kelasId) {
        this.kelasId = kelasId;
    }

    public String getKelasNama() {
        return kelasNama;
    }

    public void setKelasNama(String kelasNama) {
        this.kelasNama = kelasNama;
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
