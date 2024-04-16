package com.bobabrewery.telegram.listener;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.telegram.BotSender;
import com.bobabrewery.telegram.bean.TelegramMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class TelegramMessageListener implements MessageListener {

    @Resource
    private BotSender botSender;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        try {
            TelegramMessage telegramMessage = JSON.parseObject(new String(body), TelegramMessage.class);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(telegramMessage.getMessage());
            sendMessage.setChatId(telegramMessage.getChatId());
            sendMessage.setReplyMarkup(telegramMessage.getInlineKeyboardMarkup());
            botSender.execute(sendMessage);
            log.info("MSG:{},{}", telegramMessage.getChatId(), telegramMessage.getMessage());
        } catch (TelegramApiException e) {
            log.error("[TGListener]:Telegram消息发送失败:", e);
        }

    }
}