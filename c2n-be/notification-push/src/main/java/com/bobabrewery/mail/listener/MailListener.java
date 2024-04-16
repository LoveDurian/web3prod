package com.bobabrewery.mail.listener;

import com.bobabrewery.mail.MailAllSender;
import com.bobabrewery.mail.MailPubSubMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class MailListener implements MessageListener {


    @Resource
    private MailAllSender mailAllSender;

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String s = new String(body);
        try {
            MailPubSubMessage mailPubSubMessage = objectMapper.readValue(s, MailPubSubMessage.class);


        } catch (JsonProcessingException e) {
            log.error("json 消息解析失败");
        }


    }
}
