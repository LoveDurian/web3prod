package com.bobabrewery.mail.config;

import com.bobabrewery.mail.MailAllSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author orange
 */
@Configuration
@ConditionalOnProperty(
        prefix = "spring.mail",
        name = {"configs"}
)
public class MailSenderConfig {

    /**
     * AllMailSender 没有被配置时自动配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({MailAllSender.class})
    MailAllSender mailAllSender(MailConfig config) {
        MailAllSender allSender = new MailAllSender();
        List<JavaMailSenderImpl> javaMailSenders = new ArrayList<>();
        List<MailProperties> configs = config.getConfigs();
        for (MailProperties mailProperties : configs) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            this.applyProperties(mailProperties, sender);
            javaMailSenders.add(sender);
        }
        allSender.setJavaMailSenders(javaMailSenders);
        return allSender;
    }

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
        if (!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(this.asProperties(properties.getProperties()));
        }
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

}
