package Hotel_Room_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminService {
    Scanner sc = new Scanner(System.in);
    public boolean validateUser() throws SQLException {
        System.out.println("Username : ");
        String username = sc.nextLine();
        System.out.println("Password : ");
        String password = sc.nextLine();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setString(2,password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            System.out.println("Login Successful !");
            rs.close();
            pstmt.close();
            conn.close();
            return true;
        }
        else {
            System.out.println("Login Failed ");
            rs.close();
            pstmt.close();
            conn.close();
            return false;
        }
    }
}
