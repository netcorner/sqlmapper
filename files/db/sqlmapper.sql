/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : sqlmapper

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 20/04/2022 15:51:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL COMMENT '用户 ID',
  `Msg` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '消息内容',
  `AddTime` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of message
-- ----------------------------
BEGIN;
INSERT INTO `message` VALUES (1, 1, '业务', '2022-03-31 20:08:37');
INSERT INTO `message` VALUES (2, 1, 'xxx', '2020-08-21 14:41:33');
INSERT INTO `message` VALUES (3, 1, '22', '2020-08-19 16:49:02');
INSERT INTO `message` VALUES (4, 2, '33', '2019-11-30 13:37:47');
INSERT INTO `message` VALUES (5, 3, '44', '2016-02-29 09:31:34');
INSERT INTO `message` VALUES (2073, 1, ' 测试员ZZZ', '2022-04-20 08:58:07');
INSERT INTO `message` VALUES (2074, 1, ' 测试员2074', '2022-04-20 08:59:57');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) NOT NULL COMMENT '用户名',
  `RealName` varchar(500) DEFAULT NULL COMMENT '联系人名称',
  `Pwd` varchar(32) DEFAULT NULL COMMENT '密码',
  `LastLoginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `LastLoginIP` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'tester3', 'admin_1', '96E79218965EB72C92A549DD5A330112', '2022-03-31 20:08:37', '0:0:0:0:0:0:0:1');
INSERT INTO `user` VALUES (2, 'tester1', 'admin_2', '96E79218965EB72C92A549DD5A330112', '2020-08-21 14:41:33', '0:0:0:0:0:0:0:1');
INSERT INTO `user` VALUES (3, 'tester2', '22', '96E79218965EB72C92A549DD5A330112', '2020-08-19 16:49:02', '0:0:0:0:0:0:0:1');
INSERT INTO `user` VALUES (4, 'tester4', '33', '96E79218965EB72C92A549DD5A330112', '2019-11-30 13:37:47', '0:0:0:0:0:0:0:1');
INSERT INTO `user` VALUES (5, 'tester5', '44', '96E79218965EB72C92A549DD5A330112', '2016-02-29 09:31:34', '0:0:0:0:0:0:0:1');
INSERT INTO `user` VALUES (2073, 'abcx', ' 测试员ZZZ', '234567x', '2022-04-20 08:58:07', '127.0.0.1');
INSERT INTO `user` VALUES (2074, 'test', ' 测试员2074', '123456', '2022-04-20 08:59:57', '127.0.0.1');
INSERT INTO `user` VALUES (2075, 'test', ' 测试员2075', '123456', '2022-04-20 08:59:57', '127.0.0.1');
INSERT INTO `user` VALUES (2076, 'test', ' 测试员', '123456', NULL, '127.0.0.1');
INSERT INTO `user` VALUES (2077, 'test1', ' 测试员', '123456', '2022-04-20 09:11:28', '127.0.0.1');
INSERT INTO `user` VALUES (2078, 'test2', ' 测试员', '123456', '2022-04-20 09:11:28', '127.0.0.1');
INSERT INTO `user` VALUES (2079, 'abc', '测试员', '234567', '2022-04-20 09:27:15', '255.0.0.1');
INSERT INTO `user` VALUES (2080, 'abc', '测试员', '234567', '2022-04-20 09:27:57', '255.0.0.1');
INSERT INTO `user` VALUES (2081, 'abc', '测试员', '234567', '2022-04-20 09:28:15', '255.0.0.1');
INSERT INTO `user` VALUES (2082, 'abc', '测试员', '234567', '2022-04-20 09:41:05', '255.0.0.1');
INSERT INTO `user` VALUES (2083, '', ' 测试员2074', '', '2022-04-20 09:48:52', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
