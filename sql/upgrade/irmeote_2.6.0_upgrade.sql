CREATE TABLE `tp_communityadministrator` (                    
     `id` int(9) NOT NULL AUTO_INCREMENT,                        
     `thirdpartid` int(9) NOT NULL,                              
     `communityid` int(9) DEFAULT NULL,                          
     `phoneuserid` int(9) NOT NULL,                              
     `logicname` varchar(64) NOT NULL,                           
     `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
     PRIMARY KEY (`id`)                                          
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;  
   
   

CREATE TABLE `zwavedeviceshare` (                             
    `id` int(9) NOT NULL AUTO_INCREMENT,                        
    `touserid` int(9) NOT NULL,                                 
    `touser` varchar(64) DEFAULT NULL,                          
    `deviceid` varchar(64) NOT NULL,                            
    `zwavedeviceid` int(9) DEFAULT NULL,                        
    `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
    PRIMARY KEY (`id`)                                          
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;
  

CREATE TABLE `operationlog` (
   `operationlogid` int(9) NOT NULL auto_increment,
   `username` varchar(128) collate utf8_bin default NULL,
   `userip` varchar(32) collate utf8_bin default NULL,
   `requesturl` varchar(128) collate utf8_bin default NULL,
   `requestdata` text collate utf8_bin,
   `result` text collate utf8_bin,
   `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
   PRIMARY KEY  (`operationlogid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
 
 
 CREATE TABLE `room` (
   `roomdbid` int(9) NOT NULL auto_increment,
   `roomid` varchar(128) collate utf8_unicode_ci default NULL,
   `phoneuserid` int(9) NOT NULL,
   `phonenumber` varchar(32) collate utf8_unicode_ci NOT NULL,
   `name` varchar(128) collate utf8_unicode_ci NOT NULL,
   PRIMARY KEY  (`roomdbid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
CREATE TABLE `roomappliance` (
   `roomapplianceid` int(9) NOT NULL auto_increment,
   `roomdbid` int(9) NOT NULL,
   `deviceid` varchar(64) collate utf8_unicode_ci NOT NULL,
   `applianceid` varchar(64) collate utf8_unicode_ci default NULL,
   `majortype` varchar(32) collate utf8_unicode_ci default NULL,
   `devicetype` varchar(32) collate utf8_unicode_ci default NULL,
   `nuid` int(9) default NULL,
   `name` varchar(64) collate utf8_unicode_ci default NULL,
   PRIMARY KEY  (`roomapplianceid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
 
CREATE TABLE `timezone` (
   `timezoneid` int(9) NOT NULL auto_increment,
   `zonetext` varchar(64) collate utf8_unicode_ci default NULL,
   `zoneid` varchar(64) collate utf8_unicode_ci default NULL,
   PRIMARY KEY  (`timezoneid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
 
CREATE TABLE `timer` (
   `timerid` int(9) NOT NULL auto_increment,
   `zwavedeviceid` int(9) default NULL,
   `infrareddeviceid` int(9) default NULL,
   `weekday` int(9) NOT NULL,
   `time` int(9) NOT NULL,
   `infraredcodebase64` varchar(256) collate utf8_unicode_ci default NULL,
   `zwavecommandbase64` varchar(256) collate utf8_unicode_ci default NULL,
   `description` varchar(256) collate utf8_unicode_ci default NULL,
   `valid` int(9) NOT NULL default '1',
   `executor` int(9) NOT NULL default '0',
   PRIMARY KEY  (`timerid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
CREATE TABLE `td_zwavedeviceeventpushvalues` (
   `zwavedeviceeventpushvaluesid` int(9) NOT NULL auto_increment,
   `zwavedeviceid` int(9) NOT NULL,
   `metervalue` float(9,2) NOT NULL,
   `floatappendparam` float(9,2) default NULL,
   `createtime` datetime default NULL,
   `lastsendtime` datetime default NULL,
   PRIMARY KEY  (`zwavedeviceeventpushvaluesid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
ALTER TABLE `phoneuser`  ADD COLUMN `usertype`  int(9) default '0' AFTER `token`;
ALTER TABLE `thirdparttoken`  ADD COLUMN `thirdpartid` int(9) DEFAULT NULL AFTER `thirdparttokenid`;
ALTER TABLE `zwavedevice`  ADD COLUMN `fstatus` float default NULL AFTER `status`;
ALTER TABLE `zwavedevice`  ADD COLUMN `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL AFTER `nuid`;
ALTER TABLE `zwavedevice`  ADD COLUMN `deleteable` int(9) default 1 AFTER `enablestatus`;

ALTER TABLE `remote`  ADD COLUMN `powertype` int(9) default '0' AFTER `networkintensity`;
ALTER TABLE `remote`  ADD COLUMN `battery` int(9) default NULL AFTER `powertype`;
ALTER TABLE `remote`  ADD COLUMN `temperature` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL AFTER `battery`;
ALTER TABLE `remote`  ADD COLUMN `secritykeybase64` varchar(256) DEFAULT NULL AFTER `secritykey`;
ALTER TABLE `remote`  ADD COLUMN `zwavescuritykeybase64` varchar(256) DEFAULT NULL AFTER `zwavescuritykey`;
ALTER TABLE `remote`  ADD COLUMN `timezoneid` int(9) default NULL AFTER `temperature`;


ALTER TABLE `command`  ADD COLUMN `infraredcodebase64` varchar(512) DEFAULT NULL AFTER `infraredcode`;
ALTER TABLE `command`  ADD COLUMN `zwavecommandbase64` varchar(512) DEFAULT NULL AFTER `zwavecommand`;

alter table thirdpart add column `platform` int(9) not null default 0 after password ;
alter table thirdpart add column `name` varchar(64) COLLATE utf8_bin DEFAULT NULL after code ;

ALTER TABLE `infrareddevice`  ADD COLUMN `codeid` VARCHAR(64) NULL AFTER `name`;
ALTER TABLE `infrareddevice`  ADD COLUMN `codeliberyjson` VARCHAR(512) NULL AFTER `codeid`;
ALTER TABLE `infrareddevice`  ADD COLUMN `productorid` VARCHAR(128) NULL AFTER `codeliberyjson`;
ALTER TABLE `infrareddevice`  ADD COLUMN `controlmodeid` VARCHAR(64) NULL AFTER `productorid`;
ALTER TABLE `infrareddevice`  ADD COLUMN `codeindex` INT(9) NULL AFTER `controlmodeid`;

alter table td_eventforthirdpart add column `floatparam` float default NULL after intparam ;

ALTER TABLE `notification`  ADD COLUMN `zwavedeviceid` int(9) DEFAULT NULL AFTER `nuid`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','passworderror5times','您的门锁%s,检测到连续5次密码错误，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','lockkeyerror','您的门锁%s,检测到不匹配的钥匙插入，请注意居家安全。');
