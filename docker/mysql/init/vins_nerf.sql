/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : vins_nerf

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 11/03/2024 09:42:22
*/
CREATE database if NOT EXISTS `vins_nerf` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `vins_nerf`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dy_oss_bucket
-- ----------------------------
DROP TABLE IF EXISTS `dy_oss_bucket`;
CREATE TABLE `dy_oss_bucket` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'Bucket的ID',
  `NAME` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `AUTH` tinyint unsigned NOT NULL COMMENT '权限:\nPRIVATE(0),//私有\nPUBLIC_READ_ONLY(1),//公共读\nPUBLIC_READ_WRITE(2);//公共读写\n',
  `ENDPOINT` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目',
  `VERSION` int NOT NULL COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME_INDEX` (`NAME`,`ENDPOINT`) USING BTREE COMMENT '项目名称唯一键'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of dy_oss_bucket
-- ----------------------------
BEGIN;
INSERT INTO `dy_oss_bucket` (`ID`, `NAME`, `AUTH`, `ENDPOINT`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (1, 'lilylab-aimed-private', 0, 'cn-beijing', 0, '2022-10-28 20:13:49', '2022-10-28 20:13:49');
INSERT INTO `dy_oss_bucket` (`ID`, `NAME`, `AUTH`, `ENDPOINT`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (3, 'lilylab-aimed-public-read', 1, 'cn-beijing', 0, '2022-10-28 20:15:04', '2022-10-28 20:15:04');
INSERT INTO `dy_oss_bucket` (`ID`, `NAME`, `AUTH`, `ENDPOINT`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (4, 'lilylab-aimed-public', 2, 'cn-hangzhou', 0, '2022-11-10 16:32:00', '2022-11-10 16:32:02');
COMMIT;

-- ----------------------------
-- Table structure for image_imu_info
-- ----------------------------
DROP TABLE IF EXISTS `image_imu_info`;
CREATE TABLE `image_imu_info` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  `BUCKET_ID` bigint unsigned NOT NULL COMMENT 'dy_oss_bucket的编号',
  `NAME` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '名称',
  `LOG_PATH` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'log路径',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of image_imu_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `ACCESSKEY` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '系统颁布的AK',
  `PASSWORD` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `EMAIL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `PHONE` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机',
  `PROJECT` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '项目',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `ACCESSKEY_INDEX` (`ACCESSKEY`) USING BTREE COMMENT 'AK索引',
  UNIQUE KEY `PHONE_INDEX` (`PHONE`,`PROJECT`) USING BTREE COMMENT '手机索引',
  UNIQUE KEY `EMAIL_INDEX` (`EMAIL`,`PROJECT`) USING BTREE COMMENT '邮箱索引'
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`ID`, `ACCESSKEY`, `PASSWORD`, `EMAIL`, `PHONE`, `PROJECT`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (11, 'WiB3meejSyOAmm1eBYjxLhv3siP47x5gXW2tn6NyFrLWPifT6BYsycfOrxeKXamu', '66989eead021afc6b0f4032fa0278bba', 'jgdd@dd.gnf', '18058253188', 'ai-design', 7, '2022-10-07 13:33:35', '2023-10-19 15:41:15');
INSERT INTO `sys_user` (`ID`, `ACCESSKEY`, `PASSWORD`, `EMAIL`, `PHONE`, `PROJECT`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (12, 'aVFTDmaPLf5Xk9GdCaVdl51y6rBYtV2yT3NWjiH67eNFUHNdYr4BzEXOQRtkwywZ', '/2e83968178a084401c9d112650528a2d', NULL, '18058253188', 'myjianyi', 1, '2023-10-19 15:57:06', '2023-11-11 19:51:38');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `USER_ID` bigint unsigned NOT NULL COMMENT 'SysUser的ID',
  `NICKNAME` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `HEAD_URL` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `COUNTRY` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家',
  `PROVINCE` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份',
  `CITY` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `ID_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `GENDER` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '性别',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ID_INDEX` (`USER_ID`) USING BTREE COMMENT '用户编号索引'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (1, 6, 'aimed_e5dHhb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2022-10-04 01:00:59', '2022-10-04 01:00:59');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (2, 7, 'aimed_Wp9umU', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2022-10-04 13:07:53', '2022-10-04 13:07:53');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (3, 8, 'aimed_9JQgwS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2022-10-07 13:21:49', '2022-10-07 13:21:49');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (4, 9, 'aimed_cdLZxC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2022-10-07 13:23:29', '2022-10-07 13:23:29');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (5, 10, 'aimed_cHJ6O8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2022-10-07 13:28:38', '2022-10-07 13:28:38');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (6, 11, 'aimed_8hbsgY', 'https://lilylab-aimed-public-read.oss-cn-beijing.aliyuncs.com/WiB3meejSyOAmm1eBYjxLhv3siP47x5gXW2tn6NyFrLWPifT6BYsycfOrxeKXamu/portrait/aimed-ios_53829a484dcb68ca323abf56f446ae8d_exif.jpg', NULL, NULL, NULL, NULL, NULL, '1993-01-01', 0, 42, '2022-10-07 13:33:35', '2022-11-20 01:06:27');
INSERT INTO `sys_user_info` (`ID`, `USER_ID`, `NICKNAME`, `HEAD_URL`, `COUNTRY`, `PROVINCE`, `CITY`, `ADDRESS`, `ID_NO`, `BIRTHDAY`, `GENDER`, `VERSION`, `CREATE_TIME`, `UPDATE_TIME`) VALUES (7, 12, 'myjianyi_3U65Pp', 'https://lilylab-aimed-public-read.oss-cn-beijing.aliyuncs.com/aVFTDmaPLf5Xk9GdCaVdl51y6rBYtV2yT3NWjiH67eNFUHNdYr4BzEXOQRtkwywZ/portrait/myjianyi-ios_55d0036e975bf68bf37a223567ed404c_exif.jpg', NULL, NULL, NULL, NULL, NULL, NULL, 1, 5, '2023-10-19 15:57:06', '2023-11-01 15:00:59');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_reference
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_reference`;
CREATE TABLE `sys_user_reference` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `USER_ID` bigint unsigned NOT NULL COMMENT 'SysUser的编号',
  `TYPE` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '第三方平台（0:Unkown；1:微信）',
  `UNION_ID` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '平台给的ID',
  `NICKNAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '平台昵称',
  `HEAD_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像URL',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `USER_ID_INDEX` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_user_reference
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_camera
-- ----------------------------
DROP TABLE IF EXISTS `vins_camera`;
CREATE TABLE `vins_camera` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `PHONE_ID` bigint unsigned NOT NULL COMMENT '手机编号（与表vins_phone对应）',
  `CAMERA_TYPE_ID` bigint DEFAULT NULL COMMENT '相机类型ID（与表vins_camera_type对应）',
  `VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '相机的值',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `CAMERA_TYPE_INDEX` (`CAMERA_TYPE_ID`) COMMENT '相机类型索引',
  KEY `PHONE_ID_INDEX` (`PHONE_ID`) USING BTREE COMMENT '手机编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_camera
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_camera_type
-- ----------------------------
DROP TABLE IF EXISTS `vins_camera_type`;
CREATE TABLE `vins_camera_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `PHONE_TYPE_ID` bigint NOT NULL COMMENT '手机类型编号（与表vins_phone_type对应）',
  `POSITION` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '位置(0:NULL；1:前端；2:后端；3:外置)',
  `SELECTION` tinyint unsigned NOT NULL COMMENT '在手机上的选择值',
  `VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '相机的值',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PHONE_TYPE_SELECTION_INDEX` (`PHONE_TYPE_ID`,`SELECTION`) USING BTREE COMMENT '手机类型选择索引',
  KEY `PHONE_TYPE_POSITION_INDEX` (`PHONE_TYPE_ID`,`POSITION`) USING BTREE COMMENT '手机类型位置索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_camera_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_filter
-- ----------------------------
DROP TABLE IF EXISTS `vins_filter`;
CREATE TABLE `vins_filter` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `TYPE` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '过滤类型',
  `FEATURE1` int unsigned NOT NULL COMMENT '特征值1',
  `FEATURE2` int unsigned DEFAULT NULL COMMENT '特征值2',
  `FEATURE3` int unsigned DEFAULT NULL COMMENT '特征值3',
  `FEATURE4` int unsigned DEFAULT NULL COMMENT '特征值4',
  `FEATURE5` int unsigned DEFAULT NULL COMMENT '特征值5',
  `FEATURE6` int unsigned DEFAULT NULL COMMENT '特征值6',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `TYPE_FEATURE_UNINDEX` (`TYPE`,`FEATURE1`,`FEATURE2`,`FEATURE3`,`FEATURE4`,`FEATURE5`,`FEATURE6`) COMMENT '类型与特征值（唯一索引）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='用于表示过滤的表';

-- ----------------------------
-- Records of vins_filter
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_image
-- ----------------------------
DROP TABLE IF EXISTS `vins_image`;
CREATE TABLE `vins_image` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lINE_ID` bigint unsigned NOT NULL COMMENT '线路编号',
  `OSS_PATH` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '在OSS中的路径(图片文件地址)',
  `ORB_DESCRIPTION` blob NOT NULL COMMENT 'ORB描述子',
  `VERSION` int NOT NULL COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `VIEW_ID_INDEX` (`lINE_ID`) COMMENT '场景编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_image
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_line
-- ----------------------------
DROP TABLE IF EXISTS `vins_line`;
CREATE TABLE `vins_line` (
  `ID` bigint NOT NULL COMMENT '线路编号',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '线路名称',
  `VIEW_ID` bigint unsigned NOT NULL COMMENT '场景编号',
  `CAMERA_ID` bigint unsigned NOT NULL COMMENT '照相机编号',
  `OSS_PATH` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '在OSS中的路径(IMU文件地址)',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `VIEW_CAMERA_INDEX` (`VIEW_ID`,`CAMERA_ID`) USING BTREE COMMENT '场景相机编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_line
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_phone
-- ----------------------------
DROP TABLE IF EXISTS `vins_phone`;
CREATE TABLE `vins_phone` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `USER_ID` bigint unsigned NOT NULL COMMENT '用户ID',
  `PHONE_TYPE_ID` bigint NOT NULL COMMENT '手机型号ID（vins_phone_type）',
  `PHONE_NO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机的身份编号',
  `AX_BIAS` double DEFAULT NULL COMMENT '加速度计X轴零偏（NULL表示未赋值）',
  `AY_BIAS` double DEFAULT NULL COMMENT '加速度计Y轴零偏（NULL表示未赋值）',
  `AZ_BIAS` double DEFAULT NULL COMMENT '加速度计Z轴零偏（NULL表示未赋值）',
  `GX_BIAS` double DEFAULT NULL COMMENT '陀螺仪X轴零偏（NULL为未赋值）',
  `GY_BIAS` double DEFAULT NULL COMMENT '陀螺仪Y轴零偏（NULL为未赋值）',
  `GZ_BIAS` double DEFAULT NULL COMMENT '陀螺仪Z轴零偏（NULL为未赋值）',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ID_PHONE_NO_UNINDEX` (`USER_ID`,`PHONE_NO`) USING BTREE COMMENT '用户手机唯一性索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_phone
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_phone_type
-- ----------------------------
DROP TABLE IF EXISTS `vins_phone_type`;
CREATE TABLE `vins_phone_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `NAME` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机名称（唯一性索引）',
  `AX_BIAS` double DEFAULT NULL COMMENT '加速度计X轴零偏（NULL表示未赋值）',
  `AY_BIAS` double DEFAULT NULL COMMENT '加速度计Y轴零偏（NULL表示未赋值）',
  `AZ_BIAS` double DEFAULT NULL COMMENT '加速度计Z轴零偏（NULL表示未赋值）',
  `GX_BIAS` double DEFAULT NULL COMMENT '陀螺仪X轴零偏（NULL为未赋值）',
  `GY_BIAS` double DEFAULT NULL COMMENT '陀螺仪Y轴零偏（NULL为未赋值）',
  `GZ_BIAS` double DEFAULT NULL COMMENT '陀螺仪Z轴零偏（NULL为未赋值）',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_phone_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_point
-- ----------------------------
DROP TABLE IF EXISTS `vins_point`;
CREATE TABLE `vins_point` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `IMAGE_ID` bigint unsigned NOT NULL COMMENT '对应的图片位置',
  `X` double NOT NULL COMMENT '图片在X轴上的坐标',
  `Y` double NOT NULL COMMENT '图片在Y轴上的坐标',
  `ORB_DESCRIPTION` blob NOT NULL COMMENT 'BRIEF特征描述（2^256）',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IMAGE_X_Y_UNINDEX` (`IMAGE_ID`,`X`,`Y`) USING BTREE COMMENT '图像唯一性索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='关键点相关的信息。由于关键点的特征描述为256位（共有2^256种变化），其值是可以共用的，所以在本表只记录出现过的关键点特征描述。点与图片的对应关系(N对N)在”表dbow_image_point”中表示。';

-- ----------------------------
-- Records of vins_point
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vins_view
-- ----------------------------
DROP TABLE IF EXISTS `vins_view`;
CREATE TABLE `vins_view` (
  `ID` bigint NOT NULL COMMENT '自增ID',
  `USER_ID` bigint unsigned NOT NULL COMMENT '所属用户ID',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '场景名称',
  `VERSION` int unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `USER_ID_INDEX` (`USER_ID`) COMMENT '用户ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of vins_view
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
