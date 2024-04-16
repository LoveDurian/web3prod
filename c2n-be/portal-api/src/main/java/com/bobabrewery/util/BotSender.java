package com.bobabrewery.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author orange
 */
@Component
public class BotSender extends DefaultAbsSender {

    protected BotSender(DefaultBotOptions options) {
        super(options);
    }

    @Value("${telegram.token}")
    private String token;

    @Override
    public String getBotToken() {
        return token;
    }
}
