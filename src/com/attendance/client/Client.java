package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            AttendanceService service =
                    (AttendanceService) registry.lookup("AttendanceService");

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            System.out.print("Total Classes: ");
            int total = sc.nextInt();

            System.out.print("Absent Classes: ");
            int absent = sc.nextInt();

            Student student =
                    service.addAttendance(name, total, absent);

            System.out.println("\n--- Attendance Result ---");
            System.out.println("Name: " + student.getName());
            System.out.println("Total Classes: " + student.getTotalClasses());
            System.out.println("Absent Classes: " + student.getAbsentClasses());

            System.out.printf("Attendance Percentage: %.2f%%\n",
                    student.getPercentage());

            System.out.println("Status: " + student.getStatus());

            System.out.println("\n--- All Students ---");

            for (Student s : service.getAllStudents()) {
                System.out.printf("%s - %.2f%% - %s\n",
                        s.getName(),
                        s.getPercentage(),
                        s.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}