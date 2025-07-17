package Hotel_Room_Management_System;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    public static void main(String args[]) throws SQLException
    {
        Scanner sc = new Scanner(System.in);

        AdminService as = new AdminService();
        BookingService bs = new BookingService();
        RoomService room = new RoomService();

        System.out.println("---------------------------Welcome To Hotel Room Management System --------------------------------------");
        System.out.println("======================= LOGIN ========================");
      /*  Boolean validate = as.validateUser();
        if(validate)
        {
          */  int i=0;
            do {
                System.out.println("---------------------------Welcome To Hotel Room Management System --------------------------------------");

                System.out.println("========MENU========");

                System.out.println("1. Add New Room \n2. View Available Rooms \n3. Book a Room \n4. View Booking History \n5. CheckOut Room \n6.Cancel Booking \n7.Exit ");
                try {
                    i = sc.nextInt();
                    switch (i) {
                        case 1:
                            room.addRoom();
                            break;
                        case 2:
                            room.viewRoom();
                            break;
                        case 3:
                            bs.bookRoom();
                            break;
                        case 4:
                             bs.viewBooking();
                            break;
                        case 5:
                             bs.checkOut();
                            break;
                        case 6:
                             bs.cancelBooking();
                            break;
                        case 7:
                            System.out.println("Exiting.....");
                            break;
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid choice! Please choose a number between 1 and 7.");
                    sc.nextLine();
                }
            } while (i!=7);
                System.out.println("Thank You !");
                sc.close();
         }
//        else{
//            System.out.println("Invalid Credentials ! ");
//        }
    }

