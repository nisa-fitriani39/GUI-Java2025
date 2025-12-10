package util;

import util.DatabaseConnection;
import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Koneksi ke database BERHASIL!");
            } else {
                System.out.println("Koneksi GAGAL.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
