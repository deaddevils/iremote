insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','poweroverload','您的设备${name}，发出了电流过载报警，请注意居家安全',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','poweroverloadshort','${name},power overload ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','poweroverloadshort','${name},电流过载',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','poweroverload','${name} were over loaded, Please Caution.',null , null);

ALTER TABLE `roomappliance` ADD COLUMN `zwavedeviceid` int(9) default NULL AFTER `roomdbid`;
ALTER TABLE `roomappliance` ADD COLUMN `infrareddeviceid` int(9) default NULL AFTER `zwavedeviceid`;
UPDATE roomappliance ra SET zwavedeviceid = ( SELECT zwavedeviceid FROM zwavedevice zd WHERE ra.`deviceid`=  zd.`deviceid` AND ra.`applianceid` = zd.`applianceid`);
UPDATE roomappliance ra SET infrareddeviceid = ( SELECT infrareddeviceid FROM infrareddevice id WHERE ra.`deviceid`=  id.`deviceid` AND ra.`applianceid` = id.`applianceid`);

alter table associationscene add column  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL after `zwavedeviceid`;
alter table card add column  `cardtype` INT(9) NULL after `cardsequence`;
alter table zwavedevice add column  `functionversion` varchar(32) collate utf8_bin default NULL after `version2`;

alter table zwavedeviceshare add column  `username` varchar(128) collate utf8_bin default NULL after `touser`;
alter table zwavedeviceshare add column  `token` varchar(128) collate utf8_bin default NULL after `infrareddeviceid`;
alter table zwavedeviceshare add column  `securitycode` varchar(32) collate utf8_bin default NULL after `token`;

