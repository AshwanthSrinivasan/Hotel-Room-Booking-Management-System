package Hotel_Room_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RoomService {
    Scanner sc = new Scanner(System.in);
    public void addRoom() throws SQLException
    {
        int num;
        do {
            System.out.println("Enter the Room Number : ");
            num = sc.nextInt();
            Connection con = DBConnection.getConnection();
            String s = "SELECT * FROM rooms WHERE room_number = ?";
            PreparedStatement p = con.prepareStatement(s);
            p.setInt(1, num);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                System.out.println("This Room Number already exists ! ");
            } else {
                    break;
            }
            r.close();
            p.close();
            con.close();
        }
        while (true);
        String type;
        System.out.println("Enter the Room Type(AC / Non - AC) ");
        do {
            sc.nextLine();
            type = sc.nextLine();
            type=type.toUpperCase();
            if (type.equals("AC") || type.equals("NON-AC"))
            {
                break;
            }
            else {
                System.out.println("Invalid room type. Please enter AC or Non-AC");
            }
        }
        while(true);


        System.out.println("Enter the Room Price : ");
        double price;
        do {
            price = sc.nextDouble();
            if (price>0)
            {
                break;
            }
            else {
                System.out.println("Please enter positive value for room price");

            }
        }
        while (true);

        Connection conn = DBConnection.getConnection();
        String sql="INSERT INTO rooms (room_number, type, price, status) VALUES (?, ?, ?, 'AVAILABLE')";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,num);
        pstmt.setString(2,type);
        pstmt.setDouble(3,price);
        pstmt.executeUpdate();
        System.out.println("Room added successfully");
        pstmt.close();
        conn.close();
    }


    public void viewRoom() throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * from rooms where  status ='AVAILABLE' ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.println("Room Number\tRoom Type\tPrice");
        while(rs.next())
        {
            int num = rs.getInt("room_number");
            double price = rs.getDouble("price");
            String type = rs.getString("type");

            //System.out.println("Room Number\tRoom Type\tPrice");
            System.out.println(num+"\t"+type+"\t"+price);
        }
        rs.close();
        ps.close();
        conn.close();
    }
}
