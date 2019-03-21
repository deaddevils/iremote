delete from messagetemplate where `key` = 'bulliedopenlock' and language = 'zh_CN' ;

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','zh_CN','bulliedopenlock','您的家人${username},通过门锁${name}，发出了被胁迫告警，请注意居家安全。','SMS_59025308' , '{}');

