-- MySQL dump 10.13  Distrib 5.6.32, for Linux (x86_64)
--
-- Host: localhost    Database: iremotenotification
-- ------------------------------------------------------
-- Server version	5.6.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iremotenotification` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `iremotenotification`;
--
-- Table structure for table `notification_1`
--

DROP TABLE IF EXISTS `notification_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_1` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_2`
--

DROP TABLE IF EXISTS `notification_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_2` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_3`
--

DROP TABLE IF EXISTS `notification_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_3` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_4`
--

DROP TABLE IF EXISTS `notification_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_4` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_5`
--

DROP TABLE IF EXISTS `notification_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_5` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_6`
--

DROP TABLE IF EXISTS `notification_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_6` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_camera`
--

DROP TABLE IF EXISTS `notification_camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_camera` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_warning`
--

DROP TABLE IF EXISTS `notification_warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_warning` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `orimessage` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `reporttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `eclipseby` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendmessage` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `appendjsonstring` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `unalarmphoneuserid` int(9) DEFAULT NULL,
  `deleteflag` int(9) DEFAULT '0',
  `deletephoneuserid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`),
  KEY `Index_Notification_warning_phoneuserid` (`phoneuserid`),
  KEY `Index_Notification_warning_reporttime` (`reporttime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-11 10:41:46
