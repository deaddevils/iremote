alter table camera add column `warningstatuses` varchar(32) collate utf8_bin default NULL after `status`;
alter table td_eventforthirdpart add column `cameraid` int(9) default NULL after `zwavedeviceid`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','cameradectectmove','Your camera ${name}, check the object to move, please pay attention to home security.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','cameradectectmove','�������ͷ${name},��鵽�����ƶ�����ע��ӼҰ�ȫ��',null  , '{"alarmmessage":"�����ƶ�"}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','cameraoffline','���${name}�Ѿ����ߣ������Ƿ���ϡ�','SMS_35090001' , '{}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','cameraoffline','Your ${name} is offline, please check if there is anything wrong.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','locklockerror','��������${name},�޷������رգ���ע��ӼҰ�ȫ��',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','locklockerror','your doorlock ${name} can''t be closed properly, please pay attention to home safety.',null , null);

ALTER TABLE `command` ADD INDEX `Index_command_scenedbid` (`scenedbid`); 

