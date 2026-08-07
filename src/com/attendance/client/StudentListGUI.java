package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class StudentListGUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private AttendanceService service;

    public StudentListGUI() {

        setTitle("Student List");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        connectRMI();
        initUI();
        loadStudents();

        setVisible(true);
    }

    private void connectRMI() {

        try {
            Registry registry =
                    LocateRegistry.getRegistry("localhost", 1099);

            service = (AttendanceService)
                    registry.lookup("AttendanceService");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Cannot connect to RMI Server");
        }
    }

    private void initUI() {

        model = new DefaultTableModel(
                new String[]{
                        "Name",
                        "Total",
                        "Absent",
                        "Percentage",
                        "Status"
                }, 0);

        table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadStudents() {

        try {
            model.setRowCount(0);

            List<Student> students =
                    service.getAllStudents();

            for (Student s : students) {

                model.addRow(new Object[]{
                        s.getName(),
                        s.getTotalClasses(),
                        s.getAbsentClasses(),
                        String.format("%.2f %%",
                                s.getPercentage()),
                        s.getStatus()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}