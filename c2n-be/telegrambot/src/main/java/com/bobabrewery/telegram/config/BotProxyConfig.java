package com.bobabrewery.telegram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author PailieXiangLong
 */
@Configuration
public class BotProxyConfig {


    @Value("${telegram.proxy}")
    private Boolean proxy = false;

    /**
     * 为TelegramBot设置代理
     *
     * @return
     */
    @Bean
    public DefaultBotOptions setBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        if (proxy) {
            defaultBotOptions.setProxyHost("127.0.0.1");
            defaultBotOptions.setProxyPort(1089);
            defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        }
        return defaultBotOptions;
    }

}
