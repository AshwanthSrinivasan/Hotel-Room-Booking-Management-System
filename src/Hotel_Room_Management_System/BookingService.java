package Hotel_Room_Management_System;

import java.io.StringReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Scanner;

public class BookingService {
    Scanner sc = new Scanner(System.in);
    public void bookRoom() throws SQLException
    {
        System.out.println("Enter your Name : ");
        String name = sc.nextLine();
        System.out.println("Please Enter the Room Type (AC or NON-AC) :");
        String type;
        do {
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
        Connection con = DBConnection.getConnection();
        String sql = "Select * from rooms where status = 'AVAILABLE' and type = ? ";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,type);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst())
        {
            System.out.println("No rooms available for selected type");
            return;
        }else {
            System.out.println("Room Number\tRoom Type\tPrice");
        while(rs.next())
        {
            int num = rs.getInt("room_number");
            double price = rs.getDouble("price");
            String type1 = rs.getString("type");
            System.out.println(num+"\t\t"+type1+"\t\t"+price);
        }}
        System.out.println("Please Enter the Room Number : ");
        int room = sc.nextInt();
        String validateSql = "SELECT * FROM rooms WHERE room_number = ? AND status = 'AVAILABLE' AND type = ?";
        PreparedStatement validateStmt = con.prepareStatement(validateSql);
        validateStmt.setInt(1, room);
        validateStmt.setString(2, type);
        ResultSet validateRs = validateStmt.executeQuery();

        if (!validateRs.next()) {
            System.out.println("Invalid room number or room is not available.");
            validateRs.close();
            validateStmt.close();
            con.close();
            return;
        }
        validateRs.close();
        validateStmt.close();
        sc.nextLine();
        LocalDate checkin, checkout;
        try {
            System.out.println("Enter Check-In Date (yyyy-MM-dd): ");
            String in = sc.nextLine();
            checkin = LocalDate.parse(in);

            System.out.println("Enter Check-Out Date (yyyy-MM-dd): ");
            String out = sc.nextLine();
            checkout = LocalDate.parse(out);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter date as yyyy-MM-dd.");
            con.close();
            return;
        }

        if (checkout.isAfter(checkin)) {
            System.out.println("Are you Sure to Book this room ? (yes/no)");
            String opinion = sc.nextLine();
            opinion=opinion.toLowerCase();
            if (opinion.equals("yes")) {
                String sq1 = "Update rooms set status ='BOOKED' where room_number = ? ";
                String sq2 = "INSERT INTO bookings (room_number, customer_name, check_in, check_out, status) VALUES (?, ?, ?, ?,'ACTIVE')";
                PreparedStatement p = con.prepareStatement(sq1);
                PreparedStatement pstmt = con.prepareStatement(sq2);
                p.setInt(1, room);
                p.executeUpdate();
                pstmt.setInt(1,room);
                pstmt.setString(2,name);
                pstmt.setDate(3, Date.valueOf(checkin));
                pstmt.setDate(4, Date.valueOf(checkout));
                pstmt.executeUpdate();
                System.out.println("Room Booked Successfully ! ");
                p.close();
                pstmt.close();

            }
            else
            {
                System.out.println("Booking Not Processed ");
            }

        }
        else{
            System.out.println("Check-In Date is After the Check-Out Date .");
        }
        ps.close();
        rs.close();
        con.close();
    }


    public void viewBooking() throws  SQLException {
        Connection conn = DBConnection.getConnection();
        String sql ="SELECT * FROM bookings";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();
        if (rs.isBeforeFirst())
        {
            System.out.println("Room No.\tCustomer Name\t\tCheck-In\t\tCheck-Out\t\t\tStatus");
                while (rs.next())
                {
                    int roomNumber = rs.getInt("room_number");
                    String customerName = rs.getString("customer_name");
                    Date checkIn = rs.getDate("check_in");
                    Date checkOut = rs.getDate("check_out");
                    String status = rs.getString("status");

                    System.out.println(roomNumber + "\t\t\t" + customerName + "\t\t\t" + checkIn + "\t\t" + checkOut + "\t\t" + status);
                }
            }
        else {
            System.out.println("No booking records found.");
        }
        rs.close();
        pstmt.close();
        conn.close();
    }

    public void checkOut() throws SQLException
    {
        System.out.println("Enter your Room Number : ");
        int room = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from bookings where room_number = ? and status ='ACTIVE' ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,room);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            String sql1="UPDATE bookings SET status = 'COMPLETED' WHERE room_number = ? AND status = 'ACTIVE'";
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1,room);
            pst.executeUpdate();
            pst.close();
            System.out.println("Checked Out Successfully ! ");

            String sql2 = "Update rooms set status = 'AVAILABLE' where room_number = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setInt(1,room);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        else {
            System.out.println("No active booking found for this room.");
        }
        pstmt.close();
        rs.close();
        conn.close();

    }

    public void cancelBooking() throws SQLException
    {
        System.out.print("Enter your Room Number to cancel booking: ");
        int room = sc.nextInt();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM bookings WHERE room_number = ? AND status = 'ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, room);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next())
        {
            String sql1="UPDATE bookings SET status = 'CANCELLED' WHERE room_number = ? AND status = 'ACTIVE'";
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setInt(1,room);
            pst.executeUpdate();
            pst.close();
            System.out.println("Booking cancelled successfully!");

            String sql2 = "Update rooms set status = 'AVAILABLE' where room_number = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setInt(1,room);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        else {
            System.out.println("No active booking found for this room.");
        }
        pstmt.close();
        rs.close();
        conn.close();

    }
}
