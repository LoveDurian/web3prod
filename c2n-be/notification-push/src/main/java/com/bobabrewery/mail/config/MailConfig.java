package com.bobabrewery.mail.config;


import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author orange
 */
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {
    private List<MailProperties> configs;

    public List<MailProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<MailProperties> configs) {
        this.configs = configs;
    }
}
