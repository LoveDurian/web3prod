package com.bobabrewery.telegram.handler;

import com.bobabrewery.telegram.command.StartCommand;
import com.bobabrewery.telegram.command.VerificationCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class CommandHandler extends TelegramLongPollingCommandBot {

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.name}")
    private String name;

    public CommandHandler(DefaultBotOptions options,
                          StartCommand startCommand,
                          VerificationCommand verificationCommand) {
        super(options);
        register(startCommand);
        register(verificationCommand);

    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        int size = update.getMessage().getNewChatMembers().size();
        if (size > 0) {
            for (User newChatMember : update.getMessage().getNewChatMembers()) {
                log.info("join:{}", newChatMember.getId());
            }
        }
        User leftChatMember = update.getMessage().getLeftChatMember();
        if (leftChatMember != null) {
            log.info("leave:{}", leftChatMember.getId());
        }
        log.info(update.toString());
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
