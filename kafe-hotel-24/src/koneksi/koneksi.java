package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sadindiiw!
 */
public class koneksi {
    private static Connection conn = null;
    
    public static Connection getCon() {
        if(conn != null) return conn;
        else {
            String dbUrl = "jdbc:mysql://localhost:3306/kasircafe?user=root&password=&serverTimezone=UTC";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbUrl);
            } catch (ClassNotFoundException e) {
                System.out.println("Driver tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Koneksi gagal: " + e.getMessage());
            }
            return conn;
        }
    }
    public static void main(String[] args) {
        getCon();
    }
}
