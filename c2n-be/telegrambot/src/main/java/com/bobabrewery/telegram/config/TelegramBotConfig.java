package com.bobabrewery.telegram.config;

import com.bobabrewery.telegram.handler.CommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Configuration
public class TelegramBotConfig {
    @Resource
    private CommandHandler commandHandler;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(commandHandler);
        return telegramBotsApi;
    }

}