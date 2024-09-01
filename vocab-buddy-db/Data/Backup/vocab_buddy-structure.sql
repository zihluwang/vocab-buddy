/*
 Navicat Premium Data Transfer

 Source Server         : Local UnRaid Server
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 192.168.2.51:3306
 Source Schema         : vocab_buddy

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 15/06/2023 10:40:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator_logs
-- ----------------------------
DROP TABLE IF EXISTS `administrator_logs`;
CREATE TABLE `administrator_logs` (
  `id` bigint NOT NULL,
  `admin_id` bigint NOT NULL,
  `operation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__administrator_logs__administrators` (`admin_id`) USING BTREE,
  CONSTRAINT `fk__administrator_logs__administrators` FOREIGN KEY (`admin_id`) REFERENCES `administrators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for administrators
-- ----------------------------
DROP TABLE IF EXISTS `administrators`;
CREATE TABLE `administrators` (
  `id` bigint NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(320) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `region` int DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `admin_type` tinyint NOT NULL DEFAULT '1',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for dictation_details
-- ----------------------------
DROP TABLE IF EXISTS `dictation_details`;
CREATE TABLE `dictation_details` (
  `id` bigint NOT NULL,
  `dictation_id` bigint NOT NULL,
  `word_id` bigint NOT NULL,
  `user_spelling` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `correct` tinyint NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__dictation_details__dictations` (`dictation_id`) USING BTREE,
  KEY `fk__dictation_details__wordbooks` (`word_id`) USING BTREE,
  CONSTRAINT `fk__dictation_details__dictations` FOREIGN KEY (`dictation_id`) REFERENCES `dictations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk__dictation_details__wordbooks` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for dictations
-- ----------------------------
DROP TABLE IF EXISTS `dictations`;
CREATE TABLE `dictations` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `answering_mode` tinyint NOT NULL,
  `generation_mode` tinyint NOT NULL,
  `tag_id` bigint DEFAULT NULL,
  `words_count` int NOT NULL DEFAULT '0',
  `correct_words_count` int NOT NULL DEFAULT '0',
  `incorrect_words_count` int NOT NULL DEFAULT '0',
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__dictations__users` (`user_id`) USING BTREE,
  KEY `fk__dictations__tags` (`tag_id`) USING BTREE,
  CONSTRAINT `fk__dictations__tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk__dictations__users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` bigint NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_logs
-- ----------------------------
DROP TABLE IF EXISTS `user_logs`;
CREATE TABLE `user_logs` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `operation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__user_logs__users` (`user_id`) USING BTREE,
  CONSTRAINT `fk__user_logs__users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL COMMENT 'ID of the user generated using the Snowflake algorithm',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `region` int DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(320) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade` tinyint(1) NOT NULL COMMENT 'From 1-12 denotes Primary 1, Primary 2,... High School Year 3',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for word_descriptions
-- ----------------------------
DROP TABLE IF EXISTS `word_descriptions`;
CREATE TABLE `word_descriptions` (
  `id` bigint NOT NULL,
  `word_id` bigint NOT NULL,
  `phonetics` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `word_class` tinyint NOT NULL,
  `meaning` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__word_descriptions__wordbooks` (`word_id`) USING BTREE,
  CONSTRAINT `fk__word_descriptions__wordbooks` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for word_tags
-- ----------------------------
DROP TABLE IF EXISTS `word_tags`;
CREATE TABLE `word_tags` (
  `id` bigint NOT NULL,
  `word_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk__word_tags__tags` (`tag_id`) USING BTREE,
  KEY `fk__word_tags__wordbooks` (`word_id`) USING BTREE,
  CONSTRAINT `fk__word_tags__tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk__word_tags__wordbooks` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for words
-- ----------------------------
DROP TABLE IF EXISTS `words`;
CREATE TABLE `words` (
  `id` bigint NOT NULL,
  `word` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The longest word in English by Oxford Dictionaries contains 45 letters.',
  `create_at` datetime NOT NULL,
  `create_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `archived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- View structure for view_dictation_details
-- ----------------------------
DROP VIEW IF EXISTS `view_dictation_details`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_dictation_details` AS select `dictation_details`.`id` AS `id`,`dictation_details`.`dictation_id` AS `dictation_id`,`words`.`id` AS `word_id`,`words`.`word` AS `word`,`dictation_details`.`user_spelling` AS `user_spelling`,`dictation_details`.`correct` AS `correct`,`dictation_details`.`create_at` AS `create_at`,`dictation_details`.`create_by` AS `create_by`,`dictation_details`.`update_at` AS `update_at`,`dictation_details`.`update_by` AS `update_by`,`dictation_details`.`archived` AS `archived` from (`dictation_details` join `words` on((`dictation_details`.`word_id` = `words`.`id`)));

-- ----------------------------
-- View structure for view_tags
-- ----------------------------
DROP VIEW IF EXISTS `view_tags`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_tags` AS select `t`.`id` AS `id`,`t`.`name` AS `NAME`,`t`.`code` AS `CODE`,count(0) AS `word_count`,`t`.`create_by` AS `create_by`,`t`.`create_at` AS `create_at`,`t`.`update_by` AS `update_by`,`t`.`update_at` AS `update_at`,`t`.`archived` AS `archived` from (`word_tags` `wt` left join `tags` `t` on((`wt`.`tag_id` = `t`.`id`))) group by `t`.`id`;

SET FOREIGN_KEY_CHECKS = 1;
