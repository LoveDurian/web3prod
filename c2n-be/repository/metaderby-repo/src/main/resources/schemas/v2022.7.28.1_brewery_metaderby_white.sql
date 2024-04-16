CREATE TABLE `brewery_meta_derby_white`
(
    `id`             bigint(8) auto_increment primary key NOT NULL COMMENT 'ID',
    `wallet_address` varchar(64)                          NOT NULL COMMENT '钱包地址',
    `horses`         int(8)                               NOT NULL COMMENT '使用10U的马匹ID',
    `create_time`    datetime                             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `brewery_meta_derby_user`
(
    `id`             bigint(8) auto_increment primary key NOT NULL COMMENT 'ID',
    `wallet_address` varchar(64)                          NOT NULL COMMENT '钱包地址',
    `estimate`       bigint(30)                           NOT NULL COMMENT '预估得到的金额 wei',
    `create_time`    datetime                             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
