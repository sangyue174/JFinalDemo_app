SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS  `tb_temp_record`;
CREATE TABLE `tb_temp_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `kidid` int(10) DEFAULT NULL COMMENT '孩子id',
  `equipid` int(10) DEFAULT NULL COMMENT '设备id',
  `recordTime` datetime DEFAULT NULL COMMENT '测温时间',
  `temperature` decimal(10,2) unsigned DEFAULT NULL COMMENT '温度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

insert into `tb_temp_record`(`id`,`kidid`,`equipid`,`recordTime`,`temperature`) values
('1',null,'1','2017-06-14 00:10:16',36.00),
('2',null,'1','2017-06-14 00:27:45',34.00),
('3',null,'1','2017-06-14 00:27:55',35.00),
('4',null,'1','2017-06-15 00:35:08',38.00),
('5','8','5','2017-07-20 20:31:33',36.50),
('6','8','5','2017-07-20 20:29:18',35.50),
('7','8','5','2017-07-20 19:29:18',36.50),
('8','8','5','2017-07-20 18:29:18',37.00),
('9','8','5','2017-07-20 17:29:18',35.50),
('10','8','5','2017-07-20 16:29:18',34.50),
('11','8','5','2017-07-20 15:29:18',37.50),
('12','8','5','2017-07-20 14:29:18',38.50),
('13','8','5','2017-07-20 13:29:18',39.50),
('14','8','5','2017-07-20 12:29:18',35.50);
DROP TABLE IF EXISTS  `tb_user_auth`;
CREATE TABLE `tb_user_auth` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(10) DEFAULT NULL COMMENT 'user主键',
  `identityType` varchar(15) DEFAULT 'phone' COMMENT '验证类型(username,phone,qq,weixin)',
  `identifier` varchar(50) DEFAULT NULL COMMENT '验证账号,openid',
  `credential` varchar(100) DEFAULT NULL COMMENT '验证凭证',
  `registerTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `salt` varchar(50) DEFAULT NULL COMMENT '验证盐',
  `tokenKey` varchar(255) DEFAULT NULL COMMENT 'tokenKey,access_token',
  `tokenTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'token失效时间',
  `loginTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `verified` varchar(5) DEFAULT '1' COMMENT '是否验证通过(0:无效1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

insert into `tb_user_auth`(`id`,`userid`,`identityType`,`identifier`,`credential`,`registerTime`,`salt`,`tokenKey`,`tokenTime`,`loginTime`,`verified`) values
('1','1','phone','123',null,'2017-05-22 21:42:23',null,null,null,'2017-05-22 21:42:23','1'),
('2','1','qq','1147468768','xxx','2017-06-09 23:40:19','123','xxx','2017-07-09 23:40:19','2017-06-09 23:40:19','1'),
('7','7','phone','18615566651','3f0b17baeaaf7d69f9d731d83ae5bf32','2017-06-11 00:16:16','[B@1571e0f6','2e54ec2056','2017-08-02 19:00:56','2017-07-02 19:00:56','1'),
('8','8','phone','15966755821','48e0114df5a0e45b17a8449d370c8b74','2017-06-27 19:54:24','[B@8e3e375','2e557efbab','2017-08-02 21:41:20','2017-07-02 21:41:20','1'),
('9','9','phone','18615625210','7f8527ed8dea916786b26ad818fdd91c','2017-07-01 11:58:24','[B@7f7340b3','30a5b80052','2017-11-25 21:38:49','2017-10-25 21:38:49','1'),
('10','10','phone','18518989105','eb51302b4ef181d1670d1e20aab4f77d','2017-07-03 16:48:08','[B@7a2ce1a0','30bce80d89','2017-11-30 09:42:34','2017-10-30 09:42:34','1');
DROP TABLE IF EXISTS  `tb_equipment`;
CREATE TABLE `tb_equipment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(10) DEFAULT NULL COMMENT 'user主键',
  `kidid` int(10) DEFAULT NULL COMMENT '孩子id',
  `number` varchar(200) DEFAULT '' COMMENT '设备编号',
  `maxtime` decimal(10,2) unsigned DEFAULT NULL COMMENT '最高温度',
  `mintime` decimal(10,2) unsigned DEFAULT NULL COMMENT '最低温度',
  `isminalarm` varchar(5) DEFAULT NULL COMMENT '是否低温报警(0:否1:是)',
  `ismaxalarm` varchar(5) DEFAULT NULL COMMENT '是否高温报警(0:否1:是)',
  `isnotice` varchar(5) DEFAULT '1' COMMENT '是否接受提醒(0:否1:是)',
  `isactive` varchar(5) DEFAULT NULL COMMENT '是否有效(0:无效 1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

insert into `tb_equipment`(`id`,`userid`,`kidid`,`number`,`maxtime`,`mintime`,`isminalarm`,`ismaxalarm`,`isnotice`,`isactive`) values
('1','1','1','001',38.00,36.00,'1','1','1',null),
('4','7','1','testnum',41.00,38.60,'1','1','1','1'),
('5','9','8','110',41.00,1.00,null,null,null,'1');
DROP TABLE IF EXISTS  `tb_kid`;
CREATE TABLE `tb_kid` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(10) DEFAULT NULL COMMENT 'user主键',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(5) DEFAULT '1' COMMENT '性别(0:女1:男)',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `headurl` text COMMENT '头像url',
  `healthIssue` varchar(5) DEFAULT '0' COMMENT '健康问题(0:没有1:有)',
  `number` int(10) DEFAULT '1' COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

insert into `tb_kid`(`id`,`userid`,`nickname`,`sex`,`birthday`,`headurl`,`healthIssue`,`number`) values
('1','7','测试孩子昵称2','1','2016-06-17','http://1234.com','1','1'),
('2','8','gggg','0','2017-06-30','/usr/local/tomcat/webapps/API/upload/344f3cb5-8883-46cd-8fc2-995ae62b30a5.jpeg','0','1'),
('3','8','a','0','2017-06-30','/usr/local/tomcat/webapps/API/upload/P70626-121549.jpg','0','2'),
('4','8','asd','0','2017-06-30','/usr/local/tomcat/webapps/API/upload/344f3cb5-8883-46cd-8fc2-995ae62b30a5.jpeg','0','3'),
('5','8','哈哈','0','2013-10-03','/usr/local/tomcat/webapps/API/upload/S70626-08423834.jpg','0','4'),
('6','9','就好好','0','2017-07-01','http://47.93.29.50:8001/pic/IMG_20170720_0749381500551793703.jpg','0','1'),
('7','7','测试桑','0','1991-01-01','/usr/local/tomcat/webapps/API/upload/wx_camera_1498812699865.jpg','0','2'),
('8','9','wkbbbb','0','2017-07-01','http://47.93.29.50:8001/pic/IMG_20170720_0749381500551896692.jpg','1','2'),
('9','8','vvv','0','2019-07-01','/usr/local/tomcat/webapps/API/upload/S70626-08423834.jpg','0','5'),
('10','7','123','1','2017-07-02','/usr/local/tomcat/webapps/API/upload/wx_camera_1498661732643.jpg','0','3'),
('11','10','122','1','2017-06-26','http://47.93.29.50:8001/pic/test1506411658865.jpg','0','1'),
('12','10','Sad','1','2019-09-26','http://47.93.29.50:8001/pic/test1506422342470.jpg','1','2'),
('13','10','Www','1','2017-09-26','http://47.93.29.50:8001/pic/test1506422392410.jpg','1','3');
DROP TABLE IF EXISTS  `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `isactive` varchar(5) DEFAULT '1' COMMENT '是否有效(0:无效 1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

insert into `tb_user`(`id`,`isactive`) values
('1','1'),
('2','1'),
('3','1'),
('4','1'),
('5','1'),
('6','1'),
('7','1'),
('8','1'),
('9','1'),
('10','1');
DROP TABLE IF EXISTS  `tb_tip`;
CREATE TABLE `tb_tip` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tipType` varchar(10) DEFAULT 'fever' COMMENT '贴士类型(fever,food,cool)',
  `number` int(10) DEFAULT '1' COMMENT '编号',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `imageUrl` varchar(255) DEFAULT NULL COMMENT '贴士图片url',
  `content` text COMMENT '贴士内容(html内容)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

insert into `tb_tip`(`id`,`tipType`,`number`,`title`,`imageUrl`,`content`) values
('1','fever','1','发烧1','http://xxx.jpg','测试内容1'),
('2','fever','2','发烧2','http://xxx.jpg','测试内容2'),
('3','food','1','饮食1','http://xxx.jpg','测试饮食1'),
('4','cool','1','降温1','http://xxx.jpg','测试降温1');
SET FOREIGN_KEY_CHECKS = 1;

