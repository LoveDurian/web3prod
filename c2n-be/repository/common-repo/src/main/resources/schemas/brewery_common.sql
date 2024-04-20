create table `brewery_airdrop`
(
    `id`              int(8) unsigned primary key not null auto_increment comment 'id',
    `pid`             int(8)                      not null comment '项目ID',
    `wallet_address`  varchar(60)                 not null comment '钱包地址',
    `amount`          bigint(20)                  not null comment '参与项目获得的Airdrop数量'
) engine = InnoDB
  default charset = utf8mb4 comment '空投记录表';

create table `brewery_wallet_status`
(
    `id`              bigint(8) unsigned primary key not null auto_increment comment 'id',
    `type`            int(8)                         not null comment '类型',
    `create_time`     datetime                       not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                       not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `wallet_address`  varchar(42)                    not null comment '用户钱包地址',
    `amount`          decimal                        not null comment 'statking时累加withdraw时累减',
    `withdrawn`       int(8)                         not null comment '钱包地址是否已经withdraw过',
    `staked`          int(8)                         not null comment '该钱包地址是否已经staking过 0 否 1 是',
    `chain_id`        int(8)                         not null comment '链ID',
    `contract_address` varchar(42)                   comment "合约地址",
    UNIQUE KEY `brewery_wallet_status_wallet_address_uindex` (`wallet_address`)
) engine = InnoDB
  default charset = utf8mb4 comment '钱包状态记录表';

create table `brewery_bridge_order`
(
    `id`              varchar(40)        primary key not null comment 'id',
    `account_id`      varchar(42)                    not null comment '用户钱包地址',
    `status`          int(8)                         not null comment '订单状态',
    `deposit_coin`    varchar(42)                    ,
    `deposit_txid`    varchar(42)                    ,
    `deposit_network` int(8)                         ,
    `deposit_addr`    varchar(42)                    ,
    `deposit_amount`  varchar(42)                    comment '接收token数',
    `sp_receive_addr` varchar(42)                    ,
    `sp_receive_memo` varchar(200)                   ,
    `receive_coin`    varchar(42)                    ,
    `receive_network` int(8)                         ,
    `receive_addr`    varchar(42)                    ,
    `receive_memo`    varchar(200)                   ,
    `receive_amount`  varchar(42)                    comment '发送token数',
    `receivet_txid`   varchar(42)                    ,
    `receive_fee`     varchar(42)                    comment '手续费',
    `tx_fee_rate`     varchar(42)                    ,
    `refund_addr`     varchar(42)                    ,
    `refund_memo`     varchar(200)                   ,
    `refund_status`   varchar(42)                    ,
    `refund_txid`     int(8)                         ,
    `create_time`     datetime                       not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                       not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) engine = InnoDB
  default charset = utf8mb4 comment '订单表';

create table `brewery_move_refund`
(
    `id`              bigint(8) unsigned primary key not null auto_increment comment 'id',
    `wallet_address`  varchar(42)                    not null comment '钱包地址',
    `token_id`        int(8)                         ,
    `price`           int(8)
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_nft`
(
    `id`              int(8) unsigned primary key not null auto_increment comment 'id',
    `name`            varchar(200)                not null comment 'NFT项目名',
    `subTitle`        varchar(60)                          default null comment '子标题',
    `logo`            varchar(500)                         default null comment '项目Logo',
    `publisher_name`  varchar(30)                          default null comment '项目方名称',
    `publisher_logo`  varchar(200)                         default null comment '项目方Logo',
    `introduction`    longtext                    not null comment '简介',
    `steps`           json                        not null comment '项目阶段',
    `mint_contract`   varchar(60)                 not null comment 'Mint合约',
    `nft_contract`    varchar(60)                 not null comment 'NFT合约地址',
    `status`          int(2)                      not null default 0 comment '项目状态 0-未开始 1-白名单 2-publicSale 3-end',
    `total_quantity`  int(6)                      not null default 0 comment '总数量',
    `current_price`   bigint(20)                  not null default 0 comment '当前价格',
    `price`           bigint(20)                  not null default 0 comment '单个NFT的价格 单位wei',
    `medias`          json                                 default null comment '项目相关媒体',
    `background`      json                                 default null comment '项目背景等参数',
    `chain_id`        int(8)                      not null default 0 comment '链的chain_id',
    `expiration_time` datetime                             default null comment '截至时间',
    `issue_time`      datetime                             default null comment 'IssueTime',
    `create_time`     datetime                    not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
) engine = InnoDB
  default charset = utf8mb4 comment 'NFT项目表';

create table `brewery_nft_move_order`
(
    `id`              bigint(8) unsigned primary key not null auto_increment comment 'id',
    `tokenid`         bigint(20)                     not null comment '钱包地址',
    `status`          int(2)                         not null,
    `txid`            varchar(60)                    not null
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_medieval_order`
(
    `order_id`        varchar(40)   primary key not null comment 'id',
    `pid`             int(8)                    not null comment '项目ID',
    `wallet_address`  varchar(42)               not null comment '钱包地址',
    `usage_code_id`   int(8)                    not null comment '使用的优惠码ID',
    `amount`          int(8)                    not null comment '购买数量',
    `actual_price`    bigint(20)                not null comment '实际成交价格',
    `status`          int(2)                    not null default 0 comment '0-未支付,1-支付成功',
    `create_time`     datetime                  not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                  not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
) engine = InnoDB
  default charset = utf8mb4 comment 'Medieval订单信息';

create table `brewery_nft_white`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `wallet_address`  varchar(42)                      not null comment '钱包地址',
    `amount`          int(8)                           not null comment '限制次数',
    `usage_code_id`   int(8)                           not null comment '使用的优惠码ID',
    `status`          int(2)                           not null default 0 comment '0-未购买,1-购买成功'
) engine = InnoDB
  default charset = utf8mb4 comment 'NFT白名单';

create table `product_contract`
(
    `id`                       int(8) unsigned primary key not null auto_increment comment 'id',
    `name`                     varchar(80)                 not null comment '项目名称',
    `description`              longtext                    default null comment '项目描述',
    `img`                      varchar(500)                default null comment '项目图标地址',
    `twitter_name`             varchar(40)                 default null,
    `status`                   int(4)                      not null default 0 comment '项目状态',
    `amount`                   varchar(40)                 default null comment '当前币种质押个数',
    `sale_contract_address`    varchar(42)                 default null comment 'sale合约地址',
    `token_address`            varchar(42)                 default null comment 'bre合约地址',
    `payment_token`            varchar(42)                 default null comment '支付地址',
    `follower`                 int(8)                      not null default 0 comment 'ins或推特的follow数',
    `tge`                      datetime                    default null comment 'tge、时间',
    `project_website`          varchar(500)                default null comment 'projectWebsite',
    `about_html`               varchar(500)                default null comment 'about_html',
    `registration_time_starts` datetime                    default null comment '开始时间',
    `registration_time_ends`   datetime                    default null comment '结束时间',
    `sale_start`               datetime                    default null comment 'sale开始时间',
    `sale_end`                 datetime                    default null comment 'sale结束时间',
    `max_participation`        varchar(40)                 default null comment '硬顶',
    `token_price_in_PT`        varchar(40)                 default null comment 'Token price',
    `total_tokens_sold`        varchar(40)                 default null comment '所有已卖的token个数',
    `amount_of_tokens_to_sell` varchar(60)                 default null comment '质押币种',
    `total_raised`             varchar(60)                 default null comment '质押币种单位',
    `symbol`                   varchar(60)                 default null comment '币种单位（缩写）',
    `decimals`                 int(8)                      default 8    comment '数位',
    `unlock_time`              datetime                    default null comment '解锁时间',
    `medias`                   varchar(200)                default null comment '媒体链接',
    `number_of_registrants`    int(8)                      default null comment '注册人数',
    `vesting`                  varchar(40)                 default null ,
    `tricker`                  varchar(40)                 default null ,
    `token_name`               varchar(20)                 default null comment 'token名',
    `roi`                      varchar(20)                 default null comment 'roi',
    `vesting_portions_unlock_time`  varchar(60)            default null,
    `vesting_percent_per_portion`   varchar(60)            default null,
    `create_time`              datetime                    not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`              datetime                    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    `type`                     int(8)                      default null comment '项目类型',
    `card_link`                varchar(200)                default null comment '主页卡片跳转链接',
    `tweet_id`                 varchar(40)                 default null comment '转推任务的推文ID',
    `chain_id`                 int(8)                      default 0 comment '链ChainID',
    `payment_token_decimals`   int(8)                      default 8,
    `current_price`            bigint(12)                  default 0
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_promo_code`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `promo_code`      varchar(40)                      not null comment '优惠码',
    `discount`        int(8)                           not null comment '优惠减免'
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_referral_code`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `wallet_address`  varchar(42)                      not null comment '邀请人用户钱包地址',
    `pid`             int(8)                           not null comment '项目ID',
    `referral_code`   varchar(60)                      not null comment '邀请码 md5(wallet_address+"_"+pid)',
    `participant`     varchar(42)                      default null comment '参与人钱包地址',
    `status`          int(2)                           not null default 0 comment '0-邀请绑定 1-邀请成功'
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_sale_token_ids`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `wallet_address`  varchar(42)                      not null ,
    `token_id`        bigint(8)                        not null ,
    `tx_id`           varchar(60)                      not null ,
    `status`          int(2)                           not null default 0
) engine = InnoDB
  default charset = utf8mb4;

create table `twitter_task`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `account_id`      varchar(42)                      default null,
    `pid`             int(8)                           default null,
    `twitter_id`      bigint(10)                       default null,
    `twitter_name`    varchar(40)                      default null,
    `retweet_link`    varchar(200)                     default null,
    `follower`        int(2)                           default 0 comment '0 未关注 1 已关注',
    `retweet`         int(2)                           default 0 comment '0 未转发 1 已转发',
    `create_time`     datetime                         not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
) engine = InnoDB
  default charset = utf8mb4;

CREATE TABLE `user_info`
(
    `id`             int(8)    unsigned primary key auto_increment NOT NULL COMMENT '用户ID',
    `account_id`     varchar(42)                                   NOT NULL comment '用户的钱包地址',
    `login_email`    varchar(64)                                   NOT NULL COMMENT '邮箱地址',
    `tg_name`        varchar(32)                                   DEFAULT NULL COMMENT '登录时签名的UUID',
    `tg_id`          varchar(32)                                   DEFAULT NULL COMMENT '注册IP',
    `login_time`     datetime                                      DEFAULT NULL COMMENT '链接钱包地址时的IP',
    `login_ip`       varchar(32)                                   DEFAULT NULL COMMENT 'telegram名称',
    `user_address`   varchar(40)                                   DEFAULT NULL COMMENT 'telegramID',
    `create_time`    varchar(40)                                   DEFAULT NULL COMMENT '创建时间',
    UNIQUE KEY `brewery_user_account_id_uindex` (`account_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table `user_register_product`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `account_id`      varchar(42)                      not null comment '钱包id',
    `product_id`      int(8)                           default null comment '用户注册项目id',
    `staking_amount`  varchar(20)                      default null comment '用户质押金额',
    `win_amount`      varchar(20)                      default null comment '用户质押金额后可得到amount数量',
    `purchased`       varchar(40)                      default null comment 'purchase',
    `create_time`     datetime                         not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime                         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
) engine = InnoDB
  default charset = utf8mb4;

create table `brewery_voucher`
(
    `id`              bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `wallet_address`  varchar(42)                      default null comment 'Voucher名单地址',
    `status`          int(2)                           not null default 0 comment '使用状态 0-未使用 1-已经使用'
) engine = InnoDB
  default charset = utf8mb4;

create table `who_staked_log`
(
    `id`                bigint(8)   unsigned primary key not null auto_increment comment 'id',
    `wallet_address`    varchar(42)                      default null comment '钱包地址',
    `contract_address`  varchar(42)                      default null comment '合约地址',
    `ip`                varchar(40)                      default null comment 'stake时的IP',
    `create_time`       datetime                         not null default CURRENT_TIMESTAMP comment '创建时间'
) engine = InnoDB
  default charset = utf8mb4;