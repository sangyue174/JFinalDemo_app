/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50622
Source Host           : 127.0.0.1:3306
Source Database       : app

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2017-06-18 18:46:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_equipment
-- ----------------------------
DROP TABLE IF EXISTS `tb_equipment`;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_equipment
-- ----------------------------
INSERT INTO `tb_equipment` VALUES ('1', '1', '1', '001', '38.00', '36.00', '1', '1', '1', null);
INSERT INTO `tb_equipment` VALUES ('4', '7', '1', 'testnum', '41.00', '38.60', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for tb_kid
-- ----------------------------
DROP TABLE IF EXISTS `tb_kid`;
CREATE TABLE `tb_kid` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(10) DEFAULT NULL COMMENT 'user主键',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(5) DEFAULT '1' COMMENT '性别(0:女1:男)',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `headurl` text COMMENT '头像url',
  `healthIssue` varchar(5) DEFAULT '0' COMMENT '健康问题(0:没有1:有)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_kid
-- ----------------------------
INSERT INTO `tb_kid` VALUES ('1', '7', '测试孩子昵称2', '1', '2016-06-17', 'http://1234.com', '1');

-- ----------------------------
-- Table structure for tb_temp_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_temp_record`;
CREATE TABLE `tb_temp_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `kidid` int(10) DEFAULT NULL COMMENT '孩子id',
  `equipid` int(10) DEFAULT NULL COMMENT '设备id',
  `recordTime` datetime DEFAULT NULL COMMENT '测温时间',
  `temperature` decimal(10,2) unsigned DEFAULT NULL COMMENT '温度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_temp_record
-- ----------------------------
INSERT INTO `tb_temp_record` VALUES ('1', null, '1', '2017-06-14 00:10:16', '36.00');
INSERT INTO `tb_temp_record` VALUES ('2', null, '1', '2017-06-14 00:27:45', '34.00');
INSERT INTO `tb_temp_record` VALUES ('3', null, '1', '2017-06-14 00:27:55', '35.00');
INSERT INTO `tb_temp_record` VALUES ('4', null, '1', '2017-06-15 00:35:08', '38.00');

-- ----------------------------
-- Table structure for tb_tip
-- ----------------------------
DROP TABLE IF EXISTS `tb_tip`;
CREATE TABLE `tb_tip` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tipType` varchar(10) DEFAULT 'fever' COMMENT '贴士类型(fever,food,cool)',
  `number` int(10) DEFAULT '1' COMMENT '编号',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `imageUrl` varchar(255) DEFAULT NULL COMMENT '贴士图片url',
  `content` text COMMENT '贴士内容(html内容)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_tip
-- ----------------------------
INSERT INTO `tb_tip` VALUES ('1', 'fever', '1', '发烧1', 'http://xxx.jpg', '测试内容1');
INSERT INTO `tb_tip` VALUES ('2', 'fever', '2', '发烧2', 'http://xxx.jpg', '测试内容2');
INSERT INTO `tb_tip` VALUES ('3', 'food', '1', '饮食1', 'http://xxx.jpg', '测试饮食1');
INSERT INTO `tb_tip` VALUES ('4', 'cool', '1', '降温1', 'http://xxx.jpg', '测试降温1');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `isactive` varchar(5) DEFAULT '1' COMMENT '是否有效(0:无效 1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '1');
INSERT INTO `tb_user` VALUES ('2', '1');
INSERT INTO `tb_user` VALUES ('3', '1');
INSERT INTO `tb_user` VALUES ('4', '1');
INSERT INTO `tb_user` VALUES ('5', '1');
INSERT INTO `tb_user` VALUES ('6', '1');
INSERT INTO `tb_user` VALUES ('7', '1');

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES ('1', '1', 'phone', '123', null, '2017-05-22 21:42:23', null, null, null, '2017-05-22 21:42:23', '1');
INSERT INTO `tb_user_auth` VALUES ('2', '1', 'qq', '1147468768', 'xxx', '2017-06-09 23:40:19', '123', 'xxx', '2017-07-09 23:40:19', '2017-06-09 23:40:19', '1');
INSERT INTO `tb_user_auth` VALUES ('7', '7', 'phone', '18615566651', '3f0b17baeaaf7d69f9d731d83ae5bf32', '2017-06-11 00:16:16', '[B@1571e0f6', '2de4e96238', '2017-07-18 15:25:35', '2017-06-18 11:27:50', '1');
