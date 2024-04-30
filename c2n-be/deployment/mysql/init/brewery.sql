/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : brewery

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 30/04/2024 14:15:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brewery_airdrop
-- ----------------------------
DROP TABLE IF EXISTS `brewery_airdrop`;
CREATE TABLE `brewery_airdrop` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(8) NOT NULL COMMENT '项目ID',
  `wallet_address` varchar(60) NOT NULL COMMENT '钱包地址',
  `amount` bigint(20) NOT NULL COMMENT '参与项目获得的Airdrop数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='空投记录表';

-- ----------------------------
-- Records of brewery_airdrop
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_bridge_order
-- ----------------------------
DROP TABLE IF EXISTS `brewery_bridge_order`;
CREATE TABLE `brewery_bridge_order` (
  `id` varchar(40) NOT NULL COMMENT 'id',
  `account_id` varchar(42) NOT NULL COMMENT '用户钱包地址',
  `status` int(8) NOT NULL COMMENT '订单状态',
  `deposit_coin` varchar(42) DEFAULT NULL,
  `deposit_txid` varchar(42) DEFAULT NULL,
  `deposit_network` int(8) DEFAULT NULL,
  `deposit_addr` varchar(42) DEFAULT NULL,
  `deposit_amount` varchar(42) DEFAULT NULL COMMENT '接收token数',
  `sp_receive_addr` varchar(42) DEFAULT NULL,
  `sp_receive_memo` varchar(200) DEFAULT NULL,
  `receive_coin` varchar(42) DEFAULT NULL,
  `receive_network` int(8) DEFAULT NULL,
  `receive_addr` varchar(42) DEFAULT NULL,
  `receive_memo` varchar(200) DEFAULT NULL,
  `receive_amount` varchar(42) DEFAULT NULL COMMENT '发送token数',
  `receivet_txid` varchar(42) DEFAULT NULL,
  `receive_fee` varchar(42) DEFAULT NULL COMMENT '手续费',
  `tx_fee_rate` varchar(42) DEFAULT NULL,
  `refund_addr` varchar(42) DEFAULT NULL,
  `refund_memo` varchar(200) DEFAULT NULL,
  `refund_status` varchar(42) DEFAULT NULL,
  `refund_txid` int(8) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Records of brewery_bridge_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_medieval_order
-- ----------------------------
DROP TABLE IF EXISTS `brewery_medieval_order`;
CREATE TABLE `brewery_medieval_order` (
  `order_id` varchar(40) NOT NULL COMMENT 'id',
  `pid` int(8) NOT NULL COMMENT '项目ID',
  `wallet_address` varchar(42) NOT NULL COMMENT '钱包地址',
  `usage_code_id` int(8) NOT NULL COMMENT '使用的优惠码ID',
  `amount` int(8) NOT NULL COMMENT '购买数量',
  `actual_price` bigint(20) NOT NULL COMMENT '实际成交价格',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0-未支付,1-支付成功',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Medieval订单信息';

-- ----------------------------
-- Records of brewery_medieval_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_move_refund
-- ----------------------------
DROP TABLE IF EXISTS `brewery_move_refund`;
CREATE TABLE `brewery_move_refund` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) NOT NULL COMMENT '钱包地址',
  `token_id` int(8) DEFAULT NULL,
  `price` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_move_refund
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_nft
-- ----------------------------
DROP TABLE IF EXISTS `brewery_nft`;
CREATE TABLE `brewery_nft` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) NOT NULL COMMENT 'NFT项目名',
  `subTitle` varchar(60) DEFAULT NULL COMMENT '子标题',
  `logo` varchar(500) DEFAULT NULL COMMENT '项目Logo',
  `publisher_name` varchar(30) DEFAULT NULL COMMENT '项目方名称',
  `publisher_logo` varchar(200) DEFAULT NULL COMMENT '项目方Logo',
  `introduction` longtext NOT NULL COMMENT '简介',
  `steps` json NOT NULL COMMENT '项目阶段',
  `mint_contract` varchar(60) NOT NULL COMMENT 'Mint合约',
  `nft_contract` varchar(60) NOT NULL COMMENT 'NFT合约地址',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '项目状态 0-未开始 1-白名单 2-publicSale 3-end',
  `total_quantity` int(6) NOT NULL DEFAULT '0' COMMENT '总数量',
  `current_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前价格',
  `price` bigint(20) NOT NULL DEFAULT '0' COMMENT '单个NFT的价格 单位wei',
  `medias` json DEFAULT NULL COMMENT '项目相关媒体',
  `background` json DEFAULT NULL COMMENT '项目背景等参数',
  `chain_id` int(8) NOT NULL DEFAULT '0' COMMENT '链的chain_id',
  `expiration_time` datetime DEFAULT NULL COMMENT '截至时间',
  `issue_time` datetime DEFAULT NULL COMMENT 'IssueTime',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='NFT项目表';

-- ----------------------------
-- Records of brewery_nft
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_nft_move_order
-- ----------------------------
DROP TABLE IF EXISTS `brewery_nft_move_order`;
CREATE TABLE `brewery_nft_move_order` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tokenid` bigint(20) NOT NULL COMMENT '钱包地址',
  `status` int(2) NOT NULL,
  `txid` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_nft_move_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_nft_white
-- ----------------------------
DROP TABLE IF EXISTS `brewery_nft_white`;
CREATE TABLE `brewery_nft_white` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) NOT NULL COMMENT '钱包地址',
  `amount` int(8) NOT NULL COMMENT '限制次数',
  `usage_code_id` int(8) NOT NULL COMMENT '使用的优惠码ID',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0-未购买,1-购买成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='NFT白名单';

-- ----------------------------
-- Records of brewery_nft_white
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_promo_code
-- ----------------------------
DROP TABLE IF EXISTS `brewery_promo_code`;
CREATE TABLE `brewery_promo_code` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promo_code` varchar(40) NOT NULL COMMENT '优惠码',
  `discount` int(8) NOT NULL COMMENT '优惠减免',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_promo_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_referral_code
-- ----------------------------
DROP TABLE IF EXISTS `brewery_referral_code`;
CREATE TABLE `brewery_referral_code` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) NOT NULL COMMENT '邀请人用户钱包地址',
  `pid` int(8) NOT NULL COMMENT '项目ID',
  `referral_code` varchar(60) NOT NULL COMMENT '邀请码 md5(wallet_address+"_"+pid)',
  `participant` varchar(42) DEFAULT NULL COMMENT '参与人钱包地址',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0-邀请绑定 1-邀请成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_referral_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_sale_token_ids
-- ----------------------------
DROP TABLE IF EXISTS `brewery_sale_token_ids`;
CREATE TABLE `brewery_sale_token_ids` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) NOT NULL,
  `token_id` bigint(8) NOT NULL,
  `tx_id` varchar(60) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_sale_token_ids
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_voucher
-- ----------------------------
DROP TABLE IF EXISTS `brewery_voucher`;
CREATE TABLE `brewery_voucher` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) DEFAULT NULL COMMENT 'Voucher名单地址',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '使用状态 0-未使用 1-已经使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of brewery_voucher
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for brewery_wallet_status
-- ----------------------------
DROP TABLE IF EXISTS `brewery_wallet_status`;
CREATE TABLE `brewery_wallet_status` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(8) NOT NULL COMMENT '类型',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `wallet_address` varchar(42) NOT NULL COMMENT '用户钱包地址',
  `amount` decimal(10,0) NOT NULL COMMENT 'statking时累加withdraw时累减',
  `withdrawn` int(8) NOT NULL COMMENT '钱包地址是否已经withdraw过',
  `staked` int(8) NOT NULL COMMENT '该钱包地址是否已经staking过 0 否 1 是',
  `chain_id` int(8) NOT NULL COMMENT '链ID',
  `contract_address` varchar(42) DEFAULT NULL COMMENT '合约地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `brewery_wallet_status_wallet_address_uindex` (`wallet_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱包状态记录表';

-- ----------------------------
-- Records of brewery_wallet_status
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
BEGIN;
INSERT INTO `flyway_schema_history` VALUES (1, '1.0', 'Init db', 'SQL', 'V1.0__Init_db.sql', 353071075, 'root', '2024-04-30 05:39:35', 1363, 1);
INSERT INTO `flyway_schema_history` VALUES (2, '1.1', 'Init data', 'SQL', 'V1.1__Init_data.sql', 434007359, 'root', '2024-04-30 05:39:35', 22, 1);
COMMIT;

-- ----------------------------
-- Table structure for product_contract
-- ----------------------------
DROP TABLE IF EXISTS `product_contract`;
CREATE TABLE `product_contract` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(80) NOT NULL COMMENT '项目名称',
  `description` longtext COMMENT '项目描述',
  `img` varchar(500) DEFAULT NULL COMMENT '项目图标地址',
  `twitter_name` varchar(40) DEFAULT NULL,
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '项目状态',
  `amount` varchar(40) DEFAULT NULL COMMENT '当前币种质押个数',
  `sale_contract_address` varchar(42) DEFAULT NULL COMMENT 'sale合约地址',
  `token_address` varchar(42) DEFAULT NULL COMMENT 'bre合约地址',
  `payment_token` varchar(42) DEFAULT NULL COMMENT '支付地址',
  `follower` int(8) NOT NULL DEFAULT '0' COMMENT 'ins或推特的follow数',
  `tge` datetime DEFAULT NULL COMMENT 'tge、时间',
  `project_website` varchar(500) DEFAULT NULL COMMENT 'projectWebsite',
  `about_html` varchar(500) DEFAULT NULL COMMENT 'about_html',
  `registration_time_starts` datetime DEFAULT NULL COMMENT '开始时间',
  `registration_time_ends` datetime DEFAULT NULL COMMENT '结束时间',
  `sale_start` datetime DEFAULT NULL COMMENT 'sale开始时间',
  `sale_end` datetime DEFAULT NULL COMMENT 'sale结束时间',
  `max_participation` varchar(40) DEFAULT NULL COMMENT '硬顶',
  `token_price_in_PT` varchar(40) DEFAULT NULL COMMENT 'Token price',
  `total_tokens_sold` varchar(40) DEFAULT NULL COMMENT '所有已卖的token个数',
  `amount_of_tokens_to_sell` varchar(60) DEFAULT NULL COMMENT '质押币种',
  `total_raised` varchar(60) DEFAULT NULL COMMENT '质押币种单位',
  `symbol` varchar(60) DEFAULT NULL COMMENT '币种单位（缩写）',
  `decimals` int(8) DEFAULT '8' COMMENT '数位',
  `unlock_time` datetime DEFAULT NULL COMMENT '解锁时间',
  `medias` varchar(200) DEFAULT NULL COMMENT '媒体链接',
  `number_of_registrants` int(8) DEFAULT NULL COMMENT '注册人数',
  `vesting` varchar(40) DEFAULT NULL,
  `tricker` varchar(40) DEFAULT NULL,
  `token_name` varchar(20) DEFAULT NULL COMMENT 'token名',
  `roi` varchar(20) DEFAULT NULL COMMENT 'roi',
  `vesting_portions_unlock_time` varchar(60) DEFAULT NULL,
  `vesting_percent_per_portion` varchar(60) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `type` int(8) DEFAULT NULL COMMENT '项目类型',
  `card_link` varchar(200) DEFAULT NULL COMMENT '主页卡片跳转链接',
  `tweet_id` varchar(40) DEFAULT NULL COMMENT '转推任务的推文ID',
  `chain_id` int(8) DEFAULT '0' COMMENT '链ChainID',
  `payment_token_decimals` int(8) DEFAULT '8',
  `current_price` bigint(12) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of product_contract
-- ----------------------------
BEGIN;
INSERT INTO `product_contract` VALUES (1, 'pcontract_1', 'pcontract_1 desc', '/img/pc_1.jpg', 'david', 0, '10000000000000000000000', '0xbE1f0717E4Bfd6b3cAE527a543FdbA049671902f', '0x8332c63860eBAf9eCb1e61fb1829C76D2B2A1cB7', '200', 0, '2024-12-17 00:17:42', 'http://404.com', 'http://404.com/about.html', '2024-04-29 12:44:22', '2024-04-27 20:22:37', '2024-08-11 16:54:22', '2024-08-12 16:54:22', '10', '100000000000', '1', '30', '111', '222', 18, '2024-12-17 00:17:42', NULL, 1, NULL, NULL, 'DemoToken1', '1', NULL, NULL, '2024-04-25 12:25:07', '2024-04-25 12:25:07', 1, 'http://card_link1.com', 'tweet_id_1', 11155111, 18, 0);
INSERT INTO `product_contract` VALUES (2, 'pcontract_2', 'pcontract_2 desc', '/img/pc_2.jpg', 'david', 0, '100', '0x111222', '0x111333', '200', 0, '2024-04-25 20:21:47', 'http://404.com', 'http://404.com/about.html', '2024-04-24 20:22:31', '2024-04-27 20:22:37', '2024-04-27 20:22:53', '2024-04-30 20:23:00', '10', '2', '1', '30', '111', '222', 8, '2024-04-26 20:23:40', NULL, 1, NULL, NULL, 'DemoToken2', '1', NULL, NULL, '2024-04-25 12:25:07', '2024-04-25 12:25:07', 1, 'http://card_link2.com', 'tweet_id_2', 0, 8, 0);
COMMIT;

-- ----------------------------
-- Table structure for twitter_task
-- ----------------------------
DROP TABLE IF EXISTS `twitter_task`;
CREATE TABLE `twitter_task` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` varchar(42) DEFAULT NULL,
  `pid` int(8) DEFAULT NULL,
  `twitter_id` bigint(10) DEFAULT NULL,
  `twitter_name` varchar(40) DEFAULT NULL,
  `retweet_link` varchar(200) DEFAULT NULL,
  `follower` int(2) DEFAULT '0' COMMENT '0 未关注 1 已关注',
  `retweet` int(2) DEFAULT '0' COMMENT '0 未转发 1 已转发',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of twitter_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account_id` varchar(42) NOT NULL COMMENT '用户的钱包地址',
  `login_email` varchar(64) NOT NULL COMMENT '邮箱地址',
  `tg_name` varchar(32) DEFAULT NULL COMMENT '登录时签名的UUID',
  `tg_id` varchar(32) DEFAULT NULL COMMENT '注册IP',
  `login_time` datetime DEFAULT NULL COMMENT '链接钱包地址时的IP',
  `login_ip` varchar(32) DEFAULT NULL COMMENT 'telegram名称',
  `user_address` varchar(40) DEFAULT NULL COMMENT 'telegramID',
  `create_time` varchar(40) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `brewery_user_account_id_uindex` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_register_product
-- ----------------------------
DROP TABLE IF EXISTS `user_register_product`;
CREATE TABLE `user_register_product` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` varchar(42) NOT NULL COMMENT '钱包id',
  `product_id` int(8) DEFAULT NULL COMMENT '用户注册项目id',
  `staking_amount` varchar(20) DEFAULT NULL COMMENT '用户质押金额',
  `win_amount` varchar(20) DEFAULT NULL COMMENT '用户质押金额后可得到amount数量',
  `purchased` varchar(40) DEFAULT NULL COMMENT 'purchase',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_register_product
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for who_staked_log
-- ----------------------------
DROP TABLE IF EXISTS `who_staked_log`;
CREATE TABLE `who_staked_log` (
  `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wallet_address` varchar(42) DEFAULT NULL COMMENT '钱包地址',
  `contract_address` varchar(42) DEFAULT NULL COMMENT '合约地址',
  `ip` varchar(40) DEFAULT NULL COMMENT 'stake时的IP',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of who_staked_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
