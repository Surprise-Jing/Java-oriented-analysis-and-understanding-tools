/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80031
Source Host           : localhost:3306
Source Database       : joaut

Target Server Type    : MYSQL
Target Server Version : 80031
File Encoding         : 65001

Date: 2023-06-08 23:30:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS `j_user`;
CREATE TABLE `j_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) NOT NULL COMMENT '用户名称(唯一)',
  `password` varchar(50) NOT NULL COMMENT '用户密码(密文存储)',
  `phone_number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户电话号码',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `update_at` varchar(50) NOT NULL COMMENT '最近登录日期',
  `status` int NOT NULL DEFAULT '0' COMMENT '用户状态，0表示正常，1表示被禁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

INSERT INTO `j_user` VALUES (1, 'admin', '123456', '123456778', '123@nju.edu.cn', '2023-07-07','0');

DROP TABLE IF EXISTS `j_file`;
DROP TABLE IF EXISTS `j_files`;
CREATE TABLE `j_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `md5` varchar(255) DEFAULT NULL COMMENT 'MD5值',
  `url` varchar(255) NOT NULL COMMENT '下载链接',
  `upload_time` datetime(3) NOT NULL COMMENT '上传时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否禁用链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
