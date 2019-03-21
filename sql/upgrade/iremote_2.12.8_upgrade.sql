insert into devicefunctionversioncapability (devicetype ,functionversion,capabilitycode) values('5','0302' , '4'),('5','0302' , '5'),('5','0302' , '9');
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_46', 'zh_CN', '0', 'µ÷É«µÆ', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_46', 'en_US', '0', 'RGB LED', null, null);

CREATE TABLE `devicegroup` (
  `devicegroupid` int(9) NOT NULL auto_increment,
  `devicegroupname` varchar(128) collate utf8_bin default NULL,
  `phoneuserid` int(9) NOT NULL,
  `devicetype` varchar(32) collate utf8_bin NOT NULL,
  `icon` varchar(128) collate utf8_bin default NULL,
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`devicegroupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `devicegroupdetail` (
  `devicegroupdetailid` int(9) NOT NULL auto_increment,
  `devicegroupid` int(9) NOT NULL,
  `zwavedeviceid` int(9) NOT NULL,
  `channelids` varchar(32) collate utf8_bin default NULL,
  PRIMARY KEY  (`devicegroupdetailid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

