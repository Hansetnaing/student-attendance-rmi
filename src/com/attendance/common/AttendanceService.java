package com.attendance.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AttendanceService extends Remote {

    Student addAttendance(String name,
                          int totalClasses,
                          int absentClasses)
            throws RemoteException;

    List<Student> getAllStudents()
            throws RemoteException;
}