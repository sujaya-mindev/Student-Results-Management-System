# Student Results Management System

## Overview
The Student Results Management System is a Java-based application designed to manage and display student results. The application provides a login interface, dashboards for students and staff, and the ability to generate reports.

## Features
- **Login System**: Separate dashboards for students and staff.
- **Results Management**: Allows staff to input, modify, and view student results.
- **Reports**: Generates batch summaries using JasperReports.
- **User-Friendly Interface**: Enhanced with icons and graphical elements.

## Requirements
- **Java Version**: JDK 17 or higher
- **IDE**: NetBeans 12.5 or higher
- **Database**: MySQL 8.0 or higher
- **Build Tool**: Maven 3.6 or higher

## Setup Instructions

### 1. Install Required Software
Ensure the following software is installed:
- JDK 17 or higher
- NetBeans IDE
- MySQL Server
- Maven (bundled with NetBeans or installed separately)

### 2. Clone the Project
Download and extract the project folder from the provided zip file.

### 3. Database Setup
1. Open MySQL and create a database named `student_results`.
2. Import the database structure and sample data using the provided `database.sql` script (if included).
3. Update the database connection details in `Database.java` or the configuration file (if externalized):
   ```java
   String url = "jdbc:mysql://localhost:3306/student_result_management";
   String user = "root";
   String password = "password";
   ```

### 4. Import Project into NetBeans
1. Open NetBeans IDE.
2. Navigate to `File > Open Project` and select the extracted folder.
3. Ensure Maven resolves all dependencies by right-clicking the project and selecting `Build with Dependencies`.

### 5. Run the Application
1. Locate the main class `StudentResultsManagement.java`.
2. Right-click and select `Run` to start the application.

### 6. JasperReports Configuration
- Ensure the `batchSummary.jrxml` file is located in the `src/main/resources/reports` directory.
- The application automatically compiles and generates reports if dependencies are correctly configured.

## Dependencies
The following dependencies are managed through Maven (`pom.xml`):
- **JasperReports**: For report generation
- **MySQL Connector/J**: For database connectivity
- **Other Utilities**: As specified in the `pom.xml` file

## Folder Structure
- `src/main/java`: Contains all Java source files.
- `src/main/resources`: Includes resources such as icons and report templates.
- `pom.xml`: Maven configuration file for managing dependencies and builds.

## Notes
- Ensure the MySQL server is running before launching the application.
- Modify `Database.java` to match your local MySQL username and password.

