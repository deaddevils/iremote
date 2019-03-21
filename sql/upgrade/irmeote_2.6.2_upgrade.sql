CREATE TABLE `doorlockuser` (
   `doorlockuserid` int(9) NOT NULL auto_increment,
   `zwavedeviceid` int(9) NOT NULL,
   `usercode` int(9) NOT NULL,
   `username` varchar(256) collate utf8_bin NOT NULL,
   PRIMARY KEY  (`doorlockuserid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;