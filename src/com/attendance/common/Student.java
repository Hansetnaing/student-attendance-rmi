package com.attendance.common;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private int absentClasses;
    private int totalClasses;

    public Student(String name, int attendedClasses, int totalClasses) {
        this.name = name;
        this.absentClasses = attendedClasses;
        this.totalClasses = totalClasses;
    }

    public String getName() {
        return name;
    }

    public int getAbsentClasses() {
        return absentClasses;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public double getPercentage() {
        return ((totalClasses - absentClasses) * 100.0) / totalClasses;
    }

    public String getStatus() {
        return getPercentage() >= 75 ? "Eligible for Exam" : "Not Eligible for Exam";
    }
}