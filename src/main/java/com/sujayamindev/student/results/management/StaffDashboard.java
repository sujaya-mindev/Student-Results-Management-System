package com.sujayamindev.student.results.management;


import com.formdev.flatlaf.*;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StaffDashboard extends javax.swing.JFrame {

    protected static String id;
    protected String courseId;
    protected String batch;
    protected String module;
    protected int examMarks;
    protected String examGrade;
    protected int cwMarks;
    protected String cwGrade;
    protected String finalGP;
    protected String finalGrade;
    protected boolean err;
    


    public StaffDashboard(String id) {
        this.courseId = "null";
        this.batch = "null";
        this.module = "null";
        this.examMarks = 0;
        this.examGrade = "null";
        this.cwMarks = 0;
        this.cwGrade = "null";
        this.finalGP = "null";
        this.finalGrade = "null";
        this.err = false;
        
        
        initComponents();
        
        //get user's first name
        String first_name = Database.getStaffFirstName(id);
        welcomeLabel.setText("Welcome " + first_name + "!");

        setTitle("Staff Dashboard | Student Results Management System");
        setIcon();
        setIconImage(new ImageIcon(getClass().getResource("/icons/app-icon.png")).getImage());
        // Set default close operation to do nothing on close
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 660);
        this.setSize(1220, 660);
        
        // Add a window listener to handle the close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Show a confirmation dialog when the window is closing
                int choice = JOptionPane.showConfirmDialog(StaffDashboard.this,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                // If the user chooses YES, dispose of the frame and exit
                if (choice == JOptionPane.YES_OPTION) {
                    dispose(); // Close the window
                }
            }
        });
        
        idComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                examMarksTextField.setEnabled(true);
                cwMarksTextField.setEnabled(true);
            }
        });

        
        // Get courseIds from database
        Database db = new Database();
        List<String> courseIds = db.getCourseIds();        
        // Add items to courseComboBox
        for (String courseId : courseIds) {
            courseComboBox.addItem(courseId);
            courseComboBox1.addItem(courseId);
            courseComboBox3.addItem(courseId);
            courseComboBox2.addItem(courseId);
        }
        

        setVisible(true);
    }
    
    private void setIcon() {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                switchIcon(selectedIndex);
            }
        });
    }
    
    private void switchIcon(int index) {
        // Set the icon based on the selected tab index
        switch (index) {
            case 0:
                ImageIcon icon0 = new ImageIcon(getClass().getResource("/icons/enter-window.png"));
                jPanel15.setComponentZOrder(iconLabel, 0);
                iconLabel.setIcon(icon0);
                break;
            case 1:
                ImageIcon icon1 = new ImageIcon(getClass().getResource("/icons/modify-window.png"));
                iconLabel.setIcon(icon1);
                jPanel15.setComponentZOrder(iconLabel, 0);
                break;
            case 2:
                ImageIcon icon2 = new ImageIcon(getClass().getResource("/icons/view-window.png"));
                iconLabel.setIcon(icon2);
                jPanel15.setComponentZOrder(iconLabel, 0);
                break;
            default:
                ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icons/report-window.png"));
                iconLabel.setIcon(defaultIcon);
                jPanel15.setComponentZOrder(iconLabel, 0);
                break;
        }
    }
    

    
    // Grading system
    public static String getGrade(int marks) {
        if (marks >= 85 && marks <= 100) return "A+";
        if (marks >= 70 && marks <= 84) return "A";
        if (marks >= 65 && marks <= 69) return "A-";
        if (marks >= 60 && marks <= 64) return "B+";
        if (marks >= 55 && marks <= 59) return "B";
        if (marks >= 50 && marks <= 54) return "B-";
        if (marks >= 45 && marks <= 49) return "C+";
        if (marks >= 40 && marks <= 44) return "C";
        if (marks >= 35 && marks <= 39) return "C-";
        if (marks >= 30 && marks <= 34) return "D+";
        if (marks >= 25 && marks <= 29) return "D";
        if (marks >= 0 && marks <= 24) return "E";
        return "null";
    }
    
     // GPA
    public static String getGPA(int final_marks) {
        if (final_marks >= 85 && final_marks <= 100) return "4.0";
        if (final_marks >= 70 && final_marks <= 84) return "4.0";
        if (final_marks >= 65 && final_marks <= 69) return "3.7";
        if (final_marks >= 60 && final_marks <= 64) return "3.3";
        if (final_marks >= 55 && final_marks <= 59) return "3.0";
        if (final_marks >= 50 && final_marks <= 54) return "2.7";
        if (final_marks >= 45 && final_marks <= 49) return "2.3";
        if (final_marks >= 40 && final_marks <= 44) return "2.0";
        if (final_marks >= 35 && final_marks <= 39) return "1.7";
        if (final_marks >= 30 && final_marks <= 34) return "1.3";
        if (final_marks >= 25 && final_marks <= 29) return "1.0";
        if (final_marks >= 0 && final_marks <= 24) return "0.0";
        return "null";
    }
    
    public void examGradeUpdate() {
        // Add DocumentListener to examMarksTextField
       examMarksTextField.getDocument().addDocumentListener(new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) {
               updateGradeExam();
               updateGradeFinal();
               updateGPA();
           }
           @Override
           public void removeUpdate(DocumentEvent e) {
               updateGradeExam();
               updateGradeFinal();
               updateGPA();
           }
           @Override
           public void changedUpdate(DocumentEvent e) {
               updateGradeExam();
               updateGradeFinal();
               updateGPA();
           }
       });
    }
    
    public void examGradeUpdate2() {
        // Add DocumentListener to examMarksTextField
       examMarksTextField1.getDocument().addDocumentListener(new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) {
               updateGradeExam2();
               updateGradeFinal2();
               updateGPA2();
           }
           @Override
           public void removeUpdate(DocumentEvent e) {
               updateGradeExam2();
               updateGradeFinal2();
               updateGPA2();
           }
           @Override
           public void changedUpdate(DocumentEvent e) {
               updateGradeExam2();
               updateGradeFinal2();
               updateGPA2();
           }
       });
    }
    
    
    public void cwGradeUpdate() {
        // Add DocumentListener to examMarksTextField
       cwMarksTextField.getDocument().addDocumentListener(new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) {
               updateGradeCw();
               updateGradeFinal();
               updateGPA();
           }
           @Override
           public void removeUpdate(DocumentEvent e) {
               updateGradeCw();
               updateGradeFinal();
               updateGPA();
           }
           @Override
           public void changedUpdate(DocumentEvent e) {
               updateGradeCw();
               updateGradeFinal();
               updateGPA();
           }
       });
    }
    
    public void cwGradeUpdate2() {
        // Add DocumentListener to examMarksTextField
       cwMarksTextField1.getDocument().addDocumentListener(new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) {
               updateGradeCw2();
               updateGradeFinal2();
               updateGPA2();
           }
           @Override
           public void removeUpdate(DocumentEvent e) {
               updateGradeCw2();
               updateGradeFinal2();
               updateGPA2();
           }
           @Override
           public void changedUpdate(DocumentEvent e) {
               updateGradeCw2();
               updateGradeFinal2();
               updateGPA2();
           }
       });
    }

    // Method to update the grade based on entered marks for exam
    public void updateGradeExam() {
        String examMarksText = examMarksTextField.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            // Check if the number is within the range 0-100
            if (examMarks >= 0 && examMarks <= 100) {
                examGrade = getGrade(examMarks);
                examGradeTextField.setText(examGrade);
            } else {
                examGradeTextField.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            examGradeTextField.setText(null); // Clear grade field if input is invalid
        }
    }
    
    
    public void updateGradeExam2() {
        String examMarksText = examMarksTextField1.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            // Check if the number is within the range 0-100
            if (examMarks >= 0 && examMarks <= 100) {
                examGrade = getGrade(examMarks);
                examGradeTextField1.setText(examGrade);
            } else {
                examGradeTextField1.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            examGradeTextField1.setText(null); // Clear grade field if input is invalid
        }
    }
    
    
     // Method to update the grade based on entered marks for cw
    public void updateGradeCw() {
        String cwMarksText = cwMarksTextField.getText();
        try {
            cwMarks = Integer.parseInt(cwMarksText);
            // Check if the number is within the range 0-100
            if (cwMarks >= 0 && cwMarks <= 100) {
                cwGrade = getGrade(cwMarks);
                cwGradeTextField.setText(cwGrade);
            } else {
                cwGradeTextField.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            cwGradeTextField.setText(null); // Clear grade field if input is invalid
        }
    }
    public void updateGradeCw2() {
        String cwMarksText = cwMarksTextField1.getText();
        try {
            cwMarks = Integer.parseInt(cwMarksText);
            // Check if the number is within the range 0-100
            if (cwMarks >= 0 && cwMarks <= 100) {
                cwGrade = getGrade(cwMarks);
                cwGradeTextField1.setText(cwGrade);
            } else {
                cwGradeTextField1.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            cwGradeTextField1.setText(null); // Clear grade field if input is invalid
        }
    }
    
    
    // Method to update the final grade
    public void updateGradeFinal() {
        String examMarksText = examMarksTextField.getText();
        String cwMarksText = cwMarksTextField.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            cwMarks = Integer.parseInt(cwMarksText);
            int totalMarks = examMarks + cwMarks;
            float finalMarksFloat = totalMarks / 2;
            int finalMarks = Math.round(finalMarksFloat);
            
            // Check if the number is within the range 0-100
            if (finalMarks >= 0 && finalMarks <= 100) {
                finalGrade = getGrade(finalMarks);
                finalGradeTextField.setText(finalGrade);
            } else {
                finalGradeTextField.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            finalGradeTextField.setText(null); // Clear grade field if input is invalid
        }
    }
    public void updateGradeFinal2() {
        String examMarksText = examMarksTextField1.getText();
        String cwMarksText = cwMarksTextField1.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            cwMarks = Integer.parseInt(cwMarksText);
            int totalMarks = examMarks + cwMarks;
            float finalMarksFloat = totalMarks / 2;
            int finalMarks = Math.round(finalMarksFloat);
            
            // Check if the number is within the range 0-100
            if (finalMarks >= 0 && finalMarks <= 100) {
                finalGrade = getGrade(finalMarks);
                finalGradeTextField1.setText(finalGrade);
            } else {
                finalGradeTextField1.setText(null); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            finalGradeTextField1.setText(null); // Clear grade field if input is invalid
        }
    }
    
    // Method to update the final grade
    public void updateGPA() {
        String examMarksText = examMarksTextField.getText();
        String cwMarksText = cwMarksTextField.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            cwMarks = Integer.parseInt(cwMarksText);
            int totalMarks = examMarks + cwMarks;
            float finalMarksFloat = totalMarks / 2;
            int finalMarks = Math.round(finalMarksFloat);
            
            // Check if the number is within the range 0-100
            if (finalMarks >= 0 && finalMarks <= 100) {
                finalGP = getGPA(finalMarks);
                gpTextField.setText(finalGP);
            } else {
                gpTextField.setText(""); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            gpTextField.setText(""); // Clear grade field if input is invalid
        }
    }
    public void updateGPA2() {
        String examMarksText = examMarksTextField1.getText();
        String cwMarksText = cwMarksTextField1.getText();
        
        try {
            examMarks = Integer.parseInt(examMarksText);
            cwMarks = Integer.parseInt(cwMarksText);
            int totalMarks = examMarks + cwMarks;
            float finalMarksFloat = totalMarks / 2;
            int finalMarks = Math.round(finalMarksFloat);
            
            // Check if the number is within the range 0-100
            if (finalMarks >= 0 && finalMarks <= 100) {
                finalGP = getGPA(finalMarks);
                gpTextField1.setText(finalGP);
            } else {
                gpTextField1.setText(""); // Clear grade field if out of range
            }
        } catch (NumberFormatException e) {
            gpTextField1.setText(""); // Clear grade field if input is invalid
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        moduleLabel22 = new javax.swing.JLabel();
        IdLabel3 = new javax.swing.JLabel();
        idComboBox = new javax.swing.JComboBox<>();
        courseComboBox = new javax.swing.JComboBox<>();
        moduleComboBox = new javax.swing.JComboBox<>();
        courseIdLabel3 = new javax.swing.JLabel();
        batchLabel3 = new javax.swing.JLabel();
        batchComboBox = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        wholeBatchCheckBox = new javax.swing.JCheckBox();
        jSeparator19 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        gpTextField = new javax.swing.JTextField();
        moduleLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        moduleLabel19 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        finalGradeTextField = new javax.swing.JTextField();
        examMarksTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        examGradeTextField = new javax.swing.JTextField();
        cwGradeTextField = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        cwMarksTextField = new javax.swing.JTextField();
        examMarksLabel3 = new javax.swing.JLabel();
        moduleLabel20 = new javax.swing.JLabel();
        moduleLabel21 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        moduleLabel23 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        viewIDTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        statusTextField = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        moduleLabel30 = new javax.swing.JLabel();
        IdLabel4 = new javax.swing.JLabel();
        idComboBox1 = new javax.swing.JComboBox<>();
        courseComboBox1 = new javax.swing.JComboBox<>();
        moduleComboBox1 = new javax.swing.JComboBox<>();
        courseIdLabel4 = new javax.swing.JLabel();
        batchLabel4 = new javax.swing.JLabel();
        batchComboBox1 = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        jSeparator21 = new javax.swing.JSeparator();
        jPanel27 = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        statusTextField1 = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        gpTextField1 = new javax.swing.JTextField();
        moduleLabel32 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        moduleLabel33 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        finalGradeTextField1 = new javax.swing.JTextField();
        examMarksTextField1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        examGradeTextField1 = new javax.swing.JTextField();
        cwGradeTextField1 = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        cwMarksTextField1 = new javax.swing.JTextField();
        examMarksLabel5 = new javax.swing.JLabel();
        moduleLabel34 = new javax.swing.JLabel();
        moduleLabel35 = new javax.swing.JLabel();
        jSeparator23 = new javax.swing.JSeparator();
        moduleLabel36 = new javax.swing.JLabel();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel30 = new javax.swing.JLabel();
        jSeparator33 = new javax.swing.JSeparator();
        viewIDTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        viewButton = new javax.swing.JButton();
        clearButton1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        IdLabel6 = new javax.swing.JLabel();
        idComboBox3 = new javax.swing.JComboBox<>();
        courseComboBox3 = new javax.swing.JComboBox<>();
        courseIdLabel6 = new javax.swing.JLabel();
        batchLabel6 = new javax.swing.JLabel();
        batchComboBox3 = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        jSeparator29 = new javax.swing.JSeparator();
        statusTextField3 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        moduleLabel31 = new javax.swing.JLabel();
        IdLabel5 = new javax.swing.JLabel();
        idComboBox2 = new javax.swing.JComboBox<>();
        courseComboBox2 = new javax.swing.JComboBox<>();
        moduleComboBox2 = new javax.swing.JComboBox<>();
        courseIdLabel5 = new javax.swing.JLabel();
        batchLabel5 = new javax.swing.JLabel();
        batchComboBox2 = new javax.swing.JComboBox<>();
        jPanel30 = new javax.swing.JPanel();
        jSeparator25 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel31 = new javax.swing.JPanel();
        generateButton = new javax.swing.JButton();
        statusTextField2 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        dashboardLabel = new javax.swing.JLabel();
        themeToggleButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Dashboard | Student Results Management System");
        setMinimumSize(new java.awt.Dimension(1200, 621));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 621));
        getContentPane().setLayout(null);

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/enter-window.png"))); // NOI18N
        iconLabel.setAlignmentX(0.5F);
        iconLabel.setPreferredSize(new java.awt.Dimension(55, 55));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel15);
        jPanel15.setBounds(1010, 40, 80, 80);

        tabbedPane.setToolTipText("");
        tabbedPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabbedPane.setName(""); // NOI18N

        jPanel1.setToolTipText(null);
        jPanel1.setEnabled(false);
        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(592, 750));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setToolTipText(null);
        jPanel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Select Student Details");

        moduleLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel22.setText("Module");
        moduleLabel22.setToolTipText(null);

        IdLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdLabel3.setText("ID");
        IdLabel3.setToolTipText(null);
        IdLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        idComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        idComboBox.setToolTipText("Student ID");
        idComboBox.setEnabled(false);
        idComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idComboBoxActionPerformed(evt);
            }
        });

        courseComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        courseComboBox.setToolTipText("Course ID");
        courseComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBoxActionPerformed(evt);
            }
        });

        moduleComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        moduleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        moduleComboBox.setToolTipText("Module");
        moduleComboBox.setEnabled(false);
        moduleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleComboBoxActionPerformed(evt);
            }
        });

        courseIdLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        courseIdLabel3.setText("Course ID");
        courseIdLabel3.setToolTipText(null);
        courseIdLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseIdLabel3MouseClicked(evt);
            }
        });

        batchLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        batchLabel3.setText("Batch");
        batchLabel3.setToolTipText(null);

        batchComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        batchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        batchComboBox.setToolTipText("Batch");
        batchComboBox.setEnabled(false);
        batchComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        wholeBatchCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        wholeBatchCheckBox.setText("All Batch");
        wholeBatchCheckBox.setToolTipText("Enter results for the whole batch");
        wholeBatchCheckBox.setEnabled(false);
        wholeBatchCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wholeBatchCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(courseIdLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moduleLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IdLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moduleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(idComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(wholeBatchCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(batchLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(batchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseIdLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdLabel3)
                    .addComponent(wholeBatchCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator19))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        gpTextField.setEditable(false);
        gpTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        gpTextField.setFocusable(false);

        moduleLabel18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel18.setText("Grade");
        moduleLabel18.setToolTipText(null);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText(" Exam");
        jLabel17.setToolTipText(null);

        moduleLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel19.setText("Marks");
        moduleLabel19.setToolTipText(null);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText(" Course Work");

        finalGradeTextField.setEditable(false);
        finalGradeTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        finalGradeTextField.setToolTipText(null);
        finalGradeTextField.setFocusable(false);

        examMarksTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examMarksTextField.setToolTipText("Exam Marks");
        examMarksTextField.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText(" Final Result");
        jLabel14.setToolTipText(null);

        examGradeTextField.setEditable(false);
        examGradeTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examGradeTextField.setToolTipText(null);
        examGradeTextField.setFocusable(false);

        cwGradeTextField.setEditable(false);
        cwGradeTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cwGradeTextField.setToolTipText(null);
        cwGradeTextField.setFocusable(false);

        cwMarksTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cwMarksTextField.setToolTipText("Course Work Marks");
        cwMarksTextField.setEnabled(false);

        examMarksLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examMarksLabel3.setText("Marks");
        examMarksLabel3.setToolTipText(null);

        moduleLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel20.setText("Grade");
        moduleLabel20.setToolTipText(null);

        moduleLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel21.setText("GP");
        moduleLabel21.setToolTipText(null);

        moduleLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel23.setText("Grade");
        moduleLabel23.setToolTipText(null);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("Enter Results");

        viewIDTextField.setEditable(false);
        viewIDTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewIDTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viewIDTextField.setText("STUDENT ID");
        viewIDTextField.setBorder(null);
        viewIDTextField.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(moduleLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(cwMarksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(cwGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(examMarksLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(examMarksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(examGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(5, 5, 5)
                                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator24, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                                .addComponent(viewIDTextField))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(moduleLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(finalGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel21)
                        .addGap(39, 39, 39)
                        .addComponent(gpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(viewIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(examMarksLabel3)
                    .addComponent(examMarksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel23)
                    .addComponent(examGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleLabel18)
                    .addComponent(cwGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel19)
                    .addComponent(cwMarksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleLabel21)
                    .addComponent(gpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel20)
                    .addComponent(finalGradeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        saveButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        saveButton.setText(" SAVE");
        saveButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear.png"))); // NOI18N
        clearButton.setText("CLEAR");
        clearButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        nextButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        nextButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        backButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/previous.png"))); // NOI18N
        backButton.setToolTipText("");
        backButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statusTextField.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusTextField))
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );

        tabbedPane.addTab("Enter Results", jPanel1);

        jPanel13.setToolTipText(null);
        jPanel13.setEnabled(false);
        jPanel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel13.setPreferredSize(new java.awt.Dimension(592, 750));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel11.setToolTipText(null);
        jPanel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Select Student Details");

        moduleLabel30.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel30.setText("Module");
        moduleLabel30.setToolTipText(null);

        IdLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdLabel4.setText("ID");
        IdLabel4.setToolTipText(null);
        IdLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        idComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        idComboBox1.setToolTipText("Student ID");
        idComboBox1.setEnabled(false);
        idComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idComboBox1ActionPerformed(evt);
            }
        });

        courseComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        courseComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        courseComboBox1.setToolTipText("Course ID");
        courseComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBox1ActionPerformed(evt);
            }
        });

        moduleComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        moduleComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        moduleComboBox1.setToolTipText("Module");
        moduleComboBox1.setEnabled(false);
        moduleComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleComboBox1ActionPerformed(evt);
            }
        });

        courseIdLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        courseIdLabel4.setText("Course ID");
        courseIdLabel4.setToolTipText(null);

        batchLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        batchLabel4.setText("Batch");
        batchLabel4.setToolTipText(null);

        batchComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        batchComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        batchComboBox1.setToolTipText("Batch");
        batchComboBox1.setEnabled(false);
        batchComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                .addComponent(courseIdLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(courseComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moduleLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IdLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moduleComboBox1, 0, 401, Short.MAX_VALUE)
                                    .addComponent(idComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(batchLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(batchComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseIdLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batchComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator21))
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        updateButton.setText(" UPDATE");
        updateButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        updateButton.setPreferredSize(new java.awt.Dimension(130, 30));
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        deleteButton.setText(" DELETE");
        deleteButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        deleteButton.setPreferredSize(new java.awt.Dimension(130, 33));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statusTextField1.setEditable(false);
        statusTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusTextField1ActionPerformed(evt);
            }
        });

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        gpTextField1.setEditable(false);
        gpTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        gpTextField1.setFocusable(false);

        moduleLabel32.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel32.setText("Grade");
        moduleLabel32.setToolTipText(null);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText(" Exam");
        jLabel27.setToolTipText(null);

        moduleLabel33.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel33.setText("Marks");
        moduleLabel33.setToolTipText(null);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText(" Course Work");

        finalGradeTextField1.setEditable(false);
        finalGradeTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        finalGradeTextField1.setToolTipText(null);
        finalGradeTextField1.setFocusable(false);

        examMarksTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examMarksTextField1.setToolTipText("Exam Marks");
        examMarksTextField1.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText(" Final Result");
        jLabel29.setToolTipText(null);

        examGradeTextField1.setEditable(false);
        examGradeTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examGradeTextField1.setToolTipText(null);
        examGradeTextField1.setFocusable(false);

        cwGradeTextField1.setEditable(false);
        cwGradeTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cwGradeTextField1.setToolTipText(null);
        cwGradeTextField1.setFocusable(false);

        cwMarksTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cwMarksTextField1.setToolTipText("Course Work Marks");
        cwMarksTextField1.setEnabled(false);

        examMarksLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        examMarksLabel5.setText("Marks");
        examMarksLabel5.setToolTipText(null);

        moduleLabel34.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel34.setText("Grade");
        moduleLabel34.setToolTipText(null);

        moduleLabel35.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel35.setText("GP");
        moduleLabel35.setToolTipText(null);

        moduleLabel36.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel36.setText("Grade");
        moduleLabel36.setToolTipText(null);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("Update Results");

        viewIDTextField1.setEditable(false);
        viewIDTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewIDTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viewIDTextField1.setText("STUDENT ID");
        viewIDTextField1.setBorder(null);
        viewIDTextField1.setEnabled(false);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(moduleLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(cwMarksTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(cwGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(examMarksLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(examMarksTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(examGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(5, 5, 5)
                                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel28Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator23))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel28Layout.createSequentialGroup()
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator33, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(viewIDTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(moduleLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(finalGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(moduleLabel35)
                        .addGap(39, 39, 39)
                        .addComponent(gpTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(viewIDTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(examMarksLabel5)
                    .addComponent(examMarksTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel36)
                    .addComponent(examGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(20, 20, 20)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleLabel32)
                    .addComponent(cwGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel33)
                    .addComponent(cwMarksTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(26, 26, 26)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleLabel35)
                    .addComponent(gpTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel34)
                    .addComponent(finalGradeTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusTextField1))
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        tabbedPane.addTab("Modify Results", jPanel13);

        resultTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module", "Exam", "CW", "Final Grade", "Grade Point"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        resultTable.setName(""); // NOI18N
        resultTable.setRowHeight(30);
        resultTable.setShowGrid(false);
        resultTable.setShowHorizontalLines(true);
        resultTable.setShowVerticalLines(true);
        resultTable.getTableHeader().setResizingAllowed(false);
        jScrollPane1.setViewportView(resultTable);

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        viewButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        viewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view (1).png"))); // NOI18N
        viewButton.setText(" VIEW");
        viewButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        clearButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clearButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear.png"))); // NOI18N
        clearButton1.setText("CLEAR");
        clearButton1.setMargin(new java.awt.Insets(3, 14, 3, 14));
        clearButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(viewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewButton)
                    .addComponent(clearButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel18.setToolTipText(null);
        jPanel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("Select Student Details");

        IdLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdLabel6.setText("ID");
        IdLabel6.setToolTipText(null);
        IdLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        idComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        idComboBox3.setToolTipText("Student ID");
        idComboBox3.setEnabled(false);
        idComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idComboBox3ActionPerformed(evt);
            }
        });

        courseComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        courseComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        courseComboBox3.setToolTipText("Course ID");
        courseComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBox3ActionPerformed(evt);
            }
        });

        courseIdLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        courseIdLabel6.setText("Course ID");
        courseIdLabel6.setToolTipText(null);

        batchLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        batchLabel6.setText("Batch");
        batchLabel6.setToolTipText(null);

        batchComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        batchComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        batchComboBox3.setToolTipText("Batch");
        batchComboBox3.setEnabled(false);
        batchComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addComponent(courseIdLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(courseComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(IdLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(idComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(batchLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(batchComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseIdLabel6))
                .addGap(35, 35, 35)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batchComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchLabel6))
                .addGap(35, 35, 35)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator29))
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        statusTextField3.setEditable(false);
        statusTextField3.setToolTipText(null);
        statusTextField3.setAutoscrolls(false);
        statusTextField3.setInheritsPopupMenu(true);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setText("Results Summary");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusTextField3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator30))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tabbedPane.addTab("View Results", jPanel5);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel14.setToolTipText(null);
        jPanel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Select Student Details");

        moduleLabel31.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        moduleLabel31.setText("Module");
        moduleLabel31.setToolTipText(null);
        moduleLabel31.setEnabled(false);

        IdLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdLabel5.setText("ID");
        IdLabel5.setToolTipText(null);
        IdLabel5.setEnabled(false);
        IdLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        idComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        idComboBox2.setToolTipText("Student ID");
        idComboBox2.setEnabled(false);
        idComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idComboBox2ActionPerformed(evt);
            }
        });

        courseComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        courseComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        courseComboBox2.setToolTipText("Course ID");
        courseComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboBox2ActionPerformed(evt);
            }
        });

        moduleComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        moduleComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        moduleComboBox2.setToolTipText("Module");
        moduleComboBox2.setEnabled(false);
        moduleComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleComboBox2ActionPerformed(evt);
            }
        });

        courseIdLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        courseIdLabel5.setText("Course ID");
        courseIdLabel5.setToolTipText(null);

        batchLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        batchLabel5.setText("Batch");
        batchLabel5.setToolTipText(null);

        batchComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        batchComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        batchComboBox2.setToolTipText("Batch");
        batchComboBox2.setEnabled(false);
        batchComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(courseIdLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(courseComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(batchLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(batchComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(IdLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(idComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(moduleLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(moduleComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseIdLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batchComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batchLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moduleLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Batch Summary");
        jRadioButton1.setActionCommand("jRadioButtonBatch");

        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jRadioButton2.setText("Course Summary");
        jRadioButton2.setActionCommand("jRadioButtonCourse");
        jRadioButton2.setEnabled(false);

        jRadioButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jRadioButton3.setText("Module Summary");
        jRadioButton3.setActionCommand("jRadioButtonModule");
        jRadioButton3.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addGap(28, 28, 28)
                .addComponent(jRadioButton3)
                .addGap(50, 50, 50))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator25))
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        generateButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        generateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view (4).png"))); // NOI18N
        generateButton.setText(" GENERATE");
        generateButton.setMargin(new java.awt.Insets(3, 14, 3, 14));
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(generateButton)
                .addGap(20, 20, 20))
        );

        statusTextField2.setEditable(false);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusTextField2))
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        tabbedPane.addTab("Generate Reports", jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(24, 98, 1167, 523);

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        welcomeLabel.setText("Welcome User123");

        dashboardLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dashboardLabel.setText("STAFF DASHBOARD");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardLabel)
                .addContainerGap())
        );

        getContentPane().add(jPanel22);
        jPanel22.setBounds(40, 22, 354, 70);

        themeToggleButton.setBackground(new java.awt.Color(242, 242, 242));
        themeToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/light-mode.png"))); // NOI18N
        themeToggleButton.setToolTipText("Change Theme");
        themeToggleButton.setBorder(null);
        themeToggleButton.setBorderPainted(false);
        themeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themeToggleButtonActionPerformed(evt);
            }
        });
        getContentPane().add(themeToggleButton);
        themeToggleButton.setBounds(1162, 22, 20, 20);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Clear all
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        courseComboBox.setSelectedIndex(0);
        batchComboBox.setSelectedIndex(0);
        moduleComboBox.setSelectedIndex(0);
        idComboBox.setSelectedIndex(0);
        examMarksTextField.setText("");
        examGradeTextField.setText("");
        cwMarksTextField.setText("");
        cwGradeTextField.setText("");
        gpTextField.setText("");
        finalGradeTextField.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    // Call the method to save marks to database
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        err = false;
        // Get courseId
        if (courseComboBox.getSelectedItem() != "---") {
            courseId = (String) courseComboBox.getSelectedItem();
        }
        else {
            err = true;
            //statusTextField.setText("Error: Select a course");
        }

        // Get batch
        if (batchComboBox.getSelectedItem() != "---") {
            batch = (String) batchComboBox.getSelectedItem();
        }
        else {
            err = true;
            //setText("Error: Select a batch");
        }

        // Get id
        if (idComboBox.getSelectedItem() != "---") {
            id = (String) idComboBox.getSelectedItem();
        }
        else {
            err = true;
            //setText("Error: Select a id");
        }

        // Get module
        if (moduleComboBox.getSelectedItem() != "---") {
            module = (String) moduleComboBox.getSelectedItem();
        }
        else {
            err = true;
            //statusTextField.setText("Error: Select a module");
        }

        // Get examMarks
        try {
            examMarks = Integer.parseInt(examMarksTextField.getText());
            if (examMarks >= 0 && examMarks <= 100) {
            } else {
                err = true;
                String currentText = statusTextField.getText();
                statusTextField.setText(currentText + "\nError: Please enter valid marks!");
            }
        } catch (NumberFormatException e) {
            err = true;
            String currentText = statusTextField.getText();
            statusTextField.setText(currentText + "\nError: Please enter valid marks!");
        }

        // Get examGrade
        if (examGradeTextField.getText() != null) {
            examGrade = (String) examGradeTextField.getText();
        }

        // Get cwMarks
        try {
            cwMarks = Integer.parseInt(cwMarksTextField.getText());
            if (cwMarks >= 0 && cwMarks <= 100) {
            } else {
                err = true;
                String currentText = statusTextField.getText();
                statusTextField.setText(currentText + "\nError: Please enter valid marks!");
            }
        } catch (NumberFormatException e) {
            err = true;
            String currentText = statusTextField.getText();
            statusTextField.setText(currentText + "\nError: Please enter valid marks!");
        }

        // Get cwGrade
        if (cwGradeTextField.getText() != null) {
            cwGrade = (String) cwGradeTextField.getText();
        }

        // Get finalGP
        if (gpTextField.getText() != null) {
            finalGP = (String) gpTextField.getText();
        }
        // Get finalGrade
        if (finalGradeTextField.getText() != null) {
            finalGrade = (String) finalGradeTextField.getText();
        }

        // Save results
        if (err == false) {
            try {
                Database db = new Database();
                
                try {
                    db.saveMarks(id, courseId, batch, module, examMarks, examGrade, cwMarks, cwGrade, finalGP, finalGrade);
                    String currentText = statusTextField.getText();
                    statusTextField.setText(currentText + "\nResults entered successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                    String currentText = statusTextField.getText();
                    statusTextField.setText(currentText + "\nError: Results Not Saved!");
                }
                
                
                if (wholeBatchCheckBox.isSelected()) {
                    int index = idComboBox.getSelectedIndex();
                    idComboBox.setSelectedIndex(index + 1);
                }
                else {
                    courseComboBox.setSelectedIndex(0);
                    examMarksTextField.setText("");
                    examGradeTextField.setText("");
                    cwMarksTextField.setText("");
                    cwGradeTextField.setText("");
                    gpTextField.setText("");
                    finalGradeTextField.setText("");
                }

            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            //statusTextField.setText("Error!");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void batchComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchComboBoxActionPerformed
        // Clear selected items
        statusTextField.setText("");
        examMarksTextField.setText("");
        cwMarksTextField.setText("");
        moduleComboBox.removeAllItems();
        moduleComboBox.addItem("---");
        idComboBox.removeAllItems();
        idComboBox.addItem("---");
        wholeBatchCheckBox.setEnabled(false);
        wholeBatchCheckBox.setSelected(false);
        
        if (batchComboBox.getSelectedIndex() > 0) {
            moduleComboBox.setEnabled(true);
        }
        else {
            moduleComboBox.setEnabled(false);
            idComboBox.setEnabled(false);
        }

        // Get selected courseID and batch
        courseId = (String) courseComboBox.getSelectedItem();
        batch = (String) batchComboBox.getSelectedItem();

        // Get selected item
        Database db = new Database();
        // Get modules from database
        List<String> modules = db.getModules(courseId, batch);
        // Add items to ComboBox
        for (String module : modules) {
            moduleComboBox.addItem(module);
        }
    }//GEN-LAST:event_batchComboBoxActionPerformed

    private void moduleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleComboBoxActionPerformed
        // Clear selected items
        statusTextField.setText("");
        examMarksTextField.setText("");
        cwMarksTextField.setText("");
        courseId = (String) courseComboBox.getSelectedItem();
        idComboBox.removeAllItems();
        idComboBox.addItem("---");
        wholeBatchCheckBox.setEnabled(true);
        
        if (moduleComboBox.getSelectedIndex() > 0) {
            idComboBox.setEnabled(true);
        }
        else {
            idComboBox.setEnabled(false);
        }
        
        // Get selected item
        batch = (String) batchComboBox.getSelectedItem();
        // Get ids from database
        Database db = new Database();
        List<String> ids = db.getIds(courseId, batch);
        // Add items to idComboBox
        for (String id : ids) {
            idComboBox.addItem(id);
        }
    }//GEN-LAST:event_moduleComboBoxActionPerformed

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboBoxActionPerformed
        // Clear selected items
        statusTextField.setText("");
        batchComboBox.removeAllItems();
        batchComboBox.addItem("---");
        examMarksTextField.setText("");
        cwMarksTextField.setText("");
        wholeBatchCheckBox.setEnabled(false);
        wholeBatchCheckBox.setSelected(false);
        
        if (courseComboBox.getSelectedIndex() > 0) {
            batchComboBox.setEnabled(true);
        }
        else {
            batchComboBox.setEnabled(false);
            moduleComboBox.setEnabled(true);
            idComboBox.setEnabled(false);
        }
        // Get selected item
        courseId = (String) courseComboBox.getSelectedItem();
        Database db = new Database();
        // Get batches from database
        List<String> batches = db.getBatches(courseId);
        // Add items to batchComboBox
        for (String batch : batches) {
            batchComboBox.addItem(batch);
        }
        // Update grades
        examGradeUpdate();
        cwGradeUpdate();
    }//GEN-LAST:event_courseComboBoxActionPerformed

    private void idComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idComboBoxActionPerformed
        
        if (wholeBatchCheckBox.isSelected()) {
            
        } else {
            statusTextField.setText("");
        }
        id = (String) idComboBox.getSelectedItem();
        courseId = (String) courseComboBox.getSelectedItem();
        batch = (String) batchComboBox.getSelectedItem();
        module = (String) moduleComboBox.getSelectedItem();

        examMarksTextField.setText("");
        cwMarksTextField.setText("");
        
        if (idComboBox.getSelectedIndex() > 0) {
            examMarksTextField.setEnabled(true);
            cwMarksTextField.setEnabled(true);
            //saveButton.setEnabled(true);
            //clearButton.setEnabled(true);
            //nextButton.setEnabled(true);
            //backButton.setEnabled(true);
            viewIDTextField.setText((String) idComboBox.getSelectedItem());
        }
        else {
            //saveButton.setEnabled(false);
            //clearButton.setEnabled(false);
            //nextButton.setEnabled(false);
            //backButton.setEnabled(false);
            cwMarksTextField.setEnabled(false);
            examMarksTextField.setEnabled(false);
        }
        

        // Check marks already entered or not
        try {
            Database db = new Database();
            List<String> marks = db.getMarks(id, courseId, batch, module);

            if (marks.isEmpty()) {
                examMarksTextField.setEnabled(true);
                cwMarksTextField.setEnabled(true);
                examMarksTextField.setEditable(true);
                cwMarksTextField.setEditable(true);
            } else {
                examMarksTextField.setText(marks.get(0));
                examGradeTextField.setText(marks.get(1));
                cwMarksTextField.setText(marks.get(2));
                cwGradeTextField.setText(marks.get(3));
                gpTextField.setText(marks.get(4));
                finalGradeTextField.setText(marks.get(5));
                //saveButton.setEnabled(false);
                examMarksTextField.setEditable(false);
                cwMarksTextField.setEditable(false);
                String currentText = statusTextField.getText();
                statusTextField.setText(currentText + "\nMarks already entered!");
            }
        }
        catch (Exception e) {
        }
    }//GEN-LAST:event_idComboBoxActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        // Get courseId
        if (courseComboBox1.getSelectedItem() != "---") {
            courseId = (String) courseComboBox1.getSelectedItem();
        } else {
            err = true;
            statusTextField1.setText("Error: Select a course");
        }

        // Get batch
        if (batchComboBox1.getSelectedItem() != "---") {
            batch = (String) batchComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select a batch");
        }

        // Get id
        if (idComboBox1.getSelectedItem() != "---") {
            id = (String) idComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select a id");
        }

        // Get module
        if (moduleComboBox1.getSelectedItem() != "---") {
            module = (String) moduleComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select a module");
        }

        // Get examMarks
        try {
            examMarks = Integer.parseInt(examMarksTextField1.getText());
        } catch (NumberFormatException e) {
            err = true;
            statusTextField1.setText("Error: Please enter valid marks!");
        }
        // Get examGrade
        if (examGradeTextField1.getText() != null) {
            examGrade = (String) examGradeTextField1.getText();
        }

        // Get cwMarks
        try {
            cwMarks = Integer.parseInt(cwMarksTextField1.getText());
        } catch (NumberFormatException e) {
            err = true;
            statusTextField1.setText("Error: Please enter valid marks!");
        }
        // Get cwGrade
        if (cwGradeTextField1.getText() != null) {
            cwGrade = (String) cwGradeTextField1.getText();
        }

        // Get finalGP
        if (gpTextField1.getText() != null) {
            finalGP = (String) gpTextField1.getText();
        }
        // Get finalGrade
        if (finalGradeTextField1.getText() != null) {
            finalGrade = (String) finalGradeTextField1.getText();
        }

        // Delete results
        if (err == false) {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to DELETE the results?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    Database db = new Database();
                    db.deleteMarks(id, courseId, batch, module);
                    courseComboBox1.setSelectedIndex(0);
                    examMarksTextField1.setText("");
                    examGradeTextField1.setText("");
                    cwMarksTextField1.setText("");
                    cwGradeTextField1.setText("");
                    gpTextField1.setText("");
                    finalGradeTextField1.setText("");
                    statusTextField1.setText("Results DELETED successfully!");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                statusTextField1.setText("Error");
            }

        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        err = false;
        // Get courseId
        if (courseComboBox1.getSelectedItem() != "---") {
            courseId = (String) courseComboBox1.getSelectedItem();
        } else {
            err = true;
            statusTextField1.setText("Error: Select a course");
        }

        // Get batch
        if (batchComboBox1.getSelectedItem() != "---") {
            batch = (String) batchComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select a batch");
        }

        // Get id
        if (idComboBox1.getSelectedItem() != "---") {
            id = (String) idComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select id");
        }

        // Get module
        if (moduleComboBox1.getSelectedItem() != "---") {
            module = (String) moduleComboBox1.getSelectedItem();
        }else {
            err = true;
            statusTextField1.setText("Error: Select module");
        }

        // Get examMarks
        try {
            examMarks = Integer.parseInt(examMarksTextField1.getText());
            if (examMarks >= 0 && examMarks <= 100) {
            } else {
                err = true;
                statusTextField1.setText("Error: Please enter valid marks!");
            }
        } catch (NumberFormatException e) {
            err = true;
            statusTextField1.setText("Error: Please enter valid marks!");
        }

        // Get examGrade
        if (examGradeTextField1.getText() != null) {
            examGrade = (String) examGradeTextField1.getText();
        }

        // Get cwMarks
        try {
            cwMarks = Integer.parseInt(cwMarksTextField1.getText());
            if (cwMarks >= 0 && cwMarks <= 100) {
            } else {
                err = true;
                statusTextField1.setText("Error: Please enter valid marks!");
            }
        } catch (NumberFormatException e) {
            err = true;
            statusTextField1.setText("Error: Please enter valid marks!");
        }
        // Get cwGrade
        if (cwGradeTextField1.getText() != null) {
            cwGrade = (String) cwGradeTextField1.getText();
        }

        // Get finalGP
        if (gpTextField1.getText() != null) {
            finalGP = (String) gpTextField1.getText();
        }
        // Get finalGrade
        if (finalGradeTextField1.getText() != null) {
            finalGrade = (String) finalGradeTextField1.getText();
        }

        // Update results
        if (err == false) {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to modify the results?",
                "Confirm Modification",
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    Database db = new Database();
                    db.updateMarks(id, courseId, batch, module, examMarks, examGrade, cwMarks, cwGrade, finalGP, finalGrade);
                    courseComboBox1.setSelectedIndex(0);
                    examMarksTextField1.setText("");
                    examGradeTextField1.setText("");
                    cwMarksTextField1.setText("");
                    cwGradeTextField1.setText("");
                    gpTextField1.setText("");
                    finalGradeTextField1.setText("");
                    statusTextField1.setText("Results updated successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }else if (choice == JOptionPane.NO_OPTION) {

            }
        } else {
            //statusTextField2.setText("Error");
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void examMarksTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examMarksTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_examMarksTextField1ActionPerformed

    private void batchComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchComboBox1ActionPerformed
        statusTextField1.setText("");
        examMarksTextField1.setText("");
        cwMarksTextField1.setText("");
        courseId = (String) courseComboBox1.getSelectedItem(); // Get selected item
        batch = (String) batchComboBox1.getSelectedItem(); // Get selected item
        moduleComboBox1.removeAllItems();
        moduleComboBox1.addItem("---");
        idComboBox1.removeAllItems();
        idComboBox1.addItem("---");

        Database db = new Database();
        // Get modules from database
        List<String> modules = db.getModules(courseId, batch);
        // Add items to ComboBox
        for (String module : modules) {
            moduleComboBox1.addItem(module);
        }
        
        if (batchComboBox1.getSelectedIndex() > 0 ) {
            moduleComboBox1.setEnabled(true);
        }else {
            moduleComboBox1.setEnabled(false);
            idComboBox1.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_batchComboBox1ActionPerformed

    private void moduleComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleComboBox1ActionPerformed
        statusTextField1.setText("");
        examMarksTextField1.setText("");
        cwMarksTextField1.setText("");
        courseId = (String) courseComboBox1.getSelectedItem();
        idComboBox1.removeAllItems();
        idComboBox1.addItem("---");
        // Get selected item
        batch = (String) batchComboBox1.getSelectedItem();
        Database db = new Database();
        // Get ids from database
        List<String> ids = db.getIds(courseId, batch);
        // Add items to idComboBox
        for (String id : ids) {
            idComboBox1.addItem(id);
        }
        
        
        if (moduleComboBox1.getSelectedIndex() > 0 ) {
            idComboBox1.setEnabled(true);
        }else {
            idComboBox1.setEnabled(false);
        }
    }//GEN-LAST:event_moduleComboBox1ActionPerformed

    private void courseComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboBox1ActionPerformed
        statusTextField1.setText("");
        batchComboBox1.removeAllItems();
        batchComboBox1.addItem("---");
        examMarksTextField1.setText("");
        cwMarksTextField1.setText("");
        // Get selected item
        courseId = (String) courseComboBox1.getSelectedItem();
        Database db = new Database();
        // Get batches from database
        List<String> batches = db.getBatches(courseId);
        // Add items to batchComboBox
        for (String batch : batches) {
            batchComboBox1.addItem(batch);
        }
        // Update grades
        examGradeUpdate2();
        cwGradeUpdate2();
        
        if (courseComboBox1.getSelectedIndex() > 0 ) {
            batchComboBox1.setEnabled(true);
            examMarksTextField1.setEnabled(true);
            cwMarksTextField1.setEnabled(true);
        }else {
            batchComboBox1.setEnabled(false);
            moduleComboBox1.setEnabled(false);
            idComboBox1.setEnabled(false);
            examMarksTextField1.setEnabled(false);
            cwMarksTextField1.setEnabled(false);
        }
    }//GEN-LAST:event_courseComboBox1ActionPerformed

    private void idComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idComboBox1ActionPerformed
        examMarksTextField1.setText("");
        cwMarksTextField1.setText("");
        statusTextField1.setText("");
        id = (String) idComboBox1.getSelectedItem();
        courseId = (String) courseComboBox1.getSelectedItem();
        batch = (String) batchComboBox1.getSelectedItem();
        module = (String) moduleComboBox1.getSelectedItem();

        if (!"---".equals(courseId)) {
            if (!"---".equals(batch)) {
                if (!"---".equals(module)) {
                    if (!"---".equals(id)) {
                        // Check marks already entered or not
                        try {
                            Database db = new Database();
                            List<String> marks = db.getMarks(id, courseId, batch, module);

                            if (marks.isEmpty()) {
                                examMarksTextField1.setEditable(false);
                                cwMarksTextField1.setEditable(false);
                                statusTextField1.setText("Results not available!");
                            } else {
                                examMarksTextField1.setEditable(true);
                                cwMarksTextField1.setEditable(true);
                            }

                            examMarksTextField1.setText(marks.get(0));
                            examGradeTextField1.setText(marks.get(1));
                            cwMarksTextField1.setText(marks.get(2));
                            cwGradeTextField1.setText(marks.get(3));
                            gpTextField1.setText(marks.get(4));
                            finalGradeTextField1.setText(marks.get(5));
                        }
                        catch (Exception e) {
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_idComboBox1ActionPerformed

    private void idComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idComboBox3ActionPerformed
        id = (String) idComboBox3.getSelectedItem(); // Get id
        courseId = (String) courseComboBox3.getSelectedItem(); //Get courseId
        batch = (String) batchComboBox3.getSelectedItem();  // Get batch
        statusTextField3.setText(""); 
        
    }//GEN-LAST:event_idComboBox3ActionPerformed

    private void courseComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboBox3ActionPerformed
        batchComboBox3.removeAllItems();
        batchComboBox3.addItem("---");
        idComboBox3.removeAllItems();
        idComboBox3.addItem("---");
        
        // Get selected item
        courseId = (String) courseComboBox3.getSelectedItem();
        Database db = new Database();
        // Get batches from database
        List<String> batches = db.getBatches(courseId);
        
        // Add items to batchComboBox
        for (String batch : batches) {
            batchComboBox3.addItem(batch);
        }
        
        if (courseComboBox3.getSelectedIndex() > 0) {
            batchComboBox3.setEnabled(true);
        } else {
            batchComboBox3.setEnabled(false);
            idComboBox3.setEnabled(false);
        }
        
    }//GEN-LAST:event_courseComboBox3ActionPerformed

    private void batchComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchComboBox3ActionPerformed
        courseId = (String) courseComboBox3.getSelectedItem(); // Get selected item
        batch = (String) batchComboBox3.getSelectedItem(); // Get selected item
        
        idComboBox3.removeAllItems();
        idComboBox3.addItem("---");
               
        Database db = new Database();
        // Get ids from database
        List<String> ids = db.getIds(courseId, batch);
        //ids.clear();
        // Add items to idComboBox
        for (String id : ids) {
            idComboBox3.addItem(id);
        }
        idComboBox3.setEnabled(true);
    }//GEN-LAST:event_batchComboBox3ActionPerformed

    private void courseIdLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseIdLabel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_courseIdLabel3MouseClicked

    private void wholeBatchCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wholeBatchCheckBoxActionPerformed
        // TODO add your handling code here:
        if (wholeBatchCheckBox.isSelected()) {
            idComboBox.setSelectedIndex(1);
            idComboBox.setEditable(false);
        }
        
        
    }//GEN-LAST:event_wholeBatchCheckBoxActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        int index = idComboBox.getSelectedIndex();
        idComboBox.setSelectedIndex(index + 1);
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        int index = idComboBox.getSelectedIndex();
        idComboBox.setSelectedIndex(index - 1);
    }//GEN-LAST:event_backButtonActionPerformed

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        // Clear all records
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();   
        model.setRowCount(0); 
               
        // Get results
        try {
            if (!"---".equals(batch)) {
                if (!"---".equals(id)) {
                    Database db = new Database();
                    List<String> results = db.getResults(id, courseId, batch);
                    if (results.isEmpty()) {
                        statusTextField3.setText("No results available");
                    } else {
                        // Set table header text bold
                        JTableHeader tableHeader = resultTable.getTableHeader();
                        Font headerFont = new Font("Segoe UI", Font.BOLD, 13);
                        tableHeader.setFont(headerFont);

                        // Set Table Columns Width
                        TableColumnModel columnModel = resultTable.getColumnModel();	
                        columnModel.getColumn(0).setPreferredWidth(90); // Module
                        columnModel.getColumn(1).setPreferredWidth(15); // Exam
                        columnModel.getColumn(2).setPreferredWidth(15); // CW
                        columnModel.getColumn(3).setPreferredWidth(15); // Grade
                        columnModel.getColumn(4).setPreferredWidth(15); // GP

                        // Center align table cells
                        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                        int nocols = model.getColumnCount();
                        for(int x=0;x<nocols;x++){
                            resultTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
                        }

                        int rowSize = 5; // Number of elements per row
                        for (int i = 0; i < results.size(); i += rowSize) {
                            Object[] rowData = new Object[rowSize];
                            for (int j = 0; j < rowSize; j++) {
                                rowData[j] = results.get(i + j);
                            }
                            model.addRow(rowData);  // Add data to the table
                        }
                    }  
                }
            }
            
            
        }
        catch (Exception e) {
            //System.out.println(e);
        }
    }//GEN-LAST:event_viewButtonActionPerformed

    private void idComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idComboBox2ActionPerformed

    private void courseComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboBox2ActionPerformed
        statusTextField2.setText("");
        batchComboBox2.removeAllItems();
        batchComboBox2.addItem("---");
        
        if (courseComboBox2.getSelectedIndex() > 0) {
            batchComboBox2.setEnabled(true);
        }
        else {
            batchComboBox2.setEnabled(false);
        }
        // Get selected item
        courseId = (String) courseComboBox2.getSelectedItem();
        Database db = new Database();
        // Get batches from database
        List<String> batches = db.getBatches(courseId);
        // Add items to batchComboBox
        for (String batch : batches) {
            batchComboBox2.addItem(batch);
        }       
    }//GEN-LAST:event_courseComboBox2ActionPerformed

    private void moduleComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moduleComboBox2ActionPerformed

    private void batchComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batchComboBox2ActionPerformed

    private void statusTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusTextField1ActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        String courseIdd = (String) courseComboBox2.getSelectedItem();
        String batchh = (String) batchComboBox2.getSelectedItem();
        
        // Display a message while the report is loading
        statusTextField2.setText("Loading report, please wait...");

        // Create a new thread for report generation
        new Thread(() -> {
            try {
                // Generate the report
                ReportGenerator RG = new ReportGenerator();
                RG.batchSummaryReport(courseIdd, batchh);

                // Update the status after the report is loaded
                SwingUtilities.invokeLater(() -> statusTextField2.setText("Report loaded successfully!"));
            } catch (Exception e) {
                // Handle exceptions and update the status
                SwingUtilities.invokeLater(() -> statusTextField2.setText("Error loading report: " + e.getMessage()));
            }
        }).start();

    }//GEN-LAST:event_generateButtonActionPerformed

    private void themeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themeToggleButtonActionPerformed
        ImageIcon lightIcon = new ImageIcon(getClass().getResource("/icons/light-mode.png"));
        ImageIcon darkIcon = new ImageIcon(getClass().getResource("/icons/dark-mode.png"));
        
        if (themeToggleButton.isSelected()) {
            FlatDarkLaf.setup();
            SwingUtilities.updateComponentTreeUI(this);
            themeToggleButton.setIcon(darkIcon);
        } else {
            FlatLightLaf.setup();
            SwingUtilities.updateComponentTreeUI(this);
            themeToggleButton.setIcon(lightIcon);
        }
    }//GEN-LAST:event_themeToggleButtonActionPerformed

    private void clearButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton1ActionPerformed
        // Clear all records
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();   
        model.setRowCount(0); 
        
        courseComboBox3.setSelectedIndex(0);
        batchComboBox3.setSelectedIndex(0);
        idComboBox3.setSelectedIndex(0);
    }//GEN-LAST:event_clearButton1ActionPerformed

    
    public static void main(String args[]) {
        
        //FlatLightLaf.setup();   // Load Look and Feel theme
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatLightLaf.setup();   // Load Look and Feel theme
                new StaffDashboard(id).setVisible(true);
            }
        });  
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IdLabel3;
    private javax.swing.JLabel IdLabel4;
    private javax.swing.JLabel IdLabel5;
    private javax.swing.JLabel IdLabel6;
    private javax.swing.JButton backButton;
    protected javax.swing.JComboBox<String> batchComboBox;
    protected javax.swing.JComboBox<String> batchComboBox1;
    protected javax.swing.JComboBox<String> batchComboBox2;
    protected javax.swing.JComboBox<String> batchComboBox3;
    private javax.swing.JLabel batchLabel3;
    private javax.swing.JLabel batchLabel4;
    private javax.swing.JLabel batchLabel5;
    private javax.swing.JLabel batchLabel6;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearButton1;
    protected javax.swing.JComboBox<String> courseComboBox;
    protected javax.swing.JComboBox<String> courseComboBox1;
    protected javax.swing.JComboBox<String> courseComboBox2;
    protected javax.swing.JComboBox<String> courseComboBox3;
    private javax.swing.JLabel courseIdLabel3;
    private javax.swing.JLabel courseIdLabel4;
    private javax.swing.JLabel courseIdLabel5;
    private javax.swing.JLabel courseIdLabel6;
    private javax.swing.JTextField cwGradeTextField;
    private javax.swing.JTextField cwGradeTextField1;
    private javax.swing.JTextField cwMarksTextField;
    private javax.swing.JTextField cwMarksTextField1;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField examGradeTextField;
    private javax.swing.JTextField examGradeTextField1;
    private javax.swing.JLabel examMarksLabel3;
    private javax.swing.JLabel examMarksLabel5;
    private javax.swing.JTextField examMarksTextField;
    private javax.swing.JTextField examMarksTextField1;
    private javax.swing.JTextField finalGradeTextField;
    private javax.swing.JTextField finalGradeTextField1;
    private javax.swing.JButton generateButton;
    private javax.swing.JTextField gpTextField;
    private javax.swing.JTextField gpTextField1;
    private javax.swing.JLabel iconLabel;
    protected javax.swing.JComboBox<String> idComboBox;
    protected javax.swing.JComboBox<String> idComboBox1;
    protected javax.swing.JComboBox<String> idComboBox2;
    protected javax.swing.JComboBox<String> idComboBox3;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    protected javax.swing.JComboBox<String> moduleComboBox;
    protected javax.swing.JComboBox<String> moduleComboBox1;
    protected javax.swing.JComboBox<String> moduleComboBox2;
    private javax.swing.JLabel moduleLabel18;
    private javax.swing.JLabel moduleLabel19;
    private javax.swing.JLabel moduleLabel20;
    private javax.swing.JLabel moduleLabel21;
    private javax.swing.JLabel moduleLabel22;
    private javax.swing.JLabel moduleLabel23;
    private javax.swing.JLabel moduleLabel30;
    private javax.swing.JLabel moduleLabel31;
    private javax.swing.JLabel moduleLabel32;
    private javax.swing.JLabel moduleLabel33;
    private javax.swing.JLabel moduleLabel34;
    private javax.swing.JLabel moduleLabel35;
    private javax.swing.JLabel moduleLabel36;
    private javax.swing.JButton nextButton;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField statusTextField;
    private javax.swing.JTextField statusTextField1;
    private javax.swing.JTextField statusTextField2;
    private javax.swing.JTextField statusTextField3;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToggleButton themeToggleButton;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton viewButton;
    private javax.swing.JTextField viewIDTextField;
    private javax.swing.JTextField viewIDTextField1;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JCheckBox wholeBatchCheckBox;
    // End of variables declaration//GEN-END:variables
}
