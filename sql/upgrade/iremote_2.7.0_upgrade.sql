CREATE TABLE `doorlockuser` (
   `doorlockuserid` int(9) NOT NULL auto_increment,
   `zwavedeviceid` int(9) NOT NULL,
   `usertype` int(9) NOT NULL,
   `usercode` int(9) NOT NULL,
   `username` varchar(256) collate utf8_bin NOT NULL,
   PRIMARY KEY  (`doorlockuserid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
CREATE TABLE `devicecapability` (
   `devicecapabilityid` int(11) NOT NULL auto_increment,
   `zwavedeviceid` int(11) NOT NULL,
   `capabilitycode` int(11) NOT NULL,
   PRIMARY KEY  (`devicecapabilityid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
 
 CREATE TABLE `doorlockpassword` (
   `doorlockpasswordid` int(9) NOT NULL auto_increment,
   `zwavedeviceid` int(9) NOT NULL,
   `usercode` int(9) NOT NULL,
   `password` varchar(256) collate utf8_bin NOT NULL,
   `validfrom` datetime NOT NULL,
   `validthrough` datetime NOT NULL,
   `synstatus` int(9) NOT NULL,
   `status` int(9) NOT NULL,
   `errorcount` int(9) NOT NULL default '0',
   `createtime` datetime NOT NULL,
   `sendtime` datetime default NULL,
   PRIMARY KEY  (`doorlockpasswordid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
 
 ALTER TABLE `zwavedevice`  ADD COLUMN `warningstatuses` varchar(32) collate utf8_bin default NULL AFTER `shadowstatus`;
 ALTER TABLE `notification`    CHANGE `devicetype` `devicetype` VARCHAR(64) default NULL ;
 ALTER TABLE `zwavedevice`     CHANGE `devicetype` `devicetype` VARCHAR(64) default NULL;
 
ALTER TABLE `scene` CHANGE `sceneid` `sceneid` VARCHAR(128) NOT NULL;
ALTER TABLE `command` ADD COLUMN `zwavecommandsbase64` VARCHAR(512) NULL AFTER `zwavecommandbase64`;
ALTER TABLE `infrareddevice` ADD COLUMN `statuses` varchar(32) COLLATE utf8_bin DEFAULT NULL AFTER `codeindex`;


