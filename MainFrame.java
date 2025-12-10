// src/view/MainFrame.java
package view;

import view.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Tambahkan form ke tabbed pane
        tabbedPane.addTab("Registrasi Member", new MemberPanel());
        tabbedPane.addTab("Data Instruktur", new InstrukturPanel());
        tabbedPane.addTab("Jadwal Kelas", new JadwalKelasPanel());
        tabbedPane.addTab("Pendaftaran Kelas", new PendaftaranKelasPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}