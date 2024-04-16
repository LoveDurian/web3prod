package com.bobabrewery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanpanyi
 * @date 2019/3/28
 */
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

    /**
     * 发件人
     */
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


}
