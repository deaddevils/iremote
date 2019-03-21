ALTER TABLE `deviceextendinfo` ADD COLUMN `devicepassword` VARCHAR(128) NULL AFTER `zwaveproductormessage`;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','armfailed','���ʧ�ܣ������豸�Ƿ��λ��ע��ӼҰ�ȫ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','armfailed','Arm failure, please check if the device is in place, pay attention to home safety.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','dscalarm','�����豸${name}�������澯����ע��ӼҰ�ȫ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','dscalarm','Your device ${name} issued a warning, please pay attention to home safety.',null , null);
