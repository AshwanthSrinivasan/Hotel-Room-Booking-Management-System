# 🏨 Hotel Room Booking Management System

A simple Java-based hotel room booking management system with MySQL integration. This application allows customers to book rooms, view booking history, check out, and cancel bookings. It also handles room availability updates.

---

## 🚀 Features

- Book a hotel room
- View booking history
- Check-out functionality
- Cancel a booking
- Room availability updates
- Backend validation of room status
- Menu-based interface for easy navigation

---

## 🛠️ Technologies Used

- Java (Core Java with JDBC)
- MySQL (Database)
- Eclipse IDE (for development)
- Git & GitHub (for version control)

---

## 🧱 Database Details

**Database Name:** `hotel_management_system`  
**Tables Used:** `rooms`, `bookings`

### 🔸 Table 1: `rooms`
| Column Name   | Data Type | Description                  |
|---------------|-----------|------------------------------|
| room_number   | INT       | Primary Key, Unique ID       |
| room_type     | VARCHAR   | Type of room (e.g., Deluxe)  |
| price         | DOUBLE    | Price per night              |
| status        | VARCHAR   | AVAILABLE / BOOKED           |

### 🔸 Table 2: `bookings`
| Column Name    | Data Type | Description                      |
|----------------|-----------|----------------------------------|
| id             | INT       | Primary Key, Auto Increment      |
| room_number    | INT       | Foreign Key referenc_
