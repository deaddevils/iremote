ALTER TABLE `td_eventforthirdpart` ADD COLUMN `warningstatus` int(9) DEFAULT null AFTER `floatparam`;
ALTER TABLE `td_eventforthirdpart` ADD COLUMN `warningstatuses` varchar(64) collate utf8_bin DEFAULT null AFTER `warningstatus`;

CREATE TABLE `oemproductor` (
  `oemproductorid` int(9) NOT NULL auto_increment,
  `name` varchar(256) collate utf8_bin default NULL,
  `platform` int(9) default NULL,
  `pushmasterkey` varchar(256) collate utf8_bin default NULL,
  `pushappkey` varchar(256) collate utf8_bin default NULL,
  `smssign` varchar(256) collate utf8_bin default NULL,
  `deviceprefix` varchar(256) collate utf8_bin default NULL,
  `lechangeappid` varchar(256) collate utf8_bin default NULL,
  `lechangeappSecret` varchar(256) collate utf8_bin default NULL,
  `lechangeadmin` varchar(256) collate utf8_bin default NULL,
  `androidappdownloadurl` varchar(256) collate utf8_bin default NULL,
  `iosappdownloadurl` varchar(256) collate utf8_bin default NULL,
  PRIMARY KEY  (`oemproductorid`),
  UNIQUE KEY `Unique_Index_Oemproductor_platform` (`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('1','经纬纵横','0','gmoZmRV0fBsazwNQW9mKWax8LBnbgF7QPP9pDHkHpSg=','meLVvQ27XPIXH9uxxI4Q6kRPNxJySMzdfiXmhD3o5zc=','【小白管家】','[\"iRemote2005\",\"iRemote3006\",\"iRemote3007\"]','BckE8jwvXwNG623tM3g9Jje5UlZGkhJyjpABesVB1hQ=','vNaLxLr8oD3rWx6Gizu6zQD3s91XBF4DNtDS4pSp/5I=','69f72609a26246ed','http://a.app.qq.com/o/simple.jsp?pkgname=com.jwzh.main','https://itunes.apple.com/cn/app/xiao-bai-guan-jia/id962651511?l=zh&ls=1&mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('2','创佳','2','YLl1IRsljn6USi2G+3BStzdvHa2fhEIBO3tgqFctsXo=','osb1s0Z65F3HfLcYOpT88B8e22ZivaENVcoU2/3BocY=','【创佳智居】','[\"iRemote3005\"]','0KO4XOlGZFcQEngaHoa3wRz6t/FoXKT8MNXvsdqIKsI=','h6M+Gq55NT6m5qDwXTQYDdcElUyzdqQfhC42NRWHugs=','7264fb326eca45b6','http://shouji.baidu.com/software/22782579.html?qq-pf-to=pcqq.c2c','https://itunes.apple.com/cn/app/创佳智居/id1079794520?mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('3','多灵','3','PFbur9ZVsrJruQJe4uQnv3xAv7ykMU4WbKH6ZUICrgQ=','khGG3m7j/MTSzXJwSNXkTk6+NSpn86oDAEtzj63bZEI=','【多灵慧居】','[\"iRemote4005\"]','gvbx9vMW7Rfjd0nougMmYEDAF5OV4YvBDXYtETv7oJY=','4vCCYwMVduEpfnbKWW/eVDKDqquxuXnU2zLqqDUgqpk=','c6b8ddda40e24002','http://sj.qq.com/myapp/detail.htm?apkName=com.jwzh.doorlink.main','https://itunes.apple.com/cn/app/多灵慧居/id1030923806?mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('4','Tecus','4','y9eU7XThnKjs23kIXvEJjP1iaPS6Z9qxrVBTa8b8re8=','JnJhEgDyEnawZs2Y3Pz6Y1Q9UUoJPrsdsqW2uVM4/1Q=','【Tecus】','[\"iRemote5005\"]','4aRtTr4Qs6h6ho9Z67l6updkQbDfkqvr7Kp/SOw7j9Y=','bFs3ptWOLTj5xGUgh6kegsq23LD7lfrUKzbtO+Rm0jQ=','60428c5124bc4d83','http://sj.qq.com/myapp/detail.htm?apkName=com.jwzh.tecus.main','https://itunes.apple.com/cn/app/tecus/id1078555650?mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('6','小虎','6','DRS2JlYrEcrGEL6Jfli0YIUpgy5BLjVWlCMMpm6RBrE=','734xpGDX91KZBj/Z+vZD3vKhR9dJhs3V00rLYSA4y1w=','【小虎智慧家】','[\"iRemote3008\"]','oxQe2jCU1EhcBj4nO2D7maGXWOHvabpI8Q90LWsv3Vg=','ZswGmrgKbSkhLAvr17i9AT/NpowBubWSXZY8EtG/vbk=','\"\"','http://sj.qq.com/myapp/detail.htm?apkName=cn.com.isurpass.xiaohu.main','https://itunes.apple.com/cn/app/小虎智慧家/id1220271796?l=zh&ls=1&mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('7','iSurpass','7','ZX42AWQJgqPDhD37m//f4zpIQ8EOPA+ZF7RLVGed9Hc=','RpeHYpgHZ3Z0FUM36jvsU31hYuGPVNTiv3g+2zRqTag=','【iSurpass】','[\"iRemote6005\"]','fYyipOxjmnmi1ivuSBP65mfqa09bqjDSq8AoBickaRI=','7WEvobXGEGONKqxUAfG0cuCjC9hb5u4SaCLHyQQhKgM=','\"\"',NULL,'https://itunes.apple.com/cn/app/isurpass/id1318641310?l=zh&ls=1&mt=8');
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('8','Keemple','8','mhtKLKG2AtLbGeQJWP2a7n4F4bgLu/jCuejXuW9ICVI=','4Zpr1MAkWx45mCdGgwhMxpZx1ElpRLRZgKBkgP301Bo=','【Keemple】','[\"iRemote7005\"]','\"\"','\"\"','\"\"',NULL,NULL);
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('9','Ameta','9','8cP/pcunBi9mx1ScDkk4cxjFaniskvt5Vd02rmsHrrA=','dR6582oeiCkVJQcC9joQSeNKYoEMkBWU+6wXn8z18Q8=','【小白管家】','[\"iRemote8005\"]','\"\"','\"\"','\"\"',NULL,NULL);
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('10','nCube','10','Yl++pzyGfoVXoN1Q3sRop1VHX6NaK9yHWkqJfaIq7YY=','e14Q7Cg2its18KwMm9Q5eiJYoqhJ/iks4g4e0JYl6S8=','【小白管家】','[\"iRemote3009\"]','\"\"','\"\"','\"\"',NULL,NULL);
insert into `oemproductor` (`oemproductorid`, `name`, `platform`, `pushmasterkey`, `pushappkey`, `smssign`, `deviceprefix`, `lechangeappid`, `lechangeappSecret`, `lechangeadmin`, `androidappdownloadurl`, `iosappdownloadurl`) values('11','乾坤小智','11','N6vJyMG62jfQmzMaz/1d93rCCEjFEnrCseu8omVq0Yw=','WJuWNe8466lrW+n6lVnfVLMXWnqmvmz8aTqko0rfLhU=','【小白管家】','[\"iRemote3010\"]','ghUBHL7oWCW6K0oaPzwnNsGYuZmisnTdP56WYijXVNE=','2G2Oz+EhB011Fhu3oC1C8EdRbP1XoSkc5WsNIvm6MbM=','474a157b34b24335',NULL,NULL);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_47', 'zh_CN', '0', 'DSC', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_47', 'en_US', '0', 'DSC', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_48', 'zh_CN', '0', '情景面板', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_48', 'en_US', '0', 'scene panel', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_48', 'zh_CN', '0', '情景面板', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_49', 'en_US', '0', 'scene panel', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_50', 'zh_CN', '0', '情景面板', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_50', 'en_US', '0', 'scene panel', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_51', 'zh_CN', '0', '暖气控制器', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_51', 'en_US', '0', 'heating', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_52', 'zh_CN', '0', '情景面板', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_52', 'en_US', '0', 'scene panel', null, null);


DELETE FROM messagetemplate WHERE `key` = 'invation' AND platform IN ( 1, 2, 3, 4,6,7);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('1','en_US','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=1',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('1','zh_CN','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=1',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('2','en_US','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=2',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('2','zh_CN','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=2',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('3','en_US','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=3',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('3','zh_CN','invation','您的朋友${phonenumber},邀请您试用智能盒子，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=3',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('4','zh_CN','invation','您的朋友${phonenumber},邀请您试用Tecus，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=4',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('4','en_US','invation','Your friend ${phonenumber} invites you to trial-use \"Tecus App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=4',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('6','zh_CN','invation','您的朋友${phonenumber},邀请您试用小虎智慧家，下载地址：http://iremote.isurpass.com.cn/iremote/download/download?platform=6',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('6','en_US','invation','Your friend ${phonenumber} invites you to trial-use \"i-tiger App.\", and the download address is as below:http://iremote.isurpass.com.cn/iremote/download?platform=6',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('7','en_US','invation','Your friend ${phonenumber} invites you to trial-use iSurpass，and the download address is as below：http://iremote.isurpass.com.cn/iremote/download?platform=7',NULL , NULL);
INSERT INTO `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) VALUES('7','zh_CN','invation','您的朋友${phonenumber},邀请您试用iSurpass，下载地址：http://iremote.isurpass.com.cn/iremote/download?platform=7',NULL , NULL);


