ALTER TABLE `deviceextendinfo` ADD COLUMN `devicepassword` VARCHAR(128) NULL AFTER `zwaveproductormessage`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','armfailed','设防失败，请检测设备是否就位，注意居家安全',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','armfailed','Arm failure, please check if the device is in place, pay attention to home safety.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','dscalarm','您的设备${name}，发出告警，请注意居家安全',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','dscalarm','Your device ${name} issued a warning, please pay attention to home safety.',null , null);
