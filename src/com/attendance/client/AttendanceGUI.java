package com.attendance.client;

import com.attendance.common.AttendanceService;
import com.attendance.common.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class AttendanceGUI extends JFrame {

    private AttendanceService service;

    private JTextField txtName;
    private JComboBox<String> studentCombo;

    private JLabel lblTotalValue;
    private JLabel lblPresentValue;
    private JLabel lblAbsentValue;
    private JLabel lblPercentageValue;
    private JLabel lblStatusValue;

    private JButton btnPresent;
    private JButton btnAbsent;

    public AttendanceGUI() {

        // Use normal Windows/System UI
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {
        }

        setTitle("Student Attendance System");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        connectRMI();
        initUI();

        setVisible(true);
    }

    // ==============================
    // RMI CONNECTION
    // ==============================

    private void connectRMI() {

        try {
            Registry registry =
                    LocateRegistry.getRegistry("localhost", 1099);

            service = (AttendanceService)
                    registry.lookup("AttendanceService");

            System.out.println(
                    "[CLIENT] Connected to RMI Server successfully."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot connect to RMI Server.\n" +
                            "Please start the server first.",
                    "RMI Connection Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.out.println(
                    "[CLIENT] ERROR: Cannot connect to RMI Server."
            );
        }
    }

    // ==============================
    // GUI
    // ==============================

    private void initUI() {

        Font normalFont =
                new Font("Segoe UI", Font.PLAIN, 14);

        Font boldFont =
                new Font("Segoe UI", Font.BOLD, 14);

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        // ==============================
        // TITLE
        // ==============================

        JLabel title =
                new JLabel(
                        "Student Attendance System",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // ==============================
        // CENTER PANEL
        // ==============================

        JPanel centerPanel =
                new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // ==============================
        // ADD STUDENT PANEL
        // ==============================

        JPanel addPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        addPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Add Student"
                )
        );

        JLabel nameLabel =
                new JLabel("Student Name:");

        nameLabel.setFont(boldFont);

        txtName =
                new JTextField(18);

        txtName.setFont(normalFont);

        JButton btnAddStudent =
                new JButton("Add Student");

        JButton btnUploadNames =
                new JButton("Upload Names");

        btnAddStudent.setFont(boldFont);
        btnUploadNames.setFont(boldFont);

        btnAddStudent.setFocusPainted(false);
        btnUploadNames.setFocusPainted(false);

        addPanel.add(nameLabel);
        addPanel.add(txtName);
        addPanel.add(btnAddStudent);
        addPanel.add(btnUploadNames);

        centerPanel.add(addPanel);

        // ==============================
        // SELECT STUDENT
        // ==============================

        JPanel selectPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        selectPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Select Student"
                )
        );

        JLabel selectLabel =
                new JLabel("Student:");

        selectLabel.setFont(boldFont);

        studentCombo =
                new JComboBox<>();

        studentCombo.setPreferredSize(
                new Dimension(300, 28)
        );

        studentCombo.setFont(normalFont);

        selectPanel.add(selectLabel);
        selectPanel.add(studentCombo);

        centerPanel.add(selectPanel);

        // ==============================
        // ATTENDANCE BUTTONS
        // ==============================

        JPanel attendancePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                15
                        )
                );

        attendancePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Record Attendance"
                )
        );

        btnPresent =
                new JButton("PRESENT");

        btnAbsent =
                new JButton("ABSENT");

        btnPresent.setPreferredSize(
                new Dimension(150, 40)
        );

        btnAbsent.setPreferredSize(
                new Dimension(150, 40)
        );

        btnPresent.setFont(boldFont);
        btnAbsent.setFont(boldFont);

        btnPresent.setFocusPainted(false);
        btnAbsent.setFocusPainted(false);

        attendancePanel.add(btnPresent);
        attendancePanel.add(btnAbsent);

        centerPanel.add(attendancePanel);

        // ==============================
        // RESULT PANEL
        // ==============================

        JPanel resultPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                8
                        )
                );

        resultPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Attendance Information"
                )
        );

        JLabel lblTotal =
                new JLabel("Total Classes:");

        JLabel lblPresent =
                new JLabel("Present Classes:");

        JLabel lblAbsent =
                new JLabel("Absent Classes:");

        JLabel lblPercentage =
                new JLabel("Attendance:");

        JLabel lblStatus =
                new JLabel("Exam Status:");

        lblTotal.setFont(boldFont);
        lblPresent.setFont(boldFont);
        lblAbsent.setFont(boldFont);
        lblPercentage.setFont(boldFont);
        lblStatus.setFont(boldFont);

        lblTotalValue =
                new JLabel("0");

        lblPresentValue =
                new JLabel("0");

        lblAbsentValue =
                new JLabel("0");

        lblPercentageValue =
                new JLabel("0.00%");

        lblStatusValue =
                new JLabel("-");

        resultPanel.add(lblTotal);
        resultPanel.add(lblTotalValue);

        resultPanel.add(lblPresent);
        resultPanel.add(lblPresentValue);

        resultPanel.add(lblAbsent);
        resultPanel.add(lblAbsentValue);

        resultPanel.add(lblPercentage);
        resultPanel.add(lblPercentageValue);

        resultPanel.add(lblStatus);
        resultPanel.add(lblStatusValue);

        centerPanel.add(resultPanel);

        // ==============================
        // BOTTOM BUTTONS
        // ==============================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        JButton btnViewStudents =
                new JButton("View All Students");

//        JButton btnRefresh =
//                new JButton("Refresh");

        btnViewStudents.setFont(boldFont);
//        btnRefresh.setFont(boldFont);

        btnViewStudents.setFocusPainted(false);
//        btnRefresh.setFocusPainted(false);

        bottomPanel.add(btnViewStudents);
//        bottomPanel.add(btnRefresh);

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // ==============================
        // BUTTON ACTIONS
        // ==============================

        btnAddStudent.addActionListener(
                e -> addStudent()
        );

        btnUploadNames.addActionListener(
                e -> uploadStudentNames()
        );

        btnPresent.addActionListener(
                e -> markAttendance(true)
        );

        btnAbsent.addActionListener(
                e -> markAttendance(false)
        );

        btnViewStudents.addActionListener(
                e -> showStudentList()
        );

//        btnRefresh.addActionListener(
//                e -> loadStudents()
//        );

        studentCombo.addActionListener(
                e -> updateSelectedStudent()
        );

        // Initially disabled
        btnPresent.setEnabled(false);
        btnAbsent.setEnabled(false);

        loadStudents();
    }

    // ==============================
    // ADD STUDENT
    // ==============================

    private void addStudent() {

        if (service == null) {
            showServerError();
            return;
        }

        String name =
                txtName.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter student name.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            // Check duplicate first
            if (service.studentExists(name)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student already exists.",
                        "Duplicate Student",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Student student =
                    service.addStudent(name);

            txtName.setText("");

            loadStudents();

            studentCombo.setSelectedItem(
                    student.getName()
            );

            updateStudentDisplay(student);

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // UPLOAD STUDENT NAMES
    // ==============================

    private void uploadStudentNames() {

        if (service == null) {
            showServerError();
            return;
        }

        JFileChooser fileChooser =
                new JFileChooser();

        fileChooser.setDialogTitle(
                "Select Student Name File"
        );

        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Text Files (*.txt)",
                        "txt"
                )
        );

        int result =
                fileChooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file =
                fileChooser.getSelectedFile();

        int addedCount = 0;
        int duplicateCount = 0;
        int emptyCount = 0;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String name =
                        line.trim();

                // Ignore empty lines
                if (name.isEmpty()) {

                    emptyCount++;
                    continue;
                }

                // Check duplicate
                if (service.studentExists(name)) {

                    duplicateCount++;

                    System.out.println(
                            "[CLIENT] Skipped duplicate: "
                                    + name
                    );

                } else {

                    service.addStudent(name);

                    addedCount++;

                    System.out.println(
                            "[CLIENT] Student uploaded: "
                                    + name
                    );
                }
            }

            reader.close();

            // Refresh student list
            loadStudents();

            JOptionPane.showMessageDialog(
                    this,
                    "Student names uploaded successfully.\n\n" +
                            "Added: " + addedCount + "\n" +
                            "Already existed: " + duplicateCount + "\n" +
                            "Empty lines: " + emptyCount,
                    "Upload Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (java.io.IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot read the selected file.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // MARK ATTENDANCE
    // ==============================

    private void markAttendance(
            boolean present
    ) {

        if (service == null) {
            showServerError();
            return;
        }

        String name =
                (String) studentCombo.getSelectedItem();

        if (name == null ||
                name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first.",
                    "No Student Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            service.markAttendance(
                    name,
                    present
            );

            Student student =
                    service.getStudent(name);

            updateStudentDisplay(student);

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // LOAD STUDENTS
    // ==============================

    private void loadStudents() {

        if (service == null) {
            return;
        }

        try {

            List<Student> students =
                    service.getAllStudents();

            String current =
                    (String)
                            studentCombo.getSelectedItem();

            studentCombo.removeAllItems();

            for (Student student : students) {

                studentCombo.addItem(
                        student.getName()
                );
            }

            if (current != null) {

                studentCombo.setSelectedItem(
                        current
                );
            }

            updateSelectedStudent();

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // SELECT STUDENT
    // ==============================

    private void updateSelectedStudent() {

        if (service == null) {
            return;
        }

        String name =
                (String)
                        studentCombo.getSelectedItem();

        if (name == null) {

            btnPresent.setEnabled(false);
            btnAbsent.setEnabled(false);

            clearStudentDisplay();

            return;
        }

        try {

            Student student =
                    service.getStudent(name);

            updateStudentDisplay(student);

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // UPDATE DISPLAY
    // ==============================

    private void updateStudentDisplay(
            Student student
    ) {

        if (student == null) {
            return;
        }

        lblTotalValue.setText(
                String.valueOf(
                        student.getTotalClasses()
                )
        );

        lblPresentValue.setText(
                String.valueOf(
                        student.getPresentClasses()
                )
        );

        lblAbsentValue.setText(
                String.valueOf(
                        student.getAbsentClasses()
                )
        );

        lblPercentageValue.setText(
                String.format(
                        "%.2f%%",
                        student.getPercentage()
                )
        );

        lblStatusValue.setText(
                student.getStatus()
        );

        btnPresent.setEnabled(true);
        btnAbsent.setEnabled(true);
    }

    // ==============================
    // CLEAR DISPLAY
    // ==============================

    private void clearStudentDisplay() {

        lblTotalValue.setText("0");
        lblPresentValue.setText("0");
        lblAbsentValue.setText("0");
        lblPercentageValue.setText("0.00%");
        lblStatusValue.setText("-");
    }

    // ==============================
    // VIEW ALL STUDENTS
    // ==============================

    private void showStudentList() {

        if (service == null) {
            showServerError();
            return;
        }

        try {

            List<Student> students =
                    service.getAllStudents();

            JFrame studentWindow =
                    new JFrame("All Students");

            studentWindow.setSize(850, 450);
            studentWindow.setLocationRelativeTo(this);
            studentWindow.setDefaultCloseOperation(
                    JFrame.DISPOSE_ON_CLOSE
            );

            String[] columns = {
                    "Student Name",
                    "Total",
                    "Present",
                    "Absent",
                    "Attendance",
                    "Status"
            };

            Object[][] data =
                    new Object[students.size()][6];

            for (int i = 0;
                 i < students.size();
                 i++) {

                Student student =
                        students.get(i);

                data[i][0] =
                        student.getName();

                data[i][1] =
                        student.getTotalClasses();

                data[i][2] =
                        student.getPresentClasses();

                data[i][3] =
                        student.getAbsentClasses();

                data[i][4] =
                        String.format(
                                "%.2f%%",
                                student.getPercentage()
                        );

                data[i][5] =
                        student.getStatus();
            }

            JTable table =
                    new JTable(data, columns);

            table.setRowHeight(28);

            table.setAutoResizeMode(
                    JTable.AUTO_RESIZE_ALL_COLUMNS
            );

            table.setFillsViewportHeight(true);

            JScrollPane scrollPane =
                    new JScrollPane(table);

            // Close button
            JButton btnClose =
                    new JButton("Close");

            btnClose.setPreferredSize(
                    new Dimension(100, 32)
            );

            btnClose.setFocusPainted(false);

            btnClose.addActionListener(
                    e -> studentWindow.dispose()
            );

            JPanel bottomPanel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER
                            )
                    );

            bottomPanel.add(btnClose);

            studentWindow.add(
                    scrollPane,
                    BorderLayout.CENTER
            );

            studentWindow.add(
                    bottomPanel,
                    BorderLayout.SOUTH
            );

            studentWindow.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
            showServerError();
        }
    }

    // ==============================
    // ERROR MESSAGE
    // ==============================

    private void showServerError() {

        JOptionPane.showMessageDialog(
                this,
                "RMI Server doesn't work.\n" +
                        "Please check that the server is running.",
                "Server Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ==============================
    // MAIN
    // ==============================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                AttendanceGUI::new
        );
    }
}