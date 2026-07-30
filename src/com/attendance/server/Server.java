package com.attendance.server;

import com.attendance.common.AttendanceService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {
            AttendanceService service = new AttendanceServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("AttendanceService", service);

            System.out.println("Attendance RMI Server started...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}