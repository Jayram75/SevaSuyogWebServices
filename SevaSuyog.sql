-- MariaDB dump 10.18  Distrib 10.5.8-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: SevaSuyog
-- ------------------------------------------------------
-- Server version	10.5.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AddressType` enum('LOCAL','PERMANENT','WORK') NOT NULL DEFAULT 'LOCAL',
  `UserID` bigint(20) NOT NULL,
  `Address` varchar(150) NOT NULL,
  `LandMark` varchar(50) DEFAULT NULL,
  `LocalityID` mediumint(9) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_address` (`AddressType`,`UserID`),
  KEY `fk_Address_user` (`UserID`),
  KEY `fk_Address_locality` (`LocalityID`),
  CONSTRAINT `fk_Address_locality` FOREIGN KEY (`LocalityID`) REFERENCES `locality` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Address_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(3) NOT NULL,
  `Attribute` varchar(50) NOT NULL,
  `PossibleValues` varchar(150) NOT NULL,
  `DefaultValue` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_attribute` (`GUID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'EE','Is Employee','true/false','false'),(2,'ER','Is Employer','true/false','false'),(3,'AD','Is Admin','true/false','false'),(4,'V','Is Verified','true/false','false'),(5,'AC','Is Active','true/false','true'),(6,'AV','Is Available','true/false','true');
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bhasha`
--

DROP TABLE IF EXISTS `bhasha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bhasha` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `Bhasha` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_bhasha` (`Bhasha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bhasha`
--

LOCK TABLES `bhasha` WRITE;
/*!40000 ALTER TABLE `bhasha` DISABLE KEYS */;
/*!40000 ALTER TABLE `bhasha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biodata`
--

DROP TABLE IF EXISTS `biodata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biodata` (
  `FullName` varchar(50) NOT NULL,
  `MaritalStatus` enum('SINGLE','MARRIED','DIVORCED','WIDOWED') NOT NULL DEFAULT 'SINGLE',
  `Gender` enum('MALE','FEMALE','TRANSGENDER') NOT NULL DEFAULT 'MALE',
  `DOB` date NOT NULL,
  `AadhaarNumber` varchar(12) NOT NULL,
  `PANNumber` varchar(10) DEFAULT NULL,
  `Photo` blob DEFAULT NULL,
  `UserID` bigint(20) NOT NULL,
  UNIQUE KEY `u_biodata` (`UserID`),
  CONSTRAINT `fk_BioData_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biodata`
--

LOCK TABLES `biodata` WRITE;
/*!40000 ALTER TABLE `biodata` DISABLE KEYS */;
/*!40000 ALTER TABLE `biodata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `City` varchar(50) NOT NULL,
  `IndianStateID` tinyint(4) NOT NULL,
  `GUID` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_city` (`GUID`),
  KEY `fk_City_indianstate` (`IndianStateID`),
  CONSTRAINT `fk_City_indianstate` FOREIGN KEY (`IndianStateID`) REFERENCES `indianstate` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `communication`
--

DROP TABLE IF EXISTS `communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `communication` (
  `CallNumber` varchar(10) NOT NULL,
  `SMSNumber` varchar(10) NOT NULL,
  `EmailAddress` varchar(50) DEFAULT NULL,
  `WhatsAppNumber` varchar(10) DEFAULT NULL,
  `UserID` bigint(20) NOT NULL,
  UNIQUE KEY `u_communication` (`UserID`),
  CONSTRAINT `fk_Communication_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `communication`
--

LOCK TABLES `communication` WRITE;
/*!40000 ALTER TABLE `communication` DISABLE KEYS */;
/*!40000 ALTER TABLE `communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indianstate`
--

DROP TABLE IF EXISTS `indianstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `indianstate` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(2) NOT NULL,
  `IndianState` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_indianstate` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `indianstate`
--

LOCK TABLES `indianstate` WRITE;
/*!40000 ALTER TABLE `indianstate` DISABLE KEYS */;
/*!40000 ALTER TABLE `indianstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locality`
--

DROP TABLE IF EXISTS `locality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locality` (
  `ID` mediumint(9) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(4) NOT NULL,
  `Locality` varchar(50) NOT NULL,
  `CityID` smallint(6) NOT NULL,
  `PinCode` varchar(6) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_locality` (`GUID`),
  KEY `fk_Locality_city` (`CityID`),
  CONSTRAINT `fk_Locality_city` FOREIGN KEY (`CityID`) REFERENCES `city` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locality`
--

LOCK TABLES `locality` WRITE;
/*!40000 ALTER TABLE `locality` DISABLE KEYS */;
/*!40000 ALTER TABLE `locality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `UserID` bigint(20) NOT NULL,
  `Latitude` decimal(10,8) DEFAULT NULL,
  `Longitude` decimal(11,8) DEFAULT NULL,
  `UpdateTS` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  KEY `fk_Location_user` (`UserID`),
  CONSTRAINT `fk_Location_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profession`
--

DROP TABLE IF EXISTS `profession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profession` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(3) NOT NULL,
  `Profession` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_profession` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profession`
--

LOCK TABLES `profession` WRITE;
/*!40000 ALTER TABLE `profession` DISABLE KEYS */;
/*!40000 ALTER TABLE `profession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qualification` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(8) NOT NULL,
  `QualificationType` enum('TENTH','TWELFTH','DEGREE','DIPLOMA','ENTRANCE_EXAM','TRAINING') DEFAULT NULL,
  `Detail` varchar(150) NOT NULL,
  `ScoreType` enum('RANK','PERCENTAGE','PERCENTILE','CGPA') DEFAULT 'PERCENTAGE',
  `Score` decimal(10,3) DEFAULT NULL,
  `CompletionYear` year(4) NOT NULL,
  `CertificatePhoto` blob DEFAULT NULL,
  `UserID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_qualification` (`GUID`),
  KEY `fk_Qualification_user` (`UserID`),
  CONSTRAINT `fk_Qualification_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skill` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(3) NOT NULL,
  `Skill` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_skill` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suggestionbyuser`
--

DROP TABLE IF EXISTS `suggestionbyuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suggestionbyuser` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `FieldType` enum('CITY','LOCALITY','PROFESSION','SKILL') NOT NULL,
  `FieldValue` varchar(50) NOT NULL,
  `IsResolved` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  KEY `fk_SuggestionByUser_user` (`UserID`),
  CONSTRAINT `fk_SuggestionByUser_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestionbyuser`
--

LOCK TABLES `suggestionbyuser` WRITE;
/*!40000 ALTER TABLE `suggestionbyuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `suggestionbyuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(50) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userattribute`
--

DROP TABLE IF EXISTS `userattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userattribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `AttributeID` smallint(6) NOT NULL,
  `AttributeValue` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_userattribute` (`UserID`,`AttributeID`),
  KEY `fk_userattribute_bhasha` (`AttributeID`),
  CONSTRAINT `fk_UserAttribute_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userattribute_bhasha` FOREIGN KEY (`AttributeID`) REFERENCES `attribute` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userattribute`
--

LOCK TABLES `userattribute` WRITE;
/*!40000 ALTER TABLE `userattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `userattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userbhasha`
--

DROP TABLE IF EXISTS `userbhasha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userbhasha` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `BhashaID` tinyint(4) NOT NULL,
  `CanSpeak` tinyint(1) NOT NULL DEFAULT 0,
  `CanRead` tinyint(1) NOT NULL DEFAULT 0,
  `CanWrite` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_userbhasha` (`UserID`,`BhashaID`),
  KEY `fk_UserBhasha_bhasha` (`BhashaID`),
  CONSTRAINT `fk_UserBhasha_bhasha` FOREIGN KEY (`BhashaID`) REFERENCES `bhasha` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBhasha_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userbhasha`
--

LOCK TABLES `userbhasha` WRITE;
/*!40000 ALTER TABLE `userbhasha` DISABLE KEYS */;
/*!40000 ALTER TABLE `userbhasha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersession`
--

DROP TABLE IF EXISTS `usersession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usersession` (
  `SessionID` varchar(50) NOT NULL,
  `UserID` bigint(20) DEFAULT NULL,
  `UserRole` enum('EMPLOYER','EMPLOYEE','ADMIN') DEFAULT NULL,
  `DeviceInfo` varchar(150) NOT NULL,
  `IsExpired` tinyint(1) NOT NULL DEFAULT 0,
  `InsertTS` timestamp NOT NULL DEFAULT current_timestamp(),
  `UpdateTS` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`SessionID`),
  KEY `fk_usersession_user` (`UserID`),
  CONSTRAINT `fk_usersession_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersession`
--

LOCK TABLES `usersession` WRITE;
/*!40000 ALTER TABLE `usersession` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userskill`
--

DROP TABLE IF EXISTS `userskill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userskill` (
  `UserID` bigint(20) NOT NULL,
  `SkillID` smallint(6) NOT NULL,
  UNIQUE KEY `u_userskill` (`UserID`,`SkillID`),
  KEY `fk_UserSkill_skill` (`SkillID`),
  CONSTRAINT `fk_UserSkill_skill` FOREIGN KEY (`SkillID`) REFERENCES `skill` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserSkill_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userskill`
--

LOCK TABLES `userskill` WRITE;
/*!40000 ALTER TABLE `userskill` DISABLE KEYS */;
/*!40000 ALTER TABLE `userskill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workexperience`
--

DROP TABLE IF EXISTS `workexperience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workexperience` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(8) NOT NULL,
  `ProfessionID` smallint(6) NOT NULL,
  `UserID` bigint(20) NOT NULL,
  `ExperienceInMonths` tinyint(4) NOT NULL,
  `Organization` varchar(50) DEFAULT NULL,
  `ExperienceCertificate` blob DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_workexperience` (`GUID`),
  KEY `fk_WorkExperience_profession` (`ProfessionID`),
  KEY `fk_WorkExperience_user` (`UserID`),
  CONSTRAINT `fk_WorkExperience_profession` FOREIGN KEY (`ProfessionID`) REFERENCES `profession` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkExperience_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workexperience`
--

LOCK TABLES `workexperience` WRITE;
/*!40000 ALTER TABLE `workexperience` DISABLE KEYS */;
/*!40000 ALTER TABLE `workexperience` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-24 20:26:49
