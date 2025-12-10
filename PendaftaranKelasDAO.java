// src/dao/PendaftaranKelasDAO.java
package dao;

import model.PendaftaranKelas;
import util.DatabaseConnection;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PendaftaranKelasDAO {
    
    public boolean addPendaftaran(PendaftaranKelas pendaftaran) {
        String sql = "INSERT INTO pendaftaran_kelas (id, member_id, kelas_id, tanggal_daftar, catatan) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pendaftaran.getId());
            pstmt.setString(2, pendaftaran.getMemberId());
            pstmt.setString(3, pendaftaran.getKelasId());
            pstmt.setDate(4, Date.valueOf(pendaftaran.getTanggalDaftar()));
            pstmt.setString(5, pendaftaran.getCatatan());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<PendaftaranKelas> getAllPendaftaran() {
        List<PendaftaranKelas> pendaftaranList = new ArrayList<>();
        String sql = "SELECT p.*, m.nama as member_nama, j.nama_kelas " +
                    "FROM pendaftaran_kelas p " +
                    "JOIN member m ON p.member_id = m.id " +
                    "JOIN jadwal_kelas j ON p.kelas_id = j.id " +
                    "ORDER BY p.tanggal_daftar DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PendaftaranKelas pendaftaran = new PendaftaranKelas();
                pendaftaran.setId(rs.getString("id"));
                pendaftaran.setMemberId(rs.getString("member_id"));
                pendaftaran.setMemberNama(rs.getString("member_nama"));
                pendaftaran.setKelasId(rs.getString("kelas_id"));
                pendaftaran.setKelasNama(rs.getString("nama_kelas"));
                pendaftaran.setTanggalDaftar(rs.getDate("tanggal_daftar").toLocalDate());
                pendaftaran.setCatatan(rs.getString("catatan"));
                pendaftaranList.add(pendaftaran);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendaftaranList;
    }
    
    public boolean deletePendaftaran(String id) {
        String sql = "DELETE FROM pendaftaran_kelas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public String generateId() {
        String sql = "SELECT MAX(id) as max_id FROM pendaftaran_kelas";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId != null) {
                    int num = Integer.parseInt(maxId.substring(3)) + 1;
                    return String.format("REG%03d", num);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "REG001";
    }
}
