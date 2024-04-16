create table `brewery_mail_template`
(
    `id`          bigint(8) unsigned not null auto_increment comment 'id',
    `title`       varchar(255)       not null comment '邮件标题',
    `content`     longtext           not null comment '邮件内容html',
    `create_time` datetime           not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time` datetime           not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    primary key (`id`)
) ENGINE = InnoDB
  default charset = utf8mb4 comment ='邮件模板表';

create table `brewery_mail_history`
(
    `id`          bigint(8) unsigned not null auto_increment comment 'id',
    `template_id` bigint(8) unsigned not null comment '邮件模板ID',
    `email`       varchar(200)       not null comment '电子邮件地址',
    `create_time` datetime           not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time` datetime           not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    primary key (`id`)
) ENGINE = InnoDB
  default charset = utf8mb4 comment ='邮件发送历史表';