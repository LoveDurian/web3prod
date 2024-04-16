package com.bobabrewery.service;

import java.io.File;

/**
 * 邮件
 *
 * @author yanpanyi
 * @date 2019/3/27
 */
public interface MailService {


    /**
     * 发送带附件的邮件
     *
     * @param subject        邮件主题
     * @param content        邮件内容
     * @param toUser         收件人
     * @param cc             抄送人
     * @param attachmentName 附件名称
     * @param file           附件
     * @throws Exception
     */
    void sendAttachmentMail(String subject, String content, String toUser, String cc, String attachmentName, File file) throws Exception;

    /**
     * 发送纯文本邮件
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param toUser  对方邮件地址
     * @throws Exception
     */
    void sendTextMail(String subject, String content, String toUser) throws Exception;

    /**
     * 发送HTML邮件
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param toUser  对方邮件地址
     * @throws Exception
     */
    void sendHtmlMail(String subject, String content, String toUser) throws Exception;
}
