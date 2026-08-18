package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Registry registry =
                    LocateRegistry.getRegistry("localhost", 1099);

            AttendanceService service =
                    (AttendanceService) registry.lookup("AttendanceService");

            Scanner sc = new Scanner(System.in);

            System.out.println("================================");
            System.out.println("     STUDENT ATTENDANCE RMI");
            System.out.println("================================");

            // Add student
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            Student student = service.addStudent(name);

            System.out.println("\nStudent added successfully!");
            System.out.println("Name: " + student.getName());

            // Record attendance
            System.out.print("\nHow many attendance records do you want to add? ");
            int numberOfClasses = sc.nextInt();

            for (int i = 1; i <= numberOfClasses; i++) {

                System.out.print(
                        "Class " + i + " (P = Present, A = Absent): "
                );

                String input = sc.next();

                if (input.equalsIgnoreCase("P")) {
                    service.markAttendance(name, true);
                } else if (input.equalsIgnoreCase("A")) {
                    service.markAttendance(name, false);
                } else {
                    System.out.println(
                            "Invalid input! Please enter P or A."
                    );
                    i--;
                }
            }

            // Get updated student information
            student = service.getStudent(name);

            System.out.println("\n================================");
            System.out.println("       ATTENDANCE RESULT");
            System.out.println("================================");

            System.out.println("Name: " + student.getName());
            System.out.println(
                    "Total Classes: " + student.getTotalClasses()
            );
            System.out.println(
                    "Present Classes: " + student.getPresentClasses()
            );
            System.out.println(
                    "Absent Classes: " + student.getAbsentClasses()
            );

            System.out.printf(
                    "Attendance Percentage: %.2f%%%n",
                    student.getPercentage()
            );

            System.out.println(
                    "Status: " + student.getStatus()
            );

            System.out.println("================================");

            // Show all students
            System.out.println("\n--- All Students ---");

            for (Student s : service.getAllStudents()) {

                System.out.printf(
                        "%s - %.2f%% - %s%n",
                        s.getName(),
                        s.getPercentage(),
                        s.getStatus()
                );
            }

            sc.close();

        } catch (Exception e) {
            System.out.println(
                    "[CLIENT] ERROR: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}