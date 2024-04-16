package com.bobabrewery.service;

import com.bobabrewery.repo.common.domain.model.UserInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramMessageService {


    void sendRegisterPlatformSuccess(UserInfo userInfo) throws TelegramApiException;

    /**
     * 发送项目注册成功消息
     *
     * @param userInfo
     * @param projectId
     */
    void sendRegisterProjectSuccess(UserInfo userInfo, Integer projectId) throws TelegramApiException;

    /**
     * 发送项目Participated成功消息
     *
     * @param accountId
     * @param projectId
     */
    void sendProjectParticipatedSuccess(String accountId, Integer projectId) throws TelegramApiException;


}
