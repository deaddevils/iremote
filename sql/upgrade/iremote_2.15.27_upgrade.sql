insert into messagetemplate(`key`, language, platform, value) values("setpartitionarmstatusfailed", "zh_CN",0,"情景联动防区${partitionname}操作失败");
insert into messagetemplate(`key`, language, platform, value) values("setpartitionarmstatusfailed", "en_US",0,"Scene associated Partition ${partitionname} failed");

ALTER TABLE `iremote`.`timer` ADD COLUMN `timetype` INT(9) DEFAULT 0;

ALTER TABLE `iremote`.`doorlockpassword` ADD COLUMN `tid` varchar(256);

-- 默认语言类型根据实际情况填写,国内服务器填 zh_CN
INSERT INTO systemparameter(`key`, strvalue) VALUES("defaultlanguage", "en_US");

insert into messagetemplate (`key`,language,platform,value) values('deviceauthrizeshorturl','zh_CN',0,'您获得授权，从${validfrom}到${validthrough}，您可以打开${name}的门锁,开锁链接${url}');
insert into messagetemplate (`key`,language,platform,value) values('deviceauthrizeshorturl','en_US',0,'You are authorized. You can unlock ${name} lock from ${validfrom} to ${validthrough}. Unlock link ${url}');

CREATE TABLE `address` (
  `addressid` INT(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` INT(9) DEFAULT NULL,
  `regionid` INT(9) DEFAULT NULL COMMENT '国家或地区主键',
  `provinceid` INT(9) DEFAULT NULL COMMENT '省份主键',
  `cityid` INT(9) DEFAULT NULL COMMENT '用户所在城市主键',
  `createtime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`addressid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
CREATE TABLE `city` (
  `cityid` int(9) NOT NULL AUTO_INCREMENT,
  `provinceid` int(9) NOT NULL,
  `code` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '城市的名称，与系统默认语言一致',
  `locationkey` varchar(32) DEFAULT NULL COMMENT '调用api所需',
  PRIMARY KEY (`cityid`)
) ENGINE=InnoDB AUTO_INCREMENT=4096 DEFAULT CHARSET=utf8;
CREATE TABLE `province` (
  `provinceid` int(9) NOT NULL AUTO_INCREMENT,
  `regionid` int(9) NOT NULL,
  `code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '省份的名称，与系统默认语言一致',
  `admincode` varchar(64) NOT NULL COMMENT '调用api所需',
  PRIMARY KEY (`provinceid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
CREATE TABLE `region` (
  `regionid` int(9) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL,
  `countrycode` varchar(16) NOT NULL COMMENT '调用api所需参数',
  PRIMARY KEY (`regionid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;