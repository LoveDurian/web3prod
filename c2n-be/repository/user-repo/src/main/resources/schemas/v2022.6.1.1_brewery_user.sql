CREATE TABLE `brewery_user`
(
    `id`             bigint(8) unsigned primary key auto_increment NOT NULL COMMENT '用户ID',
    `wallet_address` varchar(42)                                   not null comment '用户的钱包地址',
    `email`          varchar(64)                                   NOT NULL COMMENT '邮箱地址',
    `code`           varchar(32)                                            DEFAULT NULL COMMENT '登录时签名的UUID',
    `register_ip`    varchar(12)                                   NOT NULL COMMENT '注册IP',
    `login_ip`       varchar(12)                                            DEFAULT NULL COMMENT '链接钱包地址时的IP',
    `telegram_name`  varchar(32)                                            DEFAULT NULL COMMENT 'telegram名称',
    `telegram_id`    bigint(20)                                             DEFAULT NULL COMMENT 'telegramID',
    `region`         varchar(32)                                            DEFAULT NULL COMMENT '用户所在地区',
    `create_time`    datetime                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `brewery_user_email_uindex` (`email`),
    UNIQUE KEY `brewery_user_wallet_address_uindex` (`wallet_address`),
    UNIQUE KEY `brewery_user_telegram_id_uindex` (`telegram_id`),
    UNIQUE KEY `brewery_email_id_telegram_id_uindex` (`email`, `wallet_address`, `telegram_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';