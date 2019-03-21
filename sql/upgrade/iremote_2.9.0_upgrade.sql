alter table doorlockuser add column `cardid` int(9) NOT NULL default 0 after username ;

CREATE TABLE `card` (
  `cardid` int(9) NOT NULL auto_increment,
  `thirdpartid` int(9) default '0',
  `sha1key` varchar(256) collate utf8_bin NOT NULL,
  `cardsequence` int(9) default '0',
  PRIMARY KEY  (`cardid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `gatewayinfo` (
  `gatewayinfoid` int(9) NOT NULL auto_increment,
  `deviceid` varchar(128) collate utf8_bin NOT NULL,
  `qrcodekey` varchar(128) collate utf8_bin default NULL,
  PRIMARY KEY  (`gatewayinfoid`),
  UNIQUE KEY `Index_GatewayInfo_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table zwavedevice change column `statuses` `statuses` varchar(256) COLLATE utf8_bin DEFAULT NULL;

ALTER TABLE `timer` ADD COLUMN `scenedbid` INT(9) NULL AFTER `executor`;
ALTER TABLE `timer` ADD COLUMN `scenetype` INT(9) DEFAULT 3 NOT NULL AFTER `scenedbid`;

ALTER TABLE `scene` ADD COLUMN `thirdpartid` INT(9) NULL AFTER `sceneid`;
ALTER TABLE `scene`ADD COLUMN `scenetype` INT(9) DEFAULT 1 NOT NULL AFTER `thirdpartid`;

ALTER TABLE `associationscene`ADD COLUMN `scenetype` INT(11) DEFAULT 2  NOT NULL AFTER `zwavedeviceid`;
ALTER TABLE `associationscene`ADD COLUMN `devicestatus` VARCHAR(32) NULL AFTER `scenetype`;
ALTER TABLE `associationscene`ADD COLUMN `scenedbid` INT(11) NULL AFTER `devicestatus`;
ALTER TABLE `associationscene`ADD COLUMN `operator` VARCHAR(32) NULL AFTER `scenedbid`;

ALTER TABLE `command` ADD COLUMN `commandjson` TEXT COLLATE utf8_bin AFTER `description`;
ALTER TABLE `command`  ADD COLUMN `zwavedeviceid` INT(9) NULL AFTER `deviceid`;
ALTER TABLE `command`  ADD COLUMN `launchscenedbid` INT(9) NULL AFTER `commandjson`;
ALTER TABLE `command`  ADD COLUMN `infrareddeviceid` INT(9) NULL AFTER `zwavedeviceid`;
  
ALTER TABLE associationscene change column `zwavedeviceid` `zwavedeviceid` int(11) DEFAULT NULL;
  
UPDATE `associationscene` SET zwavedeviceid = NULL  WHERE NOT EXISTS ( SELECT * FROM zwavedevice z WHERE associationscene.zwavedeviceid = z.`zwavedeviceid`);
UPDATE timer t SET zwavedeviceid = NULL WHERE NOT EXISTS ( SELECT * FROM zwavedevice z WHERE t.`zwavedeviceid` = z.`zwavedeviceid` );
UPDATE timer t SET infrareddeviceid = NULL WHERE NOT EXISTS ( SELECT * FROM infrareddevice z WHERE t.`infrareddeviceid` = z.`infrareddeviceid` );
UPDATE command c SET zwavedeviceid = ( SELECT zwavedeviceid FROM zwavedevice z WHERE c.`deviceid` = z.`deviceid` AND c.`applianceid` = z.`applianceid`);
UPDATE command c SET infrareddeviceid = ( SELECT infrareddeviceid FROM infrareddevice z WHERE c.`deviceid` = z.`deviceid` AND c.`applianceid` = z.`applianceid`);
UPDATE command c SET c.`associationsceneid` = NULL WHERE c.associationsceneid IS NOT NULL AND  NOT EXISTS ( SELECT * FROM associationscene ass WHERE c.associationsceneid = ass.`associationsceneid`);

ALTER TABLE `doorlockuser`   
  ADD COLUMN `cardsequence` int(9) default '0' AFTER `username`;
  
ALTER TABLE `doorlockuser`   
  ADD COLUMN `validfrom` DATETIME NULL AFTER `cardsequence`;
  
ALTER TABLE `doorlockuser`   
  ADD COLUMN `validthrough` DATETIME NULL AFTER `validfrom`;
  
ALTER TABLE `doorlockuser`   
  ADD COLUMN `alarmtype` INT DEFAULT 1  NOT NULL AFTER `validthrough`;

CREATE TABLE `doorlockalarmphone` (
  `doorlockalarmphoneid` int(11) NOT NULL auto_increment,
  `doorlockuserid` int(11) NOT NULL,
  `countrycode` varchar(10) collate utf8_bin default NULL,
  `alarmphone` varchar(30) character set latin1 default NULL,
  PRIMARY KEY  (`doorlockalarmphoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `zwavedevice`  ADD COLUMN `productor` varchar(32) collate utf8_bin default NULL AFTER `enablestatus`;
ALTER TABLE `zwavedevice`  ADD COLUMN `model` varchar(32) collate utf8_bin default NULL AFTER `productor`;
ALTER TABLE `zwavedevice`  ADD COLUMN `version1` varchar(32) collate utf8_bin default NULL AFTER `model`;
ALTER TABLE `zwavedevice`  ADD COLUMN `version2` varchar(32) collate utf8_bin default NULL AFTER `version1`;

CREATE TABLE `deviceupgradepackage` (
  `deviceupgradepackageid` int(9) NOT NULL auto_increment,
  `devicetype` varchar(32) collate utf8_bin NOT NULL,
  `productor` varchar(32) collate utf8_bin default NULL,
  `model` varchar(32) collate utf8_bin NOT NULL,
  `version1` varchar(32) collate utf8_bin default NULL,
  `version2` varchar(32) collate utf8_bin default NULL,
  `packagepath` varchar(512) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`deviceupgradepackageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `deviceversioninfo` (
  `deviceversioninfoid` int(9) NOT NULL auto_increment,
  `version` varchar(32) collate utf8_bin NOT NULL,
  `productor` varchar(32) collate utf8_bin NOT NULL,
  `model` varchar(32) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`deviceversioninfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `infrereddeviceproductor` (
  `infrereddeviceproductorid` int(9) NOT NULL auto_increment,
  `productor` varchar(128) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`infrereddeviceproductorid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `infrereddeviceproductorcodemap` (
  `infrereddeviceproductorcodemapid` int(9) NOT NULL auto_increment,
  `productor` varchar(128) collate utf8_bin NOT NULL,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  `codeids` text collate utf8_bin NOT NULL,
  PRIMARY KEY  (`infrereddeviceproductorcodemapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `infrereddeviceproductormap` (
  `infrereddeviceproductormapid` int(9) NOT NULL auto_increment,
  `productor` varchar(128) collate utf8_bin NOT NULL,
  `productormap` varchar(128) collate utf8_bin NOT NULL,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`infrereddeviceproductormapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `infreredcodeliberay` (
  `infreredcodeliberayid` int(9) NOT NULL auto_increment,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  `codeid` int(9) NOT NULL,
  `code` varchar(512) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`infreredcodeliberayid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


insert into devicecapability(zwavedeviceid , capabilitycode ) select zwavedeviceid  , 1 from zwavedevice zd where devicetype = 5 and not exists ( select zwavedeviceid from devicecapability dp where dp.capabilitycode = 1 and dp.zwavedeviceid = zd.zwavedeviceid);
insert into devicecapability(zwavedeviceid , capabilitycode ) select zwavedeviceid  , 4 from zwavedevice zd where devicetype = 5 and not exists ( select zwavedeviceid from devicecapability dp where dp.capabilitycode = 4 and dp.zwavedeviceid = zd.zwavedeviceid);

/*com.iremote.action.scens.AssociationDataUpgrade*/
/*com.iremote.action.scens.TimerDataUpgrade*/

ALTER TABLE `usershare`   
  ADD COLUMN `sharedevicetype` INT(1) DEFAULT 0  NULL AFTER `sharetype`;
  
CREATE TABLE `usersharedevice`(  
  `usersharedeviceid` INT(9) NOT NULL auto_increment,
  `usershareid` INT(9) NOT NULL,
  `zwavedeviceid` INT(9),
  `infrareddeviceid` INT(9),
  PRIMARY KEY  (`usersharedeviceid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;