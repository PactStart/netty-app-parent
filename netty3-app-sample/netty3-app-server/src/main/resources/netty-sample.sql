/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : netty-sample

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2017-05-11 17:04:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `playerId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `playerName` varchar(255) DEFAULT NULL,
  `passward` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `exp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
