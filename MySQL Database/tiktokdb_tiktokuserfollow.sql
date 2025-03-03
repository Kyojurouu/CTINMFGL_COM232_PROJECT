-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: tiktokdb
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `tiktokuserfollow`
--

DROP TABLE IF EXISTS `tiktokuserfollow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiktokuserfollow` (
  `FollowID` int NOT NULL AUTO_INCREMENT,
  `UserNameID` varchar(50) DEFAULT NULL,
  `FollowUserNameID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FollowID`),
  KEY `UserNameID` (`UserNameID`),
  KEY `FollowUserNameID` (`FollowUserNameID`),
  CONSTRAINT `tiktokuserfollow_ibfk_1` FOREIGN KEY (`UserNameID`) REFERENCES `tiktokusers` (`UserNameID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tiktokuserfollow_ibfk_2` FOREIGN KEY (`FollowUserNameID`) REFERENCES `tiktokusers` (`UserNameID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiktokuserfollow`
--

LOCK TABLES `tiktokuserfollow` WRITE;
/*!40000 ALTER TABLE `tiktokuserfollow` DISABLE KEYS */;
INSERT INTO `tiktokuserfollow` VALUES (4,'@howardo','@johnmikael'),(5,'@howardo','@leonmarco'),(6,'@howardo','@teejay'),(7,'@howardo','@jersey'),(8,'@teejay','@howardo'),(9,'@teejay','@johnmikael'),(10,'@teejay','@leonmarco'),(11,'@leonmarco','@johnmikael'),(12,'@leonmarco','@teejay'),(13,'@leonmarco','@jersey'),(14,'@jersey','@johnmikael'),(15,'@jersey','@leonmarco'),(16,'@jersey','@teejay'),(17,'@johnmikael','@teejay'),(18,'@johnmikael','@leonmarco');
/*!40000 ALTER TABLE `tiktokuserfollow` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-03 22:08:44
