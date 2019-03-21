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


alter table phoneuser add column `name` varchar(256) COLLATE utf8_bin default NULL after phonenumber ;
ALTER TABLE `remote` ADD COLUMN `remotetype` INT(9) DEFAULT 0 NOT NULL AFTER `timezoneid`; 

ALTER TABLE `messagetemplate` ADD COLUMN `alitemplatecode` varchar(256) COLLATE utf8_bin DEFAULT NULL AFTER `value`; 
ALTER TABLE `messagetemplate` ADD COLUMN `alitemplateparam` varchar(512) COLLATE utf8_bin DEFAULT NULL AFTER `alitemplatecode`; 

ALTER TABLE `td_eventforthirdpart` change COLUMN `objparam` `objparam` varchar(8192) collate utf8_bin default NULL; 
