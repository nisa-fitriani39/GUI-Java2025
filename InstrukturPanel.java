package view.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InstrukturPanel extends JPanel {

    private JTextField txtIdInstruktur, txtNama, txtEmail, txtUsia, txtTelepon, txtKeahlian;
    private JComboBox<String> cmbGender;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnSimpan, btnUpdate, btnDelete, btnReset;

    public InstrukturPanel() {

        setLayout(new BorderLayout());

        // ========================= PANEL FORM =========================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Instruktur"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        JLabel lblId = new JLabel("ID Instruktur:");
        JLabel lblNama = new JLabel("Nama Instruktur:");
        JLabel lblGender = new JLabel("Jenis Kelamin:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblUsia = new JLabel("Usia:");
        JLabel lblKeahlian = new JLabel("Keahlian:");
        JLabel lblTelepon = new JLabel("No. Telepon:");

        // Input Fields
        txtIdInstruktur = new JTextField(15);
        txtIdInstruktur.setEnabled(false); // ID auto from database

        txtNama = new JTextField(15);
        txtEmail = new JTextField(15);
        txtUsia = new JTextField(15);
        txtKeahlian = new JTextField(15);
        txtTelepon = new JTextField(15);

        cmbGender = new JComboBox<>(new String[] { "Laki-laki", "Perempuan" });

        // Buttons
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Hapus");
        btnReset = new JButton("Reset");

        // ========== POSISI FORM ==========

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblId, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdInstruktur, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblNama, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblGender, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbGender, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblUsia, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblKeahlian, gbc);
        gbc.gridx = 1;
        formPanel.add(txtKeahlian, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblTelepon, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTelepon, gbc);

        // tombol
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButton.add(btnSimpan);
        panelButton.add(btnUpdate);
        panelButton.add(btnDelete);
        panelButton.add(btnReset);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(panelButton, gbc);

        // ========================= TABEL =========================
        String[] kolom = {
                "ID Instruktur",
                "Nama Instruktur",
                "Jenis Kelamin",
                "Email",
                "Usia",
                "Keahlian",
                "No Telepon"
        };

        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Instruktur"));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ========================= AKSI =========================
        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> hapusData());

        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTable());
    }

    // =========================== SIMPAN ===========================
    private void simpanData() {
        String nama = txtNama.getText();
        String gender = (String) cmbGender.getSelectedItem();
        String email = txtEmail.getText();
        String usia = txtUsia.getText();
        String keahlian = txtKeahlian.getText();
        String telepon = txtTelepon.getText();

        if (nama.isEmpty() || email.isEmpty() || usia.isEmpty() || telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.addRow(new Object[] {
                null, nama, gender, email, usia, keahlian, telepon
        });

        resetForm();
    }

    // =========================== UPDATE ===========================
    private void updateData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.setValueAt(txtNama.getText(), row, 1);
        model.setValueAt(cmbGender.getSelectedItem(), row, 2);
        model.setValueAt(txtEmail.getText(), row, 3);
        model.setValueAt(txtUsia.getText(), row, 4);
        model.setValueAt(txtKeahlian.getText(), row, 5);
        model.setValueAt(txtTelepon.getText(), row, 6);

        resetForm();
    }

    // =========================== HAPUS ===========================
    private void hapusData() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================== ISI FORM ===========================
    private void isiFormDariTable() {
        int row = table.getSelectedRow();
        if (row != -1) {
            txtIdInstruktur.setText(model.getValueAt(row, 0) == null ? "" : model.getValueAt(row, 0).toString());
            txtNama.setText(model.getValueAt(row, 1).toString());
            cmbGender.setSelectedItem(model.getValueAt(row, 2).toString());
            txtEmail.setText(model.getValueAt(row, 3).toString());
            txtUsia.setText(model.getValueAt(row, 4).toString());
            txtKeahlian.setText(model.getValueAt(row, 5).toString());
            txtTelepon.setText(model.getValueAt(row, 6).toString());
        }
    }

    // =========================== RESET ===========================
    private void resetForm() {
        txtIdInstruktur.setText("");
        txtNama.setText("");
        txtEmail.setText("");
        txtUsia.setText("");
        txtKeahlian.setText("");
        txtTelepon.setText("");
        cmbGender.setSelectedIndex(0);

        table.clearSelection();
    }
}
