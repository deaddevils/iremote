CREATE TABLE `zwavedeviceactivetime` (
  `zwavedeviceid` int(9) NOT NULL,
  `lastactivetime` datetime NOT NULL,
  `nextactivetime` datetime NOT NULL,
  `status` int(9) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`zwavedeviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `advertbanner` (
  `advertbannerid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `thirdpartid` int(9) DEFAULT NULL,
  `isdefault` int(9) NOT NULL COMMENT '是否为默认广告栏，默认为0,0：不是默认广告栏,1：是默认广告栏;每个第三方平台只能指定一个默认广告栏，当没有为其某个用户指定广告栏时，该用户显示默认广告栏',
  `description` varchar(1024) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`advertbannerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `advertbannerpic` (
  `advertbannerpicid` int(11) NOT NULL AUTO_INCREMENT,
  `advertbannerid` int(11) NOT NULL COMMENT '所属广告栏ID',
  `url` varchar(512) NOT NULL COMMENT '广告图片URL',
  `picindex` int(9) NOT NULL COMMENT '序号，有多张广告图片时，按序号顺序显示',
  `description` varchar(1024) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`advertbannerpicid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
