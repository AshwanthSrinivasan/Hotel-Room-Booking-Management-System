package Hotel_Room_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String url="jdbc:mysql://localhost/Hotel_Management_System";
    private static final String user ="root";
    private static final String pass ="Lakshmivasan74@";


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user, pass);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}
