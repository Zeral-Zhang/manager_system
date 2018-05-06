/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : manager

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-05-06 23:10:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `has_son` bit(1) DEFAULT NULL,
  `parent_Id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '项目部', '', '0', '2018-01-09 15:32:25');
INSERT INTO `department` VALUES ('2', '技术部', '', '0', '2018-01-10 11:17:22');
INSERT INTO `department` VALUES ('3', '采购部', '\0', '0', '2018-02-02 13:21:45');
INSERT INTO `department` VALUES ('4', '工程部', '\0', '0', '2018-02-02 13:22:20');
INSERT INTO `department` VALUES ('6', '质检部', '\0', '0', '2018-02-02 13:23:00');
INSERT INTO `department` VALUES ('7', '财务部', '\0', '0', '2018-02-02 13:23:13');

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_equipment_supplier_id` (`supplier_id`),
  CONSTRAINT `fk_equipment_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `file_type` varchar(50) DEFAULT NULL,
  `state` tinyint(3) DEFAULT NULL,
  `des` varchar(500) DEFAULT NULL,
  `upload_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_upload_user` (`upload_user_id`),
  CONSTRAINT `fk_file_upload_user` FOREIGN KEY (`upload_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1', 'test.pdf', '/community/2018-03-096ac8d3d0-4ba1-4802-8577-16848f508bb6.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('2', 'test.pdf', '/boiler/2018-03-096cd4b738-e0c5-4311-a306-6c49f7b9c005.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('3', 'test.pdf', '/boiler/2018-03-09ddb129dd-3105-454f-b086-1de12fa135e1.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('4', 'test.pdf', '/boiler/2018-03-09a719f191-aece-453f-86f5-0699e79f3181.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('5', 'test.pdf', '/boiler/2018-03-0989226b0e-e329-4394-890e-c8e880b00368.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('6', 'test.pdf', '/boiler/2018-03-09290e93ad-270b-487a-9481-20e3b221e0aa.pdf', 'application', null, null, '2');
INSERT INTO `file` VALUES ('9', 'test.pdf', '2018-03-09/d7c77fca-7c25-4495-840e-4b0ba5c8d965.pdf', 'application', null, null, '2');

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `des` varchar(500) DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT '1',
  `statue` tinyint(1) DEFAULT NULL COMMENT '已完成与未完成状态',
  `pro_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`),
  KEY `FKnj1axllhysj5l101mjgtwqidg` (`pro_id`),
  CONSTRAINT `FKnj1axllhysj5l101mjgtwqidg` FOREIGN KEY (`pro_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES ('1', '设备清单', '2018-01-29 13:14:01', '12312312321', '1', '0', '1');
INSERT INTO `inventory` VALUES ('2', '采购清单', '2018-01-29 13:14:22', '12312312312', '1', '1', '1');

-- ----------------------------
-- Table structure for line_body
-- ----------------------------
DROP TABLE IF EXISTS `line_body`;
CREATE TABLE `line_body` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of line_body
-- ----------------------------
INSERT INTO `line_body` VALUES ('8', '工艺1', null, '1');
INSERT INTO `line_body` VALUES ('9', '工艺2', null, '1');
INSERT INTO `line_body` VALUES ('10', '工艺3', null, '1');
INSERT INTO `line_body` VALUES ('11', '工艺4', null, '1');

-- ----------------------------
-- Table structure for line_process
-- ----------------------------
DROP TABLE IF EXISTS `line_process`;
CREATE TABLE `line_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `line_id` bigint(20) DEFAULT NULL,
  `process_id` bigint(20) DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT NULL,
  `sort` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK98alks0ysf7j3kjv29jfgsnpd` (`line_id`),
  KEY `FKe49wgb4ybfg1cyc16r45n6642` (`process_id`),
  CONSTRAINT `FK98alks0ysf7j3kjv29jfgsnpd` FOREIGN KEY (`line_id`) REFERENCES `line_body` (`id`),
  CONSTRAINT `FKe49wgb4ybfg1cyc16r45n6642` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=472 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of line_process
-- ----------------------------

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_material_supplier_id` (`supplier_id`),
  CONSTRAINT `fk_material_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `ico` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT '',
  `sort` int(11) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `has_parent` tinyint(1) DEFAULT NULL,
  `has_son` tinyint(1) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id` (`parent_id`),
  CONSTRAINT `fk_menu_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', 'fa fa-home', '/dashboard', '0', null, '0', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('2', '组织管理', 'fa fa-object-ungroup', '/basic', '1', null, '0', '1', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('3', '用户管理', 'fa fa-user-o', '/basic/user-manage', '11', '2', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('4', '角色管理', 'fa fa-vcard-o', '/basic/role-manage', '12', '2', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('5', '单位管理', 'fa fa-address-book-o', '/basic/unit-manage', '13', '2', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('6', '工艺流程', 'fa fa-sign-out', '/base_config/line-manage', '29', '8', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('7', '部门/员工管理', 'fa fa-th-large', '/basic/staff-manage', '15', '2', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('8', '基础配置', 'fa fa-gears', '/base_config', '2', null, '0', '1', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('10', '基础数据', 'fa fa-bars', '/base_config/infrastructure', '22', '8', '1', '1', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('11', '产品型号', 'fa fa-credit-card', '/base_config/infrastructure/type-manage', '23', '10', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('12', '设备', 'fa fa-magnet', '/base_config/infrastructure/equipment-manage', '24', '10', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('13', '材料', 'fa fa-file-zip-o', '/base_config/infrastructure/material-manage', '25', '10', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('14', '工序管理', 'fa fa-wrench', '/base_config/process-manage', '28', '8', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('15', '供应商', 'fa fa-handshake-o', '/base_config/infrastructure/supplier-manage', '26', '10', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('20', '清单管理', 'fa fa-hdd-o', '/inventory_manage', '4', null, '0', '1', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');
INSERT INTO `menu` VALUES ('34', '清单列表', 'fa fa-sitemap', '/inventory_manage/inventory-list', '40', '20', '1', '0', '', '2018-03-16 17:02:18', null, '2018-03-16 17:02:18');

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `menus_id` bigint(20) NOT NULL,
  `roles_code` varchar(50) NOT NULL,
  PRIMARY KEY (`menus_id`,`roles_code`),
  KEY `fk_roles_code` (`roles_code`),
  CONSTRAINT `fk_menu_role_menus_id` FOREIGN KEY (`menus_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `fk_menu_role_roles_code` FOREIGN KEY (`roles_code`) REFERENCES `role` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('3', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('4', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('5', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('7', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('8', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('10', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('11', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('12', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('13', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('14', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('15', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('20', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('34', 'ROLE_ADMIN');
INSERT INTO `menu_role` VALUES ('2', 'ROLE_USER');
INSERT INTO `menu_role` VALUES ('3', 'ROLE_USER');
INSERT INTO `menu_role` VALUES ('4', 'ROLE_USER');
INSERT INTO `menu_role` VALUES ('5', 'ROLE_USER');
INSERT INTO `menu_role` VALUES ('7', 'ROLE_USER');

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `delete_status` int(2) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of process
-- ----------------------------

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) DEFAULT NULL,
  `type_standard` varchar(100) DEFAULT NULL,
  `type_specification` varchar(100) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1q60x3yk7ndr3qox0iqxasd95` (`supplier_id`),
  CONSTRAINT `FK1q60x3yk7ndr3qox0iqxasd95` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_type
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `simple_name` varchar(100) DEFAULT NULL COMMENT '项目简称',
  `pro_plan_id` bigint(20) DEFAULT NULL COMMENT '项目计划书id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `des` varchar(500) DEFAULT NULL COMMENT '描述0未完成，1延期，2完成',
  `delete_statue` tinyint(1) DEFAULT '1',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `principal` bigint(20) DEFAULT NULL COMMENT '项目负责人',
  PRIMARY KEY (`id`),
  KEY `FK5y7ohb1pumd5nlif8mocj7em6` (`pro_plan_id`),
  KEY `FK4qa3tux2g18wn1wwbpot8ehb` (`principal`),
  CONSTRAINT `FK4qa3tux2g18wn1wwbpot8ehb` FOREIGN KEY (`principal`) REFERENCES `user` (`id`),
  CONSTRAINT `FK5y7ohb1pumd5nlif8mocj7em6` FOREIGN KEY (`pro_plan_id`) REFERENCES `project_plan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '海宁立德宝印染有限公司1x1500万Kcal/h水煤浆导热锅炉', '海宁立德宝1x1500万Kcal/h水煤浆导热油锅炉', '1', '2018-01-29 13:10:36', '122121221121212', '1', '0', '2018-01-31 10:01:42', '1');
INSERT INTO `project` VALUES ('2', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻', '详细信息', '1', '2018-01-16 10:33:55', '433333', '1', '1', '2018-01-17 10:34:07', '1');

-- ----------------------------
-- Table structure for project_plan
-- ----------------------------
DROP TABLE IF EXISTS `project_plan`;
CREATE TABLE `project_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

-- ----------------------------
-- Records of project_plan
-- ----------------------------
INSERT INTO `project_plan` VALUES ('1', '项目计划书');

-- ----------------------------
-- Table structure for project_template
-- ----------------------------
DROP TABLE IF EXISTS `project_template`;
CREATE TABLE `project_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `delete_statue` tinyint(1) DEFAULT '1',
  `des` varchar(500) DEFAULT NULL,
  `project_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_type_id` (`project_type_id`),
  CONSTRAINT `project_template_ibfk_1` FOREIGN KEY (`project_type_id`) REFERENCES `project_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_template
-- ----------------------------

-- ----------------------------
-- Table structure for project_type
-- ----------------------------
DROP TABLE IF EXISTS `project_type`;
CREATE TABLE `project_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_type_name` varchar(100) DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT '1',
  `des` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_type
-- ----------------------------

-- ----------------------------
-- Table structure for project_user
-- ----------------------------
DROP TABLE IF EXISTS `project_user`;
CREATE TABLE `project_user` (
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`project_id`),
  KEY `FK4ug72llnm0n7yafwntgdswl3y` (`project_id`),
  CONSTRAINT `FK4jl2o131jivd80xsuw6pivnbx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK4ug72llnm0n7yafwntgdswl3y` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_user
-- ----------------------------

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_supplier_id` (`supplier_id`),
  CONSTRAINT `fk_purchase_supplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchase
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `role_desc` varchar(250) DEFAULT NULL,
  `describe` text,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('ROLE_ADMIN', '管理员', '管理员', null);
INSERT INTO `role` VALUES ('ROLE_USER', '普通用户', '普通用户', null);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `link_man` varchar(50) DEFAULT NULL,
  `contact_information` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------

-- ----------------------------
-- Table structure for type_specification
-- ----------------------------
DROP TABLE IF EXISTS `type_specification`;
CREATE TABLE `type_specification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `equipment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_equipment_type` (`equipment_id`),
  CONSTRAINT `fk_equipment_type` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of type_specification
-- ----------------------------

-- ----------------------------
-- Table structure for units
-- ----------------------------
DROP TABLE IF EXISTS `units`;
CREATE TABLE `units` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT '0000-00-00 00:00:00',
  `code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of units
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `id_card` varchar(18) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `job_title` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`),
  KEY `fk_user_department_id` (`department_id`),
  CONSTRAINT `fk_user_department_id` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'system', 'system@localhost', '', null, '1', '2', '', '', 'zh-cn', '1', null, '\0', 'system', '2017-12-14 13:22:08', null, 'admin', '2018-05-06 22:53:13', '18829526587', '854');
INSERT INTO `user` VALUES ('2', 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'User', 'anonymous@localhost', '', null, '1', '', '', '', 'zh-cn', '1', null, '', 'system', '2017-12-14 13:22:08', null, 'admin', '2018-03-27 16:04:25', null, null);
INSERT INTO `user` VALUES ('3', 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'admin@localhost', '', null, null, '', '', '', 'zh-cn', '1', null, '\0', 'system', '2017-12-14 13:22:08', null, 'admin', '2018-05-06 22:53:31', '教师', null);
INSERT INTO `user` VALUES ('4', 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'user@localhost', '', null, '0', '18829012080', '', '', 'zh-cn', '1', null, '\0', 'system', '2017-12-14 13:22:08', null, 'admin', '2018-03-07 16:49:17', null, null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_code` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`role_code`),
  KEY `fk_role_code` (`role_code`),
  CONSTRAINT `fk_role_code` FOREIGN KEY (`role_code`) REFERENCES `role` (`code`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES ('3', 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES ('1', 'ROLE_USER');
INSERT INTO `user_role` VALUES ('3', 'ROLE_USER');

-- ----------------------------
-- Table structure for work_plan
-- ----------------------------
DROP TABLE IF EXISTS `work_plan`;
CREATE TABLE `work_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dep_id` bigint(20) DEFAULT NULL,
  `pro_id` bigint(20) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `has_parent` tinyint(1) DEFAULT NULL,
  `has_son` tinyint(1) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '预计开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '预计结束时间',
  `create_time` datetime DEFAULT NULL,
  `delete_statue` tinyint(1) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0表示未完成，1表示已结束，2表示延迟',
  `delay_time` datetime DEFAULT NULL COMMENT '延迟时间',
  `actual_time` datetime DEFAULT NULL COMMENT '实际完成时间',
  `sort` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7v9smbb2qejonenogvax5whvh` (`dep_id`),
  KEY `FKmlk2fgrtpuje6awyydjgt57p7` (`pro_id`),
  KEY `FKdqoyea0uq9q30md13cb7spa0w` (`parent_id`),
  CONSTRAINT `FK7v9smbb2qejonenogvax5whvh` FOREIGN KEY (`dep_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKdqoyea0uq9q30md13cb7spa0w` FOREIGN KEY (`parent_id`) REFERENCES `work_plan` (`id`),
  CONSTRAINT `FKmlk2fgrtpuje6awyydjgt57p7` FOREIGN KEY (`pro_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_plan
-- ----------------------------
INSERT INTO `work_plan` VALUES ('1', '1', '1', '编制设备采购条件、参与合格供应商的确定', null, '0', '0', '2018-01-04 13:58:26', '2018-01-11 13:58:31', '2018-01-01 13:58:35', '1', '1', null, '2018-01-11 13:58:56', '0');
INSERT INTO `work_plan` VALUES ('2', '1', '1', '锅炉房布置图定板', null, '0', '0', '2018-01-17 13:59:34', '2018-01-31 13:59:49', '2018-01-30 13:59:54', '1', '0', null, '2018-01-31 10:35:44', '1');
INSERT INTO `work_plan` VALUES ('3', '1', '1', '编制设备采购条件、参与合格供应商的确定', null, '0', '0', '2018-01-17 10:36:14', '2018-01-31 10:36:17', '2018-01-17 10:36:19', '1', '2', '2018-01-31 10:36:26', '2018-01-31 10:36:29', '2');
INSERT INTO `work_plan` VALUES ('4', '1', '2', '编制设备采购条件、参与合格供应商的确定', null, '0', '0', '2018-01-31 10:36:57', '2018-01-31 10:36:59', '2018-01-31 10:37:01', '1', '1', '2018-01-31 10:37:30', null, '3');

-- ----------------------------
-- Table structure for _persistent_audit_event
-- ----------------------------
DROP TABLE IF EXISTS `_persistent_audit_event`;
CREATE TABLE `_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=988 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _persistent_audit_event
-- ----------------------------

-- ----------------------------
-- Table structure for _persistent_audit_evt_data
-- ----------------------------
DROP TABLE IF EXISTS `_persistent_audit_evt_data`;
CREATE TABLE `_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _persistent_audit_evt_data
-- ----------------------------
