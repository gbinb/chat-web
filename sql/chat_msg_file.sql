/*
Navicat MySQL Data Transfer

Source Server         : 172.18.10.210-开发
Source Server Version : 50729
Source Host           : 172.18.10.210:3306
Source Database       : hn_chat

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-01-11 16:03:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_msg_file
-- ----------------------------
DROP TABLE IF EXISTS `chat_msg_file`;
CREATE TABLE `chat_msg_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `msg_id` varchar(50) NOT NULL COMMENT '消息ID',
  `user_id` varchar(30) NOT NULL COMMENT '用户ID',
  `file_id` varchar(50) NOT NULL COMMENT '文件ID',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `file_format` varchar(10) DEFAULT NULL COMMENT '文件格式',
  `bucket` varchar(30) DEFAULT NULL COMMENT '存储桶',
  `file_url` varchar(200) DEFAULT NULL COMMENT '文件Url',
  `relative_path` varchar(100) DEFAULT NULL COMMENT '存储相对位置',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5值',
  `alive` int(11) DEFAULT '0' COMMENT '存活时长，单位秒',
  `destroy_time` datetime DEFAULT NULL COMMENT '销毁时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_read` char(1) DEFAULT '0' COMMENT '是否已读：0-未读 1-已读',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  PRIMARY KEY (`id`),
  KEY `msg_id` (`msg_id`),
  KEY `user_id` (`user_id`),
  KEY `file_id` (`file_id`),
  KEY `md5` (`md5`),
  KEY `destroy_time` (`destroy_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_msg_file
-- ----------------------------
