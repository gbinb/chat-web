/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.8-dsm
Source Server Version : 50647
Source Host           : 192.168.1.8:3306
Source Database       : chat_prod

Target Server Type    : MYSQL
Target Server Version : 50647
File Encoding         : 65001

Date: 2021-03-14 19:21:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_app_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `chat_app_upgrade`;
CREATE TABLE `chat_app_upgrade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(50) NOT NULL COMMENT '版本号',
  `version_type` varchar(20) NOT NULL COMMENT '版本类型：test-测试，release-发行版',
  `apk_url` varchar(200) NOT NULL COMMENT 'apk下载地址',
  `note` varchar(100) DEFAULT NULL COMMENT '版本说明',
  `force_upgrade` char(1) NOT NULL DEFAULT '0' COMMENT '强制升级：0-否 1-是',
  `is_skip` char(1) NOT NULL DEFAULT '1' COMMENT '是否跳过版本：0-否 1-是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `version` (`version`,`version_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of chat_app_upgrade
-- ----------------------------

-- ----------------------------
-- Table structure for chat_apply_friend
-- ----------------------------
DROP TABLE IF EXISTS `chat_apply_friend`;
CREATE TABLE `chat_apply_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `apply_id` varchar(32) NOT NULL COMMENT '申请编号',
  `apply_user_id` varchar(30) NOT NULL COMMENT '申请人ID',
  `target_user_id` varchar(30) DEFAULT NULL,
  `apply_nick_name` varchar(30) DEFAULT NULL COMMENT '申请人昵称',
  `apply_avatar` varchar(200) DEFAULT NULL COMMENT '申请人图像',
  `apply_country` varchar(20) DEFAULT NULL COMMENT '申请人国家',
  `leave_msg` varchar(100) DEFAULT NULL COMMENT '留言',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '是否通过：0-未处理, 1-通过，2-不通过',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `apply_id` (`apply_id`),
  UNIQUE KEY `apply_user_id` (`apply_user_id`,`target_user_id`) USING BTREE,
  KEY `target_user_id` (`target_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='申请加好友';

-- ----------------------------
-- Records of chat_apply_friend
-- ----------------------------
INSERT INTO `chat_apply_friend` VALUES ('29', '38182a2f66c44a1a88aff5b512d785ae', 'U210313E1HFP000203', 'U210313IDADJ000202', '华南虎', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, '加个好友', '1', '2021-03-13 17:22:29', '2021-03-13 17:23:29');

-- ----------------------------
-- Table structure for chat_friend
-- ----------------------------
DROP TABLE IF EXISTS `chat_friend`;
CREATE TABLE `chat_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `user_id` varchar(30) NOT NULL COMMENT '用户ID',
  `friend_id` varchar(30) NOT NULL COMMENT '好友ID',
  `friend_name` varchar(30) NOT NULL COMMENT '朋友昵称',
  `friend_mobile` varchar(15) DEFAULT NULL COMMENT '朋友电话',
  `friend_avatar` varchar(200) DEFAULT NULL COMMENT '朋友头像',
  `friend_country` varchar(50) DEFAULT NULL COMMENT '所在地区',
  `alias` varchar(30) DEFAULT NULL COMMENT '备注名',
  `tag` varchar(30) DEFAULT NULL COMMENT '标签',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `black` char(1) DEFAULT '0' COMMENT '黑名单：0-否 1-是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`friend_id`),
  KEY `friend_id` (`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_friend
-- ----------------------------
INSERT INTO `chat_friend` VALUES ('93', 'U210313IDADJ000202', 'U210313E1HFP000203', '华南虎', '17611266845', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '0', '2021-03-13 17:23:29');
INSERT INTO `chat_friend` VALUES ('94', 'U210313E1HFP000203', 'U210313IDADJ000202', '米酒', '18600783892', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '0', '2021-03-13 17:23:29');

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `group_id` varchar(30) NOT NULL COMMENT '群ID',
  `owner_id` varchar(30) NOT NULL COMMENT '群主ID',
  `name` varchar(100) NOT NULL COMMENT '群名称',
  `topic` varchar(200) DEFAULT NULL COMMENT '公告',
  `avatar` varchar(200) DEFAULT NULL COMMENT '群头像',
  `members` int(11) NOT NULL DEFAULT '0' COMMENT '成员人数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`),
  KEY `user_id` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_group
-- ----------------------------

-- ----------------------------
-- Table structure for chat_group_member
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_member`;
CREATE TABLE `chat_group_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `owner_id` varchar(30) NOT NULL COMMENT '群主ID',
  `group_id` varchar(30) NOT NULL COMMENT '群ID',
  `user_id` varchar(30) NOT NULL COMMENT '成员用户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_user` (`group_id`,`user_id`),
  KEY `group_id` (`group_id`),
  KEY `owner_id` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_group_member
-- ----------------------------

-- ----------------------------
-- Table structure for chat_msg_file
-- ----------------------------
DROP TABLE IF EXISTS `chat_msg_file`;
CREATE TABLE `chat_msg_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `user_id` varchar(30) NOT NULL COMMENT '用户ID',
  `file_id` varchar(100) NOT NULL COMMENT '文件ID',
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
  KEY `user_id` (`user_id`),
  KEY `file_id` (`file_id`),
  KEY `md5` (`md5`),
  KEY `destroy_time` (`destroy_time`)
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_msg_file
-- ----------------------------

-- ----------------------------
-- Table structure for chat_sequence
-- ----------------------------
DROP TABLE IF EXISTS `chat_sequence`;
CREATE TABLE `chat_sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `category` varchar(30) NOT NULL COMMENT '类别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_sequence
-- ----------------------------
INSERT INTO `chat_sequence` VALUES ('202', 'userId', '2021-03-13 17:18:17');
INSERT INTO `chat_sequence` VALUES ('203', 'userId', '2021-03-13 17:21:29');

-- ----------------------------
-- Table structure for chat_user
-- ----------------------------
DROP TABLE IF EXISTS `chat_user`;
CREATE TABLE `chat_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `user_id` varchar(30) NOT NULL COMMENT '用户编号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(15) NOT NULL COMMENT '手机号',
  `pass_word` varchar(50) NOT NULL COMMENT '密码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '图像',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `gender` char(1) DEFAULT NULL COMMENT '性别: M-男 W-女',
  `signature` varchar(50) DEFAULT NULL COMMENT '个性签名',
  `aes_key` varchar(32) DEFAULT NULL COMMENT 'aesKey',
  `status` char(1) NOT NULL COMMENT '状态：1-正常',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_ip` varchar(35) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_user
-- ----------------------------
INSERT INTO `chat_user` VALUES ('19', 'U210313IDADJ000202', '18600783892', '米酒', '18600783892', 'L/7xVUBE6rRDSd4F9tsd6sibL3w=', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '1', null, '2021-03-13 17:18:17', null, null, null);
INSERT INTO `chat_user` VALUES ('20', 'U210313E1HFP000203', '17611266845', '华南虎', '17611266845', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '1', null, '2021-03-13 17:21:29', null, null, null);
INSERT INTO `chat_user` VALUES ('21', 'U210313D1EFP000205', '18566666666', '测试一', '18566666666', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '1', null, '2021-03-14 08:29:36', null, null, null);
INSERT INTO `chat_user` VALUES ('22', 'U210456D1BOP000206', '15899999999', '测试二', '15899999999', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://minio.fetosoft.cn:8000/chat/default_avatar.png', null, null, null, null, '1', null, '2021-03-14 08:45:49', null, null, null);

-- ----------------------------
-- Table structure for chat_user_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_user_group`;
CREATE TABLE `chat_user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `user_id` varchar(30) NOT NULL COMMENT '成员用户ID',
  `group_id` varchar(30) NOT NULL COMMENT '群ID',
  `user_nick` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `user_avatar` varchar(200) DEFAULT NULL COMMENT '用户图像',
  `user_country` varchar(50) DEFAULT NULL COMMENT '用户地区',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_group` (`user_id`,`group_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for chat_user_key
-- ----------------------------
DROP TABLE IF EXISTS `chat_user_key`;
CREATE TABLE `chat_user_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(30) NOT NULL COMMENT '用户编号',
  `public_key` varchar(1000) NOT NULL COMMENT '公钥',
  `private_key` varchar(1000) NOT NULL COMMENT '私钥',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_user_key
-- ----------------------------
INSERT INTO `chat_user_key` VALUES ('17', 'U210313IDADJ000202', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDiLozcoTD4bjYcFSlMcjN8zoAND4DHfx+ykh8FZgE9f1INDPrmRrU2n5smdQKnMT2wvBw9g81WH3KZELvci34Wqf8aeT3Z0OV0JfGBNOdBstBO3a0bUYhCEMtWZcq2VBxs1hxqZiUlygEJrH9L3vOyhyCwg0L0nEVaL18b//B01wIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOIujNyhMPhuNhwVKUxyM3zOgA0PgMd/H7KSHwVmAT1/Ug0M+uZGtTafmyZ1AqcxPbC8HD2DzVYfcpkQu9yLfhap/xp5PdnQ5XQl8YE050Gy0E7drRtRiEIQy1ZlyrZUHGzWHGpmJSXKAQmsf0ve87KHILCDQvScRVovXxv/8HTXAgMBAAECgYEAoQ3Yi7nTrvJ7YR/IWoDvHK3GDIa/gk6I+ZSGtkdeFrY0bMqTVal/m7emuImVLAwqC1DHIx5Y9jxKU6fQvUgurQnGfUezHBXCXUK+N31/NWFMZW9xwqYUZ/m4Yz1sMZcyV/TqEN/ZzClA/MYL0Y8TP2ciAm5wFIHjjJ3XnJGOGMECQQD6WD/3pQC9ngr7JeFvO6mlQvJ+RLJZh9NSH7/YrHmieoSqTCKin7eRaygwVBqSFdfJb1RKSD3s2HY6DRoxdoInAkEA50qQ1LzTMJTi9Oflh/OHovyFWjkDJHEZeRNUg6npKNmdQuAwtoFWjK/1yaszQl+UcaboHmoR7NU/2UD3R0UV0QJAQKXhfvYhRtY1R730hWEG4iJztJ/Q3sXwkFialj2T+51f/QpEN3K/WNIvQqu1YNw7KU2SmswlEbCkd0FC7lsIKQJAIoMC12uRi7c7olWT71RMUeqiL1NAf8iMh3915euNifXmvFrdie4Erxt2bnAoRugiK08/ZYJoSFGNpGpIO484cQJBAL0t0aE6TBmeAM8i2Y/ue8+rjLNFSHcX2dBc4+zOvnfrffvBVHi/T9w43e/Abtjb2WnQpt5oAxpcMItj3uBbxAw=', '2021-03-13 17:18:17', null);
INSERT INTO `chat_user_key` VALUES ('18', 'U210313E1HFP000203', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOXiCsSjMNfFI+7KHRhcHZvowL4ASemJC/LAoF7O6CFOTsFGLc4xf8C/IiG6yDyIp5zQFMKbvvmL/APSPoy6W2ZPOzLvOqBw5A7MihB2QyRJrWF2lhTvYy1wUvOveMDhutAbzhyQlGzO/9NhopLsbpOLBLf75L6OxWYutkzQm6sQIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI5eIKxKMw18Uj7sodGFwdm+jAvgBJ6YkL8sCgXs7oIU5OwUYtzjF/wL8iIbrIPIinnNAUwpu++Yv8A9I+jLpbZk87Mu86oHDkDsyKEHZDJEmtYXaWFO9jLXBS8694wOG60BvOHJCUbM7/02Gikuxuk4sEt/vkvo7FZi62TNCbqxAgMBAAECgYEAgJ3q+JvECY/iIbTX8Bjd62Bo1yWYJsuAISWhHH9TbjOAKQO12QZUmcEVC9lifmADMkyRuoQNpESkHwFdWqf4WSxJLiauToBq7Fh862dLup9ZwHP/2e4F8aP+IsmgFQwa6/uo0GtQPjNpdkWoZcxt2I9d7siMr4s5oyrBlg0HcJECQQD7RcVd8+uC/iaLgfadZWKINeQHH5nSlnRWkPA+25aJ5R4QM3nyQ3/ysw9PcdxkA6GF2eopWcNCUyKLVYVAHQGFAkEAkQvTy9oH9zZ9qnoftIdXLdPzoCcym91omLYuSYpmOFxQQ1Otdd+AULddHzbd+TTYVn3alFVon+rY7Y0ty75GPQJAf05WNMfEZtXFSaFLBWu8hC5pnYJROPSpdMJyNiajGrJOjP7Hpgq05I38D9AsTS/ZwisqDFFCMHRWaCmoHVeU0QJAP/qjlcu6r8UNcWElExCP19siH4aqwC3NsW7KxA2UQVAW51vFq7TGeNQo81fUWdSBiMVC4SM7Xy7MORyJEtH/UQJAJIDHuefc72MV2UtPh94DTOCE/TIDI7vo4IvUJU00n5PnHFCvIgNGTzfR/ewBVGJC5Quve4T3XlM11T6JgDAfKw==', '2021-03-13 17:21:30', null);

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `admin_id` varchar(30) NOT NULL COMMENT '管理员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机',
  `mail` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `locked` char(1) DEFAULT NULL COMMENT '锁定状态：0-正常，1-锁定',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `login_ip` varchar(32) DEFAULT NULL COMMENT '登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_id` (`admin_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
