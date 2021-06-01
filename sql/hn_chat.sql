/*
Navicat MySQL Data Transfer

Source Server         : 172.18.10.210-开发
Source Server Version : 50729
Source Host           : 172.18.10.210:3306
Source Database       : hn_chat

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-01-08 18:03:39
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='申请加好友';

-- ----------------------------
-- Records of chat_apply_friend
-- ----------------------------
INSERT INTO `chat_apply_friend` VALUES ('17', '5b6b1e7da44a4b4aba0bf9828c576807', 'U201229NYIQ8000007', 'U2101012FC8G000013', '旺旺', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, '赵培伦', '1', '2021-01-08 13:58:30', '2021-01-08 13:58:45');
INSERT INTO `chat_apply_friend` VALUES ('18', '3c7f209715d0499880863c9ac2b40206', 'U2101012FC8G000013', 'U201229NYIQ8000007', '赵培伦', 'http://119.45.139.102:9000/chat/single.png', null, 'zhaopeilun', '1', '2021-01-08 13:53:29', '2021-01-08 13:53:40');
INSERT INTO `chat_apply_friend` VALUES ('19', 'b4a3a4f89af74244bd50cb1f7b3375a8', 'U201216000002', 'U201216000001', '长江', 'http://172.18.10.211:9000/chat/202101041924169709183.png', '湖北', '加个好友吧', '1', '2021-01-08 11:22:57', '2021-01-08 11:23:03');
INSERT INTO `chat_apply_friend` VALUES ('20', 'd9a7097fa6ac44e49e843cb9660a8178', 'U201216000001', 'U201228EO1GO000006', '米酒', 'http://172.18.10.211:9000/chat/202101081133467435465.png', '北京大兴', '加个好友吧', '1', '2021-01-08 13:31:02', '2021-01-08 13:31:08');
INSERT INTO `chat_apply_friend` VALUES ('21', 'c04ef2f8b2b249dcb5840bf6e2fe2078', 'U201229NYIQ8000007', 'U201216000002', '旺旺', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, '加个好友', '1', '2021-01-08 15:50:33', '2021-01-08 15:51:04');
INSERT INTO `chat_apply_friend` VALUES ('22', '1ba5f4890422435b90cfd15ea1359a6f', 'U201229NYIQ8000007', 'U201228EO1GO000006', '旺旺', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, '加个好友', '1', '2021-01-08 15:51:47', '2021-01-08 15:52:02');
INSERT INTO `chat_apply_friend` VALUES ('23', '1e39ee97fa3c494b8a1643cc3be33e07', 'U210108ZNO3Z000035', 'U201229NYIQ8000007', '黄河', 'http://172.18.10.211:9000/chat/202101081554525687953.png', '山东', '加个好友', '1', '2021-01-08 15:56:05', '2021-01-08 15:56:32');
INSERT INTO `chat_apply_friend` VALUES ('24', '5de1a64fc54b4e3e8ff14225ae1150bc', 'U210108ETB4B000036', 'U201229NYIQ8000007', '长城', 'http://172.18.10.211:9000/chat/202101081602024933892.png', '北京怀柔', '加个好友吧', '1', '2021-01-08 16:02:41', '2021-01-08 16:02:49');
INSERT INTO `chat_apply_friend` VALUES ('25', 'b75ecfc016d042288bfe2054fa8c0c5d', 'U210108DWTH2000037', 'U201229NYIQ8000007', '故宫', 'http://172.18.10.211:9000/chat/202101081605438814044.png', '博物院', '加个好友吧', '1', '2021-01-08 16:06:00', '2021-01-08 16:06:08');

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
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_friend
-- ----------------------------
INSERT INTO `chat_friend` VALUES ('7', 'U201216000001', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2020-12-29 14:31:01');
INSERT INTO `chat_friend` VALUES ('8', 'U201229NYIQ8000007', 'U201216000001', '米酒', '18600567899', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, null, null, '0', '2020-12-29 14:31:01');
INSERT INTO `chat_friend` VALUES ('9', 'U201216000001', 'U201226S51OC000005', '我叫啥？', '15103565596', 'http://172.18.10.211:9000/chat/202101081123281172321.png', null, '李成', '山西晋城', null, '0', '2020-12-29 14:31:04');
INSERT INTO `chat_friend` VALUES ('10', 'U201226S51OC000005', 'U201216000001', '米酒', '18600567899', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, null, null, '0', '2020-12-29 14:31:04');
INSERT INTO `chat_friend` VALUES ('11', 'U201216000001', 'U201229MAC9Z000008', '小小盖', '18511023521', 'http://172.18.10.211:9000/chat/202101081134379560131.jpg', null, null, null, null, '0', '2020-12-29 14:32:12');
INSERT INTO `chat_friend` VALUES ('12', 'U201229MAC9Z000008', 'U201216000001', '米酒', '18600567899', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, null, null, '0', '2020-12-29 14:32:12');
INSERT INTO `chat_friend` VALUES ('13', 'U201229MAC9Z000008', 'U201226S51OC000005', '我叫啥？', '15103565596', 'http://172.18.10.211:9000/chat/202101081123281172321.png', null, null, null, null, '0', '2020-12-29 14:33:15');
INSERT INTO `chat_friend` VALUES ('14', 'U201226S51OC000005', 'U201229MAC9Z000008', '小小盖', '18511023521', 'http://172.18.10.211:9000/chat/202101081134379560131.jpg', null, null, null, null, '0', '2020-12-29 14:33:15');
INSERT INTO `chat_friend` VALUES ('15', 'U201229MAC9Z000008', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2020-12-29 14:33:19');
INSERT INTO `chat_friend` VALUES ('16', 'U201229NYIQ8000007', 'U201229MAC9Z000008', '小小盖', '18511023521', 'http://172.18.10.211:9000/chat/202101081134379560131.jpg', null, null, null, null, '0', '2020-12-29 14:33:19');
INSERT INTO `chat_friend` VALUES ('17', 'U201229NYIQ8000007', 'U201226S51OC000005', '我叫啥？', '15103565596', 'http://172.18.10.211:9000/chat/202101081123281172321.png', null, null, null, null, '0', '2020-12-29 14:35:16');
INSERT INTO `chat_friend` VALUES ('18', 'U201226S51OC000005', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2020-12-29 14:35:16');
INSERT INTO `chat_friend` VALUES ('21', 'U201216000001', 'U2101012FC8G000013', '赵培伦', '18300702318', 'http://119.45.139.102:9000/chat/single.png', null, null, null, null, '0', '2021-01-03 23:30:45');
INSERT INTO `chat_friend` VALUES ('54', 'U201216000001', 'U201216000002', '长江', '18699999999', 'http://172.18.10.211:9000/chat/202101081652340575807.png', '湖北', null, null, null, '0', '2021-01-08 11:23:03');
INSERT INTO `chat_friend` VALUES ('55', 'U201216000002', 'U201216000001', '米酒', '18600567899', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, null, null, '0', '2021-01-08 11:23:03');
INSERT INTO `chat_friend` VALUES ('61', 'U201228EO1GO000006', 'U201216000001', '米酒', '18600567899', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', '米酒', '湖北', null, '0', '2021-01-08 13:31:08');
INSERT INTO `chat_friend` VALUES ('62', 'U201216000001', 'U201228EO1GO000006', '北京', '15311689563', 'http://172.18.10.211:9000/chat/202101081332240382766.png', '大兴', null, null, null, '0', '2021-01-08 13:31:08');
INSERT INTO `chat_friend` VALUES ('71', 'U2101012FC8G000013', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 13:58:45');
INSERT INTO `chat_friend` VALUES ('72', 'U201229NYIQ8000007', 'U2101012FC8G000013', '赵培伦', '18300702318', 'http://119.45.139.102:9000/chat/single.png', null, null, null, null, '0', '2021-01-08 13:58:45');
INSERT INTO `chat_friend` VALUES ('73', 'U201216000002', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 15:51:04');
INSERT INTO `chat_friend` VALUES ('74', 'U201229NYIQ8000007', 'U201216000002', '长江', '18699999999', 'http://172.18.10.211:9000/chat/202101081652340575807.png', '湖北', null, null, null, '0', '2021-01-08 15:51:04');
INSERT INTO `chat_friend` VALUES ('75', 'U201228EO1GO000006', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 15:52:02');
INSERT INTO `chat_friend` VALUES ('76', 'U201229NYIQ8000007', 'U201228EO1GO000006', '北京', '15311689563', 'http://172.18.10.211:9000/chat/202101081332240382766.png', '大兴', null, null, null, '0', '2021-01-08 15:52:02');
INSERT INTO `chat_friend` VALUES ('77', 'U201229NYIQ8000007', 'U210108ZNO3Z000035', '黄河', '18655555555', 'http://172.18.10.211:9000/chat/202101081554525687953.png', '山东', null, null, null, '0', '2021-01-08 15:56:32');
INSERT INTO `chat_friend` VALUES ('78', 'U210108ZNO3Z000035', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 15:56:32');
INSERT INTO `chat_friend` VALUES ('79', 'U201229NYIQ8000007', 'U210108ETB4B000036', '长城', '18677777777', 'http://172.18.10.211:9000/chat/202101081602024933892.png', '北京怀柔', null, null, null, '0', '2021-01-08 16:02:49');
INSERT INTO `chat_friend` VALUES ('80', 'U210108ETB4B000036', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 16:02:49');
INSERT INTO `chat_friend` VALUES ('81', 'U201229NYIQ8000007', 'U210108DWTH2000037', '故宫', '18688888888', 'http://172.18.10.211:9000/chat/202101081605438814044.png', '博物院', null, null, null, '0', '2021-01-08 16:06:08');
INSERT INTO `chat_friend` VALUES ('82', 'U210108DWTH2000037', 'U201229NYIQ8000007', '旺旺', '15696801082', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '0', '2021-01-08 16:06:08');

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `group_id` varchar(30) NOT NULL COMMENT '群ID',
  `user_id` varchar(30) NOT NULL COMMENT '群主ID',
  `name` varchar(100) NOT NULL COMMENT '群名称',
  `topic` varchar(200) DEFAULT NULL COMMENT '公告',
  `avatar` varchar(200) DEFAULT NULL COMMENT '群头像',
  `members` int(11) NOT NULL DEFAULT '0' COMMENT '成员人数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_group
-- ----------------------------
INSERT INTO `chat_group` VALUES ('25', 'G210108JR3LE000034', 'U201216000001', 'Chat聊天测试', null, 'http://172.18.10.211:9000/certificate/group.png', '7', '2021-01-08 13:35:00', '2021-01-08 13:35:34');
INSERT INTO `chat_group` VALUES ('26', 'G210108JJ2YO000038', 'U201216000002', '米酒、长江、旺旺', null, 'http://172.18.10.211:9000/certificate/group.png', '3', '2021-01-08 17:41:38', null);

-- ----------------------------
-- Table structure for chat_group_member
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_member`;
CREATE TABLE `chat_group_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动ID',
  `owner_id` varchar(30) NOT NULL COMMENT '群主ID',
  `group_id` varchar(30) NOT NULL COMMENT '群ID',
  `user_id` varchar(30) NOT NULL COMMENT '成员用户ID',
  `user_nick` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `user_avatar` varchar(200) DEFAULT NULL COMMENT '用户图像',
  `user_country` varchar(50) DEFAULT NULL COMMENT '用户地区',
  `user_alias` varchar(30) DEFAULT NULL COMMENT '用户备注的好友别名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_user` (`group_id`,`user_id`),
  KEY `group_id` (`group_id`),
  KEY `owner_id` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_group_member
-- ----------------------------
INSERT INTO `chat_group_member` VALUES ('83', 'U201216000001', 'G210108JR3LE000034', 'U201216000001', '米酒', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('84', 'U201216000001', 'G210108JR3LE000034', 'U201216000002', '长江', 'http://172.18.10.211:9000/chat/202101081652340575807.png', '湖北', null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('85', 'U201216000001', 'G210108JR3LE000034', 'U201226S51OC000005', '我叫啥？', 'http://172.18.10.211:9000/chat/202101081123281172321.png', null, '李成', '2021-01-08 13:35:00', '2021-01-08 14:34:09');
INSERT INTO `chat_group_member` VALUES ('86', 'U201216000001', 'G210108JR3LE000034', 'U201228EO1GO000006', '北京', 'http://172.18.10.211:9000/chat/202101081332240382766.png', '大兴', null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('87', 'U201216000001', 'G210108JR3LE000034', 'U201229NYIQ8000007', '旺旺', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('88', 'U201216000001', 'G210108JR3LE000034', 'U201229MAC9Z000008', '小小盖', 'http://172.18.10.211:9000/chat/202101081134379560131.jpg', null, null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('89', 'U201216000001', 'G210108JR3LE000034', 'U2101012FC8G000013', '赵培伦', 'http://119.45.139.102:9000/chat/single.png', null, null, '2021-01-08 13:35:00', null);
INSERT INTO `chat_group_member` VALUES ('90', 'U201216000002', 'G210108JJ2YO000038', 'U201216000001', '米酒', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, '2021-01-08 17:41:38', null);
INSERT INTO `chat_group_member` VALUES ('91', 'U201216000002', 'G210108JJ2YO000038', 'U201216000002', '长江', 'http://172.18.10.211:9000/chat/202101081652340575807.png', '湖北', null, '2021-01-08 17:41:38', null);
INSERT INTO `chat_group_member` VALUES ('92', 'U201216000002', 'G210108JJ2YO000038', 'U201229NYIQ8000007', '旺旺', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, '2021-01-08 17:41:38', null);

-- ----------------------------
-- Table structure for chat_sequence
-- ----------------------------
DROP TABLE IF EXISTS `chat_sequence`;
CREATE TABLE `chat_sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `category` varchar(30) NOT NULL COMMENT '类别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_sequence
-- ----------------------------
INSERT INTO `chat_sequence` VALUES ('1', 'userId', '2020-12-16 18:38:02');
INSERT INTO `chat_sequence` VALUES ('2', 'userId', '2020-12-16 19:09:21');
INSERT INTO `chat_sequence` VALUES ('3', 'userId', '2020-12-24 11:05:12');
INSERT INTO `chat_sequence` VALUES ('4', 'groupId', '2020-12-24 11:44:44');
INSERT INTO `chat_sequence` VALUES ('5', 'userId', '2020-12-26 17:03:46');
INSERT INTO `chat_sequence` VALUES ('6', 'userId', '2020-12-28 18:50:31');
INSERT INTO `chat_sequence` VALUES ('7', 'userId', '2020-12-29 14:30:26');
INSERT INTO `chat_sequence` VALUES ('8', 'userId', '2020-12-29 14:31:08');
INSERT INTO `chat_sequence` VALUES ('9', 'groupId', '2020-12-29 15:50:40');
INSERT INTO `chat_sequence` VALUES ('10', 'groupId', '2020-12-29 16:10:14');
INSERT INTO `chat_sequence` VALUES ('11', 'groupId', '2020-12-29 16:11:31');
INSERT INTO `chat_sequence` VALUES ('12', 'userId', '2020-12-29 17:48:35');
INSERT INTO `chat_sequence` VALUES ('13', 'userId', '2021-01-01 19:06:41');
INSERT INTO `chat_sequence` VALUES ('14', 'groupId', '2021-01-04 19:27:11');
INSERT INTO `chat_sequence` VALUES ('15', 'groupId', '2021-01-07 18:22:59');
INSERT INTO `chat_sequence` VALUES ('16', 'groupId', '2021-01-07 18:24:58');
INSERT INTO `chat_sequence` VALUES ('17', 'groupId', '2021-01-07 18:35:19');
INSERT INTO `chat_sequence` VALUES ('18', 'groupId', '2021-01-07 18:37:05');
INSERT INTO `chat_sequence` VALUES ('19', 'groupId', '2021-01-07 18:39:02');
INSERT INTO `chat_sequence` VALUES ('20', 'groupId', '2021-01-07 18:40:47');
INSERT INTO `chat_sequence` VALUES ('21', 'groupId', '2021-01-07 18:44:00');
INSERT INTO `chat_sequence` VALUES ('22', 'groupId', '2021-01-07 18:53:44');
INSERT INTO `chat_sequence` VALUES ('23', 'groupId', '2021-01-07 18:54:53');
INSERT INTO `chat_sequence` VALUES ('24', 'groupId', '2021-01-07 18:56:15');
INSERT INTO `chat_sequence` VALUES ('25', 'groupId', '2021-01-07 18:57:36');
INSERT INTO `chat_sequence` VALUES ('26', 'groupId', '2021-01-07 18:59:25');
INSERT INTO `chat_sequence` VALUES ('27', 'groupId', '2021-01-07 19:00:19');
INSERT INTO `chat_sequence` VALUES ('28', 'groupId', '2021-01-07 19:11:26');
INSERT INTO `chat_sequence` VALUES ('29', 'groupId', '2021-01-07 19:12:08');
INSERT INTO `chat_sequence` VALUES ('30', 'groupId', '2021-01-07 19:13:03');
INSERT INTO `chat_sequence` VALUES ('31', 'groupId', '2021-01-07 19:18:00');
INSERT INTO `chat_sequence` VALUES ('32', 'groupId', '2021-01-08 11:16:08');
INSERT INTO `chat_sequence` VALUES ('33', 'groupId', '2021-01-08 11:29:42');
INSERT INTO `chat_sequence` VALUES ('34', 'groupId', '2021-01-08 13:35:00');
INSERT INTO `chat_sequence` VALUES ('35', 'userId', '2021-01-08 15:53:24');
INSERT INTO `chat_sequence` VALUES ('36', 'userId', '2021-01-08 16:01:44');
INSERT INTO `chat_sequence` VALUES ('37', 'userId', '2021-01-08 16:03:53');
INSERT INTO `chat_sequence` VALUES ('38', 'groupId', '2021-01-08 17:41:38');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_user
-- ----------------------------
INSERT INTO `chat_user` VALUES ('4', 'U201216000001', '18600567899', '米酒', '18600567899', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081433336498953.png', '北京大兴', null, '今天的创新就是明日的标准', null, '1', null, '2020-12-16 18:38:15', '2021-01-08 14:33:35', null, null);
INSERT INTO `chat_user` VALUES ('5', 'U201216000002', '18699999999', '长江', '18699999999', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081652340575807.png', '湖北', null, '楚天下', null, '1', null, '2020-12-16 19:09:21', '2021-01-08 16:52:38', null, null);
INSERT INTO `chat_user` VALUES ('6', 'U201224IrTbw000003', '15311111111', '黄河', '15311111111', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://119.45.139.102:9000/chat/single.png', null, null, null, null, '1', null, '2020-12-24 11:05:21', null, null, null);
INSERT INTO `chat_user` VALUES ('7', 'U201226S51OC000005', '15103565596', '我叫啥？', '15103565596', 'KYodVD567RvyKrA1gjtkJv+gRF8=', 'http://172.18.10.211:9000/chat/202101081123281172321.png', null, null, null, null, '1', null, '2020-12-26 17:03:46', '2021-01-08 11:23:31', null, null);
INSERT INTO `chat_user` VALUES ('8', 'U201228EO1GO000006', '15311689563', '北京', '15311689563', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081332240382766.png', '大兴', null, null, null, '1', null, '2020-12-28 18:50:31', '2021-01-08 14:04:07', null, null);
INSERT INTO `chat_user` VALUES ('9', 'U201229NYIQ8000007', '15696801082', '旺旺', '15696801082', 'BpAhLjLLAtavw7fIH67r/SqwcWk=', 'http://172.18.10.211:9000/chat/202101081121322941305.jfif', null, null, null, null, '1', null, '2020-12-29 14:30:26', '2021-01-08 11:21:39', null, null);
INSERT INTO `chat_user` VALUES ('10', 'U201229MAC9Z000008', '18511023521', '小小盖', '18511023521', 'qTmkNw726yRhkMmEw4EWK9RwRZ8=', 'http://172.18.10.211:9000/chat/202101081134379560131.jpg', null, null, null, null, '1', null, '2020-12-29 14:31:08', '2021-01-08 11:34:41', null, null);
INSERT INTO `chat_user` VALUES ('11', 'U201229BF9LG000012', '13313093253', '农民工', '13313093253', '1rIYpXkBjht5ih3K0R19241OwxM=', 'http://119.45.139.102:9000/chat/single.png', null, null, null, null, '1', null, '2020-12-29 17:48:35', null, null, null);
INSERT INTO `chat_user` VALUES ('12', 'U2101012FC8G000013', '18300702318', '赵培伦', '18300702318', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://119.45.139.102:9000/chat/single.png', null, null, null, null, '1', null, '2021-01-01 19:06:41', null, null, null);
INSERT INTO `chat_user` VALUES ('13', 'U210108ZNO3Z000035', '18655555555', '黄河', '18655555555', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081554525687953.png', '山东', null, null, null, '1', null, '2021-01-08 15:53:25', '2021-01-08 15:55:01', null, null);
INSERT INTO `chat_user` VALUES ('14', 'U210108ETB4B000036', '18677777777', '长城', '18677777777', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081602024933892.png', '北京怀柔', null, null, null, '1', null, '2021-01-08 16:01:44', '2021-01-08 16:02:19', null, null);
INSERT INTO `chat_user` VALUES ('15', 'U210108DWTH2000037', '18688888888', '故宫', '18688888888', 'm307R8vJ+7orXRkLOaFAE++IxHk=', 'http://172.18.10.211:9000/chat/202101081605438814044.png', '博物院', null, null, null, '1', null, '2021-01-08 16:03:53', '2021-01-08 16:05:46', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_user_key
-- ----------------------------
INSERT INTO `chat_user_key` VALUES ('2', 'U201216000001', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfQz5KGbMxbSMEqWobAhL0z2jPBQfNTEKxkQDzko+zHixxAuZOANBmqzTA6t/bCPhg+vQ0iqNi6miiq3dJlieOrFi+yUQW6rRMQXyqaRmK2BcsOh46TUxoKmPB1hlo0A9r9vXlf8IrATOSOtHJ/dzvh3nCa4N+ICs7hbgJhBV59QIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ9DPkoZszFtIwSpahsCEvTPaM8FB81MQrGRAPOSj7MeLHEC5k4A0GarNMDq39sI+GD69DSKo2LqaKKrd0mWJ46sWL7JRBbqtExBfKppGYrYFyw6HjpNTGgqY8HWGWjQD2v29eV/wisBM5I60cn93O+HecJrg34gKzuFuAmEFXn1AgMBAAECgYAu4IIDGZFu6Oq3wo3iqTefKzV0SczEFsBCt6WKDAeT9zupf+r95qtwrM/4QKnnNLq3AObEWFJDGS/3QXT5CeNWWNOe5wIqCigQrFqBaP/OqMbi0RgQSJvvTcPOeQJFQWnQ4gKUtr9VXkJoFCL5UaTTOMj7umCAqxCnj7nTcDq0AQJBAO4gBIGkXkoH3qVjmb+FWGbivHtYRckuzLJToq7XmGtq5dPMJcS8YSF8aTdtysbT4Ep6qZr5rxqTsWTR10dgGoECQQCrN7/Kzt90ZlGuh3Ua2FXlGCXaEtRq5cpEWAxMKuDGHj4oNpX2Q6gyeilgrDL20+kTYCDrIW5BKWhPAt96SN11AkBjENzvFnPHtshw5CP1osMpYpDpe8rkrNyqH9Nhi/40Si+9UoQv+fMq3Dhlv/6Jg2IfejKPb+riUqmNeSNn5VqBAkAjGh7b+WWee4ureGHtywRxH25DMzERaWL6eAKtau0CMRkVLXu5LU3Ca4EoLKWWLQPP0Rlumz/7Y/3alQA0FdQxAkEAkox1/HWBajjFQQXTxzEpoqbZgKg3t/aRn8+w1wDJxND0To2XauDGeTsdhdYbCcDdgP4iXSEwzRarUcBV8dCYQQ==', '2020-12-16 18:38:15', null);
INSERT INTO `chat_user_key` VALUES ('3', 'U201216000002', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCS8ckPBM0ghWrcqZqRj/Jnf7+H+S6rbrXDuH8X6PhoTm6tzTk/yme68YATreFZAajAAr+rtG3aEB1YdOlsK8osOcg+er/jJq22RRdLULpzxv6bopT88d7Xan2I1ViBXZc5Yqs5tIYrq0ibqin4Bg0WEMB0NMvmulwLKXSmGGJlZQIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJLxyQ8EzSCFatypmpGP8md/v4f5LqtutcO4fxfo+GhObq3NOT/KZ7rxgBOt4VkBqMACv6u0bdoQHVh06Wwryiw5yD56v+MmrbZFF0tQunPG/puilPzx3tdqfYjVWIFdlzliqzm0hiurSJuqKfgGDRYQwHQ0y+a6XAspdKYYYmVlAgMBAAECgYBdB4wl4QFIzI66Avpjm4F4+wy0NhLQ3/ddYOm9pnXV+4ngqOPMugTv+sL+MoBPjFhcr6DPQRynCLq7bND29cY5PLh28pf3zdo8pYPuvO2SLBi5QqEOUzQRRYRWTN24hIFk6v0LLrPo5+cfkrIc1O9pOW607SgjuWwSKJ/GN46YQQJBAO69aNPVS7qFRo70wjpJvdtTrdCUJjfDkN+IXwR8eVfOFAaPiBantmxaOdAY1iupTGoOAUq3a3yidmlFOxu+tnECQQCdkW3lj5/h98n2NoypNFe031ji6dAEwU7DigKGImRMx0esz+s4st6INlWAJagYAIYHXodAZ3zBZoK2JA9V4KA1AkAgA14PKcbh2AVCHif9UO4YfvwLwBHv4FWI4+x52Ycb8xJUXuLMxuh7Vw3xjaMKb8VDY2Vg83+CUva13wayDbzBAkAS55ru2v+MxgUGNF9GAdCwE8f/WZwqpeLEVS+eVkwAZjx0QHHxWIyYfSRe/qWfU5jBE5dYo9H1Bk6SedsZK50tAkEAkyHY+pbf76J/ZzToZDqyA7ODh67g/Uv9EpkTkaXD+lQJWnP3aLiHXcvQ5wEQUlfvErLLe52nKz4DDdhJzvUcoA==', '2020-12-16 19:09:21', null);
INSERT INTO `chat_user_key` VALUES ('4', 'U201224IrTbw000003', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDr5onTsd2s+tp4SbKJ/xbf2w/VPG6cahyQ/nQehlh6MEHI0+mydv97ZvGkHbeYgI7BiScCk9AO9wbRA0FXsjLF25e5fSJ55ks90UPxswun1++ggKisWNk6qLor4zQRF8u9xesDkCC6bnvjTJnrYzPMvmCqivvw+av95YQGPMeCMQIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOvmidOx3az62nhJson/Ft/bD9U8bpxqHJD+dB6GWHowQcjT6bJ2/3tm8aQdt5iAjsGJJwKT0A73BtEDQVeyMsXbl7l9InnmSz3RQ/GzC6fX76CAqKxY2TqouivjNBEXy73F6wOQILpue+NMmetjM8y+YKqK+/D5q/3lhAY8x4IxAgMBAAECgYBtrFflwOrD0jih9fl4qMz0tD7vL1Jpi/fN/U71JhaacDcVbFhZLXPn5ltvppVF5Hb6SY6GZzwyyOw1XmMRp3psmAEAU4vk2ZLf3IZQyBHh+P7+OZ5OVcNufKqICz420ZqnJKtG+17T43/lSZL85HxxmbKq6gfOo/ttXoJMpfQUQQJBAPf0g4AKYyLGkfypWnSrhs9IGIwcoAZRVl0F271DRYL9gekOzJI5UXrGit3VAoziB/WQUgZqmwG8r68FTW4ivwkCQQDzjeaTsHsSMlya1rihQLsAS3OvPjiRD3J/mm3TblQoMpbYWEXkOH7Ng8iwCJEXX4WiSdvGsZO1QY7XaZ1jsEvpAkEAng6PhJ8acqCBjr2lm5l7ZcZfX3Ef0OcoVkPVvt+S/9z5Ysa8QLmo5sqI2r9d0NCEolRM9SofhQvyMW7/3KTpgQJAFyjvPCwUB+7yD2dLt0nIfoauQYPRLToi0S+GMgzTw97LqVb7jnAw3qohc1vdMDPNKVmMpKBOQ3Ywdxt78/bXwQJBAIgCr+klFqfkxVTNu6OioJGgTwr3AvWXENS1+SbU+YRVoJ3+ba+2xTAbFccOqFzJUrl+Oa/v0N0s4oCQv3WOArk=', '2020-12-24 11:05:21', null);
INSERT INTO `chat_user_key` VALUES ('5', 'U201226S51OC000005', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCH6/y97k7NI5CyYaJgmI/kyNL7aviQJHRndk5+jUEFwdrsK+tTXZSa8sRtCR+rGl7RcJmj1mAXgmxu6Y924+rY4kvA60Z+hPTzha4XcGeqnaUB0S8GXXq7MVms+veP+wlIkyNhTOc658aAZPstmCX3Ap0E/o9RbCdH6jXF2ENwXwIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIfr/L3uTs0jkLJhomCYj+TI0vtq+JAkdGd2Tn6NQQXB2uwr61NdlJryxG0JH6saXtFwmaPWYBeCbG7pj3bj6tjiS8DrRn6E9POFrhdwZ6qdpQHRLwZdersxWaz694/7CUiTI2FM5zrnxoBk+y2YJfcCnQT+j1FsJ0fqNcXYQ3BfAgMBAAECgYA3C5YeoR0erNGTvECTWBWmNMJk+YxSa8EMiXf+flwf5cMwNBUofNSK79gmxDQkChjuPuip1k6yFqqtPD9Sibj4p25Li+QDno0ohAv6/egmITZULtjDPagLfmnX4JbZmKewfX8loTGd563BXSqoi0tOvb7rrAlGJHWnUxlhOp8HUQJBAMJACqdPAz9Zy/oS19R9LuL5/+fsozbpbV5TcMEGqgiZ52VQ0Q1MKlOYPddBSnGHQNKrgVAZtDJqJX+rDhmZeHcCQQCzITi37bmdWliUsoNPw4PsoITgHa99ibOx91SJ0VE1kI7+PD9bv7AGZVNRc5Mh8/et7QtkuG+nQVjFTH3kLqlZAkBW6+1YfKTJXldGKdRmgzXN32zBzUQjuER2ZwQXe/HuM2A2F/nT53cxPPi/L7ouHwXEv3X37+iQfqsU++gtUF11AkAaAzAGXao/tHf6l/XDpoPI4AKLvSPX2iBTLrSDEvd1DNoHB164Bx0bwKkZwevS2WfVRQALj1D/e9jVRedeNHx5AkEAvlqrAbZMJt4m0HxFn9wEz514FK71aMNle16105rlizMaRIeYHHkD1QrHcuPgazdRb715md/RJEPcv8epqDi/sQ==', '2020-12-26 17:03:46', null);
INSERT INTO `chat_user_key` VALUES ('6', 'U201228EO1GO000006', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCk/qQgqiDyXU4TfXr0+9Mgo6xKlCyharWOiHJzTzGdeOIf8Q3MGARLgbWF671O5CksYw/mtFXUjQqJOsGfg6Qe0NcQRJQBoQD+vhnSD/FnPjgLkRWv2WiE52G6emXjXnSYRjq8YmA3lfcyEkwIUSliEDWO+7E59JqnMMObezbJPQIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKT+pCCqIPJdThN9evT70yCjrEqULKFqtY6IcnNPMZ144h/xDcwYBEuBtYXrvU7kKSxjD+a0VdSNCok6wZ+DpB7Q1xBElAGhAP6+GdIP8Wc+OAuRFa/ZaITnYbp6ZeNedJhGOrxiYDeV9zISTAhRKWIQNY77sTn0mqcww5t7Nsk9AgMBAAECgYBgKf4U/7NmLxWTbH4ejSt3iY2ghg4hImWhJku2g0DCrDL3NPK6soDGVVosLMLQWVDaCv64kwC3emDarMEvLHWZhHlw+0My9fHW3+jo374PllFQ0PbgSR69j9xHrhowLdeUOAZGqBvd25Lxdjsow9XtdajHvBj25mVZGlveUGvQgQJBANfQoPzB586hqzdtjeqbmd6J2ikCiNxUQiMLFEycySRSPm7qJ2XiMrDaCg/P8DWJ/4D6w59IP1rqXeOA1r1GsWECQQDDt4jYJi8SuiymHShjWaB+dQXxCnplbj0UXNFx7TmQ3h3R2x+HizqrbiagvnAxj4TnX8D5Ok42mmhH5jpoofldAkEAokYPJr6ujGJnmW/Y6/VAB2LkFT4Appr8d9kW1Fx3qcXliASsxyEEOJJaSnVyCu/OnK7xdM0gRj4/hpcT4JJNQQJAaVjFI/OJqv7jQZa4QqHfrbG3vQLy1t37qpY7dqNGNSAZ1PephQAAWH419JgNM+AYnaIA8SXdK675KppWHjPxUQJBAMSQLCtGvtsAw1f4XeJTgXw1LDBn/oa5qoneGUCtEgcfV5Uu4UEuzpPSiiHgvd5Ai5o4k66leWiykPsdezg0Ytc=', '2020-12-28 18:50:31', null);
INSERT INTO `chat_user_key` VALUES ('7', 'U201229NYIQ8000007', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCqN2CSzXjZcL8aV8QjmCP6duMfLqW+Q+FNJPuTz+kc0+/Les8u60u59OvPv23hbeOq8VP4uAtj0aOBqn+HLD+rE9b1+b5BDK3x22E927CK040in5V/PPJo1luINiYxMlFPReNCBi1hAoEVd5n84zI5/U08v0BGPa+K08OtYng0wIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIKo3YJLNeNlwvxpXxCOYI/p24x8upb5D4U0k+5PP6RzT78t6zy7rS7n068+/beFt46rxU/i4C2PRo4Gqf4csP6sT1vX5vkEMrfHbYT3bsIrTjSKflX888mjWW4g2JjEyUU9F40IGLWECgRV3mfzjMjn9TTy/QEY9r4rTw61ieDTAgMBAAECgYABTMTHYxAqLvz9nd7AP2pehCkXALaqgdfRr3A5UriDXlJQyJgqhoN2j//fInVLuhzVYogX/13TljVRfGd8i7SHEFjTyYI4+XbqLqHgFHcA/SFHczGUazYriuttyWvPA8wWxcJTZSbh2Hqz6B2+RZmrlSq3bh9+hmpVEF+9iJM/4QJBAOjqFL2rLdqrzLSxOPgfgGzXqwekhThaUMulckT5rZ/gQe6JvHv75XhGXPijsn8K7GxCS4+wEJ8UViwxZSwU1i0CQQCPnDHEonmdypc5+g3TsaF+wCAZODNm66E/w2l2up/plMPCV/ZyFuyRC79i3RvMex1VGlVtJWvMyXtZ5F+Y2vL/AkEA5qosMEq+9rj92lVJHjHUdfaAoukEJnMLYSNQr461rIfjw+EUkZU+BIdqu7Miqz6eNfjR7FNpoqH5VXr1v1pDYQJADSktTm3tY06LqV6HId1nhWwqBOWnoEj70BIieiUEPq3JeugAx9fd+jTvZyoIQCcQOxpctdH2HSBh040gTavTFQJACvnLpDk4hjyhz3dYpMgv0aWNgbpyE+OJ/A8oeyBTBV3hsVE+dFZgJWCiIYwogRrwGB0woCcMv6QoGdRMqrGPrg==', '2020-12-29 14:30:27', null);
INSERT INTO `chat_user_key` VALUES ('8', 'U201229MAC9Z000008', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIaxwqIipQ8qFu/yV91sMB1jkt1zFhxFCeZkLqmQrTFgHju1axCJIoHalBdy22ltn7LBJSO1aweZjuEic3KmyofUUUC5orYXP8pVCRAs/9p06lS3RmeCj76D8RlrHb9klbkmMFEGroeUspwNrNWM0f5VsyfeRxIjMCrY5MdgDnpwIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMhrHCoiKlDyoW7/JX3WwwHWOS3XMWHEUJ5mQuqZCtMWAeO7VrEIkigdqUF3LbaW2fssElI7VrB5mO4SJzcqbKh9RRQLmithc/ylUJECz/2nTqVLdGZ4KPvoPxGWsdv2SVuSYwUQauh5SynA2s1YzR/lWzJ95HEiMwKtjkx2AOenAgMBAAECgYBlFRCpd33cQeOF9g3UwCKupHtGUYjIhJkcqvsJE5NQD4N4dp4sv+Pnc75QFGnr832RnxLGgsMgUtP9Dw7UVgNiunPcieJf0PQqr7Us2LK/9Q1BD4Q5dOxxHwnsxhD56SAF97+C3EyygcaCamg/islByiSCMj0QLZRN9Shs6Xx40QJBAPKtLpLziua7F954u+ExzIXBHcRP5Tcu2ln7sYqCvhV4jFZ6a6/e5XJ08d3BirbPqIRCcpT+CU/+f78T5p7n4gMCQQDTa/2XRGSR7RTjl97v9fO7I+893amYiq58aSv4j3amQ8QxCe2wiimL0wqackPdK6MQr9Gvr7krKMoMpjfKKySNAkA/snlRFR/WZDFlsacO+vVF/mSDzLzLeT7S85+sTZj7JcMgR83QQd9T5aI+pLh2N5dx78cHmI51MsXxL5YPC6plAkEAn6XcSG2Mw+SS8Yms/uZqtdXcwoqvllCika+ZVIiAv+xMKfulULoDxWIHj7jZJhndeKxLWq8G1GBoskSRvEf3wQJBANL2jYitYaYjYWwxVrEkJIp79Kx2qEqla567Kk/efyjpvZrpuwnbfHZgacBBl257THLd45RAsRLBAE+BQFDlw0s=', '2020-12-29 14:31:08', null);
INSERT INTO `chat_user_key` VALUES ('9', 'U201229BF9LG000012', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChVcnvUNEQ7RbDdO6A7NRFh8OCohbg/EDvzzJd09TZpDGdN0ERqwyidF/+LZVIVwxAysUXap+2tbhaAylYG+mhNK48ypQ8MxvOetUK2BaE2DXkdngD7hV9vokTvbavXiFy+MMo9dtqhmLidYcHNCRcmkuTtwdDJ/pbAZkbe0/TnQIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKFVye9Q0RDtFsN07oDs1EWHw4KiFuD8QO/PMl3T1NmkMZ03QRGrDKJ0X/4tlUhXDEDKxRdqn7a1uFoDKVgb6aE0rjzKlDwzG8561QrYFoTYNeR2eAPuFX2+iRO9tq9eIXL4wyj122qGYuJ1hwc0JFyaS5O3B0Mn+lsBmRt7T9OdAgMBAAECgYEAgwEGWC1nljbupQfuIwIr28nCF/FGYqpykEfOviGkVJXfM6oKTC54s/GLgPnCnlsQuAvuvnT1+4bTi0PF7qW7rsDoN+hne97+hHUp2sz30+iBle1Y5Cmlc0Clxt0brrRHJKNA/0vvQoOhsESvrKxU2quPlCVcwCZydduC6U3w9AECQQDNKhDyjAYxPO9n71AxEKdAmGga+FUNp8cx0SR7Dhf9FCYcSBW5ScvpunJc6verlhPL35C7CrEPhrIcWQVolfVlAkEAyU+PDendTraF2UFmVpPK9szAomSo3GFCS/GgHz2MEJdDa8VC5TcwJaSlykFOquB0Nw0IbOV0D+VIxUQQwhb92QJBAJ0e0CfQUJW4wufoAJPy2wO/NEsjT4/Lo+Nk9fk/i+OHYMBb/DcQaxeucBL054QCxVpm9qInx/3J+/F1w4ya+3kCQE4z5Nu/1OQGDbsXJ91MLpVvdwne1qY99hYweEaKj0XGrT9JczI+HSsSagFHG3tQFHVqT7KV9F9Ub8+KIIR1kAkCQDgaVEzDBspS/P/JpJ1tWRKXV/bUTjWwHSzFRf9j5LGtDlR5Jx8VHbLPwwW5rTh9a1dNFkOfmwn6iN6Zs8zJX9U=', '2020-12-29 17:48:35', null);
INSERT INTO `chat_user_key` VALUES ('10', 'U2101012FC8G000013', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE2KerLKEbNaNxA60LGAt+gi8ioR0v9jvLKU9/o80kqJpjtbKpBm8ejy1VbFV80kNjQVosOW/PoLFQuAf5gutoyvd3VXR0aKB/vyIWXeQIb6T05hKunC2DXuo28v8mlEucG4lefznuhEJl0tlHfAnMb29Lx+R2+yWQys4iHwcdLQIDAQAB', 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAITYp6ssoRs1o3EDrQsYC36CLyKhHS/2O8spT3+jzSSommO1sqkGbx6PLVVsVXzSQ2NBWiw5b8+gsVC4B/mC62jK93dVdHRooH+/IhZd5AhvpPTmEq6cLYNe6jby/yaUS5wbiV5/Oe6EQmXS2Ud8Ccxvb0vH5Hb7JZDKziIfBx0tAgMBAAECgYAwTWphhd+qOg2MaQ7BKuP7ArDKwZQQPVKuYv3h5hgQDTHdsVIdvRYoIV0VY0eOFBGmhDuaOuQZtU7jj5ZutZiMPaCjEwaS/905rutroXpYgLleu30GGjzgyp2AIGQ/5XwoetIXGhkKCTu//EXIcHVI2qFIx378yk3Fqm35KGnzYQJBAPJuQWxhP6AcFbn7zp2M8OcFeNLMsPO0j155BL8Y1VW5Iz9yK674Ae2/5nqm3xRZyh7GBQt3lWAPsgc8EQHbRdkCQQCMSC9kl/A0Yy30CNPEycPi44m7PwMc4tlBrrxqUhyVhDHbql7ekOKlxHVaZBkpFv14hx6GzDalXFMRWvVnLhl1AkB387vlT1z28M6DPqel5vXPqTJ2QxePDhkeKo0BrNzp1HgvTWN16Hqz047Lw0N/w0mx3lLisKG1bS6uS7cyjvdJAkB92Tx7ODuMI/XT2h9xibisUYj8bLk0LubNIB90Gpn+IIxCDwPxE3XhVPAa2XYhALkAaeee8pGLo9n51eoWWKsJAkBOQ9eas3zfd3f7yKMsQxO2UtBwB42Qjl+IK+x9T+AkeTWoKMkoOoDVdC6KDH9Rlv+W7kLNA+d1ewzIXRpp0yfP', '2021-01-01 19:06:41', null);
INSERT INTO `chat_user_key` VALUES ('11', 'U210108ZNO3Z000035', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQSw0uDTtXp+qocmuFq+osH1GBtoXlU1kccy7omz/6qxrRpV1mFK2YfUlWvZwmYHsZLHJIkSpVGm7u1V1hYaTd9Vd6orwGkoUz0atWX6TGuhbonVde5y4fPPphUh0CuL7dSDFut/ZMDi1w7bm3J1HEuOvKGjcg12tu6IvKKiO6zwIDAQAB', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJBLDS4NO1en6qhya4Wr6iwfUYG2heVTWRxzLuibP/qrGtGlXWYUrZh9SVa9nCZgexksckiRKlUabu7VXWFhpN31V3qivAaShTPRq1ZfpMa6FuidV17nLh88+mFSHQK4vt1IMW639kwOLXDtubcnUcS468oaNyDXa27oi8oqI7rPAgMBAAECgYAjmFAVBzCXbBXoHenGXOV0ovkxXXxkWQ5HhWaR/RJoXONNylRjBUDZuDZqTnYBxZU4XY2Htz/RJKcvBbV2e2b28+m4OIP6VX/3BEKrKfswqpWXfc5zR7quhfZdTgzuiya+j8DfqjGiZKAW4jrT+66QTNzBKCIGF8k+Bk30fV6gSQJBAPvgdX1qd2V8DGE/KzOnplqC2m/5rg7reGzliTKJDGABvaYvOGJ2hGBDGKZ1mXm2MvhubFxcVMCv8gG0cM3fzI0CQQCSp73Ofl+6Pr8Vsc2UDIoKw3KQJNe/cqyhOm5/yTiM4MGZLor4Mjphyk2ZyQ+Xz3nRo0pAOBeDOgKmJA5EWmPLAkEAtkJ64hDras0zrB7opQ04bCBp7kMO947eanZCLAZNpR89W4ap114Jdhq3OKS6NP9prKlC0iah0Xi/twoZ4m+CdQJAJu/SPRjgYZp3MVNbuYr6u5DYwdyGS5Yurz/MRrEuCnlB7WGmE++CAKStpb70HOJA/Z/U/uflljm4qlO4Vf2JsQJBALEf8hRAU+Y8VmGc+PWcv0Ff5dRnFr8PSNE5rYWFBDIAw7echIG769h9vrm/nJbcnb1PqOTIVVLxZ+fmxL/a1pA=', '2021-01-08 15:53:25', null);
INSERT INTO `chat_user_key` VALUES ('12', 'U210108ETB4B000036', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMbLg0R6FNXadHvqiqsS6wfaTL+GnMXYxl5zXWuhn8j8BJHJy8Ea1U6Q4WQ3eV56SgElLcKg2Vx9hEBD4Ko3r1Y3XLrK1iJjg9P0i7nXfr1UbANGfsZvwlyRcMVfN92ip2sBSrzaQwP9AK4WaUkYk0x6RcTdDUSLZNuW2bfVNT+wIDAQAB', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMxsuDRHoU1dp0e+qKqxLrB9pMv4acxdjGXnNda6GfyPwEkcnLwRrVTpDhZDd5XnpKASUtwqDZXH2EQEPgqjevVjdcusrWImOD0/SLudd+vVRsA0Z+xm/CXJFwxV833aKnawFKvNpDA/0ArhZpSRiTTHpFxN0NRItk25bZt9U1P7AgMBAAECgYEArK1dgBPNpitpBUqA8QaP+Nd5IC6rN80BroZ6zPIQx22qMFfx8U9BSgs/MgE1f7PXNLulsIH5Tb8s6G9o9NWZIzFZq5dWut4UWIDtdY8/wzU+j1myEnUBgSafqwh5aM7AtUaZM5lVRM5uiLla30bUtHXaE5dRjxUml1wQCkGgDPECQQD4o8SyKje00xxxBP5kkZyaoxMd9DH5Jpn5j3kkqlRba7ez1MUQg1RjN3T/H6u4GhXHB8FcgGF7xi9WxeuMwtTjAkEA0nnh8Sfic6fFkNlAccl71erPVtNqMP8KMHFy8QrIZFsqN96KA2Q501KXJseObjtyeDMpw6JVs2YtH/OOxB5ICQJBAMEGcqdWOxT4Z7YeCFiO6qLqQucYMsuX+iVSCtw6F285us2zOqHNMvArl3qQXcF5DrsK/EQRrpxmiU22LV2Dy70CQCR/BfKvG9E6MkPfqBETAuohATrpBezhDH9B7NBnMkBUnol5iSz1YCGInLUYvVXX8Q1usbs+luW4vfJgZU0UgyECQQCGe/EPp7cTgo9bkjThz6bLU27ADoFHUbYl+nkTiO3SNwGhxaE+fQMBtf0hW0np+B+MIoWMyi/gn4p6wZSDsNp/', '2021-01-08 16:01:44', null);
INSERT INTO `chat_user_key` VALUES ('13', 'U210108DWTH2000037', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBtS7fNmvThfLSzraIiQ8G/7wlhvlM2hVo+Ge1jw5K03/5uDmy7bDDnXWT6bmAhpNpn7CQ2+nSWh456CYEggNk86geZd7ltz/hSROSrsfZWYzqarnKZ8bLP2PNYMtUhlR9fw3oWsY39Dc/+XXUFY/9ZzazEddJdNcIK8AF3xCThQIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIG1Lt82a9OF8tLOtoiJDwb/vCWG+UzaFWj4Z7WPDkrTf/m4ObLtsMOddZPpuYCGk2mfsJDb6dJaHjnoJgSCA2TzqB5l3uW3P+FJE5Kux9lZjOpqucpnxss/Y81gy1SGVH1/Dehaxjf0Nz/5ddQVj/1nNrMR10l01wgrwAXfEJOFAgMBAAECgYAVIqfjmWOSkRYTQbm+n87CDjPjTY4b9sFcXHXRwODMUpBc6acFP/P4vBSnqIMgrU0h/fJweFUugJ7CQtDHFURmGDRljbHtP1fto2aDXq05FKGsnkGK8PJ4CbJGPFqXStcpOWz6R4LOmkkxX9itS6m+MRMomgE6JTuqrdIjTFrE4QJBAMW+H7E6xWcyv+WKHvWmZWVsGAQsokA5aRzjyUJWu9KKu9ew4JUA4/zaHutX+QQ+ra3Noy2m4OgxYmPC2h1ZB9kCQQCn69KYLKx43zMxPUwwNfR/GfW2o5DzF1H5NlnUK2TEmxS0IsLEwwxDUBip9Y4Pfquja18FG23WFCNP9tC/AamNAkAX32QkGVxLoSzZoVsrfPMw02Py3RCZgFTYUAe0IpR6TpVu7MOwOXNMy2iAMRa3FHoTSYgxsrvkTFH8iUGb2WvBAkBUBVNigTfrYwMiaCFvHhAwaLThpgYpHEoQAHoCB5RsK4y4dYwvF8lTBQjSQePLLP6EznzE+WyD6Z+hq0XXzOUhAkEAkVAdA6S92bwtVu1Wf2Bw5oxnwDYt/icujDg6lmLeoyHv5exRnPq9EmJ6jIkBwmih0Ftjy7+6EfV1nIH/dNM1pg==', '2021-01-08 16:03:53', null);
