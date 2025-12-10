package dao;

import model.JadwalKelas;
import util.DatabaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalKelasDAO {

    public boolean addJadwal(JadwalKelas jadwal) {
        String sql = "INSERT INTO jadwal_kelas (id, nama_kelas, hari, jam, instruktur_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jadwal.getId());
            pstmt.setString(2, jadwal.getNamaKelas());
            pstmt.setString(3, jadwal.getHari());
            pstmt.setString(4, jadwal.getJam());
            pstmt.setString(5, jadwal.getInstrukturId()); // idInstruktur

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Add Jadwal: " + e.getMessage());
            return false;
        }
    }

    public List<JadwalKelas> getAllJadwal() {
        List<JadwalKelas> jadwalList = new ArrayList<>();

        String sql = "SELECT j.*, i.namaInstruktur AS instruktur_nama " +
                "FROM jadwal_kelas j " +
                "LEFT JOIN instruktur i ON j.instruktur_id = i.idInstruktur " +
                "ORDER BY j.hari, j.jam";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                JadwalKelas jadwal = new JadwalKelas();

                jadwal.setId(rs.getString("id"));
                jadwal.setNamaKelas(rs.getString("nama_kelas"));
                jadwal.setHari(rs.getString("hari"));
                jadwal.setJam(rs.getString("jam"));
                jadwal.setInstrukturId(rs.getString("instruktur_id"));
                jadwal.setInstrukturNama(rs.getString("instruktur_nama"));

                jadwalList.add(jadwal);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Get Jadwal: " + e.getMessage());
        }

        return jadwalList;
    }

    public boolean updateJadwal(JadwalKelas jadwal) {
        String sql = "UPDATE jadwal_kelas SET nama_kelas = ?, hari = ?, jam = ?, instruktur_id = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jadwal.getNamaKelas());
            pstmt.setString(2, jadwal.getHari());
            pstmt.setString(3, jadwal.getJam());
            pstmt.setString(4, jadwal.getInstrukturId());
            pstmt.setString(5, jadwal.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Update Jadwal: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteJadwal(String id) {
        String sql = "DELETE FROM jadwal_kelas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Delete Jadwal: " + e.getMessage());
            return false;
        }
    }

    public String generateId() {
        String sql = "SELECT MAX(id) AS max_id FROM jadwal_kelas";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");

                if (maxId != null) {
                    int num = Integer.parseInt(maxId.substring(3)) + 1;
                    return String.format("JDL%03d", num);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "JDL001";
    }
}