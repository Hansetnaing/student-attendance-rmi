package com.attendance.server;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AttendanceServiceImpl extends UnicastRemoteObject
        implements AttendanceService {

    protected AttendanceServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Student addAttendance(String name,
                                 int attended, int total)
            throws RemoteException {

        return new Student(name, attended, total);
    }
}