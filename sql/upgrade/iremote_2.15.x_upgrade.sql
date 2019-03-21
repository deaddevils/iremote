
ALTER TABLE usershare ADD COLUMN usercode INT(9);

insert into oemproductor(name,platform,pushmasterkey,pushappkey,smssign,deviceprefix,lechangeappid,lechangeappSecret,lechangeadmin,androidappdownloadurl,iosappdownloadurl,adverpicname) values("西屋安防 A",14,"o9QxQyX4Oj8SzQwZImxYh6TfM3ZJtaUyNM6hxkMX38I=","GEJLXbvAuo65UJF8fai6PiaHyFFhTbIUzvsuNige0pM=","【小白管家】","[\"iRemote3013\"]",NULL,NULL,NULL,NULL,NULL,"[\"1.jpg\"]");

insert into systemparameter (`key`,strvalue) values('standardzwaveproductor','["012980021000"]');



CREATE TABLE `advertbanner` (
  `advertbannerid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `thirdpartid` int(9) DEFAULT NULL,
  `isdefault` int(9) NOT NULL COMMENT '是否为默认广告栏，默认为0,0：不是默认广告栏,1：是默认广告栏;每个第三方平台只能指定一个默认广告栏，当没有为其某个用户指定广告栏时，该用户显示默认广告栏',
  `description` varchar(1024) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`advertbannerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `advertbannerpic` (
  `advertbannerpicid` int(11) NOT NULL AUTO_INCREMENT,
  `advertbannerid` int(11) NOT NULL COMMENT '所属广告栏ID',
  `url` varchar(512) NOT NULL COMMENT '广告图片URL',
  `picindex` int(9) NOT NULL COMMENT '序号，有多张广告图片时，按序号顺序显示',
  `description` varchar(1024) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`advertbannerpicid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*广告表advertbanner、advertbannerpic数据插入模板
INSERT INTO advertbanner (advertbannerid,`name`,thirdpartid,isdefault,description,createtime) VALUES(2,"主页",27,0,"广告栏",NOW());
INSERT INTO advertbannerpic (advertbannerid,url,picindex,description,createtime)VALUES(2,"https://dev.isurpass.com.cn/iremotestatic/uploadfile/advertbanner/title2.png",1,"测试图片",NOW());
*/

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


ALTER TABLE `iremote`.`timer` ADD COLUMN `timetype` INT(9) DEFAULT 0;


ALTER TABLE doorlockpassword MODIFY COLUMN `password` VARCHAR(2048);
ALTER TABLE doorlockpassword ADD COLUMN tid VARCHAR(256) DEFAULT NULL;
ALTER TABLE doorlockpassword ADD COLUMN locktype INT(9) DEFAULT 0;

ALTER TABLE doorlockpassword ADD COLUMN `username` VARCHAR(256)  DEFAULT NULL AFTER phonenumber;

ALTER TABLE `deviceoperationsteps` CHANGE `appendmessage` `appendmessage` VARCHAR(4096) CHARSET utf8 COLLATE utf8_bin NULL;


ALTER TABLE oemproductor ADD COLUMN `abroadlechangeappid` VARCHAR(256) COLLATE utf8_bin DEFAULT NULL;
ALTER TABLE oemproductor ADD COLUMN `abroadlechangeappSecret` VARCHAR(256) COLLATE utf8_bin DEFAULT NULL;

update oemproductor set abroadlechangeappid="7tUrvAMytuFsBs5ESpCVpGKF+0/yEzBKA/ChO6bdtmI=",abroadlechangeappSecret="4Asj3bcKbGTdU01V2RAjHTzacR0rcQwb+uG1F8131Kg=" where oemproductorid = 7;

ALTER TABLE `iremote`.`timertask` ADD UNIQUE INDEX (`type`, `deviceid`, `objid`);
CREATE TABLE `bluetoothpassword` (
  `bluetoothpasswordid` int(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` int(9) NOT NULL,
  `phoneuserid` int(9) NOT NULL,
  `password` varchar(2048) NOT NULL,
  `validfrom` datetime DEFAULT NULL,
  `validthrought` datetime DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`bluetoothpasswordid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into messagetemplate (`key`,language,platform,value) values('sharerequest','en_US',17,'Your friend ${phonenumber} has shared with you the access to his "ARK App."');
insert into messagetemplate (`key`,language,platform,value) values('sharerequest','zh_CN',17,'您的朋友${phonenumber},把他的佳承智慧共享给您了。');

ALTER TABLE `iremote`.`conditions` ADD COLUMN `deviceid` VARCHAR(64) NULL AFTER `status`, ADD COLUMN `operator` VARCHAR(16) NULL AFTER `deviceid`, ADD COLUMN `statusesindex` INT(9) NULL AFTER `operator`;
ALTER TABLE conditions MODIFY status FLOAT NOT NULL;

CREATE TABLE `iremote`.`devicerawcmd`(
  `devicerawcmdid` INT(9) NOT NULL AUTO_INCREMENT,
  `zwavedeviceid` INT(9) NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `cmdindex` INT(9) NOT NULL,
  `cmdtype` INT(9) NOT NULL,
  `rawcmd` VARCHAR(2048) NOT NULL,
  `createtime` DATETIME NOT NULL,
  PRIMARY KEY (`devicerawcmdid`)
);

CREATE TABLE `iremote`.`rawcmdtemplate`(
  `rawcmdtemplateid` INT(9) NOT NULL AUTO_INCREMENT,
  `phoneuserid` INT(9),
  `name` VARCHAR(256) NOT NULL,
  `templatetype` VARCHAR(256),
  `createtime` DATETIME NOT NULL,
  PRIMARY KEY (`rawcmdtemplateid`)
);

CREATE TABLE `iremote`.`rawcmdtemplatedtl`(
  `rawcmdtemplatedtlid` INT(9) NOT NULL AUTO_INCREMENT,
  `rawcmdtemplateid` INT(9) NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `cmdindex` INT(9) NOT NULL,
  `cmdtype` INT(9) NOT NULL,
  `rawcmd` VARCHAR(2048) NOT NULL,
  PRIMARY KEY (`rawcmdtemplatedtlid`)
);


insert into messagetemplate (`key`,language,platform,value) values('tv','zh_CN',0,'电视'),('stb','zh_CN',0,'机顶盒'),('ac','zh_CN',0,'空调'),('bgm','zh_CN',0,'背景音乐'),('prj','zh_CN',0,'投影仪'),('other','zh_CN',0,'其他');
insert into messagetemplate (`key`,language,platform,value) values('tv','en_US',0,'TV'),('stb','en_US',0,'STB'),('ac','en_US',0,'AC'),('bgm','en_US',0,'BGM'),('prj','en_US',0,'PRJ'),('other','en_US',0,'OTHER');

insert into messagetemplate (`key`, language, platform, value) values('defaultname_61', 'zh_CN', 0 ,'透传设备'),('defaultname_61', 'en_US', 0 ,'PassThrough Device');
insert into messagetemplate (`key`, language, platform, value) values('defaultname_62', 'zh_CN', 0 ,'红外透传设备'),('defaultname_62', 'en_US', 0 ,'Infrared PassThrough Device');

ALTER TABLE rawcmdtemplate ADD COLUMN type INT(9) NOT NULL ;

CREATE TABLE `iremote`.`datadictionary`(
    datadictionaryid INT(9) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `key` VARCHAR(256) NOT NULL,
    language VARCHAR(32),
    value VARCHAR(256) NOT NULL
);

INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('TV', 'zh_CN','电视'),('TV', 'en_US','TV'),('TV', 'vi_VN','TV'),('TV', 'zh_HK','TV'),('TV', 'fr_CA','TV');
INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('STB', 'zh_CN','机顶盒'),('STB', 'en_US','STB'),('STB', 'vi_VN','STB'),('STB', 'zh_HK','STB'),('STB', 'fr_CA','STB');
INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('AC', 'zh_CN','空调'),('AC', 'en_US','AC'),('AC', 'vi_VN','AC'),('AC', 'zh_HK','AC'),('AC', 'fr_CA','AC');
INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('BGM', 'zh_CN','背景音乐'),('BGM', 'en_US','BGM'),('BGM', 'vi_VN','BGM'),('BGM', 'zh_HK','BGM'),('BGM', 'fr_CA','BGM');
INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('PRJ', 'zh_CN','投影仪'),('PRJ', 'en_US','PRJ'),('PRJ', 'vi_VN','PRJ'),('PRJ', 'zh_HK','PRJ'),('PRJ', 'fr_CA','PRJ');
INSERT INTO datadictionary(`key`, LANGUAGE, VALUE) VALUES ('OTHER', 'zh_CN','其他'),('OTHER', 'en_US','OTHER'),('OTHER', 'vi_VN','OTHER'),('OTHER', 'zh_HK','OTHER'),('OTHER', 'fr_CA','OTHER');

CREATE TABLE `authqrcode` (
  `authqrcodeid` int(9) NOT NULL AUTO_INCREMENT,
  `qid` varchar(128) NOT NULL,
  `authtype` varchar(128) NOT NULL,
  `status` int(9) NOT NULL,
  `createtime` datetime NOT NULL,
  `applianceuuid` varchar(256) DEFAULT NULL,
  `authtime` datetime DEFAULT NULL,
  `operator` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authqrcodeid`),
  UNIQUE KEY `qid` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

ALTER TABLE scene ADD COLUMN `executorsetting` int(9);

insert into systemparameter (`key`,strvalue) values('aliiotappkey','25537775');
insert into systemparameter (`key`,strvalue) values('aliiotappsecret','010b486e897ef99db3676f393016595f');
insert into systemparameter (`key`,strvalue) values('aliiotprojectres','a124XhvOuwdxiNqH');

insert into messagetemplate(`key`,language,platform,value) values("defaultname_63","zh_CN",0,"离子烘干机");
insert into messagetemplate(`key`,language,platform,value) values("defaultname_63","en_US",0,"Plasma Dryer");

INSERT INTO messagetemplate(`key`,LANGUAGE,platform,VALUE) VALUES("defaultname_60","zh_CN",0,"穿衣小助手");
INSERT INTO messagetemplate(`key`,LANGUAGE,platform,VALUE) VALUES("defaultname_60","en_US",0,"Dress Helper");

INSERT INTO messagetemplate(`key`,LANGUAGE,platform,VALUE) VALUES("defaultname_64","zh_CN",0,"四通道开关");
INSERT INTO messagetemplate(`key`,LANGUAGE,platform,VALUE) VALUES("defaultname_64","en_US",0,"4 channel switch");

ALTER TABLE iremote.gatewaycapability ADD COLUMN `capabilityvalue` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL;


INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("dscfkeyalarm", "zh_CN", 0, "您的家人按响了${name}的火警警报，请注意家人安全。"),("dscpkeyalarm", "zh_CN", 0, "您的家人按响了${name}的紧急警报，请注意家人安全。"),("dscakeyalarm", "zh_CN", 0, "您的家人按响了${name}的医疗警报，请注意家人安全。");
INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("dscfkeyalarm", "en_US", 0, "Your family has pressed the ${name} fire alarm, please pay attention to home safety"),("dscpkeyalarm", "en_US", 0, "Your family has pressed the ${name} emergency alarm, please pay attention to home safety"),("dscakeyalarm", "en_US", 0, "Your family has pressed the ${name} medical alert, please pay attention to home safety.");
INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("dscfkeyalarm", "fr_CA", 0, "Votre famille ${name} déclenché l’alarme incendie."),("dscpkeyalarm", "fr_CA", 0, "Votre famille ${name} déclenché l’alarme d’urgence."),("dscakeyalarm", "fr_CA", 0, "Votre famille ${name} déclenché l’alarme médicale.");

CREATE TABLE `county` (
  `countyid` int(9) NOT NULL AUTO_INCREMENT,
  `cityid` int(9) NOT NULL,
  `code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `alilocationkey` varchar(64) NOT NULL,
  PRIMARY KEY (`countyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("remoteNetworkChanged_3G", "zh_CN", 0, "您的网关${name}已切换为3G网络连接。"),("remoteNetworkChanged_WIFI", "zh_CN", 0, "您的网关${name}已切换为WIFI网络连接。"),("remoteoffline_3G", "zh_CN", 0, "您的网关${name}的3G网络连接已断开，网关已离线，请检查是否故障。"),("remoteoffline_WIFI", "zh_CN", 0, "您的网关${name}的WIFI网络连接已断开，网关已离线，请检查是否故障。");
INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("remoteNetworkChanged_3G", "en_US", 0, "Your gateway ${name} has been switched to 3G Network"),("remoteNetworkChanged_WIFI", "en_US", 0, "Your gateway ${name} has been switched to WIFI Network"),("remoteoffline_3G", "en_US", 0, "Gateway offline.The 3G connection of your gateway ${name} has been disconnected, Please check the failure."),("remoteoffline_WIFI", "en_US", 0, "Gateway offline.The WiFi connection of your gateway ${name} has been disconnected, Please check the failure.");
INSERT INTO messagetemplate(`key`, LANGUAGE, platform, VALUE) VALUES("remoteNetworkChanged_3G", "fr_CA", 0, "Votre passerelle ${name} a été changée pour une connexion 3G."),("remoteNetworkChanged_WIFI", "fr_CA", 0, "Votre passerelle ${name} a été changée en connexion WIFI."),("remoteoffline_3G", "fr_CA", 0, "La connexion du réseau 3G de votre gateway ${name} est déconnectée."),("remoteoffline_WIFI", "fr_CA", 0, "La connexion WIFI de votre gateway ${name} est déconnectée et la passerelle est déconnectée.");