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

        setTitle("Student Attendance Eligibility System");
        setSize(980, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(189, 189, 201));

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
        container.setBackground(new Color(24, 24, 27));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel(
                "Student Attendance Eligibility System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(96, 165, 250));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 244, 248));
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(30, 0, 20, 0));
        titlePanel.add(title);

        container.add(titlePanel);

        // Form Card
        JPanel formCard = new JPanel(new GridLayout(3, 2, 12, 12));
        formCard.setBackground(new Color(39, 39, 42));
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

        // Label & Text Field Color
        lblName.setForeground(Color.WHITE);
        lblTotal.setForeground(Color.WHITE);
        lblAbsent.setForeground(Color.WHITE);

        txtName.setBackground(new Color(63, 63, 70));
        txtName.setForeground(Color.WHITE);
        txtName.setCaretColor(Color.WHITE);

        txtTotal.setBackground(new Color(63, 63, 70));
        txtTotal.setForeground(Color.WHITE);
        txtTotal.setCaretColor(Color.WHITE);

        txtAbsent.setBackground(new Color(63, 63, 70));
        txtAbsent.setForeground(Color.WHITE);
        txtAbsent.setCaretColor(Color.WHITE);

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
        btnAdd.setBackground(new Color(17, 24, 39));
        btnAdd.setForeground(Color.WHITE);
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
        resultCard.setBackground(new Color(39, 39, 42));
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblPer = new JLabel("Attendance Percentage:");
        lblPer.setFont(labelFont);

        JLabel lblSta = new JLabel("Exam Status:");
        lblSta.setFont(labelFont);

        lblPer.setForeground(Color.WHITE);
        lblSta.setForeground(Color.WHITE);


        lblPercentage = new JLabel("-");
        lblPercentage.setFont(new Font(
                "Segoe UI", Font.BOLD, 16));

        lblStatus = new JLabel("-");
        lblStatus.setFont(new Font(
                "Segoe UI", Font.BOLD, 16));

        lblPercentage.setForeground(Color.WHITE);
        lblStatus.setForeground(Color.WHITE);

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
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        table.getTableHeader().setFont(new Font(
                "Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(17, 24, 39));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(191, 219, 254));

        table.setBackground(new Color(24, 24, 27));
        table.setForeground(Color.WHITE);
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionBackground(new Color(59, 130, 246));

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
        tablePanel.setBackground(new Color(24, 24, 27));
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

            if (total <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Total classes must be greater than 0.");
                return;
            }

            if (absent < 0) {
                JOptionPane.showMessageDialog(this,
                        "Absent classes cannot be negative.");
                return;
            }

            if (absent > total) {
                JOptionPane.showMessageDialog(this,
                        "Absent classes cannot be greater than total classes.");
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
                    "Cannot connect to RMI Server.");
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