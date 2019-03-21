CREATE TABLE `deviceindentifyinfo` (
  `deviceindentifyinfoid` int(9) NOT NULL auto_increment,
  `devicetype` varchar(32) collate utf8_bin default NULL,
  `devicecode` varchar(32) collate utf8_bin default NULL,
  `qrcodeid` varchar(128) collate utf8_bin default NULL,
  `manufacturer` varchar(64) collate utf8_bin default NULL,
  `model` varchar(64) collate utf8_bin default NULL,
  `zwavedeviceid` int(9) default NULL,
  PRIMARY KEY  (`deviceindentifyinfoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `gatewaycapability` (
  `gatewaycapabilityid` int(9) NOT NULL auto_increment,
  `deviceid` varchar(64) collate utf8_bin NOT NULL,
  `capabilitycode` int(9) NOT NULL,
  PRIMARY KEY  (`gatewaycapabilityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


ALTER TABLE `timer` ADD COLUMN `commandjson` text NULL AFTER `excutetime`;

ALTER TABLE `associationscene` ADD COLUMN `description` varchar(256) COLLATE utf8_bin DEFAULT NULL AFTER `zwavedeviceid`;
ALTER TABLE `zwavedeviceshare` ADD COLUMN `username` varchar(128) collate utf8_bin default NULL AFTER `touser`;
ALTER TABLE `zwavedeviceshare` ADD COLUMN `token` varchar(128) collate utf8_bin default NULL AFTER `infrareddeviceid`;
ALTER TABLE `zwavedeviceshare` ADD COLUMN `securitycode` varchar(32) collate utf8_bin default NULL AFTER `token`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','deviceauthrize','您获得授权，从${validfrom}到${validthrough}，您可以使用链接${url}，打开${name}的门锁',null , null);

