-- MySQL dump 10.13  Distrib 5.6.32, for Linux (x86_64)
--
-- Host: localhost    Database: iremote
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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iremote` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `iremote`;

--
-- Table structure for table `appversion`
--

DROP TABLE IF EXISTS `appversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appversion` (
  `appversionid` int(9) NOT NULL AUTO_INCREMENT,
  `platform` int(9) NOT NULL,
  `latestversion` varchar(32) COLLATE utf8_bin NOT NULL,
  `latestiversion` int(9) NOT NULL,
  `description` text COLLATE utf8_bin,
  `downloadurl` varchar(128) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`appversionid`),
  KEY `Unique_Index_AppVersion_platform` (`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `associationscene`
--

DROP TABLE IF EXISTS `associationscene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `associationscene` (
  `associationsceneid` int(11) NOT NULL AUTO_INCREMENT,
  `channelid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `zwavedeviceid` int(11) DEFAULT NULL,
  `cameraid` int(11) DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `scenetype` int(11) NOT NULL DEFAULT '2',
  `devicestatus` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `scenedbid` int(11) DEFAULT NULL,
  `operator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`associationsceneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `camera`
--

DROP TABLE IF EXISTS `camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `camera` (
  `cameraid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `productorid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `applianceuuid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `enablestatus` int(9) DEFAULT '0',
  `status` int(9) DEFAULT '1',
  `warningstatuses` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `armstatus` int(9) DEFAULT '1',
  `partitionid` int(9) DEFAULT NULL,
  PRIMARY KEY (`cameraid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `cardid` int(9) NOT NULL AUTO_INCREMENT,
  `thirdpartid` int(9) DEFAULT '0',
  `sha1key` varchar(256) COLLATE utf8_bin NOT NULL,
  `cardsequence` int(9) DEFAULT '0',
  `cardtype` int(9) DEFAULT NULL,
  PRIMARY KEY (`cardid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `command`
--

DROP TABLE IF EXISTS `command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `command` (
  `commandid` int(9) NOT NULL AUTO_INCREMENT,
  `associationsceneid` int(9) DEFAULT NULL,
  `scenedbid` int(9) DEFAULT NULL,
  `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `cmdindex` int(9) DEFAULT NULL,
  `delay` int(9) DEFAULT NULL,
  `infraredcodebase64` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `zwavecommandbase64` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `zwavecommandsbase64` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `weekday` int(9) DEFAULT NULL,
  `starttime` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `startsecond` int(9) DEFAULT NULL,
  `endsecond` int(9) DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `commandjson` text COLLATE utf8_bin,
  `launchscenedbid` int(9) DEFAULT NULL,
  `cameraid` int(11) DEFAULT NULL,
  PRIMARY KEY (`commandid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conditions`
--

DROP TABLE IF EXISTS `conditions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conditions` (
  `conditionsid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `channelid` int(9) DEFAULT NULL,
  `devicestatus` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scenedbid` int(9) NOT NULL,
  `status` int(9) NOT NULL,
  PRIMARY KEY (`conditionsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `databackup`
--

DROP TABLE IF EXISTS `databackup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databackup` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `userid` int(9) NOT NULL,
  `filename` varchar(128) COLLATE utf8_bin NOT NULL,
  `data` varchar(16384) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `databackup_userid_index` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `devicecapability`
--

DROP TABLE IF EXISTS `devicecapability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicecapability` (
  `devicecapabilityid` int(11) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(11) DEFAULT NULL,
  `capabilitycode` int(11) NOT NULL,
  `infrareddeviceid` int(11) DEFAULT NULL,
  `cameraid` int(11) DEFAULT NULL,
  `capabilityvalue` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`devicecapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceextendinfo`
--

DROP TABLE IF EXISTS `deviceextendinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceextendinfo` (
  `deviceextendinfoid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `zwaveproductormessage` text COLLATE utf8_bin,
  `devicepassword` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`deviceextendinfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `devicefunctionversioncapability`
--

DROP TABLE IF EXISTS `devicefunctionversioncapability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicefunctionversioncapability` (
  `devicefunctionversioncapabilityid` int(9) NOT NULL AUTO_INCREMENT,
  `devicetype` varchar(32) COLLATE utf8_bin NOT NULL,
  `functionversion` varchar(32) COLLATE utf8_bin NOT NULL,
  `capabilitycode` int(9) NOT NULL,
  PRIMARY KEY (`devicefunctionversioncapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `devicegroup`
--

DROP TABLE IF EXISTS `devicegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicegroup` (
  `devicegroupid` int(9) NOT NULL AUTO_INCREMENT,
  `devicegroupname` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `phoneuserid` int(9) NOT NULL,
  `devicetype` varchar(32) COLLATE utf8_bin NOT NULL,
  `icon` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`devicegroupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `devicegroupdetail`
--

DROP TABLE IF EXISTS `devicegroupdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicegroupdetail` (
  `devicegroupdetailid` int(9) NOT NULL AUTO_INCREMENT,
  `devicegroupid` int(9) NOT NULL,
  `zwavedeviceid` int(9) NOT NULL,
  `channelids` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`devicegroupdetailid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceindentifyinfo`
--

DROP TABLE IF EXISTS `deviceindentifyinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceindentifyinfo` (
  `deviceindentifyinfoid` int(9) NOT NULL AUTO_INCREMENT,
  `devicetype` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `devicecode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `qrcodeid` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `manufacturer` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `model` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  PRIMARY KEY (`deviceindentifyinfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceinitsetting`
--

DROP TABLE IF EXISTS `deviceinitsetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceinitsetting` (
  `deviceinitsettingid` int(9) NOT NULL AUTO_INCREMENT,
  `mid` varchar(32) COLLATE utf8_bin NOT NULL,
  `manufacture` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(16) COLLATE utf8_bin NOT NULL,
  `initcmds` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`deviceinitsettingid`),
  UNIQUE KEY `Index_deviceinitsetting_mid` (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceoperationsteps`
--

DROP TABLE IF EXISTS `deviceoperationsteps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceoperationsteps` (
  `deviceoprationstatusid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `optype` int(9) DEFAULT NULL,
  `appendmessage` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
  `status` int(9) NOT NULL,
  `starttime` datetime NOT NULL,
  `expiretime` datetime NOT NULL,
  `finished` tinyint(1) NOT NULL DEFAULT '0',
  `infrareddeviceid` int(11) DEFAULT NULL,
  `keyindex` int(11) DEFAULT NULL,
  `objid` int(9) DEFAULT NULL,
  PRIMARY KEY (`deviceoprationstatusid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceupgradepackage`
--

DROP TABLE IF EXISTS `deviceupgradepackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceupgradepackage` (
  `deviceupgradepackageid` int(9) NOT NULL AUTO_INCREMENT,
  `devicetype` varchar(32) COLLATE utf8_bin NOT NULL,
  `productor` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `model` varchar(32) COLLATE utf8_bin NOT NULL,
  `version1` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `version2` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `packagepath` varchar(512) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`deviceupgradepackageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceversioncapablity`
--

DROP TABLE IF EXISTS `deviceversioncapablity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceversioncapablity` (
  `deviceversioncapabilityid` int(9) NOT NULL AUTO_INCREMENT,
  `capabilitycode` int(9) NOT NULL,
  `deviceversioninfoid` int(9) NOT NULL,
  PRIMARY KEY (`deviceversioncapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceversioninfo`
--

DROP TABLE IF EXISTS `deviceversioninfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceversioninfo` (
  `deviceversioninfoid` int(9) NOT NULL AUTO_INCREMENT,
  `version` varchar(32) COLLATE utf8_bin NOT NULL,
  `productor` varchar(32) COLLATE utf8_bin NOT NULL,
  `model` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`deviceversioninfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doorlockalarmphone`
--

DROP TABLE IF EXISTS `doorlockalarmphone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doorlockalarmphone` (
  `doorlockalarmphoneid` int(11) NOT NULL AUTO_INCREMENT,
  `doorlockuserid` int(11) NOT NULL,
  `countrycode` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `alarmphone` varchar(30) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`doorlockalarmphoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doorlockassociation`
--

DROP TABLE IF EXISTS `doorlockassociation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doorlockassociation` (
  `doorlockassociationid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `usercode` int(9) NOT NULL,
  `objtype` int(9) NOT NULL,
  `objid` int(9) DEFAULT NULL,
  `appendmessage` varchar(1024) DEFAULT NULL,
  `creattime` datetime NOT NULL,
  PRIMARY KEY (`doorlockassociationid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doorlockpassword`
--

DROP TABLE IF EXISTS `doorlockpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doorlockpassword` (
  `doorlockpasswordid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `usertype` int(9) NOT NULL,
  `usercode` int(9) NOT NULL,
  `password` varchar(256) COLLATE utf8_bin NOT NULL,
  `validfrom` datetime DEFAULT NULL,
  `validthrough` datetime DEFAULT NULL,
  `weekday` int(9) DEFAULT NULL,
  `dayofweekjson` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `starttime` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `synstatus` int(9) NOT NULL,
  `status` int(9) NOT NULL,
  `errorcount` int(9) NOT NULL DEFAULT '0',
  `phonenumber` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `passwordtype` int(9) NOT NULL DEFAULT '1',
  `createtime` datetime NOT NULL,
  `sendtime` datetime DEFAULT NULL,
  PRIMARY KEY (`doorlockpasswordid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doorlockuser`
--

DROP TABLE IF EXISTS `doorlockuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doorlockuser` (
  `doorlockuserid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `usertype` int(9) NOT NULL,
  `usercode` int(9) NOT NULL,
  `username` varchar(256) COLLATE utf8_bin NOT NULL,
  `cardid` int(9) NOT NULL DEFAULT '0',
  `cardsequence` int(9) NOT NULL DEFAULT '0',
  `validfrom` datetime DEFAULT NULL,
  `validthrough` datetime DEFAULT NULL,
  `alarmtype` int(11) NOT NULL DEFAULT '1',
  `weekday` int(9) DEFAULT NULL,
  `starttime` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`doorlockuserid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family` (
  `familyid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `armstatus` int(9) DEFAULT NULL,
  PRIMARY KEY (`familyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fee_account`
--

DROP TABLE IF EXISTS `fee_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_account` (
  `accountid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) NOT NULL,
  `balance` int(9) DEFAULT '0',
  `blockedbalance` int(9) DEFAULT '0',
  `giftbalance` int(9) DEFAULT '0',
  `freesmscount` int(9) DEFAULT '5',
  `giftsmscount` int(9) DEFAULT '0',
  `payedsmscount` int(9) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fee_item`
--

DROP TABLE IF EXISTS `fee_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_item` (
  `feeitemid` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `feetype` int(9) NOT NULL,
  `objecttype` int(9) NOT NULL,
  PRIMARY KEY (`feeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fee_paymentdetail`
--

DROP TABLE IF EXISTS `fee_paymentdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_paymentdetail` (
  `paymentdetailid` int(11) NOT NULL AUTO_INCREMENT,
  `transactionid` int(11) NOT NULL,
  `paymenttype` int(11) NOT NULL,
  `amount` int(11) DEFAULT '0',
  `maxdrawback` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '1',
  `rf paymentdetailid` int(11) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `payedtime` datetime DEFAULT NULL,
  `expiretime` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentdetailid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fee_phoneuserfeeitem`
--

DROP TABLE IF EXISTS `fee_phoneuserfeeitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_phoneuserfeeitem` (
  `phoneuserfeeitemid` int(11) NOT NULL AUTO_INCREMENT,
  `phoneuserproductionid` int(11) NOT NULL,
  `feeitemid` int(11) NOT NULL,
  `fullname` int(11) NOT NULL,
  `feetype` int(11) NOT NULL,
  `objecttype` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `maxdrawback` int(11) NOT NULL,
  `freezeamount` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `validfrom` datetime NOT NULL,
  `validthrought` datetime NOT NULL,
  PRIMARY KEY (`phoneuserfeeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fee_transaction`
--

DROP TABLE IF EXISTS `fee_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_transaction` (
  `transactionid` int(9) NOT NULL AUTO_INCREMENT,
  `accountid` int(9) NOT NULL,
  `transactiontype` int(9) NOT NULL,
  `price` int(9) DEFAULT '0',
  `recharge` int(9) DEFAULT '0',
  `gift` int(9) DEFAULT '0',
  `deduct` int(9) DEFAULT '0',
  `freezeamount` int(9) DEFAULT '0',
  `freezegift` int(9) DEFAULT '0',
  `prebalance` int(9) DEFAULT '0',
  `postbalance` int(9) DEFAULT '0',
  `pregift` int(9) DEFAULT '0',
  `postgift` int(9) DEFAULT '0',
  `prefreeze` int(9) DEFAULT '0',
  `postfreeze` int(9) DEFAULT '0',
  `maxdrawback` int(9) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gatewaycapability`
--

DROP TABLE IF EXISTS `gatewaycapability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gatewaycapability` (
  `gatewaycapabilityid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) COLLATE utf8_bin NOT NULL,
  `capabilitycode` int(9) NOT NULL,
  PRIMARY KEY (`gatewaycapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gatewayinfo`
--

DROP TABLE IF EXISTS `gatewayinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gatewayinfo` (
  `gatewayinfoid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(128) COLLATE utf8_bin NOT NULL,
  `qrcodekey` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `gatewaytype` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`gatewayinfoid`),
  UNIQUE KEY `Index_GatewayInfo_deviceid` (`deviceid`),
  UNIQUE KEY `Index_GatewayInfo_qrcodekey` (`qrcodekey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infrareddevice`
--

DROP TABLE IF EXISTS `infrareddevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infrareddevice` (
  `infrareddeviceid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `codeid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `codeliberyjson` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `productorid` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `controlmodeid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `codeindex` int(9) DEFAULT NULL,
  `statuses` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `encrypted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`infrareddeviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infrareddevice2`
--

DROP TABLE IF EXISTS `infrareddevice2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infrareddevice2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` int(11) DEFAULT NULL,
  `brand_en` varchar(256) DEFAULT NULL,
  `brand_cn` varchar(256) DEFAULT NULL,
  `model` varchar(256) DEFAULT NULL,
  `pinyin` varchar(256) DEFAULT NULL,
  `code` varchar(9192) DEFAULT NULL,
  `devicetype` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infraredkey`
--

DROP TABLE IF EXISTS `infraredkey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infraredkey` (
  `infraredkeyid` int(9) NOT NULL AUTO_INCREMENT,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `keyindex` int(9) DEFAULT NULL,
  `keycode` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`infraredkeyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infreredcodeliberay`
--

DROP TABLE IF EXISTS `infreredcodeliberay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infreredcodeliberay` (
  `infreredcodeliberayid` int(9) NOT NULL AUTO_INCREMENT,
  `devicetype` varchar(16) COLLATE utf8_bin NOT NULL,
  `codeid` int(9) NOT NULL,
  `code` varchar(512) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`infreredcodeliberayid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infrereddeviceproductor`
--

DROP TABLE IF EXISTS `infrereddeviceproductor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infrereddeviceproductor` (
  `infrereddeviceproductorid` int(9) NOT NULL auto_increment,
  `productor` varchar(128) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  `name_en` varchar(128) collate utf8_bin default NULL,
  `codeids` varchar(16384) collate utf8_bin default NULL,
  PRIMARY KEY  (`infrereddeviceproductorid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `infreredcodeliberay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infreredcodeliberay` (
  `infreredcodeliberayid` int(9) NOT NULL auto_increment,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  `codeid` int(9) NOT NULL,
  `code` varchar(2048) collate utf8_bin NOT NULL default '',
  PRIMARY KEY  (`infreredcodeliberayid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `infreredcodeliberaymodel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infreredcodeliberaymodel` (
  `infreredcodeliberaymodelid` int(9) NOT NULL auto_increment,
  `productor` varchar(256) collate utf8_bin NOT NULL,
  `devicetype` varchar(64) collate utf8_bin NOT NULL,
  `model` varchar(256) collate utf8_bin NOT NULL,
  `codeid` int(9) NOT NULL,
  PRIMARY KEY  (`infreredcodeliberaymodelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
--
-- Table structure for table `infrereddeviceproductorcodemap`
--

DROP TABLE IF EXISTS `infrereddeviceproductorcodemap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infrereddeviceproductorcodemap` (
  `infrereddeviceproductorcodemapid` int(9) NOT NULL AUTO_INCREMENT,
  `productor` varchar(128) COLLATE utf8_bin NOT NULL,
  `devicetype` varchar(16) COLLATE utf8_bin NOT NULL,
  `codeids` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`infrereddeviceproductorcodemapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infrereddeviceproductormap`
--

DROP TABLE IF EXISTS `infrereddeviceproductormap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infrereddeviceproductormap` (
  `infrereddeviceproductormapid` int(9) NOT NULL AUTO_INCREMENT,
  `productor` varchar(128) COLLATE utf8_bin NOT NULL,
  `productormap` varchar(128) COLLATE utf8_bin NOT NULL,
  `devicetype` varchar(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`infrereddeviceproductormapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messagetemplate`
--

DROP TABLE IF EXISTS `messagetemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagetemplate` (
  `messagetemplateid` int(9) NOT NULL AUTO_INCREMENT,
  `key` varchar(32) COLLATE utf8_bin NOT NULL,
  `language` varchar(32) COLLATE utf8_bin NOT NULL,
  `platform` int(9) NOT NULL,
  `value` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `alitemplatecode` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `alitemplateparam` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`messagetemplateid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin NOT NULL,
  `nuid` int(9) DEFAULT NULL,
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
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  PRIMARY KEY (`notificationid`),
  KEY `Index_Notification_phoneuserid` (`phoneuserid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_16`
--

DROP TABLE IF EXISTS `notification_16`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_16` (
  `notificationid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `familyid` int(9) DEFAULT NULL,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `applianceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
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
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notificationsetting`
--

DROP TABLE IF EXISTS `notificationsetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificationsetting` (
  `notificationsettingid` int(9) NOT NULL AUTO_INCREMENT,
  `phonenumber` varchar(64) COLLATE utf8_bin NOT NULL,
  `notificationtype` int(9) NOT NULL,
  `athome` int(1) DEFAULT NULL,
  `starttime` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `startsecond` int(9) DEFAULT NULL,
  `endsecond` int(9) DEFAULT NULL,
  `phoneuserid` int(9) DEFAULT NULL,
  `app` int(9) DEFAULT NULL,
  `ring` int(9) DEFAULT NULL,
  `mail` varchar(125) COLLATE utf8_bin DEFAULT NULL,
  `builder_id` int(9) DEFAULT NULL,
  `sound` varchar(125) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`notificationsettingid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oemproductor`
--

DROP TABLE IF EXISTS `oemproductor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oemproductor` (
  `oemproductorid` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `platform` int(9) DEFAULT NULL,
  `pushmasterkey` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `pushappkey` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `smssign` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `deviceprefix` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `lechangeappid` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `lechangeappSecret` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `lechangeadmin` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `androidappdownloadurl` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `iosappdownloadurl` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `adverpicname` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`oemproductorid`),
  UNIQUE KEY `Unique_Index_Oemproductor_platform` (`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operationlog`
--

DROP TABLE IF EXISTS `operationlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operationlog` (
  `operationlogid` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `userip` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `requesturl` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `requestdata` text COLLATE utf8_bin,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `zwavedeviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `result` text COLLATE utf8_bin,
  `resultCode` int(9) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`operationlogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otherplatformuseridmap`
--

DROP TABLE IF EXISTS `otherplatformuseridmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otherplatformuseridmap` (
  `otherplatformuseridmapid` int(9) NOT NULL AUTO_INCREMENT,
  `otherplatformuserid` varchar(512) COLLATE utf8_bin NOT NULL,
  `phoneuserid` int(9) NOT NULL,
  `otherplatform` int(9) NOT NULL,
  `usertoken` varchar(64) COLLATE utf8_bin NOT NULL,
  `tokenid` int(9) NOT NULL,
  `createtime` datetime NOT NULL,
  `roomdbid` int(9) DEFAULT NULL,
  PRIMARY KEY (`otherplatformuseridmapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pd_phoneuserproduction`
--

DROP TABLE IF EXISTS `pd_phoneuserproduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pd_phoneuserproduction` (
  `phoneuserproductionid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) NOT NULL,
  `productionid` int(9) NOT NULL,
  `status` int(9) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`phoneuserproductionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pd_production`
--

DROP TABLE IF EXISTS `pd_production`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pd_production` (
  `productionid` int(11) NOT NULL AUTO_INCREMENT,
  `platform` int(11) NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `price` int(11) NOT NULL,
  `fullname` varchar(512) COLLATE utf8_bin NOT NULL,
  `status` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `validfrom` datetime NOT NULL,
  `validthrought` datetime DEFAULT NULL,
  PRIMARY KEY (`productionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pd_productionfeeitem`
--

DROP TABLE IF EXISTS `pd_productionfeeitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pd_productionfeeitem` (
  `productionfeeitemid` int(11) NOT NULL AUTO_INCREMENT,
  `productionid` int(11) NOT NULL,
  `feeitemid` int(11) NOT NULL,
  `fullname` varchar(512) COLLATE utf8_bin NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '0',
  `maxdrawback` int(11) NOT NULL DEFAULT '0',
  `freezeamount` int(11) NOT NULL DEFAULT '0',
  `validamount` int(11) DEFAULT NULL,
  `validunit` int(11) DEFAULT NULL,
  PRIMARY KEY (`productionfeeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phoneuser`
--

DROP TABLE IF EXISTS `phoneuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phoneuser` (
  `phoneuserid` int(9) NOT NULL AUTO_INCREMENT,
  `countrycode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `phonenumber` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `language` varchar(64) COLLATE utf8_bin DEFAULT 'zh_CN',
  `password` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `lastupdatetime` datetime DEFAULT NULL,
  `remote` text COLLATE utf8_bin,
  `scene` text COLLATE utf8_bin,
  `alias` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `longitude` int(9) DEFAULT NULL,
  `latitude` int(9) DEFAULT NULL,
  `smscount` int(11) DEFAULT '0',
  `callcount` int(11) DEFAULT '0',
  `armstatus` int(9) DEFAULT '1',
  `platform` int(9) NOT NULL DEFAULT '0',
  `familyid` int(9) DEFAULT NULL,
  `openId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `token` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `usertype` int(9) DEFAULT '0',
  `mail` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(9) DEFAULT NULL,
  PRIMARY KEY (`phoneuserid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phoneuserattribute`
--

DROP TABLE IF EXISTS `phoneuserattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phoneuserattribute` (
  `phoneuserattributeid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) NOT NULL,
  `code` varchar(64) COLLATE utf8_bin NOT NULL,
  `value` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`phoneuserattributeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `randcode`
--

DROP TABLE IF EXISTS `randcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `randcode` (
  `randcodeid` int(9) NOT NULL AUTO_INCREMENT,
  `randcode` varchar(32) COLLATE utf8_bin NOT NULL,
  `countrycode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `phonenumber` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `mail` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `platform` int(9) NOT NULL DEFAULT '0',
  `type` int(1) NOT NULL,
  `createtime` datetime NOT NULL,
  `expiretime` datetime NOT NULL,
  PRIMARY KEY (`randcodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `remote`
--

DROP TABLE IF EXISTS `remote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remote` (
  `deviceid` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phonenumber` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ssid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ip` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` int(9) DEFAULT '0',
  `latitude` int(9) DEFAULT '0',
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data` text COLLATE utf8_unicode_ci,
  `createtime` timestamp NULL DEFAULT NULL,
  `lastupdatetime` datetime DEFAULT NULL,
  `mac` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `homeid` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT '1',
  `phoneuserid` int(9) DEFAULT NULL,
  `platform` int(9) NOT NULL DEFAULT '0',
  `version` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `iversion` int(9) DEFAULT '0',
  `secritykeybase64` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zwavescuritykeybase64` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `network` int(9) DEFAULT '0',
  `networkintensity` int(9) DEFAULT '100',
  `powertype` int(9) DEFAULT '0',
  `battery` int(9) DEFAULT NULL,
  `temperature` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `timezoneid` int(9) DEFAULT NULL,
  `remotetype` int(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `roomdbid` int(9) NOT NULL AUTO_INCREMENT,
  `roomid` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phoneuserid` int(9) NOT NULL,
  `phonenumber` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`roomdbid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roomappliance`
--

DROP TABLE IF EXISTS `roomappliance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roomappliance` (
  `roomapplianceid` int(9) NOT NULL AUTO_INCREMENT,
  `roomdbid` int(9) NOT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `deviceid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `applianceid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `majortype` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `devicetype` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nuid` int(9) DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `subdeviceid` int(9) DEFAULT NULL,
  `channelid` int(9) DEFAULT NULL,
  PRIMARY KEY (`roomapplianceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roomdevice`
--

DROP TABLE IF EXISTS `roomdevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roomdevice` (
  `roomdeviceid` int(11) NOT NULL AUTO_INCREMENT,
  `roomdbid` int(11) DEFAULT NULL,
  `deviceid` int(11) DEFAULT NULL,
  `command` varchar(128) NOT NULL,
  `devicetype` varchar(10) DEFAULT NULL,
  `channel` int(11) DEFAULT NULL,
  `otherplatformuseridmapid` int(11) NOT NULL,
  PRIMARY KEY (`roomdeviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene`
--

DROP TABLE IF EXISTS `scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scene` (
  `scenedbid` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phoneuserid` int(11) NOT NULL,
  `sceneid` varchar(128) COLLATE utf8_bin NOT NULL,
  `thirdpartid` int(9) DEFAULT NULL,
  `scenetype` int(9) NOT NULL DEFAULT '1',
  `enablestatus` int(9) NOT NULL DEFAULT '1',
  `scenenotificationid` int(9) DEFAULT NULL,
  `starttime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `endtime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `weekday` int(9) DEFAULT NULL,
  `startsecond` int(9) DEFAULT NULL,
  `endsecond` int(9) DEFAULT NULL,
  PRIMARY KEY (`scenedbid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scenenotification`
--

DROP TABLE IF EXISTS `scenenotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scenenotification` (
  `scenenotificationid` int(9) NOT NULL AUTO_INCREMENT,
  `app` int(9) DEFAULT NULL,
  `mail` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ring` int(9) DEFAULT NULL,
  `builder_id` int(9) DEFAULT NULL,
  `sound` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `scenedbid` int(9) NOT NULL,
  PRIMARY KEY (`scenenotificationid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `securitypartition`
--

DROP TABLE IF EXISTS `securitypartition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `securitypartition` (
  `partitionid` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '防区名称',
  `phoneuserid` int(9) DEFAULT NULL,
  `dsczwavedeviceid` int(9) DEFAULT NULL COMMENT '本防区关联的DSC设备的ID',
  `dscpartitionid` int(9) DEFAULT NULL,
  `armstatus` int(9) DEFAULT '0',
  `warningstatus` int(9) DEFAULT '0',
  `status` int(9) DEFAULT '0',
  `delay` int(9) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`partitionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serverruntimglog`
--

DROP TABLE IF EXISTS `serverruntimglog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serverruntimglog` (
  `serverruntimglogid` int(9) NOT NULL AUTO_INCREMENT,
  `onlinegatewaycount` int(9) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`serverruntimglogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `smshistory`
--

DROP TABLE IF EXISTS `smshistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smshistory` (
  `smshistoryid` int(9) NOT NULL AUTO_INCREMENT,
  `countrycode` varchar(32) COLLATE utf8_bin NOT NULL,
  `phonenumber` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `phoneuserid` int(9) DEFAULT NULL,
  `message` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `sendtime` datetime DEFAULT NULL,
  PRIMARY KEY (`smshistoryid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `systemparameter`
--

DROP TABLE IF EXISTS `systemparameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemparameter` (
  `key` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `strvalue` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `intvalue` int(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `table_brand`
--

DROP TABLE IF EXISTS `table_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` int(11) DEFAULT NULL,
  `brand_cn` varchar(128) DEFAULT NULL,
  `brand_en` varchar(128) DEFAULT NULL,
  `devicetype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_eventforthirdpart`
--

DROP TABLE IF EXISTS `td_eventforthirdpart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_eventforthirdpart` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `thirdpartid` int(9) NOT NULL,
  `type` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `deviceid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `intparam` int(9) DEFAULT NULL,
  `objparam` varchar(8192) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `eventtime` datetime NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `floatparam` float(9,2) DEFAULT NULL,
  `warningstatus` int(9) DEFAULT NULL,
  `warningstatuses` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_td_eventforthirdpart_eventtime` (`eventtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_zwavedeviceeventpushvalues`
--

DROP TABLE IF EXISTS `td_zwavedeviceeventpushvalues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_zwavedeviceeventpushvalues` (
  `zwavedeviceeventpushvaluesid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `metervalue` float(9,2) NOT NULL,
  `floatappendparam` float(9,2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `lastsendtime` datetime DEFAULT NULL,
  PRIMARY KEY (`zwavedeviceeventpushvaluesid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `thirdpart`
--

DROP TABLE IF EXISTS `thirdpart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thirdpart` (
  `thirdpartid` int(9) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `platform` int(9) NOT NULL DEFAULT '0',
  `adminprefix` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`thirdpartid`),
  UNIQUE KEY `thirdpart_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `thirdpartattribute`
--

DROP TABLE IF EXISTS `thirdpartattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thirdpartattribute` (
  `thirdpartattributeid` int(9) NOT NULL AUTO_INCREMENT,
  `thirdpartid` int(9) NOT NULL,
  `code` varchar(128) COLLATE utf8_bin NOT NULL,
  `value` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`thirdpartattributeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `thirdparttoken`
--

DROP TABLE IF EXISTS `thirdparttoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thirdparttoken` (
  `thirdparttokenid` int(9) NOT NULL AUTO_INCREMENT,
  `thirdpartid` int(9) NOT NULL,
  `code` varchar(64) COLLATE utf8_bin NOT NULL,
  `token` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdatetime` datetime DEFAULT NULL,
  `validtime` datetime NOT NULL,
  PRIMARY KEY (`thirdparttokenid`),
  UNIQUE KEY `thirdparttoken_code` (`code`),
  UNIQUE KEY `thirdparttoken_token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timer`
--

DROP TABLE IF EXISTS `timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timer` (
  `timerid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `weekday` int(9) NOT NULL,
  `time` int(9) NOT NULL,
  `infraredcodebase64` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zwavecommandbase64` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `valid` int(9) NOT NULL DEFAULT '1',
  `executor` int(9) NOT NULL DEFAULT '0',
  `scenedbid` int(9) DEFAULT NULL,
  `scenetype` int(9) NOT NULL DEFAULT '3',
  `excutetime` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `commandjson` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`timerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timertask`
--

DROP TABLE IF EXISTS `timertask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timertask` (
  `timertaskid` int(9) NOT NULL AUTO_INCREMENT,
  `type` int(9) NOT NULL,
  `deviceid` varchar(64) DEFAULT NULL,
  `objid` int(9) DEFAULT NULL,
  `jsonpara` varchar(1024) DEFAULT NULL,
  `excutetime` datetime NOT NULL,
  `expiretime` datetime DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`timertaskid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timezone`
--

DROP TABLE IF EXISTS `timezone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timezone` (
  `timezoneid` int(9) NOT NULL AUTO_INCREMENT,
  `zonetext` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zoneid` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`timezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tp_communityadministrator`
--

DROP TABLE IF EXISTS `tp_communityadministrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tp_communityadministrator` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `thirdpartid` int(9) NOT NULL,
  `communityid` int(9) DEFAULT NULL,
  `phoneuserid` int(9) NOT NULL,
  `logicname` varchar(64) COLLATE utf8_bin NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `question` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `answer` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `user_username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userinout`
--

DROP TABLE IF EXISTS `userinout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinout` (
  `userinoutid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `phoneuserid` int(9) DEFAULT NULL,
  `phonenumber` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `action` int(9) DEFAULT NULL,
  PRIMARY KEY (`userinoutid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userservicemap`
--

DROP TABLE IF EXISTS `userservicemap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userservicemap` (
  `userservicemapid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `phonenumber` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `platform` int(9) DEFAULT NULL,
  `serviceid` int(9) DEFAULT NULL,
  PRIMARY KEY (`userservicemapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usershare`
--

DROP TABLE IF EXISTS `usershare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usershare` (
  `shareid` int(9) NOT NULL AUTO_INCREMENT,
  `shareuser` varchar(32) COLLATE utf8_bin NOT NULL,
  `touser` varchar(32) COLLATE utf8_bin NOT NULL,
  `tousercountrycode` varchar(32) COLLATE utf8_bin DEFAULT '86',
  `status` int(1) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `shareuserid` int(9) DEFAULT NULL,
  `touserid` int(9) DEFAULT NULL,
  `sharetype` int(9) DEFAULT '0',
  `sharedevicetype` int(1) DEFAULT '0',
  PRIMARY KEY (`shareid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usersharedevice`
--

DROP TABLE IF EXISTS `usersharedevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usersharedevice` (
  `usersharedeviceid` int(9) NOT NULL AUTO_INCREMENT,
  `usershareid` int(9) NOT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `zwavedeviceshareid` int(9) DEFAULT NULL,
  PRIMARY KEY (`usersharedeviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usertoken`
--

DROP TABLE IF EXISTS `usertoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertoken` (
  `tokenid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL,
  `token` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `securitytoken` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tokentype` int(9) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`tokenid`),
  UNIQUE KEY `usertoken_token` (`token`),
  KEY `usertoken_phoneuserid` (`phoneuserid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `voicetext`
--

DROP TABLE IF EXISTS `voicetext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voicetext` (
  `voicetextid` int(11) NOT NULL AUTO_INCREMENT,
  `voicetext` varchar(256) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`voicetextid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wcj_comunity`
--

DROP TABLE IF EXISTS `wcj_comunity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wcj_comunity` (
  `comunityid` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `code` varchar(64) CHARACTER SET utf8 NOT NULL,
  `thirdpartid` int(9) NOT NULL,
  PRIMARY KEY (`comunityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wcj_comunityremote`
--

DROP TABLE IF EXISTS `wcj_comunityremote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wcj_comunityremote` (
  `comunityremoteid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(64) CHARACTER SET utf8 NOT NULL,
  `comunityid` int(9) NOT NULL,
  `thirdpartid` int(9) NOT NULL,
  `fix` int(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`comunityremoteid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wcj_server`
--

DROP TABLE IF EXISTS `wcj_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wcj_server` (
  `thirdpartid` int(9) NOT NULL,
  `serverurl` varchar(128) DEFAULT NULL,
  `loginname` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`thirdpartid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zwavedevice`
--

DROP TABLE IF EXISTS `zwavedevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zwavedevice` (
  `zwavedeviceid` int(9) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(32) COLLATE utf8_bin NOT NULL,
  `nuid` int(9) NOT NULL,
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `status` int(9) DEFAULT NULL,
  `fstatus` float DEFAULT NULL,
  `shadowstatus` int(9) DEFAULT NULL,
  `warningstatuses` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `battery` int(9) DEFAULT NULL,
  `statuses` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `enablestatus` int(9) DEFAULT '0',
  `productor` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `model` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `version1` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `productor2` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `model2` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `version2` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `functionversion` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `deleteable` int(9) DEFAULT '1',
  `armstatus` int(9) DEFAULT '1',
  `version3` varchar(125) COLLATE utf8_bin DEFAULT NULL,
  `partitionid` int(9) DEFAULT NULL,
  PRIMARY KEY (`zwavedeviceid`),
  KEY `Index_ZwaveDevice_Deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zwavedeviceshare`
--

DROP TABLE IF EXISTS `zwavedeviceshare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zwavedeviceshare` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `touserid` int(9) NOT NULL,
  `touser` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `deviceid` varchar(64) COLLATE utf8_bin NOT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `cameraid` int(9) DEFAULT NULL,
  `token` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `securitycode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `shareowntype` int(9) DEFAULT '0',
  `validfrom` datetime DEFAULT NULL,
  `validthrough` datetime DEFAULT NULL,
  `validtype` int(9) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zwavesubdevice`
--

DROP TABLE IF EXISTS `zwavesubdevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zwavesubdevice` (
  `zwavesubdeviceid` int(11) NOT NULL AUTO_INCREMENT,
  `channelid` int(11) DEFAULT NULL,
  `name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `zwavedeviceid` int(11) DEFAULT NULL,
  `subdevicetype` varchar(9) COLLATE utf8_bin DEFAULT NULL,
  `partitionid` int(9) DEFAULT NULL,
  `status` int(9) DEFAULT NULL,
  `warningstatuses` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `enablestatus` int(9) DEFAULT '0',
  PRIMARY KEY (`zwavesubdeviceid`)
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

LOCK TABLES `oemproductor` WRITE;
/*!40000 ALTER TABLE `oemproductor` DISABLE KEYS */;
INSERT INTO `oemproductor` VALUES (1,'经纬纵横',0,'gmoZmRV0fBsazwNQW9mKWax8LBnbgF7QPP9pDHkHpSg=','meLVvQ27XPIXH9uxxI4Q6kRPNxJySMzdfiXmhD3o5zc=','【小白管家】','[\"iRemote2005\",\"iRemote3006\",\"iRemote3007\"]','BckE8jwvXwNG623tM3g9Jje5UlZGkhJyjpABesVB1hQ=','vNaLxLr8oD3rWx6Gizu6zQD3s91XBF4DNtDS4pSp/5I=','69f72609a26246ed','http://a.app.qq.com/o/simple.jsp?pkgname=com.jwzh.main','https://itunes.apple.com/cn/app/xiao-bai-guan-jia/id962651511?l=zh&ls=1&mt=8',NULL),(2,'创佳',2,'YLl1IRsljn6USi2G+3BStzdvHa2fhEIBO3tgqFctsXo=','osb1s0Z65F3HfLcYOpT88B8e22ZivaENVcoU2/3BocY=','【创佳智居】','[\"iRemote3005\"]','0KO4XOlGZFcQEngaHoa3wRz6t/FoXKT8MNXvsdqIKsI=','h6M+Gq55NT6m5qDwXTQYDdcElUyzdqQfhC42NRWHugs=','7264fb326eca45b6','http://shouji.baidu.com/software/22782579.html?qq-pf-to=pcqq.c2c','https://itunes.apple.com/cn/app/创佳智居/id1079794520?mt=8',NULL),(3,'多灵',3,'PFbur9ZVsrJruQJe4uQnv3xAv7ykMU4WbKH6ZUICrgQ=','khGG3m7j/MTSzXJwSNXkTk6+NSpn86oDAEtzj63bZEI=','【多灵慧居】','[\"iRemote4005\"]','gvbx9vMW7Rfjd0nougMmYEDAF5OV4YvBDXYtETv7oJY=','4vCCYwMVduEpfnbKWW/eVDKDqquxuXnU2zLqqDUgqpk=','c6b8ddda40e24002','http://sj.qq.com/myapp/detail.htm?apkName=com.jwzh.doorlink.main','https://itunes.apple.com/cn/app/多灵慧居/id1030923806?mt=8',NULL),(4,'Tecus',4,'y9eU7XThnKjs23kIXvEJjP1iaPS6Z9qxrVBTa8b8re8=','JnJhEgDyEnawZs2Y3Pz6Y1Q9UUoJPrsdsqW2uVM4/1Q=','【Tecus】','[\"iRemote5005\"]','4aRtTr4Qs6h6ho9Z67l6updkQbDfkqvr7Kp/SOw7j9Y=','bFs3ptWOLTj5xGUgh6kegsq23LD7lfrUKzbtO+Rm0jQ=','60428c5124bc4d83','http://sj.qq.com/myapp/detail.htm?apkName=com.jwzh.tecus.main','https://itunes.apple.com/cn/app/tecus/id1078555650?mt=8',NULL),(6,'小虎智慧家',6,'DRS2JlYrEcrGEL6Jfli0YIUpgy5BLjVWlCMMpm6RBrE=','734xpGDX91KZBj/Z+vZD3vKhR9dJhs3V00rLYSA4y1w=','【小虎智慧家】','[\"iRemote3008\"]','oxQe2jCU1EhcBj4nO2D7maGXWOHvabpI8Q90LWsv3Vg=','ZswGmrgKbSkhLAvr17i9AT/NpowBubWSXZY8EtG/vbk=','\"\"','http://sj.qq.com/myapp/detail.htm?apkName=cn.com.isurpass.xiaohu.main','https://itunes.apple.com/cn/app/小虎智慧家/id1220271796?l=zh&ls=1&mt=8',NULL),(7,'iSurpass',7,'ZX42AWQJgqPDhD37m//f4zpIQ8EOPA+ZF7RLVGed9Hc=','RpeHYpgHZ3Z0FUM36jvsU31hYuGPVNTiv3g+2zRqTag=','【iSurpass】','[\"iRemote6005\"]','fYyipOxjmnmi1ivuSBP65mfqa09bqjDSq8AoBickaRI=','7WEvobXGEGONKqxUAfG0cuCjC9hb5u4SaCLHyQQhKgM=','\"\"','https://play.google.com/store/apps/details?id=cn.com.isurpass.irtsg.main&hl=zh-CN','https://itunes.apple.com/cn/app/isurpass/id1318641310?l=zh&ls=1&mt=8',NULL),(8,'Keemple',8,'mhtKLKG2AtLbGeQJWP2a7n4F4bgLu/jCuejXuW9ICVI=','4Zpr1MAkWx45mCdGgwhMxpZx1ElpRLRZgKBkgP301Bo=','【Keemple】','[\"iRemote7005\"]','\"\"','\"\"','\"\"',NULL,NULL,NULL),(9,'Ameta',9,'8cP/pcunBi9mx1ScDkk4cxjFaniskvt5Vd02rmsHrrA=','dR6582oeiCkVJQcC9joQSeNKYoEMkBWU+6wXn8z18Q8=','【小白管家】','[\"iRemote8005\"]','\"\"','\"\"','\"\"',NULL,NULL,NULL),(10,'金网智能',10,'Yl++pzyGfoVXoN1Q3sRop1VHX6NaK9yHWkqJfaIq7YY=','e14Q7Cg2its18KwMm9Q5eiJYoqhJ/iks4g4e0JYl6S8=','【小白管家】','[\"iRemote3009\"]','42BdUCz658065TDiGNIWfP8lspk8Cy6c6IPYr0bo5fQ=','fng8d+PWpfX9gaQoXew73f8kzucDYaXDgrXRTFc9Qwk=','c9b8285471a4479a',NULL,NULL,'[\"1.jpg\",\"2.jpg\",\"3.jpg\"]'),(11,'乾坤小智',11,'N6vJyMG62jfQmzMaz/1d93rCCEjFEnrCseu8omVq0Yw=','WJuWNe8466lrW+n6lVnfVLMXWnqmvmz8aTqko0rfLhU=','【小白管家】','[\"iRemote3010\"]','ghUBHL7oWCW6K0oaPzwnNsGYuZmisnTdP56WYijXVNE=','2G2Oz+EhB011Fhu3oC1C8EdRbP1XoSkc5WsNIvm6MbM=','474a157b34b24335',NULL,NULL,NULL),(12,'欧多客',12,'8/D8WnrazAxe3Sm7UEvLmZT+v8ZCnAx/nfsTxolRlmk=','PMWQ41ri4kk4+DgigAt29bB3Vn9suibeS9+wFwRWzkc=','【小白管家】','[\"iRemote3011\"]','o4ljP2fw1zroA9tJrCom+QUexZN7cvWHnA+6B/vzr2E=','W+v/9tTnREYGRtN8Gr/bGup0IRSLiK0XUduIVT4lOqo=','a32284299ec24151',NULL,NULL,NULL),(13,'迅宿智能锁',13,'I3MdI6CYGEVtwiXT+BkW+3S6zM9p6exG1p3AdhdGlqU=','DlsuR3PGmN0RKkvAXXwGfGoDaYReJfK4A/FLVDb7xkA=','【小白管家】','[\"iRemote3012\"]','\"\"','\"\"','\"\"',NULL,NULL,NULL),(14,'西屋安防 A',14,'o9QxQyX4Oj8SzQwZImxYh6TfM3ZJtaUyNM6hxkMX38I=','GEJLXbvAuo65UJF8fai6PiaHyFFhTbIUzvsuNige0pM=','【小白管家】','[\"iRemote3013\"]',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `oemproductor` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `devicefunctionversioncapability` WRITE;
/*!40000 ALTER TABLE `devicefunctionversioncapability` DISABLE KEYS */;
INSERT INTO `devicefunctionversioncapability` VALUES (22,'5','0102',4),(23,'5','0102',5),(24,'5','0102',6),(25,'5','0102',7),(26,'5','0102',9),(27,'5','0102',10),(28,'5','0102',11),(29,'5','0103',4),(30,'5','0103',5),(31,'5','0103',6),(32,'5','0103',7),(33,'5','0103',9),(34,'5','0103',10),(35,'5','0103',11),(36,'5','0301',4),(37,'5','0301',5),(38,'5','0302',4),(39,'5','0302',5),(40,'5','0302',9),(41,'5','0401',4),(42,'5','0401',5),(43,'5','0401',6),(44,'5','0401',7),(45,'5','0401',9),(46,'5','0401',10),(47,'5','0401',11),(48,'5','0702',4),(49,'5','0702',5),(50,'5','0702',7),(51,'5','0702',9),(52,'5','0702',11),(53,'5','FFFF',4),(54,'5','FFFF',5),(55,'5','FFFF',6);
/*!40000 ALTER TABLE `devicefunctionversioncapability` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `systemparameter` WRITE;
/*!40000 ALTER TABLE `systemparameter` DISABLE KEYS */;
INSERT INTO `systemparameter` VALUES ('remoteport1',NULL,8920),('remoteport2',NULL,8921),('default_sms_count',NULL,5),('default_call_count',NULL,0),('remoteport1',NULL,8920),('remoteport2',NULL,8921),('default_sms_count',NULL,5),('default_call_count',NULL,0),('systemcode',NULL,0),('DeviceInitSetting_Version','v3.33',NULL),('doorlinkallthirdpartid',NULL,18),('ametaallthirdpartid',NULL,27),('domain_name','http://dev.isurpass.com.cn',NULL),('user_mail_server_host','smtp.exmail.qq.com',NULL),('user_mail_server_post','465',NULL),('user_mail_username','debug@isurpass.com.cn',NULL),('user_mail_password','i7oTfF7Mx8QyVsJH',NULL),('user_mail_fromaddress','debug@isurpass.com.cn',NULL),('user_mail_validate','true',NULL),('support_mail_server_host','smtp.exmail.qq.com',NULL),('support_mail_server_post','465',NULL),('support_mail_username','debug@isurpass.com.cn',NULL),('support_mail_password','i7oTfF7Mx8QyVsJH',NULL),('support_mail_fromaddress','debug@isurpass.com.cn',NULL),('support_mail_validate','true',NULL),('support_mail_toaddress','894258497@qq.com',NULL),('neoproductor','[\"025800030083\",\"025800031083\",\"025800032083\",\"025800033083\",\"025800034083\",\"025800035083\",\"025800036083\",\"025800037083\",\"025800038083\",\"02580003008d\",\"02580003108d\",\"02580003208d\",\"02580003308d\",\"02580003408d\",\"02580003508d\",\"02580003608d\",\"02580003708d\",\"02580003808d\",\"025800030023\",\"025800031023\",\"025800032023\",\"025800033023\",\"025800034023\",\"025800035023\",\"025800036023\",\"025800037023\",\"025800030086\"]',NULL),('gatewayheartbeattime',NULL,60),('ctccNBiotAppId','sHzOZl_nriBk8GePywnrY2KcPWoa',NULL),('ctccNBiotSecret','lxML6Wg3g_RvY_MYDAsqI17KlIYa',NULL),('defaultdebugdeviceid','[\"iRemote6005000000248\"]',NULL),('defaultheartbeatwithwifi',NULL,15),('defaultheartbeatwithgsm',NULL,300),('defaultdebugdeviceid','[\"iRemote6005000000248\"]',NULL),('defaultheartbeatwithwifi',NULL,15);
insert into systemparameter(`key`, strvalue, intvalue) values("defaultdebugdeviceid", "[]", NULL);
insert into systemparameter(`key`, strvalue, intvalue) values("defaultheartbeatwithwifi", NULL, 15);
insert into systemparameter(`key`, strvalue, intvalue) values("defaultheartbeatwithgsm", NULL, 300);
/*!40000 ALTER TABLE `systemparameter` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2018-09-11 10:13:43
