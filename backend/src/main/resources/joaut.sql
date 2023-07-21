/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80031
Source Host           : localhost:3306
Source Database       : joaut

Target Server Type    : MYSQL
Target Server Version : 80031
File Encoding         : 65001

Date: 2023-07-21 13:11:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for j_files
-- ----------------------------
DROP TABLE IF EXISTS `j_files`;
CREATE TABLE `j_files` (
  `id` varchar(255) NOT NULL COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `md5` varchar(255) DEFAULT NULL COMMENT 'MD5值',
  `url` varchar(255) NOT NULL COMMENT '下载链接',
  `upload_time` varchar(255) NOT NULL COMMENT '上传时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否禁用链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of j_files
-- ----------------------------
INSERT INTO `j_files` VALUES ('02b1a756-7e18-41dd-b649-25d812a8c8bb', 'WhileIfBreakContinue.java', 'java', '50547ca15cbd50bea0e818bc35f24a8e', 'http://localhost:8888/file?id=02b1a756-7e18-41dd-b649-25d812a8c8bb.java', '2023-07-17 12:01:07', '0', '1');
INSERT INTO `j_files` VALUES ('07c4a5d9-7395-46be-a2a2-d1679cf06f7d', 'switchWithBreak.java', 'java', '025e36c65f8d541cb4606b406aa8b87b', 'http://localhost:8888/file?id=07c4a5d9-7395-46be-a2a2-d1679cf06f7d.java', '2023-07-17 14:23:19', '0', '1');
INSERT INTO `j_files` VALUES ('0abd0851-e75b-41d1-9b37-a18cc6c6a62a', 'DynamicLabelAndBreakTest.java', 'java', '7ca5dd3ba8994afa684ad28fe59c3e0c', 'http://localhost:8888/file?id=0abd0851-e75b-41d1-9b37-a18cc6c6a62a.java', '2023-07-17 13:23:29', '0', '1');
INSERT INTO `j_files` VALUES ('0e7b2d7a-fa81-4b10-b53e-dc63d9eac766', 'graph.java', 'java', '26b0c8a68c282d4ad9ddbe0a46b83b76', 'http://localhost:8888/file?id=0e7b2d7a-fa81-4b10-b53e-dc63d9eac766.java', '2023-07-17 10:57:01', '0', '1');
INSERT INTO `j_files` VALUES ('122b118e-092b-40de-b145-72ede9984ab6', 'test1.java', 'java', '1b87765f3ca65db7b4372eae5fc99bb5', 'http://localhost:8888/file?id=122b118e-092b-40de-b145-72ede9984ab6.java', '2023-07-17 13:01:01', '0', '1');
INSERT INTO `j_files` VALUES ('1c35bf07-defd-42e4-bbc5-75b8c824beea', 'report.java', 'java', 'eed47984c45832f223b8b45dd8fa1d8b', 'http://localhost:8888/file?id=1c35bf07-defd-42e4-bbc5-75b8c824beea.java', '2023-07-17 11:01:57', '0', '1');
INSERT INTO `j_files` VALUES ('223fd923-e56d-441d-b053-3c734b6e2b2f', 'If1.java', 'java', 'd54c4addb74b11eeb8dd826df6c42fca', 'http://172.27.158.191:8888/file?id=223fd923-e56d-441d-b053-3c734b6e2b2f.java', '2023-07-16 19:27:53', '0', '1');
INSERT INTO `j_files` VALUES ('328044d9-7683-4afa-966d-706811ef41f5', 'WhileContinue.java', 'java', 'e7b2cde11ff43c7eb4a64b9559acb324', 'http://172.27.158.191:8888/file?id=328044d9-7683-4afa-966d-706811ef41f5.java', '2023-07-16 18:03:46', '0', '1');
INSERT INTO `j_files` VALUES ('3bba132e-9ff4-41b9-b27f-82bb504593d3', 'If.java', 'java', '28c757bdee6919caae35de201f57353b', 'http://172.27.158.191:8888/file?id=3bba132e-9ff4-41b9-b27f-82bb504593d3.java', '2023-07-16 17:56:46', '0', '1');
INSERT INTO `j_files` VALUES ('5d5ab45c-59a3-4eaa-87aa-5fda5c737371', 'DynamicLabelAndBreakTest.java', 'java', 'ed963b41d26e133432ff6c84cff016e9', 'http://localhost:8888/file?id=5d5ab45c-59a3-4eaa-87aa-5fda5c737371.java', '2023-07-17 11:56:26', '0', '1');
INSERT INTO `j_files` VALUES ('618a7610-ae7b-485b-91a1-dbdb2e6876e6', 'test1.java', 'java', 'ce5c7a2ae1bdac352f23d9f66bdb96fe', 'http://localhost:8888/file?id=618a7610-ae7b-485b-91a1-dbdb2e6876e6.java', '2023-07-17 13:02:25', '0', '1');
INSERT INTO `j_files` VALUES ('65a30e67-d0f0-4205-af00-7515b1be1e7d', 'dynamicSimple.java', 'java', '3d916ee09ebf0356a0fd4b3f31d6f9b0', 'http://localhost:8888/file?id=65a30e67-d0f0-4205-af00-7515b1be1e7d.java', '2023-07-17 12:53:03', '0', '1');
INSERT INTO `j_files` VALUES ('6fbd4605-d2e6-4361-8052-81cc3173bc77', 'report.java', 'java', 'aa68c8f76c37543a68097ebd776652d0', 'http://localhost:8888/file?id=6fbd4605-d2e6-4361-8052-81cc3173bc77.java', '2023-07-17 11:05:04', '0', '1');
INSERT INTO `j_files` VALUES ('751edf0b-2ddd-4a69-a927-5637bba164a5', 'IfSeq.java', 'java', 'b8e840ee91eed4827dd74fa08644d183', 'http://172.27.158.191:8888/file?id=751edf0b-2ddd-4a69-a927-5637bba164a5.java', '2023-07-16 17:56:54', '0', '1');
INSERT INTO `j_files` VALUES ('77c94a9a-d871-4c80-9c55-d992872f7215', 'WhileBreak.java', 'java', '933522ad17e715e3253eef644111cc82', 'http://172.27.158.191:8888/file?id=77c94a9a-d871-4c80-9c55-d992872f7215.java', '2023-07-16 18:02:44', '0', '1');
INSERT INTO `j_files` VALUES ('7e6b8554-e3a1-4797-9542-127143737ed5', 'Comment.java', 'java', 'b1ddc5a973069d20c0e7043ba438e38f', 'http://172.27.158.191:8888/file?id=7e6b8554-e3a1-4797-9542-127143737ed5.java', '2023-07-16 19:23:01', '0', '1');
INSERT INTO `j_files` VALUES ('7fccec96-7a0f-4f14-991f-ee0958461c2d', 'WhileIfBreakContinue.java', 'java', '0960d52a7a9486e28bcf43a32a5022dd', 'http://localhost:8888/file?id=7fccec96-7a0f-4f14-991f-ee0958461c2d.java', '2023-07-17 11:56:35', '0', '1');
INSERT INTO `j_files` VALUES ('86d26c15-3444-4af1-9cf3-cb2d50801d06', 'WhileIfBreakContinue.java', 'java', 'fd6a2db374a06b1508866dc6a41044cf', 'http://localhost:8888/file?id=86d26c15-3444-4af1-9cf3-cb2d50801d06.java', '2023-07-17 11:59:30', '0', '1');
INSERT INTO `j_files` VALUES ('8fc94ed4-ee15-4e06-a856-3836d0dd8480', 'forTest.java', 'java', '8c733b16a6959e52f2bb01307b683c02', 'http://localhost:8888/file?id=8fc94ed4-ee15-4e06-a856-3836d0dd8480.java', '2023-07-17 12:37:42', '0', '1');
INSERT INTO `j_files` VALUES ('90642e9a-acf3-451f-902a-256b4b81643a', 'Test2.java', 'java', '1c70d915fe2be62fe9b6840e12dedd5e', 'http://172.27.158.191:8888/file?id=90642e9a-acf3-451f-902a-256b4b81643a.java', '2023-07-16 18:07:58', '0', '1');
INSERT INTO `j_files` VALUES ('961c8128-3920-49ae-b8e7-4df7dc22e848', 'WhileIfBreakContinue.java', 'java', 'a8adcaea36a3e337ae73d3d93d8dc3e6', 'http://localhost:8888/file?id=961c8128-3920-49ae-b8e7-4df7dc22e848.java', '2023-07-17 12:03:05', '0', '1');
INSERT INTO `j_files` VALUES ('98a6b73a-21b4-4ede-a111-e13759af0a52', 'MiniFloat.java', 'java', 'c4d31a8d8565892d9464d1355e61c9ce', 'http://172.27.158.191:8888/file?id=98a6b73a-21b4-4ede-a111-e13759af0a52.java', '2023-07-17 14:27:01', '0', '1');
INSERT INTO `j_files` VALUES ('991ad35a-50e3-44ac-aef1-0cb42c02954f', 'Sequence.java', 'java', '83cf681fee0467426095383d66b258b1', 'http://localhost:8888/file?id=991ad35a-50e3-44ac-aef1-0cb42c02954f.java', '2023-07-17 11:09:43', '0', '1');
INSERT INTO `j_files` VALUES ('a4e31d4b-e7b9-4401-a0f7-bff2f6fd9cac', 'slicer.java', 'java', 'e7448c6b50dde18685f4319dba67ca82', 'http://localhost:8888/file?id=a4e31d4b-e7b9-4401-a0f7-bff2f6fd9cac.java', '2023-07-17 12:12:24', '0', '1');
INSERT INTO `j_files` VALUES ('a790b528-ab95-4369-9117-a04207ce1a0c', 'WhileIf.java', 'java', '01ebc67f2a3c1e8af02b02929e5f4f39', 'http://localhost:8888/file?id=a790b528-ab95-4369-9117-a04207ce1a0c.java', '2023-07-17 14:15:36', '0', '1');
INSERT INTO `j_files` VALUES ('a807fac2-4d96-475e-846f-fc6cfc0ade7c', 'WhileIfBreakContinue.java', 'java', 'e51848a8ad380f34766b5d390d4bac91', 'http://localhost:8888/file?id=a807fac2-4d96-475e-846f-fc6cfc0ade7c.java', '2023-07-17 11:55:19', '0', '1');
INSERT INTO `j_files` VALUES ('aa89b604-9196-4a10-94a3-7eebc6141fd9', 'WhileIfBreakContinue.java', 'java', '3114fa28c00797ebc9efbec9e9499225', 'http://localhost:8888/file?id=aa89b604-9196-4a10-94a3-7eebc6141fd9.java', '2023-07-17 11:57:50', '0', '1');
INSERT INTO `j_files` VALUES ('ad4c4c17-eacb-42c6-8cc1-e08965c8bbec', 'IfWhile.java', 'java', '6c49e40532d1f1c4b88574e15c303784', 'http://172.27.158.191:8888/file?id=ad4c4c17-eacb-42c6-8cc1-e08965c8bbec.java', '2023-07-16 18:06:36', '0', '1');
INSERT INTO `j_files` VALUES ('b1eb6491-db9f-40a8-9e92-1f5a9a0b7c14', 'switchWithoutBreak.java', 'java', '0f7382d0875352b3b1e50d6229132f4a', 'http://localhost:8888/file?id=b1eb6491-db9f-40a8-9e92-1f5a9a0b7c14.java', '2023-07-17 14:41:59', '0', '1');
INSERT INTO `j_files` VALUES ('b97dfc04-171d-4cf3-a042-44f8198969e0', 'IfTest.java', 'java', '36423bea3632bd72e77235437e5865ff', 'http://localhost:8888/file?id=b97dfc04-171d-4cf3-a042-44f8198969e0.java', '2023-07-17 14:06:09', '0', '1');
INSERT INTO `j_files` VALUES ('d2f3e84a-a75c-489a-b5ad-daec3f965e0d', 'WhileBreak.java', 'java', '171bc387b9ceaecdde257f29d278e371', 'http://172.27.158.191:8888/file?id=d2f3e84a-a75c-489a-b5ad-daec3f965e0d.java', '2023-07-16 18:01:51', '0', '1');
INSERT INTO `j_files` VALUES ('d6bb264e-2e89-4fce-9cf0-8194c99383e2', 'WhileTest.java', 'java', 'af6728b8f6cbb5d588132b4032f8964f', 'http://172.27.158.191:8888/file?id=d6bb264e-2e89-4fce-9cf0-8194c99383e2.java', '2023-07-16 18:05:58', '0', '1');
INSERT INTO `j_files` VALUES ('d742d3e7-8588-4553-9420-c637d05c78d7', 'DynamicTest.java', 'java', 'fae051bd5e394806f4a5b9d04b548999', 'http://localhost:8888/file?id=d742d3e7-8588-4553-9420-c637d05c78d7.java', '2023-07-17 14:27:11', '0', '1');
INSERT INTO `j_files` VALUES ('db47d817-c5e4-41c4-9b80-6c68a020ee39', 'Specific.java', 'java', 'f880069b4f456f223d2031c68abc635f', 'http://172.27.158.191:8888/file?id=db47d817-c5e4-41c4-9b80-6c68a020ee39.java', '2023-07-16 17:55:55', '0', '1');
INSERT INTO `j_files` VALUES ('fee3bd26-6f1e-4960-8231-def7cf56a88c', 'Sequence.java', 'java', '52a699fc8b5db394b829bc5ccae28d23', 'http://localhost:8888/file?id=fee3bd26-6f1e-4960-8231-def7cf56a88c.java', '2023-07-17 14:19:35', '0', '1');
INSERT INTO `j_files` VALUES ('ff543261-2743-4dad-ae84-af513e4b3c6d', 'report.java', 'java', '3cd02ff5d0b013e6cba857710bb79ba2', 'http://localhost:8888/file?id=ff543261-2743-4dad-ae84-af513e4b3c6d.java', '2023-07-17 10:59:54', '0', '1');

-- ----------------------------
-- Table structure for j_user
-- ----------------------------
DROP TABLE IF EXISTS `j_user`;
CREATE TABLE `j_user` (
  `id` varchar(255) NOT NULL COMMENT '用户编号',
  `username` varchar(255) NOT NULL COMMENT '用户名称(唯一)',
  `password` varchar(255) NOT NULL COMMENT '用户密码(密文存储)',
  `phone_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
  `update_at` varchar(255) NOT NULL COMMENT '最近登录日期',
  `status` int NOT NULL DEFAULT '0' COMMENT '用户状态，0表示正常，1表示被禁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of j_user
-- ----------------------------
INSERT INTO `j_user` VALUES ('115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'test', '$2a$10$iV6NUVdrPeSgkr9SO8CM5e9V7lspCLy9oRkcdYlzKRUOysXiCIZEe', '', '', '2023-07-21 11:59:11', '0');
INSERT INTO `j_user` VALUES ('64b6a1f1-11c8-44bd-bdbc-d064a1d38167', 'user1', '$2a$10$sojb6ZfR2yEV8EEWhsVjTuObvqCBJ/78UuenTZuOLDo/ffa5ls07K', '', '', '2023-07-17 16:41:12', '0');
INSERT INTO `j_user` VALUES ('bc8932c3-6fe9-4b10-bb1c-5ae648bbafd6', 'user2', '$2a$10$SJGPp4pREjFdyfQc3.DLWOdju1EoE1AReU8ODxHvpNaeuZQMitGIG', '', '', '2023-07-17 16:18:25', '0');
INSERT INTO `j_user` VALUES ('eaacaf35-53f0-446d-92f9-46314bfdb34f', 'user', '$2a$10$82nr/dTo1MkZzqzVDW7RQOYKwoRgWRnse80AnrkitRRLhtaq1WjV2', '', '', '2023-07-18 08:02:53', '0');

-- ----------------------------
-- Table structure for j_userfile
-- ----------------------------
DROP TABLE IF EXISTS `j_userfile`;
CREATE TABLE `j_userfile` (
  `id` varchar(255) NOT NULL COMMENT '编号',
  `uid` varchar(255) NOT NULL COMMENT '用户编号',
  `fid` varchar(255) NOT NULL COMMENT '文件编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of j_userfile
-- ----------------------------
INSERT INTO `j_userfile` VALUES ('02ec73fb-48a1-4a10-b973-f2f3b6322bd1', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', 'd6bb264e-2e89-4fce-9cf0-8194c99383e2');
INSERT INTO `j_userfile` VALUES ('1480c964-564f-4848-a263-2f694553b33d', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '3bba132e-9ff4-41b9-b27f-82bb504593d3');
INSERT INTO `j_userfile` VALUES ('3c935699-2b07-49e4-92c6-d4f3ef0c3ec4', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '98a6b73a-21b4-4ede-a111-e13759af0a52');
INSERT INTO `j_userfile` VALUES ('49f8dc72-a569-49a3-b23c-7b3d95b858a4', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '90642e9a-acf3-451f-902a-256b4b81643a');
INSERT INTO `j_userfile` VALUES ('55d7978d-fbc0-4119-b5cb-48a21272c7de', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '1c35bf07-defd-42e4-bbc5-75b8c824beea');
INSERT INTO `j_userfile` VALUES ('56d97104-5ecc-4b9a-ada2-7496282583ca', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'a790b528-ab95-4369-9117-a04207ce1a0c');
INSERT INTO `j_userfile` VALUES ('6703ca3d-714d-40a2-b03a-906beea8d423', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'fee3bd26-6f1e-4960-8231-def7cf56a88c');
INSERT INTO `j_userfile` VALUES ('672115aa-722f-4ade-b008-439e5f346514', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '98a6b73a-21b4-4ede-a111-e13759af0a52');
INSERT INTO `j_userfile` VALUES ('6d61c41b-6ab9-4211-941c-73f0f611c74c', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'a4e31d4b-e7b9-4401-a0f7-bff2f6fd9cac');
INSERT INTO `j_userfile` VALUES ('7206d6d2-adb0-454e-b053-e93b0e68cd21', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '7e6b8554-e3a1-4797-9542-127143737ed5');
INSERT INTO `j_userfile` VALUES ('73fd81a9-67c3-4120-80c5-2650c5e299a4', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'd742d3e7-8588-4553-9420-c637d05c78d7');
INSERT INTO `j_userfile` VALUES ('76a03b4b-bcf5-4f5a-b5be-a212245c49c9', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '0abd0851-e75b-41d1-9b37-a18cc6c6a62a');
INSERT INTO `j_userfile` VALUES ('a41f3995-a59a-42ec-9cfb-2f24040a610f', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '223fd923-e56d-441d-b053-3c734b6e2b2f');
INSERT INTO `j_userfile` VALUES ('a78c9ff7-556e-44d5-b91f-a16061a258bc', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '618a7610-ae7b-485b-91a1-dbdb2e6876e6');
INSERT INTO `j_userfile` VALUES ('b8e6696d-b078-4a12-8f12-bc563c1dd1ef', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '0e7b2d7a-fa81-4b10-b53e-dc63d9eac766');
INSERT INTO `j_userfile` VALUES ('b99e23c5-9e33-4bf6-8761-301c1f6b1dd2', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', '751edf0b-2ddd-4a69-a927-5637bba164a5');
INSERT INTO `j_userfile` VALUES ('bcf361d0-65e5-4050-a226-6fae55145f07', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', 'db47d817-c5e4-41c4-9b80-6c68a020ee39');
INSERT INTO `j_userfile` VALUES ('bef9dff0-19ea-4778-801b-010deab1f80d', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '8fc94ed4-ee15-4e06-a856-3836d0dd8480');
INSERT INTO `j_userfile` VALUES ('c179980d-389c-4073-a05c-047c556b4d72', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '65a30e67-d0f0-4205-af00-7515b1be1e7d');
INSERT INTO `j_userfile` VALUES ('c5b39170-4db7-4db8-964f-abedb7c1f5e6', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '6fbd4605-d2e6-4361-8052-81cc3173bc77');
INSERT INTO `j_userfile` VALUES ('c891fef5-7d1d-4d06-8aca-82e830a08c50', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', '07c4a5d9-7395-46be-a2a2-d1679cf06f7d');
INSERT INTO `j_userfile` VALUES ('dcba42fb-c3e3-4118-915d-d94f8450edfe', '64b6a1f1-11c8-44bd-bdbc-d064a1d38167', 'ad4c4c17-eacb-42c6-8cc1-e08965c8bbec');
INSERT INTO `j_userfile` VALUES ('f3265ded-5fec-46ab-8933-93282a851d84', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'b1eb6491-db9f-40a8-9e92-1f5a9a0b7c14');
INSERT INTO `j_userfile` VALUES ('f4f586b4-d957-4892-a678-215252ab2a5c', '115c5cb2-cd97-4bce-bdd9-a22aae1647ec', 'b97dfc04-171d-4cf3-a042-44f8198969e0');
