// src/service/PendaftaranKelasService.java
package service;

import dao.PendaftaranKelasDAO;
import model.PendaftaranKelas;
import java.util.List;

public class PendaftaranKelasService {
    private PendaftaranKelasDAO pendaftaranKelasDAO;
    
    public PendaftaranKelasService() {
        this.pendaftaranKelasDAO = new PendaftaranKelasDAO();
    }
    
    public boolean addPendaftaran(PendaftaranKelas pendaftaran) {
        return pendaftaranKelasDAO.addPendaftaran(pendaftaran);
    }
    
    public List<PendaftaranKelas> getAllPendaftaran() {
        return pendaftaranKelasDAO.getAllPendaftaran();
    }
    
    public boolean deletePendaftaran(String id) {
        return pendaftaranKelasDAO.deletePendaftaran(id);
    }
    
    public String generateId() {
        return pendaftaranKelasDAO.generateId();
    }
}