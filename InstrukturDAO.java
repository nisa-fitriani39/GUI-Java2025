package dao;

import model.Instruktur;
import util.DatabaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrukturDAO {

    public boolean addInstruktur(Instruktur instruktur) {
        String sql = "INSERT INTO instruktur (idInstruktur, namaInstruktur, jenisKelamin, email, usia, keahlian, noTelepon) "
                +
                "VALUES (('Ari Pratama', 'L', 'ari.pratama@gym.com', 32, 'Yoga & Flexibility', '081234567890'),\n" + //
                "('Dewi Lestari', 'P', 'dewi.lestari@gym.com', 28, 'Zumba & Dance Cardio', '082233445566'),\n" + //
                "('Rafi Nugraha', 'L', 'rafi.nugraha@gym.com', 35, 'Strength Training', '085612341234');)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, instruktur.getIdInstruktur());
            pstmt.setString(2, instruktur.getNamaInstruktur());
            pstmt.setString(3, instruktur.getJenisKelamin());
            pstmt.setString(4, instruktur.getEmail());
            pstmt.setInt(5, instruktur.getUsia());
            pstmt.setString(6, instruktur.getKeahlian());
            pstmt.setString(7, instruktur.getNoTelepon());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Add: " + e.getMessage());
            return false;
        }
    }

    public List<Instruktur> getAllInstrukturs() {
        List<Instruktur> instrukturs = new ArrayList<>();
        String sql = "SELECT * FROM instruktur ORDER BY namaInstruktur";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Instruktur instruktur = new Instruktur();
                instruktur.setIdInstruktur(rs.getString("idInstruktur"));
                instruktur.setNamaInstruktur(rs.getString("namaInstruktur"));
                instruktur.setJenisKelamin(rs.getString("jenisKelamin"));
                instruktur.setEmail(rs.getString("email"));
                instruktur.setUsia(rs.getInt("usia"));
                instruktur.setKeahlian(rs.getString("keahlian"));
                instruktur.setNoTelepon(rs.getString("noTelepon"));

                instrukturs.add(instruktur);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Get All: " + e.getMessage());
        }
        return instrukturs;
    }

    public boolean updateInstruktur(Instruktur instruktur) {
        String sql = "UPDATE instruktur SET namaInstruktur = ?, jenisKelamin = ?, email = ?, usia = ?, " +
                "keahlian = ?, noTelepon = ? WHERE idInstruktur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, instruktur.getNamaInstruktur());
            pstmt.setString(2, instruktur.getJenisKelamin());
            pstmt.setString(3, instruktur.getEmail());
            pstmt.setInt(4, instruktur.getUsia());
            pstmt.setString(5, instruktur.getKeahlian());
            pstmt.setString(6, instruktur.getNoTelepon());
            pstmt.setString(7, instruktur.getIdInstruktur());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Update: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteInstruktur(String id) {
        String sql = "DELETE FROM instruktur WHERE idInstruktur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Delete: " + e.getMessage());
            return false;
        }
    }

    public String generateId() {
        String sql = "SELECT MAX(idInstruktur) AS max_id FROM instruktur";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId != null) {
                    int num = Integer.parseInt(maxId.substring(3)) + 1;
                    return String.format("INS%03d", num);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "INS001";
    }
}