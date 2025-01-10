package com.sujayamindev.student.results.management;

import java.io.InputStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportGenerator {
    public void batchSummaryReport(String courseId, String batch) {
        try {
            // Step 1: Compile the Jasper report
            InputStream reportStream = getClass().getResourceAsStream("/reports/batchSummary.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Step 2: Fetch data from the database
            Database db = new Database();
            ResultSet resultSet = db.getModuleMarksBSData(courseId, batch);

            if (resultSet == null) {
                return;
            }

            // Step 3: Process data and sort records by created_at
            List<Map<String, Object>> records = new ArrayList<>();
            int totalStudents = 0;
            int totalExamPass = 0;
            int totalCWPass = 0;
            int totalModules = 0;
            int passedStudents = 0;
            int studentCount = 0;
            double totalFinalPass = 0;

            while (resultSet.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("moduleName", resultSet.getString("moduleName"));  // Updated to use full module name
                record.put("examMarks", resultSet.getInt("examMarks"));
                record.put("cwMarks", resultSet.getInt("cwMarks"));
                record.put("created_at", resultSet.getTimestamp("created_at"));

                records.add(record);



                // Aggregate overall pass counts
                totalStudents++;
                if (resultSet.getInt("examMarks") >= 60) totalExamPass++;
                if (resultSet.getInt("cwMarks") >= 60) totalCWPass++;
                //if (resultSet.getInt("examMarks") >= 60 && resultSet.getInt("cwMarks") >= 60) totalFinalPass++;
            }

            // Sort records by created_at
            records.sort(Comparator.comparing(record -> (Timestamp) record.get("created_at")));

            // Step 4: Aggregate data by module
            Map<String, Map<String, Object>> aggregatedData = new LinkedHashMap<>();

            for (Map<String, Object> record : records) {
                String moduleName = (String) record.get("moduleName");
                int examMarks = (int) record.get("examMarks");
                int cwMarks = (int) record.get("cwMarks");

                aggregatedData.putIfAbsent(moduleName, new HashMap<>());
                Map<String, Object> aggregatedRecord = aggregatedData.get(moduleName);

                aggregatedRecord.put("moduleName", moduleName);
                aggregatedRecord.put("examMarks", aggregatedRecord.getOrDefault("examMarks", 0));
                aggregatedRecord.put("cwMarks", aggregatedRecord.getOrDefault("cwMarks", 0));
                aggregatedRecord.put("created_at", record.get("created_at")); // Use the latest created_at for this module

                aggregatedRecord.put("examMarks", (int) aggregatedRecord.get("examMarks") + examMarks);
                aggregatedRecord.put("cwMarks", (int) aggregatedRecord.get("cwMarks") + cwMarks);
                aggregatedRecord.put("studentCount", (int) aggregatedRecord.getOrDefault("studentCount", 0) + 1);

                // Calculate pass percentages for the module
                int examPassCount = (int) aggregatedRecord.getOrDefault("examPassCount", 0);
                int cwPassCount = (int) aggregatedRecord.getOrDefault("cwPassCount", 0);
                int finalPassCount = (int) aggregatedRecord.getOrDefault("finalPassCount", 0);
                if (examMarks >= 60) examPassCount++;
                if (cwMarks >= 60) cwPassCount++;
                if (examMarks >= 60 && cwMarks >= 60)  finalPassCount++;

                aggregatedRecord.put("examPassCount", examPassCount);
                aggregatedRecord.put("cwPassCount", cwPassCount);
                aggregatedRecord.put("finalPassCount", finalPassCount);
            }

            // Step 5: Convert aggregated data to a sorted list
            List<Map<String, Object>> dataList = new ArrayList<>(aggregatedData.values());
            dataList.sort(Comparator.comparing(record -> (Timestamp) record.get("created_at")));

            for (Map<String, Object> aggregatedRecord : dataList) {
                studentCount = (int) aggregatedRecord.get("studentCount");
                totalModules++;
                double examPassPercentage = Math.round(((double) (int) aggregatedRecord.get("examPassCount") / studentCount * 100) * 100.0) / 100.0;
                double cwPassPercentage = Math.round(((double) (int) aggregatedRecord.get("cwPassCount") / studentCount * 100) * 100.0) / 100.0;
                double totalPassPercentage = Math.round(((double) (int) (examPassPercentage + cwPassPercentage) / studentCount * 100)) / 100.0;
                totalFinalPass = totalFinalPass + totalPassPercentage;
                passedStudents = ((int) aggregatedRecord.get("examPassCount") + (int) aggregatedRecord.get("cwPassCount") / studentCount);
                aggregatedRecord.put("examPassPercentage", examPassPercentage + "%");
                aggregatedRecord.put("cwPassPercentage", cwPassPercentage + "%");
                aggregatedRecord.put("totalPassPercentage", totalPassPercentage + "%");
                
            }

            // Step 6: Calculate overall percentages
            double overallExamPassPercentage = Math.round(((double) totalExamPass / totalStudents * 100) * 100.0) / 100.0;
            double overallCWPassPercentage = Math.round(((double) totalCWPass / totalStudents * 100) * 100.0) / 100.0;
            double overallTotalPassPercentage = Math.round(((double) totalFinalPass / totalModules) * 100.0) / 100.0;


            // Step 7: Add additional parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("courseName", db.getCourseName(courseId));
            parameters.put("batch", batch);
            parameters.put("totalExamPassPercentage", overallExamPassPercentage + "%");
            parameters.put("totalCWPassPercentage", overallCWPassPercentage + "%");
            parameters.put("totalOverallPassPercentage", overallTotalPassPercentage + "%");
            parameters.put("studentCountTotal", studentCount);
            parameters.put("studentCountPassed", passedStudents);
            parameters.put("studentCountFailed", studentCount - passedStudents);



            // Use a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            // Fill the report with the data source
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Step 8: Display the report
            JasperViewer.viewReport(jasperPrint, false);

            // Step 9: Close the connection
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}