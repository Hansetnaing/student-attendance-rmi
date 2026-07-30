# Student Attendance Eligibility System Using Java RMI

This project is a **Student Attendance Eligibility System** developed using **Java RMI (Remote Method Invocation)**. The main purpose of the system is to help tutors manage student attendance and automatically calculate attendance percentages through a client–server architecture.

In this system, the tutor enters the student name, total number of classes, and number of absent classes through a graphical user interface (GUI). The client application sends the attendance data to the RMI server. The server stores the student information in an **ArrayList**, calculates the attendance percentage using the formula:

**Attendance % = ((Total Classes − Absent Classes) / Total Classes) × 100**

The system then checks whether the student has at least **75% attendance**. Students with attendance greater than or equal to 75% are marked **Eligible for Exam**, while students below 75% are marked **Not Eligible for Exam**. The calculated result is returned from the server and displayed in the GUI.

This project demonstrates the concepts of **distributed computing, remote method invocation, client–server communication, object serialization, and Java Swing GUI development**. It is designed as an educational mini-project for university students to understand how Java RMI works in a real-world attendance management scenario.