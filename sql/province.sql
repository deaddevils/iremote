/*
SQLyog Ultimate v8.32 
MySQL - 5.5.49 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

CREATE TABLE `province` (
  `provinceid` int(9) NOT NULL AUTO_INCREMENT,
  `regionid` int(9) NOT NULL,
  `code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '省份的名称，与系统默认语言一致',
  `admincode` varchar(64) NOT NULL COMMENT '调用api所需',
  PRIMARY KEY (`provinceid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8; 
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('1','1','alberta','Alberta','AB');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('2','1','britishcolumbia','British Columbia','BC');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('3','1','manitoba','Manitoba','MB');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('4','1','newfoundland','Newfoundland','NL');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('5','1','newbrunswick','New Brunswick','NB');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('6','1','novascotia','Nova Scotia','NS');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('7','1','ontario','Ontario','ON');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('8','1','princeedwardisland','Prince Edward Island','PE');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('9','1','quebec','Quebec','QC');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('10','1','saskatchewan','Saskatchewan','SK');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('11','1','nunavut','Nunavut','NU');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('12','1','northwestterritories','Northwest Territories','NT');
insert into `province` (`provinceid`, `regionid`, `code`, `name`, `admincode`) values('13','1','yukonterritory','Yukon Territory','YT');
