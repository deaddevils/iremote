CREATE TABLE `serverruntimglog` (
  `serverruntimglogid` int(9) NOT NULL auto_increment,
  `onlinegatewaycount` int(9) NOT NULL,
  `time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`serverruntimglogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `phoneuserattribute` (
  `phoneuserattributeid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) NOT NULL,
  `code` varchar(64) collate utf8_bin NOT NULL,
  `value` varchar(128) collate utf8_bin default NULL,
  PRIMARY KEY  (`phoneuserattributeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


alter table zwavedeviceshare add column `cameraid` int(9) DEFAULT NULL after `infrareddeviceid`;
alter table usersharedevice add column `cameraid` int(9) DEFAULT NULL after `infrareddeviceid`;
alter table roomappliance add column `cameraid` int(9) DEFAULT NULL after `infrareddeviceid`;
alter table gatewayinfo add column `gatewaytype` varchar(128) collate utf8_bin default NULL after `qrcodekey`;


insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','facelockuser','人脸认证用户',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','facelockuser','face scanning user ',null , null);

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','lockkeyevent','您的门锁${name},检测到有人试图使用钥匙开门，请注意居家安全。',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','lockkeyevent','Your device ${name} detects that someone want to unlock your door with a key, please be cautious about home safety.',null , null);

