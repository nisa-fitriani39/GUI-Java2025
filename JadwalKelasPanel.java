package view.panels;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JadwalKelasPanel extends JPanel {

    private JTextField txtIdKelas, txtNamaKelas, txtWaktu;
    private JComboBox<String> cmbInstruktur, cmbHari;
    private JTable table;
    private DefaultTableModel model;

    public JadwalKelasPanel() {
        setLayout(new BorderLayout());

        // ========================== FORM PANEL ==========================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Jadwal Kelas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID Kelas:");
        JLabel lblNama = new JLabel("Nama Kelas:");
        JLabel lblInstruktur = new JLabel("Instruktur:");
        JLabel lblHari = new JLabel("Hari:");
        JLabel lblWaktu = new JLabel("Waktu:");

        txtIdKelas = new JTextField(10);
        txtNamaKelas = new JTextField(15);
        txtWaktu = new JTextField(10);

        cmbInstruktur = new JComboBox<>();
        cmbHari = new JComboBox<>(new String[] {
                "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"
        });

        JButton btnSimpan = new JButton("Simpan");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");

        // ========= ADD FORM ==========
        addForm(formPanel, gbc, 0, lblId, txtIdKelas);
        addForm(formPanel, gbc, 1, lblNama, txtNamaKelas);
        addForm(formPanel, gbc, 2, lblInstruktur, cmbInstruktur);
        addForm(formPanel, gbc, 3, lblHari, cmbHari);
        addForm(formPanel, gbc, 4, lblWaktu, txtWaktu);

        // ========= BUTTONS ==========
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnSimpan);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // ========================== TABLE PANEL ==========================
        String[] column = { "ID", "Nama Kelas", "Instruktur", "Hari", "Waktu" };
        model = new DefaultTableModel(column, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Data Jadwal Kelas"));

        add(formPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ========================== LOAD DATABASE ==========================
        loadInstruktur();
        loadTable();

        // ========================== ACTION BUTTON ==========================
        btnSimpan.addActionListener(e -> simpanData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnReset.addActionListener(e -> resetForm());

        table.getSelectionModel().addListSelectionListener(e -> tableKlik());
    }

    // ----------- Helper untuk menambahkan form ----------
    private void addForm(JPanel panel, GridBagConstraints gbc, int y,
            JComponent label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // ============================ DB CONNECTION ============================
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/management_gym_db",
                "postgres",
                "your_password");
    }

    // ============================ LOAD INSTRUKTUR ============================
    private void loadInstruktur() {
        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT id_instruktur, nama FROM instruktur_gym");
                ResultSet rs = pst.executeQuery()) {

            cmbInstruktur.removeAllItems();
            cmbInstruktur.addItem("- Pilih Instruktur -");

            while (rs.next()) {
                cmbInstruktur.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load instruktur: " + e.getMessage());
        }
    }

    // ============================ LOAD TABLE ============================
    private void loadTable() {
        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM jadwal_kelas");
                ResultSet rs = pst.executeQuery()) {

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getInt("id_instruktur"),
                        rs.getString("hari"),
                        rs.getString("jam_kelas")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load table: " + e.getMessage());
        }
    }

    // ============================ SIMPAN ============================
    private void simpanData() {
        if (txtIdKelas.getText().isEmpty() || txtNamaKelas.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi semua field!");
            return;
        }

        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO jadwal_kelas VALUES (?,?,?,?,?)")) {

            pst.setInt(1, Integer.parseInt(txtIdKelas.getText()));
            pst.setString(2, txtNamaKelas.getText());
            pst.setString(3, cmbHari.getSelectedItem().toString());
            pst.setString(4, txtWaktu.getText());
            pst.setInt(5, getInstrukturId());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");

            loadTable();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error simpan: " + e.getMessage());
        }
    }

    private int getInstrukturId() {
        String selected = (String) cmbInstruktur.getSelectedItem();
        if (selected == null || selected.equals("- Pilih Instruktur -"))
            return 0;
        return Integer.parseInt(selected.split(" - ")[0]);
    }

    // ============================ UPDATE ============================
    private void updateData() {
        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(
                        "UPDATE jadwal_kelas SET nama_kelas=?, hari=?, jam_kelas=?, id_instruktur=? WHERE id_kelas=?")) {

            pst.setString(1, txtNamaKelas.getText());
            pst.setString(2, cmbHari.getSelectedItem().toString());
            pst.setString(3, txtWaktu.getText());
            pst.setInt(4, getInstrukturId());
            pst.setInt(5, Integer.parseInt(txtIdKelas.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");

            loadTable();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error update: " + e.getMessage());
        }
    }

    // ============================ DELETE ============================
    private void deleteData() {
        try (Connection conn = getConnection();
                PreparedStatement pst = conn.prepareStatement(
                        "DELETE FROM jadwal_kelas WHERE id_kelas=?")) {

            pst.setInt(1, Integer.parseInt(txtIdKelas.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");

            loadTable();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error delete: " + e.getMessage());
        }
    }

    // ============================ KLIK TABEL ============================
    private void tableKlik() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        txtIdKelas.setText(model.getValueAt(row, 0).toString());
        txtNamaKelas.setText(model.getValueAt(row, 1).toString());

        // pilih instruktur berdasarkan ID
        int idInstruktur = (int) model.getValueAt(row, 2);
        for (int i = 0; i < cmbInstruktur.getItemCount(); i++) {
            if (cmbInstruktur.getItemAt(i).startsWith(idInstruktur + " -")) {
                cmbInstruktur.setSelectedIndex(i);
                break;
            }
        }

        cmbHari.setSelectedItem(model.getValueAt(row, 3).toString());
        txtWaktu.setText(model.getValueAt(row, 4).toString());
    }

    // ============================ RESET FORM ============================
    private void resetForm() {
        txtIdKelas.setText("");
        txtNamaKelas.setText("");
        txtWaktu.setText("");
        cmbInstruktur.setSelectedIndex(0);
        cmbHari.setSelectedIndex(0);
        table.clearSelection();
    }
}
