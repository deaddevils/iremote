alter table camera add column `warningstatuses` varchar(32) collate utf8_bin default NULL after `status`;
alter table td_eventforthirdpart add column `cameraid` int(9) default NULL after `zwavedeviceid`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','cameradectectmove','Your camera ${name}, check the object to move, please pay attention to home security.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','cameradectectmove','你的摄像头${name},检查到物体移动，请注意居家安全。',null  , '{"alarmmessage":"物体移动"}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','cameraoffline','你的${name}已经离线，请检查是否故障。','SMS_35090001' , '{}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','cameraoffline','Your ${name} is offline, please check if there is anything wrong.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','locklockerror','您的门锁${name},无法正常关闭，请注意居家安全。',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','locklockerror','your doorlock ${name} can''t be closed properly, please pay attention to home safety.',null , null);

ALTER TABLE `command` ADD INDEX `Index_command_scenedbid` (`scenedbid`); 

