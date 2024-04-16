package com.bobabrewery.mail;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailService {

    public static final String CONTENT = "Dear brewer,\n" +
            "Congratulations on Winning the Brewery × Sacred Realm NFTgiveaway, your NFT will be distributed on July 20, more information will be provided by Brewery, follow us on Twitter!\n" +
            "\n" +
            "Giveaway Winner Details: \n" +
            "https://medium.com/@boba_brewery/brewery-sacred-realm-giveaway-winner-list-part-1-bb4c89e4f43d\n" +
            "\n" +
            "website: https://bobabrewery.com/\n" +
            "twitter: https://twitter.com/boba_brewery\n" +
            "discord: https://discord.gg/Uc5AWmgEcn\n" +
            "telegram: https://t.me/+6jNxyl-l96FhNDM5\n" +
            "\n" +
            "\n" +
            "Regards,\n" +
            "Boba Brewery Team";

    public static final String TITLE="Congratulations on Winning the Brewery × Sacred Realm NFTgiveaway";
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.from}")
    private String from;

    public void send(String mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(mail);
        helper.setSubject(TITLE);
        helper.setText(CONTENT, true);
        javaMailSender.send(message);
    }

}
