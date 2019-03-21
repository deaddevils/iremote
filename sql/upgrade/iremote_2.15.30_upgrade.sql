insert into messagetemplate(`key`, language, platform, value) values("associatepartitionarmfailed","zh_CN",0,"门锁${username}联动设防防区${partitionname}失败。");
insert into messagetemplate(`key`, language, platform, value) values("associatepartitionarmfailed","en_US",0,"Door Lock${username}associate partition ${partitionname} arm failed");

ALTER TABLE doorlockpassword MODIFY COLUMN `password` VARCHAR(2048);
ALTER TABLE doorlockpassword ADD COLUMN tid VARCHAR(256) DEFAULT NULL;
ALTER TABLE doorlockpassword ADD COLUMN locktype INT(9) DEFAULT 0;