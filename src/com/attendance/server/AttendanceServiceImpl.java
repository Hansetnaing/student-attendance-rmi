package com.attendance.server;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AttendanceServiceImpl
        extends UnicastRemoteObject
        implements AttendanceService {

    // Store students in ArrayList
    private List<Student> students;

    protected AttendanceServiceImpl() throws RemoteException {
        super();
        students = new ArrayList<>();

        System.out.println("[SERVER] Attendance Service initialized.");
    }

    @Override
    public Student addStudent(String name)
            throws RemoteException {

        // Check if student already exists
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println(
                        "[SERVER] Student already exists: " + name
                );
                return student;
            }
        }

        // Create new student
        Student student = new Student(name);

        // Store student
        students.add(student);

        System.out.println(
                "[SERVER] Student added: " + name
        );

        return student;
    }

    @Override
    public void markAttendance(String name, boolean present)
            throws RemoteException {

        Student student = findStudent(name);

        if (student == null) {
            System.out.println(
                    "[SERVER] ERROR: Student not found: " + name
            );
            return;
        }

        // Add attendance record
        student.addAttendance(present);

        String status = present ? "PRESENT" : "ABSENT";

        System.out.println(
                "[SERVER] Attendance recorded: "
                        + name + " -> " + status
        );

        System.out.printf(
                "[SERVER] %s | Total: %d | Present: %d | Absent: %d | Percentage: %.2f%%%n",
                name,
                student.getTotalClasses(),
                student.getPresentClasses(),
                student.getAbsentClasses(),
                student.getPercentage()
        );

        System.out.println(
                "[SERVER] Exam Status: " + student.getStatus()
        );
    }

    @Override
    public Student getStudent(String name)
            throws RemoteException {

        Student student = findStudent(name);

        if (student == null) {
            System.out.println(
                    "[SERVER] Student not found: " + name
            );
        } else {
            System.out.println(
                    "[SERVER] Student information requested: "
                            + name
            );
        }

        return student;
    }

    @Override
    public List<Student> getAllStudents()
            throws RemoteException {

        System.out.println(
                "[SERVER] Sending all students. Count: "
                        + students.size()
        );

        return students;
    }

    @Override
    public boolean studentExists(String name)
            throws RemoteException {

        Student student = findStudent(name);

        if (student != null) {
            System.out.println(
                    "[SERVER] Student exists: " + name
            );
            return true;
        }

        return false;
    }

    // Helper method to find a student
    private Student findStudent(String name) {

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }

        return null;
    }
}