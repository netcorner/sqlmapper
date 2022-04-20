/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : sqlmapper2

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 20/04/2022 15:52:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL COMMENT '用户 ID',
  `Msg` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '消息内容',
  `AddTime` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2085 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of info
-- ----------------------------
BEGIN;
INSERT INTO `info` VALUES (1, 1, '业务', '2022-03-31 20:08:37');
INSERT INTO `info` VALUES (2, 1, 'xxx', '2020-08-21 14:41:33');
INSERT INTO `info` VALUES (3, 1, '22', '2020-08-19 16:49:02');
INSERT INTO `info` VALUES (4, 2, '33', '2019-11-30 13:37:47');
INSERT INTO `info` VALUES (5, 3, '44', '2016-02-29 09:31:34');
INSERT INTO `info` VALUES (2073, 1, ' 测试员ZZZ', '2022-04-20 08:58:07');
INSERT INTO `info` VALUES (2074, 1, ' 测试员2074', '2022-04-20 08:59:57');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
