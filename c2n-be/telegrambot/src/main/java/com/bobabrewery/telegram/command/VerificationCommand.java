package com.bobabrewery.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class VerificationCommand extends BotCommand {

    public static final String error = "Sorry, the verification failed. Please kindly check the command form, make sure you type in CORRECT wallet address and do not forget the SPACE between 'verification' and 'your Brewery wallet address'. \n\nFollow the example here:\n/verification 0xxxxxx";

    public static final String success = "You have successfully verified, your bind Brewery wallet address: {walletAddress}\n\nBe the early bird to go to the project page to join the current campaigns \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89";



    public VerificationCommand() {
        super("verification", "Verification you wallet address");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!user.getId().equals(chat.getId())) {
            return;
        }
        Long chatId = chat.getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        if (strings.length == 1) {
            String walletAddress = strings[0];
            //TODO
            String accountId = "";
            if (accountId != null) {
                //TODO: 更新用户信息
                int i = 0;
                if (i > 0) {
                    sendMessage.setText(success.replace("{walletAddress}", walletAddress));
                } else {
                    sendMessage.setText(error);
                }
            } else {
                sendMessage.setText(error);
            }
        } else {
            sendMessage.setText(error);
        }

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
