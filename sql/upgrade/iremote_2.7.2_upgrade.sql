ALTER TABLE `operationlog` ADD COLUMN `resultCode` int(9) default NULL AFTER `result`;

ALTER TABLE `operationlog` ADD COLUMN `deviceid` varchar(32) collate utf8_bin default NULL AFTER `requestdata`;
ALTER TABLE `operationlog` ADD COLUMN `zwavedeviceid` varchar(32) collate utf8_bin default NULL AFTER `deviceid`;