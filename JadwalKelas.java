// src/model/JadwalKelas.java
package model;

public class JadwalKelas {

    private String id;
    private String namaKelas;
    private String hari;
    private String jam;
    private String instrukturId;
    private String instrukturNama; // hasil join dari tabel instruktur

    public JadwalKelas() {
    }

    public JadwalKelas(String id, String namaKelas, String hari, String jam,
            String instrukturId, String instrukturNama) {
        this.id = id;
        this.namaKelas = namaKelas;
        this.hari = hari;
        this.jam = jam;
        this.instrukturId = instrukturId;
        this.instrukturNama = instrukturNama;
    }

    // GETTER & SETTER

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getInstrukturId() {
        return instrukturId;
    }

    public void setInstrukturId(String instrukturId) {
        this.instrukturId = instrukturId;
    }

    public String getInstrukturNama() {
        return instrukturNama;
    }

    public void setInstrukturNama(String instrukturNama) {
        this.instrukturNama = instrukturNama;
    }
}
