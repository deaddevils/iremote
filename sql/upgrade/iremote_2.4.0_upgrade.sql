ALTER TABLE `phoneuser`  ADD COLUMN `countrycode` VARCHAR(32) DEFAULT '86' NOT NULL AFTER `phoneuserid`;
ALTER TABLE `phoneuser`  ADD COLUMN `armstatus` int(9) DEFAULT 1 AFTER `callcount`;
ALTER TABLE `randcode`  ADD COLUMN `countrycode` VARCHAR(32) DEFAULT '86' NOT NULL AFTER `randcode`;
ALTER TABLE `smshistory`  ADD COLUMN `countrycode` VARCHAR(32) DEFAULT '86' NOT NULL AFTER `smshistoryid`;
ALTER TABLE `notification`  ADD COLUMN `majortype` varchar(64) COLLATE utf8_bin DEFAULT NULL AFTER `orimessage`;
ALTER TABLE `notification`  ADD COLUMN `status` int(9) DEFAULT 0 AFTER `eclipseby`;
ALTER TABLE `notification`  ADD COLUMN `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL AFTER `nuid`;
ALTER TABLE `notification`  ADD COLUMN `phoneuserid` int(9) DEFAULT NULL AFTER `notificationid`;
ALTER TABLE `remote`  ADD COLUMN `networkintensity` int(9) default '100' AFTER `network`;
ALTER TABLE `phoneuser`  ADD COLUMN `familyid` int(9) default NULL AFTER `platform`;
ALTER TABLE `phoneuser`  ADD COLUMN `language` varchar(64) default 'zh_CN' AFTER `platform`;
ALTER TABLE `usershare`  ADD COLUMN `sharetype` int(9) default 0 AFTER `touserid`;
ALTER TABLE `zwavedevice`  ADD COLUMN `enablestatus` int(9) default '0' AFTER `statuses`;
ALTER TABLE `usershare`  ADD COLUMN `tousercountrycode` varchar(32) COLLATE utf8_bin default '86' AFTER `touser`;
ALTER TABLE `camera`  ADD COLUMN `status` int(9) DEFAULT 1 AFTER `enablestatus`;

CREATE TABLE `thirdpart` (
  `thirdpartid` int(9) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_bin NOT NULL,
  `password` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`thirdpartid`),
  UNIQUE KEY `thirdpart_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `thirdparttoken` (
  `thirdparttokenid` int(9) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_bin NOT NULL,
  `token` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdatetime` datetime DEFAULT NULL,
  `validtime` datetime NOT NULL,
  PRIMARY KEY (`thirdparttokenid`),
  UNIQUE KEY `thirdparttoken_token` (`token`),
  UNIQUE KEY `thirdparttoken_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `camera` (                                         
  `cameraid` int(9) NOT NULL AUTO_INCREMENT,                    
  `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,                 
  `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,              
  `devicetype` varchar(64) COLLATE utf8_bin DEFAULT NULL,               
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,    
  `productorid` varchar(64) COLLATE utf8_bin DEFAULT NULL,               
  `applianceuuid` varchar(64) COLLATE utf8_bin DEFAULT NULL, 
  `enablestatus` int(9) DEFAULT 0,
  PRIMARY KEY (`cameraid`)                                      
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

 CREATE TABLE `family` (                       
  `familyid` int(9) NOT NULL AUTO_INCREMENT, 
  `phoneuserid` int(9) NOT NULL ,
  `name` varchar(32) DEFAULT NULL,            
  `armstatus` int(9) DEFAULT NULL,            
  PRIMARY KEY (`familyid`)                    
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `command` (                                     
   `commandid` int(9) NOT NULL AUTO_INCREMENT,                
   `associationsceneid` int(9) DEFAULT NULL,                  
   `scenedbid` int(9) DEFAULT NULL,                           
   `deviceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,      
   `applianceid` varchar(64) COLLATE utf8_bin DEFAULT NULL,   
   `cmdindex` int(9) DEFAULT NULL,                               
   `delay` int(9) DEFAULT NULL,                               
   `infraredcode` varbinary(256) DEFAULT NULL,                
   `zwavecommand` varbinary(256) DEFAULT NULL,                
   `description` varchar(256) COLLATE utf8_bin DEFAULT NULL,  
   PRIMARY KEY (`commandid`)                                  
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
 CREATE TABLE `associationscene` (                        
    `associationsceneid` int(11) NOT NULL AUTO_INCREMENT,  
    `channelid` int(11) NOT NULL,                          
    `status` int(11) NOT NULL,                             
    `zwavedeviceid` int(11) NOT NULL,                      
    PRIMARY KEY (`associationsceneid`)                     
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
CREATE TABLE `scene` (                                 
  `scenedbid` int(11) NOT NULL AUTO_INCREMENT,         
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL,   
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,   
  `phoneuserid` int(11) NOT NULL,                      
  `sceneid` int(11) NOT NULL,                          
  PRIMARY KEY (`scenedbid`)                            
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;  



CREATE TABLE `messagetemplate` (                                                                                          
   `messagetemplateid` int(9) NOT NULL AUTO_INCREMENT,                                                                     
   `key` varchar(32) COLLATE utf8_bin NOT NULL,                                                                            
   `language` varchar(32) COLLATE utf8_bin NOT NULL,                                                                       
   `platform` int(9) NOT NULL,                                                                                             
   `value` varchar(256) COLLATE utf8_bin DEFAULT NULL,                                                                     
   PRIMARY KEY (`messagetemplateid`)                                                                                       
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;

CREATE TABLE `wcj_comunity` (                                                                          
    `comunityid` int(9) NOT NULL AUTO_INCREMENT,                                                         
    `name` varchar(32) CHARACTER SET utf8 DEFAULT NULL,                                                  
    `code` varchar(64) CHARACTER SET utf8 NOT NULL,                                                      
    `thirdpartid` int(9) NOT NULL,                                                                       
    PRIMARY KEY (`comunityid`)                                                                           
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;
  
CREATE TABLE `wcj_comunityremote` (                                                                    
  `comunityremoteid` int(9) NOT NULL AUTO_INCREMENT,                                                   
  `deviceid` varchar(64) CHARACTER SET utf8 NOT NULL,                                                  
  `comunityid` int(9) NOT NULL,                                                                        
  `thirdpartid` int(9) NOT NULL,                                                                       
  PRIMARY KEY (`comunityremoteid`)                                                                     
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `wcj_server` (                                                           
  `thirdpartid` int(9) NOT NULL,                                                      
  `serverurl` varchar(128) DEFAULT NULL,                                              
  `loginname` varchar(128) DEFAULT NULL,                                              
  `password` varchar(128) DEFAULT NULL,                                               
  PRIMARY KEY (`thirdpartid`)                                                         
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','doorbellring','Someone at %s is ringing your %s doorbell. Please confirm his identity before opening the door and be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','doorlockopen','Your door %s has been unlocked, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','dooropen','Your device %s detects that the door is open, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','gasleak','Your device %s detects the gas leakage, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','gooutwarning','Your device %s detects that the door is not closed, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','invation','Your friend %s invites you to trial-use \"Smart Collie.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','iremoteownerchanged','Your \"Smart Collie\" %s has been reset, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','lowbattery','Your device %s will be out of battery, please replace the battery as soon as possible.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','malfunction','There may be something wrong with your device %s, please inspect it as soon as possible.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','move','Your device %s, check the object to move, please pay attention to home security.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','movein','Your device %s detects that someone is entering, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','randcode','Your verification number is %s. Please enter it within 120 seconds. Do not disclose the verification code to others.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','remoteoffline','Your \"Smart Collie\" %s is offline, please check if there is anything wrong.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','sharerequest','Your friend %s has shared with you the access to his \"Tecus App.\" ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','smoke','Your device %s detects the suspicious smoke, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','smsrunout','30 messages have been sent to you this month (exclude this message) and it reaches the upper limit of the sent messages. No more messages will be sent to notify you of the alarm this month. Please be cautious about home safety');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','tampleralarm','Your device %s is being dismantled, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','en_US','waterleak','Your device %s detects the water leakage, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','doorbellring','有客人在%s按响了您%s的门铃，请在开门前确认来客身份，注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','doorlockopen','您的门锁%s,已经被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','dooropen','你的设备%s,检查到门被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','gasleak','你的设备%s,检查到煤气泄漏，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','gooutwarning','您的设备:%s，检测到门没有关闭，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','invation','您的朋友%s,邀请你试用小白管家，下载地址：http://iremote.isurpass.com.cn/iremote/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','iremoteownerchanged','您的转发器%s已经被人重新设置，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','lowbattery','你的设备%s,电池即将耗尽，请尽快更换。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','malfunction','你的设备%s,可能发生故障，请尽快检查。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','move','你的设备%s,检查到物体移动，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','movein','你的设备%s,检查到有人进入，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','randcode','您的验证码为%s，请于120秒内输入，请勿向他人泄露验证码内容。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','remoteoffline','你的智能盒子%s已经离线，请检查是否故障。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','sharerequest','您的朋友%s,把他的小白管家共享给您了。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','smoke','你的设备%s,检查到可疑烟雾，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','smsrunout','本月已经给您发送了30条短信(不含本条)，达到短信月上限，本月内如有告警，将不再给您发送短信，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','tampleralarm','您的设备%s,正在被拆卸，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('0','zh_CN','waterleak','你的设备%s,检查到漏水事件，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','en_US','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/1/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','doorbellring','有客人在%s按响了您%s的门铃，请在开门前确认来客身份，注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','doorlockopen','您的门锁%s,已经被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','dooropen','你的设备%s,检查到门被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','gasleak','你的设备%s,检查到煤气泄漏，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','gooutwarning','您的设备:%s，检测到门没有关闭，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/1/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','iremoteownerchanged','您的转发器%s已经被人重新设置，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','lowbattery','你的设备%s,电池即将耗尽，请尽快更换。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','malfunction','你的设备%s,可能发生故障，请尽快检查。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','move','你的设备%s,检查到物体移动，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','movein','你的设备%s,检查到有人进入，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','randcode','您的验证码为%s，请于120秒内输入，请勿向他人泄露验证码内容。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','remoteoffline','你的智能盒子%s已经离线，请检查是否故障。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','sharerequest','您的朋友%s,把他的小白管家共享给您了。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','smoke','你的设备%s,检查到可疑烟雾，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','smsrunout','本月已经给您发送了30条短信(不含本条)，达到短信月上限，本月内如有告警，将不再给您发送短信，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','tampleralarm','您的设备%s,正在被拆卸，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('1','zh_CN','waterleak','你的设备%s,检查到漏水事件，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','en_US','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/2/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','doorbellring','有客人在%s按响了您%s的门铃，请在开门前确认来客身份，注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','doorlockopen','您的门锁%s,已经被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','dooropen','你的设备%s,检查到门被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','gasleak','你的设备%s,检查到煤气泄漏，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','gooutwarning','您的设备:%s，检测到门没有关闭，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/2/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','iremoteownerchanged','您的转发器%s已经被人重新设置，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','lowbattery','你的设备%s,电池即将耗尽，请尽快更换。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','malfunction','你的设备%s,可能发生故障，请尽快检查。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','move','你的设备%s,检查到物体移动，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','movein','你的设备%s,检查到有人进入，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','randcode','您的验证码为%s，请于120秒内输入，请勿向他人泄露验证码内容。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','remoteoffline','你的智能盒子%s已经离线，请检查是否故障。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','sharerequest','您的朋友%s,把他的小白管家共享给您了。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','smoke','你的设备%s,检查到可疑烟雾，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','smsrunout','本月已经给您发送了30条短信(不含本条)，达到短信月上限，本月内如有告警，将不再给您发送短信，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','tampleralarm','您的设备%s,正在被拆卸，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('2','zh_CN','waterleak','你的设备%s,检查到漏水事件，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','en_US','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/3/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','doorbellring','有客人在%s按响了您%s的门铃，请在开门前确认来客身份，注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','doorlockopen','您的门锁%s,已经被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','dooropen','你的设备%s,检查到门被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','gasleak','你的设备%s,检查到煤气泄漏，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','gooutwarning','您的设备:%s，检测到门没有关闭，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','invation','您的朋友%s,邀请你试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/3/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','iremoteownerchanged','您的转发器%s已经被人重新设置，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','lowbattery','你的设备%s,电池即将耗尽，请尽快更换。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','malfunction','你的设备%s,可能发生故障，请尽快检查。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','move','你的设备%s,检查到物体移动，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','movein','你的设备%s,检查到有人进入，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','randcode','您的验证码为%s，请于120秒内输入，请勿向他人泄露验证码内容。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','remoteoffline','你的智能盒子%s已经离线，请检查是否故障。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','sharerequest','您的朋友%s,把他的小白管家共享给您了。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','smoke','你的设备%s,检查到可疑烟雾，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','smsrunout','本月已经给您发送了30条短信(不含本条)，达到短信月上限，本月内如有告警，将不再给您发送短信，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','tampleralarm','您的设备%s,正在被拆卸，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('3','zh_CN','waterleak','你的设备%s,检查到漏水事件，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','doorbellring','有客人在%s按响了您%s的门铃，请在开门前确认来客身份，注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','doorlockopen','您的门锁%s,已经被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','dooropen','你的设备%s,检查到门被打开，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','gasleak','你的设备%s,检查到煤气泄漏，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','gooutwarning','您的设备:%s，检测到门没有关闭，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','iremoteownerchanged','您的转发器%s已经被人重新设置，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','lowbattery','你的设备%s,电池即将耗尽，请尽快更换。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','malfunction','你的设备%s,可能发生故障，请尽快检查。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','move','你的设备%s,检查到物体移动，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','movein','你的设备%s,检查到有人进入，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','randcode','您的验证码为%s，请于120秒内输入，请勿向他人泄露验证码内容。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','remoteoffline','你的智能盒子%s已经离线，请检查是否故障。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','sharerequest','您的朋友%s,把他的tecus共享给您了。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','smoke','你的设备%s,检查到可疑烟雾，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','smsrunout','本月已经给您发送了30条短信(不含本条)，达到短信月上限，本月内如有告警，将不再给您发送短信，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','tampleralarm','您的设备%s,正在被拆卸，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','waterleak','你的设备%s,检查到漏水事件，请注意居家安全。');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','doorbellring','Someone at %s is ringing your %s doorbell. Please confirm his identity before opening the door and be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','doorlockopen','Your door %s has been unlocked, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','dooropen','Your device %s detects that the door is open, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','gasleak','Your device %s detects the gas leakage, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','gooutwarning','Your device %s detects that the door is not closed, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','invation','Your friend %s invites you to trial-use \"Tecus App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/4/download.html');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','iremoteownerchanged','Your \"Tecus\" %s has been reset, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','lowbattery','Your device %s will be out of battery, please replace the battery as soon as possible.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','malfunction','There may be something wrong with your device %s, please inspect it as soon as possible.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','move','Your device %s, check the object to move, please pay attention to home security.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','movein','Your device %s detects that someone is entering, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','randcode','Your verification number is %s. Please enter it within 120 seconds. Do not disclose the verification code to others.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','remoteoffline','Your \"Tecus\" %s is offline, please check if there is anything wrong.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','sharerequest','Your friend %s has shared with you the access to his \"Smart Collie App.\" ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','smoke','Your device %s detects the suspicious smoke, please be cautious about home safety.');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','smsrunout','30 messages have been sent to you this month (exclude this message) and it reaches the upper limit of the sent messages. No more messages will be sent to notify you of the alarm this month. Please be cautious about home safety');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','tampleralarm','Your device %s is being dismantled, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','en_US','waterleak','Your device %s detects the water leakage, please be cautious about home safety. ');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`) values('4','zh_CN','invation','您的朋友%s,邀请你试用Tecus，下载地址：http://iremote.isurpass.com.cn/iremote/4/download.html');
