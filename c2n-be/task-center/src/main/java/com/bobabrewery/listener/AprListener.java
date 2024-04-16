package com.bobabrewery.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AprListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        log.info("收到的消息为:{}:{},pattern:{}", new String(message.getChannel()), new String(body), new String(pattern));
    }
}
