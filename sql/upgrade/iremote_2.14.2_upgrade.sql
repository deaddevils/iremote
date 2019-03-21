ALTER TABLE `doorlockpassword` ADD COLUMN `weekday` int(9) DEFAULT NULL AFTER `validthrough`;
ALTER TABLE `doorlockpassword` ADD COLUMN `starttime` varchar(512) collate utf8_bin DEFAULT NULL AFTER `weekday`;
ALTER TABLE `doorlockpassword` ADD COLUMN `endtime` varchar(512) collate utf8_bin DEFAULT NULL AFTER `starttime`;


ALTER TABLE `doorlockuser` ADD COLUMN `weekday` int(9) DEFAULT NULL;
ALTER TABLE `doorlockuser` ADD COLUMN `starttime` varchar(512) collate utf8_bin DEFAULT NULL;
ALTER TABLE `doorlockuser` ADD COLUMN `endtime` varchar(512) collate utf8_bin DEFAULT NULL;
