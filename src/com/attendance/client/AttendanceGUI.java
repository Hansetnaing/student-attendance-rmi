package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class AttendanceGUI extends JFrame {

    private JTextField txtName;
    private JTextField txtTotal;
    private JTextField txtAbsent;

    private JLabel lblPercentage;
    private JLabel lblStatus;

    private JTable table;
    private DefaultTableModel model;

    private AttendanceService service;

    public AttendanceGUI() {

        // Windows look and feel
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Student Attendance Eligibility System Using Java RMI");
        setSize(950, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(240, 244, 248));

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
                    "Cannot connect to RMI Server",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI() {

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Main container
        JPanel container = new JPanel();
        container.setBackground(new Color(240, 244, 248));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel(
                "Student Attendance Eligibility System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(30, 64, 175));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 244, 248));
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 10, 0));
        titlePanel.add(title);

        container.add(titlePanel);

        // Form Card
        JPanel formCard = new JPanel(new GridLayout(3, 2, 12, 12));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblName = new JLabel("Student Name:");
        lblName.setFont(labelFont);

        JLabel lblTotal = new JLabel("Total Classes:");
        lblTotal.setFont(labelFont);

        JLabel lblAbsent = new JLabel("Absent Classes:");
        lblAbsent.setFont(labelFont);

        txtName = new JTextField();
        txtName.setFont(fieldFont);

        txtTotal = new JTextField();
        txtTotal.setFont(fieldFont);

        txtAbsent = new JTextField();
        txtAbsent.setFont(fieldFont);

        formCard.add(lblName);
        formCard.add(txtName);

        formCard.add(lblTotal);
        formCard.add(txtTotal);

        formCard.add(lblAbsent);
        formCard.add(txtAbsent);

        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formWrapper.setBackground(new Color(240, 244, 248));
        formWrapper.add(formCard);

        container.add(formWrapper);

        // Button
        JButton btnAdd = new JButton("Add Student");
        btnAdd.setPreferredSize(new Dimension(200, 42));
        btnAdd.setBackground(new Color(37, 99, 235));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setFocusPainted(false);

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 244, 248));
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnAdd);

        container.add(buttonPanel);

        // Result Card
        JPanel resultCard = new JPanel(new GridLayout(2, 2, 10, 10));
        resultCard.setBackground(Color.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblPer = new JLabel("Attendance Percentage:");
        lblPer.setFont(labelFont);

        JLabel lblSta = new JLabel("Exam Status:");
        lblSta.setFont(labelFont);

        lblPercentage = new JLabel("-");
        lblPercentage.setFont(new Font(
                "Segoe UI", Font.BOLD, 16));

        lblStatus = new JLabel("-");
        lblStatus.setFont(new Font(
                "Segoe UI", Font.BOLD, 16));

        resultCard.add(lblPer);
        resultCard.add(lblPercentage);

        resultCard.add(lblSta);
        resultCard.add(lblStatus);

        JPanel resultWrapper =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultWrapper.setBackground(new Color(240, 244, 248));
        resultWrapper.add(resultCard);

        container.add(resultWrapper);

        // Table
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
        table.setFont(new Font(
                "Segoe UI", Font.PLAIN, 14));

        table.getTableHeader().setFont(new Font(
                "Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(
                new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.BLACK);

        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(191, 219, 254));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 220));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 200, 200)),
                "Student List",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14)));

        JPanel tablePanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        tablePanel.setBackground(new Color(240, 244, 248));
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 20, 0));
        tablePanel.add(scrollPane);

        container.add(tablePanel);

        add(container);

        btnAdd.addActionListener(e -> addStudent());
    }

    private void addStudent() {

        try {
            String name = txtName.getText().trim();

            int total =
                    Integer.parseInt(txtTotal.getText().trim());

            int absent =
                    Integer.parseInt(txtAbsent.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter student name.");
                return;
            }

            Student student =
                    service.addAttendance(name, total, absent);

            lblPercentage.setText(
                    String.format("%.2f %%",
                            student.getPercentage()));

            lblStatus.setText(student.getStatus());

            if (student.getPercentage() >= 75) {
                lblStatus.setForeground(
                        new Color(22, 163, 74));
            } else {
                lblStatus.setForeground(
                        new Color(220, 38, 38));
            }

            loadStudents();

            txtName.setText("");
            txtTotal.setText("");
            txtAbsent.setText("");

            txtName.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage());
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceGUI::new);
    }
}