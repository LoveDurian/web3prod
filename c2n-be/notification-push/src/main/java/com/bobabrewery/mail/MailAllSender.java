package com.bobabrewery.mail;

import com.bobabrewery.mail.constant.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author orange
 */
@Slf4j
public class MailAllSender {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private List<JavaMailSenderImpl> javaMailSenders;

    public List<JavaMailSenderImpl> getJavaMailSenders() {
        return javaMailSenders;
    }

    public void setJavaMailSenders(List<JavaMailSenderImpl> javaMailSenders) {
        this.javaMailSenders = javaMailSenders;
    }


    /**
     * 轮询使用MailSender
     *
     * @return
     */
    public JavaMailSenderImpl getSender() {
        Long increment = redisTemplate.opsForValue().increment(Constant.MAIL_SEND_STEP_NUM);
        long countNumber = increment != null ? increment : 0L;
        int size = this.javaMailSenders.size();
        int selectSender = (int) (countNumber % size);
        return this.javaMailSenders.get(selectSender);
    }


    @Data
    static class MailTemplate {
        private String title;
        private String content;
        private boolean html;
    }

    /**
     * 轮询使用MailSender
     *
     * @return
     */
    public void sender(MailPubSubMessage message) {
        JavaMailSenderImpl sender = this.getSender();
        MimeMessage mimeMessage = sender.createMimeMessage();

        Integer templateId = message.getTemplateId();

        List<Integer> attachmentFileIds = message.getAttachmentFileIds();

        MailTemplate mailTemplate = null;

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            mimeMessageHelper.setTo(message.getTo());
            mimeMessageHelper.setFrom(Objects.requireNonNull(sender.getUsername()));
            mimeMessageHelper.setText(mailTemplate.getContent(), mailTemplate.isHtml());
            mimeMessageHelper.setSubject(mailTemplate.getTitle());

            for (Integer attachmentFileId : attachmentFileIds) {
                File file = null;
                mimeMessageHelper.addAttachment(file.getName(), file);
            }

        } catch (MessagingException e) {
            log.error("");
        }

    }


    public void sendHtml() {


    }

    public void sendText() {

    }


}
