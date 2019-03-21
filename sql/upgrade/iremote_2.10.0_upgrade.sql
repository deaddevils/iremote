CREATE TABLE `zwavesubdevice`(  
  `zwavesubdeviceid` INT NOT NULL AUTO_INCREMENT,
  `channelid` int(9) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `zwavedeviceid` int(9) NOT NULL,
  PRIMARY KEY (`zwavesubdeviceid`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `usersharedevice`(  
  `usersharedeviceid` INT(9) NOT NULL auto_increment,
  `usershareid` int(9) NOT NULL,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `infrareddeviceid` int(9) DEFAULT NULL,
  `zwavedeviceshareid` int(9) DEFAULT NULL,
  PRIMARY KEY  (`usersharedeviceid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `deviceextendinfo` (
  `deviceextendinfoid` int(9) NOT NULL auto_increment,
  `zwavedeviceid` int(9) default NULL,
  `zwaveproductormessage` text collate utf8_bin,
  PRIMARY KEY  (`deviceextendinfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `deviceversioncapablity` (
  `deviceversioncapabilityid` int(9) NOT NULL auto_increment,
  `capabilitycode` int(9) NOT NULL,
  `deviceversioninfoid` int(9) NOT NULL,
  PRIMARY KEY  (`deviceversioncapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `devicefunctionversioncapability` (
  `devicefunctionversioncapabilityid` int(9) NOT NULL auto_increment,
  `devicetype` varchar(32) collate utf8_bin NOT NULL,
  `functionversion` varchar(32) collate utf8_bin NOT NULL,
  `capabilitycode` int(9) NOT NULL,
  PRIMARY KEY  (`devicefunctionversioncapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table zwavedeviceshare add column `infrareddeviceid` int(9) DEFAULT NULL after zwavedeviceid ;
alter table zwavedeviceshare add column `shareowntype` int(9) DEFAULT 0 after infrareddeviceid ;
alter table usershare add column `sharedevicetype` int(9) DEFAULT 0 after sharetype ;
alter table usersharedevice add column `zwavedeviceshareid` int(9) DEFAULT NULL after infrareddeviceid ;

alter table zwavedevice add column `functionversion` varchar(32) collate utf8_bin default NULL after version2 ;


ALTER TABLE `timer` ADD COLUMN `excutetime` VARCHAR(15) NULL AFTER `scenetype`;
  
ALTER TABLE `card` ADD COLUMN `cardtype` INT(9) NULL AFTER `cardsequence`;
  
 insert into deviceversioninfo (version , productor , model) values ( '002','10','1'),( '001','10','01');
insert into deviceversioncapablity(deviceversioninfoid , capabilitycode) values (1,4),(1,5),(1,6),(1,7),(1,9),(1,10),(1,11),(2,4),(2,5),(2,6),(2,7),(2,9),(2,10),(2,11);
insert into devicefunctionversioncapability(devicetype , functionversion , capabilitycode) values ( '5','0102',5),( '5','0102',6),( '5','0102',7),( '5','0102',9),( '5','0102',10),( '5','0102',11);


