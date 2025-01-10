package com.sujayamindev.student.results.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;




public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_result_management";   // Database URL
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    
    // Database connection
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Authenticate users
    public static boolean authenticate(String id, String password) {
        String query = "SELECT * FROM users WHERE id=? AND password=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get role (student or staff) from database
    public static String getRole(String id, String password) {
        String query = "SELECT role FROM users WHERE id=? AND password=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
                        ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    // Get student first name
    public static String getStudentFirstName(String id) {
        String query = "SELECT first_name FROM Student WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name");
            } else {
                return null; // Handle case where no matching user is found
            }
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
    }
    
    //  Get staff first name
    public static String getStaffFirstName(String id) {
        String query = "SELECT first_name FROM Staff WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name");
            } else {
                return null; // Handle case where no matching user is found
            }
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
    }
    
    // Get course ids from database
    public List<String> getCourseIds() {
        List<String> courseIds = new ArrayList<>();
        String query = "SELECT courseId FROM Course ORDER BY courseId ASC";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courseIds.add(rs.getString("courseId"));
            }  
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
    return courseIds;
    }
    
    // Get course ids from database
    public List<String> getStudnetCourseIds(String id) {
        List<String> courseIds = new ArrayList<>();
        String query = "SELECT courseId FROM Student WHERE id=? ORDER BY courseId ASC";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courseIds.add(rs.getString("courseId"));
            }  
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
    return courseIds;
    }
    
    // Get batches from database
    public List<String> getBatches(String courseId) {
        List<String> batches = new ArrayList<>();
        String query = "SELECT batch FROM Batch WHERE courseId=? ORDER BY batch ASC";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the courseId parameter in the PreparedStatement
            pstmt.setString(1, courseId);
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            // Process the ResultSet
            while (rs.next()) {
                batches.add(rs.getString("batch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return batches;
    }

    // Get ids from database
    public List<String> getIds(String courseId, String batch) {
        List<String> ids = new ArrayList<>();
        String query = "SELECT id FROM Student WHERE courseId=? AND batch=? ORDER BY id ASC";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseId);
            pstmt.setString(2, batch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString("id"));
            }  
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
        return ids;
    }
    
    // Get modules from database
    public List<String> getModules(String courseId, String batch) {
        List<String> modules = new ArrayList<>();
        String query = "SELECT module FROM Module WHERE courseId=? AND batch=? ORDER BY module ASC";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseId);
            pstmt.setString(2, batch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                modules.add(rs.getString("module"));
            }  
        } catch (SQLException e) {
        e.printStackTrace();
        return null;
        }
        return modules;
    }
    
    // Save marks to the database
    public void saveMarks(String id, String courseId, String batch, String module, int examMarks, String examGrade, int cwMarks, String cwGrade, String finalGP, String finalGrade) {
        String sql = "INSERT INTO Mark (id, courseId, batch, module, examMarks, examGrade, cwMarks, cwGrade, finalGP, finalGrade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            pstmt.setString(3, batch);
            pstmt.setString(4, module);
            pstmt.setInt(5, examMarks);
            pstmt.setString(6, examGrade);
            pstmt.setInt(7, cwMarks);
            pstmt.setString(8, cwGrade);
            pstmt.setString(9, finalGP);
            pstmt.setString(10, finalGrade);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Get marks from the database
    public List<String> getMarks(String id, String courseId, String batch, String module) {
        List<String> marks = new ArrayList<>();
        String query = "SELECT examMarks, examGrade, cwMarks, cwGrade, finalGP, finalGrade FROM Mark WHERE id=? AND courseId=? AND batch=? AND module=?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            pstmt.setString(3, batch);
            pstmt.setString(4, module);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                marks.add(rs.getString("examMarks"));
                marks.add(rs.getString("examGrade"));
                marks.add(rs.getString("cwMarks"));
                marks.add(rs.getString("cwGrade"));
                marks.add(rs.getString("finalGP"));
                marks.add(rs.getString("finalGrade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return marks;
    }
    
    // Update marks
    public void updateMarks(String id, String courseId, String batch, String module, int examMarks, String examGrade, int cwMarks, String cwGrade, String finalGP, String finalGrade) {
        String sql = "UPDATE Mark SET examMarks = ?, examGrade = ?, cwMarks = ?, cwGrade = ?, finalGP = ?, finalGrade = ? WHERE id = ? AND courseId = ? AND batch = ? AND module = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, examMarks);
            pstmt.setString(2, examGrade);
            pstmt.setInt(3, cwMarks);
            pstmt.setString(4, cwGrade);
            pstmt.setString(5, finalGP);
            pstmt.setString(6, finalGrade);
            pstmt.setString(7, id);
            pstmt.setString(8, courseId);
            pstmt.setString(9, batch);
            pstmt.setString(10, module);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }
    
    // Delete marks
    public void deleteMarks(String id, String courseId, String batch, String module) {
        String sql = "DELETE FROM Mark WHERE id = ? AND courseId = ? AND batch = ? AND module = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            pstmt.setString(3, batch);
            pstmt.setString(4, module);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Get Results summary (Staff Dashboard)
    public List<String> getResults(String id, String courseId, String batch) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT m.module, m.examGrade, m.cwGrade, m.finalGrade, m.finalGP " +
             "FROM Mark m " +
             "JOIN Module modu ON m.courseId = modu.courseId AND m.batch = modu.batch AND m.module = modu.module " +
             "WHERE m.id = ? AND m.courseId = ? AND m.batch = ? " +
             "ORDER BY m.created_at ASC";


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            pstmt.setString(3, batch);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //results.add(rs.getString("row_num"));
                results.add(rs.getString("module"));
                results.add(rs.getString("examGrade"));
                results.add(rs.getString("cwGrade"));
                results.add(rs.getString("finalGrade"));
                results.add(rs.getString("finalGP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }
        return results;
    }
    

    // Get Results summary (Student Dashboard)
    public List<String> getResultsStudent(String id, String courseId) {
        List<String> results = new ArrayList<>();
        String batch = null;

        // Step 1: Retrieve the batch from the Student table
        String getBatchSql = "SELECT batch FROM Student WHERE id = ? AND courseId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getBatchSql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                batch = rs.getString("batch");
            } else {
                return results;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }

        // Step 2: Get results from the Mark table using the retrieved batch
        String getResultsSql = "SELECT m.module, m.examGrade, m.cwGrade, m.finalGrade, m.finalGP " +
                     "FROM Mark m " +
                     "JOIN Module modu ON m.courseId = modu.courseId AND m.batch = modu.batch AND m.module = modu.module " +
                     "WHERE m.id = ? AND m.courseId = ? AND m.batch = ? " +
                     "ORDER BY m.created_at ASC";


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getResultsSql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, courseId);
            pstmt.setString(3, batch);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                results.add(rs.getString("module"));
                results.add(rs.getString("examGrade"));
                results.add(rs.getString("cwGrade"));
                results.add(rs.getString("finalGrade"));
                results.add(rs.getString("finalGP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }
        return results;
    }
    
    
        // Execute query and return ResultSet
    public ResultSet getModuleNameBSData(String courseId, String batch) {
        String query = """
                       SELECT 
                           M.module, 
                           MN.moduleName as moduleName, 
                           M.examMarks, 
                           M.cwMarks, 
                           M.created_at
                       FROM 
                           Mark M
                       JOIN 
                           ModuleName MN ON M.module = MN.module
                       WHERE 
                           M.courseId = ? AND M.batch = ?
                       ORDER BY 
                           M.created_at;
                       
                       """;
;

        try {
            Connection connection = connect();
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, batch);

            return preparedStatement.executeQuery(); // Return the ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public String getCourseName(String courseId) {
        String query = "SELECT courseName FROM Course WHERE courseId = ?;";
        String courseName = null; // Initialize the course name variable

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // Check if a result exists
                courseName = resultSet.getString("courseName"); // Retrieve the course name
            }

            resultSet.close(); // Close the ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseName; // Return the course name (null if not found)
    }
    
    
 
    
    public ResultSet getModuleMarksBSData(String courseId, String batch) {
        String query = """
                       SELECT M.module, MN.moduleName, M.examMarks, M.cwMarks, M.created_at
                       FROM Mark M
                       JOIN ModuleName MN ON M.module = MN.module
                       WHERE M.courseId = ? AND M.batch = ?
                       ORDER BY M.created_at;
                       """;
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, batch);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}