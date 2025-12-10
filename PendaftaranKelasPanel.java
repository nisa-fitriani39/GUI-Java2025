package view.panels;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.DatabaseConnection;

public class PendaftaranKelasPanel extends JPanel {

    private JTextField txtIdMember, txtNamaMember, txtNamaKelas, txtInstruktur;
    private JComboBox<String> cmbHari;
    private JTable table;
    private DefaultTableModel model;

    // Untuk menyimpan id_pendaftaran yang sedang dipilih
    private int selectedPendaftaranId = -1;

    public PendaftaranKelasPanel() {

        setLayout(new BorderLayout());

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pendaftaran Kelas"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblIdMember = new JLabel("ID Member:");
        JLabel lblNamaMember = new JLabel("Nama Member:");
        JLabel lblNamaKelas = new JLabel("Nama Kelas:");
        JLabel lblHari = new JLabel("Hari:");
        JLabel lblInstruktur = new JLabel("Instruktur:");

        txtIdMember = new JTextField(15);
        txtNamaMember = new JTextField(15);
        txtNamaKelas = new JTextField(15);
        txtInstruktur = new JTextField(15);

        cmbHari = new JComboBox<>(new String[] {
                "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"
        });

        JButton btnDaftar = new JButton("Daftar");
        JButton btnReset = new JButton("Reset");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");

        // ===== LETAK GRID =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblIdMember, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdMember, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblNamaMember, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNamaMember, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblNamaKelas, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNamaKelas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblHari, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbHari, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblInstruktur, gbc);
        gbc.gridx = 1;
        formPanel.add(txtInstruktur, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(btnDaftar, gbc);
        gbc.gridx = 1;
        formPanel.add(btnReset, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(btnUpdate, gbc);
        gbc.gridx = 1;
        formPanel.add(btnDelete, gbc);

        // ==================== TABLE ====================
        String[] kolom = { "ID Member", "Nama Member", "Nama Kelas", "Hari", "Instruktur", "ID Pendaftaran" };
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        table.removeColumn(table.getColumnModel().getColumn(5)); // hidden id_pendaftaran

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Pendaftaran Kelas"));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load awal
        loadDataFromDatabase();

        // ==================== ACTION ====================
        btnDaftar.addActionListener(e -> simpanKeDatabase());
        btnReset.addActionListener(e -> resetForm());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());

        // Klik tabel → ambil data ke form
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    // ==================== READ ====================
    private void loadDataFromDatabase() {
        String sql = """
                SELECT
                    pk.id_pendaftaran,
                    pk.id_member,
                    mg.nama AS nama_member,
                    jk.nama_kelas,
                    jk.hari,
                    ig.nama AS instruktur
                FROM pendaftaran_kelas pk
                JOIN member_gym mg ON pk.id_member = mg.id_member
                JOIN jadwal_kelas jk ON pk.id_kelas = jk.id_kelas
                JOIN instruktur_gym ig ON jk.id_instruktur = ig.id_instruktur
                ORDER BY pk.id_pendaftaran ASC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_member"),
                        rs.getString("nama_member"),
                        rs.getString("nama_kelas"),
                        rs.getString("hari"),
                        rs.getString("instruktur"),
                        rs.getInt("id_pendaftaran")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }

    // ==================== CREATE ====================
    private void simpanKeDatabase() {

        String idMember = txtIdMember.getText();
        String namaMember = txtNamaMember.getText();
        String namaKelas = txtNamaKelas.getText();
        String hari = (String) cmbHari.getSelectedItem();
        String instruktur = txtInstruktur.getText();

        if (idMember.isEmpty() || namaMember.isEmpty() || namaKelas.isEmpty() || instruktur.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Cek member
            PreparedStatement psMember = conn.prepareStatement(
                    "SELECT id_member FROM member_gym WHERE id_member=?");
            psMember.setInt(1, Integer.parseInt(idMember));
            ResultSet rs1 = psMember.executeQuery();

            if (!rs1.next()) {
                JOptionPane.showMessageDialog(this, "Member tidak ditemukan!");
                return;
            }

            // Cari kelas
            PreparedStatement psKelas = conn.prepareStatement(
                    "SELECT id_kelas FROM jadwal_kelas WHERE nama_kelas=? AND hari=?");
            psKelas.setString(1, namaKelas);
            psKelas.setString(2, hari);

            ResultSet rs2 = psKelas.executeQuery();
            if (!rs2.next()) {
                JOptionPane.showMessageDialog(this, "Kelas tidak ditemukan!");
                return;
            }

            int idKelas = rs2.getInt("id_kelas");

            // Insert
            PreparedStatement insert = conn.prepareStatement("""
                    INSERT INTO pendaftaran_kelas
                    (id_pendaftaran, id_member, id_kelas, tanggal_daftar, catatan)
                    VALUES ((SELECT COALESCE(MAX(id_pendaftaran),0)+1 FROM pendaftaran_kelas), ?, ?, CURRENT_DATE, '-')
                    """);

            insert.setInt(1, Integer.parseInt(idMember));
            insert.setInt(2, idKelas);
            insert.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pendaftaran berhasil!");
            loadDataFromDatabase();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error simpan: " + e.getMessage());
        }
    }

    // ==================== UPDATE ====================
    private void updateData() {
        if (selectedPendaftaranId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin mengubah data?",
                "Konfirmasi Update",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        String idMember = txtIdMember.getText();
        String namaKelas = txtNamaKelas.getText();
        String hari = (String) cmbHari.getSelectedItem();

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Cek kelas
            PreparedStatement psKelas = conn.prepareStatement(
                    "SELECT id_kelas FROM jadwal_kelas WHERE nama_kelas=? AND hari=?");
            psKelas.setString(1, namaKelas);
            psKelas.setString(2, hari);

            ResultSet rs = psKelas.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Kelas tidak ditemukan!");
                return;
            }

            int idKelas = rs.getInt("id_kelas");

            // Update
            PreparedStatement update = conn.prepareStatement("""
                    UPDATE pendaftaran_kelas
                    SET id_member=?, id_kelas=?
                    WHERE id_pendaftaran=?
                    """);
            update.setInt(1, Integer.parseInt(idMember));
            update.setInt(2, idKelas);
            update.setInt(3, selectedPendaftaranId);

            update.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadDataFromDatabase();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage());
        }
    }

    // ==================== DELETE ====================
    private void deleteData() {
        if (selectedPendaftaranId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus data ini?",
                "Konfirmasi Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM pendaftaran_kelas WHERE id_pendaftaran=?");
            ps.setInt(1, selectedPendaftaranId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDataFromDatabase();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete error: " + e.getMessage());
        }
    }

    // ==================== TABLE → FORM ====================
    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        txtIdMember.setText(table.getValueAt(row, 0).toString());
        txtNamaMember.setText(table.getValueAt(row, 1).toString());
        txtNamaKelas.setText(table.getValueAt(row, 2).toString());
        cmbHari.setSelectedItem(table.getValueAt(row, 3));
        txtInstruktur.setText(table.getValueAt(row, 4).toString());

        selectedPendaftaranId = Integer.parseInt(model.getValueAt(row, 5).toString());
    }

    // ==================== RESET ====================
    private void resetForm() {
        txtIdMember.setText("");
        txtNamaMember.setText("");
        txtNamaKelas.setText("");
        txtInstruktur.setText("");
        cmbHari.setSelectedIndex(0);
        selectedPendaftaranId = -1;
    }
}
