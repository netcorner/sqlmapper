/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : test

 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 03/04/2019 23:05:50 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `a`
-- ----------------------------
DROP TABLE IF EXISTS `a`;
CREATE TABLE `a` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `a` varchar(11) DEFAULT '',
  `b` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2272 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `a`
-- ----------------------------
BEGIN;
INSERT INTO `a` VALUES ('2268', 'aaa423a', 'cba'), ('2269', 'lklk', '8989'), ('2270', 'aaa423a', 'cba'), ('2271', 'lklk', '8989');
COMMIT;

-- ----------------------------
--  Table structure for `b`
-- ----------------------------
DROP TABLE IF EXISTS `b`;
CREATE TABLE `b` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `a` varchar(11) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2366 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `b`
-- ----------------------------
BEGIN;
INSERT INTO `b` VALUES ('1', 'a'), ('2', 'b'), ('111', 'kkkk'), ('2361', '1'), ('2362', 'abc'), ('2363', 'abcx'), ('2364', 'abc'), ('2365', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
