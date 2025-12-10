package model;

public class Instruktur {

    private String idInstruktur;
    private String namaInstruktur;
    private String jenisKelamin;
    private String email;
    private int usia;
    private String keahlian;
    private String noTelepon;

    public Instruktur() {
    }

    public Instruktur(String idInstruktur, String namaInstruktur, String jenisKelamin,
            String email, int usia, String keahlian, String noTelepon) {
        this.idInstruktur = idInstruktur;
        this.namaInstruktur = namaInstruktur;
        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.usia = usia;
        this.keahlian = keahlian;
        this.noTelepon = noTelepon;
    }

    // GETTER & SETTER

    public String getIdInstruktur() {
        return idInstruktur;
    }

    public void setIdInstruktur(String idInstruktur) {
        this.idInstruktur = idInstruktur;
    }

    public String getNamaInstruktur() {
        return namaInstruktur;
    }

    public void setNamaInstruktur(String namaInstruktur) {
        this.namaInstruktur = namaInstruktur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}
