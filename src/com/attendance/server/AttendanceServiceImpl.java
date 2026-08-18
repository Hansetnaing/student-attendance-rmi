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

    // ArrayList for storing students
    private List<Student> students;

    protected AttendanceServiceImpl() throws RemoteException {
        super();
        students = new ArrayList<>(); // create ArrayList
    }


    @Override
    public Student addAttendance(String name,
                                 int totalClasses,
                                 int absentClasses)
            throws RemoteException {

        // create student object
        Student student =
                new Student(name, totalClasses, absentClasses);

        // store in ArrayList
        students.add(student);

        System.out.println("Student added: " + name);

        return student;
    }

    @Override
    public List<Student> getAllStudents()
            throws RemoteException {

        return students;
    }
}