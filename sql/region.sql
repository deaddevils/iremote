CREATE TABLE `region` (
  `regionid` int(9) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL,
  `countrycode` varchar(16) NOT NULL COMMENT '调用api所需参数',
  PRIMARY KEY (`regionid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
insert into `region` (`regionid`, `code`, `name`, `countrycode`) values('1','canada','Canada','CA');
