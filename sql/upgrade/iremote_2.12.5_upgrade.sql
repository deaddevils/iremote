alter table associationscene add column `cameraid` int(11) DEFAULT NULL after `zwavedeviceid`;
alter table associationscene change column `zwavedeviceid` `zwavedeviceid` int(11) DEFAULT NULL;

CREATE TABLE `fee_account` (
  `accountid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) NOT NULL,
  `balance` int(9) default '0',
  `blockedbalance` int(9) default '0',
  `giftbalance` int(9) default '0',
  `freesmscount` int(9) default '5',
  `giftsmscount` int(9) default '0',
  `payedsmscount` int(9) default '0',
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `fee_item` (
  `feeitemid` int(9) NOT NULL auto_increment,
  `name` varchar(256) collate utf8_bin NOT NULL,
  `feetype` int(9) NOT NULL,
  `objecttype` int(9) NOT NULL,
  PRIMARY KEY  (`feeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `fee_paymentdetail` (
  `paymentdetailid` int(11) NOT NULL auto_increment,
  `transactionid` int(11) NOT NULL,
  `paymenttype` int(11) NOT NULL,
  `amount` int(11) default '0',
  `maxdrawback` int(11) default '0',
  `status` int(11) default '1',
  `rf paymentdetailid` int(11) default NULL,
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `payedtime` datetime default NULL,
  `expiretime` datetime default NULL,
  PRIMARY KEY  (`paymentdetailid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `fee_phoneuserfeeitem` (
  `phoneuserfeeitemid` int(11) NOT NULL auto_increment,
  `phoneuserproductionid` int(11) NOT NULL,
  `feeitemid` int(11) NOT NULL,
  `fullname` int(11) NOT NULL,
  `feetype` int(11) NOT NULL,
  `objecttype` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `maxdrawback` int(11) NOT NULL,
  `freezeamount` int(11) NOT NULL,
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `validfrom` datetime NOT NULL,
  `validthrought` datetime NOT NULL,
  PRIMARY KEY  (`phoneuserfeeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `fee_transaction` (
  `transactionid` int(9) NOT NULL auto_increment,
  `accountid` int(9) NOT NULL,
  `transactiontype` int(9) NOT NULL,
  `price` int(9) default '0',
  `recharge` int(9) default '0',
  `gift` int(9) default '0',
  `deduct` int(9) default '0',
  `freezeamount` int(9) default '0',
  `freezegift` int(9) default '0',
  `prebalance` int(9) default '0',
  `postbalance` int(9) default '0',
  `pregift` int(9) default '0',
  `postgift` int(9) default '0',
  `prefreeze` int(9) default '0',
  `postfreeze` int(9) default '0',
  `maxdrawback` int(9) default '0',
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pd_phoneuserproduction` (
  `phoneuserproductionid` int(9) NOT NULL auto_increment,
  `phoneuserid` int(9) NOT NULL,
  `productionid` int(9) NOT NULL,
  `status` int(9) NOT NULL,
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`phoneuserproductionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pd_production` (
  `productionid` int(11) NOT NULL auto_increment,
  `platform` int(11) NOT NULL,
  `name` varchar(256) collate utf8_bin NOT NULL,
  `price` int(11) NOT NULL,
  `fullname` varchar(512) collate utf8_bin NOT NULL,
  `status` int(11) NOT NULL,
  `createtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `validfrom` datetime NOT NULL,
  `validthrought` datetime default NULL,
  PRIMARY KEY  (`productionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pd_productionfeeitem` (
  `productionfeeitemid` int(11) NOT NULL auto_increment,
  `productionid` int(11) NOT NULL,
  `feeitemid` int(11) NOT NULL,
  `fullname` varchar(512) collate utf8_bin NOT NULL,
  `amount` int(11) NOT NULL default '0',
  `maxdrawback` int(11) NOT NULL default '0',
  `freezeamount` int(11) NOT NULL default '0',
  `validamount` int(11) default NULL,
  `validunit` int(11) default NULL,
  PRIMARY KEY  (`productionfeeitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `deviceoperationsteps` (
  `deviceoprationstatusid` int(9) NOT NULL auto_increment,
  `deviceid` varchar(64) collate utf8_bin default NULL,
  `zwavedeviceid` int(9) default NULL,
  `status` int(9) NOT NULL,
  `starttime` datetime NOT NULL,
  `expiretime` datetime NOT NULL,
  `finished` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`deviceoprationstatusid`),
  UNIQUE KEY `Index_unique_deviceoperationstatus_zwavedeviceid` (`zwavedeviceid`),
  UNIQUE KEY `Index_unique_deviceoperationstatus_deviceid` (`deviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `deviceinitsetting` (
  `deviceinitsettingid` int(9) NOT NULL auto_increment,
  `mid` varchar(32) collate utf8_bin NOT NULL,
  `manufacture` varchar(64) collate utf8_bin default NULL,
  `devicetype` varchar(16) collate utf8_bin NOT NULL,
  `initcmds` varchar(256) collate utf8_bin default NULL,
  PRIMARY KEY  (`deviceinitsettingid`),
  UNIQUE KEY `Index_deviceinitsetting_mid` (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
