insert into messagetemplate(`key`,language,platform,value) values("defaultname_54","zh_CN",0,"车库门");
insert into messagetemplate(`key`,language,platform,value) values("defaultname_54","en_US",0,"GarageDoor");
insert into messagetemplate(`key`,language,platform,value) values("defaultname_55","zh_CN",0,"WallReader");
insert into messagetemplate(`key`,language,platform,value) values("defaultname_55","en_US",0,"WallReader");

INSERT INTO devicefunctionversioncapability(devicetype, functionversion,capabilitycode) SELECT 55,functionversion,capabilitycode FROM devicefunctionversioncapability WHERE devicetype = 5;