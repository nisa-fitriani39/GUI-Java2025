// src/service/JadwalKelasService.java
package service;

import dao.JadwalKelasDAO;
import model.JadwalKelas;
import java.util.List;

public class JadwalKelasService {
    private JadwalKelasDAO jadwalKelasDAO;
    
    public JadwalKelasService() {
        this.jadwalKelasDAO = new JadwalKelasDAO();
    }
    
    public boolean addJadwal(JadwalKelas jadwal) {
        return jadwalKelasDAO.addJadwal(jadwal);
    }
    
    public List<JadwalKelas> getAllJadwal() {
        return jadwalKelasDAO.getAllJadwal();
    }
    
    public boolean updateJadwal(JadwalKelas jadwal) {
        return jadwalKelasDAO.updateJadwal(jadwal);
    }
    
    public boolean deleteJadwal(String id) {
        return jadwalKelasDAO.deleteJadwal(id);
    }
    
    public String generateId() {
        return jadwalKelasDAO.generateId();
    }
}