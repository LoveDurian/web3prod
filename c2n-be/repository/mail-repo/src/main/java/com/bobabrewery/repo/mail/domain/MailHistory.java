package com.bobabrewery.repo.mail.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 邮件发送历史表
 *
 * @TableName brewery_mail_history
 */
@Data
public class MailHistory implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 邮件模板ID
     */
    private Long templateId;

    /**
     * 电子邮件地址
     */
    private String email;

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