/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blogdb

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 16/05/2018 17:59:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int(11) NOT NULL COMMENT '文章分类id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父亲分类id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `gategory_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_uq_gategory_name`(`gategory_name`) USING BTREE,
  INDEX `ind_user_id`(`user_id`) USING BTREE,
  INDEX `FK_parent_id`(`parent_id`) USING BTREE,
  INDEX `IND_PRIMARY`(`id`) USING BTREE,
  INDEX `ind_gategory_name`(`gategory_name`) USING BTREE,
  CONSTRAINT `FK_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `t_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_comments
-- ----------------------------
DROP TABLE IF EXISTS `t_comments`;
CREATE TABLE `t_comments`  (
  `id` int(11) NOT NULL COMMENT '评论id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '被评论文章id',
  `post_id` int(11) NOT NULL COMMENT '评论用户id',
  `user_id` int(11) NOT NULL COMMENT '此条评论的父亲id',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `comment_author_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论者的邮箱',
  `comment_author_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论者的网站url',
  `comment_author_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论者的ip',
  `comment_date` datetime(0) NULL COMMENT '评论日期',
  `comment_visible` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '评论是否可见(可见Y,不可见N）',
  `comment_delete` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '评论是否删除(删除Y不删除N）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IND_PRIMARY`(`id`) USING BTREE,
  INDEX `ind_post_id`(`post_id`) USING BTREE,
  INDEX `ind_user_id`(`user_id`) USING BTREE,
  INDEX `ind_parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `FK_post_id` FOREIGN KEY (`post_id`) REFERENCES `t_posts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FFK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FFK_comments_id` FOREIGN KEY (`parent_id`) REFERENCES `t_comments` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_links
-- ----------------------------
DROP TABLE IF EXISTS `t_links`;
CREATE TABLE `t_links`  (
  `id` int(11) NOT NULL COMMENT '链接id',
  `user_id` int(11) NOT NULL COMMENT '链接所有者用户id',
  `link_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接名字',
  `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接url',
  `link_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接的描述',
  `link_visible` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_uq_link_url`(`link_url`) USING BTREE,
  INDEX `IND_PRIMARY`(`id`) USING BTREE,
  INDEX `ind_user_id`(`user_id`) USING BTREE,
  INDEX `ind_link_visible`(`link_visible`) USING BTREE,
  CONSTRAINT `FFKK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_posts
-- ----------------------------
DROP TABLE IF EXISTS `t_posts`;
CREATE TABLE `t_posts`  (
  `id` int(11) NOT NULL COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `category_id` int(11) NOT NULL COMMENT '文章分类id',
  `post_title` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `post_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章正文',
  `post_date` datetime(0) NULL COMMENT '发布日期',
  `post_excerpt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章摘要',
  `post_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'publish' COMMENT '发布状态(publish,saved)',
  `comment_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'open' COMMENT '评论状态(open，close)',
  `post_modified` datetime(0) NULL COMMENT '修改日期',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '评论数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IND_PRIMARY`(`id`) USING BTREE,
  INDEX `ind_user_id`(`user_id`) USING BTREE,
  INDEX `ind_gategory_id`(`category_id`) USING BTREE,
  INDEX `ind_post_date`(`post_date`) USING BTREE,
  CONSTRAINT `FK_gategory_id` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_users
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_privilege` smallint(6) NOT NULL DEFAULT 1 COMMENT '用户权限(管理员0，普通用户1)',
  `user_login` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `user_pass` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆密码',
  `user_nicename` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `user_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `user_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户网站url',
  `user_registered` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `user_status` smallint(6) NULL DEFAULT 1 COMMENT '用户状态(1可用，0不可用)',
  `site_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站名称',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次登陆ip',
  `login_times` bigint(20) NULL DEFAULT NULL COMMENT '最后一次登陆时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_uq_name`(`user_login`) USING BTREE,
  INDEX `IND_PRIMARY`(`id`) USING BTREE,
  INDEX `ind_user_nicename`(`user_nicename`) USING BTREE,
  INDEX `ind_user_login`(`user_login`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
