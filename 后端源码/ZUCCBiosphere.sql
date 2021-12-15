/*
Navicat MySQL Data Transfer

Source Server         : ZUCCBiosphere
Source Server Version : 50736
Source Host           : 121.40.227.132:3306
Source Database       : ZUCCBiosphere

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2021-12-13 23:31:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acfcrecord
-- ----------------------------
DROP TABLE IF EXISTS `acfcrecord`;
CREATE TABLE `acfcrecord` (
  `user_ID` varchar(250) NOT NULL,
  `activity_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`user_ID`,`activity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acfcrecord
-- ----------------------------
INSERT INTO `acfcrecord` VALUES ('1234', '17');
INSERT INTO `acfcrecord` VALUES ('a4384c8b488baf812739acc730a24957', '17');
INSERT INTO `acfcrecord` VALUES ('a4384c8b488baf812739acc730a24957', '18');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_ID` varchar(20) NOT NULL,
  `admin_Password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`admin_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for animalcityfriendsclub
-- ----------------------------
DROP TABLE IF EXISTS `animalcityfriendsclub`;
CREATE TABLE `animalcityfriendsclub` (
  `activity_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_ID` varchar(20) DEFAULT NULL,
  `user_ID` varchar(250) DEFAULT NULL,
  `activity_Name` varchar(20) DEFAULT NULL,
  `activity_Content` varchar(100) DEFAULT NULL,
  `activity_Type` varchar(20) DEFAULT NULL,
  `activity_CurrentParticipantsNum` bigint(20) DEFAULT NULL,
  `activity_TotalParticipantsNum` bigint(20) DEFAULT NULL,
  `activity_Location` varchar(100) DEFAULT NULL,
  `activity_StartDate` datetime DEFAULT NULL,
  `activity_Image` longblob,
  `activity_ReleaseDate` datetime DEFAULT NULL,
  PRIMARY KEY (`activity_ID`),
  KEY `FK_Review2` (`admin_ID`),
  CONSTRAINT `FK_Review2` FOREIGN KEY (`admin_ID`) REFERENCES `admin` (`admin_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of animalcityfriendsclub
-- ----------------------------
INSERT INTO `animalcityfriendsclub` VALUES ('17', null, 'a4384c8b488baf812739acc730a24957', '植物养护计划', 'water flowers', '拍照', '10', '20', null, '2021-12-06 15:34:35', null, '2021-12-05 15:34:44');
INSERT INTO `animalcityfriendsclub` VALUES ('18', null, 'a4384c8b488baf812739acc730a24957', '植物养护计划', 'water flowers', '养护', null, null, null, '2021-12-04 15:34:53', null, '2021-12-06 12:36:09');
INSERT INTO `animalcityfriendsclub` VALUES ('19', null, 'a4384c8b488baf812739acc730a24957', '你猜我要干嘛', '芜湖起飞', '投喂', null, null, null, '2021-12-07 19:25:46', null, '2021-12-07 19:25:49');

-- ----------------------------
-- Table structure for browse
-- ----------------------------
DROP TABLE IF EXISTS `browse`;
CREATE TABLE `browse` (
  `user_ID` varchar(20) NOT NULL,
  `pos_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`user_ID`,`pos_ID`),
  KEY `FK_Browse2` (`pos_ID`),
  CONSTRAINT `FK_Browse` FOREIGN KEY (`user_ID`) REFERENCES `user` (`user_ID`),
  CONSTRAINT `FK_Browse2` FOREIGN KEY (`pos_ID`) REFERENCES `popularizationofscience` (`pos_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of browse
-- ----------------------------

-- ----------------------------
-- Table structure for commentrecord
-- ----------------------------
DROP TABLE IF EXISTS `commentrecord`;
CREATE TABLE `commentrecord` (
  `user_ID` varchar(250) NOT NULL,
  `post_ID` bigint(20) NOT NULL,
  `Comment_Date` datetime NOT NULL,
  `Comment_Con` varchar(500) DEFAULT NULL,
  `Comment_Acced` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`user_ID`,`post_ID`,`Comment_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commentrecord
-- ----------------------------
INSERT INTO `commentrecord` VALUES ('1234', '13', '2021-12-07 10:22:34', '你笑啥', 'a4384c8b488baf812739acc730a24957');
INSERT INTO `commentrecord` VALUES ('2', '11', '2021-12-13 22:24:00', '98', '233');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 22:02:34', '', null);
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 22:19:32', '987', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 22:23:35', '987', '');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 22:43:40', 'niuwa', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 22:44:41', 'ldzNB', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 23:03:35', 'zhende', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 23:05:29', '?', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 23:07:09', '好哇', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 23:08:19', '在吗', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '78', '2021-12-13 23:28:00', '当然能啦', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '78', '2021-12-13 23:28:53', '我觉得不能', '9311f91797025294c7c1b62142c7b37b');
INSERT INTO `commentrecord` VALUES ('a4384c8b488baf812739acc730a24957', '13', '2021-12-06 21:23:46', '哈哈哈哈', 'a4384c8b488baf812739acc730a24957');

-- ----------------------------
-- Table structure for favoriterecord
-- ----------------------------
DROP TABLE IF EXISTS `favoriterecord`;
CREATE TABLE `favoriterecord` (
  `user_ID` varchar(250) NOT NULL,
  `post_ID` bigint(20) NOT NULL,
  `FaRec_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_ID`,`post_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favoriterecord
-- ----------------------------
INSERT INTO `favoriterecord` VALUES ('1', '233', '2021-12-12 22:13:37');
INSERT INTO `favoriterecord` VALUES ('2', '233', '2021-12-12 22:13:39');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '15', '2021-12-13 13:56:52');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '38', '2021-12-13 13:56:35');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '51', '2021-12-13 13:56:29');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '52', '2021-12-13 13:48:35');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '53', '2021-12-13 13:48:33');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '54', '2021-12-13 13:48:27');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '55', '2021-12-13 14:06:52');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '56', '2021-12-13 14:07:33');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '58', '2021-12-13 12:58:18');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '59', '2021-12-13 12:58:13');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '60', '2021-12-13 14:07:13');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '61', '2021-12-13 15:23:46');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '65', '2021-12-13 19:21:26');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '75', '2021-12-13 21:15:42');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '76', '2021-12-13 21:16:09');
INSERT INTO `favoriterecord` VALUES ('9311f91797025294c7c1b62142c7b37b', '77', '2021-12-13 21:05:36');
INSERT INTO `favoriterecord` VALUES ('a4384c8b488baf812739acc730a24957', '52', '2021-12-13 14:45:56');
INSERT INTO `favoriterecord` VALUES ('f71301fccc7a4281c176342524e2c6c3', '77', '2021-12-13 19:57:28');

-- ----------------------------
-- Table structure for operationrecord
-- ----------------------------
DROP TABLE IF EXISTS `operationrecord`;
CREATE TABLE `operationrecord` (
  `Ope_ID` bigint(20) NOT NULL,
  `admin_ID` varchar(20) DEFAULT NULL,
  `Ope_Rec` varchar(100) DEFAULT NULL,
  `Ope_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`Ope_ID`),
  KEY `FK_add` (`admin_ID`),
  CONSTRAINT `FK_add` FOREIGN KEY (`admin_ID`) REFERENCES `admin` (`admin_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operationrecord
-- ----------------------------

-- ----------------------------
-- Table structure for popularizationofscience
-- ----------------------------
DROP TABLE IF EXISTS `popularizationofscience`;
CREATE TABLE `popularizationofscience` (
  `pos_ID` bigint(20) NOT NULL,
  `admin_ID` varchar(20) DEFAULT NULL,
  `pos_Type` varchar(20) DEFAULT NULL,
  `pos_Name` varchar(20) DEFAULT NULL,
  `pos_NickName` varchar(20) DEFAULT NULL,
  `pos_Character` varchar(20) DEFAULT NULL,
  `pos_Sex` varchar(20) DEFAULT NULL,
  `pos_Condition` varchar(100) DEFAULT NULL,
  `pos_Appearance` varchar(100) DEFAULT NULL,
  `pos_Location` varchar(100) DEFAULT NULL,
  `pos_Image` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`pos_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of popularizationofscience
-- ----------------------------
INSERT INTO `popularizationofscience` VALUES ('1', '', '动物', '中华田园猫', '哈士奇', '乖巧', '母', '健康', '漂亮', '', 'https://c-ssl.duitang.com/uploads/item/201912/31/20191231121259_dckjf.thumb.1000_0.jpg');
INSERT INTO `popularizationofscience` VALUES ('2', '', '动物', '中华田园猫', '菲菲', '怕生', '母', '健康', '邋遢', '', 'https://scpic.chinaz.net/files/pic/pic9/201912/zzpic22142.jpg');
INSERT INTO `popularizationofscience` VALUES ('3', '', '动物', '中华田园猫', '拉菲', '贪吃', '母', '健康', '多斑点', '', 'https://scpic.chinaz.net/files/pic/pic9/202002/zzpic23067.jpg');
INSERT INTO `popularizationofscience` VALUES ('4', '', '植物', '松树', '松树', '', '', '高', '叶细小', '', 'https://scpic.chinaz.net/files/pic/pic9/201911/zzpic21268.jpg');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `post_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_ID` varchar(200) DEFAULT NULL,
  `post_Theme` varchar(40) DEFAULT NULL,
  `post_Content` varchar(200) DEFAULT NULL,
  `post_Date` datetime DEFAULT NULL,
  `post_LikeNum` bigint(20) DEFAULT NULL,
  `post_CommentNum` bigint(20) DEFAULT NULL,
  `post_UserAvatarUrl` varchar(200) DEFAULT NULL,
  `post_ReportNum` bigint(20) DEFAULT NULL,
  `post_isTop` tinyint(1) DEFAULT NULL,
  `post_isEssential` tinyint(1) DEFAULT NULL,
  `post_isHot` tinyint(1) DEFAULT NULL,
  `post_Image1` varchar(200) DEFAULT NULL,
  `post_Image2` varchar(200) DEFAULT NULL,
  `post_Image3` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`post_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('13', 'a4384c8b488baf812739acc730a24957', '吐槽', '小兔子我滴乖', '2021-12-06 15:19:56', '0', '2', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('15', '1234', '吐槽', '楼上是不是有病', '2021-12-05 18:27:15', '1', '0', 'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('29', 'a4384c8b488baf812739acc730a24957', '晒动物', 'aaaa', '2021-12-12 16:19:30', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3140pAzivoyljG4Rf9fff2370cb8331fb8a421db25797449.jpg', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/4629ujifyFNOsuqH493d3e8846c5b4679992431a1129d673.jpg', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/7122Y32oe3ksHSrW117fa39cda526678322b3fe21beb659c.jpg');
INSERT INTO `post` VALUES ('30', 'a4384c8b488baf812739acc730a24957', '求助', '达咩达咩', '2021-12-12 17:00:24', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/4889Bw0IwngSNVUy80c7b053c52f49d542e7e077c57748b1.jpg', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3189', null);
INSERT INTO `post` VALUES ('35', 'a4384c8b488baf812739acc730a24957', '晒动物', '手机发帖啦', '2021-12-12 22:11:43', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '1', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/4842', null, null);
INSERT INTO `post` VALUES ('36', 'f2bae74fa683a29be85666adc5948f36', '晒动物', '冲啊', '2021-12-12 22:21:56', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/qDUFalC1mXgNBRBibEdQHl63uHQESfK52M8ic3KJe4ib5c789Z6QGw4uHqXyChMpBUcF0wkot2sicM1m8nibiby6KZkg/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3257', null, null);
INSERT INTO `post` VALUES ('38', '9311f91797025294c7c1b62142c7b37b', '吐槽', '到点了，开冲！', '2021-12-12 22:30:57', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '1', '1', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8812', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/1789', null);
INSERT INTO `post` VALUES ('50', 'a4384c8b488baf812739acc730a24957', '晒动物', '纯文字测试1', '2021-12-12 23:26:59', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('51', '8a26366bcff08fd1b49bd13580409f73', null, '嘿嘿 啊哈哈哈哈哈哈', '2021-12-12 23:42:24', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqY6C2mlyxhK57R33BHsSiaUC0sPlY10YWRA1zkzpiagKKDuIKpUcGpvKQuxtObUDLfsFODKYVzK8Ag/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8371null', null, null);
INSERT INTO `post` VALUES ('52', 'a4384c8b488baf812739acc730a24957', '求助', '二次元能不能爬', '2021-12-12 23:47:50', '2', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/7547null', null, null);
INSERT INTO `post` VALUES ('53', '8a26366bcff08fd1b49bd13580409f73', null, '和我网恋我超甜 吃口鼻屎觉得咸', '2021-12-12 23:49:02', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqY6C2mlyxhK57R33BHsSiaUC0sPlY10YWRA1zkzpiagKKDuIKpUcGpvKQuxtObUDLfsFODKYVzK8Ag/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/6null', null, null);
INSERT INTO `post` VALUES ('54', '6df23cd52da307fd586eb0599e415ab9', '吐槽', '呜呜呜我好喜欢阿屎啊，为了你，我要电nua子', '2021-12-12 23:52:11', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/hVy5mysSB7BSX8iarNyS2wicEFuoiaiaEkQfONDiaMI1xRXqRe1rHs4dMVEbaWzZTN4vvTLSCKpw2RHsYSa6WTkxSZg/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/6837null', null, null);
INSERT INTO `post` VALUES ('55', '9311f91797025294c7c1b62142c7b37b', '闲聊', '总为浮云遮望眼，色图不见使人愁', '2021-12-12 23:52:34', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8429null', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/224null', null);
INSERT INTO `post` VALUES ('56', 'f448ef60af274e5680aba90473fc0726', null, 'gzw帅比\ndelete * from user；\n＜text＞帅比＜/text＞', '2021-12-13 00:22:56', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/N7HSI7ucicWAOkfv5xlV29aGxlCDhmYL6Q8wx5ibSViaEic4ESbyTUsFZpZjz39q6NiaHd2X0RY80B6vMw4S0fycEew/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('61', 'a4384c8b488baf812739acc730a24957', '晒动物', '听说电脑上发图不行？', '2021-12-13 14:07:49', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '1', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3330KKXPnfhT6r1J752ec26b85b0fa99d13c46c84d9ce7ab.jpg', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/7734null', null);
INSERT INTO `post` VALUES ('62', '82274051d1ec5b478d6250e488751cfd', '吐槽', '喵嗷呜～', '2021-12-13 16:17:04', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoIch696t6D7QTa5Gs7Su9x2TElzXWlpfK0Fu181icn998Vh79cOZxic54ibbZWXhN86oTV2w0CzvbNg/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/4715null', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/1661null', null);
INSERT INTO `post` VALUES ('63', '9311f91797025294c7c1b62142c7b37b', '求助', 'no activity', '2021-12-13 17:50:51', '0', '0', null, '0', '0', '0', null, null, null, null);
INSERT INTO `post` VALUES ('64', 'f71301fccc7a4281c176342524e2c6c3', '晒动物', 'teee', '2021-12-13 18:30:26', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epdCtmKulyPGx60K2JfNGMJNa9ziakjw18puwz0fQ6ibsCl7RcmDxbpPcdq1oE99hJzAVKs7jwkLpVQ/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('65', 'a4384c8b488baf812739acc730a24957', '晒动物', '等价划分测试', '2021-12-13 18:48:39', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/6555null', null, null);
INSERT INTO `post` VALUES ('66', 'a4384c8b488baf812739acc730a24957', '晒动物', '等价划分测试2', '2021-12-13 18:59:13', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('71', 'a4384c8b488baf812739acc730a24957', null, '不加主题测试', '2021-12-13 19:20:01', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '0', '0', '0', '0', null, null, null);
INSERT INTO `post` VALUES ('72', '9311f91797025294c7c1b62142c7b37b', '吐槽', '233', '2021-12-13 19:31:48', '0', '0', null, '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/4345', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8504', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/9911');
INSERT INTO `post` VALUES ('73', '9311f91797025294c7c1b62142c7b37b', '求助', '233', '2021-12-13 19:38:13', '0', '0', null, '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/7075iSLNrHJwSca087ebb67b1522ec7db4432ec77442360.jpg', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/772', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/5382');
INSERT INTO `post` VALUES ('74', '9311f91797025294c7c1b62142c7b37b', '求助', '233', '2021-12-13 19:39:45', '0', '0', null, '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/5200fJkHGnYieHjx7cfc9f3d886624cdc37c37d81922cf66.png', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3671', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/7470');
INSERT INTO `post` VALUES ('75', '9311f91797025294c7c1b62142c7b37b', '吐槽', '123', '2021-12-13 19:41:26', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8367null', null, null);
INSERT INTO `post` VALUES ('76', '9311f91797025294c7c1b62142c7b37b', '吐槽', 'i need', '2021-12-13 19:49:40', '1', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3718qrFj9SI0P7qxec97c97f70f7033bad04641c8b6665ce.png', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/3936BKhOjVzLaXgl7cfc9f3d886624cdc37c37d81922cf66.png', null);
INSERT INTO `post` VALUES ('77', '9311f91797025294c7c1b62142c7b37b', '吐槽', 'kono 1057 da', '2021-12-13 19:51:47', '2', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8849Yd9pClk3XFTSbc28f90944a34387d4f8c8746f2d0405.jpg', null, null);
INSERT INTO `post` VALUES ('78', '9311f91797025294c7c1b62142c7b37b', '吐槽', '我能发帖吗', '2021-12-13 23:10:02', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/5695RG0MThHKwkDS254b8fbc986f41c3312766df6cffe716.jpg', null, null);
INSERT INTO `post` VALUES ('79', '9311f91797025294c7c1b62142c7b37b', '吐槽', '衰仔发帖', '2021-12-13 23:31:18', '0', '0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '0', '0', '0', '0', 'https://zucc-1308480699.cos.ap-nanjing.myqcloud.com/postImages/8405VuyyR4cq8Qnyab420b745b5a41366e62db29e08d129e.jpg', null, null);

-- ----------------------------
-- Table structure for reportrecord
-- ----------------------------
DROP TABLE IF EXISTS `reportrecord`;
CREATE TABLE `reportrecord` (
  `user_ID` varchar(250) NOT NULL,
  `post_ID` bigint(20) NOT NULL,
  `Rep_Con` varchar(500) DEFAULT NULL,
  `Rep_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_ID`,`post_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reportrecord
-- ----------------------------

-- ----------------------------
-- Table structure for rpRecord
-- ----------------------------
DROP TABLE IF EXISTS `rpRecord`;
CREATE TABLE `rpRecord` (
  `RPrecordID` int(11) NOT NULL AUTO_INCREMENT,
  `user_ID` varchar(50) DEFAULT NULL,
  `recUser_ID` varchar(50) DEFAULT NULL,
  `getDate` datetime DEFAULT NULL,
  `getType` varchar(30) DEFAULT NULL,
  `Point` int(11) DEFAULT NULL,
  PRIMARY KEY (`RPrecordID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rpRecord
-- ----------------------------
INSERT INTO `rpRecord` VALUES ('15', 'f71301fccc7a4281c176342524e2c6c3', '9311f91797025294c7c1b62142c7b37b', '2021-12-13 19:57:30', '1', '10');
INSERT INTO `rpRecord` VALUES ('16', '9311f91797025294c7c1b62142c7b37b', '9311f91797025294c7c1b62142c7b37b', '2021-12-13 21:13:29', '1', '10');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_ID` varchar(250) NOT NULL,
  `user_Name` varchar(20) DEFAULT NULL,
  `user_AvatarUrl` varchar(250) DEFAULT NULL,
  `user_rpPoint` int(11) DEFAULT NULL,
  `user_PushPostPoint` int(11) DEFAULT NULL,
  `user_CommentPoint` int(11) DEFAULT NULL,
  `user_RewardPoint` int(11) DEFAULT NULL,
  `user_isAdmin` tinyint(1) DEFAULT NULL,
  `user_Password` varchar(20) DEFAULT NULL,
  `user_Phone` varchar(20) DEFAULT NULL,
  `user_Gender` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1234', null, 'http://photocq.photo.store.qq.com/psc?/V11L9yfC1eZGvm/gWShryPLAbNNMut8n7qrsMO31B4dkTc*OXzwkM*C4XIBe6dkNb6Sr94CO*v.NiDtVKdea1.Waf..TD3gD.eONAnBX3eK*UHkmGNzfbSJzrQ!/b&bo=8ACgAPAAoAAFFzQ!&rf=viewer_4', '50', '1', '1', null, null, null, null, null);
INSERT INTO `user` VALUES ('6df23cd52da307fd586eb0599e415ab9', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoIch696t6D7QTa5Gs7Su9x2TElzXWlpfK0Fu181icn998Vh79cOZxic54ibbZWXhN86oTV2w0CzvbNg/132', '50', '1', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('82274051d1ec5b478d6250e488751cfd', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqY6C2mlyxhK57R33BHsSiaUC0sPlY10YWRA1zkzpiagKKDuIKpUcGpvKQuxtObUDLfsFODKYVzK8Ag/132', '50', '1', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('8a26366bcff08fd1b49bd13580409f73', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '50', '2', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('9311f91797025294c7c1b62142c7b37b', '1057', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0a5ozvUlwJtmUqZYS0g1MgmzjmicaHl0ibVa3B5WRLo3ha1k4PxIjR0YJ55g2BUhHO0RQERdRM6GQ/132', '50', '9', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('a4384c8b488baf812739acc730a24957', '屑黄宝', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobfn8t4BNhx1KDDpoZyQHTxW3II5D5cw6DibYcSVPJsd8xntgXmK6rMqaS60wguK6kX92WcsCKeDA/132', '50', '10', '1', '1', null, null, null, null);
INSERT INTO `user` VALUES ('f2bae74fa683a29be85666adc5948f36', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/N7HSI7ucicWAOkfv5xlV29aGxlCDhmYL6Q8wx5ibSViaEic4ESbyTUsFZpZjz39q6NiaHd2X0RY80B6vMw4S0fycEew/132', '50', '1', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('f448ef60af274e5680aba90473fc0726', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/hVy5mysSB7BSX8iarNyS2wicEFuoiaiaEkQfONDiaMI1xRXqRe1rHs4dMVEbaWzZTN4vvTLSCKpw2RHsYSa6WTkxSZg/132', '50', '1', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('f71301fccc7a4281c176342524e2c6c3', 'DONG', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epdCtmKulyPGx60K2JfNGMJNa9ziakjw18puwz0fQ6ibsCl7RcmDxbpPcdq1oE99hJzAVKs7jwkLpVQ/132', '50', '1', null, '1', null, null, null, null);
