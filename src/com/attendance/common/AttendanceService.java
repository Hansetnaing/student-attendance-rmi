package com.attendance.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AttendanceService extends Remote {

    Student addAttendance(String name,
                          int attended, int total)
            throws RemoteException;
}