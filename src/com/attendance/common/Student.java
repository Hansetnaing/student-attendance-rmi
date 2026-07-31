package com.attendance.common;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private int totalClasses;
    private int absentClasses;

    public Student(String name, int totalClasses, int absentClasses) {
        this.name = name;
        this.totalClasses = totalClasses;
        this.absentClasses = absentClasses;
    }

    public String getName() {
        return name;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getAbsentClasses() {
        return absentClasses;
    }

    public double getPercentage() {
        if (totalClasses == 0) return 0;

        int attended = totalClasses - absentClasses;
        return (attended * 100.0) / totalClasses;
    }

    public String getStatus() {
        return getPercentage() >= 75 ?
                "Eligible for Exam" :
                "Not Eligible for Exam";
    }
}