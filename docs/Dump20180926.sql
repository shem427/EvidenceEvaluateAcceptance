-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: poa
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `code` (
  `CODE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CODE_TYPE` int(10) unsigned NOT NULL,
  `CODE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CODE_ID`),
  KEY `F_CODE_TYPE_idx` (`CODE_TYPE`),
  CONSTRAINT `F_CODE_TYPE` FOREIGN KEY (`CODE_TYPE`) REFERENCES `code_type` (`code_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code_type`
--

DROP TABLE IF EXISTS `code_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `code_type` (
  `CODE_TYPE_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CODE_TYPE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CODE_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_type`
--

LOCK TABLES `code_type` WRITE;
/*!40000 ALTER TABLE `code_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dept` (
  `DEPT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DEPT_NAME` varchar(128) COLLATE utf8_bin NOT NULL,
  `DEPT_REMARK` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID` int(11) unsigned DEFAULT '0',
  PRIMARY KEY (`DEPT_ID`),
  KEY `PARENT_DEPT` (`PARENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'合肥市公安局','root node 根节点',0),(2,'包河区公安局','YYYYYYYYYtestｘｘｘ',1),(3,'蜀山区公安局','tttttttttttttttttyyyyyyyyyyyyyyyyyyyyyttttt',1),(4,'瑶海区公安局','test',1),(23,'HHHH---HHH','xxx',3);
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept_manager`
--

DROP TABLE IF EXISTS `dept_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dept_manager` (
  `MANAGER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DEPT_ID` int(10) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`MANAGER_ID`),
  KEY `MANAGER_DEPT` (`DEPT_ID`) /*!80000 INVISIBLE */,
  KEY `MANAGER_USER` (`USER_ID`) /*!80000 INVISIBLE */
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept_manager`
--

LOCK TABLES `dept_manager` WRITE;
/*!40000 ALTER TABLE `dept_manager` DISABLE KEYS */;
INSERT INTO `dept_manager` VALUES (7,3,1),(10,1,1);
/*!40000 ALTER TABLE `dept_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `USER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `POLICE_NUMBER` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `USER_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PHONE_NUMBER` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEPT_ID` int(11) unsigned NOT NULL,
  `USER_ROLE` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `USER_PASSWORD` varchar(128) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `userId_UNIQUE` (`USER_ID`),
  UNIQUE KEY `POLICE_NUMBER_UNIQUE` (`POLICE_NUMBER`),
  KEY `USER_DEPT_ID_idx` (`DEPT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'092384','张三','13865932053',1,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(2,'092385','李四','13865932054',2,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(3,'092386','王五','13865932055',2,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(4,'092387','周六','13865932056',3,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(5,'092388','吴七','13865932057',3,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(6,'092389','冯八','13865932058',4,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(7,'092390','陈九','13865932059',4,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(8,'092391','张一一','13865932054',2,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(9,'092392','李二二','13865932055',2,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(10,'092393','马三三','13865932056',3,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(11,'092394','刘四四','13865932057',3,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(12,'092395','周五五','13865932058',4,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),(13,'092396','钱六六','13865932059',4,'ROLE_ADMIN','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-26  2:49:53
