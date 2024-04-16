package com.bobabrewery.repo.mail.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 邮件模板表
 *
 * @TableName brewery_mail_template
 */
@Data
public class MailTemplate implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容html
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}