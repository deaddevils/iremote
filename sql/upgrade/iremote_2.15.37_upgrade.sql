ALTER TABLE `iremote`.`zwavesubdevice` ADD COLUMN `statuses` VARCHAR (64);

insert into messagetemplate(`key`, platform, language ,value) values("defaultname_57", 0, "zh_CN","双通道电子围栏");
insert into messagetemplate(`key`, platform, language ,value) values("defaultname_56", 0, "zh_CN","单通道电子围栏");
insert into messagetemplate(`key`, platform, language ,value) values("defaultname_57", 0, "en_US","Two Channels Electric Fence");
insert into messagetemplate(`key`, platform, language ,value) values("defaultname_56", 0 , "en_US","One Chnnel Electric Fence");
insert into messagetemplate(`key`, platform, language ,value) values("defaultname_57", 0, "fr_CA","Clôture électronique à deux canaux");
insert into messagetemplate(`key`, platform, language ,value) values("defaultname_56", 0 , "fr_CA","Clôture électronique à un canal");

INSERT INTO `messagetemplate`(`key`,language,platform,value,alitemplatecode,alitemplateparam)
VALUES ('efencedisconnection','en_US',0,'Your device ${name} were in broken line alarm, Exercise caution. ',NULL,NULL),
('efenceshortcircuit','en_US',0,'Your device ${name} were in short out alarm, Exercise caution. ',NULL,NULL),
('efencetampler','en_US',0,'Your device ${name} were in broken device alarm, Exercise caution. ',NULL,NULL),
('efenceinstrusion','en_US',0,'Your device ${name} were in intrusion alarm, Exercise caution. ',NULL,NULL),
('efencecontact','en_US',0,'Your device ${name} were in perimeter alarm, Exercise caution.',NULL,NULL),
('efencesamelineshortcircuit','en_US',0,'Your device ${name} were in collinear short out alarm, Exercise caution. ',NULL,NULL),
('efencepoweroff','en_US',0,'Your device ${name} were in outage alarm, Exercise caution.',NULL,NULL),
('efencedisconnection','zh_CN',0,'你的设备${name},发出了断线报警，请注意居家安全。',NULL,NULL),
('efenceshortcircuit','zh_CN',0,'你的设备${name},发出了短路报警，请注意居家安全。',NULL,NULL),
('efencetampler','zh_CN',0,'你的设备${name},发出了设备破坏报警，请注意居家安全。',NULL,NULL),
('efenceinstrusion','zh_CN',0,'你的设备${name},发出了入侵报警，请注意居家安全。',NULL,NULL),
('efencecontact','zh_CN',0,'你的设备${name},发出了触网报警，请注意居家安全。',NULL,NULL),
('efencesamelineshortcircuit','zh_CN',0,'你的设备${name},发出了同线短路报警，请注意居家安全。',NULL,NULL),
('efencepoweroff','zh_CN',0,'你的设备${name},发出了断电告警，请注意居家安全。',NULL,NULL),
('efencedisconnection','fr_CA',0,'Votre appareil ${name} Vérifier alarme de ligne cassée, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efenceshortcircuit','fr_CA',0,'Votre appareil ${name} Vérifier alarme de court-circuit, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efencetampler','fr_CA',0,'Votre appareil ${name} Vérifier alarme d\'appareil est défective , S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efenceinstrusion','fr_CA',0,'Votre appareil ${name} Vérifier alarme d\'intrusion, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efencecontact','fr_CA',0,'Votre appareil ${name} Vérifier alarme de périmètre, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efencesamelineshortcircuit','fr_CA',0,'Votre appareil ${name} Vérifier alarme courte colinéaire, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL),
('efencepoweroff','fr_CA',0,'Votre appareil ${name} Vérifier alarme en panne, S\'il vous plaît prêter attention à la sécurité à la maison.',NULL,NULL);