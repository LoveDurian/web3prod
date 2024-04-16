create table `brewery_ino_project`
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
  default charset = utf8mb4 comment 'INO项目表';