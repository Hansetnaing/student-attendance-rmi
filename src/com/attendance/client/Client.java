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

            System.out.print("Absented Classes: ");
            int absent = sc.nextInt();

            Student student =
                    service.addAttendance(name, absent, total);

            System.out.println("--- Attendance Result ---");
            System.out.println("Name: " + student.getName());
            System.out.println("Absented: " + student.getAbsentClasses());
            System.out.println("Total: " + student.getTotalClasses());
            System.out.printf("Percentage: %.2f%%\n",
                    student.getPercentage());
            System.out.println("Status: " + student.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}