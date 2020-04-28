/*
 Navicat Premium Data Transfer

 Source Server         : 10.95.130.115
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 10.95.130.115:8082
 Source Schema         : imageq-mind

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 28/04/2020 10:51:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_info
-- ----------------------------
DROP TABLE IF EXISTS `api_info`;
CREATE TABLE `api_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口地址',
  `method` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式(get/post)',
  `param_json` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `return_format` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回格式',
  `created_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'api接口说明' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for classify_label_result
-- ----------------------------
DROP TABLE IF EXISTS `classify_label_result`;
CREATE TABLE `classify_label_result`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `row_id` int(11) NULL DEFAULT NULL COMMENT '行id',
  `task_id` int(11) NULL DEFAULT NULL COMMENT '任务id',
  `tag_id` int(11) NULL DEFAULT NULL COMMENT '标签id',
  `created_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类标注结果集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_row
-- ----------------------------
DROP TABLE IF EXISTS `data_row`;
CREATE TABLE `data_row`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_set_id` int(11) NULL DEFAULT NULL COMMENT '数据集id',
  `row` int(11) NULL DEFAULT NULL COMMENT '行数',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1222 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_set
-- ----------------------------
DROP TABLE IF EXISTS `data_set`;
CREATE TABLE `data_set`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据集名称',
  `source_type` tinyint(4) NOT NULL COMMENT '数据集数据库类型',
  `type_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据集分类标识',
  `size` int(11) NULL DEFAULT NULL COMMENT '数量',
  `max_size` int(11) NULL DEFAULT NULL COMMENT '定义最大同步量',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '描述信息',
  `db_id` bigint(11) NULL DEFAULT NULL COMMENT '数据库id',
  `file_id` bigint(11) NULL DEFAULT NULL COMMENT '文件id',
  `column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据列的名称',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_task_map
-- ----------------------------
DROP TABLE IF EXISTS `data_task_map`;
CREATE TABLE `data_task_map`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `data_set_id` int(11) NOT NULL,
  `task_info_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_info
-- ----------------------------
DROP TABLE IF EXISTS `db_info`;
CREATE TABLE `db_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `connect_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `port` int(8) NULL DEFAULT NULL COMMENT '端口',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `database_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库名',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `pg_schema` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'pg schema',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `sheet_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队名称',
  `brief` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `logo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo',
  `status` int(1) NULL DEFAULT NULL COMMENT '@cname:状态,0,无效;1,有效',
  `create_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label_result
-- ----------------------------
DROP TABLE IF EXISTS `label_result`;
CREATE TABLE `label_result`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `row_id` int(11) NULL DEFAULT NULL COMMENT '行id',
  `task_id` int(11) NULL DEFAULT NULL COMMENT '任务id',
  `start` int(11) NULL DEFAULT NULL COMMENT '开始游标',
  `end` int(11) NULL DEFAULT NULL COMMENT '结束游标',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '划词内容',
  `tag_id` int(11) NULL DEFAULT NULL COMMENT '标签id',
  `color_index` int(5) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1163 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for model_info
-- ----------------------------
DROP TABLE IF EXISTS `model_info`;
CREATE TABLE `model_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `training_id` bigint(11) NULL DEFAULT NULL COMMENT '训练ID',
  `model_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型名称',
  `deploy_status` int(2) NULL DEFAULT NULL COMMENT '部署状态（0: 待部署，1：待发布，2：已发布）',
  `model_type` int(4) NULL DEFAULT NULL COMMENT '模型类型 1, \"实体识别\", 2, \"文本分类\", 3, \"关系抽取\"',
  `source_type` int(4) NULL DEFAULT NULL COMMENT '模型来源',
  `result_type` int(4) NULL DEFAULT NULL COMMENT '多结果：2，单结果：1',
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本',
  `types_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类标签',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型简介',
  `api_info_id` bigint(11) NULL DEFAULT NULL COMMENT 'api接口ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for model_market
-- ----------------------------
DROP TABLE IF EXISTS `model_market`;
CREATE TABLE `model_market`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(11) NOT NULL COMMENT '模型ID',
  `model_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模型名称',
  `request` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求文本',
  `response` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应文本',
  `deploy_status` int(2) NULL DEFAULT NULL COMMENT '部署状态',
  `call_time` int(6) NOT NULL COMMENT '调用次数',
  `created_by` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模型市场表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for priv
-- ----------------------------
DROP TABLE IF EXISTS `priv`;
CREATE TABLE `priv`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `priv_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `priv_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '@cname:更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type_id` int(11) NULL DEFAULT NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule_rel
-- ----------------------------
DROP TABLE IF EXISTS `rule_rel`;
CREATE TABLE `rule_rel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type_id` int(11) NOT NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort_no` int(4) NULL DEFAULT NULL,
  `model_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule_tag
-- ----------------------------
DROP TABLE IF EXISTS `rule_tag`;
CREATE TABLE `rule_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `rule_tag_map`;
CREATE TABLE `rule_tag_map`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule_word
-- ----------------------------
DROP TABLE IF EXISTS `rule_word`;
CREATE TABLE `rule_word`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NULL DEFAULT NULL,
  `l_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `r_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `word_spacing` int(11) NULL DEFAULT NULL,
  `sort_no` int(4) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule_word_rel
-- ----------------------------
DROP TABLE IF EXISTS `rule_word_rel`;
CREATE TABLE `rule_word_rel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_id` int(11) NULL DEFAULT NULL,
  `l_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `r_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `word_spacing` int(11) NULL DEFAULT NULL,
  `sort_no` int(4) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag_data_row
-- ----------------------------
DROP TABLE IF EXISTS `tag_data_row`;
CREATE TABLE `tag_data_row`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `data_set_id` int(11) NULL DEFAULT NULL COMMENT '数据集id',
  `data_row_id` int(11) NULL DEFAULT NULL COMMENT '行文本id',
  `tag_label_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签id',
  `tag_label_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `created_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标注分类的行文本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag_label
-- ----------------------------
DROP TABLE IF EXISTS `tag_label`;
CREATE TABLE `tag_label`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `type` int(4) NULL DEFAULT NULL COMMENT '标签类型 0: 未分类, 1:实体标签, 2:分类标签, 3:关系标签',
  `english_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签英文名称',
  `tag_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签样式',
  `font_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字体颜色',
  `backgroud_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景颜色',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `type_id` int(11) NOT NULL COMMENT '任务类型id',
  `size` int(11) NULL DEFAULT NULL COMMENT '任务量',
  `finished_size` int(11) NULL DEFAULT 0 COMMENT '完成量',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态',
  `current_row_id` bigint(11) NULL DEFAULT NULL COMMENT '当前标注任务行id',
  `process` decimal(12, 4) NULL DEFAULT NULL COMMENT '任务进度',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_row_map
-- ----------------------------
DROP TABLE IF EXISTS `task_row_map`;
CREATE TABLE `task_row_map`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `row_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2394 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `task_tag_map`;
CREATE TABLE `task_tag_map`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_type
-- ----------------------------
DROP TABLE IF EXISTS `task_type`;
CREATE TABLE `task_type`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类型名称',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for training_info
-- ----------------------------
DROP TABLE IF EXISTS `training_info`;
CREATE TABLE `training_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `type` int(4) NULL DEFAULT NULL COMMENT '模型训练类型',
  `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法返回的任务ID',
  `model_type` tinyint(4) NULL DEFAULT NULL COMMENT '1-基于字符  2-基于分词',
  `training_percent` decimal(12, 4) NULL DEFAULT NULL COMMENT '训练集比例',
  `validate_percent` decimal(12, 4) NULL DEFAULT NULL,
  `test_percent` decimal(12, 4) NULL DEFAULT NULL,
  `iteration_times` int(8) NULL DEFAULT NULL COMMENT '迭代次数',
  `batch_size` int(8) NULL DEFAULT NULL COMMENT '批量大小',
  `learning_rate` decimal(12, 6) NULL DEFAULT NULL COMMENT '学习率',
  `training_status` tinyint(4) NULL DEFAULT NULL,
  `dataset_dir` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `model_dir` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `training_result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `fail_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '失败信息',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `finish_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for training_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `training_tag_map`;
CREATE TABLE `training_tag_map`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `training_id` bigint(11) NULL DEFAULT NULL,
  `tag_id` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for training_task_map
-- ----------------------------
DROP TABLE IF EXISTS `training_task_map`;
CREATE TABLE `training_task_map`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `training_id` bigint(11) NULL DEFAULT NULL,
  `task_id` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `group_id` int(5) NULL DEFAULT NULL COMMENT '所属团队',
  `role_id` int(5) NULL DEFAULT NULL COMMENT '所属角色',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(1) NULL DEFAULT NULL COMMENT '用户状态,0,无效;1,有效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_add_words
-- ----------------------------
DROP TABLE IF EXISTS `user_add_words`;
CREATE TABLE `user_add_words`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_dir` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型保存路径',
  `word` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户添加的词语、人名等',
  `tag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户添加词语的词性',
  `rule` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户添加的规则表达式，后期该字段将废弃',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
