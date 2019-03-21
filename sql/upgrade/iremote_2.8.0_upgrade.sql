alter table doorlockpassword add column `phonenumber` varchar(256) collate utf8_bin default NULL after errorcount ;
alter table doorlockpassword add column `passwordtype` int(9) NOT NULL default '1' after phonenumber ;

alter table notification add column `appendjsonstring` varchar(64) COLLATE utf8_bin DEFAULT NULL after appendmessage ;


ALTER TABLE `notification` ADD INDEX `Index_Notification_reporttime` (`reporttime`); 