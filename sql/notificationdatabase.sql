/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.0.22-community-nt : Database - iremotenotification
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `iremotenotification` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

/*Table structure for table `notification_1` */
use iremotenotification;
CREATE TABLE `notification_1` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_2` */

CREATE TABLE `notification_2` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_3` */

CREATE TABLE `notification_3` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_4` */

CREATE TABLE `notification_4` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_5` */

CREATE TABLE `notification_5` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_6` */

CREATE TABLE `notification_6` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `notification_16` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification_camera` */

CREATE TABLE `notification_camera` (
  `notificationid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) default NULL,
  `familyid` int(9) default NULL,
  `deviceid` varchar(32) collate utf8_bin default NULL,
  `nuid` int(9) default NULL,
  `zwavedeviceid` int(9) default NULL,
  `cameraid` int(9) default NULL,
  `applianceid` varchar(32) collate utf8_bin default NULL,
  `message` varchar(64) collate utf8_bin default NULL,
  `orimessage` varchar(256) collate utf8_bin default NULL,
  `majortype` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(64) collate utf8_bin default NULL,
  `reporttime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `eclipseby` int(9) default NULL,
  `status` int(9) default '0',
  `name` varchar(64) collate utf8_bin default NULL,
  `appendmessage` varchar(64) collate utf8_bin default NULL,
  `appendjsonstring` varchar(64) collate utf8_bin default NULL,
  `unalarmphonenumber` varchar(64) collate utf8_bin default NULL,
  `unalarmphoneuserid` int(9) default NULL,
  `deleteflag` int(9) default '0',
  `deletephoneuserid` int(9) default NULL,
  PRIMARY KEY  (`notificationid`),
  KEY `Index_Notification_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


ALTER TABLE `notification_1` ADD INDEX `Index_Notification_1_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_1` ADD INDEX `Index_Notification_1_reporttime` (`reporttime`);    
ALTER TABLE `notification_2` ADD INDEX `Index_Notification_2_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_2` ADD INDEX `Index_Notification_2_reporttime` (`reporttime`);    
ALTER TABLE `notification_3` ADD INDEX `Index_Notification_3_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_3` ADD INDEX `Index_Notification_3_reporttime` (`reporttime`);    
ALTER TABLE `notification_4` ADD INDEX `Index_Notification_4_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_4` ADD INDEX `Index_Notification_4_reporttime` (`reporttime`);    
ALTER TABLE `notification_5` ADD INDEX `Index_Notification_5_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_5` ADD INDEX `Index_Notification_5_reporttime` (`reporttime`);    
ALTER TABLE `notification_6` ADD INDEX `Index_Notification_6_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_6` ADD INDEX `Index_Notification_6_reporttime` (`reporttime`);    
ALTER TABLE `notification_16` ADD INDEX `Index_Notification_16_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_16` ADD INDEX `Index_Notification_16_reporttime` (`reporttime`);    
ALTER TABLE `notification_camera` ADD INDEX `Index_Notification_camera_phoneuserid` (`phoneuserid`); 
ALTER TABLE `notification_camera` ADD INDEX `Index_Notification_camera_reporttime` (`reporttime`);    


CREATE EVENT `st_delete_notification_1` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_1 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_2` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_2 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_3` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_3 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_4` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_4 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_5` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_5 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_6` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_6 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_16` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_16 where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

CREATE EVENT `st_delete_notification_camera` 
ON SCHEDULE EVERY 1 day STARTS '2007-07-20 00:00:02' 
ON COMPLETION NOT PRESERVE ENABLE 
DO Delete from notification_camera where reporttime < SUBDATE(CURDATE(),INTERVAL 12 month);

