 alter table deviceoperationsteps add column `optype` int(9) default NULL after `zwavedeviceid`;
 alter table deviceoperationsteps add column  `appendmessage` varchar(512) collate utf8_bin default NULL after `optype`;

 alter table deviceoperationsteps add unique `Index_unique_deviceoperationstatus_zwavedeviceid_optype` (`zwavedeviceid`,`optype`) ;
 alter table deviceoperationsteps add unique `Index_unique_deviceoperationstatus_deviceid_optype` (`deviceid`,`optype`) ;

drop index Index_unique_deviceoperationstatus_zwavedeviceid on deviceoperationsteps ;
drop index Index_unique_deviceoperationstatus_deviceid on deviceoperationsteps ;

 
 CREATE TABLE `thirdpartattribute` (
  `thirdpartattributeid` int(9) NOT NULL auto_increment,
  `thirdpartid` int(9) NOT NULL,
  `code` varchar(128) collate utf8_bin NOT NULL,
  `value` varchar(128) collate utf8_bin default NULL,
  PRIMARY KEY  (`thirdpartattributeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;