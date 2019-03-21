ALTER TABLE `thirdpart` ADD COLUMN `adminprefix` varchar(32) default null AFTER `platform`;

ALTER TABLE `command` ADD `weekday` int(9) DEFAULT NULL AFTER `zwavecommandsbase64`;
ALTER TABLE `command` ADD `startime` varchar(512) DEFAULT NULL AFTER `weekday`;
ALTER TABLE `command` ADD `endtime` varchar(512) DEFAULT NULL AFTER `startime`;
ALTER TABLE `command` ADD `startsecond` int(9) DEFAULT NULL AFTER `endtime`;
ALTER TABLE `command` ADD `endsecond` int(9) DEFAULT NULL AFTER `startsecond`;
