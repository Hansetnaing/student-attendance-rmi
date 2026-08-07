package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AttendanceGUI extends JFrame {

    private JTextField txtName;
    private JTextField txtTotal;
    private JTextField txtAbsent;

    private AttendanceService service;

    public AttendanceGUI() {

        // Windows look and feel
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Student Attendance Eligibility System Using Java RMI");
        setSize(700, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(165, 214, 167));

        connectRMI();
        initUI();

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
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(96, 165, 250));
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
        JButton btnAdd = new JButton("Check Student");
        btnAdd.setPreferredSize(new Dimension(220, 45));
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 244, 248));
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.add(btnAdd);

        container.add(buttonPanel);

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

            // Show result in message box
            String message =String.format("%.2f %%", student.getPercentage()) +
                            "\n" + student.getStatus();

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Your Percentage Result",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            txtName.setText("");
            txtTotal.setText("");
            txtAbsent.setText("");

            txtName.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "RMI Server doesn't work.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceGUI::new);
    }
}