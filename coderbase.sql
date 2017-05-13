/*
Navicat MySQL Data Transfer

Source Server         : LocationDB
Source Server Version : 50154
Source Host           : localhost:3306
Source Database       : coderbase

Target Server Type    : MYSQL
Target Server Version : 50154
File Encoding         : 65001

Date: 2016-09-18 11:22:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `uid` varchar(40) NOT NULL,
  `roleid` varchar(40) DEFAULT NULL COMMENT '角色ID',
  `userid` varchar(40) DEFAULT NULL COMMENT '用户id',
  `createdate` varchar(21) DEFAULT NULL COMMENT '创建时间',
  `base1` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='roleid\r\n\r\n1.学生\r\n2.讲师';

-- ----------------------------
-- Records of tb_role
-- ----------------------------

-- ----------------------------
-- Table structure for tb_subject
-- ----------------------------
DROP TABLE IF EXISTS `tb_subject`;
CREATE TABLE `tb_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_subject
-- ----------------------------
INSERT INTO `tb_subject` VALUES ('1', 'JAVA');
INSERT INTO `tb_subject` VALUES ('2', 'ANDROID');
INSERT INTO `tb_subject` VALUES ('3', 'IOS');
INSERT INTO `tb_subject` VALUES ('4', 'C++');
INSERT INTO `tb_subject` VALUES ('5', 'LINUX');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(40) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `sex` int(2) DEFAULT NULL,
  `usertype` int(10) DEFAULT NULL COMMENT '用户类型',
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `mobile` char(11) CHARACTER SET utf8 DEFAULT NULL,
  `score` int(255) DEFAULT NULL,
  `base` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `base2` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `base3` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `base4` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '张三', '123', '三', '1', '1', '1028940102@qq.com', '1863015978', '12', null, null, null, null);
INSERT INTO `tb_user` VALUES ('2', '2', '2', '2', '2', '2', '2', '2', '2', null, null, null, null);
