# Student Attendance RMI System

A simple **Student Attendance Management System** developed using **Java RMI (Remote Method Invocation)**.

The system allows a teacher/tutor to manage students and record their daily attendance. Attendance percentage and exam eligibility are calculated automatically.

---

## 📌 Project Overview

This project demonstrates how Java RMI can be used to build a client-server application.

The system has three main parts:

- **Client** – Provides the user interface and sends requests.
- **Server** – Processes attendance operations and manages student data.
- **Common** – Contains shared classes and the RMI interface.

The project does **not use a database**. Student data is currently stored in an `ArrayList` on the RMI server.

---

## ✨ Features

### Student Management

- Add a student manually
- Upload multiple student names from a `.txt` file
- Prevent duplicate students
- View all students

### Attendance Management

- Select a student
- Mark attendance as **Present**
- Mark attendance as **Absent**
- Automatically calculate:
    - Total Classes
    - Present Classes
    - Absent Classes
    - Attendance Percentage
- Automatically determine exam eligibility

### GUI

The system uses Java Swing with the operating system's native Look & Feel.

The GUI provides:

- Student name input
- Add Student button
- Upload Names button
- Student selection
- Present / Absent buttons
- Attendance information
- Student list window

### RMI Communication

The client communicates with the server using Java RMI.

The main remote operations are:

```java
addStudent()
studentExists()
markAttendance()
getStudent()
getAllStudents()