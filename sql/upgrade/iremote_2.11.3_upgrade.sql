insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 1 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 1 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 10001  from remote where remotetype = 1 ;

insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 2 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 2 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 10002  from remote where remotetype = 2 ;

insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 15 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 15 ;

insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 17 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 17 ;

insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 23 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 23 ;

insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 28 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 28 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 10028  from remote where remotetype = 28 ;


insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 29 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 29 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 10029  from remote where remotetype = 29 ;


insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 1  from remote where remotetype = 36 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 2  from remote where remotetype = 36 ;
insert into gatewaycapability(deviceid , capabilitycode) select deviceid , 10036  from remote where remotetype = 36 ;

delete from messagetemplate where `key` = 'remoteoffline';
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','remoteoffline','你的${name}已经离线，请检查是否故障。','SMS_35090001' , '{}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','remoteoffline','Your ${name} is offline, please check if there is anything wrong.',null , null);

delete from messagetemplate where `key` = 'iremoteownerchanged';
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','iremoteownerchanged','您的${name}已经被人重新设置，请注意居家安全。', null  , '{}');
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','en_US','iremoteownerchanged','Your ${name} has been reset, please be cautious about home safety. ',null , null);
