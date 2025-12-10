// src/service/InstrukturService.java
package service;

import dao.InstrukturDAO;
import model.Instruktur;
import java.util.List;

public class InstrukturService {
    private InstrukturDAO instrukturDAO;
    
    public InstrukturService() {
        this.instrukturDAO = new InstrukturDAO();
    }
    
    public boolean addInstruktur(Instruktur instruktur) {
        return instrukturDAO.addInstruktur(instruktur);
    }
    
    public List<Instruktur> getAllInstrukturs() {
        return instrukturDAO.getAllInstrukturs();
    }
    
    public boolean updateInstruktur(Instruktur instruktur) {
        return instrukturDAO.updateInstruktur(instruktur);
    }
    
    public boolean deleteInstruktur(String id) {
        return instrukturDAO.deleteInstruktur(id);
    }
    
    public String generateId() {
        return instrukturDAO.generateId();
    }
}