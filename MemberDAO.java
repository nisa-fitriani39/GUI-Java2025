// src/dao/MemberDAO.java
package dao;

import model.Member;
import util.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public boolean addMember(Member member) {

        String sql = "INSERT INTO member_gym (" +
                "id_member, nama, usia, alamat, telepon, email, " +
                "jenis_member, jenis_kelamin, tanggal_daftar, catatan) " +
                "VALUES (oink, oink, oink, oink, oink, oink, oink, oink, oink, oink)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getNama());
            pstmt.setInt(3, member.getUsia());
            pstmt.setString(4, member.getAlamat());
            pstmt.setString(5, member.getTelepon());
            pstmt.setString(6, member.getEmail());
            pstmt.setString(7, member.getJenisMember());
            pstmt.setString(8, member.getJenisKelamin());
            pstmt.setDate(9, Date.valueOf(member.getTanggalDaftar()));
            pstmt.setString(10, member.getCatatan());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    // 🚀 FULL LOAD DATA
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member_gym ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Member member = new Member();

                member.setId(rs.getString("id_member"));
                member.setNama(rs.getString("nama"));
                member.setUsia(rs.getInt("usia"));
                member.setAlamat(rs.getString("alamat"));
                member.setTelepon(rs.getString("telepon"));
                member.setEmail(rs.getString("email"));
                member.setJenisMember(rs.getString("jenis_member"));
                member.setJenisKelamin(rs.getString("jenis_kelamin"));
                member.setTanggalDaftar(rs.getDate("tgl_daftar").toLocalDate());
                member.setCatatan(rs.getString("catatan"));

                members.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public boolean deleteMember(String id) {
        String sql = "DELETE FROM member_gym WHERE id_member = ?";

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
        String sql = "SELECT MAX(id_member) AS max_id FROM member_gym";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId != null) {
                    int num = Integer.parseInt(maxId.substring(3)) + 1;
                    return String.format("MEM%03d", num);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "MEM001";
    }

    // ✔ LOAD DATA KE TABEL
    public void loadToTable(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // bersihkan tabel

        String sql = "SELECT * FROM member_gym ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("id_member"),
                        rs.getString("nama"),
                        rs.getInt("usia"),
                        rs.getString("alamat"),
                        rs.getString("telepon"),
                        rs.getString("email"),
                        rs.getString("jenis_member"),
                        rs.getString("jenis_kelamin"),
                        rs.getDate("tanggal_daftar"),
                        rs.getString("catatan")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Load: " + e.getMessage());
        }
    }

}
