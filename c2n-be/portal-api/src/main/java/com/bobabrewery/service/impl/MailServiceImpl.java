package com.bobabrewery.service.impl;

import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.config.AsyncConfig;
import com.bobabrewery.config.MailConfig;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author yanpanyi
 * @date 2019/3/27
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MailConfig mailConfig;

    @Override
    public void sendAttachmentMail(String subject, String content, String toUser, String cc, String attachmentName, File file) throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setTo(toUser.split(","));
        mimeMessageHelper.setFrom(mailConfig.getFrom());
        if (StringUtils.isNotBlank(cc)) {
            mimeMessageHelper.setCc(cc.split(","));
        }
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.addAttachment(attachmentName, file);

        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败，发件人：[{}],收件人：[{}],主题：[{}]", mailConfig.getFrom(), toUser, subject, e);
        }
    }

    @Override
    public void sendTextMail(String subject, String content, String toUser) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setTo(toUser.split(","));
        mimeMessageHelper.setFrom(mailConfig.getFrom());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败，发件人：[{}],收件人：[{}],主题：[{}]", mailConfig.getFrom(), toUser, subject, e);
        }
    }

    /**
     * @Description: 发送html邮件
     * @author lc
     */
    @Override
    @Async(AsyncConfig.POOL_NAME)
    public void sendHtmlMail(String subject, String content, String toUser) throws Exception {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailConfig.getFrom());
            helper.setTo(toUser);
            helper.setSubject(subject);
            // 发送htmltext值需要给个true，不然不生效
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送邮件失败，发件人：[{}],收件人：[{}],主题：[{}]", mailConfig.getFrom(), toUser, subject, e);
            throw new CommonException(ReCode.MAIL_SEND_FAILD);
        }
    }
}
