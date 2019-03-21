CREATE TABLE `notification_warning` (
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
  KEY `Index_Notification_deviceid` (`deviceid`),
  KEY `Index_Notification_warning_phoneuserid` (`phoneuserid`),
  KEY `Index_Notification_warning_reporttime` (`reporttime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into systemparameter(`key`, strvalue, intvalue) values("defaultdebugdeviceid", "[\"iRemote6005000000248\"]", NULL);
insert into systemparameter(`key`, strvalue, intvalue) values("defaultheartbeatwithwifi", NULL, 15);
insert into systemparameter(`key`, strvalue, intvalue) values("defaultheartbeatwithgsm", NULL, 300);

alter table zwavedevice add column createtime timestamp NULL DEFAULT NULL;
alter table zwavedevice add column lastactivetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE zwavedevice SET createtime = NOW();
UPDATE zwavedevice SET lastactivetime = NOW();

insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess1","zh_CN",0,"防区${name}离家设防成功。");
insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess3","zh_CN",0,"防区${name}在家设防成功。");
insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess1","en_US",0,"Partition ${name} Away Arm Success.");
insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess3","en_US",0,"Partition ${name} Stay Arm Success");
insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess1","fr_CA",0,"la zone armer ${name} Armer hors a la maison Le succès.");
insert into messagetemplate(`key`,language,platform,value) values("partitionarmsuccess3","fr_CA",0,"la zone armer ${name} Armer lors a la maison Le succès.");

insert into messagetemplate(`key`,language,platform,value) values("partitionpasswordwrong","zh_CN",0,"门锁用户${username}联动撤防防区${partitionname}失败");
insert into messagetemplate(`key`,language,platform,value) values("partitionpasswordwrong","en_US",0,"Door lock user ${username} disarm Partition ${partitionname} failed");
insert into messagetemplate(`key`,language,platform,value) values("partitionpasswordwrong","fr_CA",0,"Les clients de la serrure ${username} ont démantelé la Partition ${partitionname}");

ALTER TABLE phoneuser ADD COLUMN avatar VARCHAR(128) DEFAULT NULL;