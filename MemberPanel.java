package view.panels;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.MemberDAO;
import util.DatabaseConnection;

public class MemberPanel extends JPanel {

    private JTextField txtId, txtNama, txtAlamat, txtTelepon, txtEmail, txtTanggalDaftar;
    private JComboBox<String> cmbJenisMember, cmbJenisKelamin;
    private JTable table;
    private DefaultTableModel tableModel;

    private int selectedMemberId = -1;

    public MemberPanel() {
        initUI();
        loadDataFromDatabase();
        MemberDAO dao = new MemberDAO();
        dao.loadToTable(table); // <-- table milik panel
    }

    private void initUI() {

        setLayout(new BorderLayout(10, 10));

        // ===================== FORM =====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Registrasi Member"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // ID
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("ID Member"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        formPanel.add(txtId, gbc);
        row++;

        // Nama
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Nama"), gbc);
        gbc.gridx = 1;
        txtNama = new JTextField(20);
        formPanel.add(txtNama, gbc);
        row++;

        // Alamat
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Alamat"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextField(20);
        formPanel.add(txtAlamat, gbc);
        row++;

        // Telepon
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Telepon"), gbc);
        gbc.gridx = 1;
        txtTelepon = new JTextField(20);
        formPanel.add(txtTelepon, gbc);
        row++;

        // Email
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        row++;

        // Jenis Kelamin
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Jenis Kelamin"), gbc);
        gbc.gridx = 1;
        cmbJenisKelamin = new JComboBox<>(new String[] { "Laki-Laki", "Perempuan" });
        formPanel.add(cmbJenisKelamin, gbc);
        row++;

        // Jenis Member (TIDAK DIUBAH SESUAI PERMINTAAN)
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Jenis Member"), gbc);
        gbc.gridx = 1;
        cmbJenisMember = new JComboBox<>(new String[] { "Reguler", "Premium", "VIP" });
        formPanel.add(cmbJenisMember, gbc);
        row++;

        // Tanggal daftar (readonly)
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Tanggal Daftar"), gbc);
        gbc.gridx = 1;
        txtTanggalDaftar = new JTextField(20);
        txtTanggalDaftar.setEditable(false);
        formPanel.add(txtTanggalDaftar, gbc);
        row++;

        // Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnReset = new JButton("Reset");
        btnPanel.add(btnSimpan);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // ===================== TABLE =====================
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nama", "Alamat", "Telepon", "Email", "Jenis Kelamin", "Jenis Member",
                        "Tanggal Daftar" },
                0);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===================== EVENT HANDLER =====================
        btnSimpan.addActionListener(e -> simpanMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember());
        btnReset.addActionListener(e -> resetForm());

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        // Layout final
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // ======================= CRUD =======================

    private void loadDataFromDatabase() {
        String sql = "SELECT * FROM member_gym ORDER BY id_member ASC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[] {
                        rs.getInt("id_member"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("telepon"),
                        rs.getString("email"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("jenis_member"),
                        rs.getDate("tanggal_daftar")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Load data gagal: " + e.getMessage());
        }
    }

    private void simpanMember() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO member_gym VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)");

            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.setString(2, txtNama.getText());
            ps.setString(3, txtAlamat.getText());
            ps.setString(4, txtTelepon.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, cmbJenisKelamin.getSelectedItem().toString());
            ps.setString(7, cmbJenisMember.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data member berhasil disimpan!");

            loadDataFromDatabase();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Simpan gagal: " + e.getMessage());
        }
    }

    private void updateMember() {
        if (selectedMemberId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("""
                        UPDATE member_gym SET
                        nama=?, alamat=?, telepon=?, email=?, jenis_kelamin=?, jenis_member=?
                        WHERE id_member=?
                    """);

            ps.setString(1, txtNama.getText());
            ps.setString(2, txtAlamat.getText());
            ps.setString(3, txtTelepon.getText());
            ps.setString(4, txtEmail.getText());
            ps.setString(5, cmbJenisKelamin.getSelectedItem().toString());
            ps.setString(6, cmbJenisMember.getSelectedItem().toString());
            ps.setInt(7, selectedMemberId);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data member berhasil diupdate!");
            loadDataFromDatabase();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Update gagal: " + e.getMessage());
        }
    }

    private void deleteMember() {
        if (selectedMemberId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM member_gym WHERE id_member=?");
            ps.setInt(1, selectedMemberId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");

            loadDataFromDatabase();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete gagal: " + e.getMessage());
        }
    }

    // ===================== TABLE → FORM =====================
    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        selectedMemberId = Integer.parseInt(table.getValueAt(row, 0).toString());

        txtId.setText(table.getValueAt(row, 0).toString());
        txtNama.setText(table.getValueAt(row, 1).toString());
        txtAlamat.setText(table.getValueAt(row, 2).toString());
        txtTelepon.setText(table.getValueAt(row, 3).toString());
        txtEmail.setText(table.getValueAt(row, 4).toString());
        cmbJenisKelamin.setSelectedItem(table.getValueAt(row, 5).toString());
        cmbJenisMember.setSelectedItem(table.getValueAt(row, 6).toString());
        txtTanggalDaftar.setText(table.getValueAt(row, 7).toString());
    }

    private void resetForm() {
        txtId.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        txtEmail.setText("");
        cmbJenisKelamin.setSelectedIndex(0);
        cmbJenisMember.setSelectedIndex(0);
        txtTanggalDaftar.setText("");
        selectedMemberId = -1;
    }
}
