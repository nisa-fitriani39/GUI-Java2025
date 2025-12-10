package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/gym_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "nisa123";

    private static Connection connection;

    // ======================================================
    // CREATE TABLES + CEK KONEKSI
    // ======================================================
    public static void createTables() {

        String[] createTableSQL = {
                "CREATE TYPE IF NOT EXISTS jenis_kelamin_enum AS ENUM ('L', 'P');",
                "CREATE TYPE IF NOT EXISTS jenis_member_enum AS ENUM ('REGULER', 'PREMIUM', 'VIP');",
                "CREATE TYPE IF NOT EXISTS hari_enum AS ENUM ('Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu');",

                // TABEL INSTRUKTUR
                "CREATE TABLE IF NOT EXISTS instruktur_gym (" +
                        "id_instruktur INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                        "nama_instruktur VARCHAR(100) NOT NULL," +
                        "jenis_kelamin jenis_kelamin_enum NOT NULL," +
                        "email VARCHAR(100) UNIQUE," +
                        "usia INT," +
                        "keahlian VARCHAR(150)," +
                        "telepon VARCHAR(20)" +
                        ");",

                // TABEL MEMBER
                "CREATE TABLE IF NOT EXISTS member_gym (" +
                        "id_member INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                        "nama VARCHAR(100) NOT NULL," +
                        "usia INT," +
                        "alamat VARCHAR(200)," +
                        "telepon VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "jenis_member jenis_member_enum NOT NULL," +
                        "jenis_kelamin jenis_kelamin_enum NOT NULL," +
                        "tanggal_daftar DATE NOT NULL," +
                        "catatan TEXT" +
                        ");",

                // TABEL JADWAL
                "CREATE TABLE IF NOT EXISTS jadwal_kelas (" +
                        "id_kelas INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                        "nama_kelas VARCHAR(100) NOT NULL," +
                        "hari hari_enum NOT NULL," +
                        "waktu TIME NOT NULL," +
                        "id_instruktur INT NOT NULL," +
                        "CONSTRAINT fk_jadwal_instruktur FOREIGN KEY (id_instruktur) " +
                        "REFERENCES instruktur_gym(id_instruktur) " +
                        "ON UPDATE CASCADE ON DELETE RESTRICT" +
                        ");",

                // TABEL PENDAFTARAN
                "CREATE TABLE IF NOT EXISTS pendaftaran_kelas (" +
                        "id_pendaftaran INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                        "id_member INT NOT NULL," +
                        "id_kelas INT NOT NULL," +
                        "nama_kelas VARCHAR(100)," +
                        "nama_member VARCHAR(100)," +
                        "tanggal_daftar DATE NOT NULL," +
                        "catatan TEXT," +
                        "id_instruktur INT NOT NULL," +
                        "nama_instruktur VARCHAR(100)," +
                        "CONSTRAINT fk_daftar_member FOREIGN KEY (id_member) REFERENCES member_gym(id_member)," +
                        "CONSTRAINT fk_daftar_kelas FOREIGN KEY (id_kelas) REFERENCES jadwal_kelas(id_kelas)," +
                        "CONSTRAINT fk_daftar_instruktur FOREIGN KEY (id_instruktur) REFERENCES instruktur_gym(id_instruktur)"
                        +
                        ");"
        };

        try (Connection conn = DatabaseConnection.getConnection()) {

            // 🟢 CEK KONEKSI
            System.out.println("Koneksi OK: " + conn);

            Statement stmt = conn.createStatement();

            for (String sql : createTableSQL) {
                stmt.executeUpdate(sql);
            }

            System.out.println("Tables checked/created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======================================================
    // GET CONNECTION
    // ======================================================
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                // 🟢 Tambahkan CEK KONEKSI DI SINI JUGA
                System.out.println("Database Connected.");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgreSQL Driver tidak ditemukan!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // ======================================================
    // CLOSE CONNECTION
    // ======================================================
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
