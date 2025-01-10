-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: student_result_management
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `batch`
--

DROP TABLE IF EXISTS `batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch` (
  `courseId` varchar(10) NOT NULL,
  `batch` varchar(10) NOT NULL,
  PRIMARY KEY (`courseId`,`batch`),
  KEY `batch` (`batch`),
  CONSTRAINT `batch_ibfk_1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch`
--

LOCK TABLES `batch` WRITE;
/*!40000 ALTER TABLE `batch` DISABLE KEYS */;
INSERT INTO `batch` VALUES ('DCSD','24.1F'),('DNE','24.1F'),('DSE','24.1F'),('DCSD','24.2F'),('DNE','24.2F'),('DSE','24.2F'),('DCSD','24.3F'),('DNE','24.3F'),('DSE','24.3F');
/*!40000 ALTER TABLE `batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `courseId` varchar(10) NOT NULL,
  `courseName` varchar(100) NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('DCSD','Diploma in Computer System Design'),('DNE','Diploma in Network Engineering'),('DSE','Diploma in Software Engineering');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mark`
--

DROP TABLE IF EXISTS `mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mark` (
  `id` varchar(50) NOT NULL,
  `courseId` varchar(10) NOT NULL,
  `batch` varchar(10) NOT NULL,
  `module` varchar(10) NOT NULL,
  `examMarks` int DEFAULT NULL,
  `examGrade` varchar(5) DEFAULT NULL,
  `cwMarks` int DEFAULT NULL,
  `cwGrade` varchar(5) DEFAULT NULL,
  `finalGrade` varchar(5) DEFAULT NULL,
  `finalGP` varchar(10) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`courseId`,`batch`,`module`),
  KEY `courseId` (`courseId`),
  KEY `batch` (`batch`),
  KEY `module` (`module`),
  CONSTRAINT `mark_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  CONSTRAINT `mark_ibfk_2` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  CONSTRAINT `mark_ibfk_3` FOREIGN KEY (`batch`) REFERENCES `batch` (`batch`),
  CONSTRAINT `mark_ibfk_4` FOREIGN KEY (`module`) REFERENCES `module` (`module`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES ('STU-0002','DSE','24.1F','ICS',84,'A',69,'A-','A','4.0','2025-01-07 15:17:02'),('STU-0004','DSE','24.1F','ICS',63,'B+',10,'E','C-','1.7','2025-01-07 15:17:24');
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `courseId` varchar(10) NOT NULL,
  `batch` varchar(10) NOT NULL,
  `module` varchar(10) NOT NULL,
  PRIMARY KEY (`courseId`,`batch`,`module`),
  UNIQUE KEY `id` (`id`),
  KEY `batch` (`batch`),
  KEY `module` (`module`),
  CONSTRAINT `module_ibfk_1` FOREIGN KEY (`module`) REFERENCES `modulename` (`module`),
  CONSTRAINT `module_ibfk_2` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  CONSTRAINT `module_ibfk_3` FOREIGN KEY (`batch`) REFERENCES `batch` (`batch`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'DSE','24.1F','ICS'),(2,'DSE','24.1F','MC'),(3,'DSE','24.1F','APF'),(4,'DNE','24.1F','ICS'),(5,'DNE','24.1F','MC'),(6,'DNE','24.1F','APF'),(7,'DCSD','24.1F','ICS'),(8,'DCSD','24.1F','MC'),(9,'DCSD','24.1F','APF'),(10,'DSE','24.2F','ICS'),(11,'DSE','24.2F','MC'),(12,'DSE','24.2F','APF'),(13,'DNE','24.2F','ICS'),(14,'DNE','24.2F','MC'),(15,'DNE','24.2F','APF'),(16,'DCSD','24.2F','ICS'),(17,'DCSD','24.2F','MC'),(18,'DCSD','24.2F','APF'),(19,'DSE','24.3F','ICS'),(20,'DSE','24.3F','MC'),(21,'DSE','24.3F','APF'),(22,'DNE','24.3F','ICS'),(23,'DNE','24.3F','MC'),(24,'DNE','24.3F','APF'),(25,'DCSD','24.3F','ICS'),(26,'DCSD','24.3F','MC'),(27,'DCSD','24.3F','APF');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulename`
--

DROP TABLE IF EXISTS `modulename`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modulename` (
  `module` varchar(10) NOT NULL,
  `moduleName` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`module`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulename`
--

LOCK TABLES `modulename` WRITE;
/*!40000 ALTER TABLE `modulename` DISABLE KEYS */;
INSERT INTO `modulename` VALUES ('APF','Algorithms and Programming Fundamentals','2025-01-04 14:08:19'),('ICS','Introduction to Computer Science','2025-01-04 14:08:10'),('MC','Mathematics for Computing','2025-01-04 14:08:15');
/*!40000 ALTER TABLE `modulename` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` varchar(50) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  `department` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('STA-0001','TestStaff1','TestStaff1','TestStaff1@staff.com','Staff','Admin');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` varchar(50) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `dob` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `courseId` varchar(10) NOT NULL,
  `batch` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `courseId` (`courseId`),
  KEY `batch` (`batch`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`),
  CONSTRAINT `student_ibfk_3` FOREIGN KEY (`batch`) REFERENCES `batch` (`batch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('STU-0001','TestStudent1','TestStudent1','2003-09-23','TestStudent1@student.com','DSE','24.1F'),('STU-0002','TestStudent2','TestStudent2','2003-09-23','TestStudent2@student.com','DSE','24.1F'),('STU-0003','TestStudent3','TestStudent3','2003-09-23','TestStudent3@student.com','DSE','24.1F'),('STU-0004','TestStudent4','TestStudent4','2003-09-23','TestStudent4@student.com','DSE','24.1F'),('STU-0005','TestStudent5','TestStudent5','2003-09-23','TestStudent5@student.com','DSE','24.1F'),('STU-0006','TestStudent6','TestStudent6','2003-09-23','TestStudent6@student.com','DSE','24.1F');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('student','staff') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('STA-0001','f708317a07996bb6f2a038334102739fae8ba195465056a0cf8977f703b1f0cb','staff'),('STU-0001','16c2447bce346bcd7a7022700c3428e6889e55d700bc5ec92d0a68e49b83f403','student'),('STU-0002','f52332d9610652858a173971782f714c21bb814d4a6a959c26a960c44ee54c0b','student'),('STU-0003','755ca71094b179edc0f77ee9e17537b0c752153eb40778af0d82a62752a6cb39','student'),('STU-0004','4fd18ec5ec7e5c748a1a46d4bae1b6e0a054d2fd957417122d98d30a7675dfb0','student'),('STU-0005','2daffe13ccf510b0c976581f781bcd1d3ff7d55f6a399234fd99b35ceb84bfd6','student'),('STU-0006','0b257d873a0f0851f8f953e57e0401664f39e20ce706c5c79fa1ec14fb40ece4','student');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-10 22:32:02
