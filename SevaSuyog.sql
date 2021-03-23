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
  `AddressType` enum('LOCAL','PERMANENT','WORK','TASK') NOT NULL DEFAULT 'LOCAL',
  `UserID` bigint(20) NOT NULL,
  `Address` varchar(150) NOT NULL,
  `LandMark` varchar(50) DEFAULT NULL,
  `LocalityID` mediumint(9) NOT NULL,
  `Label` varchar(30) DEFAULT NULL,
  `GUID` varchar(8) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_address` (`GUID`),
  KEY `fk_Address_user` (`UserID`),
  KEY `fk_Address_locality` (`LocalityID`),
  CONSTRAINT `fk_Address_locality` FOREIGN KEY (`LocalityID`) REFERENCES `locality` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Address_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'V','Is Verified','true/false','false'),(2,'AC','Is Active','true/false','true'),(3,'AV','Is Available','true/false','true');
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
  `GUID` varchar(3) NOT NULL,
  `Bhasha` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_bhasha` (`GUID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bhasha`
--

LOCK TABLES `bhasha` WRITE;
/*!40000 ALTER TABLE `bhasha` DISABLE KEYS */;
INSERT INTO `bhasha` VALUES (1,'hi','hindi');
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
  `CompanyID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `u_biodata` (`UserID`),
  KEY `fk_biodata_company` (`CompanyID`),
  CONSTRAINT `fk_BioData_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_biodata_company` FOREIGN KEY (`CompanyID`) REFERENCES `company` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biodata`
--

LOCK TABLES `biodata` WRITE;
/*!40000 ALTER TABLE `biodata` DISABLE KEYS */;
INSERT INTO `biodata` VALUES ('Jayram Kumar','SINGLE','MALE','1889-10-10','123456789012',NULL,NULL,1,NULL);
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
  `GUID` varchar(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_city` (`GUID`),
  KEY `fk_City_indianstate` (`IndianStateID`),
  CONSTRAINT `fk_City_indianstate` FOREIGN KEY (`IndianStateID`) REFERENCES `indianstate` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (2,'barahiya',1,'brya'),(6,'barahiya iyas',1,'bri'),(7,'barahiya iyas',1,'brki'),(9,'barahiya iyas',1,'kris');
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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(3) NOT NULL,
  `Company` varchar(50) NOT NULL,
  `Address` varchar(150) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_company` (`GUID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (2,'nc','new company','lohiya chowk'),(3,'ncz','new company','lohiya chowk'),(4,'ncy','new company','lohiya chowk');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favoriteemployee`
--

DROP TABLE IF EXISTS `favoriteemployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favoriteemployee` (
  `EmployerID` bigint(20) NOT NULL,
  `EmployeeID` bigint(20) NOT NULL,
  UNIQUE KEY `u_favoriteemployee` (`EmployerID`,`EmployeeID`),
  KEY `fk_FavoriteEmployee_employee` (`EmployeeID`),
  CONSTRAINT `fk_FavoriteEmployee_employee` FOREIGN KEY (`EmployeeID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_FavoriteEmployee_employer` FOREIGN KEY (`EmployerID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favoriteemployee`
--

LOCK TABLES `favoriteemployee` WRITE;
/*!40000 ALTER TABLE `favoriteemployee` DISABLE KEYS */;
/*!40000 ALTER TABLE `favoriteemployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fieldtype`
--

DROP TABLE IF EXISTS `fieldtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldtype` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(4) NOT NULL,
  `TableName` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_fieldtype` (`GUID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fieldtype`
--

LOCK TABLES `fieldtype` WRITE;
/*!40000 ALTER TABLE `fieldtype` DISABLE KEYS */;
INSERT INTO `fieldtype` VALUES (1,'ST','IndianState'),(2,'CT','City'),(3,'LCL','Locality'),(4,'PR','Profession'),(5,'SK','Skill'),(6,'BH','Bhasha');
/*!40000 ALTER TABLE `fieldtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `greeting`
--

DROP TABLE IF EXISTS `greeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `greeting` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Content` varchar(50) NOT NULL,
  `Count` int(10) unsigned zerofill NOT NULL DEFAULT 0000000001,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `greeting`
--

LOCK TABLES `greeting` WRITE;
/*!40000 ALTER TABLE `greeting` DISABLE KEYS */;
INSERT INTO `greeting` VALUES (1,'Shyam Sundar',0000000001),(2,'Mohan',0000000002),(3,'Soni Kumari',0000000490);
/*!40000 ALTER TABLE `greeting` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `indianstate`
--

LOCK TABLES `indianstate` WRITE;
/*!40000 ALTER TABLE `indianstate` DISABLE KEYS */;
INSERT INTO `indianstate` VALUES (1,'BR','બિહાર'),(2,'UP','Uttar Pradesh'),(3,'MP','Madhya Pradesh');
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
-- Table structure for table `objectalias`
--

DROP TABLE IF EXISTS `objectalias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objectalias` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FieldTypeID` tinyint(4) NOT NULL,
  `ObjectGUID` varchar(8) NOT NULL,
  `ObjectAlias` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `i_alias_object_guid` (`ObjectGUID`),
  KEY `fk_objectalias_fieldtype` (`FieldTypeID`),
  CONSTRAINT `fk_objectalias_fieldtype` FOREIGN KEY (`FieldTypeID`) REFERENCES `fieldtype` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objectalias`
--

LOCK TABLES `objectalias` WRITE;
/*!40000 ALTER TABLE `objectalias` DISABLE KEYS */;
/*!40000 ALTER TABLE `objectalias` ENABLE KEYS */;
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
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('01280940-f5ab-4330-a2c7-7dfca307e2bc','2d7e75e7-4970-425d-9d87-33ae4de11d77',1616195751113,1616203793610,2592000,1618795793610,'sevasuyogswagger'),('04962b4c-da38-4ff5-86f0-a53212bbb0e5','35267102-2c73-43ca-bb49-8150735a260d',1616162032096,1616164488337,2592000,1618756488337,'sevasuyogswagger'),('3126c89c-7caa-44e0-b2bf-1e4f4bf07060','c85179e4-e796-45f3-b244-7d746a319fa0',1616465494920,1616506282026,2592000,1619098282026,'sevasuyogswagger'),('3480055c-f4ed-4d42-b4ee-d15d8b8275c2','b1f503bd-805c-46fd-b608-3dcb177d1f68',1615997925555,1615997925555,2592000,1618589925555,NULL),('92675515-e8e0-4c5f-8f3f-e40e7fd257b8','4cec6eb3-e55d-4915-b6c2-04c68dc4899d',1616291609351,1616292588358,2592000,1618884588358,'sevasuyogswagger'),('aafe4e4b-68d8-4c09-bd1f-d8ee5c5b9226','ea56518c-07b9-4f10-a13f-835382eb82a7',1616424750988,1616424750989,2592000,1619016750989,NULL),('b5f812bf-a980-4885-a407-f7d0738fa690','5660d904-9ad3-4c73-8d38-83c8c154d712',1615977126136,1615977126136,2592000,1618569126136,NULL),('f0f11732-5033-43e6-8d81-410b8fb70661','80652a61-ffca-4f2b-ab02-97cae0cf4bae',1616377565710,1616378058911,2592000,1618970058911,'sevasuyogswagger');
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
INSERT INTO `spring_session_attributes` VALUES ('01280940-f5ab-4330-a2c7-7dfca307e2bc','SPRING_SECURITY_CONTEXT','��\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0Adminxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0\nsr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0sevasuyogswagger'),('01280940-f5ab-4330-a2c7-7dfca307e2bc','UserID','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0'),('04962b4c-da38-4ff5-86f0-a53212bbb0e5','SPRING_SECURITY_CONTEXT','��\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0Adminxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0\nsr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0sevasuyogswagger'),('04962b4c-da38-4ff5-86f0-a53212bbb0e5','UserID','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0'),('3126c89c-7caa-44e0-b2bf-1e4f4bf07060','SPRING_SECURITY_CONTEXT','��\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0Adminxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0\nsr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0sevasuyogswagger'),('3126c89c-7caa-44e0-b2bf-1e4f4bf07060','UserID','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0'),('92675515-e8e0-4c5f-8f3f-e40e7fd257b8','SPRING_SECURITY_CONTEXT','��\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0Adminxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0\nsr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0sevasuyogswagger'),('92675515-e8e0-4c5f-8f3f-e40e7fd257b8','UserID','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0'),('b5f812bf-a980-4885-a407-f7d0738fa690','UserRole','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0'),('f0f11732-5033-43e6-8d81-410b8fb70661','SPRING_SECURITY_CONTEXT','��\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0Adminxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0\nsr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0sevasuyogswagger'),('f0f11732-5033-43e6-8d81-410b8fb70661','UserID','��\0sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0');
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
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
  `FieldValue` varchar(50) NOT NULL,
  `IsResolved` tinyint(1) NOT NULL DEFAULT 0,
  `FieldTypeID` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_SuggestionByUser_user` (`UserID`),
  KEY `fk_suggestionbyuser_fieldtype` (`FieldTypeID`),
  CONSTRAINT `fk_SuggestionByUser_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_suggestionbyuser_fieldtype` FOREIGN KEY (`FieldTypeID`) REFERENCES `fieldtype` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestionbyuser`
--

LOCK TABLES `suggestionbyuser` WRITE;
/*!40000 ALTER TABLE `suggestionbyuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `suggestionbyuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(8) NOT NULL,
  `TaskName` varchar(50) NOT NULL,
  `Detail` varchar(150) DEFAULT NULL,
  `ProfessionID` smallint(6) NOT NULL,
  `EmployeeID` bigint(20) DEFAULT NULL,
  `EmployerAddressID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_task` (`GUID`),
  KEY `fk_Task_profession` (`ProfessionID`),
  KEY `fk_task_employee` (`EmployeeID`),
  KEY `fk_task_address` (`EmployerAddressID`),
  CONSTRAINT `fk_Task_profession` FOREIGN KEY (`ProfessionID`) REFERENCES `profession` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_address` FOREIGN KEY (`EmployerAddressID`) REFERENCES `address` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_employee` FOREIGN KEY (`EmployeeID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskapplication`
--

DROP TABLE IF EXISTS `taskapplication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskapplication` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GUID` varchar(8) NOT NULL,
  `TaskID` bigint(20) NOT NULL,
  `EmployeeID` bigint(20) NOT NULL,
  `IsRejected` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_taskapplication` (`GUID`),
  KEY `fk_TaskApplication_task` (`TaskID`),
  KEY `fk_TaskApplication_user` (`EmployeeID`),
  CONSTRAINT `fk_TaskApplication_task` FOREIGN KEY (`TaskID`) REFERENCES `task` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskApplication_user` FOREIGN KEY (`EmployeeID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskapplication`
--

LOCK TABLES `taskapplication` WRITE;
/*!40000 ALTER TABLE `taskapplication` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskapplication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskskill`
--

DROP TABLE IF EXISTS `taskskill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskskill` (
  `TaskID` bigint(20) NOT NULL,
  `SkillID` smallint(6) NOT NULL,
  UNIQUE KEY `u_taskskill` (`TaskID`,`SkillID`),
  KEY `fk_TaskSkill_skill` (`SkillID`),
  CONSTRAINT `fk_TaskSkill_skill` FOREIGN KEY (`SkillID`) REFERENCES `skill` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskSkill_task` FOREIGN KEY (`TaskID`) REFERENCES `task` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskskill`
--

LOCK TABLES `taskskill` WRITE;
/*!40000 ALTER TABLE `taskskill` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskskill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_user` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jayram','$2a$10$eI2FmJJ0JjfACqcbTv3JG.ADgJgn6u9kkGY1GsTZWScle2B4u19PO');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userattribute`
--

LOCK TABLES `userattribute` WRITE;
/*!40000 ALTER TABLE `userattribute` DISABLE KEYS */;
INSERT INTO `userattribute` VALUES (1,1,1,'true');
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
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `Role` enum('EMPLOYER','EMPLOYEE','ADMIN','ADVERTISER') NOT NULL DEFAULT 'EMPLOYEE',
  `GUID` varchar(8) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `u_userrole` (`GUID`),
  KEY `fk_userrole_user` (`UserID`),
  KEY `fk_userrole_sevarole` (`Role`),
  CONSTRAINT `fk_userrole_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (2,1,'ADMIN','abc');
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
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

-- Dump completed on 2021-03-23 19:09:11
