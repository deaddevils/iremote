ALTER TABLE `oemproductor` ADD COLUMN `adverpicname` varchar(256) collate utf8_bin default NULL AFTER `iosappdownloadurl`;
CREATE TABLE `conditions` (
  `conditionsid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) DEFAULT NULL,
  `channelid` int(9) DEFAULT NULL,
  `devicestatus` varchar(255),
  `description` varchar(255),
  `scenedbid` int(9) NOT NULL,
  `status` int(9) NOT NULL,
  PRIMARY KEY (`conditionsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `scenenotification` (
  `scenenotificationid` int(9) NOT NULL AUTO_INCREMENT,
  `app` int(9),
  `mail` varchar(255),
  `message` varchar(255),
  `ring` int(9),
  `builder_id` int(9),
  `sound` varchar(100),
  `scenedbid` int(9) NOT NULL,
  PRIMARY KEY (`scenenotificationid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `infraredkey` (
  `infraredkeyid` int(9) NOT NULL AUTO_INCREMENT,
  `infrareddeviceid` int(9),
	`keyindex` int(9),
  `keycode` varchar(255),
  PRIMARY KEY (`infraredkeyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `phoneuser` ADD COLUMN `mail`  varchar(255) NULL;
ALTER TABLE `phoneuser` ADD COLUMN `status`  int(9) NULL;

ALTER TABLE `randcode` ADD COLUMN `mail`  varchar(255) NULL;
ALTER TABLE `randcode` MODIFY COLUMN `countrycode`  varchar(32) NULL;
ALTER TABLE `randcode` MODIFY COLUMN `phonenumber`  varchar(32)  NULL;

ALTER TABLE `devicecapability` MODIFY COLUMN `zwavedeviceid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `infrareddeviceid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `cameraid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `zwavesubdeviceid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `capabilityvalue`  varchar(255) NULL;

ALTER TABLE `command`ADD COLUMN `cameraid`  int(11) NULL;

ALTER TABLE `camera`ADD COLUMN `armstatus`  int(9) NULL DEFAULT 1;
ALTER TABLE `zwavedevice`ADD COLUMN `armstatus`  int(9) NULL DEFAULT 1;
ALTER TABLE `scene` ADD COLUMN `enablestatus`  int(9) NOT NULL DEFAULT 1;

ALTER TABLE `devicecapability`MODIFY COLUMN `zwavedeviceid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `infrareddeviceid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `cameraid`  int(11) NULL;
ALTER TABLE `devicecapability`ADD COLUMN `capabilityvalue`  varchar(255) NULL;

ALTER TABLE `deviceoperationsteps`ADD COLUMN `infrareddeviceid`  int(11) NULL;
ALTER TABLE `deviceoperationsteps`ADD COLUMN `keyindex`  int(11) NULL;

ALTER TABLE `scene` ADD COLUMN `scenenotificationid`  int(9) NULL;
ALTER TABLE `scene` ADD COLUMN `starttime`  varchar(128) NULL;
ALTER TABLE `scene` ADD COLUMN `endtime`  varchar(128) NULL;
ALTER TABLE `scene` ADD COLUMN `weekday`  int(9) NULL;
ALTER TABLE `scene` ADD COLUMN `startsecond`  int(9) NULL;
ALTER TABLE `scene` ADD COLUMN `endsecond`  int(9) NULL;

ALTER TABLE `roomappliance` ADD COLUMN `subdeviceid`  int(9) NULL;
ALTER TABLE `roomappliance` ADD COLUMN `channelid`  int(9) NULL;

ALTER TABLE `zwavedevice` ADD COLUMN `version3`  varchar(125) NULL;

ALTER TABLE `notificationsetting` ADD COLUMN `app`  int(9) NULL;
ALTER TABLE `notificationsetting` ADD COLUMN `ring`  int(9) NULL;
ALTER TABLE `notificationsetting` ADD COLUMN `mail`  varchar(125) NULL;
ALTER TABLE `notificationsetting` ADD COLUMN `builder_id`  int(9) NULL;
ALTER TABLE `notificationsetting` ADD COLUMN `sound`  varchar(125) NULL;

INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('domain_name', 'http://127.0.0.1:8080', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_server_host', 'smtp.exmail.qq.com', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_server_post', '465', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_username', 'debug@isurpass.com.cn', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_password', 'Debug@123', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_fromaddress', 'debug@isurpass.com.cn', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('user_mail_validate', 'true', NULL);

INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_server_host', 'smtp.exmail.qq.com', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_server_post', '465', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_username', 'debug@isurpass.com.cn', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_password', 'Debug@123', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_fromaddress', 'debug@isurpass.com.cn', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_validate', 'true', NULL);
INSERT INTO `systemparameter` (`key`, `strvalue`, `intvalue`) VALUES ('support_mail_toaddress', '894258497@qq.com', NULL);


insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','lockautoclose','自动回锁',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','lockautoclose','Auto lock',null , null);


insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_subject', 'zh_CN', '0', '邮箱注册', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_subject', 'en_US', '0', 'New Account Activation', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_content', 'zh_CN', '0', '<a href=\'${url}/iremote/mailuser/enableduser?${value}\'>点击这里</a> 激活账号，24小时生效，否则重新验证，请尽快激活！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_content', 'en_US', '0', '<a href=\'${url}/iremote/mailuser/enableduser?${value}\'>click here</a> Activate account, effective 24 hours, otherwise revalidate, please activate as soon as possible.！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_subject', 'zh_CN', '0', '密码找回', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_subject', 'en_US', '0', 'Reset Password', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_content', 'zh_CN', '0', '<a href=\'${url}/iremote/mailuser/resetpasswordpage?${value}\'>点击这里</a> 找回密码，24小时生效，否则重新验证！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_content', 'en_US', '0', '<a href=\'${url}/iremote/mailuser/resetpasswordpage?${value}\'>Click Here</a> to reset your password;The link expires in 24 hours.<br>', null, null);



insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_subject', 'zh_CN', '9', 'New Account Activation', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_subject', 'en_US', '9', 'New Account Activation', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_content', 'zh_CN', '9', '<a href=\'${url}/iremote/mailuser/enableduser?${value}\'>click here</a> Activate account, effective 24 hours, please activate as soon as possible.！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_content', 'en_US', '9', '<a href=\'${url}/iremote/mailuser/enableduser?${value}\'>click here</a> Activate account, effective 24 hours, please activate as soon as possible.！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_subject', 'en_US', '9', 'Reset Password', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_subject', 'zh_CN', '9', 'Reset Password', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_content', 'zh_CN', '9', '<a href=\'${url}/iremote/mailuser/resetpasswordpage?${value}\'>点击这里</a> 找回密码，24小时生效，否则重新验证！<br>', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_content', 'en_US', '0', '<a href=\'${url}/iremote/mailuser/resetpasswordpage?${value}\'>Click Here</a> to reset your password;The link expires in 24 hours.<br>', null, null);


insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','zh_CN','invation_subject','注册邀请',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','en_US','invation_subject','Registration invitation',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','zh_CN','invation_content','您的朋友${phonenumber},邀请您试用keemple，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=9',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','en_US','invation_content','Your friend ${phonenumber} invites you to trial-use \"keemple App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=9',null , null);


insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','zh_CN','invation_subject','注册邀请',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','en_US','invation_subject','Registration invitation',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','zh_CN','invation_content','您的朋友${phonenumber},邀请您试用AiSecurity，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=8',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','en_US','invation_content','Your friend ${phonenumber} invites you to trial-use \"AiSecurity App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=8',null , null);insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','zh_CN','invation_subject','注册邀请',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','en_US','invation_subject','Registration invitation',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','zh_CN','invation_content','您的朋友${phonenumber},邀请您试用keemple，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=9',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('8','en_US','invation_content','Your friend ${phonenumber} invites you to trial-use \"keemple App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=9',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','zh_CN','invation_subject','注册邀请',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','en_US','invation_subject','Registration invitation',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','zh_CN','invation_content','您的朋友${phonenumber},邀请您试用AiSecurity，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=8',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('9','en_US','invation_content','Your friend ${phonenumber} invites you to trial-use \"AiSecurity App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=8',null , null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('dscchannel', 'zh_CN', '9', '开关', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('dscchannel', 'en_US', '9', 'Zone ', null, null);

CREATE TABLE `securitypartition` (
  `partitionid` int(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` int(9) DEFAULT NULL COMMENT '主人账号ID',
  `name` varchar(256) DEFAULT NULL COMMENT '防区名称',
  `dsczwavedeviceid` int(9) DEFAULT NULL COMMENT '本防区关联的DSC设备的ID',
  `dscpartitionid` int(9) DEFAULT NULL COMMENT '本防区对应于DSC的防区ID',
  `armstatus` int(9) DEFAULT NULL COMMENT '0：撤防1：离家设防3：在家设防4：正在把设防模式切换为离家设防模式5：正在把设防模式切换为在家设防模式',
  `warningstatus` int(9) DEFAULT NULL COMMENT '0：正常 1:告警',
  PRIMARY KEY (`partitionid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
ALTER TABLE `zwavesubdevice` ADD COLUMN `subdevicetype`  VARCHAR(9) NULL;


alter table zwavesubdevice add partitionid int(9) default NULL;
ALTER TABLE securitypartition DROP COLUMN phoneuserid;
alter table securitypartition add `status` int(9) DEFAULT '0';

ALTER TABLE securitypartition MODIFY COLUMN armstatus INT(9) DEFAULT 0;
ALTER TABLE securitypartition MODIFY COLUMN warningstatus INT(9) DEFAULT 0;

ALTER TABLE zwavesubdevice ADD COLUMN status INT(9);
ALTER TABLE zwavesubdevice ADD COLUMN warningstatuses VARCHAR(32);
ALTER TABLE zwavesubdevice ADD COLUMN  `enablestatus` INT(9) DEFAULT '0';

ALTER TABLE securitypartition ADD phoneuserid INT(9) DEFAULT NULL AFTER NAME;
ALTER TABLE securitypartition ADD delay INT(9) DEFAULT NULL AFTER STATUS;
ALTER TABLE securitypartition ADD password VARCHAR(256) DEFAULT NULL AFTER delay;

ALTER TABLE camera ADD partitionid INT(9) DEFAULT NULL AFTER armstatus;

ALTER TABLE zwavedevice ADD partitionid INT(9) DEFAULT NULL AFTER version3;

CREATE TABLE `timertask` (
  `timertaskid` int(9) NOT NULL AUTO_INCREMENT,
  `type` int(9) NOT NULL,
  `deviceid` varchar(64) DEFAULT NULL,
  `objid` int(9) DEFAULT NULL,
  `jsonpara` varchar(1024) DEFAULT NULL,
  `excutetime` datetime NOT NULL,
  `expiretime` datetime DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`timertaskid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE deviceoperationsteps ADD COLUMN objid INT(9);

INSERT INTO messagetemplate(`key`, LANGUAGE, platform,VALUE) VALUES("armsuccessdevicefailed","zh_CN",0,"设防成功，Z-wave 设备${name}设防失败，注意居家安全。"),("armsuccessdevicefailed","en_US",0,"Arm Success.Z-wave Devices ${name} Arm Failure, please check if ther device is in place, pay attention to home safety."),("armsuccessdevicefailed","fr_CA",0,"Si la défense échoue, veuillez vérifier si l'équipement ${name} est en place et faites attention à la sécurité à la maison.");


INSERT INTO messagetemplate(`key`, LANGUAGE, platform,VALUE, alitemplatecode, alitemplateparam) VALUES
("dooropendelaywarning","en_US",0,"Your device ${name} has detected a door is open. Exercise caution.",NULL,NULL),
("dooropendelaywarning","zh_CN",0,"你的设备${name},检查到门被打开，请注意居家安全。","SMS_35135038","{\"alarmmessage\":\"门被打开\"}"),
("dooropendelaywarning","fr_CA",0,"Votre appareil${name},Vérifiez que la porte est ouverte,S''il vous plaît prêter attention à la sécurité à la maison",NULL,NULL),
("moveindelaywarning","en_US",0,"Your device ${name} has detected someone is entering your premises. Exercise caution.",NULL,NULL),
("moveindelaywarning","zh_CN",0,"你的设备${name},检查到有人进入，请注意居家安全。","SMS_35135038","{\"alarmmessage\":\"有人进入\"}"),
("moveindelaywarning","fr_CA",0,"Votre appareil${name}Cochez pour voir quelqu'un entrer,S'il vous plaît prêter attention à la sécurité à la maison",NULL,NULL),
("doorlockopendelaywarning","en_US",0,"${name} A door has been unlocked. Exercise caution.",NULL,NULL),
("doorlockopendelaywarning","zh_CN",0,"您的门锁${name},已经被打开，请注意居家安全。","SMS_35235005","{}"),
("doorlockopendelaywarning","fr_CA",0,"Votre serrure de porte${name},a été ouvert，S'il vous plaît prêter attention à la sécurité à la maison",NULL,NULL);

CREATE TABLE `doorlockassociation` (
  `doorlockassociationid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `usercode` int(9) NOT NULL,
  `objtype` int(9) NOT NULL,
  `objid` int(9) DEFAULT NULL,
  `appendmessage` varchar(1024) DEFAULT NULL,
  `creattime` datetime NOT NULL,
  PRIMARY KEY (`doorlockassociationid`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

INSERT INTO systemparameter (`key`,strvalue) VALUES('leedarsonproductor','["030002000009"]');