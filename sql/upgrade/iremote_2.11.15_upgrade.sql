alter table notification add column `cameraid` int(9) DEFAULT NULL after `zwavedeviceid`;
alter table zwavedeviceshare add column `validtype` int(9) NOT NULL DEFAULT 0 after `validthrough`;


ALTER TABLE `td_zwavedeviceeventpushvalues` ADD UNIQUE INDEX `Index_zdepv_zwavedeviceid` (`zwavedeviceid`); 
ALTER TABLE `zwavedevice` ADD UNIQUE INDEX `Index_zwavedevice_device_nuid` (`deviceid`, `nuid`);
