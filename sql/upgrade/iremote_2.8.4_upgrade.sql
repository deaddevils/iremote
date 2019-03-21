alter table doorlockpassword add column `usertype` int(9) NOT NULL after zwavedeviceid ;
alter table doorlockpassword change column `validfrom` `validfrom` datetime default NULL;
alter table doorlockpassword change column `validthrough` `validthrough` datetime default NULL;
alter table doorlockpassword add column `dayofweekjson` varchar(256) collate utf8_bin default NULL after validthrough ;
alter table doorlockpassword add column `starttime` varchar(32) collate utf8_bin default NULL after dayofweekjson ;
alter table doorlockpassword add column `endtime` varchar(32) collate utf8_bin default NULL after starttime ;

