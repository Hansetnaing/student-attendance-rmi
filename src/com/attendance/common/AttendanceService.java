package com.attendance.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AttendanceService extends Remote {

    Student addStudent(String name)
            throws RemoteException;

    boolean studentExists(String name)
            throws RemoteException;

    void markAttendance(String name, boolean present)
            throws RemoteException;

    Student getStudent(String name)
            throws RemoteException;

    List<Student> getAllStudents()
            throws RemoteException;
}