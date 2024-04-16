package com.bobabrewery.telegram.bean;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author PailieXiangLong
 */
@Data
public class TelegramMessage {
    private String chatId;
    private String message;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
}
