package com.attendance.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private String name;
    private List<Boolean> attendanceRecords;

    public Student(String name) {
        this.name = name;
        this.attendanceRecords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Boolean> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void addAttendance(boolean present) {
        attendanceRecords.add(present);
    }

    public int getTotalClasses() {
        return attendanceRecords.size();
    }

    public int getAbsentClasses() {
        int absent = 0;

        for (boolean present : attendanceRecords) {
            if (!present) {
                absent++;
            }
        }

        return absent;
    }

    public int getPresentClasses() {
        return getTotalClasses() - getAbsentClasses();
    }

    public double getPercentage() {
        if (getTotalClasses() == 0) {
            return 0;
        }

        return (getPresentClasses() * 100.0) / getTotalClasses();
    }

    public String getStatus() {
        return getPercentage() >= 75
                ? "Eligible for Exam"
                : "Not Eligible for Exam";
    }
}