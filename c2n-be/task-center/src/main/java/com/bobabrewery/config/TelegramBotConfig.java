package com.bobabrewery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author orange
 */
@Configuration
public class TelegramBotConfig {

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 为TelegramBot设置代理
     *
     * @return
     */
    @Bean
    public DefaultBotOptions setBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        if ("dev".equals(env)) {
            defaultBotOptions.setProxyHost("127.0.0.1");
            defaultBotOptions.setProxyPort(1089);
            defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);
        }
        return defaultBotOptions;
    }

}
