package com.bobabrewery.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class StartCommand extends BotCommand {

    public static final String s = "Welcome to Boba Brewery, kindly set your exclusive Brewery bot here. Please enter the command to  pair your Telegram account with your Brewery wallet address.\n" +
            "\n" +
            "Verify commands:\n" +
            "-/verification + your Brewery wallet address\n" +
            "(Make sure to leave AN EMPTY SPACE between 'verification' and 'your Brewery wallet address'\n" +
            "\n" +
            "Example: \n" +
            "/verification 0xxxxxx";

    public StartCommand() {
        super("start", "start info");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!user.getId().equals(chat.getId())) {
            return;
        }
        Long chatId = chat.getId();
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardButton joinGroup = new InlineKeyboardButton();
        joinGroup.setText("Telegram Community");
        joinGroup.setUrl("https://t.me/+6jNxyl-l96FhNDM5");
        InlineKeyboardButton joinChannel = new InlineKeyboardButton();
        joinChannel.setText("Official website");
        joinChannel.setUrl("https://bobabrewery.com/");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(joinGroup);
        buttons.add(joinChannel);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(buttons);
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(list));
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(s);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
